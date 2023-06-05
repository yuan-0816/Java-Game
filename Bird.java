import java.util.Random;
import java.awt.image.BufferedImage;

public class Bird extends Object {
    private int speed = 3;
    private int randomIndex;
    private BufferedImage tmp;

    public Bird() {
        this.image = MainGame.bird;
        tmp = MainGame.bird;
        width = image.getWidth();
        height = image.getHeight();
        x = MainGame.WIDTH;
        Random rand = new Random();

        randomIndex = rand.nextInt(3);
        if(randomIndex == 0)
            y = 60;
        else if(randomIndex == 1)
            y = 160;
        else if(randomIndex == 2)
            y = 260;
    }

    public boolean outOfBounds() {
        return x < -width;
    }

    public void step() {
        x -= speed;
        if(MainGame.score >= 1000) {   
            if((int)(MainGame.score/1000) % 1 == 0) {
                tmp = MainGame.Inversebird;
            }
            if((int)(MainGame.score/1000) % 2 == 0) {
                tmp = MainGame.bird;
            }
            image = tmp;
        }
    }
    
}