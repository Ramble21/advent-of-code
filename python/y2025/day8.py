import heapq
import math
import re
from collections import Counter

from python.helper_classes.common_methods import load_lines

def parse_data():
    strs = load_lines(2025, 8)
    coord_tuples = list((int(x), int(y), int(z)) for line in strs for x,y,z in [re.split(",", line)])
    circuit_distances = []
    for i in range(len(coord_tuples)):
        for j in range(i + 1, len(coord_tuples)):
            dx, dy, dz = (x1 := coord_tuples[i][0]) - (x2 := coord_tuples[j][0]), (y1 := coord_tuples[i][1]) - (y2 := coord_tuples[j][1]), (
                z1 := coord_tuples[i][2]) - (z2 := coord_tuples[j][2])
            dist = math.sqrt(dx ** 2 + dy ** 2 + dz ** 2)
            circuit_distances.append((x1, y1, z1, x2, y2, z2, dist))
    return sorted(circuit_distances, key=lambda duo: duo[6])

def solve_part1(p_input):
    n = 1000
    circuits, _, _ = add_circuits(p_input[:n], 0)
    largest_three = [length for _, length in Counter(circuits.values()).most_common(3)]
    return largest_three[0] * largest_three[1] * largest_three[2]

def solve_part2(p_input):
    circuits, next_circuit_id, last_two = add_circuits(p_input, 0)
    while len(Counter(circuits.values())) > 1:
        circuits, next_circuit_id, last_two = add_circuits(p_input, next_circuit_id)
    return last_two[0][0] * last_two[1][0]

def add_circuits(top_x, next_circuit_id):
    circuits = {}
    last_two = None
    for dist in top_x:
        box1, box2 = (dist[0], dist[1], dist[2]), (dist[3], dist[4], dist[5])
        l2 = (box1, box2)
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
        else:
            l2 = None
        if l2 is not None:
            last_two = l2
    return circuits, next_circuit_id, last_two