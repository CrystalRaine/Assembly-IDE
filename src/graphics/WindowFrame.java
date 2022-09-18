package graphics;

import helpPages.HelpPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.System.exit;

/**
 * @author Raine
 * created 9/16/2022
 * @project Assm-Info
 */
public class WindowFrame extends JFrame {

    private static Output out;
    public static CodeWindow codeWindow;
    public static HelpPane helpWindow;
    public static String currentFileName;
    public static FileSelection selector;
    public static RegisterWindow regWind;
    public static JScrollPane codeSC;
    public static JScrollPane outSC;
    public static OptionsPane op;

    public static final String PATH = "res/";
    public static final String INFO_FOLDER = "Info/";

    public static final String EXTENSION = ".txt";

    public static final String DEFAULT_FILE_NAME = "Default" + EXTENSION;
    public static final String HELP_FILE_NAME = "Commands.txt";

    private boolean centerFilled = true;

    public WindowFrame() {
        this.setSize(1000,750);
        this.setVisible(true);
        this.setLayout(new BorderLayout());
        out = new Output();
        if(Objects.requireNonNull(new File(PATH).listFiles()).length != 0) {
            currentFileName = Objects.requireNonNull(new File(PATH).listFiles())[0].getName();
        } else {
            File f = new File(PATH + DEFAULT_FILE_NAME);
            try {
                f.createNewFile();
                currentFileName = Objects.requireNonNull(new File(PATH).listFiles())[0].getName();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static void clearLog() {
        out.setText("");
    }

    public void setup(){
        outSC = new JScrollPane(out);           // set up output
        outSC.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        outSC.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        outSC.setBorder(null);
        this.add(outSC, BorderLayout.WEST);

        regWind = new RegisterWindow();      // set up registers
        this.add(regWind, BorderLayout.EAST);

        selector = new FileSelection();
        this.add(selector, BorderLayout.NORTH);

        op = new OptionsPane();
        this.add(op, BorderLayout.SOUTH);

        helpWindow = new HelpPane();

        codeWindow = new CodeWindow();                 // set up code window, and load from datafile
        codeSC = new JScrollPane(codeWindow);
        codeSC.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        codeSC.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        codeSC.setBorder(null);
        this.add(codeSC, BorderLayout.CENTER);
        load();

        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                save();
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

    public static void save(){
        try {
            File f = new File(PATH + currentFileName);    // don't recreate files that are supposed to be deleted
            if(f.exists()) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(f));
                writer.write(codeWindow.getText());
                writer.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void load(){
        try {
            String s = "";
            Scanner sc = new Scanner(new File(PATH + currentFileName));
            while(sc.hasNextLine()){
                s += sc.nextLine() + "\n";
            }
            codeWindow.setText(s);
            sc.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public void clearCenter(){
        this.getContentPane().removeAll();
        this.add(op, BorderLayout.SOUTH);
        this.update();

        centerFilled = false;
    }

    public void addInstructions(){
        this.add(helpWindow, BorderLayout.CENTER);
        centerFilled = true;
    }

    public void addCode(){
        if(!centerFilled){
            this.add(selector, BorderLayout.NORTH);
            this.add(outSC, BorderLayout.WEST);
            this.add(regWind, BorderLayout.EAST);
            this.add(codeSC, BorderLayout.CENTER);
            // leave SOUTH

            centerFilled = true;
        }
    }

    public void update() {
        this.revalidate();
        this.repaint();
    }
}
