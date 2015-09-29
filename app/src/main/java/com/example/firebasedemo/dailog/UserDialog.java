package com.example.firebasedemo.dailog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.firebasedemo.R;

/**
 * Created by DSK02 on 9/25/2015.
 */
public class UserDialog {
    private Context context;
    private EditText nameEditText, ageEditText;
    private String name;
    private String age;
    private String title;
    private UserDialogListner listner;

    public interface UserDialogListner {
        void onOkClick(String name, int age);
    }

    public UserDialog(Context context) {
        this.context = context;
    }

    public Dialog show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_add_user, null);
        nameEditText = (EditText) view.findViewById(R.id.name);
        ageEditText = (EditText) view.findViewById(R.id.age);
        if (!TextUtils.isEmpty(name)) {
            nameEditText.setText(name);
        }
        if (!TextUtils.isEmpty(age)) {
            ageEditText.setText(age);
        }
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (listner != null) {
                    String nameString = nameEditText.getText().toString();
                    String ageString = ageEditText.getText().toString();
                    int ageInt = TextUtils.isEmpty(ageString) ? 0 : Integer.parseInt(ageString);
                    if (listner != null)
                        listner.onOkClick(nameString, ageInt);
                }
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.setView(view);
        return builder.show();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setListner(UserDialogListner listner) {
        this.listner = listner;
    }
}
