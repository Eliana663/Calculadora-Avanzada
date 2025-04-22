package com.example.calculadora_Avanzada


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.calculadora.R
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.text.format


class MainActivity : ComponentActivity() {


    var numero1 = ""
    var numero2 = ""
    var operador = ""
    var ingresandoSegundoNumero = false
    var resultadoTemporal = ""
    var punto = ""
    var operadorUsado = ""

    val numeros: List<String> = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0")

    lateinit var operacion: TextView
    lateinit var resultado: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        operacion = this.findViewById(R.id.operacion)
        resultado = this.findViewById(R.id.resultado)


    }


    // Botón para mostrar cada vez que se pulsa un número

    fun pulsarNumero(view: View) {
        val button: Button = view as Button

        if (ingresandoSegundoNumero) {
                numero2 += button.text.toString()
                resultadoTemporal += button.text.toString()
                operacion.text = resultadoTemporal
            }


            else {

                numero1= numero1 + button.text.toString()

                operacion.text = numero1
                resultadoTemporal = numero1
            }


        if (resultado.text.startsWith("=") && numeros.contains(button.text.toString()) && operacion.text == numero1 + operador + numero2) {
            numero1 = ""
            numero1 = numero1 + button.text.toString()
            operacion.text = numero1
            resultado.text = "0"
            resultadoTemporal=numero1
            operador = ""


        }
        }
// Función para agregar comas.
        fun agregarComa (view: View) {
        val button: Button = view as Button

            punto = button.text.toString()

            if (!numero1.contains(",")) {

                numero1 += button.text.toString()
                operacion.text = numero1
            }

            if (ingresandoSegundoNumero && !numero2.contains(","))  {


                resultadoTemporal += button.text.toString()
                numero2 += button.text.toString()

                operacion.text = resultadoTemporal


            }


    }
    // Función  para mostrar cada vez que se pulsa un botón de operador suma, resta, multiplicación o División
        @SuppressLint("SuspiciousIndentation")
        fun pulsarOperacion(view: View) {
            val button: Button = view as Button

                    if (resultadoTemporal.endsWith("+") && button.text.toString() == ("+")||
                        resultadoTemporal.endsWith("-") && button.text.toString() == ("-") ||
                        resultadoTemporal.endsWith("*") && button.text.toString() == ("*")||
                        resultadoTemporal.endsWith("/") && button.text.toString() == ("/")) {
                        return
                    } else if (resultadoTemporal.endsWith("+") && button.text.toString() !== ("+") ||
                        resultadoTemporal.endsWith("-") && button.text.toString() !== ("-") ||
                        resultadoTemporal.endsWith("*") && button.text.toString() !== ("*") ||
                        resultadoTemporal.endsWith("/") && button.text.toString() !== ("/")) {
                        operador = button.tag.toString()
                        resultadoTemporal = numero1 + button.text.toString()
                        operacion.text = resultadoTemporal
                        return
                    }



                    if (operador.isEmpty() || resultadoTemporal == numero1 ) {
                        operadorUsado = button.text.toString()
                        operador = button.tag.toString()

                        resultadoTemporal = resultadoTemporal + operadorUsado

                        ingresandoSegundoNumero = true
                        operacion.text = resultadoTemporal

                    } else {
                        igual(view)
                        pulsarOperacion(view)
                    }



        }
// Función para el botón de igual (resultado operación)
        fun igual(view: View) {
            val button: Button = view as Button



           if  (resultadoTemporal.endsWith("+") && button.text.toString() == ("=")||
               resultadoTemporal.endsWith("-") && button.text.toString() == ("=") ||
               resultadoTemporal.endsWith("*") && button.text.toString() == ("=")||
               resultadoTemporal.endsWith("/") && button.text.toString() == ("=")) {
               return
           }

            numero1 = numero1.replace(",", ".")
            numero2 = numero2.replace(",", ".")



            when (operador ){

                "suma" -> resultadoTemporal = (numero1.toDouble() + numero2.toDouble()).toString()
                "resta" -> resultadoTemporal = (numero1.toDouble() - numero2.toDouble()).toString()
                "multiplicacion" -> resultadoTemporal = (numero1.toDouble() * numero2.toDouble()).toString()
                "division" -> resultadoTemporal = (numero1.toDouble() / numero2.toDouble()).toString()
            }

            fun decimalFormat(numero: Double): String {
                return  String.format("%.6f", numero)

            }
            resultadoTemporal = "=" + decimalFormat(resultadoTemporal.toDouble()).toString().trimEnd('0').replace(".", ",")
            if (resultadoTemporal.endsWith(",")) {
                resultadoTemporal = resultadoTemporal.replace(",","")

            }

            resultado.text =  resultadoTemporal
            resultadoTemporal = resultadoTemporal.filter { it != '=' }
            numero1 = resultadoTemporal



            numero2 = ""
            ingresandoSegundoNumero = false


        }


