package com.example.aviyasapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import StudentModel
import android.widget.TextView
import com.example.aviyasapp.R

class StudentAdapter(private val mList: List<StudentModel>) : RecyclerView.Adapter<StudentAdapter.ViewHolder>() {
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val sName: TextView = ItemView.findViewById(R.id.student_name)
        val re: Button = ItemView.findViewById(R.id.remove_button)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]
        holder.sName.text = ItemsViewModel.name
        holder.re.setOnClickListener{

        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }


}
