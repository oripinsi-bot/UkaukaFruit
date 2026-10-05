/* public class SignUpPanel {
    
    public SignUpPanel(Main game) {

        // ตั้ง username ของตัวผู้เล่น
        // ตั้งรหัสผ่าน
        // มีปุ่ม ok สำหรับกดหลังตั้งเสร็จ
        // มีปุ่มลิงก์กลับไปหน้า Login
    }

    private void onOkClicked() {
        
        // ตรวจสอบว่าไม่ได้เว้นว่างไว้
        // เรียกใช้ระบบสมัครสมาชิก
        // เปลี่ยนไปยังหน้า Login
    }
}
*/

import javax.swing.*;
import java.awt.*;


/***
 * 
 * SignUpPanel หน้าสมัครสมาชิก
 */
public class SignUpPanel extends JPanel {
    private Main game;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton okButton;
    private JButton backButton;
    private Image backgroundImage;

    public SignUpPanel(Main game) {
        this.game = game;

       
        setBackground(Main.BG_DARK);
       /*   try {
            
            backgroundImage = new ImageIcon(getClass().getResource("/images/bg_forest.png")).getImage();
        } catch (Exception e) {
            
            backgroundImage = null;

            setOpaque(false);
        } */

        // กำหนด Layout จัดวางตำแหน่ง Component
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 1. หัวข้อหน้า
        JLabel titleLabel = new JLabel("Singup", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
        titleLabel.setForeground(Main.TEXT_LIGHT);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(titleLabel, gbc);

        gbc.gridwidth = 1;

        // 2. ป้ายและช่องกรอก ชื่อผู้ใช้ (Username)
        JLabel userLabel = new JLabel("username:");
        userLabel.setForeground(Main.TEXT_LIGHT);
        gbc.gridx = 0; gbc.gridy = 1;
        add(userLabel, gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 1;
        add(usernameField, gbc);

        // 3. ป้ายและช่องกรอก รหัสผ่าน (Password)
        JLabel passLabel = new JLabel("password:");
        passLabel.setForeground(Main.TEXT_LIGHT);
        gbc.gridx = 0; gbc.gridy = 2;
        add(passLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1; gbc.gridy = 2;
        add(passwordField, gbc);

        // 4. ปุ่ม ตกลง (OK)
        okButton = new JButton("ok");
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        add(okButton, gbc);

        // 5. ปุ่มลิงก์กลับไปหน้า Login
        backButton = new JButton("Already have an account? Back to Login");
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setForeground(new Color(129, 199, 132));
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        add(backButton, gbc);

        // การดักจับเหตุการณ์ (Event Listeners)
        okButton.addActionListener(e -> onOkClicked());
        backButton.addActionListener(e -> {
            clearFields();
            game.showCard(Main.CARD_LOGIN); // เรียกใช้ showCard ของ Main
        });
    }


    /* @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // วาดภาพยืดให้เต็มขนาด Panel (getWidth(), getHeight())
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            // ถ้าไม่มีรูป ให้แสดงสีพื้นหลังสำรอง
            g.setColor(Main.BG_DARK);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    } */


    private void onOkClicked() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        // ตรวจสอบข้อมูลเบื้องต้น
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter both username and password.",
                "error!",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // เรียกใช้ระบบสมัครสมาชิก
        boolean isSuccess = Player.register(username, password);

        if (isSuccess) {
            JOptionPane.showMessageDialog(this,
                "Registration successful!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            
            clearFields();
            game.showCard(Main.CARD_LOGIN); // สมัครสำเร็จ สลับกลับไปหน้า Login
        } else {
            JOptionPane.showMessageDialog(this,
                "Username is already taken or an error occurred.",
                "Registration Failed",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    // เคลียร์ค่าในช่องกรอกเมื่อเปลี่ยนหน้า
    public void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
    }
}