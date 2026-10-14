import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * PlayPanel หน้าจอหลักสำหรับเล่นเกม
 */
public class PlayPanel extends JPanel implements ActionListener, KeyListener {

    // กำหนดตัวแปรค่าคงที่จะได้ไม่ต้องเปลี่ยนค่าทีละที่
    private static final int TICK_MS = 20;
    private static final int TICKS_PER_SECOND = 1000 / TICK_MS; //ใน 1 วิมีกี่ Ticks
    private static final int SPAWN_INTERVAL_TICKS = 47; // ~0.94 วินาที
    private static final int NORMAL_SPEED = 7;
    private static final int SLOW_SPEED = 3; 
    private static final int SLOW_DURATION_TICKS = 3 * TICKS_PER_SECOND; //150 Ticks = 3 วิ

    // กรอบตัวรับของ
    private static final int CATCH_OFFSET_Y = 28;          //ขอบบนรูปลิงลงมาถึงมือ
    private static final int CATCH_HEIGHT = 30;            //ความสูงกล่อง
    private static final int CATCH_INSET_LEFT = 8;         //ขอบซ้ายกล่อง
    private static final int CATCH_INSET_RIGHT = 10;       //ขอบขวากล่อง
    private static final int BASKET_BOTTOM_MARGIN = -20;   //ขอบล่างจอถึงก้นรูปลิง
    private static final boolean DEBUG_HITBOX = false;     //เปลี่ยนเป็น true เพื่อดูกรอบตอนชน

    private Main game;
    private GameManager manager;
    private ArrayList<FallingObject> fallingObjects;
    private ArrayList<FallingObject.Type> typeBag; // เอาไว้เก็บให้ของออกเท่ากัน
    private Random random;

    private Timer timer;
    private int tickCount;

    // ตะกร้าผู้เล่น
    private int basketX;
    private int basketW = 130;
    private int basketH = 150;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private int slowTicks = 0; // ถ้านานกว่า 0 แสดงว่าโดนน้องเห็ดพิษ (ช้า 3 วิ)

    // รูปภาพตะกร้ากับพื้นหลัง
    private Image background;
    private Image basketImage;
    private Image scaledBackground; // พื้นหลังที่ย่อแล้ว


 

    public PlayPanel(Main game) {
        this.game = game;
        this.manager = new GameManager();
        this.fallingObjects = new ArrayList<>();
        this.typeBag = new ArrayList<>();
        this.random = new Random();

        // โหลดรูปภาพจากโฟลเดอร์ Images
        background = new ImageIcon("Images/background.png").getImage();
        basketImage = FallingObject.scaleImage(
                new ImageIcon("Images/monkey.png").getImage(), basketW, basketH); // ย่อครั้งเดียว
        FallingObject.preloadImages(); // โหลดรูปผลไม้ล่วงหน้า

        // ตั้งค่า Panel รับปุ่มกด
        setFocusable(true);
        addKeyListener(this);

        // ตั้งเวลาให้เกมรันทุกๆ 20 มิลลิวินาที (50 ครั้งต่อวินาที)
        timer = new Timer(TICK_MS, this);

       
    }

    // เริ่มเกมใหม่ (รีเซ็ตค่าทุกอย่าง)
    public void startGame() {
        timer.stop(); // กัน Timer ซ้อนถ้ากดเริ่มซ้ำ
        manager.reset();
        fallingObjects.clear();
        typeBag.clear();
        tickCount = 0;
        slowTicks = 0;
        leftPressed = false;
        rightPressed = false;

        // วางตะกร้าไว้ตรงกลางหน้าจอ
        int panelWidth;
        if (getWidth() > 0) {// หน้าจอได้ขนาดความกว้างไหม
            panelWidth = getWidth(); // พร้อม ให้ใช้ความกว้างจริงของหน้าจอ
        } else {
            panelWidth = 375; // ถ้าหน้าจอยังไม่พร้อม ใช้ค่าสำรองเป็น 375 px
        }
        basketX = (panelWidth - basketW) / 2;

        timer.start();
        requestFocusInWindow(); // ดึงโฟกัสมาที่แป้นพิมพ์

       
      
    }

    // ทำงานทุกๆ 20 ms ตามที่ Timer เรียก
    @Override
    public void actionPerformed(ActionEvent e) {
        gameTick();
       
    }

