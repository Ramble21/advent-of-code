import heapq
import math
import re
from collections import Counter

from python.helper_classes.common_methods import load_lines

def parse_data():
    strs = load_lines(2025, 8)
    coord_tuples = list((int(x), int(y), int(z)) for line in strs for x,y,z in [re.split(",", line)])
    return coord_tuples

def solve_part1(p_input):
    n = 1000
    circuit_distances = []
    for i in range(len(p_input)):
        for j in range(i + 1, len(p_input)):
            dx, dy, dz = (x1 := p_input[i][0]) - (x2 := p_input[j][0]), (y1 := p_input[i][1]) - (y2 := p_input[j][1]), (z1 := p_input[i][2]) - (z2 := p_input[j][2])
            dist = math.sqrt(dx ** 2 + dy ** 2 + dz ** 2)
            circuit_distances.append((x1, y1, z1, x2, y2, z2, dist))
    top_x = heapq.nsmallest(n, circuit_distances, key=lambda duo: duo[6])
    circuits, next_circuit_id = {}, 0
    for dist in top_x:
        box1, box2 = (dist[0], dist[1], dist[2]), (dist[3], dist[4], dist[5])
        if box1 not in circuits and box2 not in circuits:
            circuits[box1], circuits[box2] = next_circuit_id, next_circuit_id
            next_circuit_id += 1
        elif box1 in circuits and box2 not in circuits:
            circuits[box2] = circuits[box1]
        elif box1 not in circuits and box2 in circuits:
            circuits[box1] = circuits[box2]
        elif circuits[box1] != circuits[box2]:
            for k, v in circuits.items():
                if v == circuits[box2] and k != box2:
                    circuits[k] = circuits[box1]
            circuits[box2] = circuits[box1]
    largest_three = [length for _, length in Counter(circuits.values()).most_common(3)]
    return largest_three[0] * largest_three[1] * largest_three[2]

def solve_part2(p_input):
    pass