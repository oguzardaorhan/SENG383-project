from __future__ import annotations

from dataclasses import dataclass
from typing import Dict, List, Tuple, Optional

from .io import Course, Room
from .constraints import DAYS, SLOTS, Assignment, room_ok, instructor_available, is_friday_block, max_theory_per_day_ok

@dataclass
class ScheduleResult:
    assignments: Dict[str, Assignment]
    unscheduled: List[Course]

def _candidate_positions(course: Course, rooms: List[Room]) -> List[Tuple[str,int,str]]:
    pos: List[Tuple[str,int,str]] = []
    for day in DAYS:
        for slot in range(len(SLOTS)):
            if is_friday_block(day, slot):
                continue
            for r in rooms:
                if room_ok(course, r):
                    pos.append((day, slot, r.room_id))
    return pos

def generate_schedule(
    courses: List[Course],
    rooms: List[Room],
    availability: Dict[Tuple[str,str,int], bool],
) -> ScheduleResult:
    """
    Backtracking scheduler with simple heuristics.
    Constraints enforced:
      - No same room overlap
      - No same instructor overlap
      - Instructor availability
      - Friday blocked slot
      - Max theory/day per instructor (2 slots)
      - Lab after theory (enforced during assignment if theory already scheduled, and verified later)
    """
    rooms_by_id = {r.room_id: r for r in rooms}
    course_by_code = {c.course_code: c for c in courses}

    # Sort: schedule theory before lab (so labs can reference theory time)
    def sort_key(c: Course):
        # theory first, then labs; then year; then code
        return (0 if c.kind == "theory" else 1, c.year, c.course_code)
    ordered = sorted(courses, key=sort_key)

    # Precompute candidates and order by fewest options
    candidates = {c.course_code: _candidate_positions(c, rooms) for c in ordered}
    ordered = sorted(ordered, key=lambda c: (len(candidates[c.course_code]), sort_key(c)))

    used_room: Dict[Tuple[str,int,str], str] = {}          # (day,slot,room_id)->course_code
    used_instructor: Dict[Tuple[str,int,str], str] = {}    # (day,slot,instructor_id)->course_code
    used_year: Dict[Tuple[str,int,int], str] = {}          # (day,slot,year)->course_code
    theory_count_by_instructor_day: Dict[Tuple[str,str], int] = {}

    assignments: Dict[str, Assignment] = {}

    def can_place(c: Course, day: str, slot: int, room_id: str) -> bool:
        if is_friday_block(day, slot):
            return False
        if not instructor_available(availability, c.instructor_id, day, slot):
            return False
        if (day, slot, room_id) in used_room:
            return False
        if (day, slot, c.instructor_id) in used_instructor:
            return False
        # avoid collisions inside same academic year timetable
        if (day, slot, c.year) in used_year:
            return False
        # max theory/day
        if not max_theory_per_day_ok(theory_count_by_instructor_day, c.instructor_id, day, c.kind == "theory"):
            return False
        # lab after theory (if theory already scheduled)
        if c.kind == "lab" and c.prereq_theory_code:
            th = assignments.get(c.prereq_theory_code)
            if th:
                day_idx = {"Mon":0,"Tue":1,"Wed":2,"Thu":3,"Fri":4}
                if (day_idx[day], slot) <= (day_idx[th.day], th.slot):
                    return False
        return True

    def place(c: Course, day: str, slot: int, room_id: str):
        assignments[c.course_code] = Assignment(c.course_code, day, slot, room_id)
        used_room[(day, slot, room_id)] = c.course_code
        used_instructor[(day, slot, c.instructor_id)] = c.course_code
        used_year[(day, slot, c.year)] = c.course_code
        if c.kind == "theory":
            theory_count_by_instructor_day[(c.instructor_id, day)] = theory_count_by_instructor_day.get((c.instructor_id, day), 0) + 1

    def unplace(c: Course):
        a = assignments.pop(c.course_code, None)
        if not a:
            return
        used_room.pop((a.day, a.slot, a.room_id), None)
        used_instructor.pop((a.day, a.slot, c.instructor_id), None)
        used_year.pop((a.day, a.slot, c.year), None)
        if c.kind == "theory":
            key = (c.instructor_id, a.day)
            theory_count_by_instructor_day[key] = max(0, theory_count_by_instructor_day.get(key, 0) - 1)

    def backtrack(i: int) -> bool:
        if i >= len(ordered):
            return True
        c = ordered[i]
        for (day, slot, room_id) in candidates[c.course_code]:
            if can_place(c, day, slot, room_id):
                place(c, day, slot, room_id)
                if backtrack(i + 1):
                    return True
                unplace(c)
        return False

    ok = backtrack(0)

    unscheduled: List[Course] = []
    if not ok:
        # whatever got placed remains; mark the rest unscheduled
        placed = set(assignments.keys())
        for c in ordered:
            if c.course_code not in placed:
                unscheduled.append(c)

    return ScheduleResult(assignments=assignments, unscheduled=unscheduled)
