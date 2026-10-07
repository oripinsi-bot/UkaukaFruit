/**
 * GameManager.java - จัดการคะแนนและเวลาที่เหลือของเกม
 * (เวอร์ชันขั้นต่ำตาม class diagram เพื่อให้ PlayPanel ทำงานได้ — ผู้รับผิดชอบปรับต่อได้)
 */
public class GameManager {

    public static final int GAME_SECONDS = 60;

    private int score;
    private int timeLeftSeconds;

    public GameManager() {
        reset();
    }

    public void reset() {
        score = 0;
        timeLeftSeconds = GAME_SECONDS;
    }

    public void addScore(FallingObject obj) {
        score += obj.scoreValue();
    }

    public void tickOneSecond() {
        if (timeLeftSeconds > 0) {
            timeLeftSeconds--;
        }
    }

    public boolean isTimeUp() {
        return timeLeftSeconds <= 0;
    }

    public int getScore() {
        return score;
    }

    public int getTimeLeftSeconds() {
        return timeLeftSeconds;
    }
}