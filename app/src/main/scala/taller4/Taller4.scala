/**
 * @author Santiago Villa Salazar (2259527-3743) - Manuel Alexander Serna Jaraba (2259345-3743)
 * @version 1.0
 * @note 06 de Diciembre de 2023
 */

package taller4

import org.scalameter.measure
import org.scalameter.withWarmer
import org.scalameter.Warmer
import scala.util.Random
import common._
import org.scalameter._
object Taller4 {
  type Matriz = Vector[Vector[Int]]

  // Función para imprimir un saludo
  def saludo() = "Taller #4 2023-II"

  // Función para generar una matriz aleatoria
  def matrizAlAzar(long: Int, vals: Int): Matriz = {
    Vector.fill(long) {
      Vector.fill(long) {
        Random.nextInt(vals)
      }
    }
  }

  // Función para calcular el producto punto de dos vectores
  def prodPunto(v1: Vector[Int], v2: Vector[Int]): Int = {
    (v1 zip v2).map({ case (i, j) => i * j }).sum
  }

  // Función para calcular la transpuesta de una matriz
  def transpuesta(m: Matriz): Matriz = {
    val l = m.length
    Vector.tabulate(l, l)((i, j) => m(j)(i))
  }

  // Función auxiliar para multiplicar un vector por una matriz transformada
  def multMatrizAux(vec1: Vector[Int], matrizTransformer: Matriz): Vector[Int] =
    matrizTransformer.map(row => prodPunto(vec1, row))

  // Función para multiplicar una matriz por otra
  def multMatriz(m1: Matriz, m2: Matriz): Matriz = {
    val size = m1.length
    Vector.tabulate(size, size) { (i, j) =>
      val fila = m1(i)
      val columna = transpuesta(m2)(j)
      prodPunto(fila, columna)
    }
  }

  // Función para multiplicar una matriz por otra de forma paralela
  def multMatrizPar(m1: Matriz, m2: Matriz): Matriz = {
    val size = m1.length
    Vector.tabulate(size, size) { (i, j) =>
      val (fila, columna) = parallel(m1(i), transpuesta(m2)(j))
      prodPunto(fila, columna)
    }
  }

  // Función para obtener una submatriz de una matriz
  def subMatriz(m: Matriz, i: Int, j: Int, n: Int): Matriz = {
    Vector.tabulate(n) { y =>
      Vector.tabulate(n) { x =>
        m(i + y)(j + x)
      }
    }
  }

  // Función para sumar dos matrices
  def sumMatriz(m1: Matriz, m2: Matriz): Matriz = {
    Vector.tabulate(m1.length) { y =>
      Vector.tabulate(m1.head.length) { x =>
        m1(y)(x) + m2(y)(x)
      }
    }
  }

  // Función para multiplicar dos matrices de forma recursiva
  def multMatrizRec(m1: Matriz, m2: Matriz): Matriz = {
    if (m1.length == 1) {
      Vector(Vector(m1(0)(0) * m2(0)(0)))
    } else {
      val m1LengthHalf = m1.length / 2
      val m2LengthHalf = m2.length / 2

      val m1SubMatrices = Vector(
        subMatriz(m1, 0, 0, m1LengthHalf),
        subMatriz(m1, 0, m1LengthHalf, m1LengthHalf),
        subMatriz(m1, m1LengthHalf, 0, m1LengthHalf),
        subMatriz(m1, m1LengthHalf, m1LengthHalf, m1LengthHalf)
      )

      val m2SubMatrices = Vector(
        subMatriz(m2, 0, 0, m2LengthHalf),
        subMatriz(m2, 0, m2LengthHalf, m2LengthHalf),
        subMatriz(m2, m2LengthHalf, 0, m2LengthHalf),
        subMatriz(m2, m2LengthHalf, m2LengthHalf, m2LengthHalf)
      )

      val vector1 = sumMatriz(
        multMatrizRec(m1SubMatrices(0), m2SubMatrices(0)),
        multMatrizRec(m1SubMatrices(1), m2SubMatrices(2))
      )
      val vector2 = sumMatriz(
        multMatrizRec(m1SubMatrices(0), m2SubMatrices(1)),
        multMatrizRec(m1SubMatrices(1), m2SubMatrices(3))
      )
      val vector3 = sumMatriz(
        multMatrizRec(m1SubMatrices(2), m2SubMatrices(0)),
        multMatrizRec(m1SubMatrices(3), m2SubMatrices(2))
      )
      val vector4 = sumMatriz(
        multMatrizRec(m1SubMatrices(2), m2SubMatrices(1)),
        multMatrizRec(m1SubMatrices(3), m2SubMatrices(3))
      )

      val sumVector1y2 = vector1.zip(vector2).map { case (v1, v2) => v1 ++ v2 }
      val sumVector3y4 = vector3.zip(vector4).map { case (v3, v4) => v3 ++ v4 }

      sumVector1y2 ++ sumVector3y4
    }
  }

