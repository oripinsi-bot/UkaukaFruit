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

    public Main() {
        setTitle("UkaUkaFruit");
        setSize(375, 812);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

       
        add(mainContainer);
    }

    public void showCard(String cardName) {
        cardLayout.show(mainContainer, cardName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}
