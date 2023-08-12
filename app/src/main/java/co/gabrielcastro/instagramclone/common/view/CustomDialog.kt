package co.gabrielcastro.instagramclone.common.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import co.gabrielcastro.instagramclone.R
import co.gabrielcastro.instagramclone.databinding.DialogCustomBinding

class CustomDialog(context: Context) : Dialog(context) {

    private lateinit var binding: DialogCustomBinding
    private lateinit var txtButtons: Array<TextView>
    private var titleId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogCustomBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun setTitle(titleId: Int) {
        this.titleId = titleId
    }

    fun addButton(vararg texts: Int, listener: View.OnClickListener) {
        txtButtons = Array(texts.size) {
            TextView(context)
        }
        
        texts.forEachIndexed { index, textId ->
            txtButtons[index].id = textId
            txtButtons[index].setText(textId)
            txtButtons[index].setOnClickListener {
                listener.onClick(it)
                dismiss()
            }
        }
    }
    override fun show() {
        super.show()

        titleId?.let {
            binding.dialogTitle.setText(it)
        }

        for(textView in txtButtons) {
            // LayoutParams uma forma de definir margens a partir do codigo
            val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            layoutParams.setMargins(30, 50, 30, 50)
            binding.dialogContainer.addView(textView, layoutParams)
        }
    }
}