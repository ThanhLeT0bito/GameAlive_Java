/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author GF
 */
public class Player  {
    private int x;
    private int y;
    private int width;
    private int height;
    private Color color;
    private int HP;
    private List<Bullet> bullets;
    private int lvBullet;
    public boolean checkFire = true;
    private int mouseX;
    private int mouseY;
    private float speedBullet = 2;
    //check click mouse
    public boolean isMousePressed = false;
    private int speedPlayer = 10;
    private boolean flip = true;
    
    public Player(){}
    
    public Player(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.bullets = new ArrayList<>();
        this.HP = 200;
        this.checkFire = false;
        lvBullet = 1;
    }
    
    ///// FUNCTION
     public void movePlayer(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }
    
    public void fire(int mouseX, int mouseY){
       this.checkFire = true;
       Bullet bulletTemp = new Bullet(this.x + this.width / 2, this.y + this.height / 2,mouseX, mouseY, speedBullet);
       this.bullets.add(bulletTemp);
       if (lvBullet > 1){
           double angle = Math.toRadians(30); // Góc 45 độ
            double dx = mouseX - x; // Khoảng cách theo trục X
            double dy = mouseY - y; // Khoảng cách theo trục Y

            // Tính toán tọa độ đích mới
            double destinationX = x + Math.cos(angle) * dx - Math.sin(angle) * dy;
            double destinationY = y + Math.sin(angle) * dx + Math.cos(angle) * dy;
           bulletTemp = new Bullet(this.x + this.width / 2, this.y + this.height / 2,(int) destinationX, (int) destinationY, speedBullet );
           this.bullets.add(bulletTemp);
       }
       if (lvBullet > 2){
           double angle = Math.toRadians(-30); // Góc 45 độ
            double dx = mouseX - x; // Khoảng cách theo trục X
            double dy = mouseY - y; // Khoảng cách theo trục Y

            // Tính toán tọa độ đích mới
            double destinationX = x + Math.cos(angle) * dx - Math.sin(angle) * dy;
            double destinationY = y + Math.sin(angle) * dx + Math.cos(angle) * dy;
           bulletTemp = new Bullet(this.x + this.width / 2, this.y + this.height / 2,(int) destinationX, (int) destinationY, speedBullet );
           this.bullets.add(bulletTemp);
       }
    }
    public void PlusHP( int hp){
        this.HP += hp;
        if (this.HP > 200)
            this.HP = 200;
        if (this.HP <0)
            this.HP = 0;
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

    public List<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(List<Bullet> bullets) {
        this.bullets = bullets;
    }

    public int getSpeedPlayer() {
        return speedPlayer;
    }

    public void setSpeedPlayer(int speedPlayer) {
        this.speedPlayer = speedPlayer;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getMouseX() {
        return mouseX;
    }

    public void setMouseX(int mouseX) {
        this.mouseX = mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public void setMouseY(int mouseY) {
        this.mouseY = mouseY;
    }

    public int getLvBullet() {
        return lvBullet;
    }

    public void setLvBullet(int lvBullet) {
        this.lvBullet = lvBullet;
    }
    
    
    
    
}
