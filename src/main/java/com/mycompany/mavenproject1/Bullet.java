/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author GF
 */
public class Bullet {
        private double x;
        private double y;
        private int targetX; // Tọa độ X của mục tiêu
        private int targetY; // Tọa độ Y của mục tiêu
        private double directionX; // Hướng di chuyển theo trục X
        private double directionY; // Hướng di chuyển theo trục Y
        private float speed = 2; // Tốc độ di chuyển của viên đạn

        public Bullet(double x, double y, int targetX, int targetY, float speed) {
            this.x = x;
            this.y = y;
            this.targetX = targetX;
            this.targetY = targetY;
            this.speed = speed;
            // Tính toán hướng di chuyển
            double distance = Math.sqrt((targetX - x) * (targetX - x) + (targetY - y) * (targetY - y));
            directionX = (targetX - x) / distance;
            directionY = (targetY - y) / distance;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
        
        

        public void move(int dx, int dy) {
            x += dx;
            y += dy;
        }
        
        public void update() {
        x += directionX * speed ;
        y += directionY * speed ;
    }
    
}
