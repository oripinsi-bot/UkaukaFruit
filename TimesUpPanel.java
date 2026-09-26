import javax.swing.JPanel;

/**
 * TimesUpPanel.java -หน้า Game Over
 */
public class TimesUpPanel extends JPanel {
    public TimesUpPanel(Main game) {
        // หน้าจอ Game Over แสดงผลเมื่อเวลาหมด
        // มีการแสดงคะแนนล่าสุดจากที่เล่น, อันดับ (พร้อมอันดับก่อนหน้า-ถัดไป), best score
        // มีปุ่ม Play Again -> ไปหน้า Play
        // มีปุ่ม LeaderBoard -> ไปหน้า Leaderboard
        // และมีปุ่ม Menu -> ไปหน้า Menu
    }

    public void showResult(int finalScore) {
        // อัปเดตข้อความบนหน้าจอตามคะแนนที่ได้
    }
}
