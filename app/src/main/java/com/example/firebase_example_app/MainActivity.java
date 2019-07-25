package com.example.firebase_example_app;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnSend, btnRead, btnDelete;
    EditText text1;
    ListView listView;
    ArrayAdapter<String> adapter;
    DataModel dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSend = findViewById(R.id.btnSave);
        btnRead = findViewById(R.id.btnRead);
        btnDelete = findViewById(R.id.btnDel);
        text1 = findViewById(R.id.editText);
        listView = findViewById(R.id.listView);


        //Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reference = database.getReference("Data");
        ///

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = text1.getText().toString();
                saveDatabase(reference, str);

            }
        });
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.item, R.id.txtData, readDatabase(reference));
                listView.setAdapter(adapter);
                listView.setClickable(false);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDatabase(reference);
            }
        });

    }


    void saveDatabase(DatabaseReference ref, String name) {
        DataModel dm1 = new DataModel();
        dm1.setStr(name);
        ref.push().setValue(dm1);
    }

    ArrayList<String> readDatabase(DatabaseReference ref) {
        final ArrayList<String> datas = new ArrayList<String>();
        final int[] i = {1};
        datas.add(0, "Names : ");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.i("Database Test", String.valueOf(ds.child("str").getValue()));
                    datas.add(i[0], String.valueOf(ds.child("str").getValue()));
                    i[0]++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        this.text1.selectAll();
        return datas;
    }

    void deleteDatabase(DatabaseReference ref) {
        ref.removeValue();

    }
}


class DataModel {
    String str;

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}

