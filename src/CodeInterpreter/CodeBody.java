package CodeInterpreter;

import Exceptions.*;
import graphics.CodeWindow;
import graphics.WindowFrame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author Raine
 * created 9/16/2022
 * @project Assm-Info
 */
public class CodeBody {

    private static ArrayList<Line> lines = new ArrayList<>();
    private static HashMap<String, Integer> labels = new HashMap<>();
    private String cpTxt = "";

    public CodeBody(){
        lines.clear();  // clear labels and previous data
        labels.clear();

        Scanner sc = new Scanner(WindowFrame.codeWindow.getText()); // replace annotations
        int lineNumber = 0;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.contains("@")) {
                String annotation = line.substring(line.indexOf("@") + 1);    // grab annotation text
                try {
                    Annotation an = new Annotation(annotation);
                    String added = an.activate();
                    if(WindowFrame.codeWindow.getCursorLine() != lineNumber) {
                        WindowFrame.codeWindow.setText(WindowFrame.codeWindow.getText().replaceFirst(line, added));
                    }
                } catch (Exception e) {
                    WindowFrame.log("annotation error");
                }
            }
            lineNumber++;
        }

        sc = new Scanner(WindowFrame.codeWindow.getText()); // reset scanner
        StringBuilder sb = new StringBuilder("");   // grab #includes
        lineNumber = 0;
        String line;
        while (sc.hasNextLine() && (line = sc.nextLine()).contains("#include ") && line.length() >= 10){    // parse includes
            File found = null;
            for(File f : Objects.requireNonNull(new File(WindowFrame.LIBRARY_PATH).listFiles())){
                if(f.getName().equals(line.strip().substring(9) + WindowFrame.EXTENSION)){
                    found = f;
                    break;
                }
            }
            for(File f : Objects.requireNonNull(new File(WindowFrame.PATH).listFiles())){   // self defined takes precedence over stdlib
                if(f.getName().equals(line.strip().substring(9) + WindowFrame.EXTENSION)){
                    found = f;
                    break;
                }
            }
            if(found != null) {
                try (Scanner fileScanner = new Scanner(found)) {    // add import lines
                    while (fileScanner.hasNextLine()) {                           // run lines
                        String nextLine = fileScanner.nextLine();
                        if(nextLine.length() >= 1 && nextLine.charAt(0) != '#') {   // skip include lines
                            Line l = new Line(nextLine.strip(), lineNumber);   // generate each line
                            lines.add(l);   // add the line to the list
                            lineNumber++;   // inc
                        }
                    }
                } catch (FileNotFoundException e) {
                    WindowFrame.log("Invalid import ignored on line " + lineNumber);
                }
            }
        }

        WindowFrame.codeWindow.setOffsetFromLoadedFiles(lineNumber-1);

        sc = new Scanner(WindowFrame.codeWindow.getText()); // reset scanner to scan open file
        while (sc.hasNextLine()){                           // read in lines
            String nextLine = sc.nextLine();
            if(nextLine.length() >= 1 && nextLine.charAt(0) != '#') {   // skip include lines
                Line l = new Line(nextLine.strip(), lineNumber);   // generate each line
                lines.add(l);   // add the line to the list
                lineNumber++;   // inc
            }
        }

        StringBuilder b = new StringBuilder("");
        for (Line s : lines) {
            b.append(s.getText() + "\n");
        }
        cpTxt = b.toString();

        for (Line l : lines) {
            l.getLabels();
        }
        for (Line l : lines) {
            for (String s : labels.keySet()) {
                l.replaceLabels(s, labels.get(s));
            }
            l.replaceRegisterAliases();
        }
    }

    public Line GetLine(int line) {
        if(lines.size() - 1 < line || line < 0){
            return null;
        }
        return lines.get(line);
    }

    public static void addLabel(String labelName, int line) {
        labels.put(labelName, line);
    }

    public String getActualText(){
        return cpTxt;
    }
}
