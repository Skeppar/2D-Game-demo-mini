package objects;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_GoalM extends SuperObject{

    GamePanel gp;
    public OBJ_GoalM(GamePanel gp) {

        this.gp = gp;

        name = "Goal";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("objects/GoalM.png")));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }
}
