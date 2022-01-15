package com.example.simpletodo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        // Action bar with back arrow
        val actionBar = supportActionBar
        actionBar!!.title = "Edit Task"
        actionBar.setDisplayHomeAsUpEnabled(true)

        // Pre-populates editTaskField with original task at position in list of tasks
        val originalTask = getIntent().getStringExtra("original task")
        val inputTextField = findViewById<EditText>(R.id.editTaskField)
        inputTextField.setText(originalTask)

        // Upon clicking finishEditButton, saves user input data and sends position + new task back
        // to main activity.
        findViewById<Button>(R.id.finishEditButton).setOnClickListener{
            val userInputtedTask = inputTextField.text.toString()
            val position = getIntent().getIntExtra("position", 0)
            val data = Intent()
            data.putExtra("editedItem",userInputtedTask)
            data.putExtra("position",position)
            setResult(RESULT_OK, data)
            finish()
        }
    }
}