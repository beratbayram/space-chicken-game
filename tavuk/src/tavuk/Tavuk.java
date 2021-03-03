/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tavuk;

import javax.swing.JFrame;

/**
 *
 * @author berat
 */
public class Tavuk {

    public Tavuk() {}

    public static void main(String[] args) {
        
        JFrame obj = new JFrame("Chicken World by Berat and Ece");
        GamePlay gamePlay = new GamePlay();
        obj.setBounds(10, 10, 300, 600);
        obj.setName("Chicken World by Berat and Ece");
        obj.setVisible(true);
        obj.setResizable(false);
        obj.setDefaultCloseOperation(3);
        obj.add(gamePlay);
        
        
    }

}