  // Función para multiplicar dos matrices de manera recursiva y paralela
  def multMatrizRecPar(m1: Matriz, m2: Matriz): Matriz = {
    if (m1.length == 1) {
      Vector(Vector(m1(0)(0) * m2(0)(0)))
    } else {
      val m2T = m2

      val (m1SubMatrices, m2SubMatrices) = parallel(
        Vector(
          subMatriz(m1, 0, 0, m1.length / 2),
          subMatriz(m1, 0, m1.length / 2, m1.length / 2),
          subMatriz(m1, m1.length / 2, 0, m1.length / 2),
          subMatriz(m1, m1.length / 2, m1.length / 2, m1.length / 2)
        ),
        Vector(
          subMatriz(m2T, 0, 0, m2.length / 2),
          subMatriz(m2T, 0, m2.length / 2, m2.length / 2),
          subMatriz(m2T, m2.length / 2, 0, m2.length / 2),
          subMatriz(m2T, m2.length / 2, m2.length / 2, m2.length / 2)
        )
      )

      val (c1, c2, c3, c4) = parallel(
        sumMatriz(
          multMatrizRecPar(m1SubMatrices(0), m2SubMatrices(0)),
          multMatrizRecPar(m1SubMatrices(1), m2SubMatrices(2))
        ),
        sumMatriz(
          multMatrizRecPar(m1SubMatrices(0), m2SubMatrices(1)),
          multMatrizRecPar(m1SubMatrices(1), m2SubMatrices(3))
        ),
        sumMatriz(
          multMatrizRecPar(m1SubMatrices(2), m2SubMatrices(0)),
          multMatrizRecPar(m1SubMatrices(3), m2SubMatrices(2))
        ),
        sumMatriz(
          multMatrizRecPar(m1SubMatrices(2), m2SubMatrices(1)),
          multMatrizRecPar(m1SubMatrices(3), m2SubMatrices(3))
        )
      )

      val sumavector1y2 = Vector.tabulate(c1.size)(y => c1(y) ++ c2(y))
      val sumavector3y4 = Vector.tabulate(c3.size)(y => c3(y) ++ c4(y))

      sumavector1y2 ++ sumavector3y4
    }
  }

  // Función para restar dos matrices
  def restaMatriz(m1: Matriz, m2: Matriz): Matriz = {
    Vector.tabulate(m1.length) { y =>
      Vector.tabulate(m1.length) { x =>
        m1(y)(x) - m2(y)(x)
      }
    }
  }

