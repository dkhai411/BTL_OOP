package OOP.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class LoginForm extends JFrame {

    public LoginForm() {
        setTitle("🛒 Cửa hàng quần áo - Đăng nhập");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true); // bỏ viền cửa sổ
        setLayout(new BorderLayout());

        // Gradient background panel
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

        // Panel login bo tròn
        JPanel loginPanel = new JPanel();
        loginPanel.setBounds(70, 50, 360, 300); // Giữ nguyên kích thước
        loginPanel.setBackground(new Color(255, 255, 255, 230));
        loginPanel.setLayout(null);
        loginPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 2));
        background.add(loginPanel);

        JLabel lblTitle = new JLabel("Đăng nhập", SwingConstants.CENTER);
        lblTitle.setBounds(0, 20, 360, 40);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(0, 102, 204));
        loginPanel.add(lblTitle);

        // Username
        JTextField txtUser = new JTextField("Tên đăng nhập");
        txtUser.setBounds(60, 80, 240, 35);
        txtUser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUser.setForeground(Color.GRAY);
        txtUser.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 1));
        txtUser.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (txtUser.getText().equals("Tên đăng nhập")) {
                    txtUser.setText("");
                    txtUser.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent e) {
                if (txtUser.getText().isEmpty()) {
                    txtUser.setForeground(Color.GRAY);
                    txtUser.setText("Tên đăng nhập");
                }
            }
        });
        loginPanel.add(txtUser);

        // Password
        JPasswordField txtPass = new JPasswordField("Mật khẩu");
        txtPass.setBounds(60, 130, 240, 35);
        txtPass.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPass.setForeground(Color.GRAY);
        txtPass.setEchoChar((char)0); // show placeholder
        txtPass.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (String.valueOf(txtPass.getPassword()).equals("Mật khẩu")) {
                    txtPass.setText("");
                    txtPass.setEchoChar('•');
                    txtPass.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent e) {
                if (String.valueOf(txtPass.getPassword()).isEmpty()) {
                    txtPass.setForeground(Color.GRAY);
                    txtPass.setText("Mật khẩu");
                    txtPass.setEchoChar((char)0);
                }
            }
        });
        loginPanel.add(txtPass);

        // Nút đăng nhập
        JButton btnLogin = new JButton("Đăng nhập");
        btnLogin.setBounds(110, 190, 140, 40);
        btnLogin.setBackground(new Color(0, 102, 204));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogin.addActionListener(e -> {
            String user = txtUser.getText();
            String pass = String.valueOf(txtPass.getPassword());

            if (user.equals("admin") && pass.equals("123")) {
                JOptionPane.showMessageDialog(this, "Đăng nhập thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                new ClothingStore();
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Sai tên đăng nhập hoặc mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Hover hiệu ứng
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(new Color(0, 153, 255));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(new Color(0, 102, 204));
            }
        });
        loginPanel.add(btnLogin);

        // ================ PHẦN MỚI THÊM =================

        // Nút "Quên mật khẩu" (dạng link)
        JButton btnForgot = new JButton("Quên mật khẩu?");
        btnForgot.setBounds(200, 240, 100, 30); // Vị trí bên phải
        btnForgot.setHorizontalAlignment(SwingConstants.RIGHT);
        styleLinkButton(btnForgot);
        btnForgot.addActionListener(e -> {
            new ForgotPasswordForm();
            this.dispose();
        });
        loginPanel.add(btnForgot);

        // Nút "Đăng ký" (dạng link)
        JButton btnRegister = new JButton("Đăng ký");
        btnRegister.setBounds(60, 240, 120, 30); // Vị trí bên trái
        btnRegister.setHorizontalAlignment(SwingConstants.LEFT);
        styleLinkButton(btnRegister);
        btnRegister.addActionListener(e -> {
            new RegisterForm();
            this.dispose();
        });
        loginPanel.add(btnRegister);

        // ================ KẾT THÚC PHẦN MỚI ================

        // Exit button
        JButton btnExit = new JButton("✖");
        btnExit.setBounds(430, 0, 50, 30);
        btnExit.setForeground(Color.WHITE);
        btnExit.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnExit.setContentAreaFilled(false);
        btnExit.setBorder(null);
        btnExit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnExit.addActionListener(e -> System.exit(0));
        background.add(btnExit);

        setVisible(true);
    }

    // Hàm helper để tạo kiểu cho nút giống link
    private void styleLinkButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btn.setForeground(new Color(0, 102, 204));
        btn.setContentAreaFilled(false);
        btn.setBorder(null);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginForm::new);
    }
}