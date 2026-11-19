import javax.swing.*;
import java.awt.*;
import java.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Arrays;
//import java.io.File;
//import java.io.FileNotFoundException;


//!not yet completed!

public class BakeryManagementFE_GUI {

private static CustomerManager customerManager = new CustomerManager();
private static OrderManager orderManager = new OrderManager();

//input and output 
private static File currentDataFile = new File("orders.txt");

        
private static JPanel mainPanel = new JPanel(cardLayout);
private static CardLayout cardLayout = new CardLayout();

private static DefaultTableModel orderTableModel;
private static DefaultTableModel priorityTableModel;
private static DefaultTableModel customerTableModel;
private static DefaultTableModel historyTableModel;


public static void main(String[] args) {
     
        // JFrame
        JFrame frame = new JFrame("Bakery Management System");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // Menu bar 
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
       
        JMenuItem loadMenuItem = new JMenuItem("Load From File");
        JMenuItem saveAsMenuItem = new JMenuItem("Save As File");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        
        // Add Action Listeners for File I/O
        loadMenuItem.addActionListener(e -> loadFromFile(frame));
        saveAsMenuItem.addActionListener(e -> saveAsFile(frame));
        exitMenuItem.addActionListener(e -> System.exit(0));

        
        // Assemble File Menu 
        fileMenu.add(loadMenuItem);
        fileMenu.add(saveAsMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);
                                
        menuBar.add(fileMenu);

        frame.setJMenuBar(menuBar);

        
        // Card layout
        //When a user clicks a button, it takes them to that certain window rather than all windows at once.

        mainPanel.add(createMainMenuPanel(), "Main Menu");
        mainPanel.add(createOrderScreen(), "Create Order");
        mainPanel.add(createCustomerScreen(), "Customer Management");
        mainPanel.add(createOrderManagementScreen(), "Order Management");
        mainPanel.add(createPriorityScreen(), "Order Priority");
        mainPanel.add(createHistoryScreen(), "Order History");

        frame.add(mainPanel);
        frame.setVisible(true);
}

//Helper for back button
private static JPanel makeHeader(String titleText)
{
        JPanel header = new JPanel(new BorderLayout());
        JButton backButton = new JButton("Back to Main Menu");
        backButton.setBackground(Color.RED);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Main Menu"));

        
        JLabel title = new JLabel (titleText, JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));

        header.add(backButton, BorderLayout.WEST);
        header.add(title, BorderLayout.CENTER);
        return header;
}
        //Main menu panel 
                        
private static JPanel createMainMenuPanel()
{
        Jpanel menuPanel = new JPanel(new BorderLayout(20, 20));
        //add borders 
        menuPanel.setBorder(BorderFactory.createEmptyBorder(30, 180, 50, 180));

        JLabel title = new JLabel("Bakery Management System", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 25));

        //add menu buttons w/ colors! 
        JPanel menuButtons = new JPanel(new GridLayout(5, 1, 15, 15));
        

        JButton createOrderButton = new JButton("Create Order");
        createOrderButton.setBackground(Color.GREEN);
        
        JButton customerButton = new JButton("Customer Management");
        customerButton.setBackground(Color.PINK);
        
        JButton orderButton = new JButton("Order Management");
        orderButton.setBackground(Color.CYAN);
        
        JButton priorityButton = new JButton("Order Priority");
        priorityButton.setBackground(Color.ORANGE);
        
        JButton historyButton = new JButton("Order History");
        historyButton.setBackground(Color.YELLOW);

        //Action listeners for each button. 
        
       //Add action listener for createOrder button

        //add action listener for customerManagement button

       //add action listener for orderManagement button

        
                //Order Priority action listener (Meagan)
        priorityButton.addActionListener(e ->
                refreshOrderTable();
                cardLayout.show(mainPanel, "Order Management");
                

                                         
       //Add menu buttons
                                                 
        //Add createOrderButton for menu
        //Add customerButton for menu
        // Add orderButton for menu
        menuButtons.add(priorityButton); //Meagan
        //Add historyButton for menu

        
        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(menuButtons, BorderLayout.CENTER);
        return menuPanel;
}

