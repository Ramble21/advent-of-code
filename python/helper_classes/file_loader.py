from pathlib import Path

def load_lines(year, day):
    base = Path(__file__).resolve().parent.parent
    path = base / "inputs" / str(year) / f"day{day}.txt"

    with open(path, "r", encoding="utf-8") as f:
        return [line.rstrip("\n") for line in f]