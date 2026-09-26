/**
 * GameManager.java - จัดการคะแนนและเวลาที่เหลือของเกม
 */
public class GameManager {
    public static final int GAME_DURATION_SECONDS = 60; // อาจมีการแก้ไขเวลา

    private int score;
    private int timeLeftSeconds;

    public void reset() {
        // รีเซ็ตคะแนนและเวลาที่เหลือของเกมในแต่ละรอบ
        score = 0;
        timeLeftSeconds = GAME_DURATION_SECONDS;
    }

    public void addScore(FallingObject obj) {
        // เพิ่มคะแนน
    }

    public void tickOneSecond() {
        // ลดเวลาที่เหลือของเกมลงทีละ 1 วินาที
        timeLeftSeconds--;
    }

    public boolean isTimeUp() {
        // ตรวจสอบว่าเวลาหมดหรือยัง
        return timeLeftSeconds <= 0;
    }

    public int getScore() {
        // เก็บคะแนนปัจจุบันของเกม
        return score; 
    }
    public int getTimeLeft() {
        // เก็บเวลาที่เหลือของเกม
        return timeLeftSeconds; 
    }
}
