from python.helper_classes.common_methods import load_lines

def parse_data():
    lines_raw = load_lines(2025, 5)
    ranges, avail_ids = [], []
    i = 0
    while lines_raw[i]:
        s = lines_raw[i]
        ranges.append([int(s[:s.find("-")]), int(s[s.find("-") + 1:])])
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
    ranges, _ = p_input
    filtered_ranges = []
    for ran_uf in ranges:
        add_range = True
        for i in range(len(filtered_ranges) - 1, -1, -1):
            ran_f = filtered_ranges[i]
            if ran_f[0] <= ran_uf[0] and ran_f[1] >= ran_uf[1]:
                add_range = False
                break
            if ran_uf[0] <= ran_f[0] and ran_uf[1] >= ran_f[1]:
                filtered_ranges.remove(ran_f)
                continue
            if ran_f[0] <= ran_uf[0] <= ran_f[1]:
                ran_uf[0] = ran_f[1] + 1
            if ran_f[1] >= ran_uf[1] >= ran_f[0]:
                ran_uf[1] = ran_f[0] - 1
        if add_range:
            filtered_ranges.append(ran_uf)
    return sum(ran[1] - ran[0] + 1 for ran in filtered_ranges)


