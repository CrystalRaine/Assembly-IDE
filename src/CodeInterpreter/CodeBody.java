package CodeInterpreter;

import Exceptions.*;
import graphics.CodeWindow;
import graphics.WindowFrame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Raine
 * created 9/16/2022
 * @project Assm-Info
 */
public class CodeBody {

    private static ArrayList<Line> lines = new ArrayList<>();
    private static HashMap<String, Integer> labels = new HashMap<>();

    public CodeBody(){
        lines.clear();
        labels.clear();
        Scanner sc = new Scanner(WindowFrame.codeWindow.getText());
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

        lineNumber = 0;
        sc = new Scanner(WindowFrame.codeWindow.getText());
        while (sc.hasNextLine()){
            String nextLine = sc.nextLine();
            Line l = new Line(nextLine.strip(), lineNumber);   // generate each line

            l.getLabels();

            lines.add(l);   // add the line to the list
            lineNumber++;   // inc
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

    public static void addLine(Line line){
        lines.add(line);
    }
}
