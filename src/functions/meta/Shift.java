package functions.meta;

import functions.Function;

public class Shift implements Function {

    Function a;
    double x;
    double y;

    public Shift(Function a, double X, double Y) {
        this.a = a;
        this.x = X;
        this.y = Y;
    }

    @Override
    public double getLeftDomainBorder() {
        return a.getLeftDomainBorder() - x;
    }

    @Override
    public double getRightDomainBorder() {
        return a.getRightDomainBorder() + x;
    }

    @Override
    public double getFunctionValue(double x) {
        return a.getFunctionValue(x+this.x) - y;
    }

}
