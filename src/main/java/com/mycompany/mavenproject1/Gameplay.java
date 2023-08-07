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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author GF
 */
public class Gameplay extends JPanel implements KeyListener, ActionListener{
    
    
    
    private Player player = new Player(200, 200, 40, 60, Color.red);
    private List<Bullet> bullets; // Danh sách đạn
    private int HPPLayer = 200;
    private int speedPlayer =10;
    private Timer timer;
    private int delay = 10;
    
    private boolean isPause = false;
    
    
    private int mouseX;
    private int mouseY;
    
    private boolean checkFire = false;
    //check click mouse
    private boolean isMousePressed = false;
    
    
    //create Bot
    private int timeBullet = 3000;
    private int HPBot = 200;
    private int dameBot = 20;
    private Bot creBot = new Bot(20,80,40,40,HPBot);
    private int speedBot = 2;
    private float speedBotBullet = 2;
    private Bot creBot2 = new Bot(600,20,40,40,HPBot);
    private int speedBot2 = 2;
    private List<Bullet> bulletBots;//List bullet of Bot
    private Timer TimerBot;
    private int whBulletBot = 20;
    
    

    ///creBot move tròn
    private int xT = 600 ; // Tâm x của hình tròn
    private int yT = 400 ; // Tâm y của hình tròn
    
    private int radius = 320; // Bán kính hình tròn
    private double angle = 0; // Góc quay của hình tròn
    private Bot creBot3 = new Bot(xT ,yT ,40,40,HPBot);
    private double speedBot3 = 0.5;

    
    // position buff hp of player 
    private boolean isHP = true;
    private int BuffHP = 30;
    private int BuffHPX = 150, BuffHPY = 150;
    private boolean showTextHp = false;
    
    
    // thêm đạn bắn của người chơi (tối đa đươc bắn 3 đạn liền cùng lúc ) width = height = 50
    private boolean isIncreaseBullet = true;
    private int posXIncreaseBullet = 200;
    private int posYIncreaseBullet = 400;

    
    ///hiển thị giảm HP khi trúng đạn
    private boolean showTextDecrease = false;
    
    
    ///img
    private Image imageBackground, imagePlayer, imageBot, imageDoubleBullet, imageShield, imageBot2, imgBotBullet, imgSpiteBullet;
    
    
    //Khởi tạo các lv game
    // LV1: 
    private int LvGame = 1;
    private int maxLevel =0;
    
    // Đọc biến level từ file
    String filePath = "C:\\Users\\GF\\Documents\\NetBeansProjects\\mavenproject1\\src\\main\\java\\com\\mycompany\\mavenproject1\\images\\data.txt";
        
    
    
