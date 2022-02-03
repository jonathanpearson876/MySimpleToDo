package com.example.jonathanssimpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.FileUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLockClickListener {
            override fun onItemLongClicked(position: Int) {
                //1. Remove the item from the list
                listOfTasks.removeAt(position)
                //2. Notify the adapter that data has changed
                adapter.notifyDataSetChanged()

                saveItems()
            }

        }

        //1. Detect when the yser clicks the add button
/*        findViewById<Button>(R.id.button).setOnClickListener {
            //Code to be executed when the user clicks a button
            Log.i("Caren","User clicked on button")*/

        loadItems()

        // Look up recycler view in the layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter
        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)

        //Set up the button and input field so that the user can enter a task and add it

        val inputTextField = findViewById<EditText>(R.id.addTaskField)
        //Get a reference to the button
        //Set an OnClickListener to it
        findViewById<Button>(R.id.button).setOnClickListener {
            //1. Grab text that user has inputted into the edit text field @id/addTaskField
            val userInputtedTask = inputTextField.text.toString()

            //2. Add the string to our list of tasks: listOfTasks
            listOfTasks.add(userInputtedTask)

            //Notify the data adapter that data has been upated
            adapter.notifyItemInserted(listOfTasks.size - 1)

            //3. Reset the text field
            inputTextField.setText("")

            saveItems()
        }
    }

    //Save the data that the user inputs
    //Save the data by writing and reading from a file

    //Get the data file needed
    fun getDataFile(): File {

        //Every line will represent a specific task in our list of tasks
        return File(filesDir,"data.txt")
    }
    //Load the items by reading every line in the data file
    fun loadItems(){
        try {
            listOfTasks = org.apache.commons.io.FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException){
            ioException.printStackTrace()
        }
    }

    //Save items by writing them into our data file
    fun saveItems() {
        try {
            org.apache.commons.io.FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (ioException:IOException){
            ioException.printStackTrace()
        }
    }

    //Edit items in the data file

}

