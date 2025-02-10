package objects;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Pumpkin extends SuperObject{

    GamePanel gp;
    public OBJ_Pumpkin(GamePanel gp) {

        name = "Pumpkin";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("objects/Pumpkin_1.png")));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        }catch (IOException e) {
            e.printStackTrace();
        }

        /*
        // Can set the specific solid are for each object like this.
        solidArea.x = 5;
        solidArea.y = 5;
        solidArea.height = 48;
        solidArea.width = 48;
         */
    }
}
