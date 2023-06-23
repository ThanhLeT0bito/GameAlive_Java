/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author GF
 */
public class Gameplay extends JPanel implements KeyListener, ActionListener{
    
    private Player player = new Player(200, 200, 40, 60, Color.red);
    private int playerX = 200; // Tọa độ X của người chơi
    private int playerY = 200; // Tọa độ Y của người chơi
    private List<Bullet> bullets; // Danh sách đạn
    private int HPPLayer = 200;
    private int speedPlayer =10;
    private Timer timer;
    private int delay = 10;
    
    private int backgroundWH = 800;
    
    private int mouseX;
    private int mouseY;
    
    private boolean checkFire = false;
    
    //check click mouse
    private boolean isMousePressed = false;
    
    //temp from class Bullet
    private Bullet bulletTemp;
    
    //create Bot
    private Bot creBot = new Bot(20,20,40,40,Color.green,200);
    private int speedBot = 2;
    private Bot creBot2 = new Bot(600,20,40,40,Color.green,200);
    private int speedBot2 = 3;
    private List<Bullet> bulletBots;//List bullet of Bot
    private Timer TimerBot;
    
    
        ///creBot move tròn
        private int xT = 360; // Tâm x của hình tròn
        private int yT = 360; // Tâm y của hình tròn
        private int radius = 350; // Bán kính hình tròn
        private double angle = 0; // Góc quay của hình tròn
        private Bot creBot3 = new Bot(xT + radius,yT + radius,40,40,Color.green,200);

    
    // position buff hp of player 
    private boolean isHP = true;
    private int BuffHP = 100;
    private int BuffHPX = 150, BuffHPY = 150;
    private boolean showTextHp = false;
    
    ///img
    private Image imagePlayer, imageBot, imageDoubleBullet, imageShield;
    
    
    public Gameplay(){
        
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        addKeyListener(this);
        //addMouseListener(player);
        
         try {
            // Đọc hình ảnh từ tệp tin
                imagePlayer = ImageIO.read(new File("C:\\Users\\GF\\Documents\\NetBeansProjects\\testImportImg\\src\\main\\java\\com\\mycompany\\testimportimg\\spriteNew.png"));
                imageBot = ImageIO.read(new File("C:\\Users\\GF\\Documents\\NetBeansProjects\\testImportImg\\src\\main\\java\\com\\mycompany\\testimportimg\\bot.png"));
                imageDoubleBullet = ImageIO.read(new File("C:\\Users\\GF\\Documents\\NetBeansProjects\\testImportImg\\src\\main\\java\\com\\mycompany\\testimportimg\\doublebullet.png"));
                imageShield = ImageIO.read(new File("C:\\Users\\GF\\Documents\\NetBeansProjects\\testImportImg\\src\\main\\java\\com\\mycompany\\testimportimg\\shield.png"));



                //image = new ImageIcon(getClass().getClassLoader().getResource("images/sprite.png")).getImage();
            } catch (Exception e) {
                System.out.println("helllllllo");
                e.printStackTrace();
                System.out.println("helllllllo");
            
        }
        
        bullets = new ArrayList<>();
        bulletBots = new ArrayList<>();
        //listener click mouse
    this.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
            player.fire(e.getX(), e.getY());
            player.setMouseX(e.getX());
            player.setMouseY(e.getY());
            TimerBot.start();
            
            // Xử lý các thao tác sau khi click chuột
        }
        @Override
        public void mousePressed(MouseEvent e) {
            isMousePressed = true;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            isMousePressed = false;
        }
    });
