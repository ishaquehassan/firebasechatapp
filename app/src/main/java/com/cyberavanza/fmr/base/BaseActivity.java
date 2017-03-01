package com.cyberavanza.fmr.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cyberavanza.fmr.R;
import com.cyberavanza.fmr.SplashActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BaseActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseDatabase database;

    private boolean fragmentAddToBackStack = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            auth = FirebaseAuth.getInstance();
            database = FirebaseDatabase.getInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isFragmentAddToBackStack() {
        return fragmentAddToBackStack;
    }

    public void setFragmentAddToBackStack(boolean fragmentAddToBackStack) {
        this.fragmentAddToBackStack = fragmentAddToBackStack;
    }

    protected void loadBg(){
        Glide.with(this).load(R.drawable.pattern).diskCacheStrategy(DiskCacheStrategy.RESULT).into(((ImageView)findViewById(R.id.bgImage)));
        Log.e("BG Load","loaded");
    }

    public FirebaseAuth getAuth() {
        return auth;
    }

    public void setAuth(FirebaseAuth auth) {
        this.auth = auth;
    }

    public FirebaseDatabase getDatabase() {
        return database;
    }

    public void setDatabase(FirebaseDatabase database) {
        this.database = database;
    }

    public DatabaseReference getUsersRef(){
        return getDatabase().getReference().child(getResources().getString(R.string.users_node));
    }

    public DatabaseReference getCurrentUserRef(){
        return getDatabase().getReference().child(getResources().getString(R.string.users_node)).child(getAuth().getCurrentUser().getUid());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.signOut:
                getAuth().signOut();
                startActivity(new Intent(this, SplashActivity.class));
                finish();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
