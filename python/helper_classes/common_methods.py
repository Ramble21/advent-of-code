from pathlib import Path
import time
import importlib

def run_single_day(year, day):
    mod = importlib.import_module(get_mod_name(year, day))
    return solve(year, day, mod.solve_part1, mod.solve_part2)

def module_exists(name):
    try:
        importlib.import_module(name)
        return True
    except ModuleNotFoundError:
        return False

def get_mod_name(year, day):
    return f"python.y{year}.day{day}"

def run_all_days(year):
    day = 1
    mod_name = get_mod_name(year, day)
    total_time = 0
    while module_exists(mod_name):
        total_time += run_single_day(year, day)
        print()
        day += 1
        mod_name = get_mod_name(year, day)
    print(f"Advent of Code {year} total: {total_time:.0f} ms")

def load_lines(year, day):
    base = Path(__file__).resolve().parent.parent
    path = base / "inputs" / str(year) / f"day{day}.txt"

    with open(path, "r", encoding="utf-8") as f:
        return [line.rstrip("\n") for line in f]

def solve(year, day, part1, part2):
    start = time.perf_counter()
    parsed_data = load_lines(year, day)
    pd_ts = time.perf_counter()
    print(f"Day {day} data parsing: {((pd_ts - start) * 1000):.0f} ms")
    part1_answer = part1(parsed_data)
    mid = time.perf_counter()
    print(f"Part 1: {part1_answer} | {((mid - pd_ts) * 1000):.0f} ms")
    part2_answer = part2(parsed_data)
    end = time.perf_counter()
    print(f"Part 2: {part2_answer} | {((end - mid) * 1000):.0f} ms")
    return (end - start) * 1000