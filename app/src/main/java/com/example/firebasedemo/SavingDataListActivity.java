package com.example.firebasedemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.firebasedemo.adapter.UserListAdapter;
import com.example.firebasedemo.dailog.UserDialog;
import com.example.firebasedemo.model.User;
import com.example.firebasedemo.utils.FireBaseHelper;
import com.example.firebasedemo.utils.Utils;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;
import com.firebase.client.ValueEventListener;

/**
 * Created by DSK02 on 9/25/2015.
 */
public class SavingDataListActivity extends AppCompatActivity implements UserListAdapter.UserListener {
    private ListView userListView;
    private UserListAdapter listAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_data_list);
        this.userListView = (ListView) findViewById(R.id.userListView);
        listAdapter = new UserListAdapter(this);
        userListView.setAdapter(listAdapter);
//        getUserList();
        addUserListener();
        listAdapter.setUserListener(this);
    }

    private void addUserListener() {
        FireBaseHelper.get().getUserListObject().orderByChild("name").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("Demo", "onChildAdded " + dataSnapshot.getKey());
                User newPost = dataSnapshot.getValue(User.class);
                newPost.setUserId(dataSnapshot.getKey());
                listAdapter.addUserAndNotify(newPost);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("Demo", "onChildChanged " + dataSnapshot.getKey());
                User newPost = dataSnapshot.getValue(User.class);
                newPost.setUserId(dataSnapshot.getKey());
                listAdapter.updateUser(newPost);
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d("Demo", "onChildRemoved " + dataSnapshot.getKey());
                User deletedUser = dataSnapshot.getValue(User.class);
                deletedUser.setUserId(dataSnapshot.getKey());
                listAdapter.deleteUser(deletedUser);
                listAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void getUserList() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        FireBaseHelper.get().getUserListObject().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("There are " + snapshot.getChildrenCount() + " Users");
                progressDialog.dismiss();
                listAdapter.removeAll();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    User post = postSnapshot.getValue(User.class);
                    post.setUserId(postSnapshot.getKey());
                    listAdapter.addUser(post);
                }
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            openUserDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openUserDialog() {
        UserDialog userDialog = new UserDialog(this);
        userDialog.setTitle("Add");
        userDialog.setListner(new UserDialog.UserDialogListner() {
            @Override
            public void onOkClick(String name, int age) {
                addUser(name, age);
            }
        });
        userDialog.show();
    }

    private void addUser(String name, int age) {
        final User user = new User(name, age);
        Firebase firebase = FireBaseHelper.get().getUserListObject().push();
        firebase.setValue(user, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    Toast.makeText(getContext(), "Data could not be saved. " + firebaseError.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Utils.makeToast(getContext(), "Data saved successfully.");
                    // user is added and adapter is notify by
                    // addValueEventListener -> onDataChange;
                    // addChildEventListener -> onChildAdded;
                }
            }
        });

    }

    private Context getContext() {
        return SavingDataListActivity.this;
    }

    @Override
    public void onClick(User user) {
        Firebase firebase = FireBaseHelper.get().getUserListObject().child(user.getUserId()).child(User.FIELD_VOTE);
        firebase.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData currentData) {
                if (currentData.getValue() == null) {
                    currentData.setValue(1);
                } else {
                    currentData.setValue((Long) currentData.getValue() + 1);
                }
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(FirebaseError firebaseError, boolean b, DataSnapshot dataSnapshot) {
                if (firebaseError == null) {
                    Log.d("Demo", "runTransaction onComplete " + dataSnapshot.getKey());
                    // user is updated and adapter notify by
                    // addChildEventListener -> onChildChanged;
                }
            }
        });
    }

    @Override
    public void onDelete(User user) {
        // delete user object
        FireBaseHelper.get().getUserListObject().child(user.getUserId()).setValue(null);
        // user is deleted and adapter notify by
        // addChildEventListener -> onChildRemoved;
    }

    @Override
    public void onEdit(final User user) {
        UserDialog userDialog = new UserDialog(this);
        userDialog.setTitle("Update");
        userDialog.setName(user.getName());
        userDialog.setAge(user.getAge() + "");
        userDialog.setListner(new UserDialog.UserDialogListner() {
            @Override
            public void onOkClick(String name, int age) {
                user.setName(name);
                user.setAge(age);
                FireBaseHelper.get().getUserListObject().child(user.getUserId()).setValue(user);
                // user is updated and adapter notify by
                // addChildEventListener -> onChildChanged;
            }
        });
        userDialog.show();
    }
}