    private void gameTick() {
        tickCount++;
        
        if (!isFocusOwner()) {
            requestFocusInWindow();
        }

        // คำนวณการเคลื่อนที่ของตะกร้า
        int speed = NORMAL_SPEED;
        if (slowTicks > 0) {
            speed = SLOW_SPEED; // ติดสถานะช้า
            slowTicks--;
        }

        if (leftPressed) {
            basketX -= speed;
        }
        if (rightPressed) {
            basketX += speed;
        }
        // ให้ตะกร้าอยู่ในจอเสมอ
        basketX = Math.max(0, Math.min(basketX, getWidth() - basketW));

        //สุ่มของตกใหม่ทุกๆ 47 รอบ (~0.94 วินาที)
        if (tickCount % SPAWN_INTERVAL_TICKS == 0) {
            spawnFallingObject();
        }

        // ขยับของที่กำลังตก และเช็คการชนตะกร้า
        Rectangle basketBounds = getCatchBounds(); // ใช้กรอบเดียวกับที่วาดใน debug

        for (int i = fallingObjects.size() - 1; i >= 0; i--) {
            FallingObject obj = fallingObjects.get(i);
            obj.update(); // ขยับตำแหน่ง Y ลงมา

            // เช็คว่าชนตะกร้าหรือไม่
            if (obj.getBounds().intersects(basketBounds)) {
                manager.addScore(obj);

                // ถ้าชนน้องเห็ดพิษ ติดสถานะช้า 3 วินาที
                if (obj.getType() == FallingObject.Type.MUSHROOM) {
                    slowTicks = SLOW_DURATION_TICKS;  
                }

                fallingObjects.remove(i); // ลบออกเมื่อเก็บได้
            }
            // เช็คว่าตกเลยขอบล่างหน้าจอหรือยัง
            else if (obj.isOutOfBounds(getHeight())) {
                fallingObjects.remove(i);
            }
        }

        // นับถอยหลังเวลาทุกๆ 1 วินาที (50 รอบ * 20ms = 1000ms)
        if (tickCount % TICKS_PER_SECOND == 0) { 
            manager.tickOneSecond();
            if (manager.isTimeUp()) {
                endGame();
            }
        }

        // สั่งวาดหน้าจอใหม่
        repaint();
    }

    // กรอบรับของของตะกร้า สร้างที่เดียว ใช้ทั้งเช็คชนและวาด debug
    private Rectangle getCatchBounds() {
        int basketTop = getHeight() - basketH - BASKET_BOTTOM_MARGIN;
        return new Rectangle(
                basketX + CATCH_INSET_LEFT,
                basketTop + CATCH_OFFSET_Y,
                basketW - CATCH_INSET_LEFT - CATCH_INSET_RIGHT,
                CATCH_HEIGHT);
    }

    private void spawnFallingObject() {
        // ถ้าของหมดถุง ให้ใส่ชนิดของวัตถุทั้งหมดลงไปใหม่แล้วทำการสับ (Shuffle)
        if (typeBag.isEmpty()) {
            FallingObject.Type[] types = FallingObject.Type.values();
            for (int i = 0; i < types.length; i++) {
                FallingObject.Type t = types[i];
                typeBag.add(t);
            }
            Collections.shuffle(typeBag); // สับถุงให้ออกแบบกระจายตัวเท่าๆ กัน
        }

        FallingObject.Type type = typeBag.remove(0);
        int panelWidth;
        if (getWidth() > 0) {
            panelWidth = getWidth();
        } else {
            panelWidth = 375;
        }

        // ใช้ความกว้างผลไม้จริง ของจะได้ไม่ล้นขอบขวา
        double x = random.nextInt(Math.max(1, panelWidth - GameObject.DEFAULT_SIZE));
        double fallingSpeed = 5 + random.nextInt(4); // ความเร็วสุ่ม 5 ถึง 8

        fallingObjects.add(new FallingObject(type, x, fallingSpeed));
    }

    private void endGame() {
        timer.stop();
        if (game != null) {
          //  game.showCard(Main.CARD_TIMEUP); // เปลี่ยนหน้าไป TimesUpPanel
            game.getTimesUpPanel().gameOver(manager.getScore());
        }
    }

    // --- ระบบรับปุ่มกด Keyboard ---
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    // --- การวาดกราฟิกบน Panel ---
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g; 

        // วาดพื้นหลัง
        if (scaledBackground == null
                || scaledBackground.getWidth(null) != getWidth()
                || scaledBackground.getHeight(null) != getHeight()) {
            scaledBackground = FallingObject.scaleImage(background, getWidth(), getHeight());
        }
        g.drawImage(scaledBackground, 0, 0, null); // ไม่ย่อภาพพื้นหลังทุกเฟรม

        // วาดวัตถุที่กำลังตก
        for (int i = 0; i < fallingObjects.size(); i++) {
            FallingObject obj = fallingObjects.get(i);
            obj.draw(g2);
        }

        // วาดตะกร้าผู้เล่น
        g.drawImage(basketImage, basketX, getHeight() - basketH - BASKET_BOTTOM_MARGIN, null);

        // โหมด debug: แสดงกรอบรับของ (เขียว) และกรอบผลไม้ (เหลือง)
        if (DEBUG_HITBOX) {
            g.setColor(Color.GREEN);
            Rectangle c = getCatchBounds();
            g.drawRect(c.x, c.y, c.width, c.height);
            g.setColor(Color.YELLOW);
            for (FallingObject obj : fallingObjects) {
                Rectangle b = obj.getBounds();
                g.drawRect(b.x, b.y, b.width, b.height);
            }
        }

        // วาดตัวหนังสือ เวลา และคะแนน (HUD)
        g.setColor(Color.WHITE);
        g.setFont(new Font("Tahoma", Font.BOLD, 18));
        g.drawString("TIME: " + manager.getTimeLeftSeconds(), 17, 35);
        g.drawString("SCORE: " + manager.getScore(), getWidth() - 110, 35);

        // แสดงคำว่า SLOW! สีแดงกลางจอถ้าติดสถานะช้า
        if (slowTicks > 0) {
           g.setColor(Color.decode("#722F37"));
            g.drawString("SLOW!", getWidth() / 2 - 25, 250);
        }

       

    }
     
}