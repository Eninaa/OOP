package functions;

import java.io.*;

public class LinkedListTabulatedFunction implements TabulatedFunction, Serializable, Cloneable {

    private class FunctionNode {

        private FunctionPoint val;
        private FunctionNode next;
        private FunctionNode prev;

        public FunctionNode() {
            this.val = new FunctionPoint();
            this.next = null;
            this.prev = null;
        }

        public FunctionNode(FunctionNode node) {
            this.val = node.val;
            this.next = node.next;
            this.prev = node.prev;
        }

        public FunctionNode(FunctionPoint point, FunctionNode next1, FunctionNode prev1) {
            this.val = point;
            this.next = next1;
            this.prev = prev1;
        }

        public FunctionNode(double x, double y, FunctionNode next1, FunctionNode prev1) {
            this.val.setX(x);
            this.val.setY(y);
            this.next = next1;
            this.prev = prev1;
        }

        public FunctionNode(FunctionPoint a) {
            this.val = a;

        }

    }
    private final FunctionNode head;
    private int size;

    public LinkedListTabulatedFunction(double leftX, double rightX, int pointsCount) throws InappropriateFunctionPointException {
        this.head = new FunctionNode();
        head.next = head.prev = head;
        if (leftX >= rightX || pointsCount < 2) {
            throw new IllegalArgumentException();
        }
        double step = (rightX - leftX) / (pointsCount - 1);
        for (int i = 0; i < pointsCount; i++) {
            FunctionPoint point = new FunctionPoint(leftX + step * i, 0);
            addNodeByIndex(i).val = point;
        }
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, double[] values) throws InappropriateFunctionPointException {
        this.head = new FunctionNode();
        head.next = head.prev = head;
        if (leftX >= rightX || values.length < 2) {
            throw new IllegalArgumentException();
        }
        double step = (rightX - leftX) / (values.length - 1);
        for (int i = 0; i < values.length; i++) {
            FunctionPoint point = new FunctionPoint(leftX + step * i, values[i]);
            addNodeByIndex(i).val = point;
        }
    }

