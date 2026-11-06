public class OrderManager {
    //Instance Variable 
    Order[] orders;
    Order[] activeOrders;
    Order[] orderHistory;

    //createOrder()
        //When a order is created, move it to an array called activeOrders 

    //cancelOrder()
    public boolean cancelOrder(int orderID)
    {
        for(int i = 0; i<activeOrders.size; i++)
        {
            if(activeOrders.get(i).getOrderID() == orderID)
            {
                Order canceledOrder = activeOrders.remove(i);

                orderHistory.add(canceledOrder);

                return true;
            }
        }

        return false;
    }

    //updateOrder()
    // based on the order object I couldn't make one main updateOrder, so I broke it up.
    //  In the GUI I assume it would be one button that says updateOrder then show the following options
        //Updates the delivery status of an order by its ID.
        public boolean updateOrderDelivery(int orderID, boolean newDeliveryStatus) 
        {
            Order order = findOrderById(orderID);
            if (order == null) 
            {
                return false; // Order not found
            }
            order.setIsDelivery(newDeliveryStatus);
            return true;
        }
        // Updates the completion status of an order by its ID.
        public boolean updateOrderCompletion(int orderID, boolean newCompletionStatus) 
        {
            Order order = findOrderById(orderID);
            if (order == null) 
            {
                return false;
            }
            order.setIsCompleted(newCompletionStatus);
            return true;
        }
        //Updates the items and quantities for an order by its ID.
        public boolean updateOrderItems(int orderID, Items[] newItems, int[] newQuantities) 
        {
            Order order = findOrderById(orderID);
            if (order == null) 
            {
                return false;
            }
            order.setItems(newItems);
            order.setQuantity(newQuantities);
            return true;
        }
        // Order Id searcher
        private Order findOrderById(int orderID) 
        {
            for (int i = 0; i < activeOrders.size(); i++) 
            {
                Order currentOrder = activeOrders.get(i);
                if (currentOrder.getOrderID() == orderID) 
                    {
                        return currentOrder;
                    }
            }
        return null;
        }
        
    
    //trackOrder()
    public void trackOrder(int orderID) {
	
	//check if order exists
	boolean found = false;
	
	//search through all orders
	for(Order order : orders) {
	
		//check if order ID matches
		if(order.getOrderID() == orderID) {
	
			found = true;
		
			//check if the order is completed
			if(order.isCompleted()) {
				System.out.println("Order " + orderID + " is completed.");
			} else {
				System.out.println("Order " + orderID + " is not completed.");
			}
		
			//stop searching once the order is found
			break;
		}
	}
	
	//Display message if order was not found
	if(!found) {
		System.out.println("Order " + orderID + " was not found.");
	}
}

    //orderPriority()
    public void orderPriority()  // Process each order in priority order
{
    order.sort((a,b) -> a.getSubmissionTime().compareTo(b.getSubmissionTime()));

    for(Order order : orders)
    {
        if(!order.isCompleted())
        {
            System.out.println(order);
            System.out.println();
        }
       
    }
}

    //orderHistory()
        //Place all orders into orderHistory array
}
