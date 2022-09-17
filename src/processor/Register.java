package processor;

import graphics.RegisterWindow;

/**
 * @author Raine
 * created 9/16/2022
 * @project Assm-Info
 *
 * stores the data for one register
 */
public class Register {
    private int id;
    private int value = 0;
    private static int nextId = 0;
    private static int lastRegisterSet = 20;

    public Register(){
        id = nextId;
        nextId++;
    }

    public int getValue() {
        return value;
    }

    public String getHexValue(){
        return Integer.toHexString(getValue());
    }

    public String getBinaryValue(){
        return Integer.toBinaryString(getValue());
    }

    public void setValue(int value){
        lastRegisterSet = id;
        if(this.id != 31) {
            this.value = value;
        }
    }

    public static int getLastRegisterSet(){
        return lastRegisterSet;
    }

    public static void clearLastRegisterSet(){
        lastRegisterSet = -1;
    }
}
