from __future__ import annotations

from dataclasses import dataclass
from typing import Dict, List, Optional, Tuple
from .io import Course
from .constraints import Assignment, is_friday_block

@dataclass
class Violation:
    code: str
    message: str

def build_report(courses: List[Course], assignments: Dict[str, Assignment], unscheduled: List[Course]) -> List[Violation]:
    violations: List[Violation] = []

    for c in unscheduled:
        violations.append(Violation("UNSCHEDULED", f"{c.course_code} ({c.course_name}) could not be scheduled."))

    # Friday blocked
    for code, a in assignments.items():
        if is_friday_block(a.day, a.slot):
            violations.append(Violation("FRIDAY_BLOCK", f"{code} scheduled in blocked slot (Fri 13:20-15:10)."))

    # Lab after theory
    course_by_code = {c.course_code: c for c in courses}
    for c in courses:
        if c.kind == "lab" and c.prereq_theory_code:
            lab_a = assignments.get(c.course_code)
            th_a = assignments.get(c.prereq_theory_code)
            if lab_a and th_a:
                # Compare (day index, slot)
                day_idx = {"Mon":0,"Tue":1,"Wed":2,"Thu":3,"Fri":4}
                if (day_idx[lab_a.day], lab_a.slot) <= (day_idx[th_a.day], th_a.slot):
                    violations.append(Violation("LAB_ORDER",
                        f"{c.course_code} lab is not after theory {c.prereq_theory_code}."))

    return violations
