import java.awt.*;
/**
 * GameObject คลาสแม่แบบเก็บพิกัด การเคลื่อนที่ และขอบเขตเช็คการชน
 */
public abstract class GameObject implements Drawable  {

    public static final int DEFAULT_SIZE = 92;

    protected double x;
    protected double y;
    protected double speed;
    protected int width = DEFAULT_SIZE;  //ความกว้างผลไม้
    protected int height = DEFAULT_SIZE; //ความสูงผลไม้

    public GameObject(double x, double y, double speed) { //Constructor
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public void update() {
        y += speed;
        //อัปเดตตำแหน่งน้องผลไม้ที่ตกจากข้างบน ตาม speed
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, width, height);
        //สร้างขอบเขตสี่เหลี่ยมเอาไปเช็คการชนกับตะกร้า
    }

     public double getX() { //อ่านค่า X
         return x;  
     }
    
    public double getY() { //อ่านค่า y
         return y; 
    }

    // เช็คว่าวัตถุหล่นเลยขอบล่างหน้าจอหรือยัง
    
    public boolean isOutOfBounds(int screenHeight) {
        return y > screenHeight;
    }

    // อ่านขนาดความกว้าง/สูง ของวัตถุ
    public int getWidth() { 
        return width; 
    }

    public int getHeight() {
         return height; 
        }

}