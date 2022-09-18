package graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

/**
 * @author Raine
 * created 9/17/2022
 * @project Assm-Info
 */
public class FileSelection extends JPanel {

    private static final Color BACKGROUND_COLOR = new Color(50,50,50);
    private static final Color FOREGROUND_COLOR = new Color(200,200,200);
    private static final Color SELECTED_COLOR = new Color(100,50,100);

    private static ArrayList<JButton> buttons = new ArrayList<>();

    public FileSelection(){

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setBackground(BACKGROUND_COLOR);
        this.setForeground(FOREGROUND_COLOR);

        File resFolder = new File(WindowFrame.PATH);

        for (File f : resFolder.listFiles()) {
            addTab(f);
        }
        buttons.get(0).setBackground(SELECTED_COLOR);
    }

    public void removeTab(String name){
        int i;
        for(i = 0; i < buttons.size(); i++){
            if((buttons.get(i).getText().substring(2, buttons.get(i).getText().length()-1) + WindowFrame.EXTENSION).equals(name)){
                this.remove(buttons.get(i));
                break;
            }
        }
        for (JButton b1 : buttons) {
            b1.setEnabled(true);
            b1.setBackground(BACKGROUND_COLOR);
        }

        buttons.get(i-1).setEnabled(false);
        buttons.get(i-1).setBackground(SELECTED_COLOR);

        WindowFrame.currentFileName = buttons.get(i-1).getText().substring(2, buttons.get(i-1).getText().length()-1) + WindowFrame.EXTENSION;

        revalidate();
        repaint();
    }

    public void addTab(File f) {
        JButton b = new JButton("| " + f.getName().substring(0, f.getName().lastIndexOf(".")) + " ");
        buttons.add(b);
        b.setBackground(BACKGROUND_COLOR);
        b.setBorder(null);
        b.setForeground(FOREGROUND_COLOR);
        b.addActionListener(e -> {
            WindowFrame.save();
            WindowFrame.currentFileName = f.getName();
            WindowFrame.load();
            for (JButton b1 : buttons) {
                b1.setEnabled(true);
                b1.setBackground(BACKGROUND_COLOR);
            }
            b.setEnabled(false);
            b.setBackground(SELECTED_COLOR);
        });
        this.add(b);
        this.revalidate();
        this.repaint();
    }
}
