from python.helper_classes.common_methods import load_grid

def parse_data():
    return load_grid(2025, 7)

def solve_part1(p_input):
    start_loc = "".join(p_input[0]).find("S")
    beams = [start_loc]
    r = 0
    num_splits = 0
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
    pass
