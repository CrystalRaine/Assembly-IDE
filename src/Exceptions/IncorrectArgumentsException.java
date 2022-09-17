package Exceptions;

/**
 * @author Raine
 * created 9/16/2022
 * @project Assm-Info
 */
public class IncorrectArgumentsException extends Exception{
    public int line;
    public IncorrectArgumentsException(int line) {
        this.line = line;
    }
}
