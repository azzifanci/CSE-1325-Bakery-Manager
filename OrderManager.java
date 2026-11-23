import java.time.LocalDateTime;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class OrderManager {
    private CustomerManager customerManager;
    private GenLL <Order> orders = new GenLL <>();
    private GenLL <Order> activeOrders = new GenLL <>();
    private GenLL <Order> canceledOrders = new GenLL <>();
    private int nextOrderID = 100;

    public OrderManager(CustomerManager customerManager) {
        if(customerManager == null)
        {
            throw new IllegalArgumentException("Error! Customer Manager is null");
        }
        this.customerManager = customerManager;
    }
    public Order createOrder(int customerID, Items[] items, int[] quantity, boolean isDelivery) {
        if(customerManager.findCustomerIDs(customerID) == null)
        {
            throw new IllegalArgumentException("Customer " + customerID + " not found.");
        }
        
        //checks if the item and quantities are valid
        if(items == null || quantity == null || items.length == 0 || items.length != quantity.length) {
            throw new IllegalArgumentException("Invalid order information");
        }
        //check each item and quantities
        for(int i = 0; i < quantity.length; i++) {
            if(items[i] == null || quantity[i] <= 0) {
                throw new IllegalArgumentException ("Invalid item or quantity");
            }
        }
        int orderID = nextOrderID;
        nextOrderID++;

        Order newOrder = new Order(orderID, customerID, items, quantity, false, isDelivery, LocalDateTime.now());
        
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

    if(order.isCompleted())
    {
        throw new IllegalArgumentException("Order already completed. Cannot be canceled");    
    }

    if(isCanceled(orderID))
    {
        throw new IllegalArgumentException("Order " + orderID + " is already canceled.");
    }

    activeOrders.remove(order);
    canceledOrders.add(order);
}

//updateOrder()
public void updateOrderDelivery(int orderID, boolean newDeliveryStatus)
{
    Order order = findOrderIDs(orderID);
    if(order == null) 
    {
        throw new IllegalArgumentException("Order " + orderID + "not found.");
    }

    //Displays message whether order is canceled 
    if(isCanceled(orderID))
    {
        throw new IllegalArgumentException("Order " + orderID + " has been canceled and cannot be updated.");
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

    //Displays message whether order is canceled 
    if(isCanceled(orderID))
    {
        System.out.println("Order " + orderID + " has been canceled and cannot be updated.");
    } 
    
    if(newCompletionStatus)
    {
        order.setIsCompleted(true);
        activeOrders.remove(order);
    }
    else
    {
        order.setIsCompleted(false);
    }
    
}
//Updates the items and quantities for an order by its ID.
public void updateOrderItems(int orderID, Items[] newItems, int[] newQuantities)
{
    Order order = findOrderIDs(orderID);
    if(order == null) 
    {
        throw new IllegalArgumentException("Order " + orderID + "not found.");
    }

    //Displays message whether order is canceled 
    if(isCanceled(orderID))
    {
        System.out.println("Order " + orderID + " has been canceled and cannot be updated.");
    } 

    order.setItemsAndQuantities(newItems, newQuantities);
}

//trackOrder()
public void trackOrder(int orderID) {

    Order order = findOrderIDs(orderID);

    //Order Not Found 
    if(order == null) 
    {
        throw new IllegalArgumentException("Order " + orderID + "not found.");
    }

    //Displays message whether order is completed or not
    if(order.isCompleted()) {
        System.out.println("Order " + orderID + " is completed.");
        } else {
            System.out.println("Order " + orderID + " is not completed.");
        }

    //Displays message whether order is canceled 
    if(isCanceled(orderID))
    {
        System.out.println("Order " + orderID + " has been canceled.");
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
public Order findOrderIDs(int orderID)
{
    for (int i = 0; i < orders.getSize(); i++)
    {
        Order current = orders.get(i);
        if (current.getOrderID() == orderID)
        {
            return current;
        }
    }
    return null;
}

//Checks whether an order has been canceled 
public boolean isCanceled(int orderID)
{
    for(int i = 0; i < canceledOrders.getSize(); i++)
    {
        if(canceledOrders.get(i).getOrderID() == orderID)
        {
            return true;
        }
    }

    return false;
}
//File output save order to file
    public void saveToFile(String filepath) throws FileNotFoundException 
    {
    PrintWriter writer = new PrintWriter(new File(filepath));

    // Loop through all orders 
    for (int i = 0; i < orders.getSize(); i++) 
    {
        Order order = orders.get(i);
        Items[] ordersItems = order.getItems();
        int[] ordersQuantities = order.getQuantity();

        String itemsText = "";
        for(int j=0; i<ordersItems.length; j++)
        {
            if(j > 0)
            {
                itemsText = itemsText + ";";
            }
            itemsText = itemsText + ordersItems[j] + ":" + ordersQuantities[j];
        }
        
        // Write order data comma-separated to the text file
        writer.println(order.getOrderID() + "," 
            + order.getCustomerID() + "," 
            + order.isDelivery() + "," 
            + order.isCompleted() + "," 
            + order.isDelivery() + ","
            + isCanceled(order.getOrderID()) 
            + order.getSubmissionTime()
            +itemsText);
    }
    writer.close();
}
    //File input load Orders form file
    public void loadFromFile(String filepath) throws FileNotFoundException 
    {
    Scanner scanner = new Scanner(new File(filepath));
    
    // Clear out old lists so loading a new file starts fresh
    GenLL <Order> newOrders = new GenLL<>();
    GenLL <Order> newActiveOrders = new GenLL<>();
    GenLL <Order> newCanceledOrders = new GenLL<>();
    
    int maxID = 0;
    int lineNumber = 0;

    try{
    while (scanner.hasNextLine()) 
    {
        String line = scanner.nextLine();
        lineNumber++;
        if(line.isEmpty())
        {
            continue;
        }

        String[] parts = line.split(",");
        
        if (parts.length != 7) 
        {
            throw new IllegalArgumentException("Line " + lineNumber + " is not valid");
        }
            int orderID = Integer.parseInt(parts[0]);
            int customerID = Integer.parseInt(parts[1]);
            boolean isDelivery = Boolean.parseBoolean(parts[2]);
            boolean isCompleted = Boolean.parseBoolean(parts[3]);
            boolean canceled = Boolean.parseBoolean(parts[4]);
            LocalDateTime time = LocalDateTime.parse(parts[5]);

            String[] pairs = parts[6].split(";");
            Items[] items = new Items[pairs.length];
            int[] quantities = new int[pairs.length];
            for(int i=0; i<pairs.length; i++)
            {
                String[] pair = pairs[i].split(":");
                items[i] = Items.valueOf(pair[0]);
                quantities[i] = Integer.parseInt(pair[1]);
            }
            // Track the highest ID so nextOrderID doesn't cause duplicates
            if (orderID > maxID) 
            {
                maxID = orderID;
            }
            
            // Re-create the order object with loaded data
            Order loadedOrder = new Order(orderID, customerID, items, quantities, isCompleted, isDelivery, LocalDateTime.now());
            
            newOrders.add(loadedOrder);
            if(canceled){
                newCanceledOrders.add(loadedOrder);
            }
            else if (!isCompleted) {
                newActiveOrders.add(loadedOrder);
            }
    }
}
        catch(RuntimeException e)
        {
            throw new IllegalArgumentException("Orders file problem near line " + lineNumber + ": " + e.getMessage());
        }
        finally
        {
            scanner.close();
        }

        orders = newOrders;
        activeOrders = newActiveOrders;
        canceledOrders = newCanceledOrders;
        nextOrderID = maxID + 1;
}

}