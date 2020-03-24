
import Model.Monom;
import Model.Polynom;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import java.util.ArrayList;

public class Controller {
    private ViewGUI view;
    private Polynom model;
    private String s1, s2;


    public Controller(ViewGUI view, Polynom model) {
        this.view = view;
        this.model = model;
        view.addADDlistener(new AdditionListener());
        view.addSUBlistener(new SubstractionListener());
        view.addMULlistener(new MultiplicationListener());
        view.addDIVlistener(new DivisionListener());
        view.addINTEGlistener(new IntegrationListener());
        view.addDIFFlistener(new DifferentiationListener());

    }

    public void initAll() {
        s1 = new String();
        s2 = new String();

        s1 = view.getFirstPolynom();
        s2 = view.getSecondPolynom();

    }

    public class AdditionListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            initAll();

            if (s1.isEmpty() == false && s2.isEmpty() == false) {
                ArrayList<Monom> m1 = new ArrayList<Monom>();
                Polynom P1 = new Polynom();
                m1 = P1.parseString(s1);
                P1 = new Polynom(m1);

                ArrayList<Monom> m2 = new ArrayList<Monom>();
                Polynom P2 = new Polynom();
                m2 = P2.parseString(s2);
                P2 = new Polynom(m2);

                Polynom res;
                res = P1.add(P2);
                String s = res.toString(res);
                view.printTextField(s);
            } else {
                JOptionPane.showMessageDialog(null, "Insert the 2nd polynomial!");
            }
        }

    }

    public class SubstractionListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            initAll();

            if (s1.isEmpty() == false && s2.isEmpty() == false) {
                ArrayList<Monom> m1 = new ArrayList<Monom>();
                Polynom P1 = new Polynom();
                m1 = P1.parseString(s1);
                P1 = new Polynom(m1);

                ArrayList<Monom> m2 = new ArrayList<Monom>();
                Polynom P2 = new Polynom();
                m2 = P2.parseString(s2);
                P2 = new Polynom(m2);

                Polynom res;
                res = P1.sub(P2);
                String s = res.toString(res);
                view.printTextField(s);
            } else {
                JOptionPane.showMessageDialog(null, "Insert the 2nd polynomial!");
            }
        }

    }

    public class MultiplicationListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            initAll();

            if (s1.isEmpty() == false && s2.isEmpty() == false) {
                ArrayList<Monom> m1 = new ArrayList<Monom>();
                Polynom P1 = new Polynom();
                m1 = P1.parseString(s1);
                P1 = new Polynom(m1);

                ArrayList<Monom> m2 = new ArrayList<Monom>();
                Polynom P2 = new Polynom();
                m2 = P2.parseString(s2);
                P2 = new Polynom(m2);

                Polynom res;
                res = P1.mul(P2);
                String s = res.toString(res);
                view.printTextField(s);
            } else {
                JOptionPane.showMessageDialog(null, "Insert the 2nd polynomial!");
            }
        }

    }

    public class DivisionListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            initAll();

            if (s1.isEmpty() == false && s2.isEmpty() == false) {
                ArrayList<Monom> m1 = new ArrayList<Monom>();
                Polynom P1 = new Polynom();
                m1 = P1.parseString(s1);
                P1 = new Polynom(m1);

                ArrayList<Monom> m2 = new ArrayList<Monom>();
                Polynom P2 = new Polynom();
                m2 = P2.parseString(s2);
                P2 = new Polynom(m2);

                 Polynom res;
                 res = P1.differentiating();
                 String s = res.toString(res);
                //view.printTextField("NOT YET FINISHED");
            } else {
                JOptionPane.showMessageDialog(null, "Insert the 2nd polynomial!");
            }
        }

    }

    public class DifferentiationListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            String text1 = new String();

            text1 = view.getFirstPolynom();

            ArrayList<Monom> m1 = new ArrayList<Monom>();
            Polynom P1 = new Polynom();
            m1 = P1.parseString(text1);
            P1 = new Polynom(m1);

            Polynom res;
            res = P1.differentiating();
            String s = res.toString(res);
            view.printTextField(s);
        }

    }

    public class IntegrationListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            String text1 = new String();

            text1 = view.getFirstPolynom();

            ArrayList<Monom> m1 = new ArrayList<Monom>();
            Polynom P1 = new Polynom();
            m1 = P1.parseString(text1);
            P1 = new Polynom(m1);

            Polynom res;
            res = P1.integration();
            String s = res.toString(res);
            view.printTextField(s);

        }

    }


    public static void main(String[] args) {
        ViewGUI v = new ViewGUI();
        Polynom p = new Polynom();
        Controller c = new Controller(v, p);
    }
}
