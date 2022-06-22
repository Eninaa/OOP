package functions;

import java.io.*;

public abstract class TabulatedFunctions {

    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount) {
        if (leftX < function.getLeftDomainBorder() || rightX > function.getRightDomainBorder()) {
            throw new IllegalArgumentException();
        }
        FunctionPoint[] arr = new FunctionPoint[pointsCount];
        double step = (rightX - leftX) / (pointsCount - 1);
        for (int i = 0; i < pointsCount; i++) {
            arr[i] = new FunctionPoint(leftX + i * step, function.getFunctionValue(leftX + i * step));
        }
        return new ArrayTabulatedFunction(arr);
    }

    public static void outputTabulatedFunction(TabulatedFunction function, OutputStream out) throws IOException {

        try (DataOutputStream Out = new DataOutputStream(out)) {
            Out.writeInt(function.getPointsCount());
            for (int i = 0; i < function.getPointsCount(); i++) {
                Out.writeDouble(function.getPointX(i));
                Out.writeDouble(function.getPointY(i));
            }
        }
    }

    public static TabulatedFunction inputTabulatedFunction(InputStream in) throws IOException {
        FunctionPoint[] point;
        try (DataInputStream In = new DataInputStream(in)) {
            int n = In.readInt();
            point = new FunctionPoint[n];
            for (int i = 0; i < n; i++) {
                point[i] = new FunctionPoint(In.readDouble(), In.readDouble());
            }
        }
        return new ArrayTabulatedFunction(point);
    }

    public static void writeTabulatedFunction(TabulatedFunction function, Writer out) throws IOException {
        try (BufferedWriter Out = new BufferedWriter(out)) {
            Out.write(function.getPointsCount() + " ");
            for (int i = 0; i < function.getPointsCount(); i++) {
                Out.write(function.getPointX(i) + " ");
                Out.write(function.getPointY(i) + " ");
            }
        }
    }

    public static TabulatedFunction readTabulatedFunction(Reader in) throws IOException {
        StreamTokenizer st = new StreamTokenizer(in);
        st.nextToken();
        int n = (int) st.nval;
        FunctionPoint[] point = new FunctionPoint[n];
        double x, y;
        for (int i = 0; i < n; i++) {
            st.nextToken();
            x = st.nval;
            st.nextToken();
            y = st.nval;
            point[i] = new FunctionPoint(x, y);
        }

        in.close();

        return new ArrayTabulatedFunction(point);
    }

}
