package com.animesh.roy.spacexmission

import ListQuery
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.voela.actrans.AcTrans


class ListAdapter(
    val launches: List<ListQuery.LaunchesPast>, context: Context
) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    var mContext: Context? = context

    var onItemClicked: ((ListQuery.LaunchesPast) -> Unit)? = null


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val missionName: TextView
        val launchDate: TextView
        val missionImg: ImageView

        init {
            // Define click listener for the ViewHolder's View.
            missionName = view.findViewById(R.id.mission_name)
            launchDate = view.findViewById(R.id.launchDate)
            missionImg = view.findViewById(R.id.missionPatch)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListAdapter.ViewHolder, position: Int) {
        holder.missionName.text = launches.get(position).mission_name
        holder.launchDate.text = launches.get(position).launch_date_local.toString()

        val uri: String = launches.get(position).links?.mission_patch_small.toString()

        mContext = holder.itemView.getContext();

        Glide.with(holder.itemView.context).load(uri).placeholder(R.drawable.no_img)
            .into(holder.missionImg)

        holder.itemView.setOnClickListener(View.OnClickListener {
            onItemClicked?.invoke(launches.get(position))
            Log.d("Clicked", launches.get(position).id.toString())


//            val duration = Toast.LENGTH_SHORT
//            val toast = Toast.makeText(
//                mContext,
//                launches.get(position).mission_name.toString(),
//                duration
//            )
//            toast.show()

            val intent = Intent(mContext, DetailsActivity::class.java)
            intent.putExtra("id_value", launches.get(position).id)
            mContext?.startActivity(intent)
            AcTrans.Builder(mContext!!).performSlideToRight()


        })
    }

    override fun getItemCount(): Int {
        return launches.size
    }


}