package functions.meta;

import functions.Function;

public class Power implements Function {

    Function a;
    double p;
    double res = 1;

    public Power(Function a, double power) {
        this.a = a;
        this.p = power;
    }

    @Override
    public double getLeftDomainBorder() {
        return a.getLeftDomainBorder();
    }

    @Override
    public double getRightDomainBorder() {
        return a.getRightDomainBorder();
    }

    @Override
    public double getFunctionValue(double x) {
        for (int i = 1; i <= p; i++) {
            res = res * a.getFunctionValue(x);
        }
        return res;
    }
}
