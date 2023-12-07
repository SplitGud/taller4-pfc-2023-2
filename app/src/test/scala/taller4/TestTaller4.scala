/**
 * @author Santiago Villa Salazar (2259527-3743) - Manuel Alexander Serna Jaraba (2259345-3743)
 * @version 1.0
 * @note 06 de Diciembre de 2023
 */
package taller4

import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner
import Taller4._

@RunWith(classOf[JUnitRunner])
class TestMultMatriz extends AnyFunSuite {
    test("Prueba1") {
        // Definicion de las matrices de prueba
        val matrix1 = Vector(Vector(1, 2, 3, 4), Vector(5, 6, 7, 8), Vector(9, 10, 11, 12), Vector(13, 14, 15, 16))
        val matrix2 = Vector(Vector(4, 3, 2, 1), Vector(8, 7, 6, 5), Vector(12, 11, 10, 9), Vector(16, 15, 14, 13))
        // Definicion del resultado esperado de la multiplicación
        val expected = Vector(Vector(120, 110, 100, 90), Vector(280, 254, 228, 202), Vector(440, 398, 356, 314), Vector(600, 542, 484, 426))

        // Verificacion de si el resultado de cada implementación coincide con el esperado
        if (multMatriz(matrix1, matrix2) != expected ||
          multMatrizPar(matrix1, matrix2) != expected ||
          multMatrizRec(matrix1, matrix2) != expected ||
          multMatrizRecPar(matrix1, matrix2) != expected ||
          multStrass(matrix1, matrix2) != expected ||
          multStrassPar(matrix1, matrix2) != expected) {
            // Lanzamiento de una excepción para ver si el resultado no coincide
            throw new Exception(s"\nEl resultado de alguna de las funciones no coincide con el resultado esperado")
        }
    }
    test("Prueba2") {
        // Definicion de las matrices de prueba más grandes
        val matrix1 = Vector(Vector(3, 2, 7, 8, 3, 4, 2, 3), Vector(0, 6, 9, 0, 3, 7, 4, 8), Vector(2, 6, 2, 1, 9, 9, 6, 9), Vector(5, 4, 8, 0, 1, 2, 0, 3), Vector(6, 5, 8, 8, 0, 3, 4, 5), Vector(4, 3, 4, 2, 0, 2, 0, 8), Vector(9, 2, 2, 8, 4, 5, 4, 9), Vector(7, 0, 0, 3, 1, 0, 2, 1))
        val matrix2 = Vector(Vector(6, 8, 3, 5, 9, 5, 5, 5), Vector(9, 8, 5, 3, 9, 3, 3, 8), Vector(8, 5, 9, 7, 9, 6, 3, 0), Vector(9, 1, 8, 6, 8, 2, 0, 0), Vector(1, 2, 4, 4, 9, 2, 8, 3), Vector(7, 4, 3, 2, 2, 2, 3, 6), Vector(1, 1, 9, 9, 6, 1, 2, 1), Vector(3, 1, 6, 6, 8, 0, 0, 5))
        // Definicion del resultado esperado de la multiplicación
        val expected = Vector(Vector(206, 110, 206, 174, 243, 95, 82, 81), Vector(206, 139, 228, 191, 264, 96, 98, 143), Vector(196, 144, 233, 210, 305, 84, 145, 190), Vector(154, 125, 135, 119, 190, 91, 75, 87), Vector(257, 157, 254, 221, 305, 119, 86, 117), Vector(139, 94, 133, 121, 183, 61, 47, 96), Vector(230, 147, 240, 229, 323, 101, 112, 152), Vector(75, 64, 73, 81, 116, 45, 47, 45))

        // Verificacion de si el resultado de cada implementación coincide con el esperado
        if (multMatriz(matrix1, matrix2) != expected ||
          multMatrizPar(matrix1, matrix2) != expected ||
          multMatrizRec(matrix1, matrix2) != expected ||
          multMatrizRecPar(matrix1, matrix2) != expected ||
          multStrass(matrix1, matrix2) != expected ||
          multStrassPar(matrix1, matrix2) != expected) {
            // Lanzamiento de una excepción por si el resultado no coincide
            throw new Exception(s"\nEl resultado de alguna de las funciones no coincide con el resultado esperado")
        }
    }
    test("Prueba3") {
        // Creacion de las matrices usando vectores inventados
        val m1 = Vector(Vector(2, 5, 3, 5, 5, 4, 2, 4, 1, 3, 4, 3, 5, 6, 3, 0), Vector(3, 0, 7, 3, 3, 5, 0, 6, 0, 6, 6, 1, 3, 2, 5, 1), Vector(2, 0, 5, 3, 3, 0, 4, 1, 2, 6, 2, 4, 5, 0, 5, 4), Vector(7, 2, 3, 1, 7, 3, 1, 0, 6, 1, 1, 4, 3, 7, 3, 3), Vector(4, 5, 0, 3, 3, 6, 5, 7, 3, 1, 0, 3, 1, 0, 6, 0), Vector(0, 6, 0, 4, 1, 2, 0, 3, 0, 7, 6, 4, 6, 1, 0, 4), Vector(6, 2, 3, 4, 4, 7, 1, 2, 1, 6, 5, 1, 5, 4, 2, 5), Vector(3, 6, 3, 3, 7, 4, 2, 5, 7, 6, 0, 2, 2, 3, 6, 6), Vector(5, 3, 4, 7, 6, 5, 7, 6, 6, 6, 1, 4, 5, 3, 3, 1), Vector(3, 7, 1, 3, 7, 0, 4, 7, 7, 4, 6, 2, 2, 6, 6, 2), Vector(5, 1, 7, 7, 3, 7, 6, 0, 1, 0, 6, 6, 7, 2, 3, 3), Vector(2, 3, 6, 4, 2, 3, 6, 2, 1, 7, 3, 0, 4, 4, 0, 2), Vector(5, 4, 3, 4, 4, 4, 3, 3, 5, 1, 3, 0, 3, 0, 2, 5), Vector(0, 4, 6, 6, 2, 5, 2, 0, 3, 7, 6, 7, 6, 7, 0, 1), Vector(1, 6, 2, 5, 1, 1, 7, 0, 6, 6, 1, 0, 2, 0, 0, 5), Vector(2, 0, 2, 5, 3, 7, 7, 4, 5, 7, 5, 4, 1, 7, 3, 4))
        val m2 = Vector(Vector(7, 1, 1, 7, 4, 0, 1, 5, 5, 6, 4, 6, 0, 6, 1, 6), Vector(4, 6, 1, 1, 0, 4, 7, 0, 1, 5, 6, 4, 3, 3, 3, 6), Vector(4, 7, 6, 3, 4, 6, 5, 4, 3, 1, 6, 4, 7, 4, 7, 4), Vector(0, 6, 4, 6, 1, 7, 2, 5, 6, 2, 4, 3, 7, 4, 1, 1), Vector(6, 4, 6, 2, 2, 7, 6, 1, 7, 2, 6, 0, 3, 1, 1, 2), Vector(4, 4, 1, 1, 1, 7, 4, 1, 2, 3, 0, 5, 7, 4, 0, 5), Vector(1, 7, 7, 1, 5, 2, 4, 0, 2, 1, 7, 4, 1, 7, 2, 4), Vector(4, 5, 1, 4, 5, 3, 2, 2, 0, 1, 4, 1, 5, 3, 2, 7), Vector(6, 5, 0, 0, 7, 1, 0, 2, 7, 4, 4, 2, 5, 3, 2, 7), Vector(6, 0, 2, 0, 0, 0, 4, 7, 0, 3, 5, 2, 5, 2, 3, 4), Vector(3, 2, 2, 3, 3, 2, 6, 1, 1, 3, 2, 0, 4, 7, 6, 6), Vector(7, 6, 7, 2, 1, 7, 6, 1, 0, 0, 3, 5, 5, 0, 1, 5), Vector(0, 6, 3, 2, 1, 5, 1, 6, 6, 2, 1, 2, 0, 1, 0, 4), Vector(2, 3, 4, 0, 2, 7, 2, 2, 2, 2, 2, 4, 2, 2, 5, 3), Vector(7, 4, 0, 3, 6, 4, 6, 7, 7, 4, 6, 3, 0, 3, 5, 1), Vector(7, 6, 5, 2, 4, 4, 2, 6, 6, 5, 3, 7, 0, 4, 6, 0))
        // Definicion del resultado esperado de la multiplicación
        val esperado = Vector(Vector(200, 244, 171, 127, 126, 261, 213, 157, 175, 137, 207, 157, 199, 169, 143, 224), Vector(218, 200, 139, 138, 144, 209, 199, 190, 154, 129, 193, 139, 207, 179, 166, 210), Vector(205, 212, 170, 108, 137, 178, 172, 191, 177, 116, 201, 147, 142, 146, 140, 159), Vector(253, 218, 167, 116, 159, 229, 171, 157, 223, 158, 198, 182, 150, 155, 137, 208), Vector(210, 221, 113, 122, 152, 188, 180, 122, 155, 132, 202, 153, 162, 164, 91, 212), Vector(168, 186, 129, 92, 69, 175, 174, 150, 109, 122, 149, 127, 159, 132, 119, 184), Vector(244, 225, 167, 144, 138, 238, 198, 209, 203, 177, 196, 195, 195, 204, 158, 225), Vector(319, 289, 178, 132, 199, 259, 234, 217, 254, 200, 280, 208, 217, 195, 181, 252), Vector(287, 329, 230, 172, 207, 292, 241, 221, 254, 181, 299, 220, 266, 237, 157, 302), Vector(293, 286, 179, 143, 215, 250, 253, 183, 233, 193, 292, 174, 210, 222, 199, 289), Vector(231, 315, 241, 177, 170, 305, 238, 197, 234, 156, 228, 226, 228, 239, 166, 246), Vector(167, 217, 174, 101, 119, 192, 175, 161, 141, 122, 203, 155, 182, 181, 148, 196), Vector(212, 229, 137, 130, 157, 187, 161, 153, 204, 158, 196, 164, 161, 187, 129, 202), Vector(222, 276, 221, 115, 119, 294, 238, 185, 169, 141, 213, 193, 262, 185, 177, 256), Vector(166, 212, 140, 76, 122, 137, 143, 137, 155, 134, 200, 149, 148, 165, 119, 170), Vector(269, 277, 215, 128, 192, 268, 227, 194, 200, 166, 245, 213, 248, 236, 185, 263))

        // Verificacion de si el resultado de cada implementación coincide con el esperado
        if (multMatriz(m1, m2) != esperado) {
            throw new Exception(s"\nEl resultado de multMatrizPar no coincide con el esperado")
        }
        if (multMatrizPar(m1, m2) != esperado) {
            throw new Exception(s"\nEl resultado de multMatrizPar no coincide con el esperado")
        }
        if (multMatrizRec(m1, m2) != esperado) {
            throw new Exception(s"\nEl resultado de multMatrizRec no coincide con el esperado")
        }
        if (multMatrizRecPar(m1, m2) != esperado) {
            throw new Exception(s"\nEl resultado de multMatrizRecPar no coincide con el esperado")
        }
        if (multStrass(m1, m2) != esperado) {
            throw new Exception(s"\nEl resultado de multStrass no coincide con el esperado")
        }
        if (multStrassPar(m1, m2) != esperado) {
            throw new Exception(s"\nEl resultado de multStrassPar no coincide con el esperado")
        }
    }
    test("Prueba4") {
        // Definicion de dos matrices 2x2
        val matrix1 = Vector(Vector(1, 2), Vector(3, 4))
        val matrix2 = Vector(Vector(5, 6), Vector(7, 8))
        // Definicion del resultado esperado de la multiplicación
        val expected = Vector(Vector(19, 22), Vector(43, 50))

        // Verificacion por si el resultado de cada implementación coincide con el esperado
        if (multMatriz(matrix1, matrix2) != expected ||
          multMatrizPar(matrix1, matrix2) != expected ||
          multMatrizRec(matrix1, matrix2) != expected ||
          multMatrizRecPar(matrix1, matrix2) != expected ||
          multStrass(matrix1, matrix2) != expected ||
          multStrassPar(matrix1, matrix2) != expected) {
            // Lanzamiento de una excepción por si algún resultado no coincide
            throw new Exception(s"\nEl resultado de alguna de las funciones no coincide con el resultado esperado")
        }
    }
}