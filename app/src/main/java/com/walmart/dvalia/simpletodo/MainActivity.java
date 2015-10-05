package com.walmart.dvalia.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> todoItems;
    ArrayAdapter<String> aToDoAdapter;
    ListView lvItems;
    EditText etText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        populateArrayItems();
        lvItems = (ListView)findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAdapter);

        etText = (EditText)findViewById(R.id.etEditText);

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                todoItems.remove(position);
                aToDoAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedValue = todoItems.get(position).toString();
                //Toast.makeText(getApplicationContext(),selectedValue,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
                intent.putExtra("item", selectedValue);
                intent.putExtra("position", position);

                //startActivity(intent);

                startActivityForResult(intent, 200);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above

        // Extract name value from result extras
        String name = data.getExtras().getString("item");
        int position = data.getExtras().getInt("position", 0);

        todoItems.set(position, name);
       // todoItems.add(position,name);
        aToDoAdapter.notifyDataSetChanged();

       // aToDoAdapter.insert(name, position);
        writeItems();

        // Toast the name to display temporarily on screen
       // Toast.makeText(this, name + position, Toast.LENGTH_SHORT).show();

    }

    private void populateArrayItems(){

//        todoItems = new ArrayList<String>();
//        todoItems.add("item1");
//        todoItems.add("item2");
//        todoItems.add("item3");
//        todoItems.add("item4");

        readItems();

        aToDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);

    }

    private void readItems(){

        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");

        try{

            todoItems = new ArrayList<String>(FileUtils.readLines(file));


        }catch (IOException e){

            todoItems = new ArrayList<String>();
        }
    }

    private void writeItems(){

        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");

        try{

           FileUtils.writeLines(file, todoItems);


        }catch (IOException e){

            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

    public void onAddItem(View view) {

        String text = etText.getText().toString();
        aToDoAdapter.add(text);
        etText.setText("");
        writeItems();
    }
}
