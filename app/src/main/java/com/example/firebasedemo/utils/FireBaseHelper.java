package com.example.firebasedemo.utils;

import com.firebase.client.Firebase;

/**
 * Created by DSK02 on 9/25/2015.
 */
public class FireBaseHelper {
    private static final String USER = "user";
    private static final String MESSAGE = "message";
    private static final String USER_LIST = "userList";

    private static FireBaseHelper fireBaseHelper;
    private static Firebase firebase;

    public static FireBaseHelper get() {
        if (fireBaseHelper == null) {
            fireBaseHelper = new FireBaseHelper();

        }
        return fireBaseHelper;
    }

    public FireBaseHelper() {
        firebase = new Firebase("https://kishandemo.firebaseio.com/");
    }

    public Firebase getRoot() {
        return firebase;
    }

    public Firebase getUserObject() {
        return firebase.child(USER);
    }

    public Firebase getUserListObject() {
        return firebase.child(USER_LIST);
    }

    public Firebase getMessageObject() {
        return firebase.child(MESSAGE);
    }

    public void saveMessage(String message) {
        firebase.child(MESSAGE).setValue(message);
    }
}
