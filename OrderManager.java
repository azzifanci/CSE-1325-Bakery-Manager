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

    //trackOrder()

    //orderPriority()

    //orderHistory()
        //Place all orders into orderHistory array
}
