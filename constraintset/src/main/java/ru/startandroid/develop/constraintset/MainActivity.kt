package ru.startandroid.develop.constraintset

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.TransitionManager

/*
    Исследуем возможности ConstraintSet, который позволит нам осуществлять програмную настройку
        ConstraintLayout
 */

class MainActivity : AppCompatActivity() {
    private lateinit var constraintLayout: ConstraintLayout
    lateinit var buttonClick: Button

    var someMargin = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        constraintLayout = findViewById(R.id.main_constraint_layout)

        buttonClick = findViewById(R.id.click_button)

        someMargin = resources.getDimension(R.dimen.some_margin).toInt()

        buttonClick.setOnClickListener {
            val set = ConstraintSet()
            set.run {
                //читаем в set настройки текущего layout
                clone(constraintLayout)
                //меняем настройки
                //changeConstraints(this)
                //changeChains(this)
                changeGuideline(this)
                //добавляем к изменениям анимацию
                TransitionManager.beginDelayedTransition(constraintLayout)
                //применяем настройки к текущему ConstraintLayout
                applyTo(constraintLayout)
            }
        }
    }

    //метод для наиболее значимой настройки
    private fun changeConstraints(set: ConstraintSet) {
        set.run {
            //Настраиваем привязки

            //удаляем верхнюю и левую привязки
            clear(R.id.textView3, ConstraintSet.LEFT)
            clear(R.id.textView3, ConstraintSet.TOP)

            /*
                Методом connect сначала привязываем левую границу TextView3 к левой границе
                    TextView2, а затем нижнюю границу TextView3 к верхней границе TextView2. Для
                    вертикальной привязки сразу указываем отступ.
             */
            connect(R.id.textView3, ConstraintSet.LEFT, R.id.textView2, ConstraintSet.LEFT)
            connect(R.id.textView3, ConstraintSet.BOTTOM, R.id.textView2, ConstraintSet.TOP,
                someMargin)

            //Настраиваем ширину

            //обнуляем левый отступ
            setMargin(R.id.button_for_expr, ConstraintSet.START, 0)
            //создаем привязку к родителю
            connect(R.id.button_for_expr, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID,
                ConstraintSet.RIGHT, 0)
            //ставим ширину
            constrainWidth(R.id.button_for_expr, ConstraintSet.MATCH_CONSTRAINT)

            /*
                Устанавливаем bias - аналог скролла из Properties, который позволит указать,
                    к какой стороне будет ближе располагаться View при двусторонней привязке.
             */
            setHorizontalBias(R.id.button_for_expr, 0.7F)
        }
    }

    /*
        Программно соединим ButtonChain1 и ButtonChain2 в горизонтальную цепочку, растянем их на
            весь экран, и укажем им weight 3 и 2 соответственно.
     */
    private fun changeChains(set: ConstraintSet) {
        set.run {
            //сначала убираем левые отступы у кнопок
            setMargin(R.id.button_chain1, ConstraintSet.START, 0)
            setMargin(R.id.button_chain2, ConstraintSet.START, 0)
            //устанавливаем ширину MATCH_CONSTRAINT, чтобы можно было настроить вес
            constrainWidth(R.id.button_chain1, ConstraintSet.MATCH_CONSTRAINT)
            constrainWidth(R.id.button_chain2, ConstraintSet.MATCH_CONSTRAINT)
            //В массиве chainViews указываем View, которые надо объединить в цепочку.
            val chainsViews = arrayOf(R.id.button_chain1, R.id.button_chain2).toIntArray()
            //В массиве chainWeights указываем веса для View из chainViews.
            val chainWeighs = arrayOf(3F, 2F).toFloatArray()
            /*
                В методе createHorizontalChain указываем, что цепочка должна быть привязана к
                    родителю слева и справа, и тип цепочки должен быть spread.
             */
            createHorizontalChain(ConstraintSet.PARENT_ID, ConstraintSet.LEFT,
                ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, chainsViews, chainWeighs,
                ConstraintSet.CHAIN_SPREAD)
        }
    }

    //Создадим вертикальную направляющую и привяжем к ней три TextView
    private fun changeGuideline(set: ConstraintSet) {
        set.run {
            //создаем вертикальную направляющую
           create(R.id.guideline, ConstraintSet.VERTICAL_GUIDELINE)
            /*
                методом setGuidelinePercent указываем, что она будет расположена с отступом в 20%
                    от ширины экрана.
             */
           setGuidelinePercent(R.id.guideline, 0.2F)

           /*
            Далее методом connect привязываем все TextView и методом setMargin обнуляем все
                отступы для них.
            */
           connect(R.id.textView4, ConstraintSet.LEFT, R.id.guideline, ConstraintSet.RIGHT, 0)
           connect(R.id.textView5, ConstraintSet.LEFT, R.id.guideline, ConstraintSet.RIGHT, 0)
           connect(R.id.textView6, ConstraintSet.LEFT, R.id.guideline, ConstraintSet.RIGHT, 0)

           setMargin(R.id.textView4, ConstraintSet.START, 0)
           setMargin(R.id.textView5, ConstraintSet.START, 0)
           setMargin(R.id.textView6, ConstraintSet.START, 0)
        }
    }
}