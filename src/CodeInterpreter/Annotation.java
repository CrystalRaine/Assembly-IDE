package CodeInterpreter;

import Exceptions.*;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Raine
 * created 9/18/2022
 * @project Assm-Info
 */
public class Annotation {

    private enum Annotations{
        INVALID, Funct, Load, Store, Init, MemInit
    }

    private String annotationText;
    private Annotations type;

    private ArrayList<Integer> values = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();

    public Annotation(String annotationText) throws Exception {
        this.annotationText = annotationText;

        if(this.annotationText.contains(" ")){
            String name = this.annotationText.substring(0, this.annotationText.indexOf(" "));
            String args = annotationText.substring(annotationText.indexOf(" ")).strip();

            type = Annotations.INVALID;
            for(Annotations ann : Annotations.values()){
                if(ann.toString().equals(name)){
                    type = ann;
                }
            }
            if(type == Annotations.INVALID){
                throw new Exception();
            }

            Scanner sc = new Scanner(args);
            while (sc.hasNext()){
                String arg = sc.next();
                try{
                    int i = Integer.parseInt(arg);
                    values.add(i);
                } catch (NumberFormatException e){
                    names.add(arg);
                }
            }
        } else {
            throw new Exception();
        }
    }

    public String activate() throws Exception {
        StringBuilder b = new StringBuilder();
        switch (type){
            case Init -> {
                requirePairs();
                for (int i = 0; i < values.size(); i++) {
                    requireRegister(names.get(i));
                    b.append("ADDI " + names.get(i) + ", ZR, #" + values.get(i) + "\n");
                }
            }
            case Load -> {      // to stack
                for(int i = 0; i < names.size(); i++){
                    requireRegister(names.get(i));
                    b.append("LUDR " + names.get(i) + ", [SP, #" + (8 * i) + "]" + (i==0?" \t// save values to stack":"") + "\n");
                }
                b.append("ADDI SP, SP, #" + (8 * names.size())+ " \t\t// shrink space\n");
            }
            case Store -> {
                b.append("SUBI SP, SP, #" + (8 * names.size())+ " \t\t// make space\n");
                for(int i = 0; i < names.size(); i++){
                    requireRegister(names.get(i));
                    b.append("STUR " + names.get(i) + ", [SP, #" + (8 * i) + "]" + (i==0?" \t// save values to stack":"") + "\n");
                }
            }
            case Funct -> {
                require(0,1);
                b.append("B " + names.get(0) + "end\n"); //skip over body if not called
                b.append(names.get(0) + ":\n");
                b.append("\t\n");
                b.append("BR LR\n");
                b.append(names.get(0) + "end:\n");
            }
            case MemInit -> {   // @MemInit x1 6 5 4 3 2 1...
                requireRegister(names.get(0));
                for (int i = 0; i < values.size(); i++) {
                    b.append("ADDI x9, ZR, #" + values.get(i) + (i==0?" \t// initialize memory":"") + "\n");  // value to store
                    b.append("STUR x9, [" + names.get(0) + ", #" + i * 8 );
                    b.append("]\n");
                }
            }
        }
        return b.toString();
    }

    private void requireRegister(String name) throws Exception {
        if(name.contains("x")){
            try{
                int i = Integer.parseInt(name.substring(1));
                if(!(i >= 0 && i <=31)){
                    throw new Exception();
                }
            } catch (NumberFormatException e){
                throw new Exception();
            }
        } else {
            throw new Exception();
        }
    }

    private void requirePairs() throws Exception {
        if(values.size() != names.size()){
            throw new Exception();
        }
    }

    private void require(int values, int names) throws Exception {
        if(values != this.values.size() || names != this.names.size()){
            throw new Exception();
        }
    }
}
