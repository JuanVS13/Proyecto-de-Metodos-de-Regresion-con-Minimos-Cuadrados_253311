/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package implementaciones;

import implementaciones.SolucionEcuaciones;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import pruebas.frmRegresionMinCuad;

/**
 * RegresionMinCuad.java
 * 
 * Clase que implementa metodos de regresion con minimos cuadrados para la solucion de ecuaciones
 * mediante los metodos de solucion de sistemas de ecuaciones como la Eliminacion de Gauss con pivoteo como dependencia
 *
 * @author Juan Antonio Valencia Sánchez
 */
public class RegresionMinCuad {
    private final DecimalFormat df6;
    private final SolucionEcuaciones solver;
    private final StringBuilder pasos;

    /**
     * Constructor que inicializa el formato decimal, la solucion de ecuaciones y los pasos
     */

    public RegresionMinCuad() {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.US);
        df6 = new DecimalFormat("0.000000", dfs);
        solver = new SolucionEcuaciones();
        pasos = new StringBuilder();
    }

    /**
     * Realiza la regresion lineal de grado 1 para los puntos dados, además, calcula las sumatorias necesarias, 
     * genera los pasos del procedimiento y regresa los coeficientes a0 y a1
     *
     * @param puntos matriz de pares (x, y) representando los puntos de entrada
     * @return un arreglo con  los coeficientes a0, a1
     */
    public double[] regresionLineal(double[][] puntos) {
        pasos.setLength(0);

        int n = puntos.length;
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;

        //Calcula las sumatorias
        for (double[] p : puntos) {
            double x = p[0];
            double y = p[1];
            sumX += x;
            sumY += y;
            sumXY += x * y;
            sumX2 += x * x;
        }

        //Calcula a1 y a0
        double promX = sumX / n;
        double promY = sumY / n;

        double a1 = (sumXY - n * promX * promY) / (sumX2 - n * promX * promX);
        double a0 = promY - a1 * promX;

        //Se muestran los pasos
        pasos.append("Sustituyendo en las ecuaciones de a1 y a0:\n\n");

        pasos.append("a1 = [n(Σxy) - (Σx)(Σy)] / [n(Σx²) - (Σx)²]\n");
        pasos.append("a1 = [").append(n).append("(").append(df6.format(sumXY)).append(") - (")
                .append(df6.format(sumX)).append(")(").append(df6.format(sumY)).append(")] / [")
                .append(n).append("(").append(df6.format(sumX2)).append(") - (")
                .append(df6.format(sumX)).append(")²]\n");
        pasos.append("a1 = ").append(df6.format(a1)).append("\n\n");

        pasos.append("a0 = (Σy/n) - a1(Σx/n)\n");
        pasos.append("a0 = (").append(df6.format(sumY)).append("/").append(n).append(") - ")
                .append(df6.format(a1)).append("(").append(df6.format(sumX)).append("/")
                .append(n).append(")\n");
        pasos.append("a0 = ").append(df6.format(a0)).append("\n");

        return new double[]{a0, a1};
    }

    /**
     * Genera la tabla con los valores utilizados en la regresion linel y se muestran los encabezados de cada resultado
     *
     * @param puntos matriz de pares (x, y) representando los puntos de entrada
     * @return una matriz con los datos de la tabla
     */
    public Object[][] generaTablaLineal(double[][] puntos) {
        int n = puntos.length;
        Object[][] tabla = new Object[n + 1][5];

        double sumX = 0, sumY = 0, sumX2 = 0, sumXY = 0;

        for (int i = 0; i < n; i++) {
            double x = puntos[i][0];
            double y = puntos[i][1];
            double x2 = x * x;
            double xy = x * y;

            sumX += x;
            sumY += y;
            sumX2 += x2;
            sumXY += xy;

            tabla[i][0] = i + 1;
            tabla[i][1] = df6.format(x);
            tabla[i][2] = df6.format(y);
            tabla[i][3] = df6.format(x2);
            tabla[i][4] = df6.format(xy);
        }

        tabla[n][0] = "Σ";
        tabla[n][1] = df6.format(sumX);
        tabla[n][2] = df6.format(sumY);
        tabla[n][3] = df6.format(sumX2);
        tabla[n][4] = df6.format(sumXY);

        return tabla;
    }

    /**
     * Realiza la regresión polinomial de grado 2 para los puntos dados, además calcula el sistema de ecuaciones que corresponde,
     * muestra los pasos y obtiene los coeficientes utilizando el metodo de eliminación de Gauss.
     *
     * @param puntos matriz de pares (x, y) representando los puntos de entrada
     * @return un arreglo con los coeficientes a0, a1, a2.
     */
    public double[] regresionPolinomial(double[][] puntos) {
        pasos.setLength(0);

        int n = puntos.length;
        double sx = 0, sx2 = 0, sx3 = 0, sx4 = 0, sy = 0, sxy = 0, sx2y = 0;

        for (double[] p : puntos) {
            double x = p[0];
            double y = p[1];
            sx += x;
            sx2 += Math.pow(x, 2);
            sx3 += Math.pow(x, 3);
            sx4 += Math.pow(x, 4);
            sy += y;
            sxy += x * y;
            sx2y += Math.pow(x, 2) * y;
        }

        double[][] sistema = {
            {n, sx, sx2, sy},
            {sx, sx2, sx3, sxy},
            {sx2, sx3, sx4, sx2y}
        };

        pasos.append("Sistema de ecuaciones para a0, a1 y a2:\n\n");
        for (double[] fila : sistema) {
            for (int j = 0; j < fila.length; j++) {
                pasos.append(df6.format(fila[j])).append("\t");
            }
            pasos.append("\n");
        }

        pasos.append("\nResolviendo el sistema de ecuaciones para a0, a1, a2:\n\n");

        double[] a = solver.eliminacionGauss(sistema);

        for (int i = 0; i < a.length; i++) {
            pasos.append("a").append(i).append(" = ").append(df6.format(a[i])).append("\n");
        }

        return a;
    }

    /**
     * Genera una tabla con los valores utilizados en la regresion polinomial de grado 2, ademas se incluyen 
     * sus encabezados y sumatoria
     *
     * @param puntos matriz de pares (x, y) representando los puntos de entrada
     * @return una matriz con los datos de la tabla
     */
    public Object[][] generaTablaPolinomial(double[][] puntos) {
        int n = puntos.length;
        Object[][] tabla = new Object[n + 1][8];
        double sumX = 0, sumY = 0, sumX2 = 0, sumX3 = 0, sumX4 = 0, sumXY = 0, sumX2Y = 0;

        for (int i = 0; i < n; i++) {
            double x = puntos[i][0];
            double y = puntos[i][1];
            double x2 = x * x;
            double x3 = x2 * x;
            double x4 = x2 * x2;
            double xy = x * y;
            double x2y = x2 * y;

            sumX += x;
            sumY += y;
            sumX2 += x2;
            sumX3 += x3;
            sumX4 += x4;
            sumXY += xy;
            sumX2Y += x2y;

            tabla[i][0] = i + 1;
            tabla[i][1] = df6.format(x);
            tabla[i][2] = df6.format(y);
            tabla[i][3] = df6.format(x2);
            tabla[i][4] = df6.format(x3);
            tabla[i][5] = df6.format(x4);
            tabla[i][6] = df6.format(xy);
            tabla[i][7] = df6.format(x2y);
        }

        tabla[n][0] = "Σ";
        tabla[n][1] = df6.format(sumX);
        tabla[n][2] = df6.format(sumY);
        tabla[n][3] = df6.format(sumX2);
        tabla[n][4] = df6.format(sumX3);
        tabla[n][5] = df6.format(sumX4);
        tabla[n][6] = df6.format(sumXY);
        tabla[n][7] = df6.format(sumX2Y);

        return tabla;
    }

     /**
     * Devuelve el texto con los pasos
     *
     * @return cadena con los pasos
     */
    public String getPasos() {
        return pasos.toString();
    }

    /**
     * Devuelve la ecuacion obtenida a partir de los coeficientes calculados
     *
     * @param coef arreglo con los coeficientes obtenidos
     * @param esLineal indica si la ecuacion es lineal o polinomial
     * @return cadena que representa la ecuacion resultante
     */
    public String getEcuacion(double[] coef, boolean esLineal) {
        if (esLineal) {
            return "Ecuacion de la linea:\ny = " + df6.format(coef[0]) + " + " + df6.format(coef[1]) + "x";
        } else {
            return "Ecuacion de la parabola:\ny = " + df6.format(coef[0]) + " + " +
                    df6.format(coef[1]) + "x + " + df6.format(coef[2]) + "x²";
        }
    }
    
    /**
     * Realiza la regresion lineal multiple con dos variables x1, x2, y, ademas de que calcula las sumatorias y 
     * genera la matriz del sistema de ecuaciones, obtiene los coeficientes con el metodode Gauss
     *
     * @param puntos matriz de tres columnas x1, x2, y
     * @return arreglo con los coeficientes a0, a1, a2
     */
    public double[] regresionLinealMultiple(double[][] puntos) {
        pasos.setLength(0);
        int n = puntos.length;

        double sumX1 = 0, sumX2 = 0, sumY = 0;
        double sumX1X1 = 0, sumX2X2 = 0, sumX1X2 = 0;
        double sumX1Y = 0, sumX2Y = 0;

        //Calcula las sumatorias
        for (double[] p : puntos) {
            double x1 = p[0];
            double x2 = p[1];
            double y = p[2];
            sumX1 += x1;
            sumX2 += x2;
            sumY += y;
            sumX1X1 += x1 * x1;
            sumX2X2 += x2 * x2;
            sumX1X2 += x1 * x2;
            sumX1Y += x1 * y;
            sumX2Y += x2 * y;
        }

        //Crea las matrices del sistema
        double[][] sistema = {
            {n, sumX1, sumX2, sumY},
            {sumX1, sumX1X1, sumX1X2, sumX1Y},
            {sumX2, sumX1X2, sumX2X2, sumX2Y}
        };

        pasos.append("Sistema de ecuaciones para a0, a1 y a2:\n\n");
        for (double[] fila : sistema) {
            for (int j = 0; j < fila.length; j++) {
                pasos.append(df6.format(fila[j])).append("\t");
            }
            pasos.append("\n");
        }

        pasos.append("\nResolviendo el sistema de ecuaciones para a0, a1, a2:\n\n");

        double[] a = solver.eliminacionGauss(sistema);

        for (int i = 0; i < a.length; i++) {
            pasos.append("a").append(i).append(" = ").append(df6.format(a[i])).append("\n");
        }

        return a;
    }

    /**
     * Genera la tabla con los valores usados en la regresion lineal multiple
     *
     * @param puntos matriz con x1, x2, y
     * @return matriz de objetos para llenar la tabla
     */
    public Object[][] generaTablaLinealMultiple(double[][] puntos) {
        int n = puntos.length;
        Object[][] tabla = new Object[n + 1][9];

        double sumX1 = 0, sumX2 = 0, sumY = 0;
        double sumX1X1 = 0, sumX2X2 = 0, sumX1X2 = 0;
        double sumX1Y = 0, sumX2Y = 0;

        for (int i = 0; i < n; i++) {
            double x1 = puntos[i][0];
            double x2 = puntos[i][1];
            double y = puntos[i][2];
            double x1x1 = x1 * x1;
            double x2x2 = x2 * x2;
            double x1x2 = x1 * x2;
            double x1y = x1 * y;
            double x2y = x2 * y;

            sumX1 += x1;
            sumX2 += x2;
            sumY += y;
            sumX1X1 += x1x1;
            sumX2X2 += x2x2;
            sumX1X2 += x1x2;
            sumX1Y += x1y;
            sumX2Y += x2y;

            tabla[i][0] = i + 1;
            tabla[i][1] = df6.format(x1);
            tabla[i][2] = df6.format(x2);
            tabla[i][3] = df6.format(y);
            tabla[i][4] = df6.format(x1x1);
            tabla[i][5] = df6.format(x2x2);
            tabla[i][6] = df6.format(x1x2);
            tabla[i][7] = df6.format(x1y);
            tabla[i][8] = df6.format(x2y);
        }

        tabla[n][0] = "Σ";
        tabla[n][1] = df6.format(sumX1);
        tabla[n][2] = df6.format(sumX2);
        tabla[n][3] = df6.format(sumY);
        tabla[n][4] = df6.format(sumX1X1);
        tabla[n][5] = df6.format(sumX2X2);
        tabla[n][6] = df6.format(sumX1X2);
        tabla[n][7] = df6.format(sumX1Y);
        tabla[n][8] = df6.format(sumX2Y);

        return tabla;
    }

    /**
     * Devuelve la ecuacion de la funcion lineal multiple
     *
     * @param coef coeficientes a0, a1, a2
     * @return texto de la ecuacion formateado
     */
    public String getEcuacionLinealMultiple(double[] coef) {
        return "Ecuacion de la función lineal:\ny = " + df6.format(coef[0]) + " + " +
                df6.format(coef[1]) + "x1 + " + df6.format(coef[2]) + "x2";
    }
    
    //Clase main que ejecuta el frame para la aplicacion
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new frmRegresionMinCuad().setVisible(true);
        });
    }
    
}
