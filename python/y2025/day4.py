from python.helper_classes.common_methods import load_grid

directions = [(-1, 0), (-1, -1), (0, -1), (1, -1), (1, 0), (1, 1), (0, 1), (-1, 1)]

def parse_data():
    return load_grid(2025, 4)

def solve_part1(p_input):
    return sum(1 for y, row in enumerate(p_input) for x, char in enumerate(row) if char == '@' and less_than_4(p_input, x, y))

def solve_part2(p_input):
    summ = 0
    while True:
        temp_sum = 0
        for y, row in enumerate(p_input):
            for x, char in enumerate(row):
                if p_input[y][x] == '@' and less_than_4(p_input, x, y):
                    temp_sum += 1
                    p_input[y][x] = '.'
        if temp_sum > 0:
            summ += temp_sum
        else:
            break
    return summ

def less_than_4(p_input, x, y):
    return sum(1 for dx, dy in directions if 0 <= x + dx < len(p_input[0]) and 0 <= y + dy < len(p_input) and p_input[y + dy][x + dx] == '@') < 4