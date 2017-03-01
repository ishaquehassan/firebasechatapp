package com.cyberavanza.fmr;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cyberavanza.fmr.base.BaseActivity;
import com.cyberavanza.fmr.base.BasicFunctionalities;
import com.cyberavanza.fmr.model.User;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.kelvinapps.rxfirebase.RxFirebaseAuth;
import com.kelvinapps.rxfirebase.RxFirebaseUser;

public class SignUpActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        loadBg();
        View in = findViewById(R.id.signIn);
        View up = findViewById(R.id.signUp);

        EditText textName = (EditText) findViewById(R.id.textName);
        EditText textEmail = (EditText) findViewById(R.id.textEmail);
        EditText textPassword = (EditText) findViewById(R.id.textPassword);

        up.setOnClickListener(v -> {
            String name = textName.getText().toString();
            String email = textEmail.getText().toString();
            String password = textPassword.getText().toString();

            if(name.length() <= 0){
                textName.setError("Please enter name");
                return;
            }

            if(email.length() <= 0){
                textEmail.setError("Please enter email");
                return;
            }

            if(password.length() <= 0){
                textPassword.setError("Please enter password");
                return;
            }

            ProgressDialog pd = BasicFunctionalities.buildProgressDialog("Signing Up",SignUpActivity.this);
            pd.show();

            RxFirebaseAuth.createUserWithEmailAndPassword(getAuth(),email,password)
                    .flatMap(x -> RxFirebaseUser.getToken(getAuth().getCurrentUser(),false))
                    .subscribe(token -> {
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .setPhotoUri(Uri.parse("http://alehsaan.com/apps-admin/assets/uploads/user.png"))
                                .build();
                        getAuth().getCurrentUser().updateProfile(profileUpdates).addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                User u = new User(name,email,getAuth().getCurrentUser().getUid());
                                getUsersRef().child(getAuth().getCurrentUser().getUid()).setValue(u);
                                Toast.makeText(SignUpActivity.this,"Signed Up!",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUpActivity.this,ChatsActivity.class));
                                finish();
                            }else{
                                Toast.makeText(SignUpActivity.this,task.getException().getMessage()+"",Toast.LENGTH_SHORT).show();
                            }
                            pd.dismiss();
                        });
                    },throwable -> {
                        Toast.makeText(SignUpActivity.this,throwable.getMessage()+"",Toast.LENGTH_SHORT).show();
                    });
        });



        in.setOnClickListener(view -> {
            Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
