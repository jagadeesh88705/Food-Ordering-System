import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer {
    public static int customerId;
    public String name;
    public String email;
    public String phoneNumber;
    public String role;
    public String password;
public Customer(){}
    public Customer(int customerId, String name, String email, String phoneNumber, String role, String password) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.password = password;
    }

    public static int getcustomerid(String mail) {
            int customerid=0;
            String sql="SELECT Customerid FROM Customer where Email="+mail;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            //pstmt.setString(1, mail);
            ResultSet rs=pstmt.executeQuery(sql);
            if(rs.next())
                customerid=rs.getInt("Customerid");
                setcustomerid(customerid);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
            return customerid;
    }

    private static void setcustomerid(int customerid) {
        customerId=customerid;
}

    public void saveToDatabase() {
        String sql = "INSERT INTO Customer (Customerid, Name, Email, phonenumber, role, password) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, this.customerId);
            pstmt.setString(2, this.name);
            pstmt.setString(3, this.email);
            pstmt.setString(4, this.phoneNumber);
            pstmt.setString(5, this.role);
            pstmt.setString(6, this.password);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
