package com.cyberavanza.fmr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.cyberavanza.fmr.adapters.ChatsListAdapter;
import com.cyberavanza.fmr.base.BaseActivity;
import com.cyberavanza.fmr.model.ChatListItem;
import com.cyberavanza.fmr.model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        loadBg();
        setTitle(getAuth().getCurrentUser().getDisplayName() + " Chat");

        try {
            Toolbar myToolbar = (Toolbar) findViewById(R.id.chatsToolbar);
            setSupportActionBar(myToolbar);
            getSupportActionBar().setElevation(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ProgressBar loadingList = (ProgressBar) findViewById(R.id.loadingList);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        View.OnClickListener listener = view -> startActivity(new Intent(ChatsActivity.this, MessagesActivity.class));
        ArrayList<ChatListItem> chatListItems = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();
        ArrayList<String> userIds = new ArrayList<>();

        ChatsListAdapter adapter = new ChatsListAdapter(chatListItems, this, position -> {
            Intent messagesActivity = new Intent(ChatsActivity.this, MessagesActivity.class);
            messagesActivity.putExtra("profileName",chatListItems.get(position).getName());
            messagesActivity.putExtra("userObj",users.get(position));
            startActivity(messagesActivity);
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        getUsersRef().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(!dataSnapshot.getKey().equals(getAuth().getCurrentUser().getUid())){
                    userIds.add(dataSnapshot.getKey());

                    User user = dataSnapshot.getValue(User.class);
                    users.add(user);
                    ChatListItem chatListItem = new ChatListItem("https://pickaface.net/assets/images/slides/slide4.png",user.getName(),"Tap To View Messages","00:00 AM");
                    chatListItems.add(chatListItem);
                    adapter.notifyItemInserted(chatListItems.size()-1);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //int itmIndex = userIds.indexOf(dataSnapshot.getKey());
                //chatListItems.get(itmIndex).setName();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        getUsersRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                loadingList.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
}
