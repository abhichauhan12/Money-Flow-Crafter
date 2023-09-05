package com.abhishek.moneyflowcrafter.screens.addTransaction.adapter

import com.abhishek.moneyflowcrafter.R
import com.abhishek.moneyflowcrafter.screens.addTransaction.model.TransactionDataModel
import com.abhishek.moneyflowcrafter.screens.addTransaction.model.TransactionType
import com.abhishek.moneyflowcrafter.databinding.CommonElementEmptyStateBinding
import com.abhishek.moneyflowcrafter.databinding.RowTransactionBinding
import com.abhishek.moneyflowcrafter.utils.RECYCLER_DATA_VIEW
import com.abhishek.moneyflowcrafter.utils.RECYCLER_NO_DATA_VIEW
import com.abhishek.moneyflowcrafter.utils.commonMethods.formatAsCurrency
import com.abhishek.moneyflowcrafter.utils.commonMethods.getDateInDD_MMM
import com.abhishek.moneyflowcrafter.utils.commonMethods.getTimeInHH_MM_A
import com.abhishek.moneyflowcrafter.utils.commonMethods.locateView
import com.abhishek.moneyflowcrafter.utils.popup.transaction.ActionType
import com.abhishek.moneyflowcrafter.utils.popup.transaction.PopupListener
import com.abhishek.moneyflowcrafter.utils.popup.transaction.TransactionPopup
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class TransactionAdapter(val callback: (action: ActionType, dataObject: TransactionDataModel) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: ArrayList<TransactionDataModel> = ArrayList()
    private lateinit var context: Context

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    override fun getItemViewType(position: Int): Int {
        return if (list.isEmpty()) {
            RECYCLER_NO_DATA_VIEW
        } else {
            RECYCLER_DATA_VIEW
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == RECYCLER_NO_DATA_VIEW) {
            EmptyStateViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.common_element_empty_state,
                    parent,
                    false
                )
            )
        } else {
            TransactionViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.row_transaction,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == RECYCLER_DATA_VIEW) {
            if (holder is TransactionViewHolder) {
                holder.bindView(list[position], position)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (list.isNullOrEmpty()) {
            1
        } else {
            list.size
        }
    }

    fun addData(data: ArrayList<TransactionDataModel>) {
        if (list.isNullOrEmpty()) {
            list = ArrayList()
        }
        list.clear()

        list.addAll(data)
        notifyDataSetChanged()
    }

    inner class TransactionViewHolder(private val binding: RowTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(row: TransactionDataModel, position: Int) {
            binding.amountTextView.text = "₹${formatAsCurrency(num = row.amount)}"

            if (row.meta?.transactionNote.isNullOrEmpty()) {
                row.meta?.transactionNote = "Regular Transaction"
            }
            binding.noteTextView.text = row.meta?.transactionNote

            row.date?.let {
                val date = Date()
                date.time = it
                binding.dateTimeTextView.text =
                    "${getDateInDD_MMM(date)} | ${getTimeInHH_MM_A(date)}"
            }

            val params = binding.parentCardView.layoutParams as RelativeLayout.LayoutParams
            when (row.meta?.transactionType) {
                TransactionType.DEBIT -> {
                    params.removeRule(RelativeLayout.ALIGN_PARENT_START)
                    params.addRule(RelativeLayout.ALIGN_PARENT_END)
                    binding.amountTextView.setTextColor(ContextCompat.getColor(context, R.color.red_400))
                }
                TransactionType.LOAN_TAKEN -> {
                    params.removeRule(RelativeLayout.ALIGN_PARENT_END)
                    params.addRule(RelativeLayout.ALIGN_PARENT_START)
                    binding.amountTextView.setTextColor(ContextCompat.getColor(context, R.color.purple_400))
                }
                TransactionType.LOAN_GIVEN -> {
                    params.removeRule(RelativeLayout.ALIGN_PARENT_END)
                    params.addRule(RelativeLayout.ALIGN_PARENT_START)
                    binding.amountTextView.setTextColor(ContextCompat.getColor(context, R.color.blue_400))
                }
                else -> {
                    params.removeRule(RelativeLayout.ALIGN_PARENT_END)
                    params.addRule(RelativeLayout.ALIGN_PARENT_START)
                    binding.amountTextView.setTextColor(ContextCompat.getColor(context, R.color.green_400))
                }
            }
            binding.parentCardView.layoutParams = params

            binding.parentCardView.setOnLongClickListener {
                val location = locateView(binding.parentCardView)
                location?.let {
                    TransactionPopup(context).showAt(
                        gravity = Gravity.TOP,
                        x = location.left,
                        y = location.bottom,
                        callback = object : PopupListener {
                            override fun onActionButtonClicked(action: ActionType) {
                                callback(action, row)
                            }

                        })
                }
                return@setOnLongClickListener true
            }
        }
    }
}

class EmptyStateViewHolder(private val binding: CommonElementEmptyStateBinding) :
    RecyclerView.ViewHolder(
        binding.root
    )
