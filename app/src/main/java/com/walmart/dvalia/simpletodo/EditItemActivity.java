package com.walmart.dvalia.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    EditText etText;
    int position;
    String item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
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

        position = getIntent().getIntExtra("position", 0);
        item = getIntent().getStringExtra("item");
        etText = (EditText)findViewById(R.id.etText);
        etText.setText(item);
        etText.setSelection(item.length());

    }

    public void onEditSave(View view) {

        Intent retData = new Intent();

        retData.putExtra("item", etText.getText().toString());
        retData.putExtra("position", position);

        // Activity finished ok, return the data
        setResult(RESULT_OK, retData); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }
}
