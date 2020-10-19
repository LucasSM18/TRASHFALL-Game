/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game_methods;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import main.game_methods;

/**
 *
 * @author User
 */
public abstract class MyGame_methods extends game_methods {
    
    //transforma a importação random em objeto
    Random random = new Random();    
    //Transforma a classe score em objeto
    Scores sc = new Scores();        
    //Variáveis necessárias para nosso jogo.
    //Elas armazenam a posição da lata(x,y) que será manipulados pelo mouse
    //e dos lixos que iram cair na tela (ex, ey)
    //sx e sy são responsaveis por fazer uma apresentação do lixo caindo no menu principal
    //maxX, maxY, minX, minY irão pegar a posição minima e maxima das opções
    private int x;
    private int y;
    private int ex = 0;
    private int ey = 0;
    private int sx = 0;
    private int sy = 0;
    private final double maxX[] = new double[3];
    private final double maxY[] = new double[3];
    private final double minX[] = new double[3];
    private final double minY[] = new double[3];
    //vetor para setar qual lixo terá determinada pontuação
    //e variavel inteira para lixos aleatórios cairem e para decidir qual sprite aparecerá
    //variavel string onde vai estar armazenado os sprites
    private int[] sum = new int[5];
    private int randomNumber = random.nextInt(sum.length);
    private String Sprite = null;
    //variavel para acumular e setar pontos
    private int score = 0;
    //variavel para acumular e setar a pontuação maxima
    private String high_score = "0";
    //variavel para cumular vidas
    private int life = 3;
    //Variaveis para manipular fonte de texto e seu conteudo respectivamente 
    private final Font font1;
    private final Font font2;
    private final String[] option = {"COMEÇAR", "AJUDA", "SAIR"};
       
    public MyGame_methods() {
        font1 = new Font("Dialog", Font.PLAIN, 48);
        font2 = new Font("Dialog", Font.PLAIN, 24);
    }
    
    //método abstrato para movimentação vertical
    abstract public int motionX(int x);
    
    //método abstrato para movimentação horizontal
    abstract public int motionY(int y);
       
    //void para controlar o estado do cursor com a posição maxima posição X
    public List<Double> maxX(){
       List<Double> x = new ArrayList<>();
       for(double i : maxX) x.add(i);        
       return x;
    }
    
    //void para controlar o estado do cursor com a posição maxima posição Y
    public List<Double> maxY(){    
        List<Double> y = new ArrayList<>();
        for(double i : maxY) y.add(i);
        return y;      
    }
   
     //void para controlar o estado do cursor com a posição minima posição X
    public List<Double> minX(){    
        List<Double> x = new ArrayList<>();
        for(double i : minX) x.add(i);
        return x;
    }
    
    //void para controlar o estado do cursor com a posição minima posição Y
    public List<Double> minY(){    
        List<Double> y = new ArrayList<>();
        for(double i : minY) y.add(i);
        return y;
    }   
    
    //método para trocar os sprites
    public void Sprites(){
      switch(randomNumber){
            case 0:
                Sprite = "src/Sprites/banana.png";
                break;
            case 1:
                Sprite = "src/Sprites/paper.png";
                break;
            case 2:
                Sprite = "src/Sprites/plastic.png";
                break;
            case 3:
                Sprite = "src/Sprites/Garrafa.png";
                break;
            case 4:
               Sprite = "src/Sprites/Latinha.png";
                break;
            default:
                break;
        }              
     }
    
    //metodo que impede que a bolinha passe para metade superior do frame
    public void barrage(){
      if (y <= 350) y = 340;
    }
    
    //método paara controlar a queda do lixo
    public void Fall(){
        //método para trocar os sprites
        Sprites();
        //se ey for maior que 0, o lixo irá cair 
        if (ey >= 0) ey += 1;        
         //se o lixo chegar no chão, ele voltara no topo e cairá em seguida
        if(ey == getHeight()){            
             ey = 0;
             ex = random.nextInt(getWidth() - 20);
             //se for o lixo organico, o efeito sonoro não ira tocar
             if(randomNumber != 0){ 
             //se for o lixo organico, o efeito sonoro não ira tocar    
             sc.LossGarbage();
             //se for o lixo organico, não perderá vida
             life -= 1;
             }
             randomNumber = random.nextInt(sum.length);
         }    
    }
    
    //Método para a acumulação de pontos quando o lixo cai na lata
    public void ScoreAcumulate(){
           //método para trocar os sprites
           Sprites();
           //condição onde se acumula pontos quando a lata de lixo se aproxima do lixo corespondente
           //e os valores de ex e o ey são modificados para que o lixo caia de novo
           if ((x >= (ex - 18) && x <= (ex + 37)) && (y >= ey && y <= ey + 15)) {
            //variavel para pegar o lenght do array para que a soma da pontuação seja determinada
            score = sc.setscore(score, randomNumber);
            ex = random.nextInt(getWidth());
            ey = 0;
            randomNumber = random.nextInt(sum.length);
         }  
       }         
    
    //método que controla a velocidade que o lixo cai
    public void speed(){
        if(score < 400){
         ey += 0;         
        }else if(score >= 400 && score < 1000){
         ey += 1;         
        }else{
         ey += 2;         
        }    
    }
    
