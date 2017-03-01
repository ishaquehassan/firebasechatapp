package com.cyberavanza.fmr;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cyberavanza.fmr.base.BaseActivity;
import com.cyberavanza.fmr.base.BasicFunctionalities;
import com.kelvinapps.rxfirebase.RxFirebaseAuth;
import com.kelvinapps.rxfirebase.RxFirebaseUser;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loadBg();
        View in = findViewById(R.id.signIn);
        View up = findViewById(R.id.signUp);

        EditText textEmail = (EditText) findViewById(R.id.textEmail);
        EditText textPassword = (EditText) findViewById(R.id.textPassword);

        in.setOnClickListener(v -> {
            String email = textEmail.getText().toString();
            String password = textPassword.getText().toString();

            if(email.length() <= 0){
                textEmail.setError("Please enter email");
                return;
            }

            if(password.length() <= 0){
                textPassword.setError("Please enter password");
                return;
            }

            ProgressDialog pd = BasicFunctionalities.buildProgressDialog("Signing Up",LoginActivity.this);
            pd.show();

            RxFirebaseAuth.signInWithEmailAndPassword(getAuth(),email,password)
                    .flatMap(x -> RxFirebaseUser.getToken(getAuth().getCurrentUser(),false))
                    .subscribe(token -> {
                        Toast.makeText(LoginActivity.this,"Logged In!",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this,ChatsActivity.class));
                        finish();
                        pd.dismiss();
                    },throwable -> {
                        pd.dismiss();
                        Toast.makeText(LoginActivity.this,throwable.getMessage()+"",Toast.LENGTH_SHORT).show();
                    });
        });


        up.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
