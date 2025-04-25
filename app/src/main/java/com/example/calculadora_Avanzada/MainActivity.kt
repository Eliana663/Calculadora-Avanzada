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
        resultadoTemporal = numero1
        resultado.text = "0"
        operador = ""


    }


    // Botón para mostrar cada vez que se pulsa un número

    fun pulsarNumero(view: View) {
        val button: Button = view as Button
        val numeros: List<String> = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0")

        if (ingresandoSegundoNumero) {
            numero2 += button.text.toString()
            resultadoTemporal += button.text.toString()
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

    fun validarComa (view: View) {
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


        if (ingresandoSegundoNumero && !numero2.contains(","))  {

            resultadoTemporal += coma
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
            resultadoTemporal = numero1
            resultadoTemporal = resultadoTemporal + operadorUsado
            operacion.text = resultadoTemporal
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


        if (operador.isEmpty() || resultadoTemporal == numero1) {

            resultadoTemporal += operadorUsado
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



        if (resultadoTemporal == numero1) {
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

    // Función para calcular potencia, raiz cuadrada, seno y coseno (operaciones avanzadas)

    fun pulsarOperacionAvanzada(view: View) {
        val button: Button = view as Button
        numero1 = "0"
        val potenciaNumero1 = "sqr($numero1)"
        val potenciaNumero2 = "sqr($numero2)"
        val raizNumero1 = "√($numero1)"
        val raizNumero2 = "√($numero2)"
        val senNumero1 = "sen($numero1)"
        val senNumero2 = "sen($numero2)"
        val cosNumero1 = "cos($numero1)"
        val cosNumero2 = "cos($numero2)"
        val operadorsinCoseno: List<String> = listOf("sqr", "sqrt", "sin")
        val operadoresAvanzados = listOf("sqr", "sqrt", "sin", "cos")
        var operadorAvanzado = button.tag.toString()




        if (operadorsinCoseno.contains(operadorAvanzado) && resultado.text == "0" && operacion.text == "0") {
            return

        } else if (operadorAvanzado == "cos" && resultado.text == "0" && operacion.text == "0") {

            operacion.text = cosNumero1
            numero1 = (cos(numero1.toDouble()).toString())
            resultado.text = numero1
            ingresandoSegundoNumero = false
            resultadoTemporal = numero1

    }

//        if (resultadoTemporal == numero1) {
//
//            operacion.text = ""
//            sqr = "sqr($numero1)"
//
//        operacion.text = sqr
//        numero1 =  numero1.replace(",", ".")
//        numero1 = (numero1.toDouble().pow(2).toString())
//        numero1 =  numero1.replace(".", ",")
//        resultadoTemporal = numero1
//        resultado.text = numero1
//        ingresandoSegundoNumero = false
//
//
//
//        }


    }


// Función para calcular la potencia (sólo al cuadrado)
    fun potencia(view: View) {
    val button: Button = view as Button
    var sqr = ""




//    if (button.text.toString() == "x²" && resultado.text == "0" && operacion.text == "0") {
//        return
//    }

    if (resultadoTemporal == numero1) {

        sqr = "sqr($numero1)"

        operacion.text = sqr
        numero1 =  numero1.replace(",", ".")
        numero1 = (numero1.toDouble().pow(2).toString())
        numero1 =  numero1.replace(".", ",")
        resultadoTemporal = numero1
        resultado.text = numero1
        ingresandoSegundoNumero = false

    } else if (resultadoTemporal.endsWith(operadorUsado) )
     {

        sqr = "sqr($numero1)"

        resultadoTemporal = numero1 + operadorUsado + sqr
        operacion.text = resultadoTemporal
        numero1 =  numero1.replace(",", ".")
        resultadoTemporal = (numero1.toDouble().pow(2).toString())
        numero1 =  numero1.replace(".", ",")
        resultado.text = resultadoTemporal
        numero2 = resultadoTemporal
        operacionBasica()
        ingresandoSegundoNumero = false

    } else if (operacion.text == resultadoTemporal ) {

        sqr = "sqr($numero2)"

        resultadoTemporal = numero1 + operadorUsado + sqr
        operacion.text = resultadoTemporal
        numero2 =  numero2.replace(",", ".")
        resultadoTemporal = (numero2.toDouble().pow(2).toString())
        numero2 =  numero2.replace(".", ",")
        resultado.text = resultadoTemporal
        numero2 = resultadoTemporal
        operacionBasica()
        ingresandoSegundoNumero = false





    }
}
    // Función para calcular la raiz Cuadrada de un número
    fun raizCuadrada(view: View) {
        val button: Button = view as Button
        var raiz = ""
        numero1 = numero1.replace(",", ".")
        numero2 = numero2.replace(",", ".")


//        if (button.text.toString() == "√" && resultado.text == "0" && operacion.text == "0") {
//            return
//        }

        if (resultadoTemporal.endsWith("+") || resultadoTemporal.endsWith("-") ||
            resultadoTemporal.endsWith("*") || resultadoTemporal.endsWith("/")
        ) {

            raiz = "√($numero1)"

            resultadoTemporal = numero1 + operadorUsado + raiz
            operacion.text = resultadoTemporal
            resultadoTemporal = (numero2.toDouble() + sqrt(numero1.toDouble())).toString()
            resultado.text = resultadoTemporal
            resultadoTemporal = numero1
            ingresandoSegundoNumero=false

        }

        if (resultadoTemporal == numero1) {

            raiz = "√($numero1)"
            operacion.text = raiz
            numero1 = (sqrt(numero1.toDouble()).toString())
            resultadoTemporal = numero1
            resultado.text = numero1
            ingresandoSegundoNumero= false
        }

        if (operacion.text == resultadoTemporal ) {

            raiz = "√($numero2)"
            resultadoTemporal = numero1 + operadorUsado + raiz
            numero2 = (sqrt(numero2.toDouble()).toString())

            operacion.text = resultadoTemporal
            resultado.text = numero2
            ingresandoSegundoNumero= false


        }


    }

    // Función para calcular el Seno
    fun seno(view: View) {

        val button: Button = view as Button
        var seno = ""
        numero1 = numero1.replace(",", ".")
        numero2 = numero2.replace(",", ".")


        if (resultadoTemporal.endsWith("+") || resultadoTemporal.endsWith("-") ||
            resultadoTemporal.endsWith("*") || resultadoTemporal.endsWith("/")
        ) {

            seno = "sen($numero2)"

            resultadoTemporal = numero1 + operadorUsado + seno
            operacion.text = resultadoTemporal
            resultadoTemporal = (numero1.toDouble() + sin(numero1.toDouble())).toString()
            resultado.text = resultadoTemporal
            resultadoTemporal = numero1
            ingresandoSegundoNumero=false

        }

//        if (button.tag.toString() == "S" && resultado.text == "0" && operacion.text == "0") {
//            numero1 = "0"
//            seno = "cos($numero1)"
//            operacion.text = seno
//            numero1 = (cos(numero1.toDouble()).toString())
//            resultado.text = numero1
//            ingresandoSegundoNumero= false
//        }

        if (resultadoTemporal == numero1) {

            seno = "sen($numero1)"
            operacion.text = seno
            numero1 = (sin(numero1.toDouble()).toString())
            resultadoTemporal = numero1
            resultado.text = numero1
            ingresandoSegundoNumero= false
        }

        if (operacion.text == resultadoTemporal) {
            seno = "sen($numero2)"
            resultadoTemporal = numero1 + operadorUsado + seno
            operacion.text = resultadoTemporal
            numero2 = (sin(numero2.toDouble()).toString())
            operacion.text = resultadoTemporal
            resultadoTemporal = numero1 + operadorUsado + numero2
            ingresandoSegundoNumero= false

        }



    }

    // Función para calcular el Coseno (ojo sólo si entramos el número en radianes)
    fun coseno(view: View) {

        val button: Button = view as Button
        var coseno = ""
        numero1 = numero1.replace(",", ".")
        numero2 = numero2.replace(",", ".")

        if (resultadoTemporal.endsWith("+") || resultadoTemporal.endsWith("-") ||
            resultadoTemporal.endsWith("*") || resultadoTemporal.endsWith("/")
        ) {

            coseno = "cos($numero2)"

            resultadoTemporal = numero1 + operadorUsado + coseno
            operacion.text = resultadoTemporal
            resultadoTemporal = (numero1.toDouble() + cos(numero1.toDouble())).toString()
            resultado.text = resultadoTemporal
            resultadoTemporal = numero1
            ingresandoSegundoNumero=false

        }

//        if (button.tag.toString() == "C" && resultado.text == "0" && operacion.text == "0") {
//            numero1 = "0"
//            coseno = "cos($numero1)"
//            operacion.text = coseno
//            numero1 = (cos(numero1.toDouble()).toString())
//            resultado.text = numero1
//            ingresandoSegundoNumero= false
//        }

        if (resultadoTemporal == numero1) {

            coseno = "cos($numero1)"
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



