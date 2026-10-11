import javax.swing.*;
import java.awt.*;

/**
 * Main.java - โครงหลักของเกม UkaUkaFruit
 * - สลับหน้าด้วย CardLayout
 * - มีข้อมูลกลางที่ทุกหน้าใช้ร่วมกัน (ผู้เล่น, Leaderboard)
 */
public class Main extends JFrame {

    public static final Color BG_DARK = new Color(24, 34, 26);
    public static final Color TEXT_LIGHT = new Color(220, 225, 210);

    public static final String CARD_LOGIN = "LOGIN";
    public static final String CARD_SIGNUP = "SIGNUP";
    public static final String CARD_MENU = "MENU";
    public static final String CARD_LEADERBOARD = "LEADERBOARD";
    public static final String CARD_PLAY = "PLAY";
    public static final String CARD_TIMEUP = "TIMEUP";

    private CardLayout cardLayout;
    private JPanel mainContainer;

    // ข้อมูลกลาง
    private Player currentPlayer;      // ผู้เล่นที่ login อยู่
    private Leaderboard leaderboard;   // ตารางอันดับ

    // หน้าที่ Main ต้องสั่งงานตอนสลับหน้า
    private MenuPanel menuPanel;
    private LeaderboardPanel leaderboardPanel;
    private PlayPanel playPanel;
    private TimesUpPanel timesUpPanel;

    public Main() {
        setTitle("UkaUkaFruit");
        setSize(375, 812);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        leaderboard = new Leaderboard();

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        menuPanel = new MenuPanel(this);
        leaderboardPanel = new LeaderboardPanel(this);
        playPanel = new PlayPanel(this);
        timesUpPanel = new TimesUpPanel(this);

        mainContainer.add(new LoginPanel(this), CARD_LOGIN);
        mainContainer.add(new SignUpPanel(this), CARD_SIGNUP);
        mainContainer.add(menuPanel, CARD_MENU);
        mainContainer.add(leaderboardPanel, CARD_LEADERBOARD);
        mainContainer.add(playPanel, CARD_PLAY);
        mainContainer.add(timesUpPanel, CARD_TIMEUP);

        add(mainContainer);

        // หมายเหตุ: ไม่เรียก playPanel.startGame() ตรงนี้แล้ว
        // (เมื่อก่อนเกมเริ่มนับเวลาเองตั้งแต่เปิดโปรแกรม ทำให้ผ่านไป 60 วิแล้วเด้งไปหน้า Game Over เอง)
        showCard(CARD_LOGIN);
    }

    public void showCard(String cardName) {
        cardLayout.show(mainContainer, cardName);

        // สั่งงานตอนสลับหน้า
        if (cardName.equals(CARD_LOGIN)) {
            currentPlayer = null;                    // กลับมาหน้า Login = ออกจากระบบ
        } else if (cardName.equals(CARD_MENU)) {  

        } else if (cardName.equals(CARD_LEADERBOARD)) {
            leaderboardPanel.refreshTable();         // โหลดอันดับล่าสุดทุกครั้งที่เปิด
        } else if (cardName.equals(CARD_PLAY)) {
            playPanel.startGame();                   // เริ่มเกมใหม่ทุกครั้งที่เข้าหน้า Play (รวม Play Again)
        }
    }

    // ===== ผู้เล่นที่ login อยู่ =====
    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Leaderboard getLeaderboard() {
        return leaderboard;
    }

    /**
     * เรียกจาก PlayPanel ตอนเวลาหมด:
     * บันทึกคะแนนลง players.txt + scores.txt แล้วไปหน้า Game Over พร้อมแสดงผล
     */
    public void onGameOver(int finalScore) {
        if (currentPlayer != null) {
            currentPlayer.setLastScore(finalScore);   // อัปเดต lastScore / bestScore
            currentPlayer.save();                     // เขียนกลับ players.txt
            leaderboard.addScore(currentPlayer.getName(), finalScore); // เขียน scores.txt
            timesUpPanel.showResult(finalScore, leaderboard, currentPlayer);
        }
        showCard(CARD_TIMEUP);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }

}