package com.example.config_management

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_empty.view.*
import kotlinx.android.synthetic.main.list_item.view.*


class DetailAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var domainOrFeatureList: List<DomainFeatureModel> = listOf()
    private var name:String = ""
    private var id:Int = -1

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

//        init {
//            view.btnEdit.setOnClickListener { onEdit.invoke(myDataset[adapterPosition].id) }
//            view.btnDelete.setOnClickListener { onDelete.invoke(myDataset[adapterPosition].id) }
//        }

        fun bind(data: DomainFeatureModel) {
            view.tvName.text = data.name
        }
    }

//    inner class EmptyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//        init {
//            itemView.button.setOnClickListener { retry.invoke() }
//        }
//    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

//        if (viewType == VIEW_MAIN) {
            // create a new view
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false)
            // set the view's size, margins, paddings and layout parameters

            return MyViewHolder(view)
//        } else {
//             create a new view
//            val view = LayoutInflater.from(parent.context)
//                .inflate(R.layout.item_empty, parent, false)
            // set the view's size, margins, paddings and layout parameters

//            return EmptyViewHolder(view)
//        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if (holder is MyViewHolder) {
            holder.bind(domainOrFeatureList[position])
        }

    }


    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return if (domainOrFeatureList.isEmpty()) {
            1
        } else {
            domainOrFeatureList.size
        }
    }

//    override fun getItemViewType(position: Int): Int {
//        return if (myDataset.isEmpty()) {
//            VIEW_EMPTY
//        } else {
//            VIEW_MAIN
//        }
//    }
//
//    companion object {
//        const val VIEW_EMPTY = 0
//        const val VIEW_MAIN = 1
//    }

//    fun setData(list: List<DomainFeatureModel>) {
//        this.myDataset = list
//        notifyDataSetChanged()
//    }

    fun setDetailData(name: String, id: Int, list: List<DomainFeatureModel>){
        this.domainOrFeatureList = list
        this.name = name
        this.id = id
    }

}
