package com.example.firebasedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.firebasedemo.utils.FireBaseHelper;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by DSK02 on 9/25/2015.
 */
public class MessageActivity extends AppCompatActivity {
    private EditText message;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        save = (Button) findViewById(R.id.save);
        message = (EditText) findViewById(R.id.message);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveMessage();
            }
        });

        //Reading data from your Firebase database is accomplished by attaching an event listener and handling the resulting events.
        FireBaseHelper.get().getMessageObject().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("onDataChange message", dataSnapshot.getValue().toString());
                message.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e("Error", firebaseError.toString());
            }
        });
    }

    private void saveMessage() {
        FireBaseHelper.get().saveMessage(message.getText().toString());
    }
}
