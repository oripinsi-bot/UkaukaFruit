import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LeaderboardPanel extends JPanel {
    private Main game;

    private JTable table;
    private DefaultTableModel tableModel;

    public LeaderboardPanel(Main game) {
        this.game = game;

        setBackground(Main.BG_DARK);
        setLayout(new BorderLayout());
        setLayout(new BorderLayout(20, 20));

        JLabel title = new JLabel("LEADERBOARD", SwingConstants.CENTER);

        title.setFont(new Font("Tahoma", Font.BOLD, 32));
        title.setForeground(Color.WHITE);

        add(title, BorderLayout.NORTH);

        //สร้างตาราง
        tableModel = new DefaultTableModel(
                new Object[]{"Rank", "Name", "Score"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        //สร้างตารางจาก tableModel
        table = new JTable(tableModel);

        table.setFont(new Font("Tahoma", Font.PLAIN, 18));

        //ความสูงของแต่ละแถว
        table.setRowHeight(35);

        table.getTableHeader().setFont(
                new Font("Tahoma", Font.BOLD, 18)
        );

        //ห้ามสลับคอลัมน์
        table.getTableHeader().setReorderingAllowed(false);

        table.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        //อยู่ตรงกลาง
        table.getColumnModel().getColumn(0)
                .setPreferredWidth(80);

        table.getColumnModel().getColumn(1)
                .setPreferredWidth(250);

        table.getColumnModel().getColumn(2)
                .setPreferredWidth(150);

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        // สร้างปุ่ม
        JPanel bottomPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER)
        );

        bottomPanel.setBackground(Main.BG_DARK);

        JButton backButton = new JButton("BACK");

        backButton.setFont(
                new Font("Tahoma", Font.BOLD, 18)
        );

        backButton.setPreferredSize(
                new Dimension(150, 45)
        );

        backButton.addActionListener(e -> {

            // กลับไปหน้า Menu
            game.showCard(game.CARD_MENU);
        });

        bottomPanel.add(backButton);

        add(bottomPanel, BorderLayout.SOUTH);

        // โหลดข้อมูลครั้งแรก
        refreshTable();

    }

    public void refreshTable() {
        //อัปเดตข้อมูล Leaderboard
        tableModel.setRowCount(0);

        Leaderboard leaderboard = new Leaderboard();

        List<Leaderboard.ScoreEntry> entries =
                leaderboard.getAll();

        int rank = 1;

        for (Leaderboard.ScoreEntry entry : entries) {

            tableModel.addRow(new Object[]{
                    rank,
                    entry.getName(),
                    entry.getScore()
            });

            rank++;
        }
    }
}