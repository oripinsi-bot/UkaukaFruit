/**
 * MenuPanel.java - หน้าเมนูหลัก
 */

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;


public class MenuPanel extends JPanel{

    /* สิ่งที่ต้องมี :

    แสดงประโยคทักทาย "hello <ชื่อผู้เล่น>" โดยเอาชื่อมาจากไฟล์ players.txt

    ถ้ากดปุ่ม Play จะไปหน้า Play เพื่อเข้าสู่เกม
    ถ้ากดปุ่ม LeaderBoard จะไปไปหน้า Leaderboard เพื่อดูอันดับ
    ถ้ากดปุ่ม Back จะกลับไปกลับหน้า login

    */

    private Main game;
    private JLabel helloLabel;

    // รูปพื้นหลัง
    private BufferedImage backgroundImage;

    public MenuPanel(Main game) {
        //แสดงคำทักทาย

        this.game = game;

        // โหลดรูปพื้นหลัง
        try {
            backgroundImage = ImageIO.read(
                    new File("UkaukaFruit-main/images/background.png")
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout(20, 20));

        // ทำให้ JPanel โปร่งใส
        setOpaque(false);

        JPanel topPanel = new JPanel();

        topPanel.setLayout(
                new BoxLayout(topPanel, BoxLayout.Y_AXIS)
        );

        topPanel.setOpaque(false);

        JLabel title = new JLabel("MENU", SwingConstants.CENTER);

        title.setFont(new Font("Tahoma", Font.BOLD, 32));

        title.setForeground(Color.WHITE);

        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // แสดงชื่อผู้เล่นที่ Login เข้ามา
        helloLabel = new JLabel(
                "hello " + Main.currentPlayerName,
                SwingConstants.CENTER
        );

        helloLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        helloLabel.setForeground(Color.WHITE);
        helloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // เพิ่ม MENU และ hello เข้า topPanel
        topPanel.add(title);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(helloLabel);

        // เพิ่ม topPanel เข้า MenuPanel
        add(topPanel, BorderLayout.NORTH);

        //การทำงานของแต่ละปุ่ม

        // สร้างปุ่ม
        JPanel buttonPanel = new JPanel();

        buttonPanel.setLayout(
                new BoxLayout(buttonPanel, BoxLayout.Y_AXIS)
        );

        //พื้นหลังโปร่งใส
        buttonPanel.setOpaque(false);

        JButton playButton = new JButton("Play");
        JButton leaderBoardButton = new JButton("LeaderBoard");
        JButton backButton = new JButton("Back");

        // กำหนดขนาดปุ่ม
        Dimension buttonSize = new Dimension(180, 45);

        playButton.setPreferredSize(buttonSize);
        playButton.setMaximumSize(buttonSize);

        leaderBoardButton.setPreferredSize(buttonSize);
        leaderBoardButton.setMaximumSize(buttonSize);

        backButton.setPreferredSize(buttonSize);
        backButton.setMaximumSize(buttonSize);

        // จัดตรงกลาง
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        leaderBoardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(playButton);
        buttonPanel.add(Box.createVerticalStrut(15));
        buttonPanel.add(leaderBoardButton);
        buttonPanel.add(Box.createVerticalStrut(15));
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.CENTER);

        // ปุ่ม Play
        playButton.addActionListener(e -> {
            game.showCard(Main.CARD_PLAY);
        });

        // ปุ่ม LeaderBoard
        leaderBoardButton.addActionListener(e -> {
            game.showCard(Main.CARD_LEADERBOARD);
        });

        // ปุ่ม Back
        backButton.addActionListener(e -> {
            game.showCard(Main.CARD_LOGIN);
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

        if (backgroundImage != null) {

            Graphics2D g2 =
                    (Graphics2D) g.create();

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


    //อ่านชื่อผู้เล่นจาก players.txt
    /*private String readPlayerName(String currentPlayerName) {
        try (BufferedReader br = new BufferedReader(new FileReader("players.txt"))) {

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

        }*/
}