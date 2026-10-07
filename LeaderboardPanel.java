import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class LeaderboardPanel extends JPanel {
    private Main game;

    private JTable table;
    private DefaultTableModel tableModel;

    // รูปพื้นหลัง
    private BufferedImage backgroundImage;

    public LeaderboardPanel(Main game) {
        this.game = game;

        // โหลดรูปพื้นหลัง
        try {
            backgroundImage = ImageIO.read(
                    new File("UkaukaFruit-main/images/background.png")
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ทำให้พื้นหลังของ JPanel โปร่งใส
        setOpaque(false);

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

        // ทำให้พื้นหลังของ ScrollPane โปร่งใส
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        add(scrollPane, BorderLayout.CENTER);

        // สร้างปุ่ม
        JPanel bottomPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER)
        );

        bottomPanel.setOpaque(false);

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

    @Override
        protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.drawImage(
                        backgroundImage,
                        0,
                        0,
                        getWidth(),
                        getHeight(),
                        this
                );
                g2.dispose();
                }
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