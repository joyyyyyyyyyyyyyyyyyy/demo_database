package sg.edu.rp.c346.id22022096.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import kotlin.NotImplementedError;

public class MainActivity extends AppCompatActivity {

    Button btnInsert, btnGetTasks;
    TextView tvResults;
    ArrayList<Task> al;
    EditText entertask, enterdate;

    ArrayAdapter<Task> adapter;
    ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInsert = findViewById(R.id.btnInsert);
        btnGetTasks = findViewById(R.id.btnGetTasks);
        tvResults = findViewById(R.id.tvResults);
        lv = findViewById(R.id.lv);
        entertask = findViewById(R.id.entertask);
        enterdate = findViewById(R.id.enterdate);

        al = new ArrayList<>();
        adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, al);
        lv.setAdapter(adapter);

        btnInsert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Create the DBHelper object, passing in the
                // activity's Context
                DBHelper db = new DBHelper(MainActivity.this);

                // Insert a task
                //db.insertTask("Submit RJ", "25 Apr 2021");

                // get user input
                String task = entertask.getText().toString();
                String date = enterdate.getText().toString();

                // insert task into database
                db.insertTask(task, date);

                entertask.setText("");
                enterdate.setText("");

                // retrieve all tasks from database table
                al = db.getTasks();
                adapter.notifyDataSetChanged();
                db.close();

            }
        });

        btnGetTasks.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Create the DBHelper object, passing in the
                // activity's Context
                DBHelper db = new DBHelper(MainActivity.this);

                // Insert a task
                //ArrayList<String> data = db.getTaskContent();
                // getTasks -> able to retrieve all data fields
                ArrayList<Task> data = db.getTasks();
                ArrayList<String> data2 = db.getTaskContent();
                db.close();

                ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,data );
                lv.setAdapter(adapter);
                // retrieve all tasks from database table
                //al = db.getTasks();
                //adapter.notifyDataSetChanged();
                //db.close();

                String txt = "";
                for (int i = 0; i < data.size(); i++) {
                    Log.d("Database Content", i +". "+data.get(i));
                    txt += i + ". " + data.get(i) + "\n";
                }
                tvResults.setText(txt);
            }
        });

    }
}
