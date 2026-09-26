/**
 * Player.java - ข้อมูลผู้เล่น
 */
public class Player {
    private String name; // เก็บชื่อผู้ใช้ (Username)
    private String password; // เก็บรหัสผ่าน (Password)
    private int lastScore; // เก็บสถิติคะแนนล่าสุดที่เล่นได้
    private int bestScore; // เก็บสถิติคะแนนสูงสุดของผู้เล่นคนนี้

    public Player(String name, String password) {
        this.name = name;
        this.password = password;
       // Constructor สำหรับสร้างวัตถุผู้เล่น
    }

    public String getName()  { 
        return name; 
         //สำหรับดึงข้อมูลชื่อผู้ใช้
     }
    public int getBestScore() { 
        return bestScore; 
        // สำหรับดึงข้อมูลคะแนนสูงสุด
    }

    public void setLastScore(int score) {
        // สำหรับดึงข้อมูลคะแนนสูงสุด และคำนวณเปรียบเทียบเพื่ออัปเดตคะแนนสูงสุด
    }

    public static Player authenticate(String name, String password) {
        // ตรวจสอบการเข้าสู่ระบบ เช็คชื่อ รหัสผ่าน และอ่านไฟล์
        return null;
    }

    public static boolean register(String name, String password) {
        // ลงชื่อเก็บจากการสมัครสมาชิก
        // เช็คว่ามีข้อมูลที่ซ้ำกันไหม ถ้าไม่มี--> เขียนข้อมูลลงในไฟล์ใหม่ 
        return false;
    }

    public void save() {
        // บันทึกคะแนนล่าสุดและคะแนนสูงสุดของผู้เล่นปัจจุบันลงไฟล์
    }

}
