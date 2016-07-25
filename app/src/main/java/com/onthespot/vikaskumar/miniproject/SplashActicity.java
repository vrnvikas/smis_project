package com.onthespot.vikaskumar.miniproject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SplashActicity extends AppCompatActivity {

    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        registerButton = (Button) findViewById(R.id.register_button);
        setFragment(LoginFragment.newInstance("","",getBaseContext()));


        if(isLoggedIn()){
            startActivityMain();
        }

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (registerButton.getText().toString().equals("SIGN UP")) {
                    registerButton.setText("LogIn");
                    setFragment(FragmentSignUp.newInstance("", "", getBaseContext()));
                }else {
                    registerButton.setText("SIGN UP");
                    setFragment(LoginFragment.newInstance("","",getBaseContext()));
                }

            }
        });

    }

    private void startActivityMain() {
        Intent i = new Intent(getBaseContext(), MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    private boolean isLoggedIn() {
        return Utility.UserTokenExists(getBaseContext());
    }


    public void setFragment(Fragment fragment){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container,fragment,"vik");
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        setFragment(LoginFragment.newInstance("","",getBaseContext()));
    }
}
