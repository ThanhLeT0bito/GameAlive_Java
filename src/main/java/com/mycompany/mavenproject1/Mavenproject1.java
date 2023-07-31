/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mavenproject1;

import javax.swing.JFrame;
/**
 *
 * @author GF
 */


public class Mavenproject1 {

    public static void main(String[] args) {
        JFrame obj = new JFrame();
//        obj.pack();
        Gameplay gameplay = new Gameplay();
        obj.setBounds(10,10,1200,800);   
        obj.setTitle("Alive");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gameplay);
//        obj.addMouseLintener(gameplay);
        
        //add listener click
//        MouseClickListener mouseClickListener = new MouseClickListener();
//        obj.addMouseListener(mouseClickListener);
    }
      
}
