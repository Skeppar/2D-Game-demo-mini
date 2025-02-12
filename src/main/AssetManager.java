package main;

import entity.NPC_OldMan;
import objects.*;

public class AssetManager {

    GamePanel gp;

    public AssetManager(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

        gp.obj[0] = new OBJ_GoalL(gp);
        gp.obj[0].setWorldX(gp.getTileSize() * 36);
        gp.obj[0].setWorldY(gp.getTileSize() * 13);

        gp.obj[1] = new OBJ_GoalM(gp);
        gp.obj[1].setWorldX(gp.getTileSize() * 37);
        gp.obj[1].setWorldY(gp.getTileSize() * 13);

        gp.obj[2] = new OBJ_GoalR(gp);
        gp.obj[2].setWorldX(gp.getTileSize() * 38);
        gp.obj[2].setWorldY(gp.getTileSize() * 13);

        gp.obj[3] = new OBJ_Carrot(gp);
        gp.obj[3].setWorldX(gp.getTileSize() * 12);
        gp.obj[3].setWorldY(gp.getTileSize() * 28);

        gp.obj[4] = new OBJ_Bad_Apple(gp);
        gp.obj[4].setWorldX(gp.getTileSize() * 22);
        gp.obj[4].setWorldY(gp.getTileSize() * 25);

        gp.obj[5] = new OBJ_Pumpkin(gp);
        gp.obj[5].setWorldX(gp.getTileSize() * 38);
        gp.obj[5].setWorldY(gp.getTileSize() * 36);

        gp.obj[6] = new OBJ_Pumpkin(gp);
        gp.obj[6].setWorldX(gp.getTileSize() * 41);
        gp.obj[6].setWorldY(gp.getTileSize() * 42);

        gp.obj[7] = new OBJ_Pumpkin(gp);
        gp.obj[7].setWorldX(gp.getTileSize() * 17);
        gp.obj[7].setWorldY(gp.getTileSize() * 10);

        gp.obj[8] = new OBJ_Pumpkin(gp);
        gp.obj[8].setWorldX(gp.getTileSize() * 37);
        gp.obj[8].setWorldY(gp.getTileSize() * 15);

        gp.obj[9] = new OBJ_Pumpkin(gp);
        gp.obj[9].setWorldX(gp.getTileSize() * 21);
        gp.obj[9].setWorldY(gp.getTileSize() * 34);

        gp.obj[10] = new OBJ_Pumpkin(gp);
        gp.obj[10].setWorldX(gp.getTileSize() * 14);
        gp.obj[10].setWorldY(gp.getTileSize() * 36);

        gp.obj[11] = new OBJ_Pumpkin(gp);
        gp.obj[11].setWorldX(gp.getTileSize() * 10);
        gp.obj[11].setWorldY(gp.getTileSize() * 39);

        gp.obj[12] = new OBJ_Pumpkin(gp);
        gp.obj[12].setWorldX(gp.getTileSize() * 42);
        gp.obj[12].setWorldY(gp.getTileSize() * 28);

        gp.obj[13] = new OBJ_Pumpkin(gp);
        gp.obj[13].setWorldX(gp.getTileSize() * 30);
        gp.obj[13].setWorldY(gp.getTileSize() * 29);

        gp.obj[14] = new OBJ_Pumpkin(gp);
        gp.obj[14].setWorldX(gp.getTileSize() * 20);
        gp.obj[14].setWorldY(gp.getTileSize() * 42);
    }

    public void setNPC() {

        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.getTileSize()*12;
        gp.npc[0].worldY = gp.getTileSize()*12;
    }
}
