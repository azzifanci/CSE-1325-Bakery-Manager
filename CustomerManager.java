import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Scanner;

public class CustomerManager
{
    private GenLL <Customer> customers = new GenLL <> ();
    public CustomerManager(){customers= new GenLL<>();}
    private int nextCustomerID = 100;

    // =========================================================
    // FR-9: Create Customer
    // ========================================================= 
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

    // =========================================================
    // FR-10: Send Update
    // =========================================================  
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

    // =========================================================
    // FR-11: Customer History
    // ========================================================= 
    public void customerHistory()
    {
        customers.displayAll();
    }

    // =========================================================
    // Back End Helpers
    // ========================================================= 
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

    // =========================================================
// Save + Load to Files 
// ========================================================= 

//File output save order to file
    public void saveToFile(String filepath) throws FileNotFoundException 
    {
    PrintWriter writer = new PrintWriter(new File(filepath));

    // Loop through all orders 
    for (int i = 0; i < customers.getSize(); i++) 
    {
        Customer customer = customers.get(i);

        // Write order data comma-separated to the text file
        writer.println(customer.getCustomerID() + " | " 
            + customer.getName() + " | " 
            + customer.getAddress() + " | " 
            + customer.getPhoneNumber() + " | " 
            + customer.getEmailAddress());
    }
    writer.close();
}
    //File input load Orders form file
    public void loadFromFile(String filepath) throws FileNotFoundException 
    {
    Scanner scanner = new Scanner(new File(filepath));
    
    // Clear out old lists so loading a new file starts fresh
    GenLL <Customer> newCustomers = new GenLL<>();
    
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

        String[] parts = line.split("\\|");
        
        if (parts.length != 5) 
        {
            throw new IllegalArgumentException("Line " + lineNumber + " is not valid");
        }
            int customerID = Integer.parseInt(parts[0].replaceAll("[^0-9]", ""));
            // Track the highest ID so nextOrderID doesn't cause duplicates
            if (customerID > maxID) 
            {
                maxID = customerID;
            }
            
            // Re-create the order object with loaded data
            Customer loadedCustomer = new Customer(customerID, parts[1], parts[2], parts[3], parts[4]);
            newCustomers.add(loadedCustomer);
    }
}
        catch(RuntimeException e)
        {
            throw new IllegalArgumentException("Customers file problem near line " + lineNumber + ": " + e.getMessage());
        }
        finally
        {
            scanner.close();
        }

        customers = newCustomers;
        nextCustomerID = maxID + 1;
}

}
