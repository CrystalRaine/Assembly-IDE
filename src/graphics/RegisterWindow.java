package graphics;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * @author Raine
 * created 9/16/2022
 * @project Assm-Info
 */
public class RegisterWindow extends JPanel {

    private static RegisterLine[] registerLines = new RegisterLine[32];

    public RegisterWindow(){
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        this.setBackground(Color.darkGray);
        this.setForeground(Color.lightGray);

        JLabel spacer = new JLabel();
        spacer.setPreferredSize(new Dimension(200,0));
        this.add(spacer);

        for (int i = 0; i < 32; i++) {
            registerLines[i] = new RegisterLine();
            this.add(registerLines[i]);
        }
    }

    public static void update(){
        for (RegisterLine r : registerLines) {
            r.updateDisplay();
        }
    }

}
