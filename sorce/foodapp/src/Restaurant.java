import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Restaurant {
    private int restaurantId;
    private String name;
    private String location;



    public Restaurant(int restaurantId, String name, String location) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.location = location;

    }

    public void saveToDatabase() {
        String sql = "INSERT INTO Restaurant (Name, Location) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, this.name);
            pstmt.setString(2, this.location);


            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error saving restaurant: " + e.getMessage());
        }
    }

    public static void displayRestaurants() {
        String sql = "SELECT * FROM Restaurant";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                System.out.println("Restaurant ID: " + rs.getInt("Restaurantid") +
                        ", Name: " + rs.getString("Name") +
                        ", Location: " + rs.getString("Location") );
            }

        } catch (SQLException e) {
            System.out.println("Error fetching restaurants: " + e.getMessage());
        }
    }
}
