package graphics;

import processor.Memory;
import processor.Processor;
import processor.Register;

import javax.swing.*;
import java.awt.*;

/**
 * @author Raine
 * created 9/16/2022
 * @project Assm-Info
 */
public class RegisterLine extends JPanel {

    private int linkedID;
    private static int nextLinkedId = 0;

    private JLabel idLabel;
    private JLabel valueLabel;
    private JLabel hexValueLabel;
    private JLabel binaryValueLabel;
    private JTextField descriptionLine;

    private static final Color GREEN_SELECTED = new Color(10,80,10);
    private static final Color RED_SELECTED = new Color(70,20,20);


    public RegisterLine(){
        this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        this.setBackground(Color.darkGray);
        this.setForeground(Color.white);

        linkedID = nextLinkedId;
        nextLinkedId++;

        descriptionLine = new JTextField();
        descriptionLine.setBackground(Color.darkGray);
        descriptionLine.setBorder(null);

        if(linkedID >= 28){
            descriptionLine.setEnabled(false);
        }
        if(linkedID == 28) {
            descriptionLine.setText("(SP) Stack Pointer");
            this.add(descriptionLine);
        } else if (linkedID == 29){
            descriptionLine.setText("(FP) Frame Pointer");
            this.add(descriptionLine);
        } else if (linkedID == 30){
            descriptionLine.setText("(LR) return address");
            this.add(descriptionLine);
        } else if (linkedID == 31){
            descriptionLine.setText("(ZR) Zero Constant");
            this.add(descriptionLine);
        } else {
            if(linkedID <= 7){
                descriptionLine.setText("Arguments and Results");
            } else if(linkedID <= 8){
                descriptionLine.setText("Indirect result");
            } else if(linkedID <= 15){
                descriptionLine.setText("Temporary");
            } else if(linkedID <= 18){
                descriptionLine.setText("Variable non-preserved");
            } else if(linkedID <= 27){
                descriptionLine.setText("Saved");
            }
            this.add(descriptionLine);
        }

        idLabel = new JLabel("x" + linkedID + (linkedID < 10 ? "  " : ""));
        valueLabel = new JLabel(String.format("%-4s", leftPad(Integer.toString(Processor.nonLoggingGetValueInRegister(linkedID)),8,"  ")));
        hexValueLabel = new JLabel(String.format("  0x%8s   ", leftPad(Processor.getHexValueInRegister(linkedID), 8, "0")));

        valueLabel.setForeground(Color.lightGray);
        hexValueLabel.setForeground(Color.lightGray);
        descriptionLine.setForeground(Color.lightGray);
        idLabel.setForeground(Color.lightGray);
        valueLabel.setBackground(Color.darkGray);
        hexValueLabel.setBackground(Color.darkGray);
        descriptionLine.setBackground(Color.darkGray);
        idLabel.setBackground(Color.darkGray);

        idLabel.setOpaque(true);
        valueLabel.setOpaque(true);
        hexValueLabel.setOpaque(true);

        this.add(idLabel);
        this.add(valueLabel);
        this.add(hexValueLabel);

    }

    void updateDisplay(){
        valueLabel.setText(String.format("%-4s", leftPad(Integer.toString(Processor.nonLoggingGetValueInRegister(linkedID)),8,"  ")));
        hexValueLabel.setText(String.format("  0x%8s   ", leftPad(Processor.getHexValueInRegister(linkedID), 8, "0")));

        if(Register.getLastRegisterSet() == this.linkedID){
            valueLabel.setBackground(GREEN_SELECTED);
            hexValueLabel.setBackground(GREEN_SELECTED);
            descriptionLine.setBackground(GREEN_SELECTED);
            idLabel.setBackground(GREEN_SELECTED);
        } else if(Register.getLastRegistersRetrieved().contains(this.linkedID)) {
            valueLabel.setBackground(RED_SELECTED);
            hexValueLabel.setBackground(RED_SELECTED);
            descriptionLine.setBackground(RED_SELECTED);
            idLabel.setBackground(RED_SELECTED);
        }else{
            valueLabel.setBackground(Color.darkGray);
            hexValueLabel.setBackground(Color.darkGray);
            descriptionLine.setBackground(Color.darkGray);
            idLabel.setBackground(Color.darkGray);

        }

        revalidate();
        repaint();
        valueLabel.revalidate();
        valueLabel.repaint();
        hexValueLabel.revalidate();
        hexValueLabel.repaint();
        idLabel.revalidate();
        idLabel.repaint();
        descriptionLine.revalidate();
        descriptionLine.repaint();

    }

    private String leftPad(String value, int len, String character) {
        return character.repeat(len - value.length()) + value;

    }
}
