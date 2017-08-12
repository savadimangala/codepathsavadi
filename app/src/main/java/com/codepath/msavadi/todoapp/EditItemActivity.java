package com.codepath.msavadi.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    EditText etEditText;
    public final static String EXTRA_MESSAGE = "com.example.ListViewTest.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        etEditText = (EditText) findViewById(R.id.etEdit);
        Intent resultIntent = getIntent();
        etEditText.setText(resultIntent.getStringExtra("listItem"));
        etEditText.setSelection(etEditText.getText().length());

    }


    public void onSave(View view) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result",etEditText.getText().toString());
        setResult(EditItemActivity.RESULT_OK,returnIntent);
        finish();
    }
}
