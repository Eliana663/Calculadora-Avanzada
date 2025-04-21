package com.example.calculadora_Avanzada


import android.annotation.SuppressLint
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



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        operacion = this.findViewById(R.id.operacion)
        resultado = this.findViewById(R.id.resultado)


    }

    // Botón para mostrar cada vez que se pulsa un número en el textview de operacion
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
        }



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

        // Botón para mostrar cada vez que se pulsa un operador
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
                        resultadoTemporal.endsWith("*") && button.text.toString() !== ("*")) {
                        operador = button.tag.toString()
                        resultadoTemporal = numero1 + button.text.toString()
                        operacion.text = resultadoTemporal
                        return
                    }



                    if (operador.isEmpty() || resultadoTemporal == numero1 ) {

                        operador = button.tag.toString()
                        resultadoTemporal = resultadoTemporal + button.text.toString()
                        ingresandoSegundoNumero = true
                        operacion.text = resultadoTemporal

                    } else {
                        igual(view)
                        pulsarOperacion(view)
                    }



        }

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
            resultado.text = resultadoTemporal
            resultadoTemporal = resultadoTemporal.toString().replace(".", ",")
            numero1 = resultadoTemporal
            numero2 = ""
            ingresandoSegundoNumero = false


        }



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


    }


