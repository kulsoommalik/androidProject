package com.example.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    EditText ed1, ed2;
    Button addButton, retButton;
    TextView tv;
    String allnames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = openOrCreateDatabase("MSE", MODE_PRIVATE, null); //creating db -> factory as layout

        ed1 = (EditText) findViewById(R.id.etUser);
        ed2 = (EditText) findViewById(R.id.etPass);
        addButton = (Button) findViewById(R.id.btnAdd);
        retButton = (Button) findViewById(R.id.btnRet);
        tv = (TextView) findViewById(R.id.tvData);

        String createTable = "CREATE TABLE IF NOT EXISTS signup" + "(name VARCHAR, password VARCHAR)"; // sql command
        db.execSQL(createTable); //executing command

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ed1.getText().toString();
                String pass = ed2.getText().toString();
                String insert = "INSERT INTO signup VALUES ('"+name+"', '"+pass+"') ";
                db.execSQL(insert);
                Toast.makeText(MainActivity.this, "Added successfully", Toast.LENGTH_SHORT ).show();
            }
        });

        retButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor c = db.rawQuery("SELECT * FROM signup", null);
                Log.d("count: ", String.valueOf(c.getCount()));
                if(c.getCount() > 0){
                    while (c.moveToNext()){
                        int index = c.getColumnIndex("name"); //getting index of name field
                        String name = c.getString(index);

                        allnames =  allnames + name + "\n";
                        tv.setText(allnames);
                        Log.d("index: ", String.valueOf(index));
                        Log.d("val: ", name);
                        //Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT ).show();
                        //String pass = c.getString(1);
                    }
                }
            }
        });
    }
}
