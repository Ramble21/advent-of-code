from python.helper_classes.common_methods import load_lines

def parse_data():
    return load_lines(2025, 3)

def solve_part1(p_input):
    summ = 0
    for bank in p_input:
        first_num, second_num = '0', '0'
        first_num_idx = 0
        for i, num in enumerate(bank):
            if num > first_num:
                first_num_idx = i
                first_num = num
            if num == '9' or i+2 == len(bank):
                    break
        for j in range(first_num_idx+1, len(bank)):
            num = bank[j]
            if num > second_num:
                second_num = num
                if num == '9':
                    break
        summ += (10 * int(first_num) + int(second_num))
    return summ

def solve_part2(p_input):
    pass