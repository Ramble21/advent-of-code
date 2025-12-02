from python.helper_classes.common_methods import load_lines

def parse_data():
    return load_lines(2025, 2, ",")

def solve_part1(p_input):
    summ = 0
    for s in p_input:
        hyphen_idx = s.index("-")
        num_1 = int(s[:hyphen_idx])
        num_2 = int(s[hyphen_idx + 1:])
        for i in range(num_1, num_2 + 1):
            if has_repeat(i):
                summ += i
    return summ

def solve_part2(p_input):
    pass

def has_repeat(num):
    s_num = str(num)
    len_num = len(s_num)
    if len_num % 2 == 1:
        return False
    len_num_div = len_num // 2
    return s_num[:len_num_div] == s_num[len_num_div:]
