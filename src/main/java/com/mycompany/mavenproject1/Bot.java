/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

import java.awt.Color;

/**
 *
 * @author GF
 */
public class Bot {
    private int x;
    private int y;
    private int width;
    private int height;
    private Color color;
    private int HPBot;

    public Bot(int x, int y, int width, int height, Color color, int HPBot) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
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

    public Color getColor() {
        return color;
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

    public void setColor(Color color) {
        this.color = color;
    }

    public void moveBot(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }
    
    public void dameHP(int dame){
        this.HPBot -= dame;
    }
}
