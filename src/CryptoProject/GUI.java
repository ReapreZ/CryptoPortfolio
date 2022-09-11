package CryptoProject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import static CryptoProject.Main.*;

public class GUI extends JFrame implements ActionListener {

    static JTable table;
    static JTable table2;
    static JScrollPane jsp;
    static JScrollPane jsp2;
    JButton btop100;
    JButton bportfolio;
    JButton baddcrypto;
    static JLabel totalvalue;
    static JLabel totalinvest;
    static JLabel lname;
    static JLabel lamount;
    static JLabel linvest;
    static JTextField tfname;
    static JTextField tfamount;
    static JTextField tfinvest;

    public static void main(String[] args) {
        new GUI();
    }
    //MainFrame

    //Cryptowährungen

    public GUI() {
        //GUI
        setTitle("Cryptoportfolio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(50, 20, 800, 800);
        getContentPane().setLayout(null);

        //TitleScreen
        //TOP100 Crypto Button
        btop100 = new JButton("Top 100 Cryptos");
        btop100.addActionListener(this);
        btop100.setBounds(36, 20, 150, 50);
        //Portfolio Button
        bportfolio = new JButton("Crypto Portfolio");
        bportfolio.addActionListener(this);
        bportfolio.setBounds(200, 20, 150, 50);
        //AddCrypto Button
        baddcrypto = new JButton("Add Crypto");
        baddcrypto.addActionListener(this);
        baddcrypto.setBounds(500, 700, 150, 50);

        //Top100-Table
        String[] columnNames = {"RANK", "NAME", "PRICE", "MARKETCAP" };
        /*String[][] data = {
                {"1", "btc", "33333", "3333"},
                {"2","eth","4334","3289238932"},
                {"3", "bnb", "1200", "33289329"},
        };*/
        table = new JTable(new DefaultTableModel(null, columnNames));
        table.setDefaultEditor(Object.class, null);
        jsp = new JScrollPane(table);
        jsp.setBounds(36, 87, 730, 600);
        getContentPane().add(jsp);
        table.getColumnModel().getColumn(0).setPreferredWidth(30);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.setFont(new Font("SANS_SERIF", Font.BOLD, 15));
        model = (DefaultTableModel) gui.table.getModel();
        model.addRow(columnNames);

        //Portfolio-Table
        String[] columnNames2 = {"RANK", "NAME", "PRICE", "24H %", "7D %", "30D %", "AMOUNT", "VALUE", "WIN/LOSS"};
        table2 = new JTable(new DefaultTableModel(null, columnNames2));
        table2.setDefaultEditor(Object.class, null);
        jsp2 = new JScrollPane(table2);
        jsp2.setBounds(36, 87, 730, 600);
        getContentPane().add(jsp2);
        table2.getColumnModel().getColumn(0).setPreferredWidth(25);
        table2.getColumnModel().getColumn(1).setPreferredWidth(100);
        table2.getColumnModel().getColumn(2).setPreferredWidth(95);
        table2.getColumnModel().getColumn(3).setPreferredWidth(50);
        table2.getColumnModel().getColumn(4).setPreferredWidth(50);
        table2.getColumnModel().getColumn(5).setPreferredWidth(50);
        table2.getColumnModel().getColumn(6).setPreferredWidth(70);
        table2.getColumnModel().getColumn(7).setPreferredWidth(70);
        table2.setFont(new Font("SANS_SERIF", Font.BOLD, 15));
        model2 = (DefaultTableModel) gui.table2.getModel();
        model2.addRow(columnNames2);

        totalvalue = new JLabel("Total Value: ");
        totalvalue.setBounds(660,690, 200, 50);

        totalinvest = new JLabel("Total Invest");
        totalinvest.setBounds(660, 710, 200, 50);


        //AddCrypto
        lname = new JLabel("Cryptocurrency Name");
        lamount = new JLabel("Amount");
        linvest = new JLabel("Total Invested");
        lname.setBounds(20, 675, 150, 50);
        lamount.setBounds(170, 675, 150, 50);
        linvest.setBounds(320, 675, 150, 50);

        tfname = new JTextField();
        tfamount = new JTextField();
        tfinvest = new JTextField();
        tfname.setBounds(20, 710, 150, 30);
        tfamount.setBounds(170, 710, 150 , 30);
        tfinvest.setBounds(320, 710, 150, 30);

        add(lname);
        add(lamount);
        add(linvest);
        add(tfname);
        add(tfamount);
        add(tfinvest);
        add(totalvalue);
        add(totalinvest);
        add(baddcrypto);
        add(bportfolio);
        add(btop100);

        lname.setVisible(false);
        lamount.setVisible(false);
        linvest.setVisible(false);
        tfname.setVisible(false);
        tfinvest.setVisible(false);
        tfamount.setVisible(false);
        totalinvest.setVisible(false);
        totalvalue.setVisible(false);
        baddcrypto.setVisible(false);
        bportfolio.setVisible(true);
        btop100.setVisible(true);
        jsp.setVisible(false);
        jsp2.setVisible(false);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == btop100) {
            top100();
            table2.setVisible(false);
            jsp2.setVisible(false);
            baddcrypto.setVisible(false);
            lname.setVisible(false);
            lamount.setVisible(false);
            linvest.setVisible(false);
            totalvalue.setVisible(false);
            totalinvest.setVisible(false);
            tfname.setVisible(false);
            tfinvest.setVisible(false);
            tfamount.setVisible(false);
            table.setVisible(true);
            jsp.setVisible(true);

        }
        if(event.getSource() == bportfolio) {
            table.setVisible(false);
            jsp.setVisible(false);
            table2.setVisible(true);
            jsp2.setVisible(true);
            baddcrypto.setVisible(true);
            totalvalue.setVisible(true);
            totalinvest.setVisible(true);
            lname.setVisible(true);
            lamount.setVisible(true);
            linvest.setVisible(true);
            tfname.setVisible(true);
            tfname.setVisible(true);
            tfinvest.setVisible(true);
            tfamount.setVisible(true);
            try {
                portfolio();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if(event.getSource() == baddcrypto) {
            plusCrypto();
            try {
                portfolio();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}