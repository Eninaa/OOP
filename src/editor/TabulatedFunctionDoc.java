package editor;

import functions.*;
import java.io.*;
import java.util.Objects;

public class TabulatedFunctionDoc implements TabulatedFunction {

    private TabulatedFunction function;
    private String fileName = "";
    private boolean modified = false;
    private boolean fileNameAssigned = false;

    public void newFunction(double leftX, double rightX, int pointsCount) {
        function = new ArrayTabulatedFunction(leftX, rightX, pointsCount);
        modified = false;
    }

    public void saveFunction() throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            TabulatedFunctions.writeTabulatedFunction(function, writer); 
            writer.close();
        }
      modified = false;
    }

    public void saveFunctionAs(String fileName) throws IOException {
        this.fileName = fileName;
        fileNameAssigned = true;
        saveFunction();
        modified = false;
    }

    public void loadFunction(String fileName) throws FileNotFoundException, IOException {
        this.fileName = fileName;
        fileNameAssigned = true;
        FileReader reader = new FileReader(fileName);
        function = TabulatedFunctions.readTabulatedFunction(reader);
        modified = false;
    }

    public void tabulateFunction(Function function, double leftX, double rightX, int pointsCount) {
        this.function = TabulatedFunctions.tabulate(function, leftX, rightX, pointsCount);
        modified = false;
    }

    public boolean isModified() {
        return modified;
    }

    public boolean isFileNameAssigned() {
        return fileNameAssigned;
    }

    @Override
    public int getPointsCount() {
        return this.function.getPointsCount();
    }

    @Override
    public FunctionPoint getPoint(int index) {
        if (index < 0 || index > this.getPointsCount() - 1) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        return new FunctionPoint(this.function.getPoint(index));
    }

    @Override
    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        this.function.setPoint(index, point);
        this.modified = true;
    }

    @Override
    public double getPointX(int index) {
        return this.function.getPointX(index);
    }

    @Override
    public void setPointX(int index, double x) throws InappropriateFunctionPointException {
        this.function.setPointX(index, x);
        this.modified = true;
    }

    @Override
    public double getPointY(int index) {
        return this.function.getPointY(index);
    }

    @Override
    public void setPointY(int index, double y) {
        this.function.setPointY(index, y);
        this.modified = true;
    }

    @Override
    public void deletePoint(int index) throws IllegalStateException {
        this.function.deletePoint(index);
        this.modified = true;

    }

    @Override
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        this.function.addPoint(point);
        this.modified = true;
    }

    @Override
    public double getLeftDomainBorder() {
        return this.function.getLeftDomainBorder();
    }

    @Override
    public double getRightDomainBorder() {
        return this.function.getRightDomainBorder();
    }

    @Override
    public double getFunctionValue(double x) {
        return this.function.getFunctionValue(x);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        TabulatedFunctionDoc clone = new TabulatedFunctionDoc();
        clone.function = (TabulatedFunction) function.clone();
        clone.fileName = fileName;
        clone.modified = modified;
        clone.fileNameAssigned = fileNameAssigned;
        return clone;
    }

    @Override
    public String toString() {
        this.function.toString();
        String s = "{";
        for (int i = 0; i < this.function.getPointsCount(); i++) {
            s += "(" + this.function.getPointX(i) + ";" + " " + this.function.getPointY(i) + ")" + "," + " ";
        }
        s += "}";
        return s;

    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.function);
        return hash;
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof TabulatedFunction) {

            TabulatedFunction f = (TabulatedFunction) o;
            if (f.getPointsCount() != this.getPointsCount()) {
                return false;
            }
            for (int i = 0; i < this.getPointsCount(); i++) {
                if (!(this.getPointX(i) == f.getPointX(i) && this.getPointY(i) == f.getPointY(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }

    }
}
