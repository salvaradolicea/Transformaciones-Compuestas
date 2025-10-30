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
    //Metodo que regresa la matriz de rotacion basada en el origen de las coordenadas
    public static double[][] R(double angulo){
        double radianes = gradosARadianes(angulo);//Convertir grados a radianes
        double cos = Math.cos(radianes);//Calcular coseno y seno
        double sin = Math.sin(radianes);

        double [][] x = {
            {cos, - sin, 0},
            {sin, cos, 0},
            {0, 0, 1}
        };
        return x;
    }

    //Metodo que regresa la matriz de rotacion basada en un punto de rotacion
    public static double[][] R(double angulo, double px, double py){
        double radianes = gradosARadianes(angulo);
        double cos = Math.cos(radianes);
        double sin = Math.sin(radianes);

        double [][] x = {
            {cos, - sin, px * (1 - cos) + py * sin},
            {sin, cos, py * (1 - cos) - px * sin},
            {0, 0, 1}
        };
        return x;
    }

    //Metodo que regresa la matriz de rotacion inversa basada en el origen de las coordenadas
    public static double[][] Rinv(double angulo){
        double radianes = gradosARadianes(angulo);
        double cos = Math.cos(radianes);
        double sin = Math.sin(radianes);

        double [][] x = {
            {cos, sin, 0},
            {-sin, cos, 0},
            {0, 0, 1}
        };
        return x;
    }

    //metodo que recibe los arreglos que contienen las coordenadas de los puntos
    // y regresa la matriz de puntos homogenea
    public static  double [][] matrizPuntos(double x[], double y[]){
        //Verificar que los arreglos tengan la misma longitud
        if(x.length != y.length){
            throw new IllegalArgumentException("Los arreglos deben tener la misma longitud");
        }
        int n = x.length;
        double [][] puntos = new double[3][n];
        for(int i = 0; i < n; i++){
            puntos[0][i] = x[i];
            puntos[1][i] = y[i];
            puntos[2][i] = 1;
        }   
        return puntos;

    }

    //Metodo que aplica una matriz de transformacion a una matriz de puntos
    public double [][] transformaPuntos(double [][]m, double [][] p){
        return multiplicarMatrices(m, p);
    }

    //Metodo qeu recibe la matriz de puntos homogenea y el renglon de las 
    //coordenadas que se desea obtener (0 para x, 1 para y)
    //Regrega un arreglo con las coordenadas bien sea de x
    //o de y y el cual se utlizara para graficar
    public int[] actualizarPuntos(double[][] puntos, int coord){
        int n = puntos[0].length;
        int[] resultado = new int[n];
        for(int i = 0; i < n; i++){
            resultado[i] = (int)Math.round(puntos[coord][i]);
        }
        return resultado;
    }

    //Metodos terminados
    

    //Metodos necesarios para hacer la reflexion del objeto
    //en sus distintos enfoques, asi como la inclinacion
    //(Corte, recorte o sesgo) de un objeto bidimensional
    
    //Reflexion respecto al eje x
    //invierte los valores de Y
    public double [][] refX(){
        double [][] x = {
            {1, 0, 0},
            {0, -1, 0},
            {0, 0, 1}
        };
        return x;
    }

    //reflexion respecto al eje y
    //invierte los valores de x
    public double [][] refY(){
        double [][] x = {
            {-1, 0, 0},
            {0, 1, 0},
            {0, 0, 1}
        };
        return x;
    }

    //reflexion respecto al origen de coordenadas
    //Refleja ambos valores
    public double [][] refOrigen(){
        double [][] x = {
            {-1, 0, 0},
            {0, -1, 0},
            {0, 0, 1}
        };
        return x;
    }   

    //Reflejar respecto a la recta y = x
    //Intercambia los valores de x y y
    public double [][] refYigualX(){
        double [][] x = {
            {0, 1, 0},
            {1, 0, 0},
            {0, 0, 1}
        };
        return x;
    }   

    //rEFLEJAR RESPECTO A LA RECTA Y = -X
    //Intecambia los valores de x y y, ademas de cambiar el signo
    public double [][] refYIgualMenosX(){
        double [][] x = {
            {0, -1, 0},
            {-1, 0, 0},
            {0, 0, 1}
        };
        return x;   
    }

    //METODOS DE INCLINACION

    //Inclinacion respecto al eje x
    //iNclina el objeto horizontalmente, es decir sobre el eje x
    public static double [][] CX(double SHx){
        double [][] x = {
            {1, SHx, 0},
            {0, 1, 0},
            {0, 0, 1}
        };
        return x;
    }

    //Inclinacion respecto a la linea Y = Yref
    //Igual que el anterior pero traladando el objeto para que la inclinacion
    //se haga con respecto a la linea Y = Yref
    public double [][] CXLinea(double Yref, double SHx){
        double [][] x = {
            {1, SHx, -SHx * Yref},
            {0, 1, 0},
            {0, 0, 1}
        };
        return x;
    }

    //inclinacion con respecto al eje y
    //Inclina el objeto verticalmente
    public double [][] CY(double SHy){
        double [][] x = {
            {1, 0, 0},
            {SHy, 1, 0},
            {0, 0, 1}
        };
        return x;
    }   
    //Inclinacion con respecto a la linea x = xref
    //Lo mism que el anterior pero trasladando el objeto
    public double [][] CYLinea(double Xref, double SHy){
        double [][] x = {
            {1, 0, 0},
            {SHy, 1, -SHy * Xref},
            {0, 0, 1}
        };
        return x;
    }

}
