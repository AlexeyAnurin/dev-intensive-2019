package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import ru.skillbranch.devintensive.R

class AspectRatioImageView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {
    companion object {
        //16:9 = 1.78
        private const val DEFAULT_ASPECT_RATIO = 1.78f
    }

    private var aspectRatio = DEFAULT_ASPECT_RATIO

    init {
        //считываем аттрибуты если они не null
        if (attrs != null) {
            //<declare-styleable name = "AspectRatioImageView"> - кастомная view
    val a = context.obtainStyledAttributes(attrs, R.styleable.AspectRatioImageView)
            //значение: <attr name="aspectRatio" format="float"/>
aspectRatio = a.getFloat(R.styleable.AspectRatioImageView_aspectRatio, DEFAULT_ASPECT_RATIO)
            //надо вызывать recycle() для эффективного использования ресурсов
            a.recycle()
        }
    }
//определяет размер View
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    // устанавливает новую высоту newHeight относительно ширины measuredWidth
        val newHeight = (measuredWidth/aspectRatio).toInt()
    //устанавливает новые размеры View
        setMeasuredDimension(measuredWidth, newHeight)
    }
}