package OOP.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ClothingStore extends JFrame {

    // Biến dữ liệu
    private List<Product> products = new ArrayList<>(); // Danh sách gốc (master list)
    private List<Product> filteredProducts = new ArrayList<>(); // Danh sách đã lọc (để hiển thị)
    private List<CartItem> cart = new ArrayList<>();

    // Biến giao diện
    private JPanel productsPanel;
    private JTextField txtSearch; // Nâng lên thành biến toàn cục

    // Biến trạng thái phân trang
    private int currentPage = 1;
    private int itemsPerPage = 8; // Số sản phẩm mỗi trang (như bạn yêu cầu)
    private JLabel lblPageInfo;
    private JButton btnPrev;
    private JButton btnNext;

    public ClothingStore() {
        setTitle("🛍️ Cửa hàng quần áo");
        setSize(900, 700); // Tăng chiều cao một chút cho pagination
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 🧩 Khởi tạo sản phẩm
        products.add(new Product(1, "Áo thun nam", 150000, 10, "C:/Users/Admin/wedquanli/src/images/ao_thun.png"));
        products.add(new Product(2, "Quần jean nữ", 300000, 8, "C:/Users/Admin/wedquanli/src/images/quan_jean.png"));
        products.add(new Product(3, "Áo sơ mi trắng", 200000, 15, "C:/Users/Admin/wedquanli/src/images/so_mi.png"));
        products.add(new Product(4, "Váy công sở", 400000, 6, "C:/Users/Admin/wedquanli/src/images/vay.png"));
        products.add(new Product(5, "Áo hoodie unisex", 350000, 12, "C:/Users/Admin/wedquanli/src/images/hoodie.png"));
        products.add(new Product(6, "Áo khoác da", 500000, 5, "C:/Users/Admin/wedquanli/src/images/ao_khoac.png"));
        products.add(new Product(7, "Giày sneaker", 600000, 7, "C:/Users/Admin/wedquanli/src/images/giay.png"));
        products.add(new Product(8, "Túi xách nữ", 450000, 9, "C:/Users/Admin/wedquanli/src/images/tui.png"));
        products.add(new Product(9, "Áo len cổ lọ", 280000, 10, "C:/Users/Admin/wedquanli/src/images/ao_len.png"));
        products.add(new Product(10, "Quần short kaki", 180000, 15, "C:/Users/Admin/wedquanli/src/images/quan_kaki.png"));



        // 🚀 Panel tìm kiếm (Không thay đổi nhiều)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel lblSearch = new JLabel("Tìm kiếm:");
        txtSearch = new JTextField(15); // Gán vào biến toàn cục
        JButton btnSearch = new JButton("🔍 Tìm");
        JButton btnShowAll = new JButton("Tất cả");
        JButton btnViewCart = new JButton("🧺 Xem giỏ hàng");
        // ... (styling cho btnViewCart) ...
        btnViewCart.setFont(new Font("Arial", Font.BOLD, 16));
        btnViewCart.setBackground(new Color(0, 153, 204));
        btnViewCart.setForeground(Color.WHITE);

        topPanel.add(lblSearch);
        topPanel.add(txtSearch);
        topPanel.add(btnSearch);
        topPanel.add(btnShowAll);
        topPanel.add(btnViewCart);
        add(topPanel, BorderLayout.NORTH);

        // Panel sản phẩm (Không thay đổi)
        productsPanel = new JPanel();
        productsPanel.setLayout(new GridLayout(0, 4, 10, 10));
        productsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(productsPanel);
        add(scrollPane, BorderLayout.CENTER);

        // 🚀 Panel Phân trang (Mới)
        JPanel paginationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPrev = new JButton("<< Trang trước");
        btnNext = new JButton("Trang sau >>");
        lblPageInfo = new JLabel("Trang 1 / 1");
        paginationPanel.add(btnPrev);
        paginationPanel.add(lblPageInfo);
        paginationPanel.add(btnNext);
        add(paginationPanel, BorderLayout.SOUTH); // Thêm ở dưới cùng


        // 🚀 Xử lý sự kiện (THAY ĐỔI LỚN)
        btnViewCart.addActionListener(e -> showCart());

        // Nút Tìm, Tất cả, và Enter sẽ gọi hàm `performSearch`
        btnSearch.addActionListener(e -> performSearch());
        btnShowAll.addActionListener(e -> {
            txtSearch.setText("");
            performSearch();
        });
        txtSearch.addActionListener(e -> performSearch());

        // Nút phân trang
        btnPrev.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                updatePaginatedDisplay();
            }
        });

        btnNext.addActionListener(e -> {
            // Tính tổng số trang (phải tính lại ở đây)
            int totalItems = filteredProducts.size();
            int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);
            if (currentPage < totalPages) {
                currentPage++;
                updatePaginatedDisplay();
            }
        });

        // 🚀 Hiển thị ban đầu
        performSearch(); // Tải tất cả sản phẩm và hiển thị trang 1
        setVisible(true);
    }

    /**
     * 🚀 HÀM MỚI: Lọc danh sách sản phẩm dựa trên ô tìm kiếm
     */
    private void performSearch() {
        String searchTerm = txtSearch.getText().toLowerCase().trim();

        filteredProducts.clear(); // Xóa danh sách lọc cũ

        for (Product p : products) {
            if (p.name.toLowerCase().contains(searchTerm)) {
                filteredProducts.add(p); // Thêm sản phẩm khớp vào ds lọc
            }
        }

        currentPage = 1; // Luôn reset về trang 1 sau mỗi lần tìm kiếm
        updatePaginatedDisplay(); // Cập nhật giao diện
    }

    /**
     * 🚀 HÀM MỚI: (Thay thế hàm updateProductDisplay cũ)
     * Hiển thị 8 sản phẩm của trang hiện tại (currentPage)
     */
    private void updatePaginatedDisplay() {
        productsPanel.removeAll(); // Xóa tất cả sản phẩm cũ

        int totalItems = filteredProducts.size();
        int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);
        if (totalPages == 0) {
            totalPages = 1; // Tránh trường hợp 0/0
        }

        // Cập nhật nhãn và nút
        lblPageInfo.setText("Trang " + currentPage + " / " + totalPages);
        btnPrev.setEnabled(currentPage > 1);
        btnNext.setEnabled(currentPage < totalPages);

        // Tính toán chỉ số sản phẩm cho trang này
        int startIndex = (currentPage - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, totalItems);

        for (int i = startIndex; i < endIndex; i++) {
            Product p = filteredProducts.get(i);
            productsPanel.add(createProductPanel(p));
        }
        // 2. 🚀 THAY ĐỔI: Thêm các PANEL GIẢ để lấp đầy chỗ trống
        int itemsOnThisPage = endIndex - startIndex;
        int dummyPanelsNeeded = itemsPerPage - itemsOnThisPage;

        for (int j = 0; j < dummyPanelsNeeded; j++) {
            JPanel dummy = new JPanel();
            dummy.setOpaque(false); // Quan trọng: làm cho nó trong suốt
            productsPanel.add(dummy); // Thêm vào để lấp đầy
        }

        // Yêu cầu Swing vẽ lại panel
        productsPanel.revalidate();
        productsPanel.repaint();
    }


    // =================================================================
    // CÁC HÀM BÊN DƯỚI GIỮ NGUYÊN (KHÔNG THAY ĐỔI)
    // createProductPanel, buyProduct, showCart, showPayment,
    // static class Product, static class CartItem, main
    // =================================================================


    private JPanel createProductPanel(Product p) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        panel.setBackground(Color.WHITE);

        // 🖼️ Thêm ảnh sản phẩm
        JLabel lblIcon = new JLabel();
        lblIcon.setHorizontalAlignment(SwingConstants.CENTER);
        lblIcon.setPreferredSize(new Dimension(150, 150));

        try {
            ImageIcon icon = new ImageIcon(p.imagePath);
            Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            lblIcon.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            lblIcon.setIcon(new ImageIcon(new BufferedImage(150, 150, BufferedImage.TYPE_INT_RGB)));
        }

        JLabel lblName = new JLabel(p.name, SwingConstants.CENTER);
        lblName.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel lblPrice = new JLabel(String.format("%,.0f VNĐ", p.price), SwingConstants.CENTER);
        lblPrice.setForeground(new Color(0, 102, 51));
        lblPrice.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel lblStock = new JLabel("Còn: " + p.stock, SwingConstants.CENTER);
        lblStock.setFont(new Font("Arial", Font.PLAIN, 12));

        JButton btnBuy = new JButton("🛒 Mua");
        btnBuy.setBackground(new Color(0, 153, 0));
        btnBuy.setForeground(Color.WHITE);
        btnBuy.addActionListener((ActionEvent e) -> buyProduct(p, lblStock));

        JPanel infoPanel = new JPanel(new GridLayout(0, 1));
        infoPanel.add(lblName);
        infoPanel.add(lblPrice);
        infoPanel.add(lblStock);
        infoPanel.add(btnBuy);

        panel.add(lblIcon, BorderLayout.CENTER);
        panel.add(infoPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void buyProduct(Product p, JLabel lblStock) {
        if (p.stock <= 0) {
            JOptionPane.showMessageDialog(this, "Sản phẩm này đã hết hàng!");
            return;
        }

        String qtyStr = JOptionPane.showInputDialog(this, "Nhập số lượng cần mua:");
        if (qtyStr == null) return;

        try {
            int qty = Integer.parseInt(qtyStr);
            if (qty <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!");
                return;
            }
            if (qty > p.stock) {
                JOptionPane.showMessageDialog(this, "Không đủ hàng trong kho!");
                return;
            }

            p.stock -= qty;
            cart.add(new CartItem(p, qty));
            lblStock.setText("Còn: " + p.stock);
            JOptionPane.showMessageDialog(this, "Đã thêm " + qty + " " + p.name + " vào giỏ hàng!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Nhập số lượng hợp lệ!");
        }
    }

    private void showCart() {
        JDialog cartDialog = new JDialog(this, "🧺 Giỏ hàng", true);
        cartDialog.setSize(600, 400);
        cartDialog.setLocationRelativeTo(this);
        cartDialog.setLayout(new BorderLayout());

        String[] cols = {"Tên sản phẩm", "Số lượng", "Thành tiền (VNĐ)"};
        Object[][] data = new Object[cart.size()][3];
        double total = 0;
        for (int i = 0; i < cart.size(); i++) {
            CartItem item = cart.get(i);
            double subtotal = item.product.price * item.quantity;
            total += subtotal;
            data[i][0] = item.product.name;
            data[i][1] = item.quantity;
            data[i][2] = subtotal;
        }

        JTable table = new JTable(data, cols);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);
        cartDialog.add(scrollPane, BorderLayout.CENTER);

        JLabel lblTotal = new JLabel("Tổng tiền: " + String.format("%,.0f VNĐ", total));
        lblTotal.setFont(new Font("Arial", Font.BOLD, 16));

        JButton btnPay = new JButton("💳 Thanh toán");
        btnPay.setBackground(new Color(0, 153, 204));
        btnPay.setForeground(Color.WHITE);
        btnPay.setFont(new Font("Arial", Font.BOLD, 14));
        final double totalAmount = total;
        btnPay.addActionListener(e -> showPayment(totalAmount, cartDialog));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(lblTotal);
        bottomPanel.add(btnPay);

        cartDialog.add(bottomPanel, BorderLayout.SOUTH);
        cartDialog.setVisible(true);
    }

    private void showPayment(double total, JDialog parentDialog) {
        JDialog payDialog = new JDialog(parentDialog, "💰 Thanh toán", true);
        payDialog.setSize(500, 250);
        payDialog.setLocationRelativeTo(parentDialog);
        payDialog.setLayout(new GridLayout(3, 1, 10, 10));

        JPanel methodPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JRadioButton rCash = new JRadioButton("💵 Tiền mặt khi nhận hàng");
        JRadioButton rBank = new JRadioButton("🏦 Chuyển khoản ngân hàng");
        ButtonGroup group = new ButtonGroup();
        group.add(rCash);
        group.add(rBank);

        JComboBox<String> bankBox = new JComboBox<>(new String[]{
                "Vietcombank", "Techcombank", "BIDV", "MB Bank", "Agribank", "VPBank"
        });
        bankBox.setVisible(false);

        rBank.addActionListener(e -> bankBox.setVisible(true));
        rCash.addActionListener(e -> bankBox.setVisible(false));

        methodPanel.add(rCash);
        methodPanel.add(rBank);
        methodPanel.add(bankBox);

        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JLabel lblAmount = new JLabel("Tổng phải trả: " + String.format("%,.0f VNĐ", total));
        lblAmount.setFont(new Font("Arial", Font.BOLD, 14));

        JButton btnConfirm = new JButton("✅ Xác nhận thanh toán");
        btnConfirm.setBackground(new Color(0, 153, 0));
        btnConfirm.setForeground(Color.WHITE);
        btnConfirm.setFont(new Font("Arial", Font.BOLD, 14));

        btnConfirm.addActionListener(e -> {
            if (!rCash.isSelected() && !rBank.isSelected()) {
                JOptionPane.showMessageDialog(payDialog, "Vui lòng chọn phương thức thanh toán!");
                return;
            }
            if (rBank.isSelected()) {
                JOptionPane.showMessageDialog(payDialog, "Bạn đã thanh toán thành công qua " + bankBox.getSelectedItem() + "!");
            } else {
                JOptionPane.showMessageDialog(payDialog, "Đơn hàng sẽ được giao và thanh toán bằng tiền mặt!");
            }
            payDialog.dispose();
            parentDialog.dispose();
            cart.clear();
        });

        totalPanel.add(lblAmount);
        totalPanel.add(btnConfirm);

        payDialog.add(methodPanel);
        payDialog.add(totalPanel);
        payDialog.setVisible(true);
    }

    static class Product {
        int id;
        String name;
        double price;
        int stock;
        String imagePath; // ✅ thêm đường dẫn ảnh

        Product(int id, String name, double price, int stock, String imagePath) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.stock = stock;
            this.imagePath = imagePath;
        }
    }

    static class CartItem {
        Product product;
        int quantity;

        CartItem(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClothingStore::new);
    }
}