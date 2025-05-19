package main.java.restaurant.ui;

import main.java.restaurant.decorator.AddOnDecorator;
import main.java.restaurant.factory.*;
import main.java.restaurant.manager.OrderManager;
import main.java.restaurant.manager.InvoiceManager;
import main.java.restaurant.model.Dish;
import main.java.restaurant.model.Ingredient;
import main.java.restaurant.model.Order;
import main.java.restaurant.model.Invoice;
import main.java.restaurant.model.Promotion;
import main.java.restaurant.strategy.CreditCardPayment;
import main.java.restaurant.strategy.EWalletPayment;
import main.java.restaurant.strategy.PaymentContext;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantUI extends JFrame {
    private OrderManager orderManager;
    private InvoiceManager invoiceManager;
    private List<Dish> menu;
    private Order currentOrder;
    private JTextArea orderTextArea;
    private JComboBox<String> dishComboBox;
    private JComboBox<String> paymentComboBox;
    private JComboBox<String> promotionComboBox;
    private List<Promotion> promotions;
    private List<Ingredient> ingredients;
    private JComboBox<String> ingredientComboBox;
    private JComboBox<String> promotionManageComboBox;

    public RestaurantUI() {
        orderManager = OrderManager.getInstance();
        invoiceManager = InvoiceManager.getInstance();
        menu = new ArrayList<>();
        currentOrder = new Order();
        promotions = new ArrayList<>();
        ingredients = new ArrayList<>();

        // Khởi tạo thực đơn, khuyến mãi và nguyên liệu
        initializeMenu();
        initializePromotions();
        initializeIngredients();

        // Thiết lập giao diện
        setTitle("Restaurant Management System");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Tạo tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab 1: Quản lý đơn hàng
        JPanel orderPanel = new JPanel(new BorderLayout());

        // Panel quản lý thực đơn
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new FlowLayout());
        JLabel dishLabel = new JLabel("Select Dish: ");
        dishComboBox = new JComboBox<>();
        updateDishComboBox();
        JButton addDishButton = new JButton("Add to Order");
        addDishButton.addActionListener(e -> addDishToOrder());
        JButton editDishButton = new JButton("Edit Dish");
        editDishButton.addActionListener(e -> editDish());
        JButton deleteDishButton = new JButton("Delete Dish");
        deleteDishButton.addActionListener(e -> deleteDish());
        JButton addNewDishButton = new JButton("Add New Dish");
        addNewDishButton.addActionListener(e -> addNewDish());
        menuPanel.add(dishLabel);
        menuPanel.add(dishComboBox);
        menuPanel.add(addDishButton);
        menuPanel.add(editDishButton);
        menuPanel.add(deleteDishButton);
        menuPanel.add(addNewDishButton);

        // Panel hiển thị đơn hàng
        orderTextArea = new JTextArea(10, 40);
        orderTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(orderTextArea);

        // Panel thanh toán
        JPanel paymentPanel = new JPanel();
        paymentPanel.setLayout(new FlowLayout());
        JLabel paymentLabel = new JLabel("Payment Method: ");
        paymentComboBox = new JComboBox<>(new String[]{"Credit Card", "E-Wallet"});
        JLabel promotionLabel = new JLabel("Select Promotion: ");
        promotionComboBox = new JComboBox<>();
        updatePromotionComboBox();
        JButton payButton = new JButton("Pay");
        payButton.addActionListener(e -> processPayment());
        paymentPanel.add(promotionLabel);
        paymentPanel.add(promotionComboBox);
        paymentPanel.add(paymentLabel);
        paymentPanel.add(paymentComboBox);
        paymentPanel.add(payButton);

        orderPanel.add(menuPanel, BorderLayout.NORTH);
        orderPanel.add(scrollPane, BorderLayout.CENTER);
        orderPanel.add(paymentPanel, BorderLayout.SOUTH);

        // Tab 2: Quản lý nguyên liệu
        JPanel ingredientPanel = new JPanel(new BorderLayout());
        JPanel ingredientControlPanel = new JPanel(new FlowLayout());
        JLabel ingredientLabel = new JLabel("Ingredients: ");
        ingredientComboBox = new JComboBox<>();
        updateIngredientComboBox();
        JButton addIngredientButton = new JButton("Add Ingredient");
        addIngredientButton.addActionListener(e -> addIngredient());
        JButton deleteIngredientButton = new JButton("Delete Ingredient");
        deleteIngredientButton.addActionListener(e -> deleteIngredient());
        ingredientControlPanel.add(ingredientLabel);
        ingredientControlPanel.add(ingredientComboBox);
        ingredientControlPanel.add(addIngredientButton);
        ingredientControlPanel.add(deleteIngredientButton);
        ingredientPanel.add(ingredientControlPanel, BorderLayout.NORTH);

        // Tab 3: Quản lý khuyến mãi
        JPanel promotionPanel = new JPanel(new BorderLayout());
        JPanel promotionControlPanel = new JPanel(new FlowLayout());
        JLabel promotionManageLabel = new JLabel("Promotions: ");
        promotionManageComboBox = new JComboBox<>();
        
        updatePromotionManageComboBox(); // update promotionManageComboBox ở đoạn này
        
        JButton addPromotionButton = new JButton("Add Promotion");
        addPromotionButton.addActionListener(e -> addPromotion());
        JButton deletePromotionButton = new JButton("Delete Promotion");
        deletePromotionButton.addActionListener(e -> deletePromotion());
        promotionControlPanel.add(promotionManageLabel);
        promotionControlPanel.add(promotionManageComboBox);
        promotionControlPanel.add(addPromotionButton);
        promotionControlPanel.add(deletePromotionButton);
        promotionPanel.add(promotionControlPanel, BorderLayout.NORTH);

        tabbedPane.addTab("Order Management", orderPanel);
        tabbedPane.addTab("Ingredient Management", ingredientPanel);
        tabbedPane.addTab("Promotion Management", promotionPanel);

        add(tabbedPane, BorderLayout.CENTER);
        setVisible(true);
    }

    private void initializeMenu() {
        DishFactory mainCourseFactory = new MainCourseFactory();
        DishFactory dessertFactory = new DessertFactory();
        AbstractFactory asianFactory = new AsianFactory();
        AbstractFactory europeanFactory = new EuropeanFactory();

        Dish pho = asianFactory.createAsianDish();
        pho.addIngredient(new Ingredient("Noodles"));
        pho.addIngredient(new Ingredient("Beef"));
        Dish spaghetti = europeanFactory.createEuropeanDish();
        spaghetti.addIngredient(new Ingredient("Pasta"));
        spaghetti.addIngredient(new Ingredient("Tomato Sauce"));
        Dish dessert = dessertFactory.createDish("Ice Cream", 5.0, "Vanilla flavor");
        dessert.addIngredient(new Ingredient("Milk"));
        dessert.addIngredient(new Ingredient("Sugar"));

//        Dish phoWithExtraBeef = new AddOnDecorator(pho, "Extra Beef", 2.0);

        menu.add(pho);
        menu.add(spaghetti);
        menu.add(dessert);
    }

    private void initializePromotions() {
        promotions.add(new Promotion("10% Off", 0.1));
        promotions.add(new Promotion("20% Off", 0.2));
    }

    private void initializeIngredients() {
        ingredients.add(new Ingredient("Noodles"));
        ingredients.add(new Ingredient("Beef"));
        ingredients.add(new Ingredient("Pasta"));
        ingredients.add(new Ingredient("Tomato Sauce"));
        ingredients.add(new Ingredient("Milk"));
        ingredients.add(new Ingredient("Sugar"));
    }

    private void updateDishComboBox() {
        dishComboBox.removeAllItems();
        for (Dish dish : menu) {
            dishComboBox.addItem(dish.toString());
        }
    }

    private void updatePromotionComboBox() {
        promotionComboBox.removeAllItems();
        for (Promotion promotion : promotions) {
            promotionComboBox.addItem(promotion.toString());
        }
    }

    private void updatePromotionManageComboBox() {
        promotionManageComboBox.removeAllItems();
        for (Promotion promotion : promotions) {
            promotionManageComboBox.addItem(promotion.toString());
        }
    }

    private void updateIngredientComboBox() {
        ingredientComboBox.removeAllItems();
        for (Ingredient ingredient : ingredients) {
            ingredientComboBox.addItem(ingredient.toString());
        }
    }

    private void addDishToOrder() {
        int selectedIndex = dishComboBox.getSelectedIndex();
        if (selectedIndex >= 0) {
            Dish selectedDish = menu.get(selectedIndex);
            currentOrder.addDish(selectedDish);
            orderManager.addOrder(currentOrder);
            updateOrderDisplay();
        }
    }

    private void addNewDish() {
        String newName = JOptionPane.showInputDialog(this, "Enter dish name:");
        String newPriceStr = JOptionPane.showInputDialog(this, "Enter price:");
        String newDescription = JOptionPane.showInputDialog(this, "Enter description:");
        String type = JOptionPane.showInputDialog(this, "Enter type (MainCourse/Dessert):");

        if (newName != null && newPriceStr != null && newDescription != null && type != null) {
            try {
                double newPrice = Double.parseDouble(newPriceStr);
                DishFactory factory;
                if (type.equalsIgnoreCase("MainCourse")) {
                    factory = new MainCourseFactory();
                } else if (type.equalsIgnoreCase("Dessert")) {
                    factory = new DessertFactory();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid type! Use MainCourse or Dessert.");
                    return;
                }
                Dish newDish = factory.createDish(newName, newPrice, newDescription);
                menu.add(newDish);
                updateDishComboBox();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid price format!");
            }
        }
    }

    private void editDish() {
        int selectedIndex = dishComboBox.getSelectedIndex();
        if (selectedIndex >= 0) {
            Dish selectedDish = menu.get(selectedIndex);
            String newName = JOptionPane.showInputDialog(this, "Enter new name:", selectedDish.getName());
            String newPriceStr = JOptionPane.showInputDialog(this, "Enter new price:", selectedDish.getPrice());
            String newDescription = JOptionPane.showInputDialog(this, "Enter new description:", selectedDish.getDescription());

            if (newName != null && newPriceStr != null && newDescription != null) {
                try {
                    double newPrice = Double.parseDouble(newPriceStr);
                    Dish newDish = new Dish(newName, newPrice, newDescription);
                    for (Ingredient ingredient : selectedDish.getIngredients()) {
                        newDish.addIngredient(ingredient);
                    }
                    menu.set(selectedIndex, newDish);
                    updateDishComboBox();
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid price format!");
                }
            }
        }
    }

    private void deleteDish() {
        int selectedIndex = dishComboBox.getSelectedIndex();
        if (selectedIndex >= 0) {
            menu.remove(selectedIndex);
            updateDishComboBox();
        }
    }

    private void addIngredient() {
        String ingredientName = JOptionPane.showInputDialog(this, "Enter ingredient name:");
        if (ingredientName != null && !ingredientName.trim().isEmpty()) {
            ingredients.add(new Ingredient(ingredientName));
            updateIngredientComboBox();
        }
    }

    private void deleteIngredient() {
        int selectedIndex = ingredientComboBox.getSelectedIndex();
        if (selectedIndex >= 0) {
            Ingredient ingredient = ingredients.get(selectedIndex);
            boolean inUse = false;
            for (Dish dish : menu) {
                if (dish.getIngredients().contains(ingredient)) {
                    inUse = true;
                    break;
                }
            }
            if (inUse) {
                JOptionPane.showMessageDialog(this, "Cannot delete ingredient. It is in use by a dish!");
            } else {
                ingredients.remove(selectedIndex);
                updateIngredientComboBox();
            }
        }
    }

    private void addPromotion() {
        String promotionName = JOptionPane.showInputDialog(this, "Enter promotion name:");
        String discountRateStr = JOptionPane.showInputDialog(this, "Enter discount rate (0-1):");
        if (promotionName != null && discountRateStr != null) {
            try {
                double discountRate = Double.parseDouble(discountRateStr);
                if (discountRate >= 0 && discountRate <= 1) {
                    promotions.add(new Promotion(promotionName, discountRate));
                    updatePromotionComboBox();
                    updatePromotionManageComboBox();
                } else {
                    JOptionPane.showMessageDialog(this, "Discount rate must be between 0 and 1!");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid discount rate format!");
            }
        }
    }

    private void deletePromotion() {
        int selectedIndex = promotionManageComboBox.getSelectedIndex();
        if (selectedIndex >= 0) {
            promotions.remove(selectedIndex);
            updatePromotionComboBox();
            updatePromotionManageComboBox();
        }
    }

    private void updateOrderDisplay() {
        orderTextArea.setText("Current Order:\n");
        for (Dish dish : currentOrder.getDishes()) {
            orderTextArea.append(dish.toString() + "\n");
        }
        orderTextArea.append("Total: $" + currentOrder.getTotalPrice());
    }

    private void processPayment() {
        if (currentOrder.getDishes().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please add dishes to the order first!");
            return;
        }

        // Tạo hóa đơn
        Invoice invoice = new Invoice(currentOrder);
        int selectedPromotionIndex = promotionComboBox.getSelectedIndex();
        if (selectedPromotionIndex >= 0) {
            Promotion selectedPromotion = promotions.get(selectedPromotionIndex);
            invoice.applyPromotion(selectedPromotion);
        }
        invoiceManager.addInvoice(invoice);

        // Sử dụng Strategy để thanh toán
        PaymentContext paymentContext = new PaymentContext();
        String paymentMethod = (String) paymentComboBox.getSelectedItem();
        if (paymentMethod.equals("Credit Card")) {
            paymentContext.setPaymentStrategy(new CreditCardPayment());
        } else {
            paymentContext.setPaymentStrategy(new EWalletPayment());
        }
        paymentContext.pay(invoice.getFinalPrice());

        JOptionPane.showMessageDialog(this, "Payment successful!\n" + invoice.toString());
        currentOrder = new Order();
        updateOrderDisplay();
    }
}