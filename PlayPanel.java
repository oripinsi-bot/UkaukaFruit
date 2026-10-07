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

    private Main game;
    private GameManager manager;
    private ArrayList<FallingObject> fallingObjects;
    private ArrayList<FallingObject.Type> typeBag; // เอาไว้เก็บให้ของออกเท่ากัน
    private Random random;

    private Timer timer;
    private int tickCount;

    // ตะกร้าผู้เล่น
    private int basketX;
    private int basketW = 110;
    private int basketH = 130;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private int slowTicks = 0; // ถ้านานกว่า 0 แสดงว่าโดนเห็ดพิษ (สถานะช้า)

    // รูปภาพตะกร้ากับพื้นหลัง
    private Image background;
    private Image basketImage;

    public PlayPanel(Main game) {
        this.game = game;
        this.manager = new GameManager();
        this.fallingObjects = new ArrayList<>();
        this.typeBag = new ArrayList<>();
        this.random = new Random();

        // โหลดรูปภาพจากโฟลเดอร์ Images
        background = new ImageIcon("Images/background.png").getImage();
        basketImage = new ImageIcon("Images/monkey.png").getImage();

        // ตั้งค่า Panel รับปุ่มกด
        setFocusable(true);
        addKeyListener(this);

        // ตั้งเวลาให้เกมรันทุกๆ 20 มิลลิวินาที (50 ครั้งต่อวินาที)
        timer = new Timer(20, this);
        
    }

    // เริ่มเกมใหม่ (รีเซ็ตค่าทุกอย่าง)
    public void startGame() {
        manager.reset();
        fallingObjects.clear();
        typeBag.clear();
        tickCount = 0;
        slowTicks = 0;
        leftPressed = false;
        rightPressed = false;

        // วางตะกร้าไว้ตรงกลางหน้าจอ
        int panelWidth = getWidth() > 0 ? getWidth() : 375;
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


        // 1. คำนวณการเคลื่อนที่ของตะกร้า
        int speed = 7;
        if (slowTicks > 0) {
            speed = 3; // ติดสถานะช้า
            slowTicks--;
        }

        if (leftPressed && basketX > 0) {
            basketX -= speed;
        }
        if (rightPressed && basketX < getWidth() - basketW) {
            basketX += speed;
        }

        // 2. สุ่มของตกใหม่ทุกๆ 30 รอบ (~0.6 วินาที)
        if (tickCount % 30 == 0) {
            spawnFallingObject();
        }

        // 3. ขยับของที่กำลังตก และเช็คการชนตะกร้า
        Rectangle basketBounds = new Rectangle(basketX + 10, getHeight() - basketH, basketW - 20, 30);

        for (int i = fallingObjects.size() - 1; i >= 0; i--) {
            FallingObject obj = fallingObjects.get(i);
            obj.update(); // ขยับตำแหน่ง Y ลงมา

            // เช็คว่าชนตะกร้าหรือไม่
            if (obj.getBounds().intersects(basketBounds)) {
                manager.addScore(obj);

                // ถ้าชนเห็ดพิษ ติดสถานะช้า 3 วินาที (150 รอบ * 20ms = 3000ms)
                if (obj.getType() == FallingObject.Type.MUSHROOM) {
                    slowTicks = 150;
                }

                fallingObjects.remove(i); // ลบออกเมื่อเก็บได้
            } 
            // เช็คว่าตกเลยขอบล่างหน้าจอหรือยัง
            else if (obj.isOutOfBounds(getHeight())) {
                fallingObjects.remove(i);
            }
        }

        // 4. นับถอยหลังเวลาทุกๆ 1 วินาที (50 รอบ * 20ms = 1000ms)
        if (tickCount % 50 == 0) {
            manager.tickOneSecond();
            if (manager.isTimeUp()) {
                endGame();
            }
        }

        // สั่งวาดหน้าจอใหม่
        repaint();
    }

    private void spawnFallingObject() {
        // ถ้าของหมดถุง ให้ใส่ชนิดของวัตถุทั้งหมดลงไปใหม่แล้วทำการสับ (Shuffle)
        if (typeBag.isEmpty()) {
            FallingObject.Type[] types = FallingObject.Type.values();
            for (FallingObject.Type t : types) {
                typeBag.add(t);
            }
            Collections.shuffle(typeBag); // สับถุงให้ออกแบบกระจายตัวเท่าๆ กัน
        }

        FallingObject.Type type = typeBag.remove(0);
        int panelWidth = getWidth() > 0 ? getWidth() : 375;
        double x = random.nextInt(Math.max(1, panelWidth - 50));
        double fallingSpeed = 3 + random.nextInt(4); // ความเร็วสุ่ม 3 ถึง 6

        fallingObjects.add(new FallingObject(type, x, fallingSpeed));
    }

    private void endGame() {
        timer.stop();
        if (game != null) {
            game.showCard(Main.CARD_TIMEUP); // เปลี่ยนหน้าไป TimesUpPanel
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
        // ไม่ได้ใช้งาน แต่ต้องมีไว้เพราะ implements KeyListener
    }

    // --- การวาดกราฟิกบน Panel ---
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 1. วาดพื้นหลัง
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null);

        // 2. วาดวัตถุที่กำลังตก
        for (FallingObject obj : fallingObjects) {
            obj.draw((Graphics2D) g);
        }

        // 3. วาดตะกร้าผู้เล่น
        g.drawImage(basketImage, basketX, getHeight() - basketH - 5, basketW, basketH, null);

        // 4. วาดตัวหนังสือ เวลา และคะแนน (HUD)
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 18));
        g.drawString("TIME: " + manager.getTimeLeftSeconds(), 15, 35);
        g.drawString("SCORE: " + manager.getScore(), getWidth() - 130, 35);

        // แสดงคำว่า SLOW! สีแดงกลางจอถ้าติดสถานะช้า
        if (slowTicks > 0) {
            g.setColor(Color.RED);
            g.drawString("SLOW!", getWidth() / 2 - 25, 250);
        }
    }
}