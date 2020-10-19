/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game_methods;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


/**
 *
 * @author User
 */
public class Scores{
    //variavel para acumular pontos e comparar com o high_score
    private int score = 0;
    //variavel para acumular a pontuação maxima
    private String high_score = "0";
    //variavel para armazenar a localização dos efeitos sonoros
    private String sound;
      
    
    //método para acumular a pontuação
    public int setscore(int score, int sum){         
        //switch case para determinar a pontuação de cada lixo
        switch (sum) {
            case 0:
                score -= 20;                
                break;
            case 1:
                score += 1;
                break;
            case 2:
                score += 5;
                break;
            case 3:
                score += 10;
                break;
            case 4:
                score += 20;
                break;
            default:
                break;
        }
        //condiação para travr a pontuação para não continuar regredindo quando chegar no 0
        if(score < 0) score = 0;        
                
        //condição para indicar em quais situações os efeitos sonoros tocarão
        if(sum != 0) sound = "src/Sons/Points.wav";
        else sound = "src/Sons/LostPoints.wav";
        
        //método para tocar os efeitos sonoros
        sound();
        
        this.score = score;
        return score; 
    }
      
    //método para tocar os efeitos sonoros
    public void sound(){
        try{
         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(sound).getAbsoluteFile());
         Clip clip = AudioSystem.getClip();
         clip.open(audioInputStream);
         clip.start();        
        }catch(Exception e){
        throw new RuntimeException("Ocorreu um erro ao tentar executar o som.");
        }
    }
    
    //método para tocar o efeito sonoro quando o lixo cair no chão
    //auxilia na classe MyGame_methods
    public void LossGarbage(){
      sound = "src/Sons/LostPoints.wav";
      sound();
    }
    
 
    
    //método para mostrar uma mensagem quando o código for batido
    public String recorde(String message){
   
    int hs = Integer.parseInt(high_score);
        
    if(score > hs){
        
    message = "Novo Recorde: " + score;    
    }else{
    message = "";
    }    
    return message;
    }    
          
    //método para pegar a pontuação maxima no bloco de notas  
    public String getHS(String hr){    
         try {            
            //lê o arquivo HS.txt para pegar a pontuação acumulada e atribuila na variavel
            FileReader reader = new FileReader("HS.txt");
            BufferedReader buffer = new BufferedReader(reader);
            String caracter = null;
 
            while ((caracter = buffer.readLine()) != null) {
                high_score = caracter;
                hr = high_score; 
            }
            
            buffer.close();
            
        } catch (IOException e) {
            throw new RuntimeException("Arquivo .txt não encontrado.");
        }          
      return hr;        
    }
    
    //método para setar o novo recorde caso o mesmo seja ultrapassado
    public int setHS(){              
         try {                               
            int hs = Integer.parseInt(high_score);
            
            if(score > hs){             
                FileWriter arq = new FileWriter("HS.txt", false);
                PrintWriter gravarArq = new PrintWriter(arq);
                
                gravarArq.printf("%d", score);
            
                arq.close();                
            }                   
        } catch (IOException e) {
            throw new RuntimeException("Arquivo .txt não encontrado.");
        }          
          return 0;
       }      
  }
