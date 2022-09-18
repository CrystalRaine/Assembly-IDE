package graphics;

import CodeInterpreter.Command;
import controller.Main;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author Raine
 * created 9/17/2022
 * @project Assm-Info
 */
public class OptionsPane extends JPanel {

    private static final Color BACKGROUND_COLOR = new Color(90,90,90);
    private static final Color FOREGROUND_COLOR = new Color(200,200,200);

    private JButton deleteFile;
    private JButton createFile;
    private JButton viewInstructions;
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

        viewInstructions.addActionListener(e -> {
            if(viewInstructions.getText().equals("View Instructions")) {
                viewInstructions.setText("close instructions");
                Main.wf.clearCenter();
                Main.wf.addInstructions();
                deleteFile.setEnabled(false);
                createFile.setEnabled(false);
            } else {
                viewInstructions.setText("View Instructions");
                Main.wf.clearCenter();
                Main.wf.addCode();
                deleteFile.setEnabled(true);
                createFile.setEnabled(true);
            }
            revalidate();
            repaint();
            Main.wf.update();
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

        bag.add(createFile);
        bag.add(deleteFile);
        bag.add(viewInstructions);

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
}
