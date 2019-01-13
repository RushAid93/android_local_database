package com.freelance.shido.testapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    DatabaseHelper mDatabaseHelper;
    Button btn_add, btn_delete, btn_view;
    EditText et_name, et_age;
    ListView list_users;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        btn_add = findViewById(R.id.btn_add);
        btn_delete = findViewById(R.id.btn_delete);
        btn_view = findViewById(R.id.btn_view_all);
        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        list_users = findViewById(R.id.list_users);
        mDatabaseHelper = new DatabaseHelper(this);

        btn_add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("[Button Add]","Add Button is clicked.");
                if(TextUtils.isEmpty(et_name.getText().toString())) {
                    et_name.setError("Please insert a name!");
                }
                else if(TextUtils.isEmpty(et_age.getText().toString())) {
                    et_age.setError("Please insert the age!");
                }
                else{
                    AddData(et_name.getText().toString(), Integer.parseInt(et_age.getText().toString()));
                }
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.d("[Button Delete]", "Delete Button is clicked.");
            }
        });

        btn_view.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.d("[Button View]", "View All Button is clicked.");
                PopulateListview();
            }
        });
    }

    public void AddData(String name, Integer age){
        boolean insertData = mDatabaseHelper.addData(name, age);
        if(insertData){
            Toast.makeText(this, "Successfully insert data.", Toast.LENGTH_LONG).show();
            et_name.setText("");
            et_age.setText("");
            et_name.requestFocus();
        }
        else{
            Toast.makeText(this, "Failed to insert data...", Toast.LENGTH_LONG).show();
        }
    }

    public void PopulateListview(){
        Log.d("[Populate ListView]", "Displaying data in listview");
        Cursor data = mDatabaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            //get value from database in column 1, add it to ArrayList
            listData.add(data.getString(0));
        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        list_users.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
