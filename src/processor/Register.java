package processor;

import java.util.ArrayList;

/**
 * @author Raine
 * created 9/16/2022
 */
public class Register {
    private int id;
    private int value = 0;
    private static int nextId = 0;
    private static int lastRegisterSet = -1;
    private static ArrayList<Integer> lastRegistersRetrieved = new ArrayList<>();
    public Register(){
        id = nextId;
        nextId++;
    }

    public int getValue() {
        lastRegistersRetrieved.add(this.id);
        return value;
    }

    public int nonLoggingGetValue(){
        return value;
    }

    public String getHexValue(){
        return Integer.toHexString(nonLoggingGetValue());
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

    public static ArrayList<Integer> getLastRegistersRetrieved() {
        return lastRegistersRetrieved;
    }

    public static void clearLastRegistersSetAndRetrieved(){
        lastRegisterSet = -1;
        lastRegistersRetrieved.clear();
    }
}
