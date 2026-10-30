import java.time.LocalDateTime;

public class Order {
    private int orderID;
    private int customerID;
    private Items[] items; //Same here
    private int[] quantity; //Go back and ask about this
    private boolean isDelivery;
    private boolean isCompleted;
    private LocalDateTime submissionTime;

    public Order(int orderID, int customerID, Items items,
                 int[] quantity, boolean isCompleted, boolean isDelivery, LocalDateTime submissionTime)
    {
        this.setOrderID(orderID);
        this.setCustomerID(customerID);
        this.Items= items;
        this.quantity= quantity;
        this.setCompleted(isCompleted);
        this.setDelivery(isDelivery);
        this.submissionTime= submissionTime;
    }

}
