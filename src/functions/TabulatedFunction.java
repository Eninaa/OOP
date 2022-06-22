package functions;

import java.io.Serializable;

public interface TabulatedFunction extends Function, Serializable, Cloneable {

    public Object clone() throws CloneNotSupportedException;

    int getPointsCount();

    FunctionPoint getPoint(int index);

    void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException;

    double getPointX(int index);

    void setPointX(int index, double x) throws InappropriateFunctionPointException;

    double getPointY(int index);

    void setPointY(int index, double y);

    void deletePoint(int index) throws IllegalStateException;

    void addPoint(FunctionPoint point) throws InappropriateFunctionPointException;

}
