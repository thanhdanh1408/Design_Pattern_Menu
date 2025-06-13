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
    private List<Supplier> suppliers;

    public Main() {
        orderManager = OrderManager.getInstance();
        invoiceManager = InvoiceManager.getInstance();
        menu = new ArrayList<>();
        currentOrder = new Order();
        promotions = new ArrayList<>();
        ingredients = new ArrayList<>();
        suppliers = new ArrayList<>();

        // Khởi tạo thực đơn, khuyến mãi, nguyên liệu và nhà cung cấp
        initializeMenu();
        initializePromotions();
        initializeIngredients();
        initializeSuppliers();

        runConsoleApp();
    }

    // Lớp Supplier (giả định)
    static class Supplier {
        private String name;
        private List<Ingredient> suppliedIngredients;

        public Supplier(String name) {
            this.name = name;
            this.suppliedIngredients = new ArrayList<>();
        }

        public String getName() {
            return name;
        }

        public void addIngredient(Ingredient ingredient) {
            if (!suppliedIngredients.contains(ingredient)) {
                suppliedIngredients.add(ingredient);
            }
        }

        public List<Ingredient> getSuppliedIngredients() {
            return suppliedIngredients;
        }

        @Override
        public String toString() {
            return name + " (Nguyên liệu: " + suppliedIngredients.size() + " loại)";
        }
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

    private void initializeSuppliers() {
        Supplier supplier1 = new Supplier("Công ty A");
        supplier1.addIngredient(ingredients.get(0)); // Mì
        supplier1.addIngredient(ingredients.get(1)); // Thịt bò
        Supplier supplier2 = new Supplier("Công ty B");
        supplier2.addIngredient(ingredients.get(2)); // Mì Ý
        supplier2.addIngredient(ingredients.get(3)); // Sốt cà chua
        suppliers.add(supplier1);
        suppliers.add(supplier2);
    }

    private void runConsoleApp() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            displayMenu();
            System.out.print("Chọn chức năng (1-12, 0 để thoát): ");
            int choice;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Xóa ký tự newline
                if (choice < 0 || choice > 12) {
                    System.out.println("Lựa chọn không hợp lệ! Vui lòng nhập số từ 0 đến 12.");
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
                    editDish(scanner);
                    break;
                case 4:
                    deleteDish(scanner);
                    break;
                case 5:
                    addPromotion(scanner);
                    break;
                case 6:
                    displayOrder();
                    break;
                case 7:
                    cancelOrder(scanner);
                    break;
                case 8:
                    processPayment(scanner);
                    break;
                case 9:
                    addNewIngredient(scanner);
                    break;
                case 10:
                    addNewSupplier(scanner);
                    break;
                case 11:
                    editSupplier(scanner);
                    break;
                case 12:
                    deleteSupplier(scanner);
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
        System.out.println("3. Sửa món");
        System.out.println("4. Xóa món");
        System.out.println("5. Thêm khuyến mãi");
        System.out.println("6. Xem đơn hàng");
        System.out.println("7. Hủy đơn hàng");
        System.out.println("8. Thanh toán");
        System.out.println("9. Thêm nguyên liệu mới");
        System.out.println("10. Thêm nhà cung cấp");
        System.out.println("11. Sửa nhà cung cấp");
        System.out.println("12. Xóa nhà cung cấp");
        System.out.println("0. Thoát");
    }

    private void addDishToOrder(Scanner scanner) {
        System.out.println("\nDanh sách món:");
        for (int i = 0; i < menu.size(); i++) {
            System.out.println((i + 1) + ". " + menu.get(i).toString());
        }
        System.out.print("Chọn số món để thêm vào đơn (hoặc 0 để quay lại): ");
        int dishChoice = getValidIntInput(scanner, 0, menu.size());

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
        double newPrice = getValidDoubleInput(scanner);
        System.out.print("Nhập mô tả: ");
        String newDescription = scanner.nextLine();
        System.out.println("Chọn loại:");
        System.out.println("1. Món chính");
        System.out.println("2. Món tráng miệng");
        System.out.print("Chọn số: ");
        int typeChoice = getValidIntInput(scanner, 1, 2);
        System.out.println("Chọn nguyên liệu:");
        for (int i = 0; i < ingredients.size(); i++) {
            System.out.println((i + 1) + ". " + ingredients.get(i).toString());
        }
        System.out.print("Chọn số nguyên liệu (hoặc 0 nếu không chọn): ");
        int ingredientChoice = getValidIntInput(scanner, 0, ingredients.size());

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
        int promoChoice = getValidIntInput(scanner, 0, promotions.size());

        if (promoChoice > 0 && promoChoice <= promotions.size()) {
            invoice.applyPromotion(promotions.get(promoChoice - 1));
        }

        System.out.println("Chọn phương thức thanh toán:");
        System.out.println("1. Thẻ tín dụng");
        System.out.println("2. Ví điện tử");
        System.out.println("3. Tiền mặt");
        System.out.print("Chọn số: ");
        int paymentChoice = getValidIntInput(scanner, 1, 3);

        PaymentContext paymentContext = new PaymentContext();
        if (paymentChoice == 1) {
            paymentContext.setPaymentStrategy(new CreditCardPayment());
        } else if (paymentChoice == 2) {
            paymentContext.setPaymentStrategy(new EWalletPayment());
        } else if (paymentChoice == 3) {
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

    private void addNewIngredient(Scanner scanner) {
        System.out.print("Nhập tên nguyên liệu mới: ");
        String ingredientName = scanner.nextLine();
        if (!ingredientName.trim().isEmpty()) {
            Ingredient newIngredient = new Ingredient(ingredientName);
            ingredients.add(newIngredient);
            System.out.println("Đã thêm nguyên liệu mới: " + newIngredient.toString());
        } else {
            System.out.println("Tên nguyên liệu không được để trống!");
        }
    }

    private void addNewSupplier(Scanner scanner) {
        System.out.print("Nhập tên nhà cung cấp mới: ");
        String supplierName = scanner.nextLine();
        if (!supplierName.trim().isEmpty()) {
            Supplier newSupplier = new Supplier(supplierName);
            suppliers.add(newSupplier);
            System.out.println("Đã thêm nhà cung cấp mới: " + newSupplier.toString());
        } else {
            System.out.println("Tên nhà cung cấp không được để trống!");
        }
    }

    private void editSupplier(Scanner scanner) {
        if (suppliers.isEmpty()) {
            System.out.println("Không có nhà cung cấp để sửa!");
            return;
        }
        System.out.println("\nDanh sách nhà cung cấp:");
        for (int i = 0; i < suppliers.size(); i++) {
            System.out.println((i + 1) + ". " + suppliers.get(i).toString());
        }
        System.out.print("Chọn số nhà cung cấp để sửa (hoặc 0 để quay lại): ");
        int supplierChoice = getValidIntInput(scanner, 0, suppliers.size());

        if (supplierChoice > 0 && supplierChoice <= suppliers.size()) {
            Supplier supplier = suppliers.get(supplierChoice - 1);
            System.out.print("Nhập tên mới cho nhà cung cấp: ");
            String newName = scanner.nextLine();
            if (!newName.trim().isEmpty()) {
                supplier = new Supplier(newName); // Tạo lại nhà cung cấp với tên mới
                suppliers.set(supplierChoice - 1, supplier);
                System.out.println("Đã cập nhật nhà cung cấp: " + supplier.toString());
            } else {
                System.out.println("Tên mới không được để trống!");
            }
        } else if (supplierChoice != 0) {
            System.out.println("Lựa chọn không hợp lệ!");
        }
    }

    private void deleteSupplier(Scanner scanner) {
        if (suppliers.isEmpty()) {
            System.out.println("Không có nhà cung cấp để xóa!");
            return;
        }
        System.out.println("\nDanh sách nhà cung cấp:");
        for (int i = 0; i < suppliers.size(); i++) {
            System.out.println((i + 1) + ". " + suppliers.get(i).toString());
        }
        System.out.print("Chọn số nhà cung cấp để xóa (hoặc 0 để quay lại): ");
        int supplierChoice = getValidIntInput(scanner, 0, suppliers.size());

        if (supplierChoice > 0 && supplierChoice <= suppliers.size()) {
            Supplier supplier = suppliers.remove(supplierChoice - 1);
            System.out.println("Đã xóa nhà cung cấp: " + supplier.toString());
        } else if (supplierChoice != 0) {
            System.out.println("Lựa chọn không hợp lệ!");
        }
    }

    private void editDish(Scanner scanner) {
        if (menu.isEmpty()) {
            System.out.println("Không có món nào để sửa!");
            return;
        }
        System.out.println("\nDanh sách món:");
        for (int i = 0; i < menu.size(); i++) {
            System.out.println((i + 1) + ". " + menu.get(i).toString());
        }
        System.out.print("Chọn số món để sửa (hoặc 0 để quay lại): ");
        int dishChoice = getValidIntInput(scanner, 0, menu.size());

        if (dishChoice > 0 && dishChoice <= menu.size()) {
            Dish dish = menu.get(dishChoice - 1);
            System.out.print("Nhập tên mới: ");
            String newName = scanner.nextLine();
            System.out.print("Nhập giá mới: ");
            double newPrice = getValidDoubleInput(scanner);
            System.out.print("Nhập mô tả mới: ");
            String newDescription = scanner.nextLine();
            System.out.println("Chọn loại mới:");
            System.out.println("1. Món chính");
            System.out.println("2. Món tráng miệng");
            System.out.print("Chọn số: ");
            int typeChoice = getValidIntInput(scanner, 1, 2);
            System.out.println("Chọn nguyên liệu:");
            for (int i = 0; i < ingredients.size(); i++) {
                System.out.println((i + 1) + ". " + ingredients.get(i).toString());
            }
            System.out.print("Chọn số nguyên liệu (hoặc 0 nếu không chọn): ");
            int ingredientChoice = getValidIntInput(scanner, 0, ingredients.size());

            DishFactory factory;
            if (typeChoice == 1) {
                factory = new MainCourseFactory();
            } else if (typeChoice == 2) {
                factory = new DessertFactory();
            } else {
                System.out.println("Loại không hợp lệ!");
                return;
            }

            Dish updatedDish = factory.createDish(newName.isEmpty() ? dish.getName() : newName,
                    newPrice == 0 ? dish.getPrice() : newPrice,
                    newDescription.isEmpty() ? dish.getDescription() : newDescription);
            if (ingredientChoice > 0 && ingredientChoice <= ingredients.size()) {
                updatedDish.addIngredient(ingredients.get(ingredientChoice - 1));
            }
            menu.set(dishChoice - 1, updatedDish);
            System.out.println("Đã cập nhật món: " + updatedDish.toString());
        } else if (dishChoice != 0) {
            System.out.println("Lựa chọn không hợp lệ!");
        }
    }

    private void deleteDish(Scanner scanner) {
        if (menu.isEmpty()) {
            System.out.println("Không có món nào để xóa!");
            return;
        }
        System.out.println("\nDanh sách món:");
        for (int i = 0; i < menu.size(); i++) {
            System.out.println((i + 1) + ". " + menu.get(i).toString());
        }
        System.out.print("Chọn số món để xóa (hoặc 0 để quay lại): ");
        int dishChoice = getValidIntInput(scanner, 0, menu.size());

        if (dishChoice > 0 && dishChoice <= menu.size()) {
            Dish dish = menu.remove(dishChoice - 1);
            System.out.println("Đã xóa món: " + dish.toString());
        } else if (dishChoice != 0) {
            System.out.println("Lựa chọn không hợp lệ!");
        }
    }

    private void cancelOrder(Scanner scanner) {
        if (currentOrder.getDishes().isEmpty()) {
            System.out.println("Đơn hàng hiện tại trống, không thể hủy!");
            return;
        }
        currentOrder.getDishes().clear();
        System.out.println("Đã hủy đơn hàng thành công!");
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