  // Función para multiplicar dos matrices usando el algoritmo de Strassen
  def multStrass(m1: Matriz, m2: Matriz): Matriz = {
    if (m1.length == 1) {
      Vector(Vector(m1(0)(0) * m2(0)(0)))
    } else {
      val m1_1 = subMatriz(m1, 0, 0, m1.length / 2)
      val m1_2 = subMatriz(m1, 0, m1.length / 2, m1.length / 2)
      val m1_3 = subMatriz(m1, m1.length / 2, 0, m1.length / 2)
      val m1_4 = subMatriz(m1, m1.length / 2, m1.length / 2, m1.length / 2)
      val m2_1 = subMatriz(m2, 0, 0, m2.length / 2)
      val m2_2 = subMatriz(m2, 0, m2.length / 2, m2.length / 2)
      val m2_3 = subMatriz(m2, m2.length / 2, 0, m2.length / 2)
      val m2_4 = subMatriz(m2, m2.length / 2, m2.length / 2, m2.length / 2)

      val s1 = restaMatriz(m2_2, m2_4)
      val s2 = sumMatriz(m1_1, m1_2)
      val s3 = sumMatriz(m1_3, m1_4)
      val s4 = restaMatriz(m2_3, m2_1)
      val s5 = sumMatriz(m1_1, m1_4)
      val s6 = sumMatriz(m2_1, m2_4)
      val s7 = restaMatriz(m1_2, m1_4)
      val s8 = sumMatriz(m2_3, m2_4)
      val s9 = restaMatriz(m1_1, m1_3)
      val s10 = sumMatriz(m2_1, m2_2)

      val p1 = multStrass(m1_1, s1)
      val p2 = multStrass(s2, m2_4)
      val p3 = multStrass(s3, m2_1)
      val p4 = multStrass(m1_4, s4)
      val p5 = multStrass(s5, s6)
      val p6 = multStrass(s7, s8)
      val p7 = multStrass(s9, s10)

      val c1 = sumMatriz(sumMatriz(p5, p4), restaMatriz(p6, p2))
      val c2 = sumMatriz(p1, p2)
      val c3 = sumMatriz(p3, p4)
      val c4 = restaMatriz(sumMatriz(p5, p1), sumMatriz(p3, p7))

      val sumavector1y2 = Vector.tabulate(c1.size)(y => c1(y) ++ c2(y))
      val sumavector3y4 = Vector.tabulate(c3.size)(y => c3(y) ++ c4(y))

      sumavector1y2 ++ sumavector3y4
    }
  }

  // Función para multiplicar dos matrices usando el algoritmo de Strassen de forma paralela
  def multStrassPar(m1: Matriz, m2: Matriz): Matriz = {
    if (m1.length == 1) {
      Vector(Vector(m1(0)(0) * m2(0)(0)))
    } else {
      val (m1SubMatrices, m2SubMatrices) = parallel(
        Vector(
          subMatriz(m1, 0, 0, m1.length / 2),
          subMatriz(m1, 0, m1.length / 2, m1.length / 2),
          subMatriz(m1, m1.length / 2, 0, m1.length / 2),
          subMatriz(m1, m1.length / 2, m1.length / 2, m1.length / 2)
        ),
        Vector(
          subMatriz(m2, 0, 0, m2.length / 2),
          subMatriz(m2, 0, m2.length / 2, m2.length / 2),
          subMatriz(m2, m2.length / 2, 0, m2.length / 2),
          subMatriz(m2, m2.length / 2, m2.length / 2, m2.length / 2)
        )
      )

      val s1 = task {
        restaMatriz(m2SubMatrices(1), m2SubMatrices(3))
      }
      val s2 = task {
        sumMatriz(m1SubMatrices(0), m1SubMatrices(1))
      }
      val s3 = task {
        sumMatriz(m1SubMatrices(2), m1SubMatrices(3))
      }
      val s4 = task {
        restaMatriz(m2SubMatrices(2), m2SubMatrices(0))
      }
      val s5 = task {
        sumMatriz(m1SubMatrices(0), m1SubMatrices(3))
      }
      val s6 = task {
        sumMatriz(m2SubMatrices(0), m2SubMatrices(3))
      }
      val s7 = task {
        restaMatriz(m1SubMatrices(1), m1SubMatrices(3))
      }
      val s8 = task {
        sumMatriz(m2SubMatrices(2), m2SubMatrices(3))
      }
      val s9 = task {
        restaMatriz(m1SubMatrices(0), m1SubMatrices(2))
      }
      val s10 = task {
        sumMatriz(m2SubMatrices(0), m2SubMatrices(1))
      }

      val p1 = task {
        multStrass(m1SubMatrices(0), s1.join)
      }
      val p2 = task {
        multStrass(s2.join, m2SubMatrices(3))
      }
      val p3 = task {
        multStrass(s3.join, m2SubMatrices(0))
      }
      val p4 = task {
        multStrass(m1SubMatrices(3), s4.join)
      }
      val p5 = task {
        multStrass(s5.join, s6.join)
      }
      val p6 = task {
        multStrass(s7.join, s8.join)
      }
      val p7 = task {
        multStrass(s9.join, s10.join)
      }

      val c1 = sumMatriz(sumMatriz(p5.join, p4.join), restaMatriz(p6.join, p2.join))
      val c2 = sumMatriz(p1.join, p2.join)
      val c3 = sumMatriz(p3.join, p4.join)
      val c4 = restaMatriz(sumMatriz(p5.join, p1.join), sumMatriz(p3.join, p7.join))

      val sumavector1y2 = Vector.tabulate(c1.size)(y => c1(y) ++ c2(y))
      val sumavector3y4 = Vector.tabulate(c3.size)(y => c3(y) ++ c4(y))

      sumavector1y2 ++ sumavector3y4
    }
  }

