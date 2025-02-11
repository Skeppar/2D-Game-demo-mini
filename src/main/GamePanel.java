package main;
import entity.Entity;
import entity.Player;
import objects.SuperObject;
import tiles.TileManager;
import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // Screen settings
    final int originalTileSize = 16; // 16x16 tiles is the default size. This is standard for many old retro games.
    final int scale = 4; // 16 pixels is too small for a modern day screen, but if you multiply it by 3 it will still look the same but be bigger.
    public final int tileSize = originalTileSize * scale; // 48x48 is not the actual size of a tile.
    final int maxScreenCol = 20;
    final int maxScreenRow = 15;
    final int screenWidth = tileSize * maxScreenCol; // 960 pixels.
    final int screenHeight = tileSize * maxScreenRow; // 720 pixels.

    // World settings - If the col and row are wrong it won't load, make sure that the map size is correct!
    final int maxWorldCol = 50;
    final int maxWorldRow = 50;
    int FPS = 60;

    // System
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler (this); // Instantiate the KeyHandler class and add it to GamePanel so that the GamePanel can recognize the key input.
    Sound music = new Sound();
    Sound se = new Sound();
    CollisionCheck cCheck = new CollisionCheck(this);
    AssetManager aManager = new AssetManager(this);
    public UI ui = new UI(this);
    Thread gameThread; // Thread is something you can start and stop, it will keep the program running. This will make the game run even without the player doing anything.


    // Entity and Object
    Player player = new Player(this, keyH);
    SuperObject[] obj = new SuperObject[10];
    public Entity[] npc = new Entity[10];

    // Game State
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int endState = 4;

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // Drawing the things from this component will be done in an offscreen painting buffer. This can improve the rendering performance.
        this.addKeyListener(keyH);
        this.setFocusable(true); // The GamePanel can be "focused" to receive key input.
    }

    public void setUpGame() {

        aManager.setObject();
        aManager.setNPC();
        gameState = titleState;
    }

    public void startGameThread() {

        gameThread = new Thread(this); // This passes the whole GamePanel class into this Thread.
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS; // Nanoseconds divided by FPS, so this is 1sec/60.
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0; // Used to check FPS
        int drawCount = 0; // Used to check FPS

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval; // Subtract current time from last time to see how much time has passed, then divide and add to delta.
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) { // This 1 is equal to the drawInterval.
                update();
                repaint();
                delta--; // Reset delta.
                drawCount++; // This adds one to the drawCount every time it runs the loop, which should be 60 per second.
            }

            if (timer >= 1000000000) { // Divide with 1b so that we only print this once every second and not every nanosecond.
                System.out.println("FPS: " + drawCount);
                drawCount = 0; // Reset so that it doesn't increase but always shows the frame for the last second.
                timer = 0;
            }
        }
    }

    public void update() {

        if(gameState == playState) {
            // Player
            player.update();

            // NPC
            for (Entity entity : npc) {
                if (entity != null) {
                    entity.update();
                }
            }
        }
        if(gameState == pauseState) {
            //Nothing for now
        }
    }

    public void paintComponent(Graphics g) {
        // paintComponent is a built-in method in Java as a way to draw on JPanel.

        super.paintComponent(g); // Super means the parent class of this class, which is JPanel.
        Graphics2D g2 = (Graphics2D) g;

        // Debug
        long drawStart = 0;
        if(keyH.checkDrawTime) {
            drawStart = System.nanoTime();
        }

        // Title screen
        if (gameState == titleState) {
            ui.draw(g2);
        }   else if (gameState == playState || gameState == dialogueState) {
            // Title
            tileM.draw(g2); // This has to be before player so that the player is on top of the tiles.

            // Object
            for (SuperObject superObject : obj) {
                if (superObject != null) { // Check if the obj is null.
                    superObject.draw(g2, this);
                }
            }
            // NPC
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].draw(g2);
                }
            }

            player.draw(g2);
            ui.draw(g2);
        } else if (gameState == pauseState) {
            tileM.draw(g2);
            player.draw(g2);
            ui.draw(g2);
        } else if (gameState == endState) {
            tileM.draw(g2);
            player.draw(g2);
            ui.draw(g2);
        }


        // Debug
        if(keyH.checkDrawTime) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw time: " + passed, 10, 400);
            System.out.println("Draw time: " + passed);
        }

        g2.dispose(); // Works without but this saves some memory.

    }

    public void playMusic(int i) {
        // Selects file i from the array of all sound files, then plays and loops it.
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        // Sound effect doesn't need to loop.
        se.setFile(i);
        se.play();
    }


    public void resetGame() {
        stopMusic();
        player.reset();
        // Reset UI elements
        ui.gameFinished = false;
        ui.playTime = 0;
        UI.pumpkinsFinal = 0;
        UI.finalScore = 0;

        gameState = playState;
        // Reset all objects
        aManager.setObject();
        aManager.setNPC();
        playMusic(0);
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getMaxWorldCol() {
        return maxWorldCol;
    }

    public int getMaxWorldRow() {
        return maxWorldRow;
    }

    public Player getPlayer() {
        return player;
    }

    public CollisionCheck getcCheck() {
        return cCheck;
    }

    public void setObj(SuperObject[] obj) {
        this.obj = obj;
    }

    public SuperObject[] getObj() {
        return obj;
    }
}
