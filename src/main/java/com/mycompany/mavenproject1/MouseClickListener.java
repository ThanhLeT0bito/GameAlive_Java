/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseClickListener extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX(); // Lấy tọa độ X khi click chuột
        int y = e.getY(); // Lấy tọa độ Y khi click chuột
        System.out.println("Clicked at position: (" + x + ", " + y + ")");
    }
}

