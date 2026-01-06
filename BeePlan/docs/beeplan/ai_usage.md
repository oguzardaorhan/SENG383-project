# AI Usage & Prompts — BeePlan (SENG 383)

This log summarizes how AI tools were used during the BeePlan implementation. The goal is to document **Prompt → Output → Revision** for the main project phases (design, coding, testing, documentation).

> Repository structure:
> - Source code: `src/`
> - Design artifacts & logs: `docs/`
> - Report: `Report/`
> - Demo video placeholder: `video/`

---

## Summary Table (Prompt → Output → Revision)

| Phase | Goal | Prompt (short) | AI Output (short) | What we revised / verified |
|------|------|-----------------|-------------------|----------------------------|
| Design | Align artifacts with assignment & UI flow | “Summarize the required BeePlan workflow and propose UI screens.” | Suggested a 3-step UI: load data → generate schedule → view violations. | Matched screens to the activity diagram and ensured all required buttons exist. |
| Coding | Implement data loading | “Create robust CSV loaders for courses/rooms/instructors/availability.” | Proposed dataclasses + pandas CSV parsing with validation. | Added file-existence checks and explicit error messages for missing files. |
| Coding | Scheduling core | “Implement a backtracking scheduler with constraints and simple heuristics.” | Provided backtracking + most-constrained-first ordering. | Verified each constraint against the assignment; added a final constraint check step. |
| Testing | Automated self-test | “Write a self_test script that runs on sample data and prints PASS/FAIL.” | Suggested running generate + constraint verification with a clear summary. | Ensured deterministic sample data and a clean PASS message used for grading. |
| Debugging | UI visibility bug | “Treeview cells are empty / text not visible on Windows; fix it.” | Suggested single-line labels and safer rendering. | Changed timetable cell labels to single-line and added a summary panel for guaranteed visibility. |
| Documentation | README + report structure | “Write a minimal README quickstart and file layout that matches submission requirements.” | Drafted README + folder checklist. | Verified repo has `/src /docs /video /Report` and updated paths to real files. |

---

## Key Prompts (longer form)

### 1) Scheduling algorithm
- **Prompt:** “Implement a backtracking scheduler for a weekly timetable with constraints: no room overlap, no instructor overlap, instructor availability, blocked Friday slot, max theory/day per instructor, lab-after-theory.”
- **Output:** Backtracking with candidate generation and course ordering (fewest options first).
- **Revision:** Added a year-overlap guard and a separate verification step that produces a violation report.

### 2) Validation report
- **Prompt:** “Generate a human-readable validation report listing constraint violations and unscheduled courses.”
- **Output:** Violation objects with course code + message.
- **Revision:** Added export to JSON and ensured the GUI report view shows all violations.

### 3) Repo packaging
- **Prompt:** “Prepare a clean repo layout and include design assets under docs.”
- **Output:** Proposed directories and a minimal report template.
- **Revision:** Ensured all referenced paths exist and match the final repo content.

