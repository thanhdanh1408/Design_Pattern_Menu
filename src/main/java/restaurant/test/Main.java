package main.java.restaurant.test;

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
import main.java.restaurant.strategy.CashPayment;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private OrderManager orderManager;
    private InvoiceManager invoiceManager;
    private List<Dish> menu;
    private Order currentOrder;
    private List<Promotion> promotions;
    private List<Ingredient> ingredients;

    public Main() {
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

        runConsoleApp();
    }

    private void initializeMenu() {
        DishFactory mainCourseFactory = new MainCourseFactory();
        DishFactory dessertFactory = new DessertFactory();
        AbstractFactory asianFactory = new AsianFactory();
        AbstractFactory europeanFactory = new EuropeanFactory();

        Dish pho = asianFactory.createAsianDish();
        pho.addIngredient(new Ingredient("Mì"));
        pho.addIngredient(new Ingredient("Thịt bò"));
        Dish spaghetti = europeanFactory.createEuropeanDish();
        spaghetti.addIngredient(new Ingredient("Mì Ý"));
        spaghetti.addIngredient(new Ingredient("Sốt cà chua"));
        Dish dessert = dessertFactory.createDish("Kem", 5.0, "Hương vani");
        dessert.addIngredient(new Ingredient("Sữa"));
        dessert.addIngredient(new Ingredient("Đường"));

        menu.add(pho);
        menu.add(spaghetti);
        menu.add(dessert);
    }

    private void initializePromotions() {
        promotions.add(new Promotion("Giảm 10%", 0.1));
        promotions.add(new Promotion("Giảm 20%", 0.2));
    }

    private void initializeIngredients() {
        ingredients.add(new Ingredient("Mì"));
        ingredients.add(new Ingredient("Thịt bò"));
        ingredients.add(new Ingredient("Mì Ý"));
        ingredients.add(new Ingredient("Sốt cà chua"));
        ingredients.add(new Ingredient("Sữa"));
        ingredients.add(new Ingredient("Đường"));
    }

    private void runConsoleApp() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            displayMenu();
            System.out.print("Chọn chức năng (1-5, 0 để thoát): ");
            int choice;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Xóa ký tự newline
                if (choice < 0 || choice > 5) {
                    System.out.println("Lựa chọn không hợp lệ! Vui lòng nhập số từ 0 đến 5.");
                    continue;
                }
            } else {
                System.out.println("Lựa chọn không hợp lệ! Vui lòng nhập số nguyên.");
                scanner.nextLine(); // Xóa đầu vào không hợp lệ
                continue;
            }

            switch (choice) {
                case 1:
                    addDishToOrder(scanner);
                    break;
                case 2:
                    addNewDish(scanner);
                    break;
                case 3:
                    addPromotion(scanner);
                    break;
                case 4:
                    processPayment(scanner);
                    break;
                case 5:
                    displayOrder();
                    break;
                case 0:
                    System.out.println("Thoát chương trình.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n=== HỆ THỐNG QUẢN LÝ NHÀ HÀNG ===");
        System.out.println("1. Thêm món vào đơn hàng");
        System.out.println("2. Thêm món mới");
        System.out.println("3. Thêm khuyến mãi");
        System.out.println("4. Thanh toán");
        System.out.println("5. Xem đơn hàng");
        System.out.println("0. Thoát");
    }

    private void addDishToOrder(Scanner scanner) {
        System.out.println("\nDanh sách món:");
        for (int i = 0; i < menu.size(); i++) {
            System.out.println((i + 1) + ". " + menu.get(i).toString());
        }
        System.out.print("Chọn số món để thêm vào đơn (hoặc 0 để quay lại): ");
        int dishChoice = scanner.nextInt();
        scanner.nextLine(); // Xóa ký tự newline

        if (dishChoice > 0 && dishChoice <= menu.size()) {
            Dish selectedDish = menu.get(dishChoice - 1);
            currentOrder.addDish(selectedDish);
            orderManager.addOrder(currentOrder);
            System.out.println("Đã thêm món: " + selectedDish.toString());
        } else if (dishChoice != 0) {
            System.out.println("Lựa chọn không hợp lệ!");
        }
    }

    private void addNewDish(Scanner scanner) {
        System.out.print("Nhập tên món: ");
        String newName = scanner.nextLine();
        System.out.print("Nhập giá: ");
        double newPrice = scanner.nextDouble();
        scanner.nextLine(); // Xóa ký tự newline
        System.out.print("Nhập mô tả: ");
        String newDescription = scanner.nextLine();
        System.out.println("Chọn loại:");
        System.out.println("1. Món chính");
        System.out.println("2. Món tráng miệng");
        System.out.print("Chọn số: ");
        int typeChoice = scanner.nextInt();
        scanner.nextLine(); // Xóa ký tự newline
        System.out.println("Chọn nguyên liệu:");
        for (int i = 0; i < ingredients.size(); i++) {
            System.out.println((i + 1) + ". " + ingredients.get(i).toString());
        }
        System.out.print("Chọn số nguyên liệu (hoặc 0 nếu không chọn): ");
        int ingredientChoice = scanner.nextInt();
        scanner.nextLine(); // Xóa ký tự newline

        DishFactory factory;
        if (typeChoice == 1) {
            factory = new MainCourseFactory();
        } else if (typeChoice == 2) {
            factory = new DessertFactory();
        } else {
            System.out.println("Loại không hợp lệ!");
            return;
        }

        Dish newDish = factory.createDish(newName, newPrice, newDescription);
        if (ingredientChoice > 0 && ingredientChoice <= ingredients.size()) {
            newDish.addIngredient(ingredients.get(ingredientChoice - 1));
        }
        menu.add(newDish);
        System.out.println("Đã thêm món mới: " + newDish.toString());
    }

    private void addPromotion(Scanner scanner) {
        System.out.print("Nhập tên khuyến mãi: ");
        String name = scanner.nextLine();
        System.out.print("Nhập tỷ lệ giảm (0-1): ");
        double discountRate = getValidDoubleInput(scanner);

        if (discountRate >= 0 && discountRate <= 1) {
            promotions.add(new Promotion(name, discountRate));
            System.out.println("Đã thêm khuyến mãi: " + name + " (" + (discountRate * 100) + "% off)");
        } else {
            System.out.println("Tỷ lệ giảm phải từ 0 đến 1!");
        }
    }

    private void processPayment(Scanner scanner) {
        if (currentOrder.getDishes().isEmpty()) {
            System.out.println("Vui lòng thêm món vào đơn hàng trước!");
            return;
        }

        Invoice invoice = new Invoice(currentOrder);
        System.out.println("Danh sách khuyến mãi:");
        for (int i = 0; i < promotions.size(); i++) {
            System.out.println((i + 1) + ". " + promotions.get(i).toString());
        }
        System.out.print("Chọn số khuyến mãi (hoặc 0 nếu không áp dụng): ");
        int promoChoice = scanner.nextInt();
        scanner.nextLine(); // Xóa ký tự newline

        if (promoChoice > 0 && promoChoice <= promotions.size()) {
            invoice.applyPromotion(promotions.get(promoChoice - 1));
        }

        System.out.println("Chọn phương thức thanh toán:");
        System.out.println("1. Thẻ tín dụng");
        System.out.println("2. Ví điện tử");
        System.out.println("3. Tiền mặt");
        System.out.print("Chọn số: ");
        int paymentChoice = scanner.nextInt();
        scanner.nextLine(); // Xóa ký tự newline

        PaymentContext paymentContext = new PaymentContext();
        if (paymentChoice == 1) {
            paymentContext.setPaymentStrategy(new CreditCardPayment());
        } else if (paymentChoice == 2) {
            paymentContext.setPaymentStrategy(new EWalletPayment());
        }
          else if (paymentChoice == 3) {
                paymentContext.setPaymentStrategy(new CashPayment());
        } else {
            System.out.println("Phương thức thanh toán không hợp lệ!");
            return;
        }

        paymentContext.pay(invoice.getFinalPrice());
        invoiceManager.addInvoice(invoice);
        System.out.println("Thanh toán thành công! " + invoice.toString());
        currentOrder = new Order();
    }

    private void displayOrder() {
        System.out.println("\nĐơn hàng hiện tại:");
        for (Dish dish : currentOrder.getDishes()) {
            System.out.println(dish.toString());
        }
        System.out.println("Tổng: $" + currentOrder.getTotalPrice());
    }
    
    private int getValidIntInput(Scanner scanner, int min, int max) {
        while (true) {
            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                scanner.nextLine(); // Xóa ký tự newline
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.println("Lựa chọn không hợp lệ! Vui lòng nhập số từ " + min + " đến " + max + ": ");
                }
            } else {
                System.out.println("Lựa chọn không hợp lệ! Vui lòng nhập số nguyên.");
                scanner.nextLine(); // Xóa đầu vào không hợp lệ
            }
        }
    }

    private double getValidDoubleInput(Scanner scanner) {
        while (true) {
            if (scanner.hasNextDouble()) {
                double value = scanner.nextDouble();
                scanner.nextLine(); // Xóa ký tự newline
                return value;
            } else {
                System.out.println("Lựa chọn không hợp lệ! Vui lòng nhập số thực.");
                scanner.nextLine(); // Xóa đầu vào không hợp lệ
            }
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}