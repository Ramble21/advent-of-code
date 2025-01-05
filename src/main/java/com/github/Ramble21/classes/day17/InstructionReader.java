package com.github.Ramble21.classes.day17;

import java.util.ArrayList;
import java.util.Arrays;

public class InstructionReader {

    private final int[] instructions;
    private long A;
    private long B;
    private long C;

    private boolean isHalted;
    private boolean isJumped;

    private int instructionPointer;
    private final ArrayList<Long> output = new ArrayList<>();

    public InstructionReader(int[] instructions, int A, int B, int C){
        this.instructions = instructions;
        this.A = A;
        this.B = B;
        this.C = C;
        this.isHalted = false;
        this.isJumped = false;
        this.instructionPointer = 0;
        read();
    }

    public void read(){
        while (true){
            System.out.println(this);
            System.out.println(getInstruction(instructionPointer) + "," + getInstruction(instructionPointer+1));
            Instruction instruction = new Instruction(instructionPointer, this);
            if (isHalted) break;
            instruction.doInstruction();
            System.out.println(isJumped);
            if (!isJumped) instructionPointer += 2;
            isJumped = false;
        }
    }

    @Override
    public String toString() {
        return "------\n" + "Register A: " + A + "\nRegister B: " + B + "\nRegister C: " + C + "\nProgram: " + Arrays.toString(instructions) + "\nOutput: " + output + "\n------";
    }

    public long getA() {
        return A;
    }

    public void setA(long a) {
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

    public void output(long output) {
        this.output.add(output);
    }
    public ArrayList<Long> getOutput(){
        return output;
    }
    public void setInstructionPointer(int instructionPointer) {
        this.instructionPointer = instructionPointer;
        isJumped = true;
    }
}
