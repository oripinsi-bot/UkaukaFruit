/**
 * MenuPanel.java - หน้าเมนูหลัก
 */

import java.awt.*;
import javax.swing.*;

public class MenuPanel extends JPanel {

    /* สิ่งที่ต้องมี :

    แสดงประโยคทักทาย "hello <ชื่อผู้เล่น>" โดยเอาชื่อมาจากไฟล์ players.txt

    ถ้ากดปุ่ม Play จะไปหน้า Play เพื่อเข้าสู่เกม
    ถ้ากดปุ่ม LeaderBoard จะไปไปหน้า Leaderboard เพื่อดูอันดับ
    ถ้ากดปุ่ม Back จะกลับไปกลับหน้า login

    */

    private final Main game;
    private JLabel helloLabel;

    // รูปพื้นหลัง
    private Image background;

    public MenuPanel(Main game) {

        // แสดงคำทักทาย
        this.game = game;

        setOpaque(false);
        try {
        background = new ImageIcon("images/background.png").getImage();
    } catch (Exception e) {
        System.err.println("do not dowload backgound " + e.getMessage());
    }
      
        // ทำให้ JPanel โปร่งใส
        setLayout(new GridBagLayout());
        setOpaque(false);

        // รวมข้อความและปุ่มทั้งหมดไว้ใน Panel เดียว
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(
            new BoxLayout(contentPanel, BoxLayout.Y_AXIS)
        );
        contentPanel.setOpaque(false);

        // =========================
        // ส่วนข้อความ MENU
        // =========================
        JPanel topPanel = new JPanel();
        topPanel.setLayout(
            new BoxLayout(topPanel, BoxLayout.Y_AXIS)
        );
        topPanel.setOpaque(false);

        JLabel title = new JLabel("MENU", SwingConstants.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 48));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // แสดงชื่อผู้เล่นที่ Login เข้ามา
        helloLabel = new JLabel(
            "hello " + Main.currentPlayerName,
            SwingConstants.CENTER
        );

        helloLabel.setFont(new Font("Tahoma", Font.BOLD, 36));
        helloLabel.setForeground(Color.WHITE);
        helloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // เพิ่ม MENU และ hello เข้า topPanel
        topPanel.add(title);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(helloLabel);

        // เพิ่มส่วนข้อความลงใน contentPanel
        contentPanel.add(topPanel);
        contentPanel.add(Box.createVerticalStrut(35));

        // =========================
        // ส่วนปุ่มเมนู
        // =========================

        // สร้างปุ่ม
        JPanel buttonPanel = new JPanel();

        buttonPanel.setLayout(
            new BoxLayout(buttonPanel, BoxLayout.Y_AXIS)
        );

        // พื้นหลังโปร่งใส
        buttonPanel.setOpaque(false);

        JButton playButton = new JButton("Play");
        JButton leaderBoardButton = new JButton("LeaderBoard");
        JButton backButton = new JButton("Back");

        // กำหนดขนาดปุ่ม
        Dimension buttonSize = new Dimension(270, 66);

        JButton[] menuButtons = {
            playButton,
            leaderBoardButton,
            backButton
        };

        for (JButton button : menuButtons) {

            button.setPreferredSize(buttonSize);
            button.setMinimumSize(buttonSize);
            button.setMaximumSize(buttonSize);

            button.setFont(new Font("Tahoma", Font.BOLD, 18));

            // จัดตรงกลาง
            button.setAlignmentX(Component.CENTER_ALIGNMENT);

            buttonPanel.add(button);
            buttonPanel.add(Box.createVerticalStrut(20));
        }

        // ลบช่องว่างหลังปุ่มสุดท้าย
        buttonPanel.remove(
            buttonPanel.getComponentCount() - 1
        );

        // เพิ่มกลุ่มปุ่มใต้ข้อความ
        contentPanel.add(buttonPanel);

        // จัดข้อความและปุ่มให้อยู่กลางหน้าจอ
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        add(contentPanel, gbc);

        // การทำงานของแต่ละปุ่ม

        // ปุ่ม Play
        playButton.addActionListener(e -> {
            this.game.showCard(Main.CARD_PLAY);
        });

        // ปุ่ม LeaderBoard
        leaderBoardButton.addActionListener(e -> {
            this.game.showCard(Main.CARD_LEADERBOARD);
        });

        // ปุ่ม Back
        backButton.addActionListener(e -> {
            this.game.showCard(Main.CARD_LOGIN);
        });
    }

    // อัปเดตชื่อผู้เล่นเมื่อเข้าสู่หน้า Menu
    public void updatePlayerName() {
        helloLabel.setText(
            "hello " + Main.currentPlayerName
        );
    }

    // วาดพื้นหลัง
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (background != null) {

            Graphics2D g2 = (Graphics2D) g.create();

            g2.drawImage(
                background,
                0,
                0,
                getWidth(),
                getHeight(),
                this
            );

            g2.dispose();
        }
    }

    // อ่านชื่อผู้เล่นจาก players.txt
    /*
    private String readPlayerName(String currentPlayerName) {
        try (BufferedReader br =
                new BufferedReader(new FileReader("players.txt"))) {

            String line;

            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                // แยกข้อมูลด้วย ,
                String[] data = line.split(",");

                // data[0] = ชื่อผู้เล่น
                String playerName = data[0].trim();

                // ถ้าชื่อตรงกับคนที่ Login
                if (playerName.equals(currentPlayerName)) {
                    return playerName;
                }
            }

            return "Player";

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    */
}
