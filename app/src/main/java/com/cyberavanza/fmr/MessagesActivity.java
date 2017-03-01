package com.cyberavanza.fmr;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cyberavanza.fmr.adapters.MessagesAdapter;
import com.cyberavanza.fmr.base.BaseActivity;
import com.cyberavanza.fmr.base.BasicFunctionalities;
import com.cyberavanza.fmr.base.Utility;
import com.cyberavanza.fmr.model.Message;
import com.cyberavanza.fmr.model.MessageType;
import com.cyberavanza.fmr.model.User;
import com.cyberavanza.fmr.views.CustomFaView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MessagesActivity extends BaseActivity {

    boolean isAttchmentOptionsOpen = false;
    String convId;
    FirebaseStorage storage;
    ProgressDialog uploadDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        loadBg();
        try {
            final Toolbar myToolbar = (Toolbar) findViewById(R.id.chatsToolbar);
            setSupportActionBar(myToolbar);
            getSupportActionBar().setElevation(0);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        storage = FirebaseStorage.getInstance();

        uploadDialog = BasicFunctionalities.buildProgressDialog("Uploading...",this);

        TextView setToolbarTitle = (TextView) findViewById(R.id.setToolbarTitle);

        ProgressBar loadingList = (ProgressBar) findViewById(R.id.loadingList);

        setToolbarTitle.setText(getIntent().getStringExtra("profileName"));

        View chatToolbarProfileImage = findViewById(R.id.chatToolbarProfileImage);
        chatToolbarProfileImage.setOnClickListener(view -> startActivity(new Intent(MessagesActivity.this, ProfileActivity.class)));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.messagesList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        User userObj = (User) getIntent().getSerializableExtra("userObj");

        ArrayList<Message> messages = new ArrayList<>();
        MessagesAdapter adapter = new MessagesAdapter(messages, this);

        getCurrentUserRef().child("chats").child(userObj.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    convId = dataSnapshot.getValue(String.class);
                } else {
                    convId = "" + System.currentTimeMillis();
                }

                getCurrentUserRef().child("chats").child(userObj.getUid()).setValue(convId);
                getUsersRef().child(userObj.getUid()).child("chats").child(getAuth().getCurrentUser().getUid()).setValue(convId);

                DatabaseReference convers = getDatabase().getReference().child("conversations").child(convId);

                convers.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Message message = dataSnapshot.getValue(Message.class);
                        if (message.getUid().equals(getAuth().getCurrentUser().getUid())) {
                            message.setFromMe(true);
                        } else {
                            message.setFromMe(false);
                        }
                        messages.add(message);
                        adapter.notifyItemInserted(messages.size() - 1);
                        recyclerView.scrollToPosition(messages.size() - 1);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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

                convers.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        loadingList.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(messages.size() - 1);

        final View attachmentOptions = findViewById(R.id.attachmentOptions);
        View attachmentToggle = findViewById(R.id.attachmentToggle);
        attachmentToggle.setOnClickListener(view -> {
            if (!isAttchmentOptionsOpen) {
                attachmentOptions.animate().setDuration(250).translationY(-findViewById(R.id.fieldLayout).getHeight());
                isAttchmentOptionsOpen = true;
            } else {
                attachmentOptions.animate().setDuration(250).translationY(500);
                isAttchmentOptionsOpen = false;
            }
        });

        EditText messageTextInput = (EditText) findViewById(R.id.messageTextInput);
        CustomFaView sendBtn = (CustomFaView) findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(v -> {
            String messageText = messageTextInput.getText().toString();
            if (messageText.length() > 0) {
                messageTextInput.setText("");
                DatabaseReference databaseReference = getDatabase().getReference().child("conversations").child(convId);
                String messageKey = databaseReference.push().getKey();
                databaseReference.child(messageKey).setValue(new Message(messageKey, getAuth().getCurrentUser().getDisplayName(), "00:00", new Message.TextMessage(messageText), true, getAuth().getCurrentUser().getUid())).addOnCompleteListener(task -> {
                    //recyclerView.scrollToPosition(messages.size()-1);
                });
            }
        });

        recyclerView.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            recyclerView.scrollToPosition(messages.size() - 1);
        });


        Button btnImage = (Button) findViewById(R.id.btnImage);
        Button btnVoice = (Button) findViewById(R.id.btnVoice);
        Button btnDocument = (Button) findViewById(R.id.btnDocument);

        btnImage.setOnClickListener(v -> {
            selectImage();
        });

        btnVoice.setVisibility(View.GONE);
        btnDocument.setVisibility(View.GONE);

        btnDocument.setOnClickListener(v -> {

        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    String userChoosenTask;
    final int REQUEST_CAMERA = 100;
    final int SELECT_FILE = 200;

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(MessagesActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, (dialog, item) -> {
            boolean result= Utility.checkPermission(MessagesActivity.this);

            if (items[item].equals("Take Photo")) {
                userChoosenTask="Take Photo";
                if(result)
                    cameraIntent();

            } else if (items[item].equals("Choose from Library")) {
                userChoosenTask="Choose from Library";
                if(result)
                    galleryIntent();

            } else if (items[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    Toast.makeText(this,"Allow to use these Features",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                sendFileMessage(convertBitmapToByteArray(bm),System.currentTimeMillis()+".jpg",MessageType.IMAGE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream(bitmap.getWidth() * bitmap.getHeight());
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, buffer);
        return buffer.toByteArray();
    }

    private  void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        sendFileMessage(convertBitmapToByteArray(thumbnail),System.currentTimeMillis()+".jpg",MessageType.IMAGE);
        //ivImage.setImageBitmap(thumbnail);
    }


    private void sendFileMessage(byte[] bytes, String name, MessageType msgType) {
        uploadDialog.show();
        StorageReference storageRef;
        FirebaseOptions opts = FirebaseApp.getInstance().getOptions();
        storageRef = storage.getReferenceFromUrl("gs://" + opts.getStorageBucket());
                storageRef = storageRef.child(name);
                UploadTask uploadTask = storageRef.putBytes(bytes);
                uploadTask.addOnFailureListener(e -> {
                    Log.e("Error FILE",e.getMessage());
                    Toast.makeText(MessagesActivity.this,"File Upload Error!"+e.getMessage(),Toast.LENGTH_LONG).show();
                }).addOnSuccessListener(taskSnapshot -> {
                    DatabaseReference databaseReference = getDatabase().getReference().child("conversations").child(convId);
                    String messageKey = databaseReference.push().getKey();
                    Message msg = null;
                    switch (msgType){
                        case IMAGE:
                            msg = new Message(messageKey, getAuth().getCurrentUser().getDisplayName(), "00:00",
                                    new Message.ImageMessage(taskSnapshot.getDownloadUrl().toString()), true, getAuth().getCurrentUser().getUid());
                            break;
                        case VOICE:
                            msg = new Message(messageKey, getAuth().getCurrentUser().getDisplayName(), "00:00",
                                    new Message.VoiceMessage(taskSnapshot.getDownloadUrl().toString()), true, getAuth().getCurrentUser().getUid());
                            break;
                        case DOCUMENT:
                            msg = new Message(messageKey, getAuth().getCurrentUser().getDisplayName(), "00:00",
                                    new Message.DocumentMessage(name,taskSnapshot.getDownloadUrl().toString()), true, getAuth().getCurrentUser().getUid());
                            break;
                    }

                    if(msg != null){
                        databaseReference.child(messageKey).setValue(msg).addOnCompleteListener(task -> {
                            //recyclerView.scrollToPosition(messages.size()-1);
                            Toast.makeText(MessagesActivity.this,"Sent",Toast.LENGTH_SHORT).show();
                            uploadDialog.dismiss();
                        });
                    }
                }).addOnCompleteListener(task -> {
                    uploadDialog.dismiss();
                });

    }
}
