package com.github.Ramble21.classes.day17;

import com.github.Ramble21.Day17;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class InstructionReader {

    private final int[] instructions;
    private int[] target;
    private final boolean hasTarget;
    private final boolean print;

    private final long initialA;
    private long A;
    private long B;
    private long C;

    private boolean isHalted;
    private boolean isJumped;
    private boolean haltedEarly;

    private int instructionPointer;

    private final ArrayList<Integer> output = new ArrayList<>();
    private final ArrayList<Long> previousAs = new ArrayList<>();

    public InstructionReader(int[] instructions, long A, int B, int C, boolean print){
        this.instructions = instructions;
        this.hasTarget = false;
        this.print = print;
        this.A = A;
        this.initialA = A;
        this.B = B;
        this.C = C;
        this.isHalted = false;
        this.haltedEarly = false;
        this.isJumped = false;
        this.instructionPointer = 0;
    }
    public InstructionReader(int[] target, int[] instructions, long A, int B, int C, boolean print){
        this.instructions = instructions;
        this.hasTarget = true;
        this.print = print;
        this.A = A;
        this.initialA = A;
        this.B = B;
        this.C = C;
        this.isHalted = false;
        this.haltedEarly = false;
        this.isJumped = false;
        this.instructionPointer = 0;
        this.target = target;
    }

    public boolean read(){
        while (true){

            Instruction instruction = new Instruction(instructionPointer, this);
            if (isHalted) break;
            instruction.doInstruction();
            if (!isJumped) instructionPointer += 2;
            isJumped = false;
        }
        if (print) System.out.println(initialA + " -> " + output + " with " + previousAs);
        return (hasTarget && isMatch());
    }
    public boolean isMatch(){
        int[] target = reverseArray(this.target);
        ArrayList<Integer> reversed = new ArrayList<>(output);
        Collections.reverse(reversed);
        for (int i = 0; i < target.length; i++){
            if (target[i] != reversed.get(i)) return false;
        }
        return true;
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

    public void setA(long a) {
        addPreviousA(A);
        A = a;
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
            haltedEarly = true;
        }
    }

    public ArrayList<Integer> getOutput(){
        return output;
    }
    public void addPreviousA(long previousA){
        this.previousAs.add(previousA);
    }
    public ArrayList<Long> getPreviousAs() {
        return previousAs;
    }

    public void setInstructionPointer(int instructionPointer) {
        this.instructionPointer = instructionPointer;
        isJumped = true;
    }

    public boolean wasHaltedEarly() {
        return haltedEarly;
    }
}
