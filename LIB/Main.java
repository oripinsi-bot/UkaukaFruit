import javax.swing.*;
import java.awt.*;

/**
 * Main.java - โครงหลักของเกม UkaUkaFruit
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

    public static String currentPlayerName = "";

    private MenuPanel menuPanel;
    private PlayPanel playPanel;
    private TimesUpPanel timesUpPanel;
    private LeaderboardPanel leaderboardPanel; 
    private Player currentPlayer;

    public Main() {
        setTitle("UkaUkaFruit");
        setSize(375, 812);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        // สร้าง Instance ครั้งเดียวแล้วนำไปใช้
        menuPanel = new MenuPanel(this);
        playPanel = new PlayPanel(this);
        timesUpPanel = new TimesUpPanel(this);
        leaderboardPanel = new LeaderboardPanel(this); // สร้างและเก็บไว้ในตัวแปร

        mainContainer.add(new LoginPanel(this), CARD_LOGIN);
        mainContainer.add(new SignUpPanel(this), CARD_SIGNUP);
        mainContainer.add(menuPanel, CARD_MENU);
        mainContainer.add(playPanel, CARD_PLAY);
        mainContainer.add(leaderboardPanel, CARD_LEADERBOARD); // ใส่ตัวแปร leaderboardPanel
        mainContainer.add(timesUpPanel, CARD_TIMEUP);          // ใส่ตัวแปร timesUpPanel (ไม่ใส่ new ซ้ำ)

        add(mainContainer);
        showCard(CARD_LOGIN);
    }

    public void showCard(String cardName) {

        if (cardName.equals(CARD_MENU) && menuPanel != null) {
            menuPanel.updatePlayerName();
        }

        // สั่งให้อัปเดตตาราง Leaderboard ทุกครั้งที่สลับมาหน้านี้
        if (cardName.equals(CARD_LEADERBOARD) && leaderboardPanel != null) {
            leaderboardPanel.refreshTable();
        }

        cardLayout.show(mainContainer, cardName);

        if (cardName.equals(CARD_PLAY) && playPanel != null) {
            SwingUtilities.invokeLater(() -> {
                playPanel.startGame();
            });
        }
    }

    public TimesUpPanel getTimesUpPanel() {
        return timesUpPanel;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}
