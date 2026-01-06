# Verification & Validation (V&V) — BeePlan

This document records the verification (automated checks) and validation (manual GUI checks) performed for BeePlan.

---

## 1) Automated Verification (Self Test)

Run:

```bash
python src/self_test.py
```

**Expected output (PASS on sample data):**
- Assignments: > 0
- Unscheduled: 0
- Violations: 0
- PASS: Core constraints satisfied

---

## 2) Manual Validation (GUI)

Run:

```bash
python src/beeplan_app.py
```

### Test Cases

| ID | Scenario | Steps | Expected Result |
|----|----------|-------|-----------------|
| TC-01 | Launch with sample data | Start app (no folder selected) | Status shows sample data loaded |
| TC-02 | Generate schedule | Click **Generate Schedule** | Status shows “Assignments: … | Violations: …” and timetable/summary updates |
| TC-03 | View report | Click **View Report** | Report window opens; violations list matches status |
| TC-04 | Export report JSON | Click **Export Report JSON** → save | JSON file created with violations + metadata |
| TC-05 | Export schedule CSV | Click **Export Schedule CSV** → save | CSV file created with rows (year/day/slot/course/room/instructor) |
| TC-06 | Load invalid data folder | Click **Load Data Folder** and choose a folder missing required CSVs | Error dialog shows missing-file message |

---

## 3) AI Tutor — Bug / Issue Resolution Evidence

The course requires evidence of resolving issues with an AI tutor. Place screenshots here:

- `docs/beeplan/vv_screenshots/bug1.png`
- `docs/beeplan/vv_screenshots/bug2.png`

### Bug #1 — Timetable cells not visible on some Windows setups
- **Symptom:** After generating, the timetable grid showed time slots but course cells appeared empty.
- **Cause:** Multi-line / long labels in `ttk.Treeview` can fail to render reliably depending on DPI/scaling.
- **Fix:** Render single-line labels and add a separate “Schedule Summary” panel that always prints the generated assignments.
- **Evidence:** Screenshot before (empty timetable) + after (summary filled and timetable showing).

### Bug #2 — Demo/Grading visibility safeguard
- **Symptom:** Even when data is generated, graders may not notice entries if the grid is clipped or scaled.
- **Fix:** Added a dedicated summary box listing all assignments sorted by (year, day, slot).
- **Evidence:** Screenshot of the summary panel after generation.

> Note: These are *presentation-critical* issues for grading/demos; they were treated as high priority even if the core scheduler logic was correct.

---

## 4) Peer Review Findings (optional)

If a partner reviewed the code, add 1–5 bullets here (e.g., naming improvements, error-handling, UI tweaks).
