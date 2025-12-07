import functools

from python.helper_classes.common_methods import load_grid

def parse_data():
    return load_grid(2025, 7)

def solve_part1(p_input):
    beams, r, num_splits = ["".join(p_input[0]).find("S")], 0, 0
    while r + 1 < len(p_input):
        new_beams = []
        for beam in beams:
            if p_input[r][beam] == '^':
                new_beams.append(beam-1)
                new_beams.append(beam+1)
                num_splits += 1
            else:
                new_beams.append(beam)
        beams = set(new_beams)
        r += 1
    return num_splits

def solve_part2(p_input):
    @functools.cache
    def num_tls(r, c):
        while r + 1 < len(p_input) and p_input[r][c] != '^':
            r += 1
        if r + 1 == len(p_input):
            return 1
        return num_tls(r, c - 1) + num_tls(r, c + 1)
    return num_tls(0, "".join(p_input[0]).find("S"))