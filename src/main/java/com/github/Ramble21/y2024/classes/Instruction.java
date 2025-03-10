package com.github.Ramble21.y2024.classes;

public class Instruction {

    private final int opcode;
    private final int literalOperand;
    private final long comboOperand;
    private final InstructionReader reader;

    public Instruction(int instructionIndex, InstructionReader reader){
        this.reader = reader;
        this.opcode = reader.getInstruction(instructionIndex);
        this.literalOperand = reader.getInstruction(instructionIndex + 1);
        if (this.opcode == Integer.MAX_VALUE || this.literalOperand == Integer.MAX_VALUE) reader.halt();
        this.comboOperand = switch (literalOperand) {
            case 0,1,2,3,Integer.MAX_VALUE -> literalOperand;
            case 4 -> reader.getA();
            case 5 -> reader.getB();
            case 6 -> reader.getC();
            case 7 -> throw new RuntimeException("Tried to use reserved combo operand 7");
            default -> throw new RuntimeException("Invalid number- over 3 bits");
        };
    }

    public void adv(){
        reader.setA(reader.getA() / (long) Math.pow(2, comboOperand));
    }
    public void bxl(){
        reader.setB(reader.getB() ^ literalOperand);
    }
    public void bst(){
        reader.setB(comboOperand % 8);
    }
    public void jnz(){
        if (reader.getA() != 0) reader.setInstructionPointer(literalOperand);
    }
    public void bxc(){
        reader.setB(reader.getB() ^ reader.getC());
    }
    public void out(){
        reader.output((int) comboOperand % 8);
    }
    public void bdv(){
        reader.setB(reader.getA() / (long) Math.pow(2, comboOperand));
    }
    public void cdv(){
        reader.setC(reader.getA() / (long) Math.pow(2, comboOperand));
    }

    public void doInstruction(){
        switch (opcode) {
            case 0 -> adv();
            case 1 -> bxl();
            case 2 -> bst();
            case 3 -> jnz();
            case 4 -> bxc();
            case 5 -> out();
            case 6 -> bdv();
            case 7 -> cdv();
        }
    }
}
