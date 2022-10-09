package controller;

import CodeInterpreter.Command;
import Exceptions.IncorrectArgumentsException;
import Exceptions.MemoryAddressNotDivisibleByEightException;
import Exceptions.MemoryAddressOutOfBoundsException;
import processor.Processor;

/**
 * @author Raine
 * created 10/9/2022
 * @project Assm-Info
 */
public class InfiniteLoopProtectionThread extends Thread{

    public boolean override = false;

    @Override
    public void run(){
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(!override) {
            Command c = new Command("DUMP", Integer.MAX_VALUE);
            try {
                c.run();
            } catch (IncorrectArgumentsException e) {
                e.printStackTrace();
            } catch (MemoryAddressNotDivisibleByEightException e) {
                e.printStackTrace();
            } catch (MemoryAddressOutOfBoundsException e) {
                e.printStackTrace();
            }
            Processor.stop();
        }
    }

}
