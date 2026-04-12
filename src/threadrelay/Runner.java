/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package threadrelay;

import java.util.Random;

/**
 *classe runner che implementa Runnable (sarà il thread)
 * @author lucia
 */
public class Runner implements Runnable{
/**
 * attributi
 */
    private boolean inPausa; //variabile boolean che ci dice se il thread è in pausa 
    private boolean reset;// variabile boolean che ci dice se il thread deve essere azzerato 
    private int tempo; // la velocità del theread 
    private int i; // varizbile intere del progresso del thread

    
    /**
     * Costruttore
     * @param tempo la velocità che viene passata a secondo della scelta sulla combobox
     */
    public Runner(int tempo) {
        this.tempo = tempo;
        inPausa= false;
        reset= false;
        i=0;
    }

    //metodo obbligatorio run
    public void run() {
       
       //ciclo per simulare la progress bar che ha come massimo 100
        for ( this.i = 1; this.i <= 100; this.i++) {

            if (reset) { // se è true lo obbliga a uscire dal ciclo 
                return;
            }

            synchronized (this) { //synchronized perchè il metdo wait deve stare sempre in contesti synchronized
                while (inPausa) {
                    try {
                        wait();// viene messo in wait se è vero
                    } catch (InterruptedException ignored) {
                        System.out.println(ignored.getMessage());
                    }
                }
            } 
       

            try {
                Thread.sleep(tempo);//velocità del runner nella gara, quanto velocemnte aumenta i
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    
    /**
     * metodo pausa che mette la variabile inPausa true
     */
    public synchronized void pausa() {
        inPausa = true;
    }
    /**
     * metodo riprendi che  mette la variabile inPausa false e fa notify per far riprendere la corsa
     */
    public synchronized void riprendi() {
        inPausa = false;
         notify();
    }

    /**
     * metodo reset che mette la variabile reset a true e notifica per resettare la corsa
     */
    public synchronized void reset() {
        reset = true;
        inPausa = false;
            notify(); // manda un notify in caso sia in wait
        
    }

    
    /**
     * metodo per permettere al form di leggere il valore del thread
     * @return valore del thread
     */
    public int getI(){
        return this.i;
    }
}
