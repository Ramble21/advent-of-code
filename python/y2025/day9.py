import math
import re
from python.helper_classes.common_methods import load_lines

def parse_data():
    strs = load_lines(2025, 9)
    coord_tuples = list((int(x), int(y)) for line in strs for x, y in [re.split(",", line)])
    return coord_tuples

def solve_part1(p_input):
    return max(abs((p_input[i][0] - p_input[j][0] + 1) * (p_input[i][1] - p_input[j][1] + 1)) for i in range(len(p_input)) for j in range(i + 1, len(p_input)))

def solve_part2(p_input):
    pass