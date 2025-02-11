package objects;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Bad_Apple extends SuperObject{

    GamePanel gp;
    public OBJ_Bad_Apple(GamePanel gp) {

        name = "Apple";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("objects/Bad_Apple_1.png")));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
