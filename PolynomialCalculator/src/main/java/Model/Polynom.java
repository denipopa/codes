package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Polynom {

    private List<Monom> monoms = new ArrayList<Monom>();
    private int degree;

    public Polynom() {
    }


    public Polynom(ArrayList<Monom> array) {
        int maxPower = 0;
        // for each value of array
        for (Monom val : array) {
            if (val.getPower() > maxPower)
                maxPower = val.getPower();
        }
        this.degree = maxPower;
        this.monoms = array;
    }

    public List<Monom> getMonoms() {
        return monoms;
    }

    public int getDegree() {
        return this.degree;
    }

    public Monom maximPower() {
        int max = this.getMonoms().get(0).getPower(); //max taken as first monom
        Monom aux ;
        aux = this.getMonoms().get(0);
        for (Monom i : this.getMonoms()) {
            if (i.getPower() > max) {
                max = i.getPower();
                aux = i;
            }
        }
        return aux;
    }


    public ArrayList<Monom> parseString(String s) {
       /* ArrayList<Monom> m = new ArrayList<Monom>();

        String[] words = s.split(String.valueOf(delim)); // after split put the words here
       */ ArrayList<Monom> m = new ArrayList<Monom>();
       Pattern delim1 = Pattern.compile("([+-]?[^-+]+)");
        String delim = "(?=[\\*^+])|(?=[-])"; // splits the characters and keeps the sign
        String[] words = s.split(delim);
        int neg;
        for (int i = 0; i < words.length - 2; i = i + 3) {//use i+3 to not have repeated values
            Monom monom;
            if (words[i].charAt(0) == '-') {
                neg = Character.getNumericValue(words[i].charAt(1));
                neg = 0 - neg;
                monom = new Monom(Character.getNumericValue(words[i + 2].charAt(1)), (float) neg);
            } else
                monom = new Monom(Character.getNumericValue(words[i + 2].charAt(1)),
                        (float) Character.getNumericValue(words[i].charAt(1)));
            m.add(monom);
        }
        return m;
    }


    public Polynom add(Polynom p1) {

        ArrayList<Monom> result = new ArrayList<>();
        int pow1;
        int c = 1;

        if (this.getDegree() >= p1.getDegree())
            pow1 = this.getDegree();
        else
            pow1= p1.getDegree();

        for (int i = pow1; i >= 0; i--) {
            for (Monom m1 : this.getMonoms())
                if (m1.getPower() == i) {
                    for (Monom m2 : p1.monoms) {
                        if (m2.getPower() == i) {// if both have exponent i
                            result.add(new Monom(m1.getPower(), m1.getCoefficient() + m1.getCoefficient()));
                            c = 0;
                        }
                    }
                    if (c != 0) {// exponent of second monom was not i=> add first monom
                        result.add(new Monom(m1.getPower(), m1.getCoefficient()));
                        c = 0;
                    }
                }

            if (c != 0)// add second monom since nothing was added
                for (Monom m2 : p1.monoms)
                    if (m2.getPower() == i)
                        result.add(new Monom(m2.getPower(), m2.getCoefficient()));
            c = 1;
        }
        Polynom P = new Polynom(result);
        return P;
    }

    public Polynom sub(Polynom p1) {
        ArrayList<Monom> result = new ArrayList<Monom>();
        int pow1;
        int c = 1;
        if (this.getDegree() >= p1.getDegree())
           pow1 = this.getDegree();
        else
           pow1 = p1.getDegree();

        for (int i = pow1; i >= 0; i--) {
            for (Monom m1 : this.getMonoms())
                if (m1.getPower() == i) {
                    for (Monom m2 : p1.monoms)
                        if (m2.getPower() == i) {// if both monoms have the same power
                            result.add(new Monom(m1.getPower(), m1.getCoefficient() - m2.getCoefficient()));
                            c = 0;
                        }

                    if (c != 0) {// exponent of b was not i=> add a
                        result.add(new Monom(m1.getPower(), m1.getCoefficient()));
                        c = 0;
                    }
                }
            if (c != 0)// add b since nothing was added
                for (Monom b : p1.monoms)
                    if (b.getPower() == i)
                        result.add(new Monom(b.getPower(), 0 - b.getCoefficient()));// change the sign of the second
            // polynomial
            c = 1;
        }
        Polynom P = new Polynom(result);
        return P;
    }

    public Polynom mul(Polynom p1) {
        ArrayList<Monom> result = new ArrayList<Monom>();
        for (Monom m1 : this.getMonoms())
            for (Monom m2 : p1.monoms) {//multiply each coefficient and add each power
                result.add(new Monom(m1.getPower()   + m2.getPower(), m1.getCoefficient() * m2.getCoefficient()));
            }
        Polynom P = new Polynom(result);
        return P;
    }

    public static Polynom div(Polynom p1, Polynom p2) {
        Polynom result = new Polynom();
        for(int i=0; i<p1.getMonoms().size(); i++)
        {
            for(int j=0; j<p2.getMonoms().size(); j++)
            {
                Integer pow1 = p1.getMonoms().get(i).getPower();
                Integer pow2 = p2.getMonoms().get(j).getPower();
                Float c1 = p1.getMonoms().get(i).getCoefficient();
                Float c2 = p2.getMonoms().get(j).getCoefficient();
                Integer pow = pow1 - pow2;
                float coeff = c1 / c2;
                Monom m = new Monom( pow,coeff);
                result.getMonoms().add(m);
            }
        }
        return result;
    }

    public Polynom differentiating() {
        ArrayList<Monom> result = new ArrayList<Monom>();
        for (Monom m : this.getMonoms()) {
            if (m.getPower() == 0) {
                result.add(new Monom(0, 0));
            } else {
                result.add(new Monom(m.getPower() - 1, m.getCoefficient() * m.getPower()));
            }
        }
        Polynom P = new Polynom(result);
        return P;
    }

    public Polynom integration() {
        ArrayList<Monom> result = new ArrayList<Monom>();

        for (Monom a : this.getMonoms()) {
            result.add(new Monom(a.getPower() + 1, a.getCoefficient() * (1.0f / (a.getPower() + 1))));
        }
        Polynom P = new Polynom(result);
        return P;
    }

    public String toString(Polynom p) {

        String s = "";
        for (Monom a : p.getMonoms()) {

            s = s + this.monoms;
        }
        return s;
    }
}
