package com.example.employee.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.employee.R
import com.example.employee.models.EmployeeModel
import com.google.firebase.database.FirebaseDatabase


class EmployeeDetailsActivity : AppCompatActivity() {

    private lateinit var tvEmpId: TextView
    private lateinit var tvEmpFName: TextView
    private lateinit var tvEmpLName: TextView
    private lateinit var tvEmpAge: TextView
    private lateinit var tvEmpSalary: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("empId").toString(),
                intent.getStringExtra("empFName").toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("empId").toString()
            )
        }

    }

    private fun initView() {
        tvEmpId = findViewById(R.id.tvEmplId)
        tvEmpFName = findViewById(R.id.tvEmplFName)
        tvEmpLName = findViewById(R.id.tvEmplLName)
        tvEmpAge = findViewById(R.id.tvEmplAge)
        tvEmpSalary = findViewById(R.id.tvEmplSal)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)


    }

    private fun setValuesToViews() {
        tvEmpId.text = intent.getStringExtra("empId")
        tvEmpFName.text = intent.getStringExtra("empFName")
        tvEmpLName.text = intent.getStringExtra("empLName")
        tvEmpAge.text = intent.getStringExtra("empAge")
        tvEmpSalary.text = intent.getStringExtra("empSalary")

    }

    private fun openUpdateDialog(
        empId: String,
        empFName: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)

        mDialog.setView(mDialogView)

        //initialize layout ids to variable
        val etEmpFName = mDialogView.findViewById<EditText>(R.id.etEmpFName)
        val etEmpLName = mDialogView.findViewById<EditText>(R.id.etEmpLName)
        val etEmpAge = mDialogView.findViewById<EditText>(R.id.etEmpAge)
        val etEmpSalary = mDialogView.findViewById<EditText>(R.id.etEmpSalary)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        //passing values to the our mDialogView
        etEmpFName.setText(intent.getStringExtra("empFName").toString())
        etEmpLName.setText(intent.getStringExtra("empLName").toString())
        etEmpAge.setText(intent.getStringExtra("empAge").toString())
        etEmpSalary.setText(intent.getStringExtra("empSalary").toString())

        mDialog.setTitle("Updating $empFName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateEmpData(
                empId,
                etEmpFName.text.toString(),
                etEmpLName.text.toString(),
                etEmpAge.text.toString(),
                etEmpSalary.text.toString()
            )

            Toast.makeText(applicationContext, "Employee Data Updated", Toast.LENGTH_LONG).show()

            // setting new entered data to textviews in employee_details_layout
            tvEmpFName.text = etEmpFName.text.toString()
            tvEmpLName.text = etEmpLName.text.toString()
            tvEmpAge.text = etEmpAge.text.toString()
            tvEmpSalary.text = etEmpSalary.text.toString()

            alertDialog.dismiss()

        }

    }

    // to update firebase database
    private fun updateEmpData(
        id: String,
        fName: String,
        lName: String,
        age: String,
        salary: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Employee").child(id)
        val empInfo = EmployeeModel(id, fName, lName, age, salary)
        dbRef.setValue(empInfo)
    }

    //delete record
    private fun deleteRecord(
        id: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Employee").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Employee Data Deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FetchDataActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener { error ->
            Toast.makeText(this, "Deleting Error ${error.message}", Toast.LENGTH_LONG).show()
        }
    }
}