    public LinkedListTabulatedFunction(FunctionPoint[] arr) {
        if (arr.length < 2) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i].getX() >= arr[i + 1].getX()) {
                throw new IllegalArgumentException();
            }
        }
        this.head = new FunctionNode();
        head.next = head.prev = head;
        for (int i = 0; i < arr.length; i++) {
            addNodeByIndex(i).val = arr[i];
        }
    }

    private FunctionNode getNodeByIndex(int index) {
        FunctionNode temp = head;
        for (int i = 0; i <= index; i++) {
            temp = temp.next;
        }
        return temp;
    }

    private FunctionNode addNodeToTail(FunctionNode node) {
        FunctionNode a = head.prev;
        a.next = node;
        head.prev = node;
        node.prev = a;
        node.next = head;
        this.size++;
        return node;
    }

    private FunctionNode addNodeByIndex(int index) {
        FunctionNode a = getNodeByIndex(index);
        FunctionNode b = a.prev;
        FunctionNode x = new FunctionNode();
        x.prev = b;
        x.next = a;
        a.prev = x;
        b.next = x;
        this.size++;
        return x;
    }

    private FunctionNode deleteNodeByIndex(int index) {
        FunctionNode node = getNodeByIndex(index);
        FunctionNode nodePrev = node.prev;
        FunctionNode nodeNext = node.next;
        nodePrev.next = nodeNext;
        nodeNext.prev = nodePrev;
        this.size--;
        return node;
    }

    @Override
    public double getLeftDomainBorder() {
        return this.head.next.val.getX();
    }

    @Override
    public double getRightDomainBorder() {
        return this.head.prev.val.getX();
    }

    @Override
    public double getFunctionValue(double x) {
        if (getLeftDomainBorder() > x || getRightDomainBorder() < x) {
            return Double.NaN;
        }
        FunctionNode node = head;
        while (node.val.getX() < x) {
            node = node.next;
        }
        if (node.val.getX() == x) {
            return node.val.getY();
        } else {
            double x1 = node.val.getX();
            double x2 = node.next.val.getX();
            double y1 = node.val.getY();
            double y2 = node.next.val.getY();
            return (((x - x1) * (y2 - y1)) / (x2 - x1)) + y1;
        }
    }

    @Override
    public int getPointsCount() {
        return this.size;
    }

    @Override
    public FunctionPoint getPoint(int index) {
        if (index < 0 || index > size - 1) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        return getNodeByIndex(index).val;
    }

    @Override
    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        if (index < 0 || index > size - 1) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        if (point.getX() < getNodeByIndex(index - 1).val.getX() && point.getX() > getNodeByIndex(index + 1).val.getX()) {
            throw new InappropriateFunctionPointException();
        }
        getNodeByIndex(index).val = point;
    }

    @Override
    public double getPointX(int index) {
        if (index < 0 || index > size - 1) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        return getPoint(index).getX();
    }

    @Override
    public void setPointX(int index, double x) throws InappropriateFunctionPointException {
        if (index < 0 || index > size - 1) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        if (x < getNodeByIndex(index - 1).val.getX() && x > getNodeByIndex(index + 1).val.getX()) {
            throw new InappropriateFunctionPointException();
        }
        getNodeByIndex(index).val.setX(x);
    }

    @Override
    public double getPointY(int index) {
        if (index < 0 || index > size - 1) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        return getPoint(index).getY();
    }

    @Override
    public void setPointY(int index, double y) {
        if (index < 0 || index > size - 1) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        getNodeByIndex(index).val.setY(y);
    }

    @Override
    @SuppressWarnings("empty-statement")
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        FunctionNode node2 = new FunctionNode();
        node2.val = point;
        for (int i = 0; i < size; i++) {
            if (point.getX() == getNodeByIndex(i).val.getX()) {
                throw new InappropriateFunctionPointException();
            }
        }

        if (point.getX() < getLeftDomainBorder()) {
            addNodeByIndex(0).val = point;
            return;

        }
        if (point.getX() > getRightDomainBorder()) {
            addNodeToTail(node2).val = point;
            return;
        }
        int i = 0;
        for (; point.getX() >= getNodeByIndex(i).val.getX(); i++);
        addNodeByIndex(i).val = point;

    }

    @Override
    public void deletePoint(int index) {
        if (index < 0 || index > size - 1) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        if (size < 3) {
            throw new IllegalStateException();
        }
        deleteNodeByIndex(index);
    }

    @Override
    public String toString() {
        String s = "{";
        for (int i = 0; i < size; i++) {
            s += "(" + getNodeByIndex(i).val.getX() + ";" + " " + getNodeByIndex(i).val.getY() + ")" + "," + " ";
        }

        s += "}";
        return s;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TabulatedFunction)) {
            return false;
        }
        if (obj instanceof LinkedListTabulatedFunction) {
            LinkedListTabulatedFunction temp = (LinkedListTabulatedFunction) obj;
            if (temp.getPointsCount() != this.size) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (!(temp.getNodeByIndex(i).val.getX() == this.getNodeByIndex(i).val.getX() && temp.getNodeByIndex(i).val.getY() == this.getNodeByIndex(i).val.getY())) {
                    return false;
                }
            }
            return true;
        } else {
            TabulatedFunction t = ((TabulatedFunction) obj);
            if (t.getPointsCount() != this.size) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (!(t.getPoint(i).getX() == this.getNodeByIndex(i).val.getX() && t.getPoint(i).getY() == this.getNodeByIndex(i).val.getY())) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int p = 37;
        int r = 1;
        for (int i = 0; i < size; i++) {
            r += getPoint(i).hashCode();
        }
        return r * p;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        FunctionPoint[] t = new FunctionPoint[this.size];
        for (int i = 0; i < this.size; i++) {
            t[i] = (FunctionPoint) this.getPoint(i).clone();
        }
        return new LinkedListTabulatedFunction(t);
    }

//    @Override
//    public void info() {
//        for (int i = 0; i < size; i++) {
//            System.out.println(i + " POINT:");
//            System.out.println(getNodeByIndex(i).val.getX());
//            System.out.println(getNodeByIndex(i).val.getY());
//        }
//    }

    
}
