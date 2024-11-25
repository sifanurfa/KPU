package com.example.kpu

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kpu.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nama = intent.getStringExtra("nama")
        val nik = intent.getStringExtra("nik")
        val gender = intent.getStringExtra("gender")
        val alamat = intent.getStringExtra("alamat")

        with(binding) {
            txtNama.text = nama
            txtNik.text = nik
            txtGender.text = gender
            txtAlamat.text = alamat

            btnKembali.setOnClickListener {
                val intent = Intent(this@DetailActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}