  // Función para comparar el tiempo de ejecución de dos algoritmos
  def compararAlgoritmos(funcion1: (Matriz, Matriz) => Matriz, funcion2: (Matriz, Matriz) => Matriz)
                        (matriz1: Matriz, matriz2: Matriz): (Double, Double, Double) = {
    val tiempoFuncion1 = withWarmer(new Warmer.Default) measure {
      funcion1(matriz1, matriz2)
    }
    val tiempoFuncion2 = withWarmer(new Warmer.Default) measure {
      funcion2(matriz1, matriz2)
    }

    val tiempo1: Double = tiempoFuncion1.value
    val tiempo2: Double = tiempoFuncion2.value
    val aceleracion = tiempo1 / tiempo2

    (tiempo1, tiempo2, aceleracion)
  }

  // Función para ejecutar el programa
  object MainApp {

    def main(args: Array[String]): Unit = {
      println(saludo())
      realizarPruebas(8, 10, "Pruebas 8x8")
      realizarPruebas(32, 10, "Pruebas 32x32")
      realizarPruebas(128, 10, "Pruebas 128x128")
      realizarPruebas(256, 10, "Pruebas 256x256")
    }

    def realizarPruebas(tamano: Int, rango: Int, mensaje: String): Unit = {
      println(mensaje)
      val m1 = matrizAlAzar(tamano, rango)
      val m2 = matrizAlAzar(tamano, rango)
      imprimirResultados("Multiplicación estándar", multMatriz, multMatrizPar, m1, m2)
      imprimirResultados("Multiplicación recursiva", multMatrizRec, multMatrizRecPar, m1, m2)
      imprimirResultados("Multiplicación Strassen", multStrass, multStrassPar, m1, m2)
    }

    def imprimirResultados(nombre: String, func1: (Matriz, Matriz) => Matriz, func2: (Matriz, Matriz) => Matriz, m1: Matriz, m2: Matriz): Unit = {
      val (tiempoFunc1, tiempoFunc2, aceleracion) = compararAlgoritmos(func1, func2)(m1, m2)
      println(s"$nombre:")
      println(s"Tiempo Función 1: $tiempoFunc1 ms")
      println(s"Tiempo Función 2: $tiempoFunc2 ms")
      println(s"Aceleración: $aceleracion veces más rápida\n")
    }
  }
}