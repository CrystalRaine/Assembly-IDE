package controller;

import graphics.WindowFrame;
import processor.Processor;

/**
 * @author Raine
 * created 9/16/2022
 * @project Assm-Info
 */
public class Main {

    public static void main(String[] args){
        WindowFrame wf = new WindowFrame();
        Processor.init();
        wf.setup();
        wf.update();
    }

}
