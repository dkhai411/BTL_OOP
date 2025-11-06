package OOP.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class RegisterForm extends JFrame {

    public RegisterForm() {
        setTitle("🛒 Cửa hàng quần áo - Đăng ký");
        setSize(500, 450); // Cao hơn một chút
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setLayout(new BorderLayout());

        // Gradient background
        JPanel background = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(135, 206, 250), 0, getHeight(), new Color(25, 25, 112));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        background.setLayout(null);
        add(background);

        // Panel đăng ký
        JPanel registerPanel = new JPanel();
        registerPanel.setBounds(70, 50, 360, 350); // Panel cao hơn
        registerPanel.setBackground(new Color(255, 255, 255, 230));
        registerPanel.setLayout(null);
        registerPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 2));
        background.add(registerPanel);

        JLabel lblTitle = new JLabel("Tạo tài khoản", SwingConstants.CENTER);
        lblTitle.setBounds(0, 20, 360, 40);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(0, 102, 204));
        registerPanel.add(lblTitle);

        // Username
        JTextField txtUser = createPlaceholderField("Tên đăng nhập");
        txtUser.setBounds(60, 80, 240, 35);
        registerPanel.add(txtUser);

        // Password
        JPasswordField txtPass = createPlaceholderPasswordField("Mật khẩu");
        txtPass.setBounds(60, 130, 240, 35);
        registerPanel.add(txtPass);

        // Confirm Password
        JPasswordField txtConfirmPass = createPlaceholderPasswordField("Xác nhận mật khẩu");
        txtConfirmPass.setBounds(60, 180, 240, 35);
        registerPanel.add(txtConfirmPass);

        // Nút đăng ký
        JButton btnRegister = new JButton("Đăng ký");
        btnRegister.setBounds(110, 240, 140, 40);
        btnRegister.setBackground(new Color(0, 102, 204));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setFocusPainted(false);
        btnRegister.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnRegister.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        btnRegister.addActionListener(e -> {
            String user = txtUser.getText();
            String pass = String.valueOf(txtPass.getPassword());
            String confirmPass = String.valueOf(txtConfirmPass.getPassword());

            if (user.equals("Tên đăng nhập") || pass.equals("Mật khẩu") || confirmPass.equals("Xác nhận mật khẩu")) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else if (!pass.equals(confirmPass)) {
                JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else {
                // Logic đăng ký thành công (hiện tại chỉ là thông báo)
                JOptionPane.showMessageDialog(this, "Đăng ký thành công! Vui lòng đăng nhập.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                new LoginForm();
                this.dispose();
            }
        });
        registerPanel.add(btnRegister);

        // Nút "Quay lại Đăng nhập"
        JButton btnBack = new JButton("Đã có tài khoản? Đăng nhập");
        btnBack.setBounds(90, 300, 180, 30);
        styleLinkButton(btnBack);
        btnBack.addActionListener(e -> {
            new LoginForm();
            this.dispose();
        });
        registerPanel.add(btnBack);

        // Nút Exit
        JButton btnExit = new JButton("✖");
        btnExit.setBounds(430, 0, 50, 30);
        styleExitButton(btnExit);
        btnExit.addActionListener(e -> System.exit(0));
        background.add(btnExit);

        setVisible(true);
    }
    
    // --- Các hàm Helper để tái sử dụng ---
    
    private JTextField createPlaceholderField(String placeholder) {
        JTextField txtField = new JTextField(placeholder);
        txtField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtField.setForeground(Color.GRAY);
        txtField.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 1));
        txtField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (txtField.getText().equals(placeholder)) {
                    txtField.setText("");
                    txtField.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent e) {
                if (txtField.getText().isEmpty()) {
                    txtField.setForeground(Color.GRAY);
                    txtField.setText(placeholder);
                }
            }
        });
        return txtField;
    }
    
    private JPasswordField createPlaceholderPasswordField(String placeholder) {
        JPasswordField txtPass = new JPasswordField(placeholder);
        txtPass.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPass.setForeground(Color.GRAY);
        txtPass.setEchoChar((char)0);
        txtPass.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 1));
        txtPass.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (String.valueOf(txtPass.getPassword()).equals(placeholder)) {
                    txtPass.setText("");
                    txtPass.setEchoChar('•');
                    txtPass.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent e) {
                if (String.valueOf(txtPass.getPassword()).isEmpty()) {
                    txtPass.setForeground(Color.GRAY);
                    txtPass.setText(placeholder);
                    txtPass.setEchoChar((char)0);
                }
            }
        });
        return txtPass;
    }

    private void styleLinkButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btn.setForeground(new Color(0, 102, 204));
        btn.setContentAreaFilled(false);
        btn.setBorder(null);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
    
    private void styleExitButton(JButton btn) {
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setContentAreaFilled(false);
        btn.setBorder(null);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}