package com.github.Ramble21;

import java.io.IOException;
import java.util.List;
import com.github.Ramble21.classes.general.*;

public class Day22 extends DaySolver{
    private final List<String> input;
    public Day22() throws IOException {
        input = getInputLines(22);
    }
    public long solvePart1() throws IOException {
        long sum = 0;
        for (String s : input){
            long init = Long.parseLong(s);
            long next = nextXSecretNos(init, 2000);
            sum += next;
        }
        return sum;
    }

    public long solvePart2() throws IOException {
        return 0;
    }

    public long nextXSecretNos(long initial, int x){
        long current = initial;
        for (int i = 0; i < x; i++){
            current = nextSecretNo(current);
        }
        return current;
    }
    public long nextSecretNo(long secretNo){
        long next = secretNo;
        next = mix(next, next * 64);
        next = prune(next);
        next = mix(next, next / 32);
        next = prune(next);
        next = mix(next, next * 2048);
        next = prune(next);
        return next;
    }
    public long mix(long secretNo, long result){
        return secretNo ^ result;
    }
    public long prune(long secretNo){
        return secretNo % 16777216L;
    }
}