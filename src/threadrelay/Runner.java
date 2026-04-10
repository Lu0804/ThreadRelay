/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package threadrelay;

import java.util.Random;

/**
 *
 * @author lucia
 */
public class Runner implements Runnable{

  
    private boolean inPausa;
    private boolean reset;
    private int tempo;
    private int i;

    public Runner(int tempo) {
        this.tempo = tempo;
        inPausa= false;
        reset= false;
        i=0;
    }

    public void run() {
       

        for ( this.i = 1; this.i <= 100; this.i++) {

            if (reset) {
                return;
            }

            synchronized (this) {
                while (inPausa) {
                    try {
                        wait();// synchronized perchè wait deve stare sempre in contesti synchronized
                    } catch (InterruptedException ignored) {
                        System.out.println(ignored.getMessage());
                    }
                }
            }

         //aggiorna barra, 
       

            try {
                Thread.sleep(tempo);//velocità del runner nella gara
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public synchronized void pausa() {
        inPausa = true;
    }

    public synchronized void riprendi() {
        inPausa = false;
         notify();
    }

    public synchronized void reset() {
        reset = true;
        inPausa = false;
            notify();
        
    }

    //metodo per permettere al form di leggere il valore del thread
    public int getI(){
        return this.i;
    }
}
