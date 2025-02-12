package entity;
import main.GamePanel;
import main.KeyHandler;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    private int hasPumpkin = 0;


    public int getHasPumpkin() {
        return hasPumpkin;
    }


    public Player(GamePanel gp, KeyHandler keyH) {

        super(gp); // Calls the constructor from Entity and passes this gp.
        this.keyH = keyH;

        screenX = gp.getScreenWidth()/2 - (gp.tileSize/2);
        screenY = gp.getScreenHeight()/2 - (gp.tileSize/2);

        solidArea = new Rectangle(8, 16, 32, 32); // The area the player is solid now starts 8px in from the right and 16 down from the top. And is 32*32 big, which is a smaller box around the body to the feet.
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {

        worldX = gp.tileSize * 10;
        worldY = gp.tileSize * 10;
        speed = 3;
        direction = "still";
    }

    public void getPlayerImage() {
        up1 = setUp("player/2D_Character_Away_Moving_1");
        up2 = setUp("player/2D_Character_Away_Moving_2");
        still1 = setUp("player/2D_Character_Front_Still");
        still2 = setUp("player/2D_Character_Front_Still2"); // Not in use, add idle animation later.
        down1 = setUp("player/2D_Character_Front_Moving_1");
        down2 = setUp("player/2D_Character_Front_Moving_2");
        dStill = setUp("player/2D_Character_Front_Still");
        right1 = setUp("player/2D_Character_Right_Moving_1");
        right2 = setUp("player/2D_Character_Right_Moving_2");
        left1 = setUp("player/2D_Character_Left_Moving_1");
        left2 = setUp("player/2D_Character_Left_Moving_2");
    }

    public void update() {

        // First we check the direction, after this we check the collision.
        if(keyH.upPressed || keyH.downPressed || keyH.rightPressed || keyH.leftPressed) {

            if (keyH.upPressed && keyH.rightPressed) {
                direction = "upR";
            } else if(keyH.upPressed && keyH.leftPressed){
                direction = "upL";
            } else if (keyH.downPressed && keyH.rightPressed) {
                direction = "downR";
            } else if(keyH.downPressed && keyH.leftPressed){
                direction = "downL";
            } else if (keyH.upPressed) {
                    direction = "up";
            } else if (keyH.rightPressed) {
                direction = "right";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else {
                direction = "down";
            }
        } else {
            // No movement keys pressed, reset to still position
            direction = "still";
            spriteNum = 1;  // Reset to first frame
            spriteCounter = 0;  // Reset counter
        }


        // Check tile collision.
            collisionOn = false;
            gp.getcCheck().checkTile(this); // Pass player class as the entity.

            // Object collision.
            int objIndex = gp.getcCheck().checkObject(this, true);
            pickUpObject(objIndex);

            // NPC collision
            int npcIndex = gp.getcCheck().checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            // If collision is false player can move.
            if(!collisionOn) {
                switch (direction) {
                    case "upR" -> {
                        worldY -= speed*0.7;
                        worldX += speed*0.7;
                    }
                    case "upL" -> {
                        worldY -= speed*0.7;
                        worldX -= speed*0.7;
                    }
                    case "downR" -> {
                        worldY += speed*0.7;
                        worldX += speed*0.7;
                    }
                    case "downL" -> {
                        worldY += speed*0.7;
                        worldX -= speed*0.7;
                    }
                    case "up" -> {
                        worldY -= speed;
                    }
                    case "down" -> {
                        worldY += speed;
                    }
                    case "left" -> {
                        worldX -= speed;
                    }
                    case "right" -> {
                        worldX += speed;
                    }
                }
            }

            // If you want more animations for walking, just add more numbers after 2. Change here and in draw.
            spriteCounter++;
            if (spriteCounter > 12) { // This changes the animation every 12 frames, which is 5 times per second.
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
    }

    public void pickUpObject(int i) {

        if (i != 999) {
            String objectName = gp.getObj()[i].getName();

            switch (objectName) {
                case "Pumpkin" -> {
                    gp.playSE(1);
                    hasPumpkin++;
                    gp.ui.updatePumpkins(hasPumpkin);
                    gp.getObj()[i] = null;
                }
                case "Door" -> {
                    if(hasPumpkin > 0) {
                        gp.playSE(4); // Sound effect 4
                        gp.getObj()[i] = null;
                        System.out.println("Pumpkin: " + hasPumpkin);
                        gp.ui.showMessage("You reached the goal.");
                    }
                    else {
                        gp.ui.showMessage("You need a key =(");
                    }
                }
                case "Goal" -> {
                    if(hasPumpkin > 0) {
                        gp.ui.updateFinalScore();
                        gp.playSE(2);
                        System.out.println("You have " + hasPumpkin + " pumpkins.");
                    }
                    else {
                        gp.ui.showMessage("You need to collect at least one pumpkin!");
                    }
                }
                case "Carrot" -> {
                    gp.playSE(3);
                    speed += 2;
                    gp.getObj()[i] = null;
                }

                case "Apple" -> {
                    gp.playSE(3);
                    speed -= 2;
                    gp.getObj()[i] = null;
                }
            }
        }
    }

    public void interactNPC(int i) {

        if (i != 999) {

            gp.gameState = gp.dialogueState;
            gp.npc[i].speak();

            /*
            if (gp.keyH.enterPressed) {
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }
        gp.keyH.enterPressed = false;
             */
        }
    }

    public void draw(Graphics2D g2) {

        /*
        g2.setColor(Color.white);
        g2.fillRect(x, y, gp.tileSize, gp.tileSize); // gp is GamePanel, remember to set tileSize public.
        // Remember to use playerX/Y and tileSize and not numbers, if you want to change the map or character size later you'd have to change it in several places.
        // If you use number the player won't be able to change position. That will be done in the update method.
         */

        BufferedImage image = null;

        // If you want more animations for walking, just add more numbers after 2. Change here and in update.
        // Add diagonal animations later.
        switch (direction) {
            case "up", "upR", "upL" -> {
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
            }
            case "down", "downR", "downL" -> {
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
            }
            case "left" -> {
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
            }
            case "right" -> {
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
            }
            default -> image = still1;
            // Add idle animation later.
        }
        // g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null); // Don't need to draw these every time, same as in TileManager
        g2.drawImage(image, screenX, screenY, null);
    }

    public void reset() {
        // Reset position to the starting point
        worldX = gp.tileSize * 10;
        worldY = gp.tileSize * 10;
        speed = 3;
        direction = "still";
        hasPumpkin = 0;
        gp.ui.updatePumpkins(hasPumpkin);  // Update UI to reflect the reset pumpkin count
        spriteCounter = 0;
        spriteNum = 1;
        // Remember to update if other things are added, such as HP or other buffs, debuffs or items.
    }
}
