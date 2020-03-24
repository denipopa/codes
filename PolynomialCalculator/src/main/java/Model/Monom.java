package Model;

import java.util.Objects;


public class Monom {
    private int power;
    private float coefficient;

    public Monom(int power, float coefficient) {
        this.power = power;
        this.coefficient = coefficient;
    }

    int getPower() {
        return this.power;
    }

    void setPower(int x) {
        this.power = x;
    }

    float getCoefficient() {
        return this.coefficient;
    }

    // setter for the coefficient
    void setCoefficient(float x) {
        this.coefficient = x;
    }

    // these two classes are mandatory when working with collections
    @Override
    public boolean equals(Object obj) {

        // checking if both the object references are referring to the same object.
        if (this == obj)
            return true;

        // it checks if the argument is of the type Model.Monom by comparing the classes
        // of the passed argument and this object.
        // if(!(obj instanceof Model.Monom)) return false; ---> avoid.
        if (obj == null || obj.getClass() != this.getClass())
            return false;

        // type casting of the argument.
        Monom m = (Monom) obj;

        // comparing the state of argument with the state of 'this' Object.
        return (m.power == this.power);
    }

    // using a library call to override the hascode method
    @Override
    public int hashCode() {
        return Objects.hashCode(this.power);
    }
    @Override
    public String toString() {
        if (this.coefficient > 0.0) {
            return String.format("+%.2fx^%d", this.coefficient, this.power);
        } else {
            return String.format("%.2fx^%d", this.coefficient, this.power);
        }

    }
}
