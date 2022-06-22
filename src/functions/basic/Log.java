package functions.basic;

import functions.Function;

public class Log implements Function {

    double b;

    public Log() {
        this.b = Math.E;
    }

    public Log(double b) {

        if (b > 0 && b != 1) {
            this.b = b;
        }
    }

    @Override
    public double getLeftDomainBorder() {
        return 0;
    }

    @Override
    public double getRightDomainBorder() {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public double getFunctionValue(double x) {
        return Math.log(x) / Math.log(b);
    }
}
