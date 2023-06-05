import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class MainGame extends JPanel {
    public final static int WIDTH = 800;
    public final static int HIGHT = 375;
    private final static int black = 0;
    private final static int white = 255;

    //init
    private int state = 0;  //game state, [0:START, 1:RUNNING, 2:PAUSE, 3:GAME_OVER]
    private final static int START = 0; 
    private final static int RUNNING = 1; 
    private final static int PAUSE = 2; 
    private final static int GAME_OVER = 3; 

    public static int score = 0;
    private Timer timer;
    private int intervel = 1000 / 100;  //時間間隔(毫秒)
    private static int Score_color = black;

    public static BufferedImage[] background;   //[0:normal, 1:Inversed]
    public static BufferedImage bird;
    public static BufferedImage Inversebird;
    public static BufferedImage dayilea;
    public static BufferedImage Inversedayilea;
    public static BufferedImage jump_dayilea;
    public static BufferedImage Inversejump_dayilea;
    public static BufferedImage start;
    public static BufferedImage pause;
    public static BufferedImage gameover;
    private Dayilea hero_dayilea = new Dayilea();
    private Background Back_Ground = new Background();
    private Object[] birds = {};

    static {
        try {
            background = new BufferedImage[] {ImageIO.read(MainGame.class.getResource("image/Background.png")), 
                                              ImageIO.read(MainGame.class.getResource("image/InverseBackground.png"))};
            bird = ImageIO.read(MainGame.class.getResource("image/Bird.png"));
            Inversebird = ImageIO.read(MainGame.class.getResource("image/InverseBird.png"));
            dayilea = ImageIO.read(MainGame.class.getResource("image/Dayilea.png"));
            Inversedayilea = ImageIO.read(MainGame.class.getResource("image/InverseDayilea.png"));
            jump_dayilea = ImageIO.read(MainGame.class.getResource("image/Jump_Dayilea.png"));
            Inversejump_dayilea = ImageIO.read(MainGame.class.getResource("image/InverseJump_Dayilea.png"));
            start = ImageIO.read(MainGame.class.getResource("image/start.png"));
            pause = ImageIO.read(MainGame.class.getResource("image/pause.png"));
            gameover = ImageIO.read(MainGame.class.getResource("image/gameover.png"));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    //畫畫
    public void paint(Graphics g) {
        g.drawImage(Back_Ground.image, Back_Ground.getX(), Back_Ground.getY(), null);
        paint_Dayilea(g);
        paint_Bird(g);
        paint_Score(g);
        paint_State(g);
    }

    public void paint_Dayilea(Graphics g) {
        g.drawImage(hero_dayilea.getImage(), hero_dayilea.getX(), hero_dayilea.getY(), null);
    }

    public void paint_Bird(Graphics g) {
        for (int i = 0; i < birds.length; i++) { 
            Object f = birds[i]; 
            g.drawImage(f.getImage(), f.getX(), f.getY(), null); 
        }
    }

    public void paint_Score(Graphics g) {
        int x = 10;     //x座標 
        int y = 30;     //y座標 
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 22);   //字型 
        g.setColor(new Color(Score_color, Score_color, Score_color)); g.setFont(font);  //設定字型 
        g.drawString("SCORE:" + score, x, y);   //畫分數
        y=y+20;     //y座標增20 
        g.drawString("LIFE:" + hero_dayilea.getLife(), x, y);   //畫命 
    }

    public void paint_State(Graphics g) {
        switch (state) { 
            case START:
            g.drawImage(start, (WIDTH - start.getWidth())/2, (HIGHT - start.getHeight())/2, null); 
            break; 
            case PAUSE:
            g.drawImage(pause, (WIDTH - pause.getWidth())/2, (HIGHT - pause.getHeight())/2, null); 
            break; 
            case GAME_OVER:
            g.drawImage(gameover, (WIDTH - gameover.getWidth())/2, (HIGHT - gameover.getHeight())/2, null); 
            break; 
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("有光跳跳");
        MainGame maingame = new MainGame();
        frame.getContentPane().add(maingame);
        maingame.setFocusable(true);
        maingame.requestFocusInWindow();
        frame.setSize(WIDTH, HIGHT);
        frame.setResizable(false);
        frame.setAlwaysOnTop(true);     //Always on TOP!
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        frame.setLocationRelativeTo(null);  //遊戲面板顯示在正中間 
        frame.setVisible(true);
        maingame.action();
    }

    //遊戲執行
    public void action() {
        MouseAdapter l = new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {    //滑鼠進入 
                if(state == PAUSE) {
                    state = RUNNING; 
                } 
            } 
            public void mouseExited(MouseEvent e) {     //滑鼠退出 
                if(state == RUNNING) {
                    state = PAUSE; 
                } 
            } 
            public void mouseClicked(MouseEvent e) {    //滑鼠點選 
                switch(state) { 
                    case START: 
                        state = RUNNING;    //啟動狀態下執行
                        break; 
                    case GAME_OVER:         //重置遊戲 
                        birds = new Object[0];
                        hero_dayilea = new Dayilea();
                        Back_Ground = new Background();
                        score = 0;
                        Score_color = 0;
                        state = START;
                        break; 
                } 
            }
        };

        KeyAdapter m = new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(state == RUNNING) {
                    int key = e.getKeyCode();
                    if(key == KeyEvent.VK_UP) {
                        hero_dayilea.jump();
                    }
                    if(key == KeyEvent.VK_DOWN) {
                        hero_dayilea.down();
                    }
                }
            }
        };
        this.addKeyListener(m);
        this.addMouseListener(l);
        this.addMouseMotionListener(l); 

        timer = new Timer(); 
        timer.schedule(new TimerTask() { 
            public void run() { 
                if (state == RUNNING) {
                    Bird_enter_Action();
                    Step_Action();
                    outOfBoundsAction();
                    addScore();
                    checkGameOverAction();
                } 
                repaint();  //畫畫:P
            } 
        }, intervel, intervel); 
    }

    int BirdEnterIndex = 0;
    public void Bird_enter_Action() {   
        BirdEnterIndex++;
        if(BirdEnterIndex % 100 == 0) {  //生成鳥鳥:3
            Object obj = nextOne();
            birds = Arrays.copyOf(birds, birds.length+1);
            birds[birds.length - 1] = obj;
        }
    }

    //鳥鳥的出生:P
    public static Object nextOne() {
        return new Bird();  
    }

    public void Step_Action() {
        Back_Ground.step();
        for(int i = 0; i < birds.length; i++) {
            Object o = birds[i];
            o.step();
        }
        hero_dayilea.step();
        if(score >= 1000) {   
            if((int)(score/1000) % 1 == 0) {
                Score_color = white;
            }
            if((int)(score/1000) % 2 == 0) {
                Score_color = black;
            }
        }
    }

    public void outOfBoundsAction() {
        int index = 0;
        Object[] birdsLives = new Object[birds.length];
        for(int i = 0; i < birds.length; i++) {
            Object o = birds[i];
            if(o.outOfBounds() == false) {
                birdsLives[index++] = o;
            }
        }
        birds = Arrays.copyOf(birdsLives, index);
    }

    public void addScore() {
        score++;
    }

    public void checkGameOverAction() {  
        if(isGameOver()==true) {  
            state = GAME_OVER;  
        }  
    }
    
    public boolean isGameOver() {
        for(int i = 0; i < birds.length; i++) {  
            int index = -1;  
            Object obj = birds[i];  
            if (hero_dayilea.hit(obj)) {
                hero_dayilea.subtractLife();  
                index = i;      //記錄碰上的鳥鳥  
            }  
            if (index != -1) {  
                Object t = birds[index];  
                birds[index] = birds[birds.length - 1];  
                birds[birds.length - 1] = t;        //碰上的與最後一個鳥鳥交換  
                birds = Arrays.copyOf(birds, birds.length - 1);         //刪除碰上的鳥鳥 
            }  
        }  
        return hero_dayilea.getLife() <= 0;  
    }
}