// Botón para reiniciar absolutamente
        fun resetAC(view: View) {
            val button: Button = view as Button
            operacion.text = "0"
            resultado.text = "0"
            resultadoTemporal=""
            numero1 = ""
            numero2 = ""
            operador = ""
            punto = ""
            ingresandoSegundoNumero = false

        }
// Función para calcular el porcentaje
    fun porcentaje(view: View) {
        val button: Button = view as Button

        numero2 = numero2.replace(",", ".")

        if (resultadoTemporal == numero1) {
            operacion.text = "0";
            resultadoTemporal = ""
            numero1 = ""

        }

        if (operacion.text == resultadoTemporal) {
            numero2 = ((numero2.toDouble()/100)*numero2.toDouble()).toString()
            numero2 = numero2.toString().replace(".", ",")
            resultadoTemporal = numero1 + operadorUsado + numero2
            operacion.text = resultadoTemporal


        }

    }
// Función para calcular la potencia (sólo al cuadrado)
    fun potencia(view: View) {
        val button: Button = view as Button
        var sqr = ""
        numero1 = numero1.replace(",", ".")
        numero2 = numero2.replace(",", ".")

        if (button.text.toString() == "x²" && resultado.text == "0" && operacion.text == "0") {
            return
        }

        if (resultadoTemporal == numero1) {

            sqr = "sqr($numero1)"
            operacion.text = sqr
            numero1 = (numero1.toDouble().pow(2).toString())
            resultadoTemporal = numero1
            resultado.text = numero1
        }

        if (operacion.text == resultadoTemporal) {
            sqr = "sqr($numero2)"
            numero2 = (numero2.toDouble().pow(2).toString())
            resultadoTemporal = numero1 + operadorUsado + sqr
            operacion.text = resultadoTemporal
            resultadoTemporal = numero1 + operadorUsado + numero2

        }

    }
// Función para calcular la raiz Cuadrada de un número
    fun raizCuadrada (view: View) {
        val button: Button = view as Button
        var raiz = ""
        numero1 = numero1.replace(",", ".")
        numero2 = numero2.replace(",", ".")

        if (button.text.toString() == "√" && resultado.text == "0" && operacion.text == "0") {
            return
        }

        if (resultadoTemporal == numero1) {

            raiz = "√($numero1)"
            operacion.text = raiz
            numero1 = (sqrt(numero1.toDouble()).toString())
            resultadoTemporal = numero1
            resultado.text = numero1
        }

        if (operacion.text == resultadoTemporal) {
            raiz = "√($numero2)"
            numero2 = (sqrt(numero2.toDouble()).toString())
            resultadoTemporal = numero1 + operadorUsado + raiz
            operacion.text = resultadoTemporal
            resultadoTemporal = numero1 + operadorUsado + numero2

        }
    }
// Función para calcular el Seno
    fun seno (view: View) {

        val button: Button = view as Button
        var seno = ""
        numero1 = numero1.replace(",", ".")
        numero2 = numero2.replace(",", ".")

        if (button.tag.toString() == "S" && resultado.text == "0" && operacion.text == "0") {
            return
        }

        if (resultadoTemporal == numero1) {

            seno= "sen($numero1)"
            operacion.text = seno
            numero1 = (sin(numero1.toDouble()).toString())
            resultadoTemporal = numero1
            resultado.text = numero1
        }

        if (operacion.text == resultadoTemporal) {
            seno = "sen($numero2)"
            numero2 = (sin(numero2.toDouble()).toString())
            resultadoTemporal = numero1 + operadorUsado + seno
            operacion.text = resultadoTemporal
            resultadoTemporal = numero1 + operadorUsado + numero2

        }



    }
// Función para calcular el Coseno (ojo sólo si entramos el número en radianes)
    fun coseno (view: View) {

        val button: Button = view as Button
        var coseno = ""
        numero1 = numero1.replace(",", ".")
        numero2 = numero2.replace(",", ".")

        if (button.tag.toString() == "C" && resultado.text == "0" && operacion.text == "0") {
            numero1 = "0"
            coseno= "cos($numero1)"
            operacion.text = coseno
            numero1 = (cos(numero1.toDouble()).toString())
            resultado.text = numero1
        }

        if (resultadoTemporal == numero1) {

            coseno= "cos($numero1)"
            operacion.text = coseno
            numero1 = (cos(numero1.toDouble()).toString())
            resultadoTemporal = numero1
            resultado.text = numero1
        }

        if (operacion.text == resultadoTemporal) {
            coseno = "cos($numero2)"
            numero2 = (cos(numero2.toDouble()).toString())
            resultadoTemporal = numero1 + operadorUsado + coseno
            operacion.text = resultadoTemporal
            resultadoTemporal = numero1 + operadorUsado + numero2

        }

    }
    }


