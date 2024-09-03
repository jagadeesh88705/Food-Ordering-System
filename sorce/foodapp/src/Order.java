import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Order {
    public int orderId;
    public int customerId;
    public int restaurantId;
    public int totalAmount;
    public String orderStatus;
    public int quantity;
    public String foodname;
    public Timestamp orderDate;


    public Order(int orderId, int customerId, int restaurantId,String foodname, int quantity,int totalAmount, String  orderStatus, Timestamp orderDate) {
        this.orderId = orderId;
        this.foodname=foodname;
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.quantity=quantity;
    }

    public void placeOrder(int foodId, int quantity) {
        String sql = "INSERT INTO `Order` (Customerid, `Restaurant id`, `Total amount`, `Orderstatus`, `Orderdate`) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, this.customerId);
            pstmt.setInt(2, this.restaurantId);
            pstmt.setInt(3, this.totalAmount);
            pstmt.setString(4, this.orderStatus);
            pstmt.setTimestamp(5, this.orderDate);

            pstmt.executeUpdate();



        } catch (SQLException e) {
            System.out.println("Error placing order: " + e.getMessage());
        }
    }
}
