package graphics;

import processor.Processor;
import processor.Register;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author Raine
 * created 9/16/2022
 * @project Assm-Info
 */
public class CodeWindow extends JTextArea {

    private int currentLine;

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
        for (char c : s.toCharArray()) {
            if (c == '\n') {
                line++;
            }
        }
        return line;
    }

    private void onUpdate(){
        currentLine = getCursorLine();
        Processor.runUntil(currentLine);
        RegisterWindow.update();
    }
}
