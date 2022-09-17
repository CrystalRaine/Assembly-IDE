package processor;

import Exceptions.MemoryAddressNotDivisibleByEightException;
import Exceptions.MemoryAddressOutOfBoundsException;

/**
 * @author Raine
 * created 9/16/2022
 * @project Assm-Info
 */
public class Memory {

    public static final int SIZE = 1000;

    static int[] memoryAddresses = new int[SIZE];

    public Memory(){

    }

    public static void clear() {
        memoryAddresses = new int[SIZE];
    }

    public void setMemoryAddress(int pos, int value) throws MemoryAddressNotDivisibleByEightException, MemoryAddressOutOfBoundsException {
        if(pos % 8 != 0){
            throw new MemoryAddressNotDivisibleByEightException();
        }
        if(pos > SIZE || pos < 0){
            throw new MemoryAddressOutOfBoundsException(pos);
        }
        memoryAddresses[pos] = value;
    }

    public int getFromPosition(int pos) throws MemoryAddressNotDivisibleByEightException, MemoryAddressOutOfBoundsException {
        if(pos % 8 != 0){
            throw new MemoryAddressNotDivisibleByEightException();
        }
        if(pos > SIZE || pos < 0){
            throw new MemoryAddressOutOfBoundsException(pos);
        }
        return memoryAddresses[pos / 8];
    }
}
