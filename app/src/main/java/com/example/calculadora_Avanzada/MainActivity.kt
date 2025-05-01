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
        ingresandoOperacionAvanzada = false
        resultadoSuperior = ""
        resultadoInferior = ""
        operadorPantalla = ""
        operadorAvanzado = ""

    }


    fun actualizarPantallaSuperior() {


        if (!ingresandoOperacionAvanzada) {

        resultadoSuperior = if (ingresandoSegundoNumero) {
            numero1 + operadorPantalla + numero2
        } else if (numero2 == "" && resultadoInferior !== ""  ) {
            numero1 + operadorPantalla
        } else {
            numero1
        }
        }


        if (ingresandoOperacionAvanzada) {


            resultadoSuperior = if  (ingresandoSegundoNumero && numero2 != "" && operadorAvanzado != "%"){
                "$numero1$operadorPantalla$operadorAvanzado($numero2)"

            } else if (operadorAvanzado == "%") {
                "$numero1$operadorPantalla$numero2$operadorAvanzado"

            } else if (numero2 == "" && ingresandoSegundoNumero) {
                "$numero1$operadorPantalla$operadorAvanzado($numero1)"


            } else {
                "$operadorAvanzado($numero1)"
            }

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
        } else if (numero1 != "") {
            resetearValores()
            numero1 = numero1 + button.text.toString()
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
        operador = button.tag.toString()
        operadorPantalla = button.text.toString()

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


    fun pulsarOperacionAvanzada(view: View) {
        val button: Button = view as Button
        operadorAvanzado = button.tag.toString()


        ingresandoOperacionAvanzada = true

        if (!ingresandoSegundoNumero && operadorAvanzado  == "%") {
            resetearValores()
            return
        }

        if (!ingresandoSegundoNumero ) {


            if (numero1 == "") {
                numero1 = "0"
                calculoAvanzado()

            } else if (operadorAvanzado == ("cos")) {
                calculoAvanzado()

            } else {
                calculoAvanzado()
            }

        } else {
            calculoAvanzado()
        }

    }

    fun actualizarPantallaNumero2() {

        actualizarPantallaSuperior()
        numero2 = resultadoInferior
        actualizarPantallaInferior()
        ingresandoOperacionAvanzada = false

    }
    fun calculoAvanzado() {

        if (!ingresandoSegundoNumero) {
            resultadoInferior = calcular.OperacionAvanzada(numero1, operadorAvanzado)

            actualizarPantallaSuperior()
            numero1 = resultadoInferior
            actualizarPantallaInferior()
            numero2 = ""
            ingresandoOperacionAvanzada = false

        } else if (numero2 == "") {
            resultadoInferior = calcular.OperacionAvanzada(numero1, operadorAvanzado)
            actualizarPantallaNumero2()

        } else {
            resultadoInferior = calcular.OperacionAvanzada(numero2, operadorAvanzado)
            actualizarPantallaNumero2()
        }




    }
}







