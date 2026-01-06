from __future__ import annotations

from dataclasses import dataclass
from pathlib import Path
from typing import Dict, List, Optional, Tuple
import pandas as pd

@dataclass(frozen=True)
class Course:
    course_code: str
    course_name: str
    year: int
    kind: str                 # "theory" or "lab"
    instructor_id: str
    room_type: str            # "classroom" or "lab"
    capacity_required: int    # for lab (0 for theory)
    prereq_theory_code: str   # for lab, the theory course code

@dataclass(frozen=True)
class Room:
    room_id: str
    room_name: str
    room_type: str
    capacity: int

@dataclass(frozen=True)
class Instructor:
    instructor_id: str
    instructor_name: str

def _read_csv(path: Path) -> pd.DataFrame:
    if not path.exists():
        raise FileNotFoundError(f"Missing file: {path}")
    return pd.read_csv(path)

def load_data_folder(folder: str) -> Tuple[List[Course], List[Room], List[Instructor], Dict[Tuple[str,str,int], bool]]:
    """
    Loads:
      - courses.csv
      - rooms.csv
      - instructors.csv
      - availability.csv  (instructor_id, day, slot, available 0/1)
    """
    p = Path(folder)
    courses_df = _read_csv(p / "courses.csv")
    rooms_df = _read_csv(p / "rooms.csv")
    inst_df = _read_csv(p / "instructors.csv")
    avail_df = _read_csv(p / "availability.csv")

    courses: List[Course] = []
    for _, r in courses_df.fillna("").iterrows():
        courses.append(
            Course(
                course_code=str(r["course_code"]).strip(),
                course_name=str(r["course_name"]).strip(),
                year=int(r["year"]),
                kind=str(r["kind"]).strip().lower(),
                instructor_id=str(r["instructor_id"]).strip(),
                room_type=str(r["room_type"]).strip().lower(),
                capacity_required=int(r.get("capacity_required", 0) or 0),
                prereq_theory_code=str(r.get("prereq_theory_code", "")).strip(),
            )
        )

    rooms: List[Room] = []
    for _, r in rooms_df.fillna("").iterrows():
        rooms.append(
            Room(
                room_id=str(r["room_id"]).strip(),
                room_name=str(r["room_name"]).strip(),
                room_type=str(r["room_type"]).strip().lower(),
                capacity=int(r["capacity"]),
            )
        )

    instructors: List[Instructor] = []
    for _, r in inst_df.fillna("").iterrows():
        instructors.append(
            Instructor(
                instructor_id=str(r["instructor_id"]).strip(),
                instructor_name=str(r["instructor_name"]).strip(),
            )
        )

    availability: Dict[Tuple[str,str,int], bool] = {}
    for _, r in avail_df.fillna("").iterrows():
        iid = str(r["instructor_id"]).strip()
        day = str(r["day"]).strip()
        slot = int(r["slot"])
        available = int(r["available"]) == 1
        availability[(iid, day, slot)] = available

    return courses, rooms, instructors, availability

def load_sample_data() -> Tuple[List[Course], List[Room], List[Instructor], Dict[Tuple[str,str,int], bool]]:
    here = Path(__file__).resolve().parent.parent
    sample = here / "sample_data"
    return load_data_folder(str(sample))
