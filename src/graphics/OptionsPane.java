package graphics;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public OptionsPane(){
        this.setLayout(new BorderLayout());

        JPanel bag = new JPanel();
        JTextField text = new JTextField();
        JButton deleteFile = new JButton("Delete Current File");
        JButton createFile = new JButton("Create New File");

        text.setBorder(null);

        bag.setLayout(new GridBagLayout());

        deleteFile.addActionListener(e -> {
            File f = new File("res/" + WindowFrame.currentFileName);
            f.delete();
            WindowFrame.selector.removeTab(f.getName());
        });

        createFile.addActionListener(e -> {
            try {
                File newFile = new File("res/" + text.getText() + ".txt");  // create actual file in the system
                newFile.createNewFile();
                text.setText("");

                WindowFrame.selector.addTab(newFile);
            } catch (IOException ex) {
                ex.printStackTrace();
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
        text.setBackground(BACKGROUND_COLOR);
        text.setForeground(FOREGROUND_COLOR);

        bag.add(createFile);
        bag.add(deleteFile);
        this.add(text, BorderLayout.NORTH);
        this.add(bag, BorderLayout.CENTER);
    }
}
