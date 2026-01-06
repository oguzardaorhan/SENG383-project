"""
Self-test for BeePlan (no GUI required).

Run:
    python src/self_test.py

Exit code:
    0 = pass
    1 = fail
"""
from __future__ import annotations

import sys

from beeplan.io import load_sample_data
from beeplan.scheduler import generate_schedule
from beeplan.report import build_report


def main() -> int:
    courses, rooms, instructors, availability = load_sample_data()
    res = generate_schedule(courses, rooms, availability)
    violations = build_report(courses, res.assignments, res.unscheduled)

    # Print summary
    print(f"Assignments: {len(res.assignments)}")
    print(f"Unscheduled: {len(res.unscheduled)}")
    print(f"Violations:  {len(violations)}")

    if violations:
        for v in violations:
            print(f"- {v.code}: {v.message}")
        return 1

    # Basic sanity: every scheduled course must have an assignment
    scheduled_codes = set(res.assignments.keys())
    all_codes = {c.course_code for c in courses}
    if not all_codes.issubset(scheduled_codes.union({c.course_code for c in res.unscheduled})):
        print("FAIL: Some courses missing from both assignments and unscheduled lists.")
        return 1

    print("PASS: Core constraints satisfied on sample data.")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
