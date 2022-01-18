package com.example.simpletodo

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.SnackbarContentLayout
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter
    // REQUEST_CODE can be any value we like, used to determine the result type later
    private val REQUEST_CODE = 20

    //Adds custom support action bar buttons to display
    override fun onCreateOptionsMenu(menu: Menu?): Boolean{
        //Action Bar tasks
        var actionBar = supportActionBar
        actionBar!!.title = "Main Task"
        menuInflater.inflate(R.menu.activity_main_menu,menu)

        return true
    }

    //Defines what happens when user selects the Delete All button.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var builder = AlertDialog.Builder(this)
        when(item.itemId){
            R.id.delete_all -> {
                Toast.makeText(this,"Delete all items in list", Toast.LENGTH_SHORT).show()
                builder.setTitle("Alert!")
                    .setMessage("Permanently delete all?")
                    .setCancelable(true)
                    .setNegativeButton("No"){dialogInterface,it -> dialogInterface.cancel()}
                    .setPositiveButton("Yes!"){dialogInterface,it ->
                            listOfTasks.clear()
                            adapter.notifyDataSetChanged()
                            saveItems()
                    }
                    .show()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                // 1. Remove the item from the list
                listOfTasks.removeAt(position)
                // 2. Notify the adapter that our data set has changed
                adapter.notifyDataSetChanged()

                saveItems()
            }
        }

        // Uses explicit intent to edit task item in separate window
        val onShortClickListener = object : TaskItemAdapter.OnShortClickListener{
            override fun onItemShortClicked(position: Int) {
                // first parameter is the context, second is the class of the activity to launch
                val i = Intent(this@MainActivity, EditActivity::class.java)
                i.putExtra("original task",listOfTasks.get(position))
                i.putExtra("position",position)
                startActivityForResult(i, REQUEST_CODE) // brings up the second activity
            }
        }

        loadItems()

        // Look up recyclerView in layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener, onShortClickListener)
        // Attach the adapter to the recyclerview to populate  items
        recyclerView.adapter = adapter
        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set up the button and input field, so that the user can enter a task and add it to the list

        val inputTextField = findViewById<EditText>(R.id.addTaskField)

        // Get a reference to the button
        // and then set an onclickListener
        findViewById<Button>(R.id.button).setOnClickListener{
            // 1. Grab the text the user has inputted into @id/addTaskField
            val userInputtedTask = inputTextField.text.toString()

            // 2. Add the string to our list of tasks: listOfTasks
            listOfTasks.add(userInputtedTask)

            // Notify the adapter that the data has been updated
            adapter.notifyItemInserted(listOfTasks.size - 1)

            // 3. Reset text field
            inputTextField.setText("")

            saveItems()

        }

    }

    // Save the data that the user has inputted
    // Save data by writing and reading from a file
    // Get the file we need
    fun getDataFile() : File {

        // Every line is going to represent a specific task in our list of tasks
        return File(filesDir,"data.text")
    }

    // Load the items by reading every line in the data file
    fun loadItems(){
        try{
            listOfTasks = org.apache.commons.io.FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch(ioException: IOException){
            ioException.printStackTrace()
        }
    }

    // Save items by writing them into our data file
    fun saveItems(){
        try{
            org.apache.commons.io.FileUtils.writeLines(getDataFile(),listOfTasks)
        } catch(ioException: IOException){
            ioException.printStackTrace()
        }

    }


    // Upon finishing edit, updates the task list
    override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // REQUEST_CODE is defined above
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            val updatedTask = data?.getExtras()?.getString("editedItem","")
            val position = data?.getExtras()?.getInt("position",0)
            listOfTasks.set(position ?: 0,updatedTask ?: "")
            // 2. Notify the adapter that our data set has changed
            adapter.notifyDataSetChanged()
            saveItems()
        }
    }
}
