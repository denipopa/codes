import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ViewGUI {

    JFrame frame;
    private JTextField polynom1, polynom2, res, rem;
    private JButton add, sub, mul, div, integ, diff;

    ViewGUI() {
        frame = new JFrame("Polynomial Calculator");

        // creates instance of JButton
        add = new JButton("Addition");
        sub = new JButton("Subtraction");
        mul = new JButton("Multiplication");
        div = new JButton("Division");
        integ = new JButton("Integration");
        diff = new JButton("Differentiation");

        // x axis, y axis, width, height
        add.setBounds(400, 100, 120, 30);
        sub.setBounds(400, 150, 120, 30);
        mul.setBounds(400, 200, 120, 30);
        div.setBounds(400, 250, 120, 30);
        integ.setBounds(400, 300, 120, 30);
        diff.setBounds(400, 350, 120, 30);

        // adds button in JFrame
        frame.add(add);
        frame.add(sub);
        frame.add(mul);
        frame.add(div);
        frame.add(integ);
        frame.add(diff);

        // edit button colors
        add.setBackground(Color.DARK_GRAY);
        add.setForeground(Color.GRAY);
        sub.setBackground(Color.DARK_GRAY);
        sub.setForeground(Color.GRAY);
        mul.setBackground(Color.DARK_GRAY);
        mul.setForeground(Color.GRAY);
        div.setBackground(Color.DARK_GRAY);
        div.setForeground(Color.GRAY);
        integ.setBackground(Color.DARK_GRAY);
        integ.setForeground(Color.GRAY);
        diff.setBackground(Color.DARK_GRAY);
        diff.setForeground(Color.GRAY);

        // creates instances of JLabel
        JLabel sel = new JLabel("Select your operation:");
        JLabel p1 = new JLabel("Give the first polynomial:");
        JLabel p2 = new JLabel("Give the second polynomial:");
        JLabel ex1 = new JLabel("Format: +a*x^b");
        JLabel ex2 = new JLabel("Format: +a*x^b");
        JLabel result = new JLabel("The result is: ");
        JLabel remainder = new JLabel("Remainder: ");
        JLabel help = new JLabel("*If the exponent or coefficient are 0 or 1 please specify in the input.");
        JLabel help1 = new JLabel("**Give the sign of the first coefficient, even if it is positive.");

        // x axis, y axis, width, height
        sel.setBounds(390, 50, 250, 30);
        p1.setBounds(50, 50, 250, 30);
        p2.setBounds(50, 175, 250, 30);
        ex1.setBounds(50, 75, 250, 30);
        ex2.setBounds(50, 200, 250, 30);
        result.setBounds(50, 300, 100, 30);
        remainder.setBounds(50, 375, 250, 30);
        help.setBounds(10, 500, 550, 30);
        help1.setBounds(10, 520, 550, 30);

        // adds label in JFrame
        frame.add(sel);
        frame.add(p1);
        frame.add(p2);
        frame.add(ex1);
        frame.add(ex2);
        frame.add(result);
        frame.add(remainder);
        frame.add(help);
        frame.add(help1);

        // create text fields
        polynom1 = new JTextField();
        polynom2 = new JTextField();
        res = new JTextField();
        rem = new JTextField();

        // x axis, y axis, width, height
        polynom1.setBounds(50, 100, 300, 30);
        polynom2.setBounds(50, 225, 300, 30);
        res.setBounds(50, 325, 300, 30);
        rem.setBounds(50, 400, 300, 30);

        // adds label in JFrame
        frame.add(polynom1);
        frame.add(polynom2);
        frame.add(res);
        frame.add(rem);

        frame.setSize(600, 600);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.WHITE);

    }

    public static void main(String[] args) {
        new ViewGUI();

    }

    // return the String form of the first polynomial
    public String getFirstPolynom() {
        return polynom1.getText();
    }

    public String getSecondPolynom() {
        return polynom2.getText();
    }


    public void addADDlistener(ActionListener addition) {
        add.addActionListener(addition);
    }

    public void addSUBlistener(ActionListener substraction) {
        sub.addActionListener(substraction);
    }

    public void addMULlistener(ActionListener multiplication) {
        mul.addActionListener(multiplication);
    }

    public void addDIVlistener(ActionListener division) {
        div.addActionListener(division);
    }

    public void addINTEGlistener(ActionListener integration) {
        integ.addActionListener(integration);
    }

    public void addDIFFlistener(ActionListener differentiate) {
        diff.addActionListener(differentiate);
    }

    public void printTextField(String s) {
        res.setText(s);
    }

    public void printTextFieldRemainder(String s) {
        rem.setText(s);
    }
}
