package com.example.kpu

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.kpu.database.Pemilih
import com.example.kpu.database.PemilihDao
import com.example.kpu.database.PemilihRoomDatabase
import com.example.kpu.databinding.ActivityMainBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var prefManager: PrefManager
    private lateinit var mPemilihsDao: PemilihDao
    private lateinit var executorService: ExecutorService
    private var updateId: Int=0
    private lateinit var adapter: PemilihAdapter
    private var pemilihList: MutableList<Pemilih> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        executorService = Executors.newSingleThreadExecutor()
        val db = PemilihRoomDatabase.getDatabase(this)
        mPemilihsDao = db!!.pemilihDao()!!

        val tambahNama = intent.getStringExtra("tambahNama")
        val tambahNik = intent.getStringExtra("tambahNik")
        val tambahGender = intent.getStringExtra("tambahGender")
        val tambahAlamat = intent.getStringExtra("tambahAlamat")

        if (!tambahNama.isNullOrEmpty() && !tambahNik.isNullOrEmpty() && !tambahAlamat.isNullOrEmpty()) {
            insert(
                Pemilih(
                    nama = tambahNama,
                    nik = tambahNik,
                    gender = tambahGender,
                    alamat = tambahAlamat
                )
            )
        }

        adapter = PemilihAdapter(this, pemilihList, this)

        // Set adapter ke ListView atau RecyclerView
        val listView: ListView = findViewById(R.id.listView)
        listView.adapter = adapter

        prefManager = PrefManager.getInstance(this)
        checkLoginStatus()
        with(binding) {
            btnTambahData.setOnClickListener {
                val intent = Intent(this@MainActivity, TambahDataActivity::class.java)
                startActivity(intent)
            }

            btnLogout.setOnClickListener {
                prefManager.setLoggedIn(false)
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
            }
        }
    }
    fun checkLoginStatus() {
        val isLoggedIn = prefManager.isLoggedIn()
        if (!isLoggedIn) {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }
    }

    private fun getAllPemilih() {
        mPemilihsDao.allNotes.observe(this) { pemilihs ->
            pemilihList.clear()  // Pastikan untuk membersihkan list sebelumnya
            pemilihList.addAll(pemilihs)  // Menambahkan data yang baru
            adapter = PemilihAdapter(this, pemilihList, this)  // Menginisialisasi adapter
            binding.listView.adapter = adapter  // Set adapter ke ListView
        }
    }

    private fun insert(pemilih: Pemilih) {
        executorService.execute { mPemilihsDao.insert(pemilih) }
    }
    private fun delete(pemilih: Pemilih) {
        executorService.execute { mPemilihsDao.delete(pemilih) }
    }
    public fun update(pemilih: Pemilih) {
        executorService.execute { mPemilihsDao.update(pemilih) }
    }

    override fun onResume() { // ketika jalan, langsung get data
        super.onResume()
        getAllPemilih()
    }

    fun onDelete(pemilih: Pemilih, position: Int) {
        delete(pemilih)

        // Hapus item dari list dan update tampilan
        pemilihList.removeAt(position)
        adapter.notifyDataSetChanged()
    }
}