/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class Frames {
      //atributos para a manipulação de frames
    //60 FPS no maximo
    private final int TARGET_FPS = 60;
    /** intervalo opcional de tempo em milesegundo */
    private final long OPTIMAL_TIME = 10 / TARGET_FPS;
    /** ultimo tempo de frame */
    private long lastFrame;
    private int fps;
    /** ultimo tempo de frame em ms */
    private long lastFPS;
    //atributos para manipular mensagens de erro
    private int result;
    private final Object[] options = {"Encerrar", "Aguardar"};
        
    
     //método para sincronizar os Frames    
     private void synchronize(long ms) {
        try {
            if (ms > 0) {
                Thread.sleep(ms);
            }
        } catch (InterruptedException ex) {
            result = JOptionPane.showOptionDialog(null, "O programa não está respondendo," +
                     "deseja Encerrar?", "ERRO", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null, options, null);
            
            if(result == JOptionPane.YES_OPTION) System.exit(-1);
        }
    }
   
     /**
     * Retorna o tempo do sistema em milisegundos
     */
    private long getTime() {
        return System.nanoTime() / 1000000;
    }

    /**
     * Retorna a diferença de tempo desde o ultimo frame
     * @retorna a diferença do tempo em ms
     */
    private int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;
        return delta;
    }

    public void updateFPS() {
        if (getTime() - lastFPS > 100) {
            System.out.println("FPS: " + fps);
            fps = 0;
            lastFPS += 100;
        }
        fps++;
    }
    
    //método para inicialização
     public void initialize() {
        getDelta();
        lastFPS = getTime();
    }
    
     
     public void onUpdate() {     
     synchronize(lastFrame - getTime() + OPTIMAL_TIME);     
     }
}
