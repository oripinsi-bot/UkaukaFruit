import javax.swing.*;
import java.awt.*;
/**
 * LoginPanel.java - หน้า Login
 */
/*public class LoginPanel extends JPanel {
public LoginPanel(Main game) {
        
        // โหลดรูปภาพพื้นหลังธีมป่าลึกเข้ามา
        // ใส่ username ที่เคยสมัครไว้
        // ใส่รหัสผ่านตามที่ตั้ง
        // ปุ่ม ok หลังกรอกข้อมูลเสร็จ
        // ลิงก์ไปหน้า Sign up
    }

    private void onOkClicked() {
    
        // ตรวจสอบว่ากรอกข้อมูลครบถ้วนหรือไม่
        // ตรวจสอบกรอกข้อมูลถูกต้อง และตรงกับที่เคยใส่ไว้ในหน้าสมัครสมาชิกหรือไม่
           // ถ้าถูกต้อง -> นำผู้ใช้ไปยังหน้าหลัก/เมนูของเกม
           // ถ้าผิด -> แสดงข้อความแจ้งเตือน Error
        
    }
}*/


public class LoginPanel extends JPanel {
    private Main game;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton okButton;
    private JButton signUpButton;

    public LoginPanel(Main game) {
        this.game = game;

        // กำหนดสีพื้นหลังธีมเดียวกับ Main
        setBackground(Main.BG_DARK);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 1. หัวข้อหน้า
        JLabel titleLabel = new JLabel("Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
        titleLabel.setForeground(Main.TEXT_LIGHT);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(titleLabel, gbc);

        gbc.gridwidth = 1;

        // 2. ช่องกรอก Username
        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Main.TEXT_LIGHT);
        gbc.gridx = 0; gbc.gridy = 1;
        add(userLabel, gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 1;
        add(usernameField, gbc);

        // 3. ช่องกรอก Password
        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Main.TEXT_LIGHT);
        gbc.gridx = 0; gbc.gridy = 2;
        add(passLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1; gbc.gridy = 2;
        add(passwordField, gbc);

        // 4. ปุ่ม OK
        okButton = new JButton("OK");
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        add(okButton, gbc);

        // 5. ปุ่มลิงก์ไปหน้า SignUp
        signUpButton = new JButton("Don't have an account? Sign up");
        signUpButton.setBorderPainted(false);
        signUpButton.setContentAreaFilled(false);
        signUpButton.setForeground(new Color(129, 199, 132));
        signUpButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        add(signUpButton, gbc);

        // Event Listeners
        okButton.addActionListener(e -> onOkClicked());
        signUpButton.addActionListener(e -> {
            clearFields();
            game.showCard(Main.CARD_SIGNUP); // สลับไปหน้า SignUp
        });
    }

    private void onOkClicked() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter both username and password.",
                "Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // ตรวจสอบข้อมูลกับไฟล์ players.txt ผ่านคลาส Player
        Player player = Player.authenticate(username, password);

        if (player != null) {
            clearFields();
            // TODO: หากมีเมธอดเก็บผู้เล่นใน Main สามารถเปิดใช้บรรทัดล่างได้
            // game.setCurrentPlayer(player); 
            game.showCard(Main.CARD_MENU); // เข้าสู่ระบบสำเร็จ สลับไปหน้า Menu
        } else {
            JOptionPane.showMessageDialog(this,
                "Invalid username or password.",
                "Login Failed",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
    }
}