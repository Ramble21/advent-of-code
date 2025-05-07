package com.github.Ramble21.y2023.days;

import com.github.Ramble21.DaySolver;
import com.github.Ramble21.y2023.classes.Brick;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Day22 extends DaySolver {
    private final Brick[] bricks;
    public Day22() throws IOException {
        List<String> input = getInputLines(2023, 22);
        Brick[] bricks = new Brick[input.size()];
        for (int i = 0; i < bricks.length; i++) {
            bricks[i] = new Brick(input.get(i));
        }
        Arrays.sort(bricks, Comparator.comparingInt(Brick::getMinZ));
        this.bricks = bricks;
    }
    public long solvePart1() throws IOException {
        for (Brick b : bricks) {
            b.fall(bricks);
        }
        return Arrays.stream(bricks).filter(b -> b.canBeDisintegrated(bricks)).toArray().length;
    }

    public long solvePart2() throws IOException {
        return Arrays.stream(bricks).mapToInt(b -> b.disintegrationChainReactionLength(bricks)).sum();
    }
}