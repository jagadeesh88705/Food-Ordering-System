import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FoodItem {
    private int foodId;
    private String name;
    private int basePrice;
    private String description;

    public FoodItem(int foodId, String name, int basePrice, String description) {
        this.foodId = foodId;
        this.name = name;
        this.basePrice = basePrice;
        this.description = description;
    }

    public int getFoodId() {
        return foodId;
    }

    public void saveToDatabase() {
        String sql = "INSERT INTO `Food Item` ( `Item Name`, `Base price`, Description) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, this.name);
            pstmt.setInt(2, this.basePrice);
            pstmt.setString(3, this.description);

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                this.foodId = rs.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println("Error saving food item: " + e.getMessage());
        }
    }
}
