/**
 * MenuPanel.java - หน้าเมนูหลัก
 */

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class MenuPanel extends JPanel{

    /* สิ่งที่ต้องมี : 
    
    แสดงประโยคทักทาย "hello <ชื่อผู้เล่น>" โดยเอาชื่อมาจากไฟล์ players.txt

    ถ้ากดปุ่ม Play จะไปหน้า Play เพื่อเข้าสู่เกม
    ถ้ากดปุ่ม LeaderBoard จะไปไปหน้า Leaderboard เพื่อดูอันดับ
    ถ้ากดปุ่ม Back จะกลับไปกลับหน้า login

    */

    private Main game;
    private JLabel helloLabel;

    public MenuPanel(Main game) {
        //แสดงคำทักทาย

        this.game = game;
        setLayout(new BorderLayout());


        String playerName = readPlayerName();


        helloLabel = new JLabel("hello " + playerName, SwingConstants.CENTER);
        helloLabel.setFont(new Font("Arial", Font.BOLD, 24));

        add(helloLabel, BorderLayout.NORTH);

        //การทำงานของแต่ละปุ่ม

        // สร้างปุ่ม
        JPanel buttonPanel = new JPanel();

        JButton playButton = new JButton("Play");
        JButton leaderBoardButton = new JButton("LeaderBoard");
        JButton backButton = new JButton("Back");

        buttonPanel.add(playButton);
        buttonPanel.add(leaderBoardButton);
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

        //อ่านชื่อผู้เล่นจาก players.txt
   
    private String readPlayerName() {
        try (BufferedReader br = new BufferedReader(new FileReader("players.txt"))) {

            String line = br.readLine();

            if (line != null && !line.trim().isEmpty()) {
                return line.trim();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Player";
    }

}
