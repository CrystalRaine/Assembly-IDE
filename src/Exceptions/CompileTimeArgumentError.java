package Exceptions;

/**
 * @author Raine
 * created 9/16/2022
 */
public class CompileTimeArgumentError extends Exception{
    public int line;
    public CompileTimeArgumentError(int line){
        this.line = line;
    }
}
