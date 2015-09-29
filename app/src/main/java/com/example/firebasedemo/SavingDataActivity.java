package com.example.firebasedemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firebasedemo.model.User;
import com.example.firebasedemo.utils.FireBaseHelper;
import com.example.firebasedemo.utils.Utils;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DSK02 on 9/25/2015.
 */
public class SavingDataActivity extends AppCompatActivity {
    private EditText name;
    private EditText age;
    private Button save, saveName, deleteUserButton, updateName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_data);
        this.save = (Button) findViewById(R.id.save);
        this.age = (EditText) findViewById(R.id.age);
        this.name = (EditText) findViewById(R.id.name);
        this.saveName = (Button) findViewById(R.id.saveName);
        this.deleteUserButton = (Button) findViewById(R.id.deleteUser);
        this.updateName = (Button) findViewById(R.id.updateName);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUser();
            }
        });
        saveName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserName();
            }
        });
        deleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser();
            }
        });
        updateName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserName();
            }
        });
//        FireBaseHelper.get().getRoot().addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    User user = dataSnapshot.getValue(User.class);
//                    if (user != null) {
//                        name.setText(user.getName());
//                        age.setText(user.getAge() + "");
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//                Log.e("Error", firebaseError.toString());
//            }
//        });
    }

    private void updateUserName() {
        Map<String, Object> stringMap = new HashMap<>();
        stringMap.put(User.FIELD_NAME, name.getText().toString());
        FireBaseHelper.get().getUserObject().updateChildren(stringMap);
    }

    private void deleteUser() {
        FireBaseHelper.get().getUserObject().setValue(null);
    }

    private void saveUserName() {
        FireBaseHelper.get().getUserObject().child(User.FIELD_NAME).setValue(name.getText().toString());
    }

    private void saveUser() {
        User user = new User(name.getText().toString(), Integer.parseInt(age.getText().toString()));
        FireBaseHelper.get().getUserObject().setValue(user, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    Toast.makeText(getContext(), "Data could not be saved. " + firebaseError.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Utils.makeToast(getContext(), "Data saved successfully.");
                }
            }
        });
    }

    private Context getContext() {
        return SavingDataActivity.this;
    }
}
