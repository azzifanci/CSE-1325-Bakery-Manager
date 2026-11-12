import java.util.Scanner;

public class BakeryManagementTest
{
    private static Scanner input = new Scanner(System.in);
    private static OrderManager orderManager = new OrderManager();
    private static CustomerManager customerManager = new CustomerManager();

    public static void main(String[] args)
    {
        int choice = -1;
        while (choice != 0)
        {
            printMenu();
            try
            {
                choice = readInt("Choose an option: ");

                if (choice == 1)
                {
                    createCustomer();
                }
                else if (choice == 2)
                {
                    findCustomer();
                }
                else if (choice == 3)
                {
                    createOrder();
                }
                else if (choice == 4)
                {
                    int id = readInt("Order ID to cancel: ");
                    orderManager.cancelOrder(id);
                    System.out.println("Order " + id + " canceled.");
                }
                else if (choice == 5)
                {
                    int id = readInt("Order ID: ");
                    boolean delivery = readYesNo("Delivery? (y/n): ");
                    orderManager.updateOrderDelivery(id, delivery);
                    System.out.println("Delivery updated: " + orderManager.findOrderIDs(id));
                }
                else if (choice == 6)
                {
                    updateCompletion();
                }
                else if (choice == 7)
                {
                    updateItems();
                }
                else if (choice == 8)
                {
                    int id = readInt("Order ID to track: ");
                    orderManager.trackOrder(id);
                }
                else if (choice == 9)
                {
                    System.out.println("--- Order priority (first = most urgent) ---");
                    orderManager.orderPriority();
                }
                else if (choice == 10)
                {
                    System.out.println("--- Order history ---");
                    orderManager.orderHistory();
                }
                else if (choice == 11)
                {
                    int id = readInt("Order ID to notify about: ");
                    customerManager.sendUpdate(orderManager.findOrderIDs(id));
                }
                else if (choice != 0)
                {
                    System.out.println("Invalid selection.");
                }
            }
            catch (NumberFormatException e)
            {
                System.out.println("ERROR: Please enter a whole number.");
            }
            catch (IllegalArgumentException e)
            {
                System.out.println("ERROR: " + e.getMessage());
            }
            catch (RuntimeException e)
            {
                // Anything unexpected (for example a NullPointerException from a bug)
                System.out.println("UNEXPECTED " + e.getClass().getSimpleName()
                                   + ": " + e.getMessage());
            }
        }
        System.out.println("Goodbye.");
    }

    private static void printMenu()
    {
        System.out.println();
        System.out.println("===== Bakery Management System (test menu) =====");
        System.out.println(" 1. Create customer");
        System.out.println(" 2. Find customer by ID");
        System.out.println(" 3. Create order");
        System.out.println(" 4. Cancel order");
        System.out.println(" 5. Update order delivery");
        System.out.println(" 6. Update order completion (sends notification if completed)");
        System.out.println(" 7. Update order items");
        System.out.println(" 8. Track order");
        System.out.println(" 9. Show order priority");
        System.out.println("10. Show order history");
        System.out.println("11. Send notification for an order");
        System.out.println(" 0. Exit");
    }

    private static void createCustomer()
    {
        System.out.print("Name: ");
        String name = input.nextLine();
        System.out.print("Email: ");
        String email = input.nextLine();
        System.out.print("Phone: ");
        String phone = input.nextLine();
        System.out.print("Address (blank for none): ");
        String address = input.nextLine();
        if (address.trim().isEmpty())
        {
            address = null;
        }

        // Your createCustomer takes (name, email, phone, address)
        Customer c = customerManager.createCustomer(name, email, phone, address);
        System.out.println("Created: " + c);
    }

    private static void findCustomer()
    {
        int id = readInt("Customer ID: ");
        Customer c = customerManager.findCustomerIDs(id);
        if (c == null)
        {
            System.out.println("No customer with ID " + id + ".");
        }
        else
        {
            System.out.println(c);
        }
    }

    private static void createOrder()
    {
        int customerID = readInt("Customer ID: ");
        // createOrder doesn't check the customer, so the menu does
        if (customerManager.findCustomerIDs(customerID) == null)
        {
            throw new IllegalArgumentException("Customer " + customerID + " not found.");
        }

        int count = readInt("How many different items? ");
        if (count <= 0)
        {
            throw new IllegalArgumentException("An order needs at least one item.");
        }
        Items[] items = readItems(count);
        int[] quantities = readQuantities(count);
        boolean isDelivery = readYesNo("Delivery? (y/n): ");

        Order o = orderManager.createOrder(customerID, items, quantities, isDelivery);
        System.out.println("Created: " + o);
    }

    private static void updateCompletion()
    {
        int id = readInt("Order ID: ");
        boolean completed = readYesNo("Mark as completed? (y/n): ");
        orderManager.updateOrderCompletion(id, completed);
        Order order = orderManager.findOrderIDs(id);
        System.out.println("Updated: " + order);

        if (completed)
        {
            customerManager.sendUpdate(order);
        }
    }

    private static void updateItems()
    {
        int id = readInt("Order ID: ");
        int count = readInt("How many different items? ");
        if (count <= 0)
        {
            throw new IllegalArgumentException("An order needs at least one item.");
        }
        Items[] items = readItems(count);
        int[] quantities = readQuantities(count);

        orderManager.updateOrderItems(id, items, quantities);
        System.out.println("Updated: " + orderManager.findOrderIDs(id));
    }

    // Lists the Items constants and lets the user pick by number
    private static Items[] readItems(int count)
    {
        Items[] all = Items.values();
        Items[] chosen = new Items[count];
        for (int i = 0; i < count; i++)
        {
            System.out.println("Item " + (i + 1) + ":");
            for (int j = 0; j < all.length; j++)
            {
                System.out.println("  " + (j + 1) + ". " + all[j]);
            }
            int pick = readInt("  Choose item number: ");
            if (pick < 1 || pick > all.length)
            {
                throw new IllegalArgumentException("Invalid item selection.");
            }
            chosen[i] = all[pick - 1];
        }
        return chosen;
    }

    private static int[] readQuantities(int count)
    {
        int[] quantities = new int[count];
        for (int i = 0; i < count; i++)
        {
            quantities[i] = readInt("Quantity for item " + (i + 1) + ": ");
        }
        return quantities;
    }

    private static boolean readYesNo(String prompt)
    {
        System.out.print(prompt);
        return input.nextLine().trim().equalsIgnoreCase("y");
    }

    // Bad input throws NumberFormatException, which main catches
    private static int readInt(String prompt)
    {
        System.out.print(prompt);
        return Integer.parseInt(input.nextLine().trim());
    }
}