//Input File (load)
private static void loadFromFile(JFrame frame) 
{
    JFileChooser chooser = new JFileChooser();
    int choice = chooser.showOpenDialog(frame);

    if (choice == JFileChooser.APPROVE_OPTION) 
    {
        File selectedFile = chooser.getSelectedFile();
        try 
        {
                if (!selectedFile.exists())
                {
                        throw new FileNotFoundException(selectedFile.getName());
                }
                //Implement orderManager load from file.
            orderManager.loadFromFile(selectedFile.getAbsolutePath());
            currentDataFile = selectedFile;
            JOptionPane.showMessageDialog(frame, "Successfully loaded from " + currentDataFile.getName(), "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshPriorityDisplay();
                refreshHistoryTable();
                
            // Refresh your table views here if applicable
                } catch (FileNotFoundException e) 
                {
            JOptionPane.showMessageDialog(frame, "File not found: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
    }
}
//File output (save as)
private static void saveAsFile(JFrame frame) 
{
    JFileChooser chooser = new JFileChooser();
    int choice = chooser.showSaveDialog(frame);

    if (choice == JFileChooser.APPROVE_OPTION) 
    {
        File selectedFile = chooser.getSelectedFile();
        try 
                {
                        if (selectedFile == null)
                        {
                                throw new FileNotFoundException("Invalid file");
                        }
                        
            orderManager.saveToFile(selectedFile.getAbsolutePath());
            currentDataFile = selectedFile;
            JOptionPane.showMessageDialog(frame, "Successfully saved as " + currentDataFile.getName(), "Success", JOptionPane.INFORMATION_MESSAGE);
                } 
        catch (FileNotFoundException e) 
                {
            JOptionPane.showMessageDialog(frame, "Error saving file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
    }
}
        //Create ordeer screen with customer selection


private static JPanel createOrderScreen()
{
        JPanel panel = new JPanel(new JPanel(new BorderLayout(10, 10));
        panel.add(makeHeader("Create Order"), BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(15, 50, 20, 50));

        //customer selection new and existing
        JPanel customerPanel = new JPanel(new GridLayout(2, 1));
        customerPanel.setBorder(new TitledBorder("Customer Information"));

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JRadioButton existingRadio = new JRadioButton("Existing Customer", true);
        JRadioButton newRadio = new JRadioButton("New Customer");
        ButtonGroup group = new ButtonGroup();
        group.add(existingRadio);
        group.add(newRadio);

        JButton newCustomerFormBtn = new JButton("New Customer Form");
        newCustomerFormBtn.setEnabled(false);
        
        radioPanel.add(existingRadio);
        radioPanel.add(newRadio);
        radioPanel.add(newCustomerFormBtn);
        
        JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField customerIdField = new JTextField(10);
        JCheckBox deliveryCheckBox = new JCheckBox("Delivery");
        idRow.add(new JLabel("Customer ID: "));
        idRow.add(customerIDField);
        idRow.add(deliveryBox);
        
        customerPanel.add(radioPanel);
        customerPanel.add(idPanel);

        
        existingRadio.addActionListener(e -> newCustomerFormBtn.setEnabled(false));
        newRadio.addActionListener(e -> 
                newCustomerFormBtn.setEnabled(true);
                openNewCustomerForm(panel, customerIdField);
}
        newCustomerFormBtn.addActionListener(e -> openNewCustomerForm(panel, customerIdField));
//bakery items
  

        Items[] allItems = Items.values();
        JTextField[] qtyFields = new JTextField[allItems.length];

        JPanel itemsPanel = new JPanel(new GridLayout(allItems.length + 1, 2, 10, 10));
        itemsPanel.setBorder(new TitledBorder("Bakery Items"));
        itemPanel.add(new JLabel("Item"));
        itemsPanel.add(new JLabel("Quantity"));

        for (int i = 0; i < allItems.length; i++)
        {
                itemsPanel.add(new JLabel(allItems[i].toString()));
                gtyFields[i] = new JTextField("0");
                itemsPanel.add(qtyFields[i]);
        }
                        

 //Save Order Button
        JButton saveButton = new JButton("Save Order");
        saveButton.setBackground(Color.GREEN);
        saveButton.addActionListener(e ->
                try 
                {
                        int customerID = Integer.parseInt(customerIdField.getText().trim());
                        boolean isDelivery = deliveryCheckBox.isSelected();
                        int count = 0;
                                //Count how many items have quantity > 0
                        for (int i = 0; i < allItems.length; i++)
                        {
                                int q = Integer.parseInt(qtyFields[i].getText().trim());
                                if(q > 0)
                                {
                                        count++;
                                }
                        }
                        Items[] selectedItems = new Items[count];
                        int[] selectedQuantities = new int[count];
                        int index = 0;
                        
                        for (int i = 0; i < allItems.length; i++)
                        {
                                int q = Integer.parseInt(qtyFields[i].getText().trim());
                                if (q > 0)
                                {
                                        selectedItems[index] = allItems[i];
                                        selectedQuantities[index] = q;
                                                index++;
                                }
                        }
                        
        //createOrder method goes here
        
                                                        
                                
        centerPanel.add(customerSection, BorderLayout.NORTH);
        centerPanel.add(itemsPanel, BorderLayout.CENTER);
        centerPanel.add(saveBtn, BorderLayout.SOUTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        return panel;
}

//Pop up for adding a new customer during the order creation
private static void showNewCustomerPopup(Component parent, JTextField customerIDField)
{

        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField addressField = new JTextField();
        JPanel form = new JPanel(new GridLayout(4, 2, 8, 8));

        form.add(new JLabel("Name: "));
        form.add(nameField);
        form.add(new JLabel("Email: "));
        form.add(emailField);
        form.add(new JLabel("Phone: "));
        form.add(phoneField);
        form.add(new JLabel("Address: ");
        form.add(addressField);

        int result = JOptionPane.showConfirmDialog(parent, form, "New Customer", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION)
        {
                String name = nameField.getText();
                String email = emailField.getText();
                int phone = phoneField.getText();
                String address = addressField.getText();



        //createCustomer method goes here
                
}
}


//order management screen
private static JPanel createOrderManagementScreen()
{
//add
        
        //update order button

        //updateOrder method for items


        //updateOrder method for delivery

        //updateOrder method for completion 

        
        //sendUpdate method
}


//Cancel Order button (Make it red)


                       
                        //cancelOrder method

                }

        }



//Track order button

        //trackOrder method

}

//sendUpdate and order completion confirmation


        //sendUpdate method

}



//View order by ID
viewBtn.addActionListener(e -> 
        String input = JOptionPane.showInputDialog(panel, "Enter orderID to view order: ");
        if (input == null)
                return;
        try
        {
                int orderID = Integer.parseInt(input);
        
                Order o = orderManager.findOrderIDs(orderID);
                if (o == null)
                {
                        JOptionPane.showMessageDialog(panel, "Order not found.");
                }
                else
                {
                        JOptionPane.showMessageDialog(panel, o.toString());
                }
        }
        catch (NumberFormatException ex)
        {
                JOptionPane.showMessageDialog(panel, "Invalid orderID.", JOptionPane.ERROR_MESSAGE);
        }
}

actionPanel.add(updateBtn);
actionPanel.add(cancelBtn);
actionPanel.add(trackBtn);
actionPanel.add(completeBtn);
actionPanel.add(viewBtn);
panel.add(scrollPane, BorderLayout.CENTER);
panel.add(buttonPanel, BorderLayout.SOUTH);
return panel;
}

private static void refreshOrderTable()
{
        orderTableModel.setRowCount(0);
        int id = 1;
        while (orderManager.findOrderIDs(id) != null)
        {
                Order o = orderManager.FindOrderIDs(id);
                String status = orderManager.isCanceled(id)//add

        ]
}


//orderPriority screen (Meagan)
private static JPanel createPriorityScreen()
{
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.add(makeHeader("Order Priority Queue"), BorderLayout.NORTH);
        JPanel centerSplit = new JPanel(new GridLayout(2, 1, 10, 10));
        centerSplit.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        String[] columns = {"Priority", "Order ID", "Customer ID", "Items", "Quantities", "Delivery", "Submission Time"};
        priorityTabelModel = new DefaultTabelModel(columns, 0);
        JTabel priorityTabel = new JTabel(priorityTabelModel);
        JScrollPane tableScroll = new JScrollPane(priorityTable);
        tableScroll.setBorder(new TitledBorder("Priority Queue based on submission time."));
        priorityConsoleArea = new JTextArea();
        priorityConsoleArea.setEditable(false);
        JScrollPane textScroll = new JScrollPane(priorityConsoleArea);
        textScroll.setBorder(new TitledBorder("Order Priority"));
        centerSplit.add(tableScroll);
        centerSplit.add(textScroll);
        JButton refreshBtn = new JButton("Refresh priority");
        refreshBtn.setBackground(Color.ORANGE);
        refreshBtn.addActionListener(e -> refreshPriorityDisplay());
        JPanel bottom = new JPanel();
        bottom.add(refreshbBtn);
        panel.add(centerSplit, BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);
        return panel;
}

private static void refreshPriorityDIsplay()
{
        //gets output from orderpriority method
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));
        orderManager.orderPriority();
        System.setOut(originalOut);
        priorityConsoleArea.setText(output.toString());
        
        //submission time order
        priorityTableModel.setRowCount(0);
        int id = 1;
        int rant = 1;
        while (orderManager.findOrderIDs(id) != null)
        {
                Order o = orderManager.findOrderIDs(id);
                if(!0.isCompleted() && !orderManager.isCanceled(id))
                {
                        priorityTableModel.addRow(new Object[]
                                rank,
                                o.getOrderID(),
                                o.getCustomerID(),
                                Arrays.toString(o.getItems()),
                                Arrays.toString(o.getQuantity()),
                                o.isDelivery(),
                                o.getSubmissionTime();
                                rank++;
                }
                id++;
        }
}

        //order history
private static JPanel createHistoryScreen()
{

}



        //customer management screen
private static JPanel createCustomerScreen()
{

        
        //createCustomer method
}





private static void refreshCustomerTable()
{
        // c = customer
                        
                

    }
}
