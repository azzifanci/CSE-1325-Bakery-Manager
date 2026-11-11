import java.time.LocalDateTime;

public class OrderManager {
    private GenLL <Order> orders = new GenLL <>();
    private GenLL <Order> activeOrders = new GenLL <>();
    private int nextOrderID = 1;

    public Order createOrder(int customerID, Items[] items, int[] quantity, boolean isDelivery) {
        //checks if the item and quasntities are valid
        if(items == null || quantity == null || items.length == 0 || items.length != quantity.length) {
            throw new IllegalArgumentException("Invalid order information");
        }
        //check each item and quantites
        for(int i = 0; i < quantity.length; i++) {
            if(items[i] == null || quantity[i] <= 0) {
                throw new IllegalArgumentException ("Invalid item or quantity");
            }
        }
        int orderID = nextOrderID;
        nextOrderID++;

        Order newOrder = new Order(orderID, customerID, items, quantity, isDelivery, false, LocalDateTime.now());
        orders.add(newOrder);
        activeOrders.add(newOrder);
        return newOrder;

    }
//cancelOrder()
public void cancelOrder(int orderID)
{
   Order order = findOrderIDs(orderID);
   if(order == null) 
    {
        throw new IllegalArgumentException("Order " + orderID + "not found.");
    }

    order.setIsCompleted(true);
    activeOrders.remove(order);
}

//updateOrder()
    // based on the order object I couldn't make one main updateOrder, so I broke it up.
    // In the GUI I assume it would be one button that says updateOrder then show the following options
    // Updates the delivery status of an order by its ID.

    //Instead of returning false, let's return an exception

public void updateOrderDelivery(int orderID, boolean newDeliveryStatus)
{
    Order order = findOrderIDs(orderID);
   if(order == null) 
    {
        throw new IllegalArgumentException("Order " + orderID + "not found.");
    }
    order.setIsDelivery(newDeliveryStatus);
}
// Updates the completion status of an order by its ID.
public void updateOrderCompletion(int orderID, boolean newCompletionStatus)
{
    Order order = findOrderIDs(orderID);
    if(order == null) 
    {
        throw new IllegalArgumentException("Order " + orderID + "not found.");
    }
    order.setIsCompleted(newCompletionStatus);
}
//Updates the items and quantities for an order by its ID.
public void updateOrderItems(int orderID, Items[] newItems, int[] newQuantities)
{
    Order order = findOrderIDs(orderID);
    if(order == null) 
    {
        throw new IllegalArgumentException("Order " + orderID + "not found.");
    }
    order.setItems(newItems);
    order.setQuantity(newQuantities);
}

//trackOrder()
public void trackOrder(int orderID) {

    Order order = findOrderIDs(orderID);

    //Order Not Found 
   if(order == null) 
    {
        throw new IllegalArgumentException("Order " + orderID + "not found.");
    }

    //Displays message where order is completed or not
    if(order.isCompleted()) {
        System.out.println("Order " + orderID + " is completed.");
        } else {
            System.out.println("Order " + orderID + " is not completed.");
        }
    }

//orderPriority()
public void orderPriority() 
{
    //Since activeOrders displays the orders in progress, we just show the orders in the list to determine priority
    //The order of items determines priority. 1st item in list means order with most priority 

    activeOrders.displayAll();
}
//orderHistory()
public void orderHistory()
{
    orders.displayAll();
}

// Searches OrderIDs 
private Order findOrderIDs(int orderID)
{
    for (int i = 0; i < activeOrders.getSize(); i++)
    {
        Order current = activeOrders.get(i);
        if (current.getOrderID() == orderID)
        {
            return current;
        }
    }
    return null;
}

}