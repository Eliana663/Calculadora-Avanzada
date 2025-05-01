package com.example.calculadora_Avanzada

import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt


class CalcularDiferentesOperaciones {



    fun decimalFormat(numero: Double): String {
        return String.format("%.2f", numero)
    }

    // Calcula todo tipo de operaciones avanzadas


    fun OperacionAvanzada(numero: String, operadorAvanzado: String): String {


        var resultadoTemporal = when (operadorAvanzado) {

            "sqr" -> (numero.toDouble().pow(2).toString())
            "√"   -> (sqrt(numero.toDouble()).toString())
            "sin" -> (sin(numero.toDouble()).toString())
            "cos" -> (cos(numero.toDouble()).toString())


            else -> {
                numero
            }
        }

        resultadoTemporal =  decimalFormat(resultadoTemporal.toDouble()).toString()

        return  resultadoTemporal

    }

    fun OperacionAvanzadaPorcentaje (numero1: String, numero2: String, operadorAvanzado: String): String {

        var resultadoTemporal = when (operadorAvanzado) {

            "%" -> ((numero1.toDouble() * numero2.toDouble() / 100).toString())
            else -> {
                numero1
            }
        }

        resultadoTemporal =  decimalFormat(resultadoTemporal.toDouble()).toString()

        return  resultadoTemporal
    }
    // Función para hacer operaciones básicas como suma, resta, multiplicación y división


    fun operacionBasica(numero1: String, numero2: String, operador: String): String {


           var resultadoTemporal =   when (operador ) {

            "suma" -> (numero1.toDouble() + numero2.toDouble()).toString()
            "resta" -> (numero1.toDouble() - numero2.toDouble()).toString()
            "multiplicacion" -> (numero1.toDouble() * numero2.toDouble()).toString()
            "division" -> (numero1.toDouble() / numero2.toDouble()).toString()
            else -> {
                "ERROR"
            }
        }


        resultadoTemporal = decimalFormat(resultadoTemporal.toDouble()).toString()


        return resultadoTemporal



    }


}