import java.awt.image.BufferedImage;

public abstract class Object {
    protected int x;    //x座標  
    protected int y;    //y座標  
    protected int width;    //寬  
    protected int height;   //高  
    protected BufferedImage image;   //圖片

    public int getX() {
        return x;
    } 
    public void setX(int x) {  
        this.x = x;  
    }  
    public int getY() {  
        return y;  
    }  
    public void setY(int y) {  
        this.y = y;  
    }  
    public int getWidth() {  
        return width;  
    }  
    public void setWidth(int width) {  
        this.width = width;  
    }  
    public int getHeight() {  
        return height;  
    }  
    public void setHeight(int height) {  
        this.height = height;  
    }  
    public BufferedImage getImage() {  
        return image;  
    }  
    public void setImage(BufferedImage image) {  
        this.image = image;  
    }
    public abstract boolean outOfBounds();

    public abstract void step();
}


