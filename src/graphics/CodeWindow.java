package graphics;

import CodeInterpreter.Annotation;
import processor.Processor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Scanner;

/**
 * @author Raine
 * created 9/16/2022
 */
public class CodeWindow extends JTextArea {

    private int currentLine;
    private int offsetFromLoadedFiles = 0;

    public CodeWindow(){
        this.setMargin(new Insets(5,5,5,5));
        this.setCaretColor(new Color(150,100,150));
        this.setBackground(Color.darkGray);
        this.setForeground(Color.white);
        this.setTabSize(5);

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyChar() == '\n' || e.getKeyChar() == '\t'){
                    Scanner sc = new Scanner(WindowFrame.codeWindow.getText()); // replace annotations
                    int lineNumber = 0;
                    while (sc.hasNextLine()) {
                        String line = sc.nextLine();
                        if (line.contains("@")) {
                            String annotation = line.substring(line.indexOf("@") + 1);    // grab annotation text
                            try {
                                Annotation an = new Annotation(annotation);
                                String added = an.activate();
                                int cursorPos = WindowFrame.codeWindow.getCaretPosition()-2;
                                WindowFrame.codeWindow.setText(WindowFrame.codeWindow.getText().replaceFirst(line, added));
                                WindowFrame.codeWindow.setCaretPosition(cursorPos + annotation.length() - 4);
                            } catch (Exception ex) {
                                WindowFrame.log("annotation error");
                            }
                        }
                        lineNumber++;
                    }
                }

                onUpdate();
            }
        });     // if arrows are used to move cursor

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onUpdate();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        }); // if mouse is used to move cursor
        currentLine = getCursorLine();
    }

    public int getCursorLine(){
        int caretPos = getCaretPosition();
        int line = 0;
        String s = getText().substring(0, caretPos);
        boolean prevIsNewline = false;
        for (char c : s.toCharArray()) {
            if (c == '\n') {
                if (!prevIsNewline){
                    line++;
                }
                prevIsNewline = true;
            } else {
                prevIsNewline = false;
            }
        }

        return line + offsetFromLoadedFiles;
    }

    public void setOffsetFromLoadedFiles(int line){
        offsetFromLoadedFiles = line;
    }

    private void onUpdate(){
        Processor.runUntil();
        RegisterWindow.update();
    }
}
