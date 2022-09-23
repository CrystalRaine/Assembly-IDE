package controller;

import graphics.WindowFrame;
import processor.Processor;

/**
 * @author Raine
 * created 9/16/2022
 * @project Assm-Info
 *
 * TODO: runToLine runs to wrong line
 *
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
