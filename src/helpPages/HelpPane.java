package helpPages;

import graphics.WindowFrame;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Raine
 * created 9/17/2022
 * @project Assm-Info
 */
public class HelpPane extends JPanel {

    private static final Color BACKGROUND_COLOR = new Color(50,50,50);
    private static final Color FOREGROUND_COLOR = new Color(150,150,150);

    public HelpPane(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(BACKGROUND_COLOR);
        this.setForeground(FOREGROUND_COLOR);

        try {
            Scanner sc = new Scanner(new File(WindowFrame.INFO_FOLDER + WindowFrame.HELP_FILE_NAME));
            while(sc.hasNextLine()){
                Scanner lineScanner = new Scanner(sc.nextLine());
                lineScanner.useDelimiter(",");

                String html = "<html><body style='width: %1spx'>%1s";
                JLabel l1 = new JLabel(lineScanner.hasNext() ? String.format(html, 35, lineScanner.next()) : " ".repeat(1000));
                JLabel l2 = new JLabel(lineScanner.hasNext() ? String.format(html, 200, lineScanner.next()) : " \n ");
                JLabel l3 = new JLabel(lineScanner.hasNext() ? String.format(html, 500, lineScanner.next()) : " \n " );
                JLabel l4 = new JLabel(lineScanner.hasNext() ? String.format(html, 200, lineScanner.nextLine().substring(1)) : " \n ");

                l1.setFont(l1.getFont().deriveFont(15f));
                l2.setFont(l1.getFont().deriveFont(15f));
                l3.setFont(l1.getFont().deriveFont(15f));
                l4.setFont(l1.getFont().deriveFont(15f));

                l1.setBackground(BACKGROUND_COLOR);
                l1.setForeground(FOREGROUND_COLOR);
                l2.setBackground(BACKGROUND_COLOR);
                l2.setForeground(FOREGROUND_COLOR);
                l3.setBackground(BACKGROUND_COLOR);
                l3.setForeground(FOREGROUND_COLOR);
                l4.setBackground(BACKGROUND_COLOR);
                l4.setForeground(FOREGROUND_COLOR);

                JPanel col1 = new JPanel();
                col1.setLayout(new BoxLayout(col1, BoxLayout.X_AXIS));
                col1.setBackground(BACKGROUND_COLOR);
                col1.setForeground(FOREGROUND_COLOR);
                col1.setAlignmentX(LEFT_ALIGNMENT);
                col1.setAlignmentY(TOP_ALIGNMENT);
                col1.setBorder(new LineBorder(new Color(0,0,0), 1));
                col1.setBorder(new CompoundBorder(col1.getBorder(), new EmptyBorder(0,10,0,0)));

                col1.add(l1);
                col1.add(l2);
                col1.add(l3);
                col1.add(l4);

                this.add(col1);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
