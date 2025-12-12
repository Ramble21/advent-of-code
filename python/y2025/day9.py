import re
from functools import lru_cache

from shapely import Polygon, Point
from python.helper_classes.common_methods import load_lines
valid_xs, valid_ys = [], []
poly = Polygon()

def parse_data():
    strs = load_lines(2025, 9)
    coord_tuples = list((int(x), int(y)) for line in strs for x, y in [re.split(",", line)])
    return coord_tuples

def solve_part1(p_input):
    return max(abs((p_input[i][0] - p_input[j][0] + 1) * (p_input[i][1] - p_input[j][1] + 1)) for i in range(len(p_input)) for j in range(i + 1, len(p_input)))

def solve_part2(p_input):
    global valid_xs, valid_ys, poly
    valid_xs, valid_ys = sorted({point[0] for point in p_input}), sorted({point[1] for point in p_input})
    vertex_indices = [(valid_xs.index(x), valid_ys.index(y)) for x, y in p_input]
    poly = Polygon(vertex_indices)
    maxx = 0

    for i in range(len(vertex_indices)):
        print(i, "/", len(vertex_indices))
        for j in range(i + 1, len(vertex_indices)):
            v1, v2 = vertex_indices[i], vertex_indices[j]
            if all_red_green(v1, v2):
                vertex1 = (valid_xs[v1[0]], valid_ys[v1[1]])
                vertex2 = (valid_xs[v2[0]], valid_ys[v2[1]])
                area = (abs(vertex1[0] - vertex2[0]) + 1) * (abs(vertex1[1] - vertex2[1]) + 1)
                maxx = max(maxx, area)
    return maxx

@lru_cache
def all_red_green(vertex_idx1, vertex_idx2):
    if vertex_idx1[0] == vertex_idx2[0] or vertex_idx1[1] == vertex_idx2[1]:
        return False
    dx = 0.5 if vertex_idx1[0] < vertex_idx2[0] else -0.5
    dy = 0.5 if vertex_idx1[1] < vertex_idx2[1] else -0.5
    dirs = [(dx, 0), (0, dy), (-dx, 0), (0, -dy)]
    x_idx, y_idx = vertex_idx1[0], vertex_idx1[1]
    for d in dirs:
        while True:
            if not poly.covers(Point(x_idx, y_idx)):
                return False
            x_idx += d[0]
            y_idx += d[1]
            if d[0] != 0 and (x_idx == vertex_idx1[0] or x_idx == vertex_idx2[0]):
                break
            if d[1] != 0 and (y_idx == vertex_idx1[1] or y_idx == vertex_idx2[1]):
                break
    return True
