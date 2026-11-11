public class BakeryManagementFE
{
    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args)
    {
        OrderManager orderManager = new OrderManager();
        CustomerManager customerManager = new CustomerManager();

        // Replace with your Items constructor
        Items c1Items = Items.VANILLA_CUPCAKE;
        Items c2Items = Items.CHOCOLATE_CUPCAKE;

        System.out.println("=== CUSTOMER TESTS ===");

        Customer ana = customerManager.createCustomer("Ana", "555-1234", "12 Oak St", "ana@email.com");
        Customer ben = customerManager.createCustomer("Ben", "555-9876", null, "ben@email.com");
        check("Customer IDs are sequential", ana.getCustomerID() == 1 && ben.getCustomerID() == 2);
        check("findCustomerById finds Ana", customerManager.findCustomerIDs(1) == ana);
        check("findCustomerById returns null for 99", customerManager.findCustomerIDs(99) == null);

        try
        {
            customerManager.createCustomer("", "555-0000", null, "x@email.com");
            check("Blank name is rejected", false);
        }
        catch (IllegalArgumentException e)
        {
            check("Blank name is rejected", true);
        }

        try
        {
            customerManager.createCustomer("Cy", "   ", null, "cy@email.com");
            check("Blank phone is rejected", false);
        }
        catch (IllegalArgumentException e)
        {
            check("Blank phone is rejected", true);
        }

        try
        {
            customerManager.createCustomer("Ana Two", "555-0000", null, "ANA@email.com");
            check("Duplicate email is rejected", false);
        }
        catch (IllegalArgumentException e)
        {
            check("Duplicate email is rejected", true);
        }


        System.out.println();
        System.out.println("=== ORDER CREATION TESTS ===");

        Order o1 = orderManager.createOrder(1, new Items[]{cake}, new int[]{2}, true);
        Order o2 = orderManager.createOrder(2, new Items[]{cookie}, new int[]{12}, false);
        Order o3 = orderManager.createOrder(1, new Items[]{cake, cookie}, new int[]{1, 6}, false);

        check("Order IDs are sequential", o2.getOrderID() == o1.getOrderID() + 1
                                       && o3.getOrderID() == o2.getOrderID() + 1);
        check("New order is not completed", !o1.isCompleted());
        check("Delivery flag saved correctly (o1 delivery)", o1.isDelivery());
        check("Delivery flag saved correctly (o2 pickup)", !o2.isDelivery());
        check("Submission time is recorded", o1.getSubmissionTime() != null);

        try
        {
            orderManager.createOrder(1, new Items[]{cake}, new int[]{0}, false);
            check("Quantity 0 is rejected", false);
        }
        catch (IllegalArgumentException e)
        {
            check("Quantity 0 is rejected", true);
        }

        try
        {
            orderManager.createOrder(1, new Items[]{cake}, new int[]{-3}, false);
            check("Negative quantity is rejected", false);
        }
        catch (IllegalArgumentException e)
        {
            check("Negative quantity is rejected", true);
        }

        try
        {
            orderManager.createOrder(1, new Items[]{null}, new int[]{2}, false);
            check("Null item is rejected", false);
        }
        catch (IllegalArgumentException e)
        {
            check("Null item is rejected", true);
        }

        try
        {
            orderManager.createOrder(1, new Items[]{cake, cookie}, new int[]{2}, false);
            check("Mismatched items/quantities rejected", false);
        }
        catch (IllegalArgumentException e)
        {
            check("Mismatched items/quantities rejected", true);
        }

        try
        {
            orderManager.createOrder(1, new Items[]{}, new int[]{}, false);
            check("Empty order is rejected", false);
        }
        catch (IllegalArgumentException e)
        {
            check("Empty order is rejected", true);
        }

        System.out.println();
        System.out.println("=== UPDATE TESTS ===");

        orderManager.updateOrderDelivery(o2.getOrderID(), true);
        check("updateOrderDelivery changes the flag", o2.isDelivery());

        orderManager.updateOrderItems(o2.getOrderID(), new Items[]{cake}, new int[]{3});
        check("updateOrderItems changes the quantity", o2.getQuantity()[0] == 3);

        try
        {
            orderManager.updateOrderDelivery(999, true);
            check("updateOrderDelivery(999) throws", false);
        }
        catch (IllegalArgumentException e)
        {
            check("updateOrderDelivery(999) throws", true);
        }

        try
        {
            orderManager.updateOrderItems(999, new Items[]{cake}, new int[]{1});
            check("updateOrderItems(999) throws", false);
        }
        catch (IllegalArgumentException e)
        {
            check("updateOrderItems(999) throws", true);
        }

        try
        {
            orderManager.updateOrderItems(o2.getOrderID(), new Items[]{cake}, new int[]{-1});
            check("updateOrderItems bad quantity throws", false);
        }
        catch (IllegalArgumentException e)
        {
            check("updateOrderItems bad quantity throws", true);
        }

        System.out.println();
        System.out.println("=== TRACK TESTS ===");

        orderManager.trackOrder(o1.getOrderID());

        try
        {
            orderManager.trackOrder(999);
            check("trackOrder(999) throws", false);
        }
        catch (IllegalArgumentException e)
        {
            check("trackOrder(999) throws", true);
        }

        System.out.println();
        System.out.println("=== PRIORITY (should list orders oldest first) ===");
        orderManager.orderPriority();

        System.out.println();
        System.out.println("=== COMPLETION + NOTIFICATION TESTS ===");
        System.out.println("(look for 'on the move' for order " + o1.getOrderID()
                           + " and 'awaiting pick up' for order " + o3.getOrderID() + ")");

        customerManager.sendUpdate(o1.getOrderID(), true);   // delivery -> on the move
        customerManager.sendUpdate(o3.getOrderID(), true);   // pickup   -> awaiting pick up
        check("Order 1 is now completed", o1.isCompleted());
        check("Order 3 is now completed", o3.isCompleted());

        customerManager.sendUpdate(o1.getOrderID(), false);  // back to not completed -> still underway
        check("Order 1 can be set back to not completed", !o1.isCompleted());

        try
        {
            customerManager.sendUpdate(999, true);
            check("sendUpdate(999) throws", false);
        }
        catch (IllegalArgumentException e)
        {
            check("sendUpdate(999) throws", true);
        }

        System.out.println();
        System.out.println("=== PRIORITY AFTER COMPLETING ORDER 3 ===");
        System.out.println("(order " + o3.getOrderID() + " should NOT appear)");
        orderManager.orderPriority();

        System.out.println();
        System.out.println("=== CANCEL TESTS ===");

        orderManager.cancelOrder(o2.getOrderID());
        System.out.println("  After cancel, priority list:");
        orderManager.orderPriority();
        System.out.println("  (order " + o2.getOrderID() + " should NOT appear)");

        try
        {
            orderManager.cancelOrder(999);
            check("cancelOrder(999) throws", false);
        }
        catch (IllegalArgumentException e)
        {
            check("cancelOrder(999) throws", true);
        }

        System.out.println();
        System.out.println("=============================");
        System.out.println("Passed: " + passed + "   Failed: " + failed);
    }

    private static void check(String testName, boolean condition)
    {
        if (condition)
        {
            passed++;
            System.out.println("PASS: " + testName);
        }
        else
        {
            failed++;
            System.out.println("FAIL: " + testName);
        }
    }
}