import java.time.LocalDateTime;
import java.util.Arrays;
public class Order {
    private int orderID;
    private int customerID;
    private Items[] items; //Same here
    private int[] quantity; //Go back and ask about this
    private boolean isDelivery;
    private boolean isCompleted;
    private LocalDateTime submissionTime;

    //Parameterized Constructor 
    public Order(int orderID, int customerID, Items[] items,
                 int[] quantity, boolean isCompleted, boolean isDelivery, LocalDateTime submissionTime)
    {
        this.orderID = orderID;
        this.customerID = customerID;
        this.setItems(items);
        this.setQuantity(quantity);
        this.setIsDelivery(isDelivery);
        this.setIsCompleted(isCompleted);
        this.submissionTime= submissionTime;
    }

    //Accessors
    public int getOrderId()
    {
        return this.orderID;
    }

    public int getCustomerID()
    {
        return this.customerID;
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
    public void setItems(Items[] item)
    {
        if(items == null || items.length == 0)
        {
            //Needs Exception 
        }

        for(int i = 0; i < items.length; i++)
        {
            if(items[i] == null)
            {
                //Needs Exception 
            }
        }

        this.items = item;
    }

    public void setQuantity(int[] quantity)
    {
        if(quantity == null || quantity.length == 0)
        {
            //Needs Exception 
        }

        for(int i = 0; i < quantity.length; i++)
        {
            if(quantity[i] <= 0)
            {
                //Needs Exception 
            }
        }

        this.quantity = quantity;
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
            " | Customer ID: " + customerID +
            " | Items: " + Arrays.toString(items) +
            " | Quantities: " + Arrays.toString(quantity) +
            " | Delivery Status: " + isDelivery +
            " | Completed Status : " + isCompleted + 
            " | Submission Time: "  + submissionTime;
    }
}
