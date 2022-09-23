package processor;

import Exceptions.MemoryAddressNotDivisibleByEightException;
import Exceptions.MemoryAddressOutOfBoundsException;

/**
 * @author Raine
 * created 9/16/2022
 */
public class Memory {

    public static final int SIZE = 1000;

    private static int[] memoryAddresses = new int[SIZE];
    private static boolean[] used = new boolean[SIZE];

    public static void clear() {
        memoryAddresses = new int[SIZE];
        used = new boolean[SIZE];
    }

    public static String dump() {
        StringBuilder compiled = new StringBuilder();
        compiled.append("-------Memory dump-------\n");
        compiled.append("Position | Value\n");
        for(int i = 0; i < SIZE; i++){
            if (used[i]){
                compiled.append(String.format("0x%s | %s\n", Integer.toHexString(i), memoryAddresses[i]));
            }
        }
        compiled.append("---Memory dump ends---\n");
        return compiled.toString();
    }

    public static void setMemoryAddress(int pos, int value) throws MemoryAddressNotDivisibleByEightException, MemoryAddressOutOfBoundsException {
        if(pos % 8 != 0){
            throw new MemoryAddressNotDivisibleByEightException();
        }
        if(pos > SIZE * 8 || pos < 0){
            throw new MemoryAddressOutOfBoundsException(pos);
        }
        memoryAddresses[pos/8] = value;
        used[pos/8] = true;
    }

    public static int getFromPosition(int pos) throws MemoryAddressNotDivisibleByEightException, MemoryAddressOutOfBoundsException {
        if(pos % 8 != 0){
            throw new MemoryAddressNotDivisibleByEightException();
        }
        if(pos > SIZE * 8 || pos < 0){
            throw new MemoryAddressOutOfBoundsException(pos);
        }
        return memoryAddresses[pos / 8];
    }
}
