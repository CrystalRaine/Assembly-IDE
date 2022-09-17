package graphics;

import javax.swing.*;
import java.awt.*;

/**
 * @author Raine
 * created 9/16/2022
 * @project Assm-Info
 */
public class Output extends JTextArea {

    public Output(){
        this.setPreferredSize(new Dimension(250,0));
        this.setEnabled(false);
        this.setBackground(new Color(70,70,70));
        this.setMargin(new Insets(5,5,5,5));
        this.setForeground(Color.lightGray);
    }

}
