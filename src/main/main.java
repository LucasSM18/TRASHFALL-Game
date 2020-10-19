/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import game_methods.Control;

/**
 *
 * @author User
 */
public class main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // A execução do programa Java começa aqui, no método estático main.
        // Tudo o que fazemos aqui é criar um objeto do tipo game_methods e 
        // colocar ele para rodar.
        game_methods myGame = new Control();
        myGame.run();
        // Quando o jogo terminar de rodar, chegaremos aqui e o programa termina.        
    }
    
}
