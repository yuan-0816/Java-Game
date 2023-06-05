import java.awt.image.BufferedImage;


public class Dayilea extends Object { 
    private int life;
    private BufferedImage[] images;
    private BufferedImage[] Inverse_images;
    private BufferedImage[] tmp;
    private final int lowest_Y = 240;
    private int speed;

    public Dayilea() {
        life = 3;
        image = MainGame.dayilea;
        width = image.getWidth();  
        height = image.getHeight();  
        x = 75;  
        y = lowest_Y;
        images = new BufferedImage[] {MainGame.dayilea, MainGame.jump_dayilea};
        Inverse_images = new BufferedImage[] {MainGame.Inversedayilea, MainGame.Inversejump_dayilea};
        tmp = images;
        speed = 15;

    }

    public void down() {
        int pos = getY();
        if(pos >= lowest_Y) {
            moveTo(lowest_Y);
        }
        else {
            pos = pos + speed;
            moveTo(pos);
        }
    }

    public void jump() {
        int pos = getY();
        if(pos <= 50) {
            moveTo(50);
        }
        else {
            pos = pos - speed;
            moveTo(pos);
        } 
    }

    public void moveTo(int y) {
        this.y = y;
    }

    public void subtractLife() {
        life--;
    }

    public int getLife() {
        return life;
    }  

    public boolean outOfBounds() {  
        return false;    
    }  

    public void step() {  
        if(MainGame.score >= 1000) {   
            if((int)(MainGame.score/1000) % 1 == 0) {
                tmp = Inverse_images;
            }
            if((int)(MainGame.score/1000) % 2 == 0) {
                tmp = images;
            }
        }
        if(getY() < 210) {
            image = tmp[1];
        }
        else
            image = tmp[0];
    }  

    public boolean hit(Object other) {
        int x1 = other.x - this.width/2;                 //x座標最小距離  
        int x2 = other.x + this.width/2 + other.width;   //x座標最大距離  
        int y1 = other.y - this.height/2;                //y座標最小距離  
        int y2 = other.y + this.height/2 + other.height; //y座標最大距離  

        int herox = this.x + this.width/2;               //有光x座標中心點距離  
        int heroy = this.y + this.height/2;              //有光y座標中心點距離  

        return herox>x1 && herox<x2 && heroy>y1 && heroy<y2;   //區間範圍內為撞上了  
    }
}
