package functions.meta;

import functions.Function;

public class Sum implements Function {

    Function a;
    Function b;

    public Sum(Function a, Function b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public double getLeftDomainBorder() {
        if (a.getLeftDomainBorder() > b.getLeftDomainBorder()) {
            return a.getLeftDomainBorder();
        } else {
            return b.getLeftDomainBorder();
        }
    }

    @Override
    public double getRightDomainBorder() {
        if (a.getRightDomainBorder() < b.getRightDomainBorder()) {
            return a.getRightDomainBorder();
        } else {
            return b.getRightDomainBorder();
        }
    }

    @Override
    public double getFunctionValue(double x) {
        return a.getFunctionValue(x) + b.getFunctionValue(x);
    }
}
