import java.time.LocalDateTime;
import java.util.Arrays;
public class Order {
    private int orderID;
    private int orderCustomerID;
    private Items[] items; //Same here
    private int[] quantity; //Go back and ask about this
    private boolean isDelivery;
    private boolean isCompleted;
    private LocalDateTime submissionTime;

    //Parameterized Constructor
    public Order(int orderID, int customerID, Items[] items, int[] quantity, boolean isCompleted, boolean isDelivery, LocalDateTime submissionTime)
    {
        this.orderID = orderID;
        this.orderCustomerID = customerID;
        this.setItemsAndQuantities(items, quantity);
        this.setIsDelivery(isDelivery);
        this.setIsCompleted(isCompleted);
        this.submissionTime= submissionTime;
    }

    //Accessors
    public int getOrderID()
    {
        return this.orderID;
    }

    public int getCustomerID()
    {
        return this.orderCustomerID;
    }

    public Items[] getItems()
    {
        return items;
    }

    public int[] getQuantity()
    {
        return quantity;
    }
    public boolean isDelivery()
    {
        return this.isDelivery;
    }

    public boolean isCompleted()
    {
        return this.isCompleted;
    }

    public LocalDateTime getSubmissionTime()
    {
        return this.submissionTime;
    }

    //Setters

    //Combine setItems and setQuantities to ensure that the size of arrays match 
    //and each item has a valid quantity
    public void setItemsAndQuantities(Items[] item, int[] quantities)
    {
        if(item == null || item.length == 0)
        {
            throw new IllegalArgumentException("Order must have at least one item.");
        }

        if(quantities == null || quantities.length == 0)
        {
            throw new IllegalArgumentException("A valid order must have item with at least one quantity.");
        }

        if(item.length != quantities.length)
        {
            throw new IllegalArgumentException("Each item must have a valid quantity.");
        }

        for(int i = 0; i < item.length; i++)
        {
            if(item[i] == null)
            {
                throw new IllegalArgumentException("Item cannot be null.");
            }
            if(quantities[i] <= 0)
            {
                throw new IllegalArgumentException("Quantity must be greater than 0");
            }
        }

        this.items = item;
        this.quantity = quantities;
    }

    public void setIsDelivery(boolean aD)
    {
        this.isDelivery = aD;
    }

    public void setIsCompleted (boolean aS)
    {
        this.isCompleted = aS;
    }

    //toString Method
    public String toString()
    {
        return "Order ID: " + orderID +
                " | Customer ID: " + orderCustomerID +
                " | Items: " + Arrays.toString(items) +
                " | Quantities: " + Arrays.toString(quantity) +
                " | Delivery Status: " + isDelivery +
                " | Completed Status : " + isCompleted +
                " | Submission Time: "  + submissionTime;
    }
}
