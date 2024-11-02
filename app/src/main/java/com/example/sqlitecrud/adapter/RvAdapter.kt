package com.example.sqlitecrud.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlitecrud.databinding.ItemRvBinding
import com.example.sqlitecrud.models.User

class RvAdapter(var list: ArrayList<User> = ArrayList(), var rvAction: RvAction):RecyclerView.Adapter<RvAdapter.Vh>(){

    inner class Vh(val itemRvBinding: ItemRvBinding):RecyclerView.ViewHolder(itemRvBinding.root){
        fun onBind(user: User){
            itemRvBinding.edtName.text = user.name
            itemRvBinding.edtNumber.text = user.number
            itemRvBinding.btnMore.setOnClickListener {
                rvAction.moreClick(user, itemRvBinding.btnMore)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int= list.size
    }


interface RvAction{
    fun moreClick(user: User, imagerView: ImageView)
}
