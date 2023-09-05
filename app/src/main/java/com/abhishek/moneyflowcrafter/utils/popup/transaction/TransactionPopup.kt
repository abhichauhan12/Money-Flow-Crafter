package com.abhishek.moneyflowcrafter.utils.popup.transaction

import com.abhishek.moneyflowcrafter.R
import com.abhishek.moneyflowcrafter.databinding.LayoutPopupMenuTransactionBinding
import com.abhishek.moneyflowcrafter.utils.commonMethods.doIfTrue
import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.databinding.DataBindingUtil


class TransactionPopup(private val context: Context) {

    private lateinit var binding: LayoutPopupMenuTransactionBinding
    private lateinit var popupWindow: PopupWindow

    init {
        val inflater = context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater?
        inflater?.let {
            binding = DataBindingUtil.inflate(
                inflater,
                R.layout.layout_popup_menu_transaction,
                null,
                false
            )
            popupWindow = PopupWindow(
                binding.root,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                true
            )
        }
    }

    fun showAt(gravity: Int = Gravity.CENTER, x: Int = 0, y: Int = 0, callback: PopupListener? = null) {
        doIfTrue(this::popupWindow.isInitialized) {
            popupWindow.elevation = 10f
            popupWindow.showAtLocation(binding.root, gravity, x, y)

            binding.deleteIconImageView.setOnClickListener {
                callback?.onActionButtonClicked(ActionType.DELETE)
                popupWindow.dismiss()
            }
//
//            binding.editIconImageView.setOnClickListener {
//                callback?.onActionButtonClicked(ActionType.EDIT)
//                popupWindow.dismiss()
//            }

//            binding.redoIconImageView.setOnClickListener {
//                callback?.onActionButtonClicked(ActionType.REDO)
//                popupWindow.dismiss()
//            }
        }
    }
}

enum class ActionType {
    DELETE, EDIT, REDO
}

interface PopupListener{
    fun onActionButtonClicked(action: ActionType)
}