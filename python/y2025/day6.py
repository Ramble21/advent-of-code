from python.helper_classes.common_methods import load_grid

def parse_data():
    grid = load_grid(2025, 6)
    max_len = max(len(row) for row in grid)
    for s in grid:
        while len(s) < max_len + 1:
            s.append(" ")
    return grid

def solve_part1(p_input):
    summ = 0
    last_space = -1
    for c in range(len(p_input[0])):
        all_spaces = True
        for r in range(len(p_input)):
            if p_input[r][c] != ' ':
                all_spaces = False
                break
        if all_spaces:
            summ += solve_math(p_input, last_space + 1, c - 1)
            last_space = c
    return summ

def solve_part2(p_input):
    summ = 0
    last_space = -1
    for c in range(len(p_input[0])):
        all_spaces = True
        for r in range(len(p_input)):
            if p_input[r][c] != ' ':
                all_spaces = False
                break
        if all_spaces:
            summ += solve_math_2(p_input, last_space + 1, c - 1)
            last_space = c
    return summ

def solve_math(p_input, first_col, last_col):
    is_mult = "*" in p_input[-1][first_col:last_col+1]
    if is_mult:
        prod = 1
        for i in range(len(p_input) - 1):
            lc_num = p_input[i][first_col:last_col+1]
            num = int("".join(lc_num).strip())
            prod *= num
        return prod
    summ = 0
    for i in range(len(p_input) - 1):
        lc_num = p_input[i][first_col:last_col + 1]
        num = int("".join(lc_num).strip())
        summ += num
    return summ

def solve_math_2(p_input, first_col, last_col):
    is_mult = "*" in p_input[-1][first_col:last_col + 1]
    if is_mult:
        prod = 1
        for i in range(last_col, first_col - 1, -1):
            lc_num = []
            for j in range(len(p_input) - 1):
                lc_num.append(p_input[j][i])
            num = int("".join(lc_num).strip())
            prod *= num
        return prod
    summ = 0
    for i in range(last_col, first_col - 1, -1):
        lc_num = []
        for j in range(len(p_input) - 1):
            lc_num.append(p_input[j][i])
        num = int("".join(lc_num).strip())
        summ += num
    return summ