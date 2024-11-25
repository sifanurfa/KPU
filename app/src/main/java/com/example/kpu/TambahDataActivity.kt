package com.example.kpu

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kpu.databinding.ActivityTambahDataBinding

class TambahDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTambahDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btnSimpan.setOnClickListener {
                val nama = edtNama.text.toString()
                val nik = edtNik.text.toString()
                val alamat = edtAlamat.text.toString()

                // Ambil gender yang dipilih
                val selectedGenderId = rgGender.checkedRadioButtonId
                val gender = when (selectedGenderId) {
                    R.id.radio_laki -> "Laki laki"
                    R.id.radio_perempuan -> "Perempuan"
                    else -> "Laki laki" // Default jika tidak ada yang dipilih
                }

                val intent = Intent(this@TambahDataActivity, MainActivity::class.java).apply {
                    putExtra("tambahNama", nama)
                    putExtra("tambahNik", nik)
                    putExtra("tambahGender", gender)
                    putExtra("tambahAlamat", alamat)
                }
                startActivity(intent)
                Toast.makeText(this@TambahDataActivity, "Data berhasil ditambahkan!", Toast.LENGTH_LONG).show()
            }
        }
    }
}