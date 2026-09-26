import java.awt.*;
/**
 * FallingObject คลาสจัดการประเภทวัตถุที่ตกลงมา (ผลไม้/อุปสรรค)
 */
public class FallingObject extends GameObject {

    public enum Type { 
        //ชนิดของวัตถุทั้งหมด
    }

    private Type type;

    public FallingObject(Type type, double x, double speed) { //Constructor
        super(x, 0, speed); // เซตตำแหน่งเริ่ม
        this.type = type;
    }

    @Override
    public void draw(Graphics2D g) {
        //วาดรูปตามชนิดวัตถุ
    }

    public boolean isObstacle() {
        //เช็คว่าใช่อุปสรรค
        return false;
    }

    public int scoreValue() {
        //ตัวนับคะแนนตามของที่ตกลงมา
        return 0;
    }

    public Type getType() {
        return type; //ส่งค่าชนิดวัตถุไปให้คลาสอื่นใช้
    }
}