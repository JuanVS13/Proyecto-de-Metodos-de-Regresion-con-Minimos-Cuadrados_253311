/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package implementaciones;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 * RegresionMinCuadTest.java
 * 
 * Clase de prueba para los metodos de la clase RegresionMinCuad
 *
 * @author Juan Antonio Valencia Sánchez
 */
public class RegresionMinCuadTest {
    
    public RegresionMinCuadTest() {
    }

    private RegresionMinCuad regresion;
    private double[][] puntosLineales;
    private double[][] puntosPolinomiales;
    private double[][] puntosLinealesMultiples;


    /**
     * Se ejecuta antes de cada prueba para inicializar los objetos y datos la prueba
     */
    @BeforeEach
    void setUp() {
        regresion = new RegresionMinCuad();

        //Puntos que siguen una relacion lineal y = 2x + 1
        puntosLineales = new double[][]{
            {1, 3},
            {2, 5},
            {3, 7},
            {4, 9}
        };

        //Puntos aproximados a una funcion cuadratica y = x² + 2x + 1
        puntosPolinomiales = new double[][]{
            {1, 4},
            {2, 9},
            {3, 16},
            {4, 25}
        };
    
        //Puntos para una regresion lineal multiple y = 2x1 + 3x2 + 5
        puntosLinealesMultiples = new double[][]{
            {1, 2, 13},
            {2, 1, 12},
            {3, 4, 23},
            {4, 3, 22}
        };
    }

    /**
     * Prueba del metodo regresionLineal()
     * 
     * Verifica que los coeficientes calculados sean correctos para una funcion lineal conocida
     */
    @Test
    void testRegresionLineal() {
        double[] coef = regresion.regresionLineal(puntosLineales);
        assertEquals(1.0, coef[0], 0.0001, "El coeficiente a0 debe ser cercano a 1");
        assertEquals(2.0, coef[1], 0.0001, "El coeficiente a1 debe ser cercano a 2");
    }

    /**
     * Prueba del metodo generaTablaLineal()
     * 
     * Verifica que la tabla tenga el tamaño y formato esperado
     */
    @Test
    void testGeneraTablaLineal() {
        Object[][] tabla = regresion.generaTablaLineal(puntosLineales);
        assertEquals(5, tabla.length, "La tabla debe tener n + 1 filas");
        assertEquals(5, tabla[0].length, "La tabla debe tener 5 columnas");
        assertEquals("Σ", tabla[tabla.length - 1][0], "La ultima fila debe tener el simbolo Σ");
    }

    /**
     * Prueba del metodo regresionPolinomial()
     * 
     * Verifica que los coeficientes obtenidos correspondan aproximadamente a la ecuacion y = x² + 2x + 1
     */
    @Test
    void testRegresionPolinomial() {
        double[] coef = regresion.regresionPolinomial(puntosPolinomiales);
        assertEquals(1.0, coef[0], 0.1, "a0 debe ser cercano a 1");
        assertEquals(2.0, coef[1], 0.1, "a1 debe ser cercano a 2");
        assertEquals(1.0, coef[2], 0.1, "a2 debe ser cercano a 1");
    }

    /**
     * Prueba del metodo generaTablaPolinomial()
     * 
     * Verifica que la tabla contenga los valores y sumatorias esperadas
     */
    @Test
    void testGeneraTablaPolinomial() {
        Object[][] tabla = regresion.generaTablaPolinomial(puntosPolinomiales);
        assertEquals(5, tabla.length, "La tabla debe tener n + 1 filas");
        assertEquals(8, tabla[0].length, "La tabla debe tener 8 columnas");
        assertEquals("Σ", tabla[tabla.length - 1][0], "La ultima fila debe tener el simbolo Σ");
    }
    
    /**
     * Prueba del metodo regresionLinealMultiple()
     * 
     * Verifica que los coeficientes obtenidos sean correctos para un sistema con dos variables
     */
    @Test
    void testRegresionLinealMultiple() {
        double[] coef = regresion.regresionLinealMultiple(puntosLinealesMultiples);
        assertEquals(5.0, coef[0], 0.1, "a0 debe ser cercano a 5");
        assertEquals(2.0, coef[1], 0.1, "a1 debe ser cercano a 2");
        assertEquals(3.0, coef[2], 0.1, "a2 debe ser cercano a 3");
    }

    /**
     * Prueba del metodo generaTablaLinealMultiple()
     * 
     * Verifica que la tabla generada tenga el tamaño y los encabezados esperados
     */
    @Test
    void testGeneraTablaLinealMultiple() {
        Object[][] tabla = regresion.generaTablaLinealMultiple(puntosLinealesMultiples);
        assertEquals(5, tabla.length, "La tabla debe tener n + 1 filas");
        assertEquals(9, tabla[0].length, "La tabla debe tener 9 columnas");
        assertEquals("Σ", tabla[tabla.length - 1][0], "La ultima fila debe contener el simbolo Σ");
    }
    
    /**
     * Prueba del metodo getEcuacionLinealMultiple()
     * 
     * Verifica que la ecuacion devuelta tenga el formato correcto
     */
    @Test
    void testGetEcuacionLinealMultiple() {
        double[] coef = {5.0, 2.0, 3.0};
        String ecuacion = regresion.getEcuacionLinealMultiple(coef);
        assertTrue(ecuacion.contains("y = 5.000000 + 2.000000x1 + 3.000000x2"),
                   "La ecuacion multiple debe tener el formato esperado");
    }

    /**
     * Prueba del metodo getEcuacion() con una ecuacion lineal
     */
    @Test
    void testGetEcuacionLineal() {
        double[] coef = {1.0, 2.0};
        String ecuacion = regresion.getEcuacion(coef, true);
        assertTrue(ecuacion.contains("y = 1.000000 + 2.000000x"), 
                   "La ecuacion lineal debe tener el formato esperado");
    }

    /**
     * Prueba del metodo getEcuacion() con una ecuacion polinomial
     */
    @Test
    void testGetEcuacionPolinomial() {
        double[] coef = {1.0, 2.0, 1.0};
        String ecuacion = regresion.getEcuacion(coef, false);
        assertTrue(ecuacion.contains("y = 1.000000 + 2.000000x + 1.000000x²"), "La ecuacion polinomial debe tener el formato esperado");
    }

    /**
     * Prueba del metodo getPasos()
     * 
     * Verifica que los pasos generados no esten vacios tras ejecutar una regresion
     */
    @Test
    void testGetPasos() {
        regresion.regresionLineal(puntosLineales);
        String pasos = regresion.getPasos();
        assertFalse(pasos.isEmpty(), "Los pasos no deben estar vacios");
        assertTrue(pasos.contains("a1"), "Los pasos deben incluir el calculo de a1");
    }
}
