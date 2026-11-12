public class CustomerManager
{
    private GenLL <Customer> customers = new GenLL <> ();
    private int nextCustomerID = 1;

    //createCustomer 
    public Customer createCustomer(String name, String emailAddress, String phoneNumber, String address)
    {
        //Exception when field is null or contains 0 characters 
        if(name == null || name.isBlank())
        {
            throw new IllegalArgumentException("Customer name is required.");
        }
        if(emailAddress == null || emailAddress.isBlank())
        {
            throw new IllegalArgumentException("Email address is required.");
        }
        if(phoneNumber == null || phoneNumber.isBlank())
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

        //Don't need to pass customerID as parameter because we are generating customerID
        int customerID = nextCustomerID;
        nextCustomerID++;

        Customer newCustomer = new Customer(customerID, name, address, phoneNumber, emailAddress);
        customers.add(newCustomer);
        return newCustomer;
    }

    //Linked List sendUpdate
        //Only sends notification when order is completed 
    public void sendUpdate(Order order)
    {
        if(order == null)
        {
            throw new IllegalArgumentException("Order not found.");
        }

        if(!order.isCompleted())
        {
            throw new IllegalArgumentException("Order is not completed");
        }

        //Links order's customerID to actual customer 
        Customer customer = findCustomerIDs(order.getCustomerID());

         if(customer == null)
        {
            throw new IllegalArgumentException("Customer not found.");
        }

        String message = "Notification: Dear Customer " + customer.getName() +
            " (" + customer.getCustomerID() + "), ";


        if(order.isCompleted())
        {
            if(order.isDelivery())
            {
                System.out.println(message + "your order is being delivered."); 
            }
            else
            {
                System.out.println(message + "your order is awaiting pick up.");
            }
        }
    }


    //findCustomerID
    // Searches OrderIDs 
    public Customer findCustomerIDs(int customerID)
    {
        for (int i = 0; i < customers.getSize(); i++)
        {
            Customer current = customers.get(i);
            if (current.getCustomerID() == customerID)
            {
                return current;
            }
        }
        return null;
    }

}