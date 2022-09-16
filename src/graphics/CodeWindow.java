package graphics;

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
        this.setBackground(Color.darkGray);
        this.setForeground(Color.white);
        this.setTabSize(5);

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                currentLine = getCursorLine();
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });     // if arrows are used to move cursor
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentLine = getCursorLine();
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

        this.setTestDefaultText();
    }
    public int getCurrentLine(){
        return currentLine;
    }

    private int getCursorLine(){
        int caretPos = getCaretPosition();
        int line = 0;
        int currPos = 0;
        String s = getText();
        while(currPos <= caretPos){
            if(s.charAt(currPos) == '\n'){
                line++;
            }
            currPos++;
        }
        return line-1;
    }

    private void setTestDefaultText(){
        this.setText("fib: \n" +
                "\n" +
                "CBZ x0, done\t\t// if <2 return immedeately \n" +
                "SUBI x9, x0, #1\n" +
                "CBZ x9, done\n" +
                "\t\t\t//STACK MANIPULATION TO FOLLOW\n" +
                "SUBI SP, SP, #24\n" +
                "STUR x19, [SP, #16]\n" +
                "STUR LR, [SP, #8]\t// back up link register\n" +
                "STUR x21, [SP, #0]\n" +
                "\n" +
                "ADDI x19, x0, #0\t\t//back up argument\n" +
                "SUBI x0, x19 #1\t\t// set x0 to n-1\n" +
                "BL fib\t\t\t//recursive call\n" +
                "ADDI x21, x0, #0\t\t// save result\n" +
                "\n" +
                "SUBI x0, x19, #2\t\t//set x0 to n-2\n" +
                "BL fib\t\t\t//recursive call\n" +
                "ADD x0, x21, x0\t// add to already saved result\n" +
                "\n" +
                "\t\t\t//STACK MANIPULATION TO FOLLOW\n" +
                "LUDR x19, [SP, #16]\n" +
                "LUDR xLR, [SP, #8]\n" +
                "LUDR x21 [SP, #0]\n" +
                "ADDI SP, SP, #24\n" +
                "\n" +
                "done: \n" +
                "BR LR");
    }
}
