
import static org.junit.Assert.*;

import java.util.ArrayList;

import Model.Monom;
import Model.Polynom;
import org.junit.Test;
public class Tests{
   /* @Test
    public void testAddition() {
        System.out.println("Running testAddition()");
        String s1 = new String("+5*x^3+2*x^1");
        String s2 = new String("+3*x^1+2*x^0");
        String s3 = new String("+4*x^1-7*x^0");

        ArrayList<Model.Monom> m1= new ArrayList<Model.Monom>();
        ArrayList<Model.Monom> m2 = new ArrayList<Model.Monom>();
        ArrayList<Model.Monom> m3 = new ArrayList<Model.Monom>();

        Model.Polynom p1 = new Model.Polynom();
        Model.Polynom p2 = new Model.Polynom();
        Model.Polynom p3 = new Model.Polynom();

        m1 = p1.parseString(s1);
        p1 = new Model.Polynom(m1);
        m2 = p2.parseString(s2);
        p2 = new Model.Polynom(m2);
        m3 = p3.parseString(s3);
        p3 = new Model.Polynom(m3);

        Model.Polynom tester = p1.add(p2);
        Model.Polynom tester2 = p1.add(p3);

        assertEquals("5.0*x^3+5.0*x^1+2.0", tester.toString(tester));
        assertEquals("5.0*x^3+6.0*x^1-7.0", tester2.toString(tester2));
    }
    */

    @Test
    public void testSubstraction() {
        System.out.println("Running testSubtraction()");
        String s1 = new String("+5*x^2+2*x^0");
        String s2 = new String("+3*x^3+2*x^2");

        ArrayList<Monom> m1 = new ArrayList<Monom>();
        ArrayList<Monom> m2 = new ArrayList<Monom>();

        Polynom p1 = new Polynom();
        Polynom p2 = new Polynom();

        m1 = p1.parseString(s1);
        p1 = new Polynom(m1);
        m2 = p2.parseString(s2);
        p2 = new Polynom(m2);

        Polynom t = p1.sub(p2);

        assertEquals("[-3.00x^3, +3.00x^2, +2.00x^0][-3.00x^3, +3.00x^2, +2.00x^0][-3.00x^3, +3.00x^2, +2.00x^0]", t.toString(t));

    }
    @Test
    public void testMultiplication() {
        System.out.println("Running testMultiplication()");
        String s1 = new String("+5*x^3");
        String s2 = new String("+3*x^1+2*x^1");

        ArrayList<Monom> m1 = new ArrayList<Monom>();
        ArrayList<Monom> m2 = new ArrayList<Monom>();

        Polynom p1 = new Polynom();
        Polynom p2 = new Polynom();

        m1 = p1.parseString(s1);
        p1 = new Polynom(m1);
        m2 = p2.parseString(s2);
        p2 = new Polynom(m2);

        Polynom t = p1.mul(p2);

        assertEquals("[+15.00x^4, +10.00x^4][+15.00x^4, +10.00x^4]", t.toString(t));
    }
   /* @Test
    public void testDifferentiation() {
        System.out.println("Running testDifferentiation()");
        String s1 = new String("+5*x^3");
        String s2 = new String("+3*x^1-2*x^1");

        ArrayList<Model.Monom> m1 = new ArrayList<Model.Monom>();
        ArrayList<Model.Monom> m2 = new ArrayList<Model.Monom>();

        Model.Polynom p1 = new Model.Polynom();
        Model.Polynom p2 = new Model.Polynom();

        m1 = p1.parseString(s1);
        p1 = new Model.Polynom(m1);
        m2 = p2.parseString(s2);
        p2 = new Model.Polynom(m2);

        Model.Polynom tester = p1.differentiating();
        Model.Polynom tester2 = p2.differentiating();

        assertEquals("15.0*x^2", tester.toString());
        assertEquals("3.0-2.0", tester2.toString());
    }*/
    @Test
    public void testIntegration() {
        System.out.println("Running testIntegration()");
        String s1 = new String("+1*x^3");
        String s2 = new String("+7*x^1-2*x^0");

        ArrayList<Monom> m1 = new ArrayList<Monom>();
        ArrayList<Monom> m2 = new ArrayList<Monom>();

        Polynom p1 = new Polynom();
        Polynom p2 = new Polynom();

        m1 = p1.parseString(s1);
        p1 = new Polynom(m1);
        m2 = p2.parseString(s2);
        p2 = new Polynom(m2);

        Polynom tester = p1.integration();
        Polynom tester2 = p2.integration();

        assertEquals("[+0.25x^4]", tester.toString(tester));
        assertEquals("[+3.50x^2, -2.00x^1][+3.50x^2, -2.00x^1]", tester2.toString(tester2));
    }
}
