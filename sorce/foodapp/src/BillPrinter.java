import java.util.List;

public class BillPrinter {

    public BillPrinter(List<Order> order) {
        Order c=order.getFirst();
        System.out.println(ConsoleColors.CYAN_BOLD + "===============================");
        System.out.println("      RESTAURANT BILL");
        System.out.println("===============================" + ConsoleColors.RESET);
        System.out.println("Customer ID: " + c.customerId);
        System.out.println("Date: " + c.orderDate);
        System.out.println(ConsoleColors.YELLOW_BOLD + "Items Ordered:" + ConsoleColors.RESET);
        int total=0;
        System.out.printf(ConsoleColors.BLUE+"%5s %10s %11s %7s\n","S.no","Food Item","Quantity","Amount"+ConsoleColors.RESET);
        for(int i=0;i<order.size();i++) {
            Order o=order.get(i);
            System.out.printf("%5s %10s %11s %7s\n", i+1, o.foodname, o.quantity, o.totalAmount);
            total+=o.totalAmount;
        }

        System.out.println(ConsoleColors.GREEN_BOLD + "Total Amount: " + total + ConsoleColors.RESET);
        System.out.println(ConsoleColors.CYAN_BOLD + "===============================" + ConsoleColors.RESET);
    }
}
