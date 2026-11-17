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

//Need to work on input and output 
//private static File currentDataFile = new File("orders.txt")
        
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

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        JMenuItem aboutMenuItem = new JMenuItem("About");

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
        
                                

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        fileMenu.add(exitMenuItem);
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
        

        JLabel title = new JLabel("Bakery Management System", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 25));

        //add menu buttons w/ colors!
        JPanel menuButtons = new JPanel(new GridLayout(5, 1, 15, 15));
        

        JButton createOrderButton = new JButton("Create Order");
        //add
        
        JButton customerButton = new JButton("Customer Management");
        //add
        
        JButton orderButton = new JButton("Order Management");
        //add
        
        JButton priorityButton = new JButton("Order Priority");
        //add
        
        JButton historyButton = new JButton("Order History");
        //add


        //Action listeners for each button.
        

        menuButtons.add(createOrderButton);
        menuButtons.add(customerButton);
        menuButtons.add(orderButton);
        menuButtons.add(priorityButton);
        menuButtons.add(historyButton);

        
        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(menuButtons, BorderLayout.CENTER);
        return menuPanel;
}


        //Create ordeer screen with customer selection






    }
}
