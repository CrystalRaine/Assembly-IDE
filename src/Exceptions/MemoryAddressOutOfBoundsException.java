package Exceptions;

/**
 * @author Raine
 * created 9/16/2022
 */
public class MemoryAddressOutOfBoundsException extends Exception {
    public int memoryPosition;
    public MemoryAddressOutOfBoundsException(int pos) {
        memoryPosition = pos;
    }
}
