package com.example.calculadora_Avanzada


import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.calculadora.R
import java.text.DecimalFormat

class MainActivity : ComponentActivity() {


    var numero1 = ""
    var numero2 = ""
    var operador = ""
    var ingresandoSegundoNumero = false
    var resultadoSuperior = ""
    var resultadoInferior = ""
    var operadorPantalla = ""
    var operadorAvanzado = ""
    var calcular = CalcularDiferentesOperaciones()
    val dec = DecimalFormat("#,00")
    var ingresandoOperacionAvanzada = false

    lateinit var pantallaSuperior: TextView
    lateinit var pantallaInferior: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        pantallaSuperior = this.findViewById(R.id.operacion)
        pantallaInferior = this.findViewById(R.id.resultado)
    }

    // Función para restaurar  valores a 0

    fun resetearValores() {

        pantallaSuperior.text = "0"
        pantallaInferior.text = "0"
        numero1 = ""
        numero2 = ""
        operador = ""
        ingresandoSegundoNumero = false
        resultadoSuperior = ""
        resultadoInferior = ""
        operadorPantalla = ""
        operadorAvanzado = ""
    }


    fun actualizarPantallaSuperior() {


        resultadoSuperior = if (ingresandoSegundoNumero || ingresandoOperacionAvanzada) {
            numero1 + operadorPantalla + numero2
        } else if (numero2 == "" && resultadoInferior !== "") {
            numero1 + operadorPantalla
        } else {
            numero1

        }

        if (resultadoSuperior.contains(".")) {
            resultadoSuperior = resultadoSuperior.replace(".", ",")
        }
        pantallaSuperior.text = resultadoSuperior


    }

    fun actualizarPantallaInferior() {
        resultadoInferior = resultadoInferior.replace(".", ",")
        pantallaInferior.text = resultadoInferior
    }


    // Botón para mostrar cada vez que se pulsa un número

    fun pulsarNumero(view: View) {
        val button: Button = view as Button

        if (ingresandoSegundoNumero) {
            numero2 += button.text.toString()
        } else {
            numero1 = numero1 + button.text.toString()

        }
        actualizarPantallaSuperior();
    }

    fun validarComa(numero: String, coma: String): String {
        var result = ""
        if (numero == "") {
            result = numero + "0$coma"
        } else if (!numero.contains(".")) {
            result = numero + coma
        } else {
            result = numero
        }
        return result;
    }

    fun pulsarComa(view: View) {
        val button: Button = view as Button
        val coma = button.tag.toString()



        if (ingresandoSegundoNumero) {
            numero2 = validarComa(numero2, coma)
        } else {
            numero1 = validarComa(numero1, coma)
        }


        actualizarPantallaSuperior()
    }


    fun pulsarOperacion(view: View) {
        val button: Button = view as Button
        operadorPantalla = button.text.toString()
        operador = button.tag.toString()

        if (!ingresandoSegundoNumero) {
            if (numero1 == "") {
                numero1 = "0"
            }
            ingresandoSegundoNumero = true
        } else if (numero2 != "") {
            calculoContinuo() // continuo
        }

        actualizarPantallaSuperior()
    }


    // Función al pulsar botón igual
    fun igual(view: View) {
        val button: Button = view as Button



        if (!ingresandoSegundoNumero) {
            return
        } else if (numero2 == "") {
            return
        } else {
            ingresandoSegundoNumero = false
            calculoContinuo()
        }
    }


    fun calculoContinuo() {
        resultadoInferior = calcular.operacionBasica(numero1, numero2, operador)
        numero1 = resultadoInferior
        actualizarPantallaSuperior()
        actualizarPantallaInferior()
        numero2 = ""
    }


    // Botón para colocar valores a 0
    fun resetAC(view: View) {
        val button: Button = view as Button
        resetearValores()


    }

    // Función para calcular el porcentaje
    fun pulsarOperacionAvanzada(view: View) {
        val button: Button = view as Button
        operadorAvanzado = button.tag.toString()

        if (numero1 == "%") {
            resetearValores()
        }

        when {
            (!ingresandoSegundoNumero) && (ingresandoOperacionAvanzada) -> numero1 =
                calcular.OperacionAvanzada(numero1, operadorAvanzado)

            (ingresandoSegundoNumero) && (ingresandoOperacionAvanzada) -> numero2 =
                calcular.OperacionAvanzada(numero2, operadorAvanzado)
        }

    }
}
//
//        when {
//
//            resultadoSuperior.endsWith(operadorPantalla) -> {
//                numero2 = ((numero1.toDouble() / 100).toString().replace(".", ","))
//                resultadoSuperior = numero1 + operadorPantalla + numero2
//                pantallaSuperior.text = resultadoSuperior
//                pantallaInferior.text = numero2
//                ingresandoSegundoNumero = false
//
//            }
//            pantallaSuperior.text == resultadoSuperior -> {
//                numero2 = ((numero2.toDouble() / 100).toString().replace(".", ","))
//                resultadoSuperior = numero1 + operadorPantalla + numero2
//                pantallaSuperior.text = resultadoSuperior
//                pantallaInferior.text = numero2
//                ingresandoSegundoNumero = false
//            }
//
////        }
//
//
//    }

    // Función al pulsar cualquier operación avazada ya sea potencia, raiz cuadrada, seno y coseno (operaciones avanzadas)
