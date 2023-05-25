package com.example.checkout.ui.uiComponents

import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.checkout.R

class BottomCard(
    val text: String = "",
    val buttonText: String = "",
    val view : View,
    onClick:()-> Unit
)  {

    private val card: View = view
    private val cl : ConstraintLayout = view.findViewById(R.id.cl)
    private val textView : TextView = view.findViewById(R.id.text)
    private val button: AppCompatButton = view.findViewById(R.id.btn_view)
    private val click = onClick
    private var expanded = false

    init {
        textView.text = this.text
        button.text = buttonText
        button.setOnClickListener { click() }
    }


    fun transition(){
        when (expanded) {
            false -> expand()
            else -> contract()
        }
        expanded = !expanded

        TransitionManager.beginDelayedTransition(cl, AutoTransition())
    }

     fun expand() {
        cl.visibility = View.VISIBLE
        TransitionManager.beginDelayedTransition(cl, AutoTransition())
    }

     fun contract() {
        cl.visibility = View.GONE
        TransitionManager.beginDelayedTransition(cl, AutoTransition())
    }



}



