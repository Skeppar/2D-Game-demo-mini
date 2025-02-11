package main;

import entity.NPC_OldMan;
import objects.OBJ_Bad_Apple;
import objects.OBJ_Carrot;
import objects.OBJ_Chest;
import objects.OBJ_Pumpkin;

public class AssetManager {

    GamePanel gp;

    public AssetManager(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

        gp.obj[0] = new OBJ_Pumpkin(gp);
        gp.obj[0].setWorldX(gp.getTileSize() * 41);
        gp.obj[0].setWorldY(gp.getTileSize() * 41);

        gp.obj[5] = new OBJ_Pumpkin(gp);
        gp.obj[5].setWorldX(gp.getTileSize() * 41);
        gp.obj[5].setWorldY(gp.getTileSize() * 42);

        gp.obj[6] = new OBJ_Pumpkin(gp);
        gp.obj[6].setWorldX(gp.getTileSize() * 11);
        gp.obj[6].setWorldY(gp.getTileSize() * 10);

        gp.obj[1] = new OBJ_Pumpkin(gp);
        gp.obj[1].setWorldX(gp.getTileSize() * 37);
        gp.obj[1].setWorldY(gp.getTileSize() * 15);

        gp.obj[3] = new OBJ_Chest(gp);
        gp.obj[3].setWorldX(gp.getTileSize() * 12);
        gp.obj[3].setWorldY(gp.getTileSize() * 10);

        gp.obj[4] = new OBJ_Carrot(gp);
        gp.obj[4].setWorldX(gp.getTileSize() * 16);
        gp.obj[4].setWorldY(gp.getTileSize() * 28);

        gp.obj[5] = new OBJ_Bad_Apple(gp);
        gp.obj[5].setWorldX(gp.getTileSize() * 16);
        gp.obj[5].setWorldY(gp.getTileSize() * 25);

    }

    public void setNPC() {

        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.getTileSize()*12;
        gp.npc[0].worldY = gp.getTileSize()*23;
    }
}
