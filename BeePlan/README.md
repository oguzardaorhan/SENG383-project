# BeePlan (SENG 383) — Minimal Working Submission

This repository contains a **Python GUI** application (Tkinter) that:
- loads curriculum/resources from CSV,
- generates a weekly timetable,
- shows a validation report,
- exports **report JSON** and **schedule CSV/JSON**.

Repository layout (required):
- `/src` (source code)
- `/docs` (design artifacts + AI usage + V&V)
- `/video` (final demo video)
- `/Report` (final report PDF)

---

## Quick start (Windows)

1) Install **Python 3.10+** (recommended 3.11)

2) Open **PowerShell** in the repo root and run:

```powershell
python -m venv .venv
.\.venv\Scripts\activate
pip install -r requirements.txt
python src\self_test.py
python src\beeplan_app.py
```

---

## Data input

Use the **Load Data Folder** button and select a folder containing these files:
- `courses.csv`
- `rooms.csv`
- `instructors.csv`
- `availability.csv`

If you don't load data, the app uses the sample CSVs in `src/sample_data/`.

---

## Outputs (GUI)

- **Generate Schedule**: fills the timetable tabs (Year 1–4).
- **View Report**: shows validation issues (if any).
- **Export Report JSON**: saves `violations` as JSON.
- **Export Schedule CSV/JSON**: saves the generated schedule.

---

## Docs

- Design assets (latest): `docs/beeplan/design/assets/`
- AI usage log: `docs/beeplan/ai_usage.md`
- V&V test log: `docs/beeplan/vv_tests.md`
- Bug-fix screenshots folder: `docs/beeplan/vv_screenshots/`

---

## Report & Video

- Put the final report PDF in: `Report/final_report.pdf`
- Put the final demo video in: `video/beeplan_demo.mp4`
