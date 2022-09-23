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
    private int pound = 0;

    public CodeBody() {
        lines.clear();  // clear labels and previous data
        labels.clear();
        WindowFrame.save();
        try {
            parseIncludes(new File(WindowFrame.PATH + WindowFrame.currentFileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner sc = new Scanner(WindowFrame.codeWindow.getText()); // reset scanner search for include lines
        while (sc.hasNextLine()){                           // read in lines from open file
            String nextLine = sc.nextLine();
            if(nextLine.length() >= 1 && nextLine.charAt(0) == '#') {   // skip include lines (files have already been added to lines)
                pound++;
            } else {
                break;
            }
        }


        WindowFrame.codeWindow.setOffsetFromLoadedFiles(getLineCount()-(pound));
        System.out.println(pound);

        sc = new Scanner(WindowFrame.codeWindow.getText()); // reset scanner to scan open file

        while (sc.hasNextLine()){                           // read in lines from open file
            String nextLine = sc.nextLine();
            if(nextLine.length() >= 1 && nextLine.charAt(0) != '#') {   // skip include lines (files have already been added to lines)
                Line l = new Line(nextLine.strip(), lines.size());   // generate each line
                lines.add(l);   // add the line to the list
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

    public void parseIncludes(File fi) throws FileNotFoundException {
        Scanner sc = new Scanner(fi);
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
//            pound++;
            if(found != null) {
                parseIncludes(found);
                try (Scanner fileScanner = new Scanner(found)) {    // add import lines
                    while (fileScanner.hasNextLine()) {                           // run lines
                        String nextLine = fileScanner.nextLine();
                        if(nextLine.length() > 1 && nextLine.charAt(0) != '#') {   // skip include lines
                            Line l = new Line(nextLine.strip(), lines.size());   // generate each line
                            lines.add(l);   // add the line to the list
                        }
                    }
                } catch (FileNotFoundException e) {
                    WindowFrame.log("Invalid import ignored on line " + lines.size());
                }
            }
        }
    }

    public int getLineCount(){
        return lines.size();
    }

    public static void addLabel(String labelName, int line) {
        labels.put(labelName, line);
    }

    public String getActualText(){
        return cpTxt;
    }
}
