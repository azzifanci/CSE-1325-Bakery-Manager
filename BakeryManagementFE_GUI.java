import javax.swing.*;
import java.awt.*;

public class BakeryManagementFE_GUI {

private static CustomerManager customerManager = new CustomerManager();
private static OrderManager orderManager = new OrderManager(customerManager);

//Need to work on input and output 
//private static File currentDataFile = new File("orders.txt")
private JPanel mainPanel;

public static void main(String[] args) {
        // =====================================================
        // 1. MAIN FRAME
        // =====================================================
        JFrame frame = new JFrame("Bakery Management System");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);


        // =====================================================
        // 2. MENU BAR
        // =====================================================
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenu helpMenu = new JMenu("Help");

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        JMenuItem aboutMenuItem = new JMenuItem("About");

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        fileMenu.add(exitMenuItem);
        helpMenu.add(aboutMenuItem);

        frame.setJMenuBar(menuBar);

        // =====================================================
        // 3. CARD LAYOUT
        // =====================================================

        //Whenever a user clicks on a button, it will take them to that window rather than opening several windows at once 

        CardLayout cardLayout = new CardLayout();

        JPanel mainPanel = new JPanel(cardLayout);

        // =====================================================
        // 4. MAIN MENU PANEL
        // =====================================================

        JPanel menuPanel = new JPanel();

        JLabel title = new JLabel("Bakery Management System", JLabel.CENTER);
        JPanel menuButtons = new JPanel(new GridLayout(5, 1, 15, 15));

        JButton createOrderButton = new JButton("Create Order");
        JButton customerButton = new JButton("Customer Management");
        JButton orderButton = new JButton("Order Management");
        JButton priorityButton = new JButton("Order Priority");
        JButton historyButton = new JButton("Order History");

        menuButtons.add(createOrderButton);
        menuButtons.add(customerButton);
        menuButtons.add(orderButton);
        menuButtons.add(priorityButton);
        menuButtons.add(historyButton);

        mainPanel.add(menuPanel, "Main Menu");
        






    }
}