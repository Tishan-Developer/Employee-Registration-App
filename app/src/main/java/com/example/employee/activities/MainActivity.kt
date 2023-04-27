package com.example.employee.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.employee.R

class MainActivity : AppCompatActivity() {

    private lateinit var btnInsertDt : Button
    private lateinit var btnFetchDt : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnInsertDt = findViewById(R.id.btnInsertData)
        btnFetchDt = findViewById(R.id.btnFetchData)

        btnInsertDt.setOnClickListener{
            val intent = Intent(this@MainActivity, SaveDataActivity::class.java)
            startActivity(intent)
        }

        btnFetchDt.setOnClickListener{
            val intent = Intent(this@MainActivity, FetchDataActivity::class.java)
            startActivity(intent)
        }

    }
}