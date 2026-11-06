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
    // These fields/and method belong inside CustomerManager.java.
// Requires Customer.setCustomerID(int).

    private Customer[] customers = new Customer[100];
    private int customerCount = 0;

    public Customer createCustomer(int customerID, String name, String emailAddress, String phoneNumber, String address)
    {
        if (customerID == 0 || name== null || emailAddress== null || phoneNumber ==null || address== null)
        {
            throw new IllegalArgumentException("Invalid customer information.");
        }

        Customer newCustomer= new Customer(customerID, name, emailAddress, phoneNumber,address);
        newCustomer.setCustomerID(customerCount + 1);
        customers[customerCount] = newCustomer;
        customerCount++;
    }
}
