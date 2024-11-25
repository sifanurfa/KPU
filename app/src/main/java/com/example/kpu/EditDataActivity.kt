package com.example.kpu

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kpu.database.Pemilih
import com.example.kpu.database.PemilihDao
import com.example.kpu.database.PemilihRoomDatabase
import com.example.kpu.databinding.ActivityEditDataBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class EditDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditDataBinding
    private lateinit var mPemilihsDao: PemilihDao
    private lateinit var executorService: ExecutorService
    private var pemilihId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        executorService = Executors.newSingleThreadExecutor()
        val db = PemilihRoomDatabase.getDatabase(this)
        mPemilihsDao = db!!.pemilihDao()!!

        val id = intent.getIntExtra("id", 0)
        val nama = intent.getStringExtra("nama")
        val nik = intent.getStringExtra("nik")
        val gender = intent.getStringExtra("gender")
        val alamat = intent.getStringExtra("alamat")

        with(binding) {
            edtNama.setText(nama)
            edtNik.setText(nik)
            edtAlamat.setText(alamat)

            when (gender) {
                "Laki laki" -> rgGender.check(R.id.radio_laki)
                "Perempuan" -> rgGender.check(R.id.radio_perempuan)
                "Laki laki" -> rgGender.check(R.id.radio_laki)
            }

            btnSimpan.setOnClickListener {
                val updatedNama = edtNama.text.toString()
                val updatedNik = edtNik.text.toString()
                val updatedAlamat = edtAlamat.text.toString()
                val updatedGender = when (rgGender.checkedRadioButtonId) {
                    R.id.radio_laki -> "Laki laki"
                    R.id.radio_perempuan -> "Perempuan"
                    else -> "Laki laki"
                }

                val updatedPemilih = Pemilih(
                    id = id,
                    nama = updatedNama,
                    nik = updatedNik,
                    gender = updatedGender,
                    alamat = updatedAlamat
                )
                updatePemilih(updatedPemilih)

                val intent = Intent(this@EditDataActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
    private fun updatePemilih(pemilih: Pemilih) {
        executorService.execute {
            mPemilihsDao.update(pemilih)
            runOnUiThread {
                Toast.makeText(this, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show()
                finish() // Kembali ke MainActivity
            }
        }
    }
}