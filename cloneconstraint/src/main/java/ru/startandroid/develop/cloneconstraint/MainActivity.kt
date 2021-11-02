package ru.startandroid.develop.cloneconstraint

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.TransitionManager

//Изучим работу метода clone ConstraintSet

class MainActivity : AppCompatActivity() {
    val set = ConstraintSet()
    val set2 = ConstraintSet()

    lateinit var constraintLayout: ConstraintLayout
    lateinit var currentSet: ConstraintSet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        constraintLayout = findViewById(R.id.container)

        //копируем настройки из текущего ConstraintLayout в ConstraintSet
        set.clone(constraintLayout)

        //копируем настройки из activity_main2 в set2
        set2.clone(this, R.layout.activity_main2)

        currentSet = set

        /*
            По нажатию кнопки мы в currentSet помещаем поочередно set или set2, и применяем эти
                настройки к текущему ConstraintLayout. Т.е. экран будет переключаться между
                начальными настройками и настройками из activity_main2.
         */
        findViewById<Button>(R.id.buttonClick).setOnClickListener {
            //изменяем current set
            currentSet = if (currentSet == set) set2 else set
            //включаем анимацию
            TransitionManager.beginDelayedTransition(constraintLayout)
            //применяем настройки
            currentSet.applyTo(constraintLayout)
        }
    }
}