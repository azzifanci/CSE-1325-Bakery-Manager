import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class BakeryManagementFE_GUI {

    //POTENTIAL GUI (IDEA)
    //TOOK FROM MEAGAN GUI + COMBINE

    //Attributes
    private static CustomerManager customerManager = new CustomerManager();
    private static OrderManager orderManager = new OrderManager(customerManager);
    private static File currentOrderDataFile = new File("orders.txt");

    private static JFrame frame;

    //Display
    private static JTextArea displayArea;
    private static JLabel statusLabel;

    //Print
    private static PrintStream originalOut = System.out;
    private static ByteArrayOutputStream output;

    public static void main(String[] args) {
        // JFrame
        frame = new JFrame("Bakery Management System");
        frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // =========================================================
        // Button Panel
        // =========================================================
        JPanel buttonPanel = new JPanel(new GridLayout (4, 2, 8, 8));
        buttonPanel.setBounds(10, 10, 300, 950);
        buttonPanel.setBorder(new TitledBorder("Menu: "));
        frame.add(buttonPanel);

        JButton orderButton = new JButton("Create Order");
        JButton cancelButton = new JButton("Cancel Order");
        JButton updateButton = new JButton("Update Order");
        JButton trackButton = new JButton("Track Order");
        JButton priorityButton = new JButton("Order Priority List");
        JButton customerButton = new JButton("Create Customer");
        JButton sendUpdateButton = new JButton("Send Update");

        //JButton findCustomerIDButton = new JButton("Find Customer ID");
        //JButton completeButton = new JButton("Mark Order Completed");
        //JButton findOrderButton = new JButton("Find Order ID");
        //JButton findCanceledButton = new JButton("Find Canceled Order");

        buttonPanel.add(orderButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(trackButton);
        buttonPanel.add(priorityButton);
        buttonPanel.add(customerButton);
        buttonPanel.add(sendUpdateButton);

        //buttonPanel.add(findCustomerIDButton);
        //buttonPanel.add(findOrderButton);
        //buttonPanel.add(findCanceledButton);
        //buttonPanel.add(completeButton);

        //Menu bar
        JMenuBar menuBar= new JMenuBar();
        JMenu viewMenu= new JMenu("View");
        JMenuItem orderHistoryItem= new JMenuItem("Order History");
        JMenuItem customerDataItem= new JMenuItem("Customer Info");

        viewMenu.add(orderHistoryItem);
        viewMenu.addSeparator();
        viewMenu.add(customerDataItem);

        JMenu fileMenu = new JMenu ("File");
        JMenuItem loadMenuItem = new JMenuItem("Load From File");
        JMenuItem saveAsMenuItem = new JMenuItem("Save As File");
        
        fileMenu.add(loadMenuItem);
        fileMenu.add(saveAsMenuItem);
        
        menuBar.add(viewMenu);
        menuBar.add(fileMenu);

        frame.setJMenuBar(menuBar);

        // =========================================================
        // Results Panel
        // =========================================================
        JPanel displayPanel = new JPanel(null);
        displayPanel.setBounds(320, 10, 660, 950);
        displayPanel.setBorder(new TitledBorder ("Results"));
        frame.add(displayPanel);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setLineWrap(true);
        displayArea.setWrapStyleWord(true);
        displayArea.setFont(new Font("Inter", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBounds(15, 25, 630, 880);
        displayPanel.add(scrollPane);

        statusLabel = new JLabel("Status: Ready");
        statusLabel.setBounds(15, 915, 630, 25);
        displayPanel.add(statusLabel);

        // =========================================================
        // Event Listeners
        // =========================================================
        orderButton.addActionListener(e -> openOrderWindow());
        cancelButton.addActionListener(e -> cancelOrder());
        updateButton.addActionListener(e-> updateOrderWindow());
        customerButton.addActionListener(e -> openCustomerWindow(null));
        trackButton.addActionListener(e-> trackOrder());
        priorityButton.addActionListener(e-> showPriority());
        orderHistoryItem.addActionListener(e-> showHistory());
        customerDataItem.addActionListener(e-> showDataBase());
        sendUpdateButton.addActionListener(e-> sendUpdate());
        loadMenuItem.addActionListener(e -> loadFromFile());
        saveAsMenuItem.addActionListener(e-> saveAsFile());

        //findCustomerIDButton.addActionListener(e -> findCustomerIDs());
        //findOrderButton.addActionListener(e-> findOrder());
        //findCanceledButton.addActionListener(e-> findCanceled());

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // =========================================================
    // FR-1: Create Order (ADD CAPABILITY TO HAVE MULTIPLE ITEMS)
    // =========================================================  
    private static void openOrderWindow()
    {
        GenLL <Items> addedItems = new GenLL <>();
        GenLL <Integer> addedQuantities = new GenLL <>();

        JDialog dialog = new JDialog(frame, "Create Order", true);
        dialog.setSize(440, 470);
        dialog.setLayout(null);

        JLabel idLabel = new JLabel("Customer ID: ");
        idLabel.setBounds(20, 20, 90, 25);
        dialog.add(idLabel);

        JTextField customerIDField = new JTextField();
        customerIDField.setBounds(115, 20, 120, 25);
        dialog.add(customerIDField);

        JButton newCustomerButton = new JButton("New Customer");
        newCustomerButton.setBounds(245, 20, 160, 25);
        dialog.add(newCustomerButton);

        JLabel itemLabel = new JLabel("Item: ");
        itemLabel.setBounds(20, 60, 90, 25);
        dialog.add(itemLabel);
        JComboBox<Items> itemComboBox = new JComboBox<>(Items.values());
        itemComboBox.setBounds(115, 60, 290, 25);
        dialog.add(itemComboBox);

        JLabel quantityLabel = new JLabel("Quantity: ");
        quantityLabel.setBounds(20, 95, 90, 25);
        dialog.add(quantityLabel);
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
        quantitySpinner.setBounds(115, 95, 80, 25);
        quantitySpinner.setBounds(115, 95, 80, 25);
        dialog.add(quantitySpinner);

        JButton addItemButton = new JButton("Add Item(s)");
        addItemButton.setBounds(245, 95, 160, 25);
        dialog.add(addItemButton);

        JTextArea itemsArea = new JTextArea("No Items added yet.");
        itemsArea.setEditable(false);
        JScrollPane itemsScroll = new JScrollPane(itemsArea);
        itemsScroll.setBounds(20, 135, 385, 160);
        itemsScroll.setBorder(new TitledBorder("Items: "));
        dialog.add(itemsScroll);

        JButton clearItemsButton = new JButton("Clear Items");
        clearItemsButton.setBounds(20, 305, 120, 25);
        dialog.add(clearItemsButton);

        JCheckBox deliveryCheckBox = new JCheckBox("Delivery");
        deliveryCheckBox.setBounds(20, 345, 150, 25);
        dialog.add(deliveryCheckBox);

        JButton saveButton = new JButton("Save Order");
        saveButton.setBounds(100, 390, 120, 30);
        dialog.add(saveButton);

        JButton closeButton = new JButton("Cancel");
        closeButton.setBounds(235, 390, 100, 30);
        dialog.add(closeButton);

        newCustomerButton.addActionListener(e -> openCustomerWindow(customerIDField));

        addItemButton.addActionListener (e -> 
            {
                Items item = (Items) itemComboBox.getSelectedItem();
                    int quantity = (Integer) quantitySpinner.getValue();

                    for(int i = 0; i< addedItems.getSize(); i++)
                    {
                        if(addedItems.get(i) == item)
                        {
                            showDialogError(dialog, item + " is already added. Click Clear Items to start over");
                            return;
                        }
                    }
                addedItems.add(item);
                addedQuantities.add(quantity);


                String listText = "";
                for(int i = 0; i<addedItems.getSize(); i++){
                    listText = listText + addedItems.get(i) + " - " + addedQuantities.get(i) + "\n";
                }
                itemsArea.setText(listText);
                quantitySpinner.setValue(1);
            }
            );

        clearItemsButton.addActionListener(e -> 
            {
                while(addedItems.getSize() > 0)
                {
                    addedItems.remove(addedItems.get(0));
                    addedQuantities.remove(addedQuantities.get(0));
                }
                itemsArea.setText("No items added yet.");
            }
        );

        saveButton.addActionListener(e -> {
                try
                {
                    int customerID = Integer.parseInt(customerIDField.getText());
                    Items[] items = new Items[addedItems.getSize()];
                    int[] quantities = new int[addedItems.getSize()];
                    for(int i = 0; i< items.length; i++)
                    {
                        items[i] = addedItems.get(i);
                        quantities[i] = addedQuantities.get(i);
                    }
                    Order order = orderManager.createOrder(customerID, items, quantities, deliveryCheckBox.isSelected());

                    display("Created order: \n" + order);
                    statusLabel.setText("Status: Order " + order.getOrderID() + " created");
                    dialog.dispose();

                }
                catch(NumberFormatException ex)
                {
                    showDialogError(dialog, "Quantity must be non-negative");
                }
                catch(IllegalArgumentException ex)
                {
                    showDialogError(dialog, ex.getMessage());
                }
        });     

        closeButton.addActionListener(e -> dialog.dispose());

        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    // =========================================================
    // FR-2: Cancel Order (TO-DO)
    // =========================================================  
    private static void cancelOrder()
    {
        String input = askForInput("Cancel order", "Enter order ID: ");

        if(input == null)
        {
            return;
        }

        try
        {
            int id = Integer.parseInt(input);
            orderManager.cancelOrder(id);
            display("Order " + id + " canceled.");
            statusLabel.setText("Status: Order " + id + " canceled");
        }
        catch(NumberFormatException e)
        {
            showError("Order ID must be a whole number.");
        }
        catch(IllegalArgumentException e)
        {
            showError(e.getMessage());
        }

    }
    // =========================================================
    // FR-3-5: Update Order Methods
    // =========================================================  
    private static void updateOrderWindow()
    {

        //Ask user which order they would like to update
        String input = askForInput("Update Order", "Enter Order ID: ");

        //User clicked cancel
        if(input == null)
        {
            return;
        }

        try
        {
            //convert orderID entered by the user into an integer
            int orderID = Integer.parseInt(input);
            
            //Ask which part of the order should be updated, such as 
            //Order, delivery, and items
            String choice = askForInput("Update Order", +
                                        
            "Enter:\n1 = Complete Order\n2 = Change Delivery\n3 = Change Items");

            //user clicked cancel
            if(choice == null)
            {
                return;
            }

            //Option 1: Mark the order as completed
            if(choice.equals("1"))
            {
                orderManager.updateOrderCompletion(orderID, true);
                display("Order " + orderID + "marked as completed.");

                statusLabel.setText("Status: Order " + orderID + "completed");
            }

                //Option 2: Change whether the order is delivery or pickup
            else if(choice.equals("2"))
            {
                String deliveryInput = askForInput("Delivery Status", +
                "Enter true for delivery or false for pickup:");

                //User clicked Cancel
                if(deliveryInput == null)
                {
                    return;
                }

                //Convert text input to boolean value
                boolean delivery = Boolean.parseBoolean(deliveryInput);

                orderManager.updateOrderDelivery(orderID, delivery);

                display("Delivery status updated for Order " + orderID);

                statusLabel.setText("Status: Delivery updated for Order " + orderID);
            }

            //Option 3: Replace the current order items
            else if(choice.equals("3"))
            {
                //: lets the user select one item from the menu
                Items item = (Items) JOptionPane.showInputDialog(frame, +
                    "Select Item: ", "Update Item", +   
                    JOptionPane.QUESTION_MESSAGE, null, Items.values(), Items.values()[0]);

                //Clicked cancel
                if(item == null)
                {
                    return;
                }

                //Ask how many of the item should be ordered
                String quantityInput = askForInput("Quantity", "Enter quantity: ");

                //clicked cancel
                if(quantityInput == null)
                {
                    return;
                }

                int quantity = Integer.parseInt(quantityInput);

                //Backend method requires arrays for items and quantities
                Items[] items = {item};
                int[] quantities = {quantity};

                orderManager.updateOrderItems(orderID, items, quantities);

                display("Items updated for Order " + orderID);

                statusLabel.setText("Status: Items updated for Order " + orderID);
            }

            //User entered something other than 1, 2, or 3
            else
            {
                showError("Invalid option.");
            }
        }

        //deals with invalid number input
        catch(NumberFormatException e)
        {
            showError("Please enter valid numbers.");
        }

        //deals with errors generated by the backend
        catch(IllegalArgumentException e)
            {
                showError(e.getMessage());
            }   
    }
    // =========================================================
    // FR-6: Track Order
    // =========================================================  
    private static void trackOrder()
    {
        String input = askForInput("Track order", "Enter order ID: ");

        if(input == null)
        {
            return;
        }

        try
        {
            int id = Integer.parseInt(input);
            grabMessage();
            orderManager.trackOrder(id);
            String message = getMessage();
            display(message);
            display("Tracked Order " + id + "\n\n" + message);
            statusLabel.setText("Status: Tracked Order " + id);
        }
        catch(NumberFormatException e)
        {
            showError("Order ID must be a whole number.");
        }
        catch(IllegalArgumentException e)
        {
            showError(e.getMessage());
        }
        finally{
            System.setOut(originalOut);
        }

    }
    // =========================================================
    // FR-7:  Order Priority
    // =========================================================  
    private static void showPriority()
    {
        try{
            grabMessage();
            orderManager.orderPriority();
            String message = getMessage();
            display("=============== Order Priority ================\n" + "Priority from High to Low\n\n" + message);
            statusLabel.setText("Status: Showing order priority list");
        }
        finally
        {
            System.setOut(originalOut);
        }
    }
    // =========================================================
    // FR-8: Order History
    // =========================================================  
    private static void showHistory()
    {
        try{
            grabMessage();
            orderManager.orderHistory();
            String message = getMessage();
            display("=============== Order History ================\n" + message);
            statusLabel.setText("Status: Showing order history");
        }
        finally
        {
            System.setOut(originalOut);
        }
    }
    private static void showDataBase()
    {
        try
        {
            grabMessage();
            //customerManager.customerDataBase();
            String message= getMessage();
            display("=============== Customer Information ================\n" + message);
        }
        finally {
            System.setOut(originalOut);
        }
    }
    // =========================================================
    // FR-11: Create Customer
    // =========================================================  
    private static void openCustomerWindow(JTextField idFieldToFill)
    {
        JDialog dialog = new JDialog(frame, "Create Customer", true);
        dialog.setSize(450, 300);
        dialog.setLayout(null);

        JLabel nameLabel = new JLabel("Name: ");
        nameLabel.setBounds(20, 20, 110, 25);
        dialog.add(nameLabel);
        JTextField nameField = new JTextField();
        nameField.setBounds(110, 20, 280, 25);
        dialog.add(nameField);

        JLabel emailLabel = new JLabel("Email: ");
        emailLabel.setBounds(20, 55, 110, 25);
        dialog.add(emailLabel);
        JTextField emailField = new JTextField();
        emailField.setBounds(135, 55, 280, 25);
        dialog.add(emailField);

        JLabel phoneLabel = new JLabel("Phone Number: ");
        phoneLabel.setBounds(20, 90, 110, 25);
        dialog.add(phoneLabel);
        JTextField phoneTextField = new JTextField();
        phoneTextField.setBounds(135, 90, 280, 25);
        dialog.add(phoneTextField);

        JLabel addressLabel = new JLabel("Address: ");
        addressLabel.setBounds(20, 125, 110, 25);
        dialog.add(addressLabel);
        JTextField addressField = new JTextField();
        addressField.setBounds(135, 125, 280, 25);
        dialog.add(addressField);

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(135, 180, 100, 30);
        dialog.add(saveButton);

        JButton closeButton = new JButton("Cancel");
        closeButton.setBounds(250, 180, 100, 30);
        dialog.add(closeButton);


        saveButton.addActionListener(e ->
                {
                    try
                    {
                        String name = nameField.getText();
                        String email = emailField.getText();
                        String phoneNumber = phoneTextField.getText();
                        String address = addressField.getText();

                        Customer c = customerManager.createCustomer(name, email, phoneNumber, address);

                        if(idFieldToFill != null)
                        {
                            idFieldToFill.setText(String.valueOf(c.getCustomerID()));
                        }

                        display("Created customer:\n" + c);
                        statusLabel.setText("Status: Customer " + c.getCustomerID() + " created");
                        dialog.dispose();
                    }
                    catch(IllegalArgumentException ex)
                    {
                        showDialogError(dialog, ex.getMessage());
                    }
                }
        );

        closeButton.addActionListener(e -> dialog.dispose());

        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);

    }
    // =========================================================
    // FR-12: Send Update
    // =========================================================  
    private static void sendUpdate()
    {
        String input = askForInput("Track order", "Enter order ID: ");

        if(input == null)
        {
            return;
        }

        try
        {
            int id = Integer.parseInt(input);
            Order order = orderManager.findOrderIDs(id);
            grabMessage();
            customerManager.sendUpdate(order);
            display(getMessage());
            display("Tracked Order " + id);
            statusLabel.setText("Status: Update sent for order " + id);
        }
        catch(NumberFormatException e)
        {
            showError("Order ID must be a whole number.");
        }
        catch(IllegalArgumentException e)
        {
            showError(e.getMessage());
        }
        finally{
            System.setOut(originalOut);
        }
    }
    // =========================================================
    // LOAD FROM FILE
    // =========================================================  
    private static void loadFromFile()
    {
        JFileChooser chooser = new JFileChooser();
        int choice = chooser.showOpenDialog(frame);

        if (choice == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();

            try {
                orderManager.loadFromFile(selectedFile.getAbsolutePath());
                currentOrderDataFile = selectedFile;

                statusLabel.setText(
                        "Status: Loaded " + currentOrderDataFile.getName());
            }
            catch (FileNotFoundException e) {
                showError("File not found: " + selectedFile.getName());
            }
        }
    }
    // =========================================================
    // SAVE TO FILE 
    // =========================================================  
    private static void saveAsFile()
    {
        JFileChooser chooser = new JFileChooser();
        int choice = chooser.showSaveDialog(frame);

        if (choice == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();

            try {
                orderManager.saveToFile(selectedFile.getAbsolutePath());
                currentOrderDataFile = selectedFile;

                statusLabel.setText(
                        "Status: Saved orders to " + currentOrderDataFile.getName());
            }
            catch (FileNotFoundException e) {
                showError("File not found: " + selectedFile.getName());
            }
        }
    }

    // =========================================================
    // FR-13: Find Customer ID (HELPER METHOD; MAY REMOVE)
    // =========================================================  
    private static void findCustomerIDs()
    {

    }

    // =========================================================
    // FR-9: Find Order ID (HELPER METHOD; MAY REMOVE)
    // =========================================================  
    private static void findOrder()
    {

    }
    // =========================================================
    // FR-10: Find Canceled Order (HELPER METHOD; MAY REMOVE)
    // =========================================================  
    private static void findCanceled()
    {

    }

    // =========================================================
    // Front End Helpers
    // ========================================================= 
    private static void display(String text)
    {
        displayArea.setText(text);
    }

    private static void showDialogError(JDialog dialog, String message)
    {
        JOptionPane.showMessageDialog(dialog, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private static String askForInput(String title, String prompt)
    {
        return JOptionPane.showInputDialog(frame, prompt, title, JOptionPane.QUESTION_MESSAGE);
    }

    private static void showError(String message)
    {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
        statusLabel.setText("Status: Error");
    }

    private static void grabMessage()
    {
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    private static String getMessage()
    {
        System.setOut(originalOut);
        return output.toString();
    }
}
