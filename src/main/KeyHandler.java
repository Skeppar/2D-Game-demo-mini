package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, shiftPressed, enterPressed;

    // Debug
    boolean checkDrawTime = false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode(); // Returns the integer keyCode associated with the key in that was pressed.

        // Title state for menu selection
        if (gp.gameState == gp.titleState || gp.gameState == gp.endState) {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 1;
                }
            }

            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum >= 2) {
                    gp.ui.commandNum = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) {
                    gp.gameState = gp.playState;
                    gp.playMusic(0);
                    resetGameVariables(); // If player chose Play Again at end screen.
                }
                if (gp.ui.commandNum == 1) {
                    System.exit(0);
                }
                if (gp.ui.commandNum == 2) {
                    // Add scoreboard later
                }
            }
        }

        // Play state
        if (gp.gameState == gp.playState) {
            if (code == KeyEvent.VK_W) { // If player presses W, this happens.
                upPressed = true;
            }

            if (code == KeyEvent.VK_S) {
                downPressed = true;
            }

            if (code == KeyEvent.VK_A) {
                leftPressed = true;
            }

            if (code == KeyEvent.VK_D) {
                rightPressed = true;
            }

            if (code == KeyEvent.VK_SHIFT) { // Not in use, change to use for sprinting later.
                shiftPressed = true;
                // gp.player.speed += 1;
            }

            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.pauseState;
            }

            if (code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }

            // Debug on and off
            if (code == KeyEvent.VK_T) {
                if (!checkDrawTime) {
                    checkDrawTime = true;
                } else {
                    checkDrawTime = false;
                }
            }
        }

        // Pause state
        else if (gp.gameState == gp.pauseState) {
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.playState;
                gp.playMusic(0);
            }
        }

        // Dialogue state
        else if (gp.gameState == gp.dialogueState) {
            if (code == KeyEvent.VK_ENTER) {
                gp.gameState = gp.playState;
            }
        }
    }

    private void resetGameVariables() {
        gp.resetGame();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W) {
            upPressed = false;
        }

        if(code == KeyEvent.VK_S) {
            downPressed = false;
        }

        if(code == KeyEvent.VK_A) {
            leftPressed = false;
        }

        if(code == KeyEvent.VK_D) {
            rightPressed = false;
        }

        if(code == KeyEvent.VK_SHIFT) { // Not in use, use for sprint later.
            shiftPressed = false;
        }
    }
}
