package org.o7planning.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLData;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {

    TableLayout table;
    Button delButton;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        table = findViewById(R.id.sTbl);
        delButton = findViewById(R.id.clrBtn);
        addButton = findViewById(R.id.addBtn);
        addButton.performClick();
    }

    public void fillDataBase() {
        SQLiteDatabase db = openOrCreateDatabase("s.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS stud (name TEXT, weight INTEGER, growth INTEGER, age INTEGER);");
        //db.execSQL("DELETE FROM stud");
        ArrayList<String> nameArr = new ArrayList<>();
        nameArr.add("Name1");
        nameArr.add("Name2");
        nameArr.add("Name3");
        nameArr.add("Name4");
        nameArr.add("Name5");

        int index = ThreadLocalRandom.current().nextInt(0,4);
        String name = (String)(nameArr.get(index));
        Integer weight = ThreadLocalRandom.current().nextInt(50, 150);
        Integer growth = ThreadLocalRandom.current().nextInt(150, 210);
        Integer age = ThreadLocalRandom.current().nextInt(17, 26);


        db.execSQL("INSERT OR IGNORE INTO stud VALUES ('"+name+"','"+weight+"', '"+growth+"', '"+age+"');");
        db.close();
    }

    public void printDataBase() {
        SQLiteDatabase db = openOrCreateDatabase("s.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS stud (name TEXT, weight INTEGER, growth INTEGER, age INTEGER);");
        table.removeAllViews();
        Cursor query = db.rawQuery("SELECT * FROM stud ORDER BY age;", null);

        while(query.moveToNext()) {
            TextView name = new TextView(this);
            TextView weight = new TextView(this);
            TextView growth = new TextView(this);
            TextView age = new TextView(this);

            name.setText(query.getString(0));
            weight.setText(query.getString(1));
            growth.setText(query.getString(2));
            age.setText(query.getString(3));

            name.setGravity(Gravity.CENTER);
            weight.setGravity(Gravity.CENTER);
            growth.setGravity(Gravity.CENTER);
            age.setGravity(Gravity.CENTER);

            name.setLayoutParams(new
                    TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1f
            ));
            weight.setLayoutParams(new
                    TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1f
            ));
            growth.setLayoutParams(new
                    TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1f
            ));
            age.setLayoutParams(new
                    TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1f
            ));

            TableRow row = new TableRow(this);
            row.addView(name);
            row.addView(weight);
            row.addView(growth);
            row.addView(age);

            table.addView(row);
        }

        query.close();
        db.close();
    }

    public void clearDataBase() {
        SQLiteDatabase db = openOrCreateDatabase("s.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS stud (name TEXT, weight INTEGER, growth INTEGER, age INTEGER);");
        db.execSQL("DELETE FROM stud");
        table.removeAllViews();
    }

    public void onClickAdd(View view){
        fillDataBase();
        printDataBase();
    }

    public void onClickClear(View view) {
        clearDataBase();
    }
}