package com.example.calculadora_Avanzada


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.calculadora.R
import kotlin.math.cos

class MainActivity : ComponentActivity() {


    var numero1 = ""
    var numero2 = ""
    var operador = ""
    var ingresandoSegundoNumero = false
    var resultadoTemporal = ""
    var operadorUsado = ""
    var operadorAvanzado = ""
    var calcular = CalcularDiferentesOperaciones()

    lateinit var operacion: TextView
    lateinit var resultado: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        operacion = this.findViewById(R.id.operacion)
        resultado = this.findViewById(R.id.resultado)
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

        } else if ((operacion.text.contains("%")  && numeros.contains(button.text.toString()) )) {
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
            resultadoTemporal = numero1 + operadorUsado + numero2
            operacion.text = resultadoTemporal

        }

    }

    // Función  para mostrar cada vez que se pulsa un botón de operador suma, resta, multiplicación o División
    @SuppressLint("SuspiciousIndentation")
    fun pulsarOperacion(view: View) {
        val button: Button = view as Button

        operadorUsado = button.text.toString()
        operador = button.tag.toString()

        when {

            (resultadoTemporal.endsWith(operadorUsado) && button.text.toString() == operadorUsado) -> return
            (resultadoTemporal.endsWith(operadorUsado) && button.text.toString() !== operadorUsado) -> {
                operador = button.tag.toString()
                resultadoTemporal = numero1 + button.text.toString()
                operacion.text = resultadoTemporal
                return
            }

            (operador.isEmpty() || operacion.text == numero1 || resultado.text == numero1) -> {
                resultadoTemporal = numero1 + operadorUsado
                ingresandoSegundoNumero = true
                operacion.text = resultadoTemporal
            }

            (button.text.toString() == operadorUsado && operacion.text == "0") -> {
                numero1 = "0"
                resultadoTemporal = numero1 +  operadorUsado
                operacion.text = resultadoTemporal
                ingresandoSegundoNumero = true
            }

            else -> {
                igual(view)
                pulsarOperacion(view)
            }
        }
    }

    // Función al pulsar botón igual
    fun igual(view: View) {
        val button: Button = view as Button

        if (resultadoTemporal.endsWith(operadorUsado) && button.text.toString() == (operadorUsado))
        { return }


        resultadoTemporal = calcular.operacionBasica(numero1, numero2, operador)
        resultado.text = resultadoTemporal
        numero1 = resultadoTemporal
        numero2 = ""
        ingresandoSegundoNumero = false


    }


    // Botón para colocar todos los valores a 0
    fun resetAC(view: View) {
        val button: Button = view as Button
        resetearValores()


    }

    // Función para calcular el porcentaje
    fun porcentaje(view: View) {
        val button: Button = view as Button

        numero2 = numero2.replace(",", ".")

        if ((operacion.text == numero1) || ((button.text.toString() == "%")  && operacion.text == "0")) {
            resetearValores()
        }

        when {

            resultadoTemporal.endsWith(operadorUsado) -> {
                numero2 = ((numero1.toDouble() / 100).toString().replace(".", ","))
                resultadoTemporal = numero1 + operadorUsado + numero2
                operacion.text = resultadoTemporal
                resultado.text = numero2
                ingresandoSegundoNumero = false

            }
            operacion.text == resultadoTemporal -> {
                numero2 = ((numero2.toDouble() / 100).toString().replace(".", ","))
                resultadoTemporal = numero1 + operadorUsado + numero2
                operacion.text = resultadoTemporal
                resultado.text = numero2
                ingresandoSegundoNumero = false
            }

        }


    }

    // Función al pulsar cualquier operación avazada ya sea potencia, raiz cuadrada, seno y coseno (operaciones avanzadas)

    fun pulsarOperacionAvanzada(view: View) {
        val button: Button = view as Button
        val operadorsinCoseno: List<String> = listOf("sqr", "sqrt", "sin")
        operadorAvanzado = button.tag.toString()

        if ((resultadoTemporal.contains(operadorAvanzado) && button.text.toString() == operadorAvanzado) ||
            (operadorsinCoseno.contains(operadorAvanzado) && resultado.text == "0" && operacion.text == "0")) {
            return

            } else if (operadorAvanzado == "cos" && resultado.text == "0" && operacion.text == "0") {

                numero1 = "0"
                resultadoTemporal = "$operadorAvanzado($numero1)"
                operacion.text = resultadoTemporal
                resultadoTemporal= calcular.OperacionAvanzada(numero1, operadorAvanzado)
                resultado.text = resultadoTemporal
                resultadoTemporal = numero1
                ingresandoSegundoNumero = false


            }
            numero1 = numero1.replace(",", ".")
            numero2 = numero2.replace(",", ".")

            when {

                (operacion.text == numero1) -> {

                    resultadoTemporal = "$operadorAvanzado($numero1)"
                    operacion.text = resultadoTemporal
                    ingresandoSegundoNumero = false
                    resultadoTemporal = calcular.OperacionAvanzada(numero1, operadorAvanzado)
                    resultado.text = resultadoTemporal
                    numero1 = resultadoTemporal
                }

                (resultadoTemporal.endsWith(operadorUsado)) -> {
                    resultadoTemporal = "$numero1$operadorUsado$operadorAvanzado($numero1)"
                    operacion.text = resultadoTemporal
                    resultadoTemporal = calcular.OperacionAvanzada(numero1, operadorAvanzado)
                    resultado.text = resultadoTemporal
                    numero2 = resultadoTemporal
                    ingresandoSegundoNumero = false


                }

                (numero2.isNotEmpty() && resultado.text != numero1) -> {
                    resultadoTemporal = "$numero1$operadorUsado$operadorAvanzado($numero2)";
                    operacion.text = resultadoTemporal
                    resultadoTemporal = calcular.OperacionAvanzada(numero2, operadorAvanzado)
                    resultado.text = resultadoTemporal
                    numero2 = resultadoTemporal
                    ingresandoSegundoNumero = false

                }

            }
        }
    }







