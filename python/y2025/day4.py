from python.helper_classes.common_methods import load_lines

directions = [(-1, 0), (-1, -1), (0, -1), (1, -1), (1, 0), (1, 1), (0, 1), (-1, 1)]

def parse_data():
    return load_lines(2025, 4)

def solve_part1(p_input):
    summ = 0
    for y, row in enumerate(p_input):
        for x, char in enumerate(row):
            if p_input[y][x] == '@':
                summ += 1 if less_than_4(p_input, x, y) else 0
    return summ

def solve_part2(p_input):
    pass

def less_than_4(p_input, x, y):
    summ = 0
    for dx, dy in directions:
        if 0 <= x + dx < len(p_input[0]) and 0 <= y + dy < len(p_input):
            char = p_input[y + dy][x + dx]
            if char == '@':
                summ += 1
    return summ < 4