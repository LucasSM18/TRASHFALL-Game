/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author User
 */
public abstract class game_methods extends JFrame implements WindowListener, KeyListener, MouseListener, MouseMotionListener{
    /* Variáveis necessária para o motor do jogo.
     mainWindow armazenará uma referência para a janela criada para o jogo.*/
    private final JFrame mainWindow;
    // active indica se o jogo está ativo.
    private boolean active;
    // bufferStrategy nos permite acessar o vídeo de forma mais eficiente.
    private BufferStrategy bufferStrategy;
    //atributo que contro-lá a pausa do jogo
    private boolean pause = false;
    //variavel boleana para controlar os menus que apareceram no frame
    private boolean menu = true;
    //atributo para fechar ou não o jogo
    private int result;
    private final Object[] options = { "Sim", "Não"};  
    private boolean cursor = false;
    //Cria um painel para manipular a posição do mouse
    JPanel p = new JPanel(); 
    //importa a classe que manipula os frames, transformando-a em objeto    
    Frames f = new Frames();
    
    //metodo construtor para manipular a janela a qual o jogo será iniciado.
    public game_methods(){
                
         // Este é o contructor desta clase, onde criamos a janela principal.
        mainWindow = new JFrame("Trash Fall");                
        // Ajustamos as dimensões da janela.
        mainWindow.setSize(800, 600);
        mainWindow.setResizable(false);
        // Cadastramos este metodo (game_method) como ouvinte dos eventos da janela.
        mainWindow.addWindowListener(this);
        // Cadastramos este metodo (game_method) como ouvinte dos eventos de teclado.
        mainWindow.addKeyListener(this);                
        //implementa o JPanel p no Frame mainWindow
        mainWindow.getContentPane().add(p);
        //adiciona o MouseMotionListener no jpainel p
        p.addMouseMotionListener(this);
        //ativa o MouseListener para que o jogo possa identificar os botões do mouse
        p.addMouseListener(this);
        
    }
    
    public void cursor(){
        cursor = changeCursor();
      //se for false o controle do cursor será feito na tela do jogo, caso contrário será feito no menu inicial  
      if(menu == false){
        //codificação para deixar o mouse transparente
        if(pause == true){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("");
        Cursor c = toolkit.createCustomCursor(image , new Point(mainWindow.getX(), 
        mainWindow.getY()), "img");
        mainWindow.setCursor(c);
        //se pause for false, o cursor voltara a ficar visivel
        }else{
        mainWindow.setCursor(Cursor.DEFAULT_CURSOR);
        }
      }else{      
        if(cursor == true){    
        mainWindow.setCursor(Cursor.DEFAULT_CURSOR);
        }else{
        mainWindow.setCursor(Cursor.HAND_CURSOR);        
        }
      }
    }
    
    public void terminate() {
        /* Este método faz com que o jogo termine, atribuindo falso para 
        a variável active;*/
        active = false;
    }
         
    /* Metodo run é o metodo principal que ira pegar todos os outros, 
    e então fará um loop que ficará repetindo até que o jogo termine.*/ 
    public void run(){        
    f.initialize();
       
    active = true;
    load();       
    while(active){
      update();
      render();
      f.onUpdate();
    }
    unload();    
    }
    
    // Método que será implementado nas classes derivadas desta.
    abstract public void onLoad();

    // Método que será implementado nas classes derivadas desta.
    abstract public void onUnload();

    // Método que será implementado nas classes derivadas desta.
    abstract public void onUpdate();
    
    // Método que será implementado nas classes derivadas desta.
    abstract public void onUpdate2();

    // Método que será implementado nas classes derivadas desta.
    abstract public void onRender(Graphics2D g);
    
    // Método que será implementado nas classes derivadas desta.
    abstract public void onRender2(Graphics2D m);
    
    //metodo abstrato responsavel por pausar o jogo
    abstract public boolean pausar();
    
    // método abstrato que troca o formato do cursor quando algo acontece no software
    abstract public boolean changeCursor();
    
    //metodo abstrato responsavel pelo controle de menus
    abstract public boolean menu();
           
    @Override
    public int getWidth() {
        // Retorna a largura da janela.
        return mainWindow.getWidth();
    }

    @Override
    public int getHeight() {
        // Retorna a altura da janela.
        return mainWindow.getHeight();
    }
    
