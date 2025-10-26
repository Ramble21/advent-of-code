package com.github.ramble21.y2024.classes;

import com.github.ramble21.y2024.days.Day17;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class InstructionReader {

    private final int[] instructions;
    private int[] target;
    private final boolean hasTarget;

    private final long initialA;
    private long A;
    private long B;
    private long C;

    private boolean isHalted;
    private boolean isJumped;

    private int instructionPointer;

    private final ArrayList<Integer> output = new ArrayList<>();

    public InstructionReader(int[] instructions, long A, int B, int C){
        this.instructions = instructions;
        this.hasTarget = false;
        this.A = A;
        this.initialA = A;
        this.B = B;
        this.C = C;
        this.isHalted = false;
        this.isJumped = false;
        this.instructionPointer = 0;
    }

    public void read(){
        while (true){

            Instruction instruction = new Instruction(instructionPointer, this);
            if (isHalted) break;
            instruction.doInstruction();
            if (!isJumped) instructionPointer += 2;
            isJumped = false;
        }
        if (hasTarget) {
            isMatch();
        }
    }
    public void isMatch(){
        int[] target = reverseArray(this.target);
        ArrayList<Integer> reversed = new ArrayList<>(output);
        Collections.reverse(reversed);
        for (int i = 0; i < target.length; i++){
            if (target[i] != reversed.get(i)) return;
        }
    }

    public static int[] reverseArray(int[] array) {
        int[] reversed = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            reversed[i] = array[array.length - 1 - i];
        }
        return reversed;
    }

    @Override
    public String toString() {
        if (hasTarget) return "\n" + "Initial Register A: " + initialA + " (0o" + Day17.decimalToOctal(initialA) + ")" + "\nTarget: " + Arrays.toString(target) + "\nOutput: " + output + "\n";
        return "\n" + "Initial Register A: " + initialA + " (0o" + Day17.decimalToOctal(initialA) + ")" + "\nProgram: " + Arrays.toString(instructions) + "\nOutput: " + output + "\n";
    }

    public long getA() {
        return A;
    }

    public long getB() {
        return B;
    }

    public void setB(long b) {
        B = b;
    }

    public long getC() {
        return C;
    }

    public void setC(long c) {
        C = c;
    }

    public void halt() {
        isHalted = true;
    }

    public int getInstruction(int index) {
        return index >= instructions.length ? Integer.MAX_VALUE : instructions[index];
    }

    public void output(int output) {
        this.output.add(output);
        int[] outputAsArr = new int[this.output.size()];
        for (int i = 0; i < this.output.size(); i++){
            outputAsArr[i] = this.output.get(i);
        }
        if (hasTarget && Arrays.equals(outputAsArr, target)){
            halt();
        }
    }

    public ArrayList<Integer> getOutput(){
        return output;
    }

    public void setInstructionPointer(int instructionPointer) {
        this.instructionPointer = instructionPointer;
        isJumped = true;
    }

    public void setA(long A) {
        this.A = A;
    }
}
