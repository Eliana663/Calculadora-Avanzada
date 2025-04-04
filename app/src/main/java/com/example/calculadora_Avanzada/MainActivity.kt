package com.example.calculadora_Avanzada

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.ComponentActivity
import com.example.calculadora.R

class MainActivity : ComponentActivity() {


    lateinit var campoNumero : EditText
    lateinit var resultado : EditText



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        campoNumero = this.findViewById(R.id.Numero)
        resultado = this.findViewById(R.id.Result)

    }

    fun sumar(view: View){
        var campoNumero: String = campoNumero.text.toString()
        val resultadotempo= campoNumero
        resultado.setText(resultadotempo.toString())
    }



    fun reset(view: View){
        campoNumero.setText("")
        resultado.setText("")

    }


    }
