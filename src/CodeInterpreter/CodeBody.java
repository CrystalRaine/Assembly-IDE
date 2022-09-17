package CodeInterpreter;

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
        while (sc.hasNextLine()){
            Line l = new Line(sc.nextLine(), lineNumber);   // generate each line
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
}
