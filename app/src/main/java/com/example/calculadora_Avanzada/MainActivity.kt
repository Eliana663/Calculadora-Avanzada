package com.example.calculadora_Avanzada

import android.R.attr.button
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


    lateinit var operacion : TextView
    lateinit var resultado : TextView


     lateinit var formatoDecimal: DecimalFormat



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        formatoDecimal = DecimalFormat("#.#########")

        operacion = this.findViewById(R.id.operacion)
        resultado = this.findViewById(R.id.resultado)



    }
    // Botón para mostrar cada vez que se pulsa un número en el textview de operacion
    fun pulsarNumero (view: View) {
        val button:Button = view as Button;
        numero1 = button.text.toString()
        operacion.text = numero1
        Toast.makeText(this,button.text,Toast.LENGTH_LONG).show();
    }
    // Botón para mostrar cada vez que se pulsa una operacion
    fun pulsarOperacion (view: View) {
        val button: Button = view as Button;
        operador = button.text.toString()
        operacion.text = operador

    }

    fun ACReset(view: View) {
        operacion.text = "0"
        resultado.text = "0"
    }



    }
