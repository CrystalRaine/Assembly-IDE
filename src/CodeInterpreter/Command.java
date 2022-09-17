package CodeInterpreter;

import Exceptions.IncorrectArgumentsException;
import Exceptions.MemoryAddressNotDivisibleByEightException;
import Exceptions.MemoryAddressOutOfBoundsException;
import graphics.WindowFrame;
import processor.Processor;

import java.util.ArrayList;

/**
 * @author Raine
 * created 9/16/2022
 * @project Assm-Info
 */
public class Command {

    public enum Instruction {
        NONE,
        ADDI,
        ADD,
        SUBI,
        SUB,
        LUDR,
        STUR,
        B,
        BR,
        CBZ,
        CBNZ,
        PRINT,
        DUMP
    }

    private Instruction instruction;
    private int line;

    private ArrayList<Integer> registers = new ArrayList<>();
    private ArrayList<Integer> constants = new ArrayList<>();

    public Command(String substring, int line){
        instruction = Instruction.NONE;
        this.line = line;
        for (Instruction ins : Instruction.values()) {
            if(substring.equals(ins.toString())){
                instruction = ins;
                break;
            }
        }
    }

    public void run() throws IncorrectArgumentsException, MemoryAddressNotDivisibleByEightException, MemoryAddressOutOfBoundsException {
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
            case LUDR -> {} // load from memory
            case STUR -> {} // store into memory
            case PRINT -> { // print to log
                require(1,0);
                WindowFrame.log(Processor.getValueInRegister(registers.get(0)));
                registers.remove(0);
            }
            case DUMP -> {} // dump memory content to log
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

    private void require(int registers, int constants) throws IncorrectArgumentsException {
        if(this.registers.size() != registers){
            throw new IncorrectArgumentsException(line);
        }
        if(this.constants.size() != constants){
            throw new IncorrectArgumentsException(line);
        }
    }
}
