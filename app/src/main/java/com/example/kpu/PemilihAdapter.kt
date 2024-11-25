package com.example.kpu

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.kpu.database.Pemilih

class PemilihAdapter(private val context: Context, private val pemilih: MutableList<Pemilih>, private val deleteListener: MainActivity) :
    BaseAdapter() {

    override fun getCount(): Int = pemilih.size

    override fun getItem(position: Int): Pemilih = pemilih[position]

    override fun getItemId(position: Int): Long = pemilih[position].id.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_pemilih, parent, false)
        val pemilih = getItem(position)

        val tvID = view.findViewById<TextView>(R.id.txt_id)
        val tvNama = view.findViewById<TextView>(R.id.txt_nama)
        val icDelete = view.findViewById<ImageView>(R.id.ic_delete)
        val icEdit = view.findViewById<ImageView>(R.id.ic_edit)
        val icDetail = view.findViewById<ImageView>(R.id.ic_detail)

        tvID.text = pemilih.id.toString()
        tvNama.text = pemilih.nama

        icDelete.setOnClickListener {
            deleteListener.onDelete(pemilih, position)
        }

        icEdit.setOnClickListener {
            val intent = Intent(context, EditDataActivity::class.java).apply {
                putExtra("id", pemilih.id)
                putExtra("nik", pemilih.nik)
                putExtra("nama", pemilih.nama)
                putExtra("gender", pemilih.gender)
                putExtra("alamat", pemilih.alamat)
            }
            context.startActivity(intent)
        }

        icDetail.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("nik", pemilih.nik)
                putExtra("nama", pemilih.nama)
                putExtra("gender", pemilih.gender)
                putExtra("alamat", pemilih.alamat)
            }
            context.startActivity(intent)
        }

        return view
    }
}