    //método para determinar o que acontece quando uma opção do botão é escolhida
    public void CloseOrNot(){
      if(life < 0){               
         if(EndGame(true)){         
        // se o método for verdadeiro, então ele é chamado, reiniciando o jogo.
        // Aqui damos os valores iniciais para as variáveis.
        x = 400;
        y = 450;
        ex = random.nextInt(getWidth());
        ey = 0;        
        score = 0;        
        //método importado que irá salvar a pontuação maxima
        sc.setHS(); 
        //variavel que irá armazenar a pontuação maxima
        high_score = sc.getHS(high_score);
        //irá resetar o número de vidas
        life = 3;                
         }else{         
         life = 0;
         JOptionPane.showMessageDialog(null, "Obrigado por jogar.\n        " + sc.recorde(high_score));
         terminate();         
         } 
      }
    }
    
    @Override
    public void onLoad() {
        // Este método é chamado quando o jogo é iniciado.
        // Aqui damos os valores iniciais para as variáveis.
        x = 400;
        y = 450;
        ex = random.nextInt(getWidth());
        ey = 0;
        sx = random.nextInt(getWidth());
        sy = 0;
        //variavel que irá armazenar a pontuação maxima
        high_score = sc.getHS(high_score);
    }
    
    @Override
    public void onUnload() {
    //método importado que irá salvar a pontuação maxima
    sc.setHS();
    }
      
    @Override
    public void onUpdate() {
         // Este método é chamado cada vez que a lógica do jogo precisa ser
         // atualizada. Aqui mudamos os valores das variáveis para
         // que o x e y sigam o mouse através do metodo motionX e motionY
         //sx e sy voltarão para posição (0,0) para que o lixo apresentativo do
         //menu pare de cair
         x = motionX(x);
         y = motionY(y);
         sx = 0;
         sy = 0;
                 
         //Método para a acumulação de pontos quando o lixo cai na lata
         ScoreAcumulate();
         //metodo que impede que a bolinha passe para metade superior do frame
         barrage();
         //método que controlá a queda do lixo
         Fall();                  
         //método que controla a velocidade que o lixo cai
         speed();         
         //método para determinar o que acontece quando uma opção do botão é escolhida
         CloseOrNot();
         
    }
    
    //updtate do menu principal para apresentar o lixo caindo no fundo a tela
    @Override
    public void onUpdate2(){
        //método para trocar os sprites no menu
         Sprites();
        //se ey for maior que 0, o lixo irá cair 
        if (sy >= 0) sy += 1;        
        //se o lixo chegar no chão, ele voltara no topo e cairá em seguida
        if(sy == getHeight()){
             sy = 0;
             sx = random.nextInt(getWidth());
             randomNumber = random.nextInt(sum.length);
        }        
    }        

    @Override
    public void onRender(Graphics2D g){
       // Este método é chamado cada vez que é preciso atualizar a imagem e a parte grafica do jogo
       //renderização do jogo em si
       try{
         final BufferedImage image = ImageIO.read(new File("src/Sprites/Lata_Lixo.png").getAbsoluteFile());
         final BufferedImage image2 = ImageIO.read(new File(Sprite));
         g.setColor(Color.white);
         g.drawImage(image2, ex, ey, null);
         g.drawImage(image, x - 20, y + 10, null);
         g.drawString("Score: " + score, 9, 60);
         g.drawString("High Score: " + high_score, 9, 45);
         g.drawString("Life: " + life, 15, 580);
       }catch(Exception e){
       throw new RuntimeException("Ocorreu um erro ao tentar extrair a imagem.");
       }
    }
    
    @Override
    public void onRender2(Graphics2D m) {
        // Este método é chamado cada vez que é preciso atualizar a imagem e a parte grafica do jogo
       //renderização do menu inicial         
        try{
         BufferedImage image = ImageIO.read(new File(Sprite).getAbsoluteFile());
         m.drawImage(image, sx, sy, null);
         }catch(Exception e){
          throw new RuntimeException("Ocorreu um erro ao tentar extrair a imagem.");
         }
         m.setColor(Color.white);
         m.setFont(font1);
         m.drawString("TRASH FALL", getWidth() / 2 - m.getFontMetrics().stringWidth("TRASH FALL") / 2, getHeight() * 1 / 4);
         m.setFont(font2);         
         //laço de repetição onde ira ser criado as 3 opções e formas geometricas para o mouse acessa-las
         //y é uma variavel para controlar o tamanho vertical dos retangulos de cada opção
         int y = 30;
         for(int i = 0; i < option.length; i++){
          if(i != 2) y -= 32;
          else y -= 0;
         //escreve na renderização grafica as opções
          m.drawString(option[i],
          getWidth() / 2 - m.getFontMetrics().stringWidth(option[i]) / 2,
          getHeight() * 3 / 4 + m.getFontMetrics(font2).getHeight() * i);
          //classe transformada em objeto para criar o retangulo de capitação do mouse
          Rectangle2D ret = new Rectangle2D.Float(getWidth() / 2 - m.getFontMetrics().stringWidth(option[i]) / 2,
           430 + m.getFontMetrics(font2).getHeight() * i,
          (getWidth()/2 - (m.getFontMetrics().stringWidth(option[2 - i]))/ 2) - 250 + y, 22);
          //pegar a altura e largura maxima e minima dos retangulos
          maxX[i] = ret.getMaxX() - 4;
          maxY[i] = ret.getMaxY() - 28;
          minX[i] = ret.getMinX() - 3;
          minY[i] = ret.getMinY() - 30;
          }                     
    }
    // Os métodos acima são chamados automaticamente pelo "motor" do jogo que
    // é herdado da classe Game. Abra o arquivo game.java e veja lá o que ocorre
    // quando o programa roda.         
}
