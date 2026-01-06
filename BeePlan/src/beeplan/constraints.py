from __future__ import annotations

from dataclasses import dataclass
from typing import Dict, List, Tuple, Optional
from .io import Course, Room

# Default time model: 4 slots/day.
DAYS = ["Mon", "Tue", "Wed", "Thu", "Fri"]
SLOTS = [
    "09:30-11:20",
    "11:30-13:20",
    "13:20-15:10",
    "15:20-17:10",
]

# Blocked slot: Friday 13:20-15:10 (day="Fri", slot=2)
FRIDAY_BLOCK = ("Fri", 2)

@dataclass
class Assignment:
    course_code: str
    day: str
    slot: int
    room_id: str

def is_friday_block(day: str, slot: int) -> bool:
    return (day, slot) == FRIDAY_BLOCK

def room_ok(course: Course, room: Room) -> bool:
    if room.room_type != course.room_type:
        return False
    if course.kind == "lab":
        # lab capacity constraint (<=40) is a requirement in the spec,
        # but here we interpret as lab rooms must have capacity >= required and required default is 40.
        if room.capacity < max(1, course.capacity_required):
            return False
    return True

def instructor_available(availability: Dict[Tuple[str,str,int], bool], instructor_id: str, day: str, slot: int) -> bool:
    return availability.get((instructor_id, day, slot), True)

def max_theory_per_day_ok(theory_count_by_instructor_day: Dict[Tuple[str,str], int],
                          instructor_id: str, day: str, adding_is_theory: bool) -> bool:
    # Spec says: max 4 hours theory per day.
    # With our 2-hour-ish slot model, that means max 2 theory slots/day.
    if not adding_is_theory:
        return True
    cur = theory_count_by_instructor_day.get((instructor_id, day), 0)
    return (cur + 1) <= 2
