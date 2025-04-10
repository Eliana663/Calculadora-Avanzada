package com.example.calculadora_Avanzada


import android.icu.text.DecimalFormat
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.calculadora.R


class MainActivity : ComponentActivity() {


    var numero1 = ""
    var numero2 = ""
    var operador = ""
    var ingresandoSegundoNumero = false
    var resultadoTemporal = ""
    var punto = ""


    lateinit var operacion: TextView
    lateinit var resultado: TextView
    lateinit var formatoDecimal: DecimalFormat


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        formatoDecimal = DecimalFormat("##,######")

        operacion = this.findViewById(R.id.operacion)
        resultado = this.findViewById(R.id.resultado)


    }

    // Botón para mostrar cada vez que se pulsa un número en el textview de operacion
    fun pulsarNumero(view: View) {
        val button: Button = view as Button

        when {
            ingresandoSegundoNumero -> {


                numero2 += button.text.toString()
                resultadoTemporal += button.text.toString()
                operacion.text = resultadoTemporal
            }
            else -> {

                numero1= numero1 + button.text.toString()
                operacion.text = numero1
                resultadoTemporal = numero1
            }
        }


        }

        fun agregarComa (view: View) {
        val button: Button = view as Button

            punto = button.text.toString()

            if (!numero1.contains(",")) {

                numero1 += button.text.toString()
                operacion.text = numero1
            }

            if (ingresandoSegundoNumero && !numero2.contains(",")) {
                resultadoTemporal += button.text.toString()
                numero2 += button.text.toString()

                operacion.text = resultadoTemporal


            }



    }


        // Botón para mostrar cada vez que se pulsa una operacion
        fun pulsarOperacion(view: View) {
            val button: Button = view as Button

            operador = button.tag.toString()
            resultadoTemporal =  resultadoTemporal + button.text.toString()
            ingresandoSegundoNumero = true
            operacion.text = resultadoTemporal

        }



        fun igual(view: View) {
            val button: Button = view as Button


            numero1 = numero1.replace(",", ".")
            numero2 = numero2.replace(",", ".")
            Toast.makeText(this, resultadoTemporal, Toast.LENGTH_LONG).show()

            when (operador) {

                "suma" -> resultadoTemporal = (numero1.toDouble() + numero2.toDouble()).toString()
            }
            resultado.text = resultadoTemporal

        }


        fun resetAC(view: View) {
            val button: Button = view as Button
            operacion.text = "0"
            resultadoTemporal=""

        }


    }


