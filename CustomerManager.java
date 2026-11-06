public class CustomerManager
{
    private Customer[] customers;
    private Order[] order;
    private OrderManager[] orderManagers;
    public static final int DEF_SIZE= 20;

    public CustomerManager()
    {
        customers= new Customer[DEF_SIZE];
    }

    //create createCustomer

    public void sendUpdate( Customer aCustomer)
    {
        if(OrderManager.updateOrderCompletion)
        {
            if(Order.isDelivery)
            {
                System.out.println("Order is on the move.");
            }else{
                System.out.println("Order is awaiting pick up");
            }
        }else{
            System.out.println("Order is still underway");
            return;
        }
    }
}
