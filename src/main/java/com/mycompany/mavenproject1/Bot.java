/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.Timer;

/**
 *
 * @author GF
 */
public class Bot {
    private int x;
    private int y;
    private int width;
    private int height;
    private int HPBot;
    
    public boolean showTextDecreaseBot = false;

    public Bot(int x, int y, int width, int height, int HPBot) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.HPBot = HPBot;
    }

    public int getHPBot() {
        return HPBot;
    }

    public void setHPBot(int HPBot) {
        this.HPBot = HPBot;
    }
    
    

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void moveBot(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }
    
    public void dameHP(int dame){
        this.HPBot -= dame;
    }
    
    
    public void showTextDecreaseHP(Graphics g){
        g.setColor(Color.RED);
        Font font = new Font("Arial", Font.CENTER_BASELINE, 15);
        g.setFont(font);
        g.drawString("-200", this.getX()+5, this.getY());
        Timer timerDecrease = new Timer(50, e -> {
            this.showTextDecreaseBot = false;
        });
        timerDecrease.setRepeats(false);
        timerDecrease.start();
    }
}
