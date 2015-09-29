package com.example.firebasedemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.firebasedemo.R;
import com.example.firebasedemo.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DSK02 on 9/25/2015.
 */
public class UserListAdapter extends BaseAdapter {

    public interface UserListener {
        void onClick(User user);

        void onDelete(User user);

        void onEdit(User user);
    }

    private Context context;
    private List<User> usersList = new ArrayList<>();
    private UserListener userListener;

    public UserListAdapter(Context context) {
        this.context = context;
    }

    public void setUserListener(UserListener userListener) {
        this.userListener = userListener;
    }

    public void addUserAndNotify(User user) {
        usersList.add(user);
        notifyDataSetChanged();
    }

    public void updateUser(User user) {
        int updateIndex = -1;
        for (int i = 0; i < usersList.size(); i++) {
            if (usersList.get(i).getUserId().equals(user.getUserId())) {
                updateIndex = i;
                break;
            }
        }
        if (updateIndex != -1) {
            usersList.remove(updateIndex);
            usersList.add(updateIndex, user);
        }
    }

    public void deleteUser(User user) {
        int updateIndex = -1;
        for (int i = 0; i < usersList.size(); i++) {
            if (usersList.get(i).getUserId().equals(user.getUserId())) {
                updateIndex = i;
                break;
            }
        }
        if (updateIndex != -1) {
            usersList.remove(updateIndex);
        }
    }

    public void addUser(User user) {
        usersList.add(user);
    }

    public void removeAll() {
        usersList.clear();
    }

    @Override
    public int getCount() {
        return usersList.size();
    }

    @Override
    public User getItem(int i) {
        return usersList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        final ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.row_user_item, viewGroup, false);
            holder = new ViewHolder();
            holder.name = (TextView) view.findViewById(R.id.name);
            holder.age = (TextView) view.findViewById(R.id.age);
            holder.vote = (TextView) view.findViewById(R.id.vote);
            holder.deleteButton = (ImageButton) view.findViewById(R.id.deleteButton);
            holder.linearLayout = (LinearLayout) view.findViewById(R.id.rootView);
            holder.editButton = (ImageButton) view.findViewById(R.id.editButton);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final User user = usersList.get(i);
        holder.name.setText("Name: " + user.getName());
        holder.age.setText("Age: " + user.getAge());
        holder.vote.setText("Vote: " + user.getVote());
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userListener != null)
                    userListener.onDelete(user);
            }
        });
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userListener != null)
                    userListener.onEdit(user);
            }
        });
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userListener != null) {
                    userListener.onClick(user);
                }
            }
        });
        return view;
    }

    private class ViewHolder {
        private TextView name, age, vote;
        private ImageButton deleteButton, editButton;
        private LinearLayout linearLayout;
    }
}
