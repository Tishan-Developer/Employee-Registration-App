package com.example.employee.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.employee.R
import com.example.employee.models.EmployeeModel

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase



class SaveDataActivity : AppCompatActivity() {

    private lateinit var fn : EditText
    private lateinit var ln : EditText
    private lateinit var age : EditText
    private lateinit var salary : EditText
    private lateinit var btnSavData : Button
    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_data)

        fn = findViewById(R.id.etxtFN)
        ln = findViewById(R.id.etxtLN)
        age = findViewById(R.id.etxtAge)
        salary = findViewById(R.id.etxtSalary)
        btnSavData = findViewById(R.id.btnSaveData)

        dbRef = FirebaseDatabase.getInstance().getReference("Employee")

        btnSavData.setOnClickListener{
            saveEmployeeData()
        }

    }

    private fun saveEmployeeData(){
        if (isValidateData()){
            //getting values from edit texts
            val fName = fn.text.toString()
            val lName = ln.text.toString()
            val eAge = age.text.toString()
            val sal = salary.text.toString()


            //use for avoiding duplicate values using unique id

            val empId = dbRef.push().key !!

            //after creating model

            val employee = EmployeeModel(empId, empFName =fName, empLName = lName, empAge =eAge, empSal = sal)

            dbRef.child(empId).setValue(employee).addOnCompleteListener {Toast.makeText(this, "Data Inserted Successful",Toast.LENGTH_LONG).show()
                fn.text.clear()
                ln.text.clear()
                age.text.clear()
                salary.text.clear()

            }.addOnFailureListener {err -> Toast.makeText(this, "Error ${err.message}",Toast.LENGTH_LONG).show() }

        } else {
            Toast.makeText(this,"Please Check You Entered Data Again",Toast.LENGTH_LONG).show()
        }
    }

    private fun isValidateData(): Boolean {

        val fName = fn.text.toString()
        val lName = ln.text.toString()
        val eAge = age.text.toString()
        val sal = salary.text.toString()

        if (fName.isEmpty()){
            fn.error = "Please enter your First Name"
            return false
        }
        if (lName.isEmpty()){
            ln.error = "Please enter your Last Name"
            return false
        }
        if (eAge.isEmpty()){
            age.error = "Please enter your Age"
            return false
        }
        if (sal.isEmpty()){
            salary.error = "Please enter your Salary"
            return false
        }
        return true
    }
}