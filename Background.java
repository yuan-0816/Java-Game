import java.awt.image.BufferedImage;


public class Background extends Object {
    private BufferedImage[] images;
    private BufferedImage tmp;

    public Background() {
        this.image = MainGame.background[0];
        this.width = image.getWidth();  
        this.height = image.getHeight();  
        this.x = 0;  
        this.y = 0;
        this.images = new BufferedImage[] {MainGame.background[0], MainGame.background[1]};
        tmp = MainGame.background[0];
    }

    @Override
    public boolean outOfBounds() {
        return false;
    }

    @Override
    public void step() {
        if(MainGame.score >= 1000) {   
            if((int)(MainGame.score/1000) % 1 == 0) {
                tmp = images[1];
            }
            if((int)(MainGame.score/1000) % 2 == 0) {
                tmp = images[0];
            }
            image = tmp;
        }   
    }
}