//
//    fun pulsarOperacionAvanzada(view: View) {
//        val button: Button = view as Button
//        val operadorsinCoseno: List<String> = listOf("sqr", "sqrt", "sin")
//        operadorAvanzado = button.tag.toString()
//
//        if ((resultadoSuperior.contains(operadorAvanzado) && button.text.toString() == operadorAvanzado) ||
//            (operadorsinCoseno.contains(operadorAvanzado) && pantallaInferior.text == "0" && pantallaSuperior.text == "0")) {
//            return
//
//            } else if (operadorAvanzado == "cos" && pantallaInferior.text == "0" && pantallaSuperior.text == "0") {
//
//                numero1 = "0"
//                resultadoSuperior = "$operadorAvanzado($numero1)"
//                pantallaSuperior.text = resultadoSuperior
//                resultadoSuperior= calcular.OperacionAvanzada(numero1, operadorAvanzado)
//                pantallaInferior.text = resultadoSuperior
//                resultadoSuperior = numero1
//                ingresandoSegundoNumero = false
//
//
//            }
//            numero1 = numero1.replace(",", ".")
//            numero2 = numero2.replace(",", ".")
//
//            when {
//
//                (pantallaSuperior.text == numero1) -> {
//
//                    resultadoSuperior = "$operadorAvanzado($numero1)"
//                    pantallaSuperior.text = resultadoSuperior
//                    ingresandoSegundoNumero = false
//                    resultadoSuperior = calcular.OperacionAvanzada(numero1, operadorAvanzado)
//                    pantallaInferior.text = resultadoSuperior
//                    numero1 = resultadoSuperior
//                }
//
//                (resultadoSuperior.endsWith(operadorPantalla)) -> {
//                    resultadoSuperior = "$numero1$operadorPantalla$operadorAvanzado($numero1)"
//                    pantallaSuperior.text = resultadoSuperior
//                    resultadoSuperior = calcular.OperacionAvanzada(numero1, operadorAvanzado)
//                    pantallaInferior.text = resultadoSuperior
//                    numero2 = resultadoSuperior
//                    ingresandoSegundoNumero = false
//
//
//                }
//
//                (numero2.isNotEmpty() && pantallaInferior.text != numero1) -> {
//                    resultadoSuperior = "$numero1$operadorPantalla$operadorAvanzado($numero2)";
//                    pantallaSuperior.text = resultadoSuperior
//                    resultadoSuperior = calcular.OperacionAvanzada(numero2, operadorAvanzado)
//                    pantallaInferior.text = resultadoSuperior
//                    numero2 = resultadoSuperior
//                    ingresandoSegundoNumero = false
//
//                }
//
//            }
//        }
//    }
//






