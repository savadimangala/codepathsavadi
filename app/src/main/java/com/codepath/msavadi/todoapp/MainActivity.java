package com.codepath.msavadi.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnAdd;
    ArrayList<String> todoItems;
    ArrayAdapter<String> aToDoAdapter;
    ListView lvItems;
    EditText etEditText;
    public int positionX=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItem();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAdapter);
        etEditText = (EditText) findViewById(R.id.etText);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                todoItems.remove(i);
                aToDoAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                positionX = position;
                Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
                intent.putExtra("listItem",lvItems.getItemAtPosition(position).toString());
                startActivityForResult(intent,1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            todoItems.set(positionX,data.getExtras().getString("result"));
            writeItems();
            aToDoAdapter.notifyDataSetChanged();
        }

    }

    private void populateArrayItem() {
        readItems();
        aToDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
    }

    private void readItems(){
        File filesDir = getFilesDir();
        File file = new File(filesDir,"todo.txt");
        try{
            todoItems = new ArrayList<String>(FileUtils.readLines(file));
        } catch (FileNotFoundException e) {
            todoItems = new ArrayList();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void writeItems(){
        File filesDir = getFilesDir();
        File file = new File(filesDir,"todo.txt");
        try{
            FileUtils.writeLines(file,todoItems);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void onAddItem(View view) {
        aToDoAdapter.add(etEditText.getText().toString());
        etEditText.setText("");
        writeItems();
    }
}
