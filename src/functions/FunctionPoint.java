package functions;

import java.io.Serializable;

public class FunctionPoint implements Serializable, Cloneable {

    private double x;
    private double y;

    public FunctionPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public FunctionPoint(FunctionPoint point) {
        this.x = point.x;
        this.y = point.y;
    }

    public FunctionPoint() {
        this.x = 0;
        this.y = 0;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + "; " + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        FunctionPoint point = (FunctionPoint) o;
        return this.getClass() == point.getClass() && point.getX() == x && point.getY() == y;
    }

    @Override
    public int hashCode() {
        int p = 37;
        int r = 1;
        long t;
        t = Double.doubleToLongBits(x);
        r = p * r + (int) (t ^ (t >>> 32));
        t = Double.doubleToLongBits(y);
        r = p * r + (int) (t ^ (t >>> 32));
        return r;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void info() {
        System.out.println("point: ");
        System.out.println(getX());
        System.out.println(getY());
    }
}
