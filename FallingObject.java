import java.awt.*;
import javax.swing.ImageIcon;
/**
 * FallingObject คลาสจัดการประเภทวัตถุที่ตกลงมา (ผลไม้/อุปสรรค)
 */
public class FallingObject extends GameObject {
 
    public enum Type {
        //ชนิดของวัตถุทั้งหมด
    STARFRUIT("Images/star_fruit.png"), //+1 คะแนน
    PASSIONFRUIT("Images/passion_fruit.png"), //+1 คะแนน
    WAIVE("Images/waive.png"), //+1 คะแนน
    DURIAN("Images/durian.png"), //+1 คะแนน
    DRAGONFRUIT("Images/dragon_fruit.png"), //+1 คะแนน
    BOMB("Images/bomb.png"), //-3 คะแนน
    SNAKE("Images/snake.png"), //-1 คะแนน
    MUSHROOM("Images/mushroom.png"); //ตะกร้าช้าลง

    private final String imagePath;

    Type(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }
    
    }
 
    private Type type;
    private Image image;
 
    public FallingObject(Type type, double x, double speed) { //Constructor
        super(x, 0, speed); // เซตตำแหน่งเริ่ม
        this.type = type;
        /// ดึงรูปภาพตาม imagePath ที่กำหนดไว้ใน enum
       this.image = new ImageIcon(type.getImagePath()).getImage();}
 
    @Override
    public void draw(Graphics2D g) {
        //วาดรูปตามชนิดวัตถุ
        g.drawImage(image, (int) x, (int) y, width, height, null);
    }
 
    public boolean isObstacle() {
        //เช็คว่าใช่อุปสรรค
        if (type == Type.BOMB || type == Type.SNAKE || type == Type.MUSHROOM) {
            return true; //รีเทิร์นเมื่อเป็นอุปสรรค
        }
        return false;
    }
 
    public int scoreValue() {
        //ตัวนับคะแนนตามของที่ตกลงมา
        if (type == Type.BOMB) {
            return -3;
        } else if (type == Type.SNAKE) {
            return -1;
        } else if (type == Type.MUSHROOM) {
            return 0;   // ไม่หักคะแนน แต่ทำให้ตะกร้าช้าลง
        } else {
            return 1;   // ผลไม้ทุกชนิดได้คะแนน 1
        }
    }
 
    public Type getType() {
        return type; //ส่งค่าชนิดวัตถุไปให้คลาสอื่นใช้
    }
}
 