//         
        timer = new Timer(delay, this);
        timer.start();
        
        ActionListener taskPerformer = (ActionEvent evt) -> {
            if(creBot.getHPBot() >0){
                Bullet bulletBot = new Bullet(creBot.getX(),creBot.getY(),player.getX() + player.getWidth()/2, player.getY() + player.getHeight()/2);
                bulletBots.add(bulletBot);
            }
            if(creBot2.getHPBot() > 0){
                Bullet bulletBot2 = new  Bullet(creBot2.getX(),creBot2.getY(),player.getX() + player.getWidth()/2, player.getY() + player.getHeight()/2);
                bulletBots.add(bulletBot2);
            }
            if(creBot3.getHPBot() > 0){
                Bullet bullet3 = new  Bullet(creBot3.getX(),creBot3.getY(),player.getX() + player.getWidth()/2, player.getY() + player.getHeight()/2);
                bulletBots.add(bullet3);
            }
            
            repaint();
        };
        
        TimerBot = new Timer(2000,taskPerformer);
    }
    
    
    @Override
    public void paint(Graphics g){
        super.paintComponent(g);
        Font font = new Font("Arial", Font.BOLD, 30);
        g.setFont(font);
        
        //background
        g.setColor(Color.WHITE);
        g.fillRect(0,0,backgroundWH,backgroundWH);
        
        //vẽ player có image player
        g.drawImage(imagePlayer, player.getX(), player.getY(),player.getWidth(),player.getHeight(), null);

        //vẽ HP
        if(isHP){
            g.setColor(Color.GREEN);
            g.fillRect(BuffHPX, BuffHPY, 25, 5);
            g.fillRect(BuffHPX + 10, BuffHPY -10, 5, 25);
        }
        //Show text player get HP
        if(showTextHp){
            g.setColor(Color.GREEN);
            g.drawString("+50 HP", 370, 370);
        }
        
          g.drawImage(imageDoubleBullet, 200, 400,50,50, null);
            //vẽ khiên cho player
//            int xShield = (int) (playerX  + 70 * Math.cos(-4*angle));
//            int yShield = (int) (playerY  + 70 * Math.sin(-4*angle));
//            g.drawImage(imageShield, xShield, yShield,100,100, null);
        
        g.setColor(Color.RED);
        g.drawString("HP Mine: "+ player.getHP(), 20, 50);
        
        //Ve BOT
        if(creBot3.getHPBot() >0){
            // Tính toán vị trí x và y dựa vào góc quay và bán kính
            int x = (int) (xT  + radius * Math.cos(angle));
            int y = (int) (yT  + radius * Math.sin(angle));
            creBot3.setX(x);
            creBot3.setY(y);
            // Vẽ hình tròn
            g.setColor(Color.green);
            g.fillOval(x , y, 40, 40);
        }
        
        
        if(creBot.getHPBot() >0){
            //g.setColor(creBot.getColor());
            //g.drawImage(imageBot,creBot.getX(), creBot.getY(),creBot.getWidth(),creBot.getHeight(), null);
            g.drawImage(imageBot,creBot.getX(), creBot.getY(),50,50, null);
        }
        if(creBot2.getHPBot() >0){
            g.setColor(creBot2.getColor());
            //g.fillRect(creBot2.getX(), creBot2.getY(),creBot2.getWidth(),creBot2.getHeight());
            g.drawImage(imageBot,creBot2.getX(), creBot2.getY(),50,50, null);
        }
        
        // Vẽ người chơi dưới dạng hình vuông màu đỏ
        if(player.getHP()>0){
            
            // Vẽ đạn dưới dạng hình vuông màu đen
            if(player.checkFire){
                g.setColor(Color.BLACK);
                
                Graphics2D g2d = (Graphics2D) g;
                AffineTransform transform = new AffineTransform();
                transform.scale(1.0, 1.0); // Chỉnh scale tùy ý (1.0 là không thay đổi)
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                for(int i=0; i< player.getBullets().size(); i++){
                    g2d.setColor(Color.blue);
                    g2d.fillOval((int) player.getBullets().get(i).getX(),(int)player.getBullets().get(i).getY(), 10, 10);
                }
                 for(int i=0; i< bulletBots.size(); i++){
                        g2d.setColor(Color.black);
                        g2d.fillOval((int) bulletBots.get(i).getX(),(int)bulletBots.get(i).getY(), 10, 10);
                    }
                if(creBot.getHPBot() <=0 && creBot2.getHPBot()<= 0 && creBot3.getHPBot() <= 0){
                    g2d.setColor(Color.RED);
                    g2d.drawString("YOU WON !!", 380, 390);
                    TimerBot.stop();
                }
                 

            }
        }
        else {
            g.setColor(Color.RED);
            g.drawString("GAME OVER !!", 370, 370);
            TimerBot.stop();
        }
        
        
        g.dispose();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        //player get HP 
        if (isHP){
            Rectangle rectW = new Rectangle(BuffHPX,BuffHPY,25,5);
            Rectangle rectH = new Rectangle(BuffHPX + 10,BuffHPY - 10,5,25);
            Rectangle rectPlayer = new Rectangle(player.getX(), player.getY(), 50, 50);
            if(rectPlayer.intersects(rectW) || rectPlayer.intersects(rectH)){
                /// player khi did qua hp
                player.PlusHP(50);
                Random random = new Random();
                BuffHPX = 10 + random.nextInt(720);
                BuffHPY = 10 + random.nextInt(720);
                showTextPlayerGetHp();
                showTextHp = true;
                isHP = false;
                delayShowBuffHp();
            }
        }
        //auto move bot
        creBot.moveBot(speedBot, 0);
        if(creBot.getX() >= backgroundWH -creBot.getWidth())
            speedBot = -speedBot;
        if(creBot.getX() <=10)
            speedBot = -speedBot;
        creBot2.moveBot(0, speedBot2);
        if(creBot2.getY()<=10)
            speedBot2 = -speedBot2;
        if(creBot2.getY() >= backgroundWH -creBot2.getWidth())
            speedBot2 = -speedBot2;
        //player
        if(player.checkFire){
            for(int i=0; i< player.getBullets().size(); i++){
                player.getBullets().get(i).update();
                
                Rectangle rect1 = new Rectangle((int)player.getBullets().get(i).getX(),(int) player.getBullets().get(i).getY(), 10, 10);
                Rectangle rect2 = new Rectangle(creBot.getX(), creBot.getY(), creBot.getWidth(), creBot.getHeight());
                Rectangle rect3 = new Rectangle(creBot2.getX(), creBot2.getY(), creBot2.getWidth(), creBot2.getHeight());
                Rectangle rect4 = new Rectangle(creBot3.getX(), creBot3.getY(), creBot3.getWidth(), creBot3.getHeight());

                if(rect1.intersects(rect2)){
                    creBot.dameHP(200);
                    player.getBullets().remove(i);
                    break;
                }
                if(rect1.intersects(rect3)){
                    creBot2.dameHP(200);
                    player.getBullets().remove(i);
                    break;
                }
                if(rect1.intersects(rect4)){
                    creBot3.dameHP(200);
                    player.getBullets().remove(i);
                    break;
                }
                if(player.getBullets().get(i).getX() <= 0 ||
                   player.getBullets().get(i).getY() <= 0 ||
                   player.getBullets().get(i).getX() >=backgroundWH ||
                   player.getBullets().get(i).getY() >=backgroundWH){
                    player.getBullets().remove(i);
                }
            }
        }
        
            for(int i=0; i< bulletBots.size(); i++){
                bulletBots.get(i).update();
                Rectangle rect1 = new Rectangle((int)bulletBots.get(i).getX(),(int) bulletBots.get(i).getY(), 10, 10);
                Rectangle rect2 = new Rectangle(player.getX(), player.getY(), player.getWidth(), player.getHeight());
                if(rect1.intersects(rect2)  && player.getHP() > 0){
                    // giảm hp khi player bị đạn BOT bắn
                    player.PlusHP(-20);
                    bulletBots.remove(i);
                    break;
                }
                if(bulletBots.get(i).getX() <= 0 ||
                   bulletBots.get(i).getY() <= 0 ||
                   bulletBots.get(i).getX() >=backgroundWH ||
                   bulletBots.get(i).getY() >=backgroundWH ){
                   bulletBots.remove(i);
                }
            }
        
        // Cập nhật góc quay
        angle += Math.toRadians(1); // Góc quay tăng 1 độ mỗi lần cập nhật
        repaint();
    }
    
    
    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_A:
                if(player.getX() > 10)
                    player.movePlayer(-speedPlayer, 0); // Di chuyển sang trái
                if(isMousePressed){
                    player.fire(player.getMouseX(), player.getMouseY());
                }
                break;
            case KeyEvent.VK_D:
                if(player.getX() < 720)
                    player.movePlayer(speedPlayer, 0); // Di chuyển sang phải
                if(isMousePressed){
                    player.fire(player.getMouseX(), player.getMouseY());
                }
                break;
            case KeyEvent.VK_W:
                if(player.getY() > 10)
                    player.movePlayer(0, -speedPlayer); // Di chuyển lên trên
                if(isMousePressed){
                    player.fire(player.getMouseX(), player.getMouseY());
                }
                break;
            case KeyEvent.VK_S:
                if(player.getY() < 720)
                    player.movePlayer(0, speedPlayer); // Di chuyển xuống dưới
                if(isMousePressed){
                    player.fire(player.getMouseX(), player.getMouseY());
                }
                break;
            case KeyEvent.VK_SPACE:
                player.setHP(200);
                creBot.setHPBot(200);
                creBot2.setHPBot(200);
                creBot3.setHPBot(200);
                break;
        }
        
    }
    
    private void fire()  {
        checkFire = true;
        bulletTemp = new Bullet(playerX + 25, playerY + 25,mouseX, mouseY);
        bullets.add(bulletTemp);
        bulletTemp = new Bullet(playerX + 25, playerY + 25,mouseX + 70, mouseY + 70);
        bullets.add(bulletTemp);
        TimerBot.start();

    }
    

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
//    private void movePlayer(int dx, int dy) {
//        playerX += dx;
//        playerY += dy;
//        repaint(playerX, playerY,50,50);
//    }
    //show text + 50 Hp
    private void showTextPlayerGetHp(){
        Timer timerShowHp = new Timer(2000, e -> {
            showTextHp = false;
            repaint();
        });
        timerShowHp.setRepeats(false);
        timerShowHp.start();
    }
    
    private void delayShowBuffHp(){
         Timer timerHp = new Timer(5000, e -> {
            isHP = true;
            repaint();
        });
        timerHp.setRepeats(false);
        timerHp.start();
    }
    
    
    
}
