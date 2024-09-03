import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Menu {
    private int menuId;
    private int foodId;
    private int restaurantId;
    private int priceAdjustment;
    private String description;
    private String quantity;

    public Menu(int menuId, int foodId, int restaurantId, int priceAdjustment, String description, String quantity) {
        this.menuId = menuId;
        this.foodId = foodId;
        this.restaurantId = restaurantId;
        this.priceAdjustment = priceAdjustment;
        this.description = description;
        this.quantity = quantity;
    }

    public static int getPrice(int foodId) {
        String sql = "SELECT * FROM `food item` where foodid=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, foodId);
            ResultSet rs = pstmt.executeQuery();
            int price = 0;
            while (rs.next()) {
                price = rs.getInt("Base price");
            }
            return price;
        } catch (SQLException e) {
            System.out.println("Error : " + e.getMessage());
        }
        return 0;
    }

    public static String findfoodname(int foodId) {
        String sql = "SELECT * FROM `food item` where foodid=?";
        String ans="";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1,foodId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {

                ans=rs.getString("Item name");
            }

        } catch (SQLException e) {
            System.out.println(ConsoleColors.RED+"Error :" + e.getMessage()+ConsoleColors.RESET);
        }
        return ans;
    }


    public void saveToDatabase() {
        String sql = "INSERT INTO Menu (Foodid, Restaurantid, Priceadjustment, Description, Quantity) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, this.foodId);
            pstmt.setInt(2, this.restaurantId);
            pstmt.setInt(3, this.priceAdjustment);
            pstmt.setString(4, this.description);
            pstmt.setString(5, this.quantity);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error saving menu: " + e.getMessage());
        }
    }

    public static void updateMenu(int restaurantId, int foodId, int priceAdjustment) {
        String sql = "UPDATE `food item` SET `base price` = ? WHERE Foodid = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, priceAdjustment);
            pstmt.setInt(2, foodId);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error updating menu: " + e.getMessage());
        }
    }

    public static void displayMenuItems(int restaurantId) {
        String sql = "SELECT * FROM `food item`";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            System.out.printf(ConsoleColors.GREEN+"%5s %15s %40s %15s\n","Food ID","Name","Description","Price"+ConsoleColors.RESET);
            while (rs.next()) {
                System.out.printf("%5s %15s %40s %15s\n" , rs.getInt("foodid")
                        , rs.getString("Item name"), rs.getString("Description")
                        , rs.getInt("Base price") );
            }

        } catch (SQLException e) {
            System.out.println("Error fetching menu items: " + e.getMessage());
        }
    }
}
