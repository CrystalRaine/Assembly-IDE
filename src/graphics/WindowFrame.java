package graphics;

import javax.swing.*;
import java.awt.*;

/**
 * @author Raine
 * created 9/16/2022
 * @project Assm-Info
 */
public class WindowFrame extends JFrame {

    public WindowFrame(){
        this.setSize(1000,750);
        this.setVisible(true);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void setup(){
        CodeWindow code = new CodeWindow();
        this.add(code, BorderLayout.CENTER);

        RegisterWindow regWind = new RegisterWindow();
        this.add(regWind, BorderLayout.EAST);
    }

    public void update() {
        this.revalidate();
        this.repaint();
    }
}
