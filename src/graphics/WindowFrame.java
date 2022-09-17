package graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

import static java.lang.System.exit;
import static java.lang.System.identityHashCode;

/**
 * @author Raine
 * created 9/16/2022
 * @project Assm-Info
 */
public class WindowFrame extends JFrame {

    private static Output out;
    public static CodeWindow codeWindow;

    public WindowFrame(){
        this.setSize(1000,750);
        this.setVisible(true);
        this.setLayout(new BorderLayout());
        out = new Output();
    }

    public static void clearLog() {
        out.setText("");
    }

    public void setup(){
        JScrollPane outSC = new JScrollPane(out);           // set up output
        outSC.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        outSC.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        outSC.setBorder(null);
        this.add(outSC, BorderLayout.WEST);

        RegisterWindow regWind = new RegisterWindow();      // set up registers
        this.add(regWind, BorderLayout.EAST);

        codeWindow = new CodeWindow();                 // set up code window, and load from datafile
        JScrollPane codeSC = new JScrollPane(codeWindow);
        codeSC.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        codeSC.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        codeSC.setBorder(null);
        this.add(codeSC, BorderLayout.CENTER);
        try {
            String s = "";
            Scanner sc = new Scanner(new File("res/data.txt"));
            while(sc.hasNextLine()){
                s += sc.nextLine() + "\n";
            }
            codeWindow.setText(s);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }

        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(new File("res/data.txt")));
                    writer.write(codeWindow.getText());
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) { exit(0); }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }

    public static void log(Object s){
        out.setText(out.getText() + "\n" + s.toString());
    }

    public void update() {
        this.revalidate();
        this.repaint();
    }
}
