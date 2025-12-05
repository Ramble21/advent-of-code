from python.helper_classes.common_methods import load_lines

def parse_data():
    lines_raw = load_lines(2025, 5)
    ranges, avail_ids = [], []
    i = 0
    while lines_raw[i]:
        s = lines_raw[i]
        ranges.append((int(s[:s.find("-")]), int(s[s.find("-") + 1:])))
        i += 1
    for j in range(i + 1, len(lines_raw)):
        avail_ids.append(int(lines_raw[j]))
    return ranges, avail_ids

def solve_part1(p_input):
    ranges, avail_ids = p_input
    num_fresh = 0
    for av_id in avail_ids:
        for ran in ranges:
            if ran[0] <= av_id <= ran[1]:
                num_fresh += 1
                break
    return num_fresh

def solve_part2(p_input):
    pass