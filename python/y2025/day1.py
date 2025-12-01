from python.helper_classes.file_loader import load_lines

def solve_part1():
    instructions = load_lines(2025, 1)
    num = 50
    password = 0
    for instruction in instructions:
        if instruction[0] == 'R':
            num += int(instruction[1:])
            num %= 100
        else:
            num -= int(instruction[1:])
            while num < 0:
                num += 100
        if num == 0:
            password += 1
    return password

def solve_part2():
    instructions = load_lines(2025, 1)
    num = 50
    password = 0
    for instruction in instructions:
        original_num = num
        move_dist = int(instruction[1:])
        real_move_dist = move_dist % 100
        password += move_dist // 100
        if real_move_dist > 0:
            if instruction[0] == 'R':
                num += real_move_dist
                if num >= 100:
                    num -= 100
                    password += 1
            else:
                num -= real_move_dist
                if num < 0:
                    num += 100
                    if original_num != 0:
                        password += 1
                if num == 0:
                    password += 1
    return password