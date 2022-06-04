package ru.ket.EP05.T4;

public class Matrix {
    private final int[][] values;

    public Matrix(int[][] values){
        this.values = values;
    }

    public int[][] multiply(int v){
        int size = values.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                values[i][j] *= v;
            }
        }
        return values;
    }

    public int[][] getValues() {
        return values;
    }
}
