package com.example.todolistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.todolistapp.Adapters.TaskAdapter;
import com.example.todolistapp.Adapters.TaskViewModel;
import com.example.todolistapp.Models.Tasks;

import java.util.ArrayList;

public class CategoryListActivity extends AppCompatActivity {

    int categoryID = 0;
    String categoryName="";
    RecyclerView rv;
    ArrayList<Tasks> tasks = new ArrayList<>();
    EditText createTask;
    private com.example.todolistapp.Adapters.AddNewTaskViewModel mViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        rv = findViewById(R.id.task_rv);
        createTask = findViewById(R.id.creattask);

        categoryID =  getIntent().getExtras().getInt("categoryID");
        categoryName =  getIntent().getExtras().getString("categoryName");

        TextView  textCategoryName =(TextView)findViewById(R.id.note_name);
        textCategoryName.setText(categoryName);
        TaskAdapter adapter = new TaskAdapter(tasks, tasks -> {
//               Toast.makeText(CategoryListActivity.this, "clicked", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(getApplicationContext(), MeetActivity.class));

        }, rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);

        TaskViewModel mtaskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        //update ui
        //update rv
        //                Toast.makeText(CategoryListActivity.this, "on changed work", Toast.LENGTH_SHORT).show();
        mtaskViewModel.getmAllTasks(categoryID).observe(this, adapter::setTasks);

        mViewModel = ViewModelProviders.of(this).get(com.example.todolistapp.Adapters.AddNewTaskViewModel.class);


    }


    public void back(View view) {
        startActivity(new Intent(getApplicationContext(), ListsActivity.class));
    }

    public void savetask(View view) {
        String task = createTask.getText().toString();
        if (TextUtils.isEmpty(task)) {
            createTask.setError("task name is required");
        } else {
            mViewModel.insert(new Tasks(task,categoryID));
        }
    }

    public void searchResults(View view) {
      Intent meetActivity=  new Intent(getApplicationContext() , MeetActivity.class);
        meetActivity.putExtra("categoryID",categoryID);
        meetActivity.putExtra("categoryName",categoryName);
        startActivity(meetActivity);
    }
}