import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Main {
    public static void main(String[] args){
        Main m=new Main();
        try {

                m.main();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

        Customer customer=new Customer();
    private  boolean login(Scanner scanner) throws InterruptedException{
        System.out.println("Enter your Email:");
        String email = scanner.nextLine();
        System.out.println("Enter your Password:");
        String password = scanner.nextLine();
        String[] frames = {"-", "\\", "|", "/"};

        for (int i = 0; i < 50; i++) {
            System.out.print("\rLoading " + frames[i % frames.length]);
            Thread.sleep(100);
        }
        String sql = "SELECT * FROM Customer WHERE Email = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
                //customer.role="Restaurant";
            if (rs.next()) {
                customer.role=rs.getString("role");
                System.out.println(ConsoleColors.BLUE_BOLD+"\rLogin successful! Welcome, " +ConsoleColors.WHITE+ rs.getString("Name")+ConsoleColors.RESET);

                return true;
            } else {
                System.out.println("\rInvalid email or password. Please try again.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
            return false;
        }
    }

    public void main() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        System.out.println(ConsoleColors.CYAN_BACKGROUND+ConsoleColors.BLACK_BOLD+" Welcome to the Food Ordering System "+ConsoleColors.RESET);
        boolean loggedIn = false;

        while (!loggedIn) {
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    loggedIn = login(scanner);
                    break;
                case 2:
                    System.out.println("Enter Registration Details");
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Phone Number: ");
                    String phoneNumber = scanner.nextLine();
                    System.out.print("Role (Enter 'Restaurant' or 'Customer'): ");
                     String role = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();
                    customer = new Customer(0, name, email, phoneNumber, role, password);
                    customer.saveToDatabase();
                    System.out.println("Registration successful! Please log in.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        boolean exit = false;
        if(customer.role.charAt(0)=='C')
        {
            while (!exit) {
                System.out.println("\nMain Menu");
                System.out.println("1. Place an Order");
                System.out.println("2. Show Menu");
                System.out.println("3. View Order History");
                System.out.println("4. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        placeOrder(scanner,customer.email);
                        break;
                    case 2:
                        viewMenuCard();
                        break;
                    case 3:
                        viewOrderHistory(scanner);
                        break;
                    case 4:
                        exit = true;
                        System.out.println(ConsoleColors.WHITE_BACKGROUND+ConsoleColors.BLACK_BOLD+"Exiting... Goodbye!"+ConsoleColors.RESET);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }

        }
        else {
            while (!exit) {
                System.out.println("\nMain Menu");
                System.out.println("1. Place an Order");
                System.out.println("2. Add/Update Menu");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        placeOrder(scanner, customer.email);
                        break;
                    case 2:
                        manageMenu(scanner);
                        break;
                    case 3:
                        exit = true;
                        System.out.println(ConsoleColors.WHITE_BACKGROUND + ConsoleColors.BLACK_BOLD + "Exiting... Goodbye!" + ConsoleColors.RESET);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }

        scanner.close();
    }

    private void viewMenuCard() {
        Menu.displayMenuItems(0);

    }

    private static void viewOrderHistory(Scanner scanner) {
        System.out.print("Null");
    }

    private void placeOrder(Scanner scanner,String mail) throws  InterruptedException{
        int customerId = Customer.getcustomerid(mail);
        scanner.nextLine();

        System.out.println("Available Restaurants:");
        Restaurant.displayRestaurants();

        System.out.print("Enter Restaurant ID: ");
        int restaurantId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Available Food Items:");
        Menu.displayMenuItems(restaurantId);
        List<Order> o=new ArrayList<>();
while(true) {
    System.out.print("Enter Food ID: ");
    int foodId = scanner.nextInt();
    scanner.nextLine();
    String itemname = Menu.findfoodname(foodId);
    System.out.print("Enter Quantity: ");
    int quantity = scanner.nextInt();
    scanner.nextLine();
    Order order = new Order(1, customerId, restaurantId,itemname,quantity, quantity * Menu.getPrice(foodId), "Booked", new Timestamp(System.currentTimeMillis()));
    order.placeOrder(foodId, quantity);
    o.add(order);


        String[] frames = {"-", "\\", "|", "/"};

        for (int i = 0; i < 50; i++) {
            System.out.print("\r Please Wait Loading " + frames[i % frames.length]);
            Thread.sleep(100);
        }

    System.out.println(ConsoleColors.PURPLE_BOLD+ConsoleColors.GREEN_UNDERLINED+"Order placed successfully!"+ConsoleColors.RESET);
    System.out.print("Did you want to add more on your order :(y/n)");
    if(scanner.next().charAt(0)=='n')   break;
}
        new BillPrinter(o);
    }



    private static void manageMenu(Scanner scanner) {
        System.out.println("Available Restaurants:");
        Restaurant.displayRestaurants();
        System.out.println("Available Food Items:");
        Menu.displayMenuItems(0);
        System.out.println("Enter Restaurant ID:");
        int restaurantId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("1. Add Menu Item");
        System.out.println("2. Update Menu Item");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                System.out.print("Enter Food Name: ");
                String itemName = scanner.nextLine();
                System.out.print("Enter Base Price: ");
                int basePrice = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter Description: ");
                String description = scanner.nextLine();
                FoodItem foodItem = new FoodItem(0, itemName, basePrice, description);
                foodItem.saveToDatabase();
                Menu menu = new Menu(0, foodItem.getFoodId(), restaurantId, 0, description, "1");
                menu.saveToDatabase();
                System.out.println("Menu item added successfully!");
                break;
            case 2:
                System.out.print("Enter Food ID to update: ");
                int foodId = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter new Price Adjustment: ");
                int priceAdjustment = scanner.nextInt();
                scanner.nextLine();
                Menu.updateMenu(restaurantId, foodId, priceAdjustment);
                System.out.println("Menu item updated successfully!");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
}
