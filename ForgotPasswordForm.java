package OOP.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class ForgotPasswordForm extends JFrame {

    public ForgotPasswordForm() {
        setTitle("🛒 Cửa hàng quần áo - Quên mật khẩu");
        setSize(500, 400); // Giống login form
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

        // Panel
        JPanel resetPanel = new JPanel();
        resetPanel.setBounds(70, 50, 360, 300);
        resetPanel.setBackground(new Color(255, 255, 255, 230));
        resetPanel.setLayout(null);
        resetPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 2));
        background.add(resetPanel);

        JLabel lblTitle = new JLabel("Khôi phục mật khẩu", SwingConstants.CENTER);
        lblTitle.setBounds(0, 20, 360, 40);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(0, 102, 204));
        resetPanel.add(lblTitle);

        JLabel lblInfo = new JLabel("Nhập email để nhận link khôi phục:", SwingConstants.CENTER);
        lblInfo.setBounds(0, 70, 360, 30);
        lblInfo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        resetPanel.add(lblInfo);

        // Email
        JTextField txtEmail = createPlaceholderField("Email đăng ký");
        txtEmail.setBounds(60, 110, 240, 35);
        resetPanel.add(txtEmail);

        // Nút Gửi
        JButton btnSend = new JButton("Gửi link");
        btnSend.setBounds(110, 170, 140, 40);
        btnSend.setBackground(new Color(0, 102, 204));
        btnSend.setForeground(Color.WHITE);
        btnSend.setFocusPainted(false);
        btnSend.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnSend.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnSend.addActionListener(e -> {
            String email = txtEmail.getText();
            if (email.equals("Email đăng ký") || email.isEmpty() || !email.contains("@")) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập email hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else {
                // Logic gửi email (hiện tại chỉ là thông báo)
                JOptionPane.showMessageDialog(this, "Đã gửi link khôi phục đến " + email, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                new LoginForm();
                this.dispose();
            }
        });
        resetPanel.add(btnSend);

        // Nút "Quay lại Đăng nhập"
        JButton btnBack = new JButton("Quay lại Đăng nhập");
        btnBack.setBounds(110, 240, 140, 30);
        styleLinkButton(btnBack);
        btnBack.addActionListener(e -> {
            new LoginForm();
            this.dispose();
        });
        resetPanel.add(btnBack);

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