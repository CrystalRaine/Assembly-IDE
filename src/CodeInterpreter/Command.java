package CodeInterpreter;

import Exceptions.IncorrectArgumentsException;
import Exceptions.MemoryAddressNotDivisibleByEightException;
import Exceptions.MemoryAddressOutOfBoundsException;
import graphics.WindowFrame;
import processor.Memory;
import processor.Processor;

import java.util.ArrayList;

/**
 * @author Raine
 * created 9/16/2022
 */
public class Command {

    /**
     * BCOND args:  <br>
     * Equal to:                EQ <br>
     * Not Equal to:            NE <br>
     * Less Than:               LT    <br>
     * Less Than or Equal to:   LE   <br>
     * Greater Than:            GT <br>
     * Greater Than or Equal to:GE <br>
     * Minus:                    MI <br>
     * Plus:                     PL <br>
     */

    public enum BranchOptions {
        NONE, EQ,
        NE, LT,
        LE, GT,
        GE, MI,
        PL
    }


    public enum Instruction {
        NONE, ADDI,
        ADD, SUBI,
        SUB, LUDR,
        STUR, B,
        BR, CBZ,
        CBNZ, PRINT,
        DUMP, LSL,
        LSR, BL,
        HALT, BCOND,
        ORR, SUBS,
        SUBIS, ORRI,
        EOR, EORI,
        AND, ANDI
    }

    private Instruction instruction;
    private BranchOptions branch;
    private int line;
    private String lineText;

    private ArrayList<Integer> registers = new ArrayList<>();
    private ArrayList<Integer> constants = new ArrayList<>();

    public Command(String substring, int line){
        lineText = substring;
        instruction = Instruction.NONE;
        branch = BranchOptions.NONE;

        this.line = line;
        if(substring.length() > 2 && substring.strip().startsWith("B.")){
            instruction = Instruction.BCOND;
            for (BranchOptions bopt : BranchOptions.values()) {
                if(substring.contains(bopt.toString())){
                    branch = bopt;
                    break;
                }
            }
            return;
        }
        for (Instruction ins : Instruction.values()) {
            if(substring.equals(ins.toString())){
                instruction = ins;
                break;
            }
        }
    }

    public void run() throws IncorrectArgumentsException, MemoryAddressNotDivisibleByEightException,
            MemoryAddressOutOfBoundsException {
        switch (instruction){
            case NONE -> {} // do nothing
            case ADD -> {   // add with registers
                require(3,0);
                Processor.setRegisterValue(registers.get(0), Processor.getValueInRegister(registers.get(1)) +
                        Processor.getValueInRegister(registers.get(2)));
            }
            case ADDI -> {  // add immediate. adds a register with a constant
                require(2, 1);
                Processor.setRegisterValue(registers.get(0), Processor.getValueInRegister(registers.get(1)) +
                        constants.get(0));
            }
            case SUB -> {   // subtract with registers
                require(3,0);
                Processor.setRegisterValue(registers.get(0), Processor.getValueInRegister(registers.get(1)) -
                        Processor.getValueInRegister(registers.get(2)));
            }
            case SUBI -> {  // subtract immediate
                require(2, 1);
                Processor.setRegisterValue(registers.get(0), Processor.getValueInRegister(registers.get(1)) -
                        constants.get(0));
            }
            case B -> {     // branch constant
                require(0, 1);
                Processor.setCurrentLine(constants.get(0));
            }
            case BR -> {    // branch register
                require(1,0);
                Processor.setCurrentLine(Processor.getValueInRegister(registers.get(0)));
            }
            case BL -> {    // branch with Link
                require(0,1);
                Processor.setRegisterValue(30, line);
                Processor.setCurrentLine(constants.get(0));
            }
            case CBZ -> {   // conditional branch if zero
                require(1,1);
                if(Processor.getValueInRegister(registers.get(0)) == 0){
                    Processor.setCurrentLine(constants.get(0));
                }
            }
            case CBNZ -> {  // conditional branch if not zero
                require(1,1);
                if(Processor.getValueInRegister(registers.get(0)) != 0){
                    Processor.setCurrentLine(constants.get(0));
                }
            }
            case LSL -> {
                require(2, 1);
                Processor.setRegisterValue(registers.get(0), Processor.getValueInRegister(registers.get(1)) <<
                        constants.get(0));
            }
            case LSR -> {
                require(2,1);
                Processor.setRegisterValue(registers.get(0), Processor.getValueInRegister(registers.get(1)) >>
                        constants.get(0));
            }
            case LUDR -> {  // load from memory
                require(2, 1);
                Processor.setRegisterValue(registers.get(0), Memory.getFromPosition(
                        Processor.getValueInRegister(registers.get(1)) + constants.get(0)));
            }
            case STUR -> {  // store into memory
                require(2, 1);
                Memory.setMemoryAddress(Processor.getValueInRegister(registers.get(1)) + constants.get(0),
                        Processor.getValueInRegister(registers.get(0)));
            }
            case PRINT -> { // print to log
                require(1,0);
                WindowFrame.log(Processor.getValueInRegister(registers.get(0)));
                registers.remove(0);
            }
            case DUMP -> WindowFrame.log(Memory.dump());// dump memory content to log
            case HALT -> Processor.setCurrentLine(Processor.codeBody.getLineCount());
            case BCOND -> { // conditional
                if(branchCondition()){
                    require(0, 1);
                    Processor.setCurrentLine(constants.get(0));
                }
            }
            case ORR -> {   // bitwise or
                require(3,0);
                Processor.setRegisterValue(registers.get(0), registers.get(1) | registers.get(2));
            }
            case SUBS -> {   // subtract with registers, set flags
                require(3,0);
                int result = Processor.getValueInRegister(registers.get(1)) - Processor.getValueInRegister(registers.get(2));
                Processor.setRegisterValue(registers.get(0), result);

                if(result < 0) Memory.setFlags(0, true);
                else Memory.setFlags(0, false);

                if(result == 0) Memory.setFlags(1, true);
                else Memory.setFlags(1, false);

                if(false) Memory.setFlags(2, true);
                else Memory.setFlags(2, false);

                if(false) Memory.setFlags(3, true);
                else Memory.setFlags(3, false);

            }
            case SUBIS -> {   // subtract with registers, set flags
                require(2,1);
                int result = Processor.getValueInRegister(registers.get(1)) - constants.get(0);
                Processor.setRegisterValue(registers.get(0), result);

                if(result < 0) Memory.setFlags(0, true);
                else Memory.setFlags(0, false);

                if(result == 0) Memory.setFlags(1, true);
                else Memory.setFlags(1, false);

                if(false) Memory.setFlags(2, true);
                else Memory.setFlags(2, false);

                if(false) Memory.setFlags(3, true);
                else Memory.setFlags(3, false);

            }
            case ORRI -> {
                require(2,1);
                Processor.setRegisterValue(registers.get(0), registers.get(1) | constants.get(1));
            }
            case EOR -> {
                require(3,0);
                Processor.setRegisterValue(registers.get(0), registers.get(1) ^ registers.get(2));
            }
            case EORI -> {
                require(2,1);
                Processor.setRegisterValue(registers.get(0), registers.get(1) ^ constants.get(1));
            }
            case AND -> {
                require(3,0);
                Processor.setRegisterValue(registers.get(0), registers.get(1) & registers.get(2));
            }
            case ANDI -> {
                require(2,1);
                Processor.setRegisterValue(registers.get(0), registers.get(1) & constants.get(1));
            }
        }
        registers.clear();
        constants.clear();
    }

    public void addRegister(int reg){
        registers.add(reg);
    }

    public void addConstant(int constant){
        constants.add(constant);
    }

    public String getCommandName(){
        return instruction.toString();
    }

    private void require(int registers, int constants) throws IncorrectArgumentsException {
        if(this.registers.size() != registers){
            throw new IncorrectArgumentsException(line);
        }
        if(this.constants.size() != constants){
            throw new IncorrectArgumentsException(line);
        }
    }

    private boolean branchCondition(){
        boolean[] flags = Memory.getFlags();
        switch(branch){
            case EQ -> {
                if (flags[1])
                    return true;
            }
            case GE -> {
                if(!flags[0])
                    return true;
            }
            case GT -> {
                if(!flags[1] && !flags[0])
                    return true;
            }
            case LE -> {
                if(flags[0] || flags[1])
                    return true;
            }
            case LT -> {
                if(flags[0])
                    return true;
            }
            case NE -> {
                if(!flags[1])
                    return true;
            }
            case MI -> {
                if(flags[0])
                    return true;
            }
            case PL -> {
                if(!flags[0])
                    return true;
            }
        }
        return false;
    }
}
