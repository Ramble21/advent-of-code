from python.helper_classes.common_methods import load_lines

def parse_data():
    return load_lines(2025, 3)

def solve_part1(p_input):
    return sum(get_joltage(bank, 0, 2) for bank in p_input)

def solve_part2(p_input):
    return sum(get_joltage(bank, 0, 12) for bank in p_input)

def get_joltage(bank, min_idx, digits_remaining):
    best_num, best_num_idx = '0', 0
    for i in range(min_idx, len(bank)):
        num = bank[i]
        if num > best_num:
            best_num = num
            best_num_idx = i
            if num == '9':
                break
        if i + digits_remaining == len(bank):
            break
    if digits_remaining == 1:
        return int(best_num)
    return int(best_num) * pow(10, digits_remaining - 1) + get_joltage(bank, best_num_idx + 1, digits_remaining - 1)

