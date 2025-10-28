public class Compuestas {
    //Metodo para convertir grados a radianes
    public static double gradosARadianes(double grados){
        return Math.toRadians(grados);
    }
    //Metodo que multiplica dos matrices y regresa la matriz resultande de dicha operacion
    public static double[][] multiplicarMatrices(double[][] mA, double[][] mB){
        int filasA = mA.length;
        int columnasA = mA[0].length;
        int filasB = mB.length;
        int columnasB = mB[0].length;
        if(columnasA != filasB){
            throw new IllegalArgumentException("El numero de columnas de la primera matriz debe ser igual al numero de filas de la segunda matriz");
        }
        double[][] resultado = new double[filasA][columnasB];
        for(int i = 0; i < filasA; i++){
            for(int j = 0; j < columnasB; j++){
                for(int k = 0; k < columnasA; k++){
                    resultado[i][j] += mA[i][k] * mB[k][j];
                }
            }   
        }
        return resultado;
    }
    //Metodo que regresa la matriz de traslacion
    public static double[][] T(double dx, double dy){
        double [][] x = {
            {1, 0, dx},
            {0, 1, dy},
            {0, 0, 1}
        };
        return x;
    }
    //Metodo qeu regresa la matriz de traslacion inversa
    public static double[][] Tinv(double dx, double dy){
        double [][] x = {
            {1, 0, -dx},
            {0, 1, -dy},
            {0, 0, 1}
        };
        return x;
    }

    //Metodo que regresa la matriz de escalacion basada en el origen de las coordenadas
    public static double[][] S(double sx, double sy){
        double [][] x = {
            {sx, 0, 0},
            {0, sy, 0},
            {0, 0, 1}
        };
        return x;
    }
    //Metodo que regresa la matriz de escalacion inversa basada en el origen de las coordenadas
    public static double[][] Sinv(double sx, double sy){
        double [][] x = {
            {1/sx, 0, 0},
            {0, 1/sy, 0},
            {0, 0, 1}

        };
        return x;
    }

}
