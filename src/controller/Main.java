package controller;

import graphics.WindowFrame;
import processor.Processor;

/**
 * @author Raine
 * created 9/16/2022
 */
public class Main {
    public static WindowFrame wf;

    public static void main(String[] args){

        wf = new WindowFrame();
        Processor.init();
        wf.setup();
        wf.update();
    }
}