    public Gameplay(){
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        addKeyListener(this);
        //addMouseListener(player);
        
        readImages();
         
         //khoi tao list bullets
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
        
        //bot tự bắn đạn tới player
        autoBotFire();
        
        timer = new Timer(delay, this);
        timer.start();
        //đọc file data để lấy max level
        readMaxLevel();
    }
    
    
    @Override
    public void paint(Graphics g){
        super.paintComponent(g);
        
        //background
        g.drawImage(imageBackground, 0, 0,getWidth(),getHeight(), this);
        
        Font font = new Font("Arial", Font.BOLD, 25);
        g.setFont(font);
        
        DrawDetailGame(g);
        //vẽ thanh HP của Bots
        drawHPBots(g);
        //Vẽ màn hình khi pausegame
        DrawPauseGame(g);
        //vẽ player có image player
        if(player.getHP()>0)
            g.drawImage(imagePlayer, player.getX(), player.getY(),player.getWidth(),player.getHeight(), null);

        
        // Vẽ item Hp và show hp khi player lấy được
        DrawHPAndShow(g);
        
       
       
        if (player.getLvBullet() < 3 && isIncreaseBullet){
            g.drawImage(imageDoubleBullet, posXIncreaseBullet, posYIncreaseBullet,50,50, null);
        }
        //Ve BOT
        DrawBots(g);
        
        //Hiển thị máu bị trừ của bot khi bị trúng đạn
        showTextDecreaseBot(g);
        
        // Vẽ người chơi 
        if(player.getHP()>0){
            
            // Vẽ đạn 
            if(player.checkFire){
                // Vẽ đạn
                DrawBullet(g);
            }
            
            //Hiển thị máu bị trừ của player khi bị trúng đạn
            if (showTextDecrease){
                g.setColor(Color.RED);
                font = new Font("Arial", Font.CENTER_BASELINE, 15);
                g.setFont(font);
                g.drawString("-"+dameBot, player.getX()+5, player.getY());
            }
        }
        else {
            
            // Vẽ Game over
            DrawGameOver(g);
            
            
        }
        
//                }

        g.dispose();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (!isPause){
            
            //player get HP 
            checkPlayerGetHP();

            // Increase Bullet
            IncreaseBulletPLayer();

            //auto move bot
            AutoMoveBot();
            
            //player intersects 
            CheckIntersectsOfPLayer();

            //Bot intersects
            CheckInterSectsOfBot();


            // Update level when bot die
            if (creBot.getHPBot() <= 0 && creBot2.getHPBot() <= 0 && creBot3.getHPBot() <= 0){
                //update Game 
                updateGamePlay();
            }

            repaint();
        }

    }
    
    
    @Override
    public void keyTyped(KeyEvent e) {
            char keyChar = e.getKeyChar();
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
                if(player.getX() < getWidth() - 50)
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
                if(player.getY() < getHeight() - 50)
                    player.movePlayer(0, speedPlayer); // Di chuyển xuống dưới
                if(isMousePressed){
                    player.fire(player.getMouseX(), player.getMouseY());
                }
                break;
            case KeyEvent.VK_SPACE:
                if(player.getHP()<=0)
                    break;
                isPause = !isPause;
                PauseGame();
                break;
            case KeyEvent.VK_ENTER:
                player.setHP(200);
                TimerBot.start();
                timer.start();
                readMaxLevel();
                resetGame();
                break;
        }
        
    }
    

    @Override
    public void keyReleased(KeyEvent e) {
        }
    
    // Kiểm tra va cham player với các vật khác
    private boolean checkIntersects(Rectangle rect){
        Rectangle rectPlayer = new Rectangle(player.getX(), player.getY(), player.getWidth(), player.getHeight());
        if(rectPlayer.intersects(rect)){
            return true;
        }
        
        return false;
    }


    
    //show text + BuffHP Hp
    private void showTextPlayerGetHp(){
        Timer timerShowHp = new Timer(1000, e -> {
            showTextHp = false;
            repaint();
        });
        timerShowHp.setRepeats(false);
        timerShowHp.start();
    }
    private void showTextDecreaseHP(){
        Timer timerDecrease = new Timer(50, e -> {
            showTextDecrease = false;
            repaint();
        });
        timerDecrease.setRepeats(false);
        timerDecrease.start();
    }
    
    private void delayShowBuffHp(){
         Timer timerHp = new Timer(5000, e -> {
            isHP = true;
            repaint();
        });
        timerHp.setRepeats(false);
        timerHp.start();
    }
    
    private void delayIncreaseBullet(){
         Timer timerBullet = new Timer(5000, e -> {
            isIncreaseBullet = true;
            repaint();
        });
        timerBullet.setRepeats(false);
        timerBullet.start();
    }
    
    
    private void PauseGame(){
        repaint();
        if(isPause){
            timer.stop();
            TimerBot.stop();
        }else {
            timer.start();
            TimerBot.start();
        }
    }
    
    private void readMaxLevel(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String levelString = reader.readLine(); // Đọc dữ liệu từ file (dạng chuỗi)
            int readLevel = Integer.parseInt(levelString); // Chuyển chuỗi thành số nguyên
            maxLevel = readLevel;
            reader.close();
            System.out.println("Read level from the file: " + readLevel);
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
    
    private void writeMaxLevel(){
        try {
                if(LvGame > maxLevel){
                    BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
                    writer.write(String.valueOf(LvGame)); // Ghi giá trị của biến level vào file
                    writer.close();
                    System.out.println("Level has been saved to the file successfully.");
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    
    private void autoBotFire(){
        ActionListener taskPerformer = (ActionEvent evt) -> {
            if(creBot.getHPBot() >0){
                Bullet bulletBot = new Bullet(creBot.getX(),creBot.getY(),player.getX() + player.getWidth()/2, player.getY() + player.getHeight()/2, speedBotBullet);
                bulletBots.add(bulletBot);
            }
            if(creBot2.getHPBot() > 0){
                Bullet bulletBot2 = new  Bullet(creBot2.getX(),creBot2.getY(),player.getX() + player.getWidth()/2, player.getY() + player.getHeight()/2,speedBotBullet);
                bulletBots.add(bulletBot2);
            }
            if(creBot3.getHPBot() > 0){
                Bullet bullet3 = new  Bullet(creBot3.getX(),creBot3.getY(),player.getX() + player.getWidth()/2, player.getY() + player.getHeight()/2, speedBotBullet);
                bulletBots.add(bullet3);
            }
            
            repaint();
        };
        TimerBot = new Timer(timeBullet,taskPerformer);
    }
    
    private void resetGame(){
        LvGame = 1;
        HPBot = 200;
        player.setHP(200);
        creBot.setHPBot(200);
        creBot2.setHPBot(200);
        creBot3.setHPBot(200);
        speedBot = 2;
        speedBot2 = 2;
        speedBot3 = 0.5;
        speedBotBullet = 1;
        dameBot = 20;
        player.setLvBullet(1);
        timeBullet = 3000;
        timer.start();
        TimerBot.start();
        player.getBullets().clear();
        bulletBots.clear();
    }

    private void readImages() {
        try {
            // Đọc hình ảnh từ tệp tin
                imagePlayer = ImageIO.read(new File("C:\\Users\\GF\\Documents\\NetBeansProjects\\mavenproject1\\src\\main\\java\\com\\mycompany\\mavenproject1\\images\\spriteNew.png"));
                imageBot = ImageIO.read(new File("C:\\Users\\GF\\Documents\\NetBeansProjects\\mavenproject1\\src\\main\\java\\com\\mycompany\\mavenproject1\\images\\bot3.png"));
                imageBot2 = ImageIO.read(new File("C:\\Users\\GF\\Documents\\NetBeansProjects\\mavenproject1\\src\\main\\java\\com\\mycompany\\mavenproject1\\images\\bot2.png"));
                imageDoubleBullet = ImageIO.read(new File("C:\\Users\\GF\\Documents\\NetBeansProjects\\mavenproject1\\src\\main\\java\\com\\mycompany\\mavenproject1\\images\\doublebullet.png"));
                imageShield = ImageIO.read(new File("C:\\Users\\GF\\Documents\\NetBeansProjects\\mavenproject1\\src\\main\\java\\com\\mycompany\\mavenproject1\\images\\shield.png"));
                imageBackground = ImageIO.read(new File("C:\\Users\\GF\\Documents\\NetBeansProjects\\mavenproject1\\src\\main\\java\\com\\mycompany\\mavenproject1\\images\\background.jpg"));
                imgBotBullet = ImageIO.read(new File("C:\\Users\\GF\\Documents\\NetBeansProjects\\mavenproject1\\src\\main\\java\\com\\mycompany\\mavenproject1\\images\\bot_bullet.png"));
                imgSpiteBullet = ImageIO.read(new File("C:\\Users\\GF\\Documents\\NetBeansProjects\\mavenproject1\\src\\main\\java\\com\\mycompany\\mavenproject1\\images\\sprite_bullet.png"));                
                //image = new ImageIcon(getClass().getClassLoader().getResource("images/sprite.png")).getImage();
            } catch (Exception e) {
                e.printStackTrace();
            
        }
    }

    private void updateGamePlay() {
        changeRandomBot(creBot);
        changeRandomBot(creBot2);
        LvGame += 1;
        if(speedBot3 < 3)
           speedBot3 += 0.1;
       //increase HP when player get
       if (BuffHP <= 100)
           BuffHP+=5;
       if (LvGame % 2 == 1 && Math.abs(speedBot) <= 10){
           if (speedBot >= 0)
               speedBot += 1;
           else
               speedBot -= 1;
       }
       if (LvGame % 2 == 1 && Math.abs(speedBot2) <= 10){
           if (speedBot2 >= 0)
               speedBot2 += 1;
           else
               speedBot2 -= 1;
       }
       if (timeBullet >= 1200)
           timeBullet -= 100;
       if (speedBotBullet < 5)
           speedBotBullet += 1;
        dameBot+=5;
        HPBot += 200;
        creBot.setHPBot(HPBot);
        creBot2.setHPBot(HPBot);
        creBot3.setHPBot(HPBot);    
    }
    
    //random x y
    private void changeRandomBot(Bot bot){
        Random random = new Random();
        bot.setX(random.nextInt(50+getWidth()- 150));
        bot.setY(random.nextInt(50+getHeight()- 150));
    }

    private void AutoMoveBot() {
        if(creBot.getHPBot()>0)
            creBot.moveBot(speedBot, 0);
        if(creBot.getX() >= getWidth() -creBot.getWidth())
            speedBot = -speedBot;
        if(creBot.getX() <= creBot.getWidth())
            speedBot = Math.abs(speedBot);
        if(creBot2.getHPBot()>0)
            creBot2.moveBot(0, speedBot2);
        if(creBot2.getY()<= creBot2.getHeight())
            speedBot2 = Math.abs(speedBot2);
        if(creBot2.getY() >= getHeight() -creBot2.getWidth())
            speedBot2 = -speedBot2;
        
        // Cập nhật góc quay cua creBot3
        if(creBot3.getHPBot()>0)
            angle += Math.toRadians(speedBot3); // Góc quay tăng 1 độ mỗi lần cập nhật
    }

    private void showTextDecreaseBot(Graphics g) {
        if(creBot.showTextDecreaseBot)
            creBot.showTextDecreaseHP(g);
        if(creBot2.showTextDecreaseBot)
            creBot2.showTextDecreaseHP(g);
        if(creBot3.showTextDecreaseBot)
            creBot3.showTextDecreaseHP(g);
    }

    private void CheckIntersectsOfPLayer() {
        //player
        if(player.checkFire){
            for(int i=0; i< player.getBullets().size(); i++){
                player.getBullets().get(i).update();
                
                Rectangle rect1 = new Rectangle((int)player.getBullets().get(i).getX(),(int) player.getBullets().get(i).getY(), 15, 15);
                Rectangle rect2 = new Rectangle(creBot.getX(), creBot.getY(), creBot.getWidth(), creBot.getHeight());
                Rectangle rect3 = new Rectangle(creBot2.getX(), creBot2.getY(), creBot2.getWidth(), creBot2.getHeight());
                Rectangle rect4 = new Rectangle(creBot3.getX(), creBot3.getY(), creBot3.getWidth(), creBot3.getHeight());

                if(rect1.intersects(rect2) && creBot.getHPBot() > 0){
                    creBot.showTextDecreaseBot = true;
                    creBot.dameHP(200);
                    player.getBullets().remove(i);
                    break;
                } 
                if(rect1.intersects(rect3) && creBot2.getHPBot() > 0){
                    creBot2.showTextDecreaseBot = true;
                    creBot2.dameHP(200);
                    player.getBullets().remove(i);
                    break;
                }
                if(rect1.intersects(rect4) && creBot3.getHPBot() > 0){
                    creBot3.showTextDecreaseBot = true;
                    creBot3.dameHP(200);
                    player.getBullets().remove(i);
                    break;
                }
                if(player.getBullets().get(i).getX() <= 0 ||
                   player.getBullets().get(i).getY() <= 0 ||
                   player.getBullets().get(i).getX() >= getWidth() ||
                   player.getBullets().get(i).getY() >= getHeight()){
                    player.getBullets().remove(i);
                }
            }
        }
    }

    private void CheckInterSectsOfBot() {
        for(int i=0; i< bulletBots.size(); i++){
                bulletBots.get(i).update();
                Rectangle rect1 = new Rectangle((int)bulletBots.get(i).getX(),(int) bulletBots.get(i).getY(), whBulletBot, whBulletBot);
                if(checkIntersects(rect1)  && player.getHP() > 0){
                    // giảm hp khi player bị đạn BOT bắn
                    player.PlusHP(-dameBot);
                    bulletBots.remove(i);
                    showTextDecreaseHP();
                    showTextDecrease = true;
                    break;
                }
                if(bulletBots.get(i).getX() <= 0 ||
                   bulletBots.get(i).getY() <= 0 ||
                   bulletBots.get(i).getX() >= getWidth() ||
                   bulletBots.get(i).getY() >= getHeight() ){
                   bulletBots.remove(i);
                }
            }
    }

    private void checkPlayerGetHP() {
        if (isHP){
            Rectangle rectW = new Rectangle(BuffHPX,BuffHPY,25,5);
            Rectangle rectH = new Rectangle(BuffHPX + 10,BuffHPY - 10,5,25);
            if(checkIntersects(rectW) || checkIntersects(rectH)){
                /// player khi them hp
                player.PlusHP(BuffHP);
                Random random = new Random();
                BuffHPX = 10 + random.nextInt(getWidth()- 100);
                BuffHPY = 10 + random.nextInt(getHeight() - 100);
                showTextPlayerGetHp();
                showTextHp = true;
                isHP = false;
                delayShowBuffHp();
            }
        }
    }

    private void IncreaseBulletPLayer() {
        if (player.getLvBullet() < 3 && isIncreaseBullet){
            Rectangle rectIncreaseBullet = new Rectangle(posXIncreaseBullet,posYIncreaseBullet, 50, 50);
            if (checkIntersects(rectIncreaseBullet)){
                int temp = player.getLvBullet() + 1;
                player.setLvBullet(temp);
                delayIncreaseBullet();
                isIncreaseBullet = false;
                Random random = new Random();
                posXIncreaseBullet = 10 + random.nextInt(720);
                posYIncreaseBullet = 10 + random.nextInt(720);
            }
        }
    }

    private void drawHPBots(Graphics g) {
        //creBot
        g.setColor(Color.red);
        g.fillRect(getWidth()- 250, 20, 200, 7);
        g.setColor(Color.GREEN);
        if (creBot.getHPBot() > 0){
            double percentCreBot = 200 / ((double)HPBot / (double)creBot.getHPBot());
            g.fillRect(getWidth()- 250, 20, (int)percentCreBot +1, 7);
        } else {
            g.fillRect(getWidth()- 250, 20, 0, 7);
        }
        
        //creBot2
        g.setColor(Color.red);
        g.fillRect(getWidth()- 250, 30, 200, 7);
        g.setColor(Color.GREEN);
        if (creBot2.getHPBot() > 0){
            double percentCreBot2 = 200 / ((double)HPBot / (double)creBot2.getHPBot());
            g.fillRect(getWidth()- 250, 30, (int)percentCreBot2 +1, 7);
        } else {
            g.fillRect(getWidth()- 250, 30, 0, 7);
        }
        
        //creBot3
        g.setColor(Color.red);
        g.fillRect(getWidth()- 250, 40, 200, 7);
        g.setColor(Color.GREEN);
        if (creBot3.getHPBot() > 0){
            double percentCreBot3 = 200 / ((double)HPBot / (double)creBot3.getHPBot());
            g.fillRect(getWidth()- 250, 40, (int)percentCreBot3 +1, 7);
        } else {
            g.fillRect(getWidth()- 250, 40, 0, 7);
        }
        
    }

    private void DrawBots(Graphics g) {
        if(creBot3.getHPBot() >0){
            // Tính toán vị trí x và y dựa vào góc quay và bán kính
            int x = (int) (xT  + radius * Math.cos(angle));
            int y = (int) (yT  + radius * Math.sin(angle));
            creBot3.setX(x);
            creBot3.setY(y);
            g.drawImage(imageBot2,creBot3.getX(), creBot3.getY(),40,40, null);
        }
        
        
        if(creBot.getHPBot() >0){
            g.drawImage(imageBot,creBot.getX(), creBot.getY(),creBot.getWidth(),creBot.getHeight(), null);
        }
        if(creBot2.getHPBot() >0){
            g.drawImage(imageBot,creBot2.getX(), creBot2.getY(),50,50, null);
        }
    }

    private void DrawHPAndShow(Graphics g) {
        //vẽ HP item
        if(isHP){
            g.setColor(Color.GREEN);
            g.fillRect(BuffHPX, BuffHPY, 25, 5);
            g.fillRect(BuffHPX + 10, BuffHPY -10, 5, 25);
        }
        
        //Show text player get HP
        if(showTextHp){
            g.setColor(Color.GREEN);
            g.drawString("+"+BuffHP +" HP", getWidth() / 2, getHeight() / 2);
        }
    }

    private void DrawDetailGame(Graphics g) {
        g.setColor(Color.RED);
        g.drawString("HP Player  "+ player.getHP(), 20, 50);
        g.setColor(Color.RED);
        g.fillRect(20, 70, 200, 10);
        g.setColor(Color.GREEN);
        g.fillRect(20, 70, player.getHP(), 10);
        
        g.drawString("Lv:" + LvGame, 270, 50);
        
        g.setColor(Color.RED);
        g.drawString("MaxLv: " + maxLevel, 370, 50);
    }

    private void DrawPauseGame(Graphics g) {
        if (isPause){
            g.setColor(Color.RED);
            g.drawString("Game Paused", getWidth() / 2 - 50, getHeight() / 2);
            Font font = new Font("Arial", Font.BOLD, 15 );
            g.setFont(font);
            g.setColor(Color.WHITE);
            g.drawString("Click space to continous", getWidth() / 2 - 60, getHeight() / 2 + 30);
        }
    }

    private void DrawBullet(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
                AffineTransform transform = new AffineTransform();
                transform.scale(1.0, 1.0); // Chỉnh scale tùy ý (1.0 là không thay đổi)
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                for(int i=0; i< player.getBullets().size(); i++){
                    g2d.drawImage(imgSpiteBullet,(int) player.getBullets().get(i).getX(),(int)player.getBullets().get(i).getY(), 15, 15, this);
                }
                for(int i=0; i< bulletBots.size(); i++){
                        g2d.drawImage(imgBotBullet, (int) bulletBots.get(i).getX(), (int) bulletBots.get(i).getY(), whBulletBot, whBulletBot, this);
                }
                if(creBot.getHPBot() <=0 && creBot2.getHPBot()<= 0 && creBot3.getHPBot() <= 0){
                    g2d.setColor(Color.RED);
                    g2d.drawString("YOU WON !!", 380, 390);
                    TimerBot.stop();
                }
    }

    private void DrawGameOver(Graphics g) {
        g.setColor(Color.RED);
        g.drawString("GAME OVER !!", getWidth()/2 - 100, getHeight()/2);
        g.setColor(Color.WHITE);
        Font font = new Font("Arial", Font.CENTER_BASELINE, 15);
        g.setFont(font);
        g.drawString("Click enter to Replay !!", getWidth()/2 - 100, getHeight()/2 + 30);
        TimerBot.stop();
        timer.stop();
        writeMaxLevel();     
    }
    

}
