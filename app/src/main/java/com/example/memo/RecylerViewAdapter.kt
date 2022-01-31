package com.example.memo

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.example.memo.model.room.Entity
import com.example.memo.viewModel.MainViewModel
import kotlinx.android.synthetic.main.recycler_item.view.*

class RecyclerViewAdapter internal constructor(private var context: Context, var ViewModel : MainViewModel)
    : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>()
{

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var users = emptyList<Entity>() // Cached copy of words

    // inner =비정적 중첩함수
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date = itemView.date
        val memo = itemView.memo


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = inflater.inflate(R.layout.recycler_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val num = users[position]
        holder.date.text = num.date.toString()
        holder.memo.text = num.memo

        holder.itemView.setOnClickListener {
            Intent(context, SelectActivity::class.java).apply{
                putExtra("id_num",num.id.toString())
                putExtra("date",num.date.toString())
                putExtra("memo",num.memo.toString())
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            }.run {context.startActivity(this)}
        }
    }

    internal fun setUsers(users: List<Entity>) {
        this.users = users
        notifyDataSetChanged()
    }

    override fun getItemCount() = users.size


}