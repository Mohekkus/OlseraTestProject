package me.mohekkus.compoundedittext

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat


class CompoundEditText(context: Context, attrs: AttributeSet): FrameLayout(context, attrs) {

    private var title: TextView
    private var editText: EditText
    private var frame: FrameLayout

    init {
        LayoutInflater.from(context).inflate(R.layout.compound_edit_text, this, true)
        title = findViewById(R.id.tv_title)
        editText = findViewById(R.id.et_compound)
        frame = findViewById(R.id.frame_et)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CompoundEditText,
            0,0
        ).apply {
            title.text = getString(R.styleable.CompoundEditText_setTitle)
            editText.setText(
                getString(R.styleable.CompoundEditText_setText)
            )
            if (getBoolean(R.styleable.CompoundEditText_setMultiLine, false))
                setMultiLines()
        }

        findViewById<LinearLayout>(R.id.container_compound).setOnClickListener {
            editText.requestFocus()
        }

        editText.apply {
            setOnFocusChangeListener { view, b ->
                frame.apply {
                    if (b)
                        setBackgroundColor(
                            Color.BLUE
                        )
                    else
                        setBackgroundColor(
                            Color.WHITE
                        )
                }
            }
            setLines(1)
        }
    }

    fun setTitle(string: String) {
        title.text = string
        editText.hint = string
    }

    fun setValue(string: String) {
        editText.setText(string)
    }

    fun setMultiLines() {
        editText.setLines(5)
    }

    fun getValue(): String {
        return editText.text.toString()
    }
}