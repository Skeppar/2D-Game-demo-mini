package main;
import entity.Entity;
import objects.OBJ_Carrot;
import objects.OBJ_Pumpkin;
import objects.SuperObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import static java.lang.Math.log;
import static java.lang.Math.round;

public class UI {

    // This class will handle all the UI like text, item icons etc.
    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B, maruMonica, bellmore;
    BufferedImage pumpkinImage;
    BufferedImage carrotImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    double playTime;

    // Final score
    public static int pumpkinsFinal = 0;
    public static double finalScore = 0;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    // Final score
    public void updatePumpkins(int pumpkins) {
        pumpkinsFinal = pumpkins; // Store the current key count in a global variable
    }

    public UI(GamePanel gp) {

        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        OBJ_Pumpkin pumpkin = new OBJ_Pumpkin(gp);
        pumpkinImage = pumpkin.getImage();
        OBJ_Carrot carrot = new OBJ_Carrot(gp);
        carrotImage = carrot.getImage();

        try {
            // Unsupported sfnt means the font is not a TrueType (ttf) font. Even if the file name says ttf it may not be correct.
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            assert is != null;
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/BellmoreFree-1GB2M.ttf"); // Some characters don't work properly.
            bellmore = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showMessage(String text) {

        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;

        g2.setFont(maruMonica);
        g2.setColor(Color.white);

        // Title state
        if(gp.gameState == gp.titleState) {
            drawTitleScreen();
        }

        // Play state
        if(gp.gameState == gp.playState) {
            // g2.setFont(new Font("Arial", Font.PLAIN, 40)); // This works but since it will render this 60 times per second next line is more efficient.
            g2.setFont(arial_40);
            g2.setColor(Color.white);
            g2.drawImage(pumpkinImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
            g2.drawString("  x  " + gp.player.getHasPumpkin(), 95, 70);
            playTime +=(double)1/60;
        }
        // Pause state
        if(gp.gameState == gp.pauseState) {
            drawPauseScreen();
            gp.stopMusic();
        }
        // Dialogue state
        if (gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
        }

        if (gp.gameState == gp.endState) {
            drawEndScreen();
            gp.stopMusic();
        }
    }

    public void drawEndScreen() {
        gp.music.stop();
        g2.setColor(new Color(245, 123, 36, 255));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);


        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 120F));
        String titleName = "Congratulations!";
        int x = getXForCenteredText(titleName);
        int y = gp.tileSize * 3;

        g2.setColor(new Color (20, 66, 11, 255));
        g2.drawString(titleName, x + 8, y + 8);

        g2.setColor(new Color (34, 121, 17, 255));
        g2.drawString(titleName, x, y);

        x = gp.screenWidth / 2 - (gp.tileSize*2)/2;
        y = gp.tileSize * 5;
        g2.drawImage(gp.player.still1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

        // Menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
        String start = "Play again";
        x = getXForCenteredText(start);
        y += gp.tileSize * 4;
        g2.drawString(start, x, y);
        if(commandNum == 0) {
            g2.drawImage(carrotImage, (int) (x - gp.tileSize*1.2), (int) (y - gp.tileSize*0.8), 64, 64, null);
        }

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
        String quit = "Quit Game";
        x = getXForCenteredText(quit);
        y += gp.tileSize * 1.1;
        g2.drawString(quit, x, y);
        if(commandNum == 1) {
            g2.drawImage(carrotImage, (int) (x - gp.tileSize*1.2), (int) (y - gp.tileSize*0.8), 64, 64, null);
        }

        updateFinalScore();

        g2.setFont(arial_40);
        g2.setColor(Color.white);

        String text;
        int textLength;
        int i;
        int z;

        g2.setColor(new Color (34, 121, 17, 255));
        String finalScoreFormatted = String.format("%.2f", finalScore);
        text = "Your final score is: " + finalScoreFormatted;
        textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        i = gp.screenWidth/2 - textLength/2;
        z = gp.screenHeight/2 + gp.tileSize*4;
        g2.drawString(text, i, z);

        text = "Your managed to reach the finish line in : " + dFormat.format(playTime) + " seconds.";
        textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        i = gp.screenWidth/2 - textLength/2;
        z = gp.screenHeight/2 + gp.tileSize*5;
        g2.drawString(text, i, z);

        text = "And collected a total of " + pumpkinsFinal + " pumpkins, good job!";
        textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        i = gp.screenWidth/2 - textLength/2;
        z = gp.screenHeight/2 + gp.tileSize*6;
        g2.drawString(text, i, z);
    }

    public void drawTitleScreen() {

        // Background color
        g2.setColor(new Color(245, 123, 36, 255));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Title name
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 120F));
        String titleName = "The Fast and the Fluffy";
        int x = getXForCenteredText(titleName);
        int y = gp.tileSize * 3;

        // Shadow for title text
        g2.setColor(new Color (20, 66, 11, 255));
        g2.drawString(titleName, x + 8, y + 8);

        // Title Color
        g2.setColor(new Color (34, 121, 17, 255));
        g2.drawString(titleName, x, y);

        // Display character image
        x = gp.screenWidth / 2 - (gp.tileSize*2)/2; // * 2 since the character is double it's size, and divide by to make sure it's in the middle and the left side doesn't start in the middle.
        y = gp.tileSize * 5;
        g2.drawImage(gp.player.still1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

        // Menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
        String start = "New Game";
        x = getXForCenteredText(start);
        y += gp.tileSize * 4;
        g2.drawString(start, x, y);
        if(commandNum == 0) {
            g2.drawImage(carrotImage, (int) (x - gp.tileSize*1.2), (int) (y - gp.tileSize*0.8), 64, 64, null);
        }

        /*
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
        String load = "Lead Board";
        x = getXForCenteredText(load);
        y += gp.tileSize * 1.1;
        g2.drawString(load, x, y);
        if(commandNum == 1) {
            g2.drawString(">", x - gp.tileSize, y); // Character instead of image
        }
        */

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
        String quit = "Quit Game";
        x = getXForCenteredText(quit);
        y += gp.tileSize * 1.1;
        g2.drawString(quit, x, y);
        if(commandNum == 1) {
            g2.drawImage(carrotImage, (int) (x - gp.tileSize*1.2), (int) (y - gp.tileSize*0.8), 64, 64, null);
        }

        g2.setColor(new Color (176, 0, 0, 255));
        String text;
        int textLength;
        int i;
        int z;
        text = "Find as many tasty treats as you can and reach the finish line";
        textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        i = gp.screenWidth/2 - textLength/2;
        z = gp.screenHeight/2 + gp.tileSize*6;
        g2.drawString(text, i, z);

    }

    public void updateFinalScore() {
        gameFinished = true;
        gp.gameState = gp.endState;

        int baseTime = 60;
        int basePumpkins = 10;
        double kConst = 0.01;

        finalScore = pumpkinsFinal * (baseTime/playTime) * (1 + (pumpkinsFinal - basePumpkins) * kConst) * log(playTime + 1); //
    }
    public void drawPauseScreen() {

        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        g2.setColor(Color.white);
        String text = "PAUSED";
        int x = getXForCenteredText(text);
        int y = gp.screenHeight/2;
        g2.drawString(text, x, y);
    }

    public void drawDialogueScreen() {

        // Dialogue window
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 3);
        int height = gp.tileSize * 3;

        if(gp.gameState == gp.dialogueState) {
            drawPlayStateScreen();
        }

        drawSubWindow(x, y, width, height);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 42F));

        String[] lines = currentDialogue.split("\n");

        // Calculate y position to center text vertically
        int textY = y + (height - (lines.length * 40)) / 2 + 40; // 40 is line height

        for (String line : lines) {
            // Calculate x position to center this line horizontally
            int textX = x + (width - (int)g2.getFontMetrics().getStringBounds(line, g2).getWidth()) / 2;
            g2.drawString(line, textX, textY);
            textY += 40;
        }
    }

    private void drawPlayStateScreen() {

        gp.tileM.draw(g2);

        for (SuperObject superObject : gp.getObj()) {
            if (superObject != null) {
                superObject.draw(g2, gp);
            }
        }

        for (Entity entity : gp.npc) {
            if (entity != null) {
                entity.draw(g2);
            }
        }

        gp.getPlayer().draw(g2);
    }

    public void drawSubWindow(int x, int y, int width, int height) {

        Color c = new Color(0, 0, 0, 200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 30, 30); // Rounds the edges of the rectangle.

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5)); // This defines the width of the outlines of graphics rendered with Graphics2D.
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25); // Since this rectangle is smaller the arc W/H has to be a bit smaller, otherwise the corners look weird.
    }

    public int getXForCenteredText(String text) {

        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth/2 - length/2;
    }
}
