package com.example.calculadora_Avanzada


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import com.example.calculadora.R
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.toString


class MainActivity : ComponentActivity() {


    var numero1 = ""
    var numero2 = ""
    var operador = ""
    var ingresandoSegundoNumero = false
    var resultadoTemporal = ""
    var operadorUsado = ""
    var operadorAvanzado = ""


    lateinit var operacion: TextView
    lateinit var resultado: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        operacion = this.findViewById(R.id.operacion)
        resultado = this.findViewById(R.id.resultado)


    }

    // Función para hacer operaciones básicas como suma, resta, multiplicación y división


    fun operacionBasica() {


        numero1 = numero1.replace(",", ".")
        numero2 = numero2.replace(",", ".")



        when (operador) {

            "suma" -> resultadoTemporal = (numero1.toDouble() + numero2.toDouble()).toString()
            "resta" -> resultadoTemporal = (numero1.toDouble() - numero2.toDouble()).toString()
            "multiplicacion" -> resultadoTemporal =
                (numero1.toDouble() * numero2.toDouble()).toString()

            "division" -> resultadoTemporal = (numero1.toDouble() / numero2.toDouble()).toString()
        }


        resultadoTemporal =
            "=" + decimalFormat(resultadoTemporal.toDouble()).toString().replace(".", ",")


        resultadoTemporal = resultadoTemporal.filter { it != '=' }
        numero1 = resultadoTemporal
        numero2 = ""
        ingresandoSegundoNumero = false


    }

    fun decimalFormat(numero: Double): String {
        return String.format("%.2f", numero)

    }
    // Función para restaurar todos los valores a 0

    fun resetearValores() {

        operacion.text = "0"
        resultado.text = "0"
        resultadoTemporal = ""
        numero2 = ""
        numero1 = ""
        operador = ""
        ingresandoSegundoNumero = false

    }

    // función para revisar que todos los valores estén correctamente ingresados antes de ejecutar cualquier operación básica


    // función para añadir el número 1
    fun addNumero1() {


        operacion.text = numero1
        resultado.text = "0"
        operador = ""


    }


    // Botón para mostrar cada vez que se pulsa un número

    fun pulsarNumero(view: View) {
        val button: Button = view as Button
        val numeros: List<String> = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0")

        if (ingresandoSegundoNumero) {
            numero2 += button.text.toString()
            resultadoTemporal = numero1 + operadorUsado + numero2
            operacion.text = resultadoTemporal

            if (numero2.count() { it == ',' } > 0) {
                return
            }

        } else if ((resultado.text.startsWith("=") && numeros.contains(button.text.toString()))) {

            addNumero1()

        } else if ((operacion.text.contains("%") && numeros.contains(button.text.toString())) ||
            (operacion.text.contains("sqr") && numeros.contains(button.text.toString())) ||
            (operacion.text.contains("√") && numeros.contains(button.text.toString())) ||
            (operacion.text.contains("sen") && numeros.contains(button.text.toString())) ||
            (operacion.text.contains("cos") && numeros.contains(button.text.toString()))
        ) {
            resetearValores()
            numero1 += button.text.toString()
            addNumero1()

        } else {
            numero1 = numero1 + button.text.toString()
            addNumero1()

        }
    }

    fun validarComa(view: View) {
        val button: Button = view as Button
        val coma = button.text.toString()


        if (numero1 == "") {
            numero1 = "0$coma"
            operacion.text = numero1
        }

        if (!numero1.contains(",")) {

            numero1 += coma
            operacion.text = numero1
        }


        if (ingresandoSegundoNumero && !numero2.contains(",")) {

            numero2 += coma
            operacion.text = resultadoTemporal

        }

    }

    // Función  para mostrar cada vez que se pulsa un botón de operador suma, resta, multiplicación o División
    @SuppressLint("SuspiciousIndentation")
    fun pulsarOperacion(view: View) {
        val button: Button = view as Button

        operadorUsado = button.text.toString()
        operador = button.tag.toString()

        if ((button.text.toString() == operadorUsado && operacion.text == "0")) {

            numero1 = "0"

            numero1 += operadorUsado
            operacion.text = numero1
            ingresandoSegundoNumero = true


        }

        if (resultadoTemporal.endsWith(operadorUsado) && button.text.toString() == operadorUsado) {
            return

        } else if (resultadoTemporal.endsWith(operadorUsado) && button.text.toString() !== operadorUsado) {
            operador = button.tag.toString()
            resultadoTemporal = numero1 + button.text.toString()
            operacion.text = resultadoTemporal
            return
        }


        if (operador.isEmpty() || operacion.text == numero1 || resultado.text == numero1 ) {

            resultadoTemporal = numero1 + operadorUsado
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

        if (resultadoTemporal.endsWith("+") && button.text.toString() == ("=") ||
            resultadoTemporal.endsWith("-") && button.text.toString() == ("=") ||
            resultadoTemporal.endsWith("*") && button.text.toString() == ("=") ||
            resultadoTemporal.endsWith("/") && button.text.toString() == ("=")
        ) {
            return
        }

        operacionBasica()
        resultado.text = resultadoTemporal

    }


    // Botón para colocar todos los valores a 0
    fun resetAC(view: View) {
        val button: Button = view as Button
        resetearValores()
        operacion.text = "0"

    }

    // Función para calcular el porcentaje
    fun porcentaje(view: View) {
        val button: Button = view as Button

        numero2 = numero2.replace(",", ".")



        if (ingresandoSegundoNumero == false) {
            operacion.text = "0"
            resultadoTemporal = ""
            numero1 = ""

        } else if (operacion.text == resultadoTemporal) {
            resultadoTemporal = "$numero1$operadorUsado$numero2%"
            operacion.text = resultadoTemporal

            numero2 = ((numero2.toDouble() / 100)).toString()
            numero2 = numero2.toString().replace(".", ",")
            numero1 = numero2
            resultado.text = numero1
            resultadoTemporal = numero1
            ingresandoSegundoNumero = false

        }

    }

    // Ejecuta todo tipo de operaciones avanzadas
    fun calcularOperacionAvanzada(numero: String): String {


        resultadoTemporal = when (operadorAvanzado) {
            "sqr" -> (numero.toDouble().pow(2).toString())
            "sqrt" -> (sqrt(numero.toDouble()).toString())
            "sin" -> (sin(numero.toDouble()).toString())
            "cos" -> (cos(numero.toDouble()).toString())

            else -> {
                numero
            }
        }

        return decimalFormat(resultadoTemporal.toDouble()).toString().replace(".", ",")

    }


    // Función para calcular potencia, raiz cuadrada, seno y coseno (operaciones avanzadas)

    fun pulsarOperacionAvanzada(view: View) {
        val button: Button = view as Button
        val operadorsinCoseno: List<String> = listOf("sqr", "sqrt", "sin")
        operadorAvanzado = button.tag.toString()

        if (resultadoTemporal.contains(operadorAvanzado) && button.text.toString() == operadorAvanzado) {
            return
        }
            if (operadorsinCoseno.contains(operadorAvanzado) && resultado.text == "0" && operacion.text == "0") {
                return


            } else if (operadorAvanzado == "cos" && resultado.text == "0" && operacion.text == "0") {

                numero1 = "0"
                resultadoTemporal = "$operadorAvanzado($numero1)"
                operacion.text = resultadoTemporal
                numero1 = (cos(numero1.toDouble()).toString())
                resultado.text = numero1
                ingresandoSegundoNumero = false


            }
            numero1 = numero1.replace(",", ".")
            numero2 = numero2.replace(",", ".")

            when {

                (operacion.text == numero1) -> {

                    resultadoTemporal = "$operadorAvanzado($numero1)"
                    operacion.text = resultadoTemporal
                    ingresandoSegundoNumero = false
                    resultadoTemporal = calcularOperacionAvanzada(numero1)
                    resultado.text = resultadoTemporal
                    numero1 = resultadoTemporal
                }

                (resultadoTemporal.endsWith(operadorUsado)) -> {
                    resultadoTemporal = "$numero1$operadorUsado$operadorAvanzado($numero1)"
                    operacion.text = resultadoTemporal
                    resultadoTemporal = calcularOperacionAvanzada(numero1)
                    resultado.text = resultadoTemporal
                    numero2 = resultadoTemporal


                }

                (numero2.isNotEmpty() && resultado.text != numero1) -> {
                    resultadoTemporal = "$numero1$operadorUsado$operadorAvanzado($numero2)";
                    operacion.text = resultadoTemporal
                    resultadoTemporal = calcularOperacionAvanzada(numero2)
                    resultado.text = resultadoTemporal
                    numero2 = resultadoTemporal

                }

            }
        }
    }







