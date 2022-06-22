package functions.meta;

import functions.Function;

public class Scale implements Function {

    Function a;
    double x;
    double y;

    public Scale(Function a, double X, double Y) {
        this.a = a;
        this.x = X;
        this.y = Y;
    }

    @Override
    public double getLeftDomainBorder() {
        if (x >= 0) {
            return a.getLeftDomainBorder() * x;
        } else {
            return a.getLeftDomainBorder() / x;
        }
    }

    @Override
    public double getRightDomainBorder() {
        if (x >= 0) {
            return a.getRightDomainBorder() * x;
        } else {
            return a.getRightDomainBorder() / x;
        }
    }

    @Override
    public double getFunctionValue(double x) {
        return a.getFunctionValue(x * this.x) / this.y;
    }

}