    //void para mostrar uma mensagem antes de fechar o jogo 
    //para o usuario decidir se realmente quer isso
    public boolean CloseMessage(boolean value){
        value = false;
        
        result = JOptionPane.showOptionDialog(null, "Você quer realmente encerrar o jogo?",
                 null , JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
                    
             if(result == JOptionPane.YES_OPTION) value = true;                                   
             
             return value;
    }
    
    //void para mostrar uma mensagem para o usuario decidir se realmente o que quer fazer
    public boolean EndGame(boolean value){             
        result = JOptionPane.showOptionDialog(null, "Você deseja tentar novamente?",
                 "Fim de Jogo" , JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
                    
        if(result == JOptionPane.YES_OPTION) value = true;                                   
        else value = false;            
                                
        return value;
    }
    
    /*onde a carga de recursos (criação de objetos, carga de imagens e sons, etc) 
    deve ser feito.*/
    public void load(){      
        /* Configuramos para ignorar o evento de desenho do sistema, 
        pois sera utilizaado a renderização ativa, ou seja, 
        desenhar por nossa conta.*/
        mainWindow.setIgnoreRepaint(true);
        // Posicionamos a janela a 100 pixels da borda.
        mainWindow.setLocation(270, 80);
        // Mostramos a janela.
        mainWindow.setVisible(true);
        // Criamos a buffer strategy com 2 buffers (double buffer).
        mainWindow.createBufferStrategy(2);
        // Armazenamos a buffer strategy na nossa variável para uso posterior.
        bufferStrategy = mainWindow.getBufferStrategy();
        // chamamos o método onLoad, que será implementado pelas classes derivadas.
        onLoad();
    }
    
    /* Onde a lógica do jogo ocorre. Dizemos que é onde o estado do jogo é atualizado,
    ou seja, verificamos se o jogador morreu, se atingiu um inimigo 
    ou se pressionou a tecla para se mover.*/
    public void update(){      
       //metodo importado da classe frame para atualizar os frames
       f.updateFPS();
       //seta se menu é verdadiro ou falso para indicar qual tela abrirá primeiro
       menu = menu();              
       //método que define o estado do cursor
       cursor();
       //se for falso o menu principal ira aparecer primeiro      
       if(menu == false){
        //pause será igual ao metodo pausar 
        pause = pausar();        
        //quando o frame estiver focado e o pause for igual a true, 
        //o frame irá continuar atualizando as informações dos codigos para rodar o jogo
        if(pause == true && mainWindow.hasFocus()){
          /* Cada vez que update é chamado, chamamos o onUpdate que será
          implementado nas classes derivadas.
          Aqui será atualizada a lógica do jogo.*/
           onUpdate();       
        /* Em seguida chamamos yieald na nossa Thread para permitir a outras
          partes do sistema processarem.*/
           Thread.yield();
        }
       }else onUpdate2();          
    }
    
    //é onde atualizamos os gráficos do jogo para refletir o estado do método update acima
    public void render(){
        /* A cada chamada a render, obtermos um graphics para desenhar.
        ele representa a tela.*/
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        // Especificamos a cor preta.
        g.setColor(Color.black);
        // E desenhamos um retângulo do tamanho da janela (limpamos a tela).
        g.fillRect(0, 0, mainWindow.getWidth(), mainWindow.getHeight());
        /* Chamamos onRender, que será implementado nas classes derivadas.
        nesse método será desenhada a tela do jogo.*/
        if(menu == false) onRender(g);
        else onRender2(g);
        // Liberamos o objeto graphics.
        g.dispose();
        // Pedimos ao buffer strategy para mostrar o que foi desenhado acima.
        bufferStrategy.show();        
    }
    
    //é onde descarregamos os recursos, ou seja é onde tudo que foi alocado será liberado
    public void unload(){
        /* Neste método primeiro chamamos o onUnload, que será implementado 
        pelas classes derivadas.*/
        onUnload();
        // Depois disso, liberamos a buffer strategy.
        bufferStrategy.dispose();
        // liberamos a janela.
        mainWindow.dispose();
        
    }
    
    @Override
    public void windowClosing(WindowEvent e) {
        /* Método chamado no evento de fechar a janela.
        nesse momento chamamos terminate para terminar o jogo.*/        
            if(CloseMessage(true)) terminate();               
            else mainWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    /* Método que precisa ser implementado porque implementamos
     a interface WindowListener, que contem ele.
     nesse caso, não precisamos nenhum código dentro dele.*/
    @Override
    public void windowOpened(WindowEvent e) {
    }

    /* Método que precisa ser implementado porque implementamos
     a interface WindowListener, que contem ele.
     nesse caso, não precisamos nenhum código dentro dele.*/
    @Override
    public void windowClosed(WindowEvent e) {
    }

    /* Método que precisa ser implementado porque implementamos
     a interface WindowListener, que contem ele.
     nesse caso, não precisamos nenhum código dentro dele.*/
    @Override
    public void windowIconified(WindowEvent e) {
    }

    /* Método que precisa ser implementado porque implementamos
     a interface WindowListener, que contem ele.
     nesse caso, não precisamos nenhum código dentro dele.*/
    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    /* Método que precisa ser implementado porque implementamos
     a interface WindowListener, que contem ele.
     nesse caso, não precisamos nenhum código dentro dele.*/
    @Override
    public void windowActivated(WindowEvent e) {
    }

    /* Método que precisa ser implementado porque implementamos
     a interface WindowListener, que contem ele.
     nesse caso, não precisamos nenhum código dentro dele.*/
    @Override
    public void windowDeactivated(WindowEvent e) {
    }    
}
