/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game_methods;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
/**
 *
 * @author User
 */
public class Control extends MyGame_methods{
    //atributo que controlá a pausa do jogo
    private boolean pause = false;
    //atributo para controlar os menus
    private boolean menu = true;    
    //atribustos para pegar a posição do mouse
    private int x = 0;
    private int y = 0;
    //controle do formato do cursor
    private boolean cursor = false; 
        
             //método abstrato para movimentação
            @Override
            public int motionX(int x){
            x = this.x;
            return x;
            }
    
            //método abstrato para movimentação
            @Override
            public int motionY(int y){
            y = this.y;            
            return y;
            }
                      
           @Override
           public boolean pausar() {
           return pause;    
           }   
    
           @Override
           public boolean menu() {
           return menu;    
           }
           
           @Override
           public boolean changeCursor(){
           return cursor;
           }
                     
            @Override
            public void keyPressed(KeyEvent ke){
                //Condição onde, quando o enter for pressionado, a pausa será convertida para true ou false               
                if((ke.getKeyCode() == KeyEvent.VK_ENTER || ke.getKeyCode() == KeyEvent.VK_SPACE) 
                && pause == false) pause = true;
                
                else if((ke.getKeyCode() == KeyEvent.VK_ENTER || ke.getKeyCode() == KeyEvent.VK_SPACE) 
                && pause == true) pause = false;
                                     
                else if(ke.getKeyCode() == KeyEvent.VK_ESCAPE) if(CloseMessage(true)) terminate();
            }
            
            //método para pegar a posição do mouse
            @Override
            public void mouseMoved(MouseEvent me) {
             //pega a posição do cursor   
             this.x = me.getX();
             this.y = me.getY();   
             //seta como true para deixar o cursor no formato padrão
             cursor = true;
             //quando a posição do mouse estiver entre os valores de x e y esteiverem entre os valores do arraylist
             //muda o formato do cursor do mouse para hand
             for (int i=0 ; i<3 ; i++) {
                if (this.x >= minX().get(i) && this.x <= maxX().get(i)  
                 && this.y >= minY().get(i) && this.y <= maxY().get(i)) 
                 cursor = false;
             }
            }            

    @Override
    public void mouseClicked(MouseEvent e) {
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        
        //se a posição do cursor estiver entre os valores da posição 0 do arraylist
        //o menu do jogo se abrirá
        if(this.x >= minX().get(0) && this.x <= maxX().get(0) &&
           this.y >= minY().get(0) && this.y <= maxY().get(0)){
        
            menu = false;        
            pause = false;
           
        //se a posição do cursor estiver entre os valores da posição 1 do arraylist
        //as dicas de como se jogar o jogo serão mostradas
        }else if(this.x >= minX().get(1) && this.x <= maxX().get(1) &&
                 this.y >= minY().get(1) && this.y <= maxY().get(1)){
            
            JOptionPane.showMessageDialog(null, "- Controles:\n" +
            "  Mouse movimenta a lixeira para esquerda e direita e clica nas opções de início e de sair.\n" +
            "  Botões Barra de espaço, Enter e colocar o jogo em segundo plano pausam o jogo.\n" +
            "\n- Jogo:\n" +
            "  Doutor Sujeira criou uma máquina que faz chover lixo, e seu objetivo como jogador, é recolher o maior número possível de lixo reciclável\n" +
            "afim de fazer a maior pontuação e salvar o planeta da poluição causada pelo descarte incorreto de lixo causado pelo ataque do Doutor Sujeira, \n" + 
            "movimentando a lixeira da esquerda para a direita ao longo da tela, enquanto recolhe o lixo reciclável.\n" +
            "\n- Pontuação:\n Papel: +1 ponto\n Plástico: +5 pontos.\n Vidro: +10 pontos.\n Metal: +20 pontos. \n Orgânico: -20 pontos.\n" +
            "\n- Vida\n" +
            " Inicia-se o jogo com três vidas, e caso o lixo reciclável atinja o solo, perde-se uma vida. Se chegar a zero vidas o jogo acaba.");
            
        //se a posição do cursor estiver entre os valores da posição 3 do arraylist
        //o jogo encerrara
        }else if(this.x >= minX().get(2) && this.x <= maxX().get(2) &&
                 this.y >= minY().get(2) && this.y <= maxY().get(2)){
       
            if(CloseMessage(true)) terminate();
            
        }    
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
    @Override
    public void mouseDragged(MouseEvent me) {
    }   
             
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}