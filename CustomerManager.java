public class CustomerManager
{
    private GenLL <Customer> customers = new GenLL <> ();
    private int nextCustomerID = 1;
    private OrderManager orderManager;

    //createCustomer 
    public Customer createCustomer(int customerID, String name, String emailAddress, String phoneNumber, String address)
    {
        //Exception when field is null or contains 0 characters 
        if(customerID == 0)
        {
            throw new IllegalArgumentException("CustomerID is missing.");
        }
        if(name.isBlank() || name == null)
        {
            throw new IllegalArgumentException("Customer name is required.");
        }
        if(emailAddress.isBlank() || emailAddress == null)
        {
            throw new IllegalArgumentException("Email address is required.");
        }
        if(phoneNumber.isBlank() || phoneNumber == null)
        {
            throw new IllegalArgumentException(" Phone number is required.");
        }

        //Exception when customer with previous phone number exists
        for(int i = 0; i<customers.getSize(); i++)
        {
            if(customers.get(i).getPhoneNumber().equals(phoneNumber))
            {
                throw new IllegalArgumentException("Customer with this phone number already exists.");
            }
        }


        customerID = nextCustomerID;
        nextCustomerID++;

        Customer newCustomer = new Customer(customerID, name, address, phoneNumber, emailAddress);
        customers.add(newCustomer);
        return newCustomer;
    }

    //Need to update based on linked list change 
    //sendUpdate
    //public void sendUpdate( Customer aCustomer)
    //{
    //    if(OrderManager.updateOrderCompletion)
    //    {
     //       if(Order.isDelivery)
     //       {
       //         System.out.println("Order is on the move.");
       //     }else{
         //       System.out.println("Order is awaiting pick up");
           // }
        //}else{
         //   System.out.println("Order is still underway");
         //   return;
      //  }
   // }
}