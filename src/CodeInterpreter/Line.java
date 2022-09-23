package CodeInterpreter;

import Exceptions.*;

/**
 * @author Raine
 * created 9/16/2022
 */
public class Line {

    String line;
    private int lineNumber;

    public Line(String line, int lineNumber){
        this.line = line;
        this.lineNumber = lineNumber;
    }

    public void getLabels() {
        if(line.contains(":")){
            CodeBody.addLabel(line.substring(0, line.indexOf(':')), lineNumber);
            line = line.substring(line.indexOf(':') + 1);
            getLabels();    // can have multiple labels per line
        }
    }

    public void replaceLabels(String s, int number) {
        line = line.replaceAll(s, "#" + number);
    }

    public void replaceRegisterAliases() {
        line = line.replaceAll("XZR", "x31");  // replace each alias with the correct register
        line = line.replaceAll("ZR", "x31");
        line = line.replaceAll("LR", "x30");
        line = line.replaceAll("FP", "x29");
        line = line.replaceAll("SP", "x28");

    }

    public Command generateCommand() throws CompileTimeArgumentError, IncorrectArgumentsException, MemoryAddressNotDivisibleByEightException, MemoryAddressOutOfBoundsException {

        if(line.contains("//")){
            line = line.substring(0, line.indexOf("//"));   // remove comments from line, comments start with "//"
        }

        if(line.equals("") || line.equals("\n")) {      // empty line is NONE command
            return new Command("NONE", lineNumber);
        }

        int index = line.indexOf(" ");
        Command c = new Command(line.substring(0, index == -1 ? line.length() : index), lineNumber);
        String args = line.substring(line.indexOf(" ") + 1);

        do {
            String arg;
            if (args.contains(",")) {
                arg = args.substring(0, args.indexOf(",")).strip();
                args = args.substring(args.indexOf(",") + 1);
            } else {
                arg = args.strip();
                args = "";
            }

            try {
                if(arg.length() > 1) {
                    if (arg.charAt(0) == '[') {                 // ignore square brackets. technically this implementation makes them optional
                        if (args.contains(",")) {
                            arg = arg.substring(1, args.indexOf(",")).strip();
                        } else {
                            arg = arg.substring(1).strip();
                        }
                    }
                    if (arg.charAt(arg.length() - 1) == ']') {  // ignore square brackets. technically this implementation makes them optional
                        arg = arg.substring(0, arg.length() - 1).strip();
                    }

                    if (arg.length() > 1) {
                        if (arg.charAt(0) == 'x') { // sort out registers
                            c.addRegister(Integer.parseInt(arg.substring(1)));
                        }

                        if (arg.charAt(0) == '#') { // sort out constants
                            c.addConstant(Integer.parseInt(arg.substring(1)));
                        }
                    }
                }
            } catch (NumberFormatException e) {
                throw new CompileTimeArgumentError(lineNumber);
            }
        } while (args.length() > 1);

        return c;
    }

    public int getLineNumber(){
        return lineNumber;
    }

    public String getText() {
        return line;
    }

    public void setLineNumber(int i) {
        lineNumber = i;
    }

    public int getLine() {
        return lineNumber;
    }
}
