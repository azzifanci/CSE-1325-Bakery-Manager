import javax.swing.*;
import java.awt.*;
import java.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
//import java.util.Arrays;

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
     
        // Main frame
        JFrame frame = new JFrame("Bakery Management System");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);


        // Menu bar 
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenu helpMenu = new JMenu("Help");
       
        JMenuItem loadMenuItem = new JMenuItem("Load From File");
        JMenuItem saveAsMenuItem = new JMenuItem("Save As File");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        JMenuItem aboutMenuItem = new JMenuItem("About");
        
        // Add Action Listeners for File I/O
        loadMenuItem.addActionListener(e -> loadFromFile(frame));
        saveAsMenuItem.addActionListener(e -> saveAsFile(frame));

        exitMenuItem.addActionListener(new ActionListener())
                {
                        public void actionPerformed(ActionEvent e)
                        {
                                System.exit(0);
                        }
        }

        aboutMenuItem.addActionListener(new ActionListener())
        {
                public void actionPerformed(ActionEvent e)
                {
                        JOptionPane.showMessageDialog(frame, "Bakery Management System");
                }
        }
        // Assemble File Menu 
        fileMenu.add(loadMenuItem);
        fileMenu.add(saveAsMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        helpMenu.add(aboutMenuItem);
                                

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        
        helpMenu.add(aboutMenuItem);

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

        backButton.addActionListner(new AcitonListner())
        {
                public void actionPerformed(ActinEvent e)
                {
                        cardLayout.show(mainPanel, "Main Menu");
                }

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

        
                //Order Priority button (Meagan)
        priorityButton.addActionListener(new ActionListener()
        {
                public void actionPerformed(ActionEvent e)
                {
                        refreshPriorityTable();
                        cardLayout.show(mainPanel, "Order Priority");
                }
        }

                                         
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
            orderManager.loadFromFile(selectedFile.getAbsolutePath());
            currentDataFile = selectedFile;
            JOptionPane.showMessageDialog(frame, "Successfully loaded from " + currentDataFile.getName(), "Success", JOptionPane.INFORMATION_MESSAGE);
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
        centerPanel.setBorder(BorderFactory.createTitledBorder("Customer Information"));

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
        JCheckBox deliveryBox = new JCheckBox("Delivery");
        idPanel.add(new JLabel("Customer ID: "));
        idPanel.add(customerIDField);
        idPanel.add(deliveryBox);
        customerSection.add(radioPanel);
        customerSection.add(idPanel);

        
        existingRadio.addActionListener(new ActionListener()
        {
                public void actionPerformed(ActionEvent e)
                {
                        newCustomerFormBtn.setEnabled(false);
                }

        newRadio.addActionListener(new ActionListener()
        {
                public void actionPerformed(ActionEvent e)
                {
                        newCustomerFormBtn.setEnabled(true);
                        showNewCustomerPopup(panel, customerIdField);
                }

        //Items and quantities
        Items[] allItems = Items.values();
        JSpinner[] spinners = new JSpinner[allItems.length];
        JPanel itemsPanel = new JPanel(new GridLayout(allItems.length + 1, 2, 10, 10));
        itemsPanel.setBorder(BorderFactory.createTitledBorder("Bakery Items"));
        itemPanel.add(new JLabel("Item"));
        itemsPanel.add(new JLabel("Quantity"));
        for (int i = 0; i < allItems.length; i++)
        {
                itemsPanel.add(new JLabel(allItems[i].toString()));
                spinners[i] = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
                itemsPanel.add(spinners[i]);
        }
                        

 //Save Order Button
        JButton saveBtn = new JButton("Save Order");
        saveBtn.setBackground(Color.GREEN);
        saveBtn.addActionListener(new ActionListener()
        {
                public void actionPerformed(ActionEvent e)
                {
                        try
                        {
                                int customerID = Integer.parseInt(customerIdField.getText().trim());
                                boolean isDelivery = deliveryBox.isSelected();
                                        
                                //Count how many items have quantity > 0
                                int count = 0;
                                for (int i = 0; i < allItems.length; i++)
                                {
                                        if((Integer) spinners[i].getValue() > 0)
                                        {
                                                count++;
                                        }
                                }
                                Items[] selectedItems = new Items[count];
                                int[] selectedQuantities = new int[count];
                                int index = 0;
                                for (int i = 0; i < allItems.length; i++)
                                {
                                        int qty = (Integer) spinners[i].getValue();
                                        if (qty > 0)
                                        {
                                                selectedItems[index] = allItems[i];
                                                selectedQuantities[index] = qty;
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
private static void showNewCustomerPopup(Component parent, JTextPield customerIDField)
{
//add



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

cancelBtn.addActionListener(new ActionListener()
{
        public void actoinPerformed(ActionEvent e)
        {
                String input = JOptionPane.showInputDialog(panel, "Enter OrderID to cancel : ");
                if (input == null)
                {
                        return;
                }
                try
                {
                        int orderID = Integer.parseInt(input.trim())
                        //cancelOrder method

                }

        }



//Track order button
trackBtn.add
                                
        
                        



    }
}
