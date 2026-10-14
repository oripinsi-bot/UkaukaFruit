import java.awt.*;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * FallingObject คลาสจัดการประเภทวัตถุที่ตกลงมา (ผลไม้/อุปสรรค)
 */
public class FallingObject extends GameObject {
    private static final int HITBOX_MARGIN = 20;

    public enum Type {
        // ชนิดของวัตถุทั้งหมด
        STARFRUIT("Images/star_fruit.png", 1, false), // +1 คะแนน
        PASSIONFRUIT("Images/passion_fruit.png", 1, false), // +1 คะแนน
        WAIVE("Images/waive.png", 1, false), // +1 คะแนน
        DURIAN("Images/durian.png", 1, false), // +1 คะแนน
        DRAGONFRUIT("Images/dragon_fruit.png", 1, false), // +1 คะแนน
        BOMB("Images/bomb.png", -3, false), // -3 คะแนน
        SNAKE("Images/snake.png", -1, false), // -1 คะแนน
        MUSHROOM("Images/mushroom.png", 0, false); // ตะกร้าช้าลง

        private final String imagePath;
        private final int score;
        private final boolean obstacle;
        private Image image;

        Type(String imagePath, int score, boolean obstacle) {
            this.imagePath = imagePath;
            this.score = score;
            this.obstacle = obstacle;
        }

        public String getImagePath() {
            return imagePath;
        }

        public int getScore() {
            return score;
        }

        public boolean isObstacle() {
            return obstacle;
        }

        public Image getImage() {
            if (image == null) {
                // ย่อรูปให้เท่าขนาดที่วาดไว้ตั้งแต่ตอนโหลด ไม่ต้องย่อทุกเฟรม
                image = scaleImage(new ImageIcon(imagePath).getImage(), DEFAULT_SIZE, DEFAULT_SIZE);
            }
            return image;
        }

    }

    public static void preloadImages() {
        Arrays.stream(Type.values()).forEach(t -> {
            System.out.println(t.getImage());
        });
    }

    public static Image scaleImage(Image src, int w, int h) {
        if (w <= 0 || h <= 0 || src.getWidth(null) <= 0) {
            return src; // รูปโหลดไม่ขึ้น (path ผิด) ปล่อยไว้ตามเดิม
        }
        BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = out.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(src, 0, 0, w, h, null);
        g2.dispose();
        return out;
    }

    private final Type type;

    public FallingObject(Type type, double x, double speed) { // Constructor
        super(x, 0, speed); // เซตตำแหน่งเริ่ม
        this.type = type;
        // ไม่โหลดรูปใน constructor แล้ว ดึงจาก type.getImage() ตอนวาดแทน
    }

    @Override
    public void draw(Graphics2D g) {
        // วาดรูปตามชนิดวัตถุ
        g.drawImage(type.getImage(), (int) x, (int) y, null); // รูปย่อมาแล้ว
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x + HITBOX_MARGIN, (int) y + HITBOX_MARGIN,
                width - 2 * HITBOX_MARGIN, height - 2 * HITBOX_MARGIN);
    }

    public boolean isObstacle() {
        return type.isObstacle(); // ใช้ค่าจาก enum
    }

    public int scoreValue() {
        return type.getScore(); // ใช้ค่าจาก enum
    }

    public Type getType() {
        return type; // ส่งค่าชนิดวัตถุไปให้คลาสอื่นใช้
    }

}