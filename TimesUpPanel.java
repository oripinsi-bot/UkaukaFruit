import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * TimesUpPanel.java -หน้า Game Over
 */
public class TimesUpPanel extends JPanel {
    private final Main game;
    private final JLabel scoreLabel;
    private final JLabel rankLabel;
    private final JLabel bestScoreLabel;
    private final JLabel neighborRanksLabel;

    public TimesUpPanel(Main game) {
        this.game = game;
        setLayout(new GridBagLayout());
        setBackground(Main.BG_DARK);

        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setOpaque(false);

        JLabel title = new JLabel("Game Over");
        title.setFont(new Font("Serif", Font.BOLD, 28));
        title.setForeground(Main.TEXT_LIGHT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("aww...man");
        subtitle.setForeground(Main.TEXT_LIGHT);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        scoreLabel = new JLabel("คะแนนของคุณ: -");
        rankLabel = new JLabel("อันดับที่: -");
        bestScoreLabel = new JLabel("Best score: -");
        neighborRanksLabel = new JLabel(" ");

        for (JLabel l : new JLabel[]{scoreLabel, rankLabel, bestScoreLabel, neighborRanksLabel}) {
            l.setForeground(Main.TEXT_LIGHT);
            l.setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        JButton playAgainBtn = new JButton("Play Again");
        playAgainBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        playAgainBtn.addActionListener(e -> game.showCard(Main.CARD_PLAY));

        JButton leaderboardBtn = new JButton("LeaderBoard");
        leaderboardBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        leaderboardBtn.addActionListener(e -> game.showCard(Main.CARD_LEADERBOARD));

        JButton menuBtn = new JButton("Menu");
        menuBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuBtn.addActionListener(e -> game.showCard(Main.CARD_MENU));

        box.add(title);
        box.add(subtitle);
        box.add(Box.createVerticalStrut(15));
        box.add(scoreLabel);
        box.add(rankLabel);
        box.add(neighborRanksLabel);
        box.add(bestScoreLabel);
        box.add(Box.createVerticalStrut(15));
        box.add(playAgainBtn);
        box.add(Box.createVerticalStrut(8));
        box.add(leaderboardBtn);
        box.add(Box.createVerticalStrut(8));
        box.add(menuBtn);

        add(box);
    }

    public void showResult(int finalScore, Leaderboard leaderboard, Player player) {
        scoreLabel.setText("คะแนนของคุณ: " + finalScore);

        int rank = leaderboard.getRankOf(finalScore);
        rankLabel.setText("อันดับที่: " + rank);

        List<Leaderboard.ScoreEntry> all = leaderboard.getAll();
        StringBuilder sb = new StringBuilder();
        if (rank - 2 >= 0 && rank - 2 < all.size()) {
            sb.append("อันดับ ").append(rank - 1).append(": ").append(all.get(rank - 2).score).append("   ");
        }
        if (rank < all.size()) {
            sb.append("อันดับ ").append(rank + 1).append(": ").append(all.get(rank).score);
        }
        neighborRanksLabel.setText(sb.toString());

        bestScoreLabel.setText("Best score: " + player.getBestScore());
    }
}
