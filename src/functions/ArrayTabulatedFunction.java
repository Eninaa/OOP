package functions;

import java.io.Serializable;


public class ArrayTabulatedFunction implements TabulatedFunction, Serializable, Cloneable {

    private int pointsCount;
    private FunctionPoint points[];

    

    public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount) {
        if (leftX >= rightX || pointsCount < 2) {
            throw new IllegalArgumentException();
        }
        this.pointsCount = pointsCount;
        points = new FunctionPoint[pointsCount];
        double step = (rightX - leftX) / (pointsCount - 1);
        for (int i = 0; i < pointsCount; i++) {
            points[i] = new FunctionPoint(leftX + step * i, 0);
        }
    }

    public ArrayTabulatedFunction(double leftX, double rightX, double[] values) throws InappropriateFunctionPointException {
        this.pointsCount = values.length;
        if (leftX >= rightX || pointsCount < 2) {
            throw new IllegalArgumentException();
        }
        points = new FunctionPoint[pointsCount];
        double step = (rightX - leftX) / (pointsCount - 1);
        for (int i = 0; i < pointsCount; i++) {
            points[i] = new FunctionPoint(leftX + step * i, values[i]);
        }
    }

    public ArrayTabulatedFunction(FunctionPoint[] arr) {
        this.pointsCount = arr.length;
        if (arr.length < 2) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i].getX() >= arr[i + 1].getX()) {
                throw new IllegalArgumentException();
            }
        }

        this.points = new FunctionPoint[arr.length];
        System.arraycopy(arr, 0, this.points, 0, arr.length);
    }

    @Override
    public double getLeftDomainBorder() {
        return points[0].getX();
    }

    @Override
    public double getRightDomainBorder() {
        return points[pointsCount - 1].getX();
    }

    @Override
    public double getFunctionValue(double x) {
        double x1, x2, y1, y2;
        for (int i = 0; i < pointsCount - 1; i++) {
            x1 = points[i].getX();
            x2 = points[i + 1].getX();
            y1 = points[i].getY();
            y2 = points[i + 1].getY();
            if (x >= x1 && x <= x2) {
                return y1 + (y2 - y1) * (x - x1) / (x2 - x1);
            }
        }
        return Double.NaN;
    }

    @Override
    public int getPointsCount() {
        return this.pointsCount;
    }

    @Override
    public FunctionPoint getPoint(int index) {
        if (index < 0 || index > pointsCount - 1) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        return new FunctionPoint(points[index]);
    }

    @Override
    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        if (index < 0 || index > pointsCount - 1) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        if (point.getX() < points[index - 1].getX() && point.getX() > points[index + 1].getX()) {
            throw new InappropriateFunctionPointException();
        }
        this.points[index] = new FunctionPoint(point);

    }

    @Override
    public double getPointX(int index) {
        if (index < 0 || index > pointsCount - 1) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        return this.points[index].getX();
    }

    @Override
    public void setPointX(int index, double x) throws InappropriateFunctionPointException {
        if (index < 0 || index > pointsCount - 1) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        if (x < points[index - 1].getX() && x > points[index + 1].getX()) {
            throw new InappropriateFunctionPointException();
        }
        this.points[index].setX(x);
    }

    @Override
    public double getPointY(int index) {
        if (index < 0 || index > pointsCount - 1) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        return this.points[index].getY();
    }

    @Override
    public void setPointY(int index, double y) {
        if (index < 0 || index > pointsCount - 1) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        this.points[index].setY(y);
    }

    @Override
    public void deletePoint(int index) {   //искл
        if (index < 0 || index > pointsCount - 1) {
            throw new FunctionPointIndexOutOfBoundsException("Index out of border");
        }
        if (pointsCount < 3) {
            throw new IllegalStateException("Count of points less than 3");
        }
        for (int i = index; i < pointsCount - 1; i++) {
            points[i] = points[i + 1];
        }
        pointsCount--;
    }

    @Override
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        for (int i = 0; i < pointsCount - 1; i++) {
            if (point.getX() == points[i].getX()) {
                throw new InappropriateFunctionPointException("The point is already exists");
            }
        }

        if (point.getX() >= getLeftDomainBorder() && point.getX() <= getRightDomainBorder()) {
            for (int i = 0; i < pointsCount - 1; i++) {
                double x1 = points[i].getX();
                double x2 = points[i + 1].getX();
                if (point.getX() >= x1 && point.getX() <= x2) {
                    if (pointsCount == points.length) {
                        FunctionPoint[] temp = new FunctionPoint[pointsCount + 1];
                        System.arraycopy(points, 0, temp, 0, i + 1);            //исх массив индекс массив индекс колво
                        temp[i + 1] = new FunctionPoint(point.getX(), point.getY());
                        System.arraycopy(points, i + 1, temp, i + 2, pointsCount - i - 1);
                        points = temp;
                    } else {
                        for (int k = pointsCount; k > i + 1; k--) {
                            points[k] = points[k - 1];
                        }
                        points[i + 1] = new FunctionPoint(point.getX(), point.getY());

                    }
                    pointsCount++;
                    return;
                }
            }
        } else {
            System.out.println();
        }
    }

//    @Override
//    public void info() {
//        for (int i = 0; i < pointsCount; i++) {
//            System.out.println(i + " POINT:");
//            System.out.println(points[i].getX());
//            System.out.println(points[i].getY());
//        }
//    }

    @Override
    public String toString() {
        String s = "{";
        for (int i = 0; i < pointsCount; i++) {
            s += "(" + points[i].getX() + ";" + " " + points[i].getY() + ")" + "," + " ";
        }

        s += "}";
        return s;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TabulatedFunction)) {
            return false;
        }
        if (o instanceof ArrayTabulatedFunction) {
            ArrayTabulatedFunction arr = (ArrayTabulatedFunction) o;
            if (arr.pointsCount != this.pointsCount) {
                return false;
            }
            for (int i = 0; i < this.pointsCount; i++) {
                if (!(arr.points[i].getX() == this.points[i].getX() && arr.points[i].getY() == this.points[i].getY())) {
                    return false;
                }
            }
            return true;
        } else {
            TabulatedFunction t = ((TabulatedFunction) o);
            if (t.getPointsCount() != this.pointsCount) {
                return false;
            }
            for (int i = 0; i < this.pointsCount; ++i) {
                if (!(t.getPoint(i).getX() == this.points[i].getX() && t.getPoint(i).getY() == this.points[i].getY())) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        int size = this.pointsCount;
        FunctionPoint[] t = new FunctionPoint[size];
        for (int i = 0; i < size; i++) {
            t[i] = (FunctionPoint) this.points[i].clone();
        }
        return new ArrayTabulatedFunction(t);
    }

    @Override
    public int hashCode() {
        int p = 37;
        int r = 1;
        int size = this.pointsCount;
        for (int i = 0; i < size; i++) {
            r += getPoint(i).hashCode();
        }
        return r * p;
    }

   
}
