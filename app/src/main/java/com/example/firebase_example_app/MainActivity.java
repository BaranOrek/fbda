package com.example.firebase_example_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    Button btnSend;
    EditText text1;
    DataModel dm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSend=findViewById(R.id.button);
        text1=findViewById(R.id.editText);
        //Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reference = database.getReference("Data");
        ///

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = text1.getText().toString();
                Log.i("Strin kontrol",str);
                reference.setValue(str);

            }
        });


    }
}

class DataModel{
    String str;

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}