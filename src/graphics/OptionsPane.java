package graphics;

import controller.Main;
import processor.Processor;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

/**
 * @author Raine
 * created 9/17/2022
 */
public class OptionsPane extends JPanel {

    private static final Color BACKGROUND_COLOR = new Color(90,90,90);
    private static final Color FOREGROUND_COLOR = new Color(200,200,200);

    private JButton deleteFile;
    private JButton createFile;
    public static JButton viewInstructions;
    public static JButton viewAnnotations;
    public static JButton viewLibrary;
    public static JButton copyText;
    private JTextField text;

    public OptionsPane(){
        this.setLayout(new BorderLayout());

        JPanel bag = new JPanel();
        text = new JTextField();
        text.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == '\n'){
                   createFile();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        deleteFile = new JButton("Delete Current File");
        createFile = new JButton("Create New File");
        viewInstructions = new JButton("View Instructions");
        viewAnnotations = new JButton("View Annotations");
        viewLibrary = new JButton("View Library");
        copyText = new JButton("Copy code");

        text.setBorder(null);

        bag.setLayout(new GridBagLayout());

        deleteFile.addActionListener(e -> {
            File f = new File(WindowFrame.PATH + WindowFrame.currentFileName);
            f.delete();
            WindowFrame.selector.removeTab(f.getName());
        });

        createFile.addActionListener(e -> {
            createFile();
        });

        viewInstructions.addActionListener(e -> view(1, viewInstructions, "View Instructions", "Hide Instructions"));
        viewAnnotations.addActionListener(e -> view(2, viewAnnotations, "View Annotations", "Hide Annotations"));
        viewLibrary.addActionListener(e -> view(3, viewLibrary, "View Library", "Hide Library"));

        copyText.addActionListener(e -> {
            if(Processor.codeBody != null) {
                StringSelection stringSelection = new StringSelection(Processor.codeBody.getActualText());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
            }
        });

        this.setBackground(BACKGROUND_COLOR);
        this.setForeground(FOREGROUND_COLOR);
        bag.setBackground(BACKGROUND_COLOR);
        bag.setForeground(FOREGROUND_COLOR);
        createFile.setBackground(BACKGROUND_COLOR);
        createFile.setForeground(FOREGROUND_COLOR);
        deleteFile.setForeground(FOREGROUND_COLOR);
        deleteFile.setBackground(BACKGROUND_COLOR);
        text.setBackground(BACKGROUND_COLOR.darker().darker());
        text.setForeground(FOREGROUND_COLOR.brighter());

        viewInstructions.setBackground(BACKGROUND_COLOR);
        viewInstructions.setForeground(FOREGROUND_COLOR);
        viewAnnotations.setBackground(BACKGROUND_COLOR);
        viewAnnotations.setForeground(FOREGROUND_COLOR);
        viewLibrary.setBackground(BACKGROUND_COLOR);
        viewLibrary.setForeground(FOREGROUND_COLOR);

        copyText.setBackground(BACKGROUND_COLOR);
        copyText.setForeground(FOREGROUND_COLOR);

        bag.add(createFile);
        bag.add(deleteFile);
        bag.add(viewInstructions);
        bag.add(viewAnnotations);
        bag.add(viewLibrary);
        bag.add(copyText);

        this.add(text, BorderLayout.NORTH);
        this.add(bag, BorderLayout.CENTER);
    }

    private void createFile(){
        if(!text.getText().strip().equals("")) {
            try {
                File newFile = new File(WindowFrame.PATH + text.getText() + WindowFrame.EXTENSION);  // create actual file in the system
                newFile.createNewFile();
                text.setText("");

                WindowFrame.selector.addTab(newFile);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            text.setText("");
        }
    }

    private void view(int screen, JButton textChange, String text1, String text2){
        if(textChange.getText().equals(text1)) {
            textChange.setText(text2);
            Main.wf.clearCenter();
            deleteFile.setEnabled(false);
            createFile.setEnabled(false);
            viewAnnotations.setEnabled(false);
            viewLibrary.setEnabled(false);
            viewInstructions.setEnabled(false);
            Main.wf.addHelp(screen);
        } else {
            textChange.setText(text1);
            Main.wf.clearCenter();
            Main.wf.addCode();
            deleteFile.setEnabled(true);
            createFile.setEnabled(true);
            viewAnnotations.setEnabled(true);
            viewLibrary.setEnabled(true);
            viewInstructions.setEnabled(true);
        }
        revalidate();
        repaint();
        Main.wf.update();
    }
}
