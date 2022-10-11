package processor;

import CodeInterpreter.CodeBody;
import CodeInterpreter.Command;
import CodeInterpreter.Line;
import Exceptions.*;
import controller.InfiniteLoopProtectionThread;
import graphics.RegisterWindow;
import graphics.WindowFrame;

/**
 * @author Raine
 * created 9/16/2022
 */
public class Processor {

    private static Register[] registers = new Register[32];
    private static Memory memory = new Memory();
    private static int currentLine = 0;
    private static boolean exceptionEncountered = false;
    public static CodeBody codeBody;
    public static boolean stopped =  false;

    public static void init(){
        for (int i = 0; i< 32; i++){
            registers[i] = new Register();
        }
    }

    public static int getValueInRegister(int id){
        return registers[id].getValue();
    }

    public static int nonLoggingGetValueInRegister(int id) {
        return registers[id].nonLoggingGetValue();
    }

    public static String getHexValueInRegister(int id) {
        return registers[id].getHexValue();
    }

    public static void setRegisterValue(int id, int value) {
        registers[id].setValue(value);
    }

    public static void runUntil() {
        WindowFrame.clearLog(); // reset all memory
        clearRegisters();
        Memory.clear();
        RegisterWindow.update();
        //InfiniteLoopProtectionThread loopProtection = new InfiniteLoopProtectionThread();
        //stopped = false;
        //loopProtection.run();
        codeBody = new CodeBody();   // generate lines
        exceptionEncountered = false;
        currentLine = 0;
        while(codeBody.GetLine(currentLine) != null && codeBody.GetLine(currentLine).getLine() <= WindowFrame.codeWindow.getCursorLine() && !exceptionEncountered && !stopped){  // grab each line one at a time. endpoint is always >= the max number of lines
            Register.clearLastRegistersSetAndRetrieved();    // clear debug statuses
            runLine(currentLine);
            currentLine++;
        }
        //loopProtection.override = true;
    }

    private static void runLine(int lineNumber){
        Line l = codeBody.GetLine(lineNumber);
        if(l != null) {
            try {
                Command c = l.generateCommand();    // generate the command for the line
                c.run();
            } catch (IncorrectArgumentsException e) {
                WindowFrame.log("incorrect arguments used on line " + e.line + 1);
                exceptionEncountered = true;
            } catch (MemoryAddressNotDivisibleByEightException e) {
                WindowFrame.log("Memory address used that is not %8 = 0");
                exceptionEncountered = true;
            } catch (MemoryAddressOutOfBoundsException e) {
                WindowFrame.log("out of bounds memory access \nat memory address " + e.memoryPosition);
                exceptionEncountered = true;
            } catch (CompileTimeArgumentError e) {
                WindowFrame.log("invalid argument at line " + e.line);
                exceptionEncountered = true;
            }
        }
    }

    private static void clearRegisters() {
        for (Register r :
                registers) {
            r.setValue(0);
        }
        registers[28].setValue(Memory.SIZE * 8);
        registers[29].setValue(Memory.SIZE * 8);
    }

    public static void setCurrentLine(int line){
        currentLine = line;
    }

    public static void stop() {
        stopped = true;
    }
}
