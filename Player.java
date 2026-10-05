import java.io.*;
import java.util.ArrayList;
import java.util.List;



/**
 * Player.java - ข้อมูลผู้เล่น
 */
/* public class Player {
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

} */

    public class Player {
    private static final String FILE_NAME = "players.txt";

    private String name;
    private String password;
    private int lastScore;
    private int bestScore;

    public Player(String name, String password) {
        this(name, password, 0, 0);
    }

    public Player(String name, String password, int lastScore, int bestScore) {
        this.name = name;
        this.password = password;
        this.lastScore = lastScore;
        this.bestScore = bestScore;
    }

    public String getName() { return name; }
    public int getLastScore() { return lastScore; }
    public int getBestScore() { return bestScore; }

    public void setLastScore(int score) {
        this.lastScore = score;
        if (score > this.bestScore) {
            this.bestScore = score;
        }
    }

    /**
     * อ่านไฟล์ players.txt เพื่อเช็ก Username และ Password
     */
    public static Player authenticate(String name, String password) {
        File file = new File(FILE_NAME);
        if (!file.exists()) return null;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    String fileUser = parts[0].trim();
                    String filePass = parts[1].trim();

                    if (fileUser.equals(name) && filePass.equals(password)) {
                        int last = Integer.parseInt(parts[2].trim());
                        int best = Integer.parseInt(parts[3].trim());
                        return new Player(fileUser, filePass, last, best);
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * สมัครสมาชิกแล้วเขียนบันทึกลงต่อท้ายไฟล์ players.txt
     */
    public static boolean register(String name, String password) {
        File file = new File(FILE_NAME);

        // ตรวจสอบชื่อซ้ำ
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length > 0 && parts[0].trim().equalsIgnoreCase(name.trim())) {
                        return false; // พบชื่อซ้ำ
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        // เขียนข้อมูลใหม่ลงต่อท้ายไฟล์ (format: name,password,lastScore,bestScore)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(name + "," + password + ",0,0");
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * อัปเดตคะแนนล่าสุดกลับลงไฟล์ players.txt
     */
    public void save() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && parts[0].trim().equals(this.name)) {
                    lines.add(this.name + "," + this.password + "," + this.lastScore + "," + this.bestScore);
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
