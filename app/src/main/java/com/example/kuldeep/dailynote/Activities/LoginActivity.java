package com.example.kuldeep.dailynote.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.example.kuldeep.dailynote.Constants.SharedPref;
import com.example.kuldeep.dailynote.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Kuldeep on 1/8/2016.
 */

public class LoginActivity extends AppCompatActivity {


    @Bind(R.id.NameTextView)
    TextView mNameTextview;

    @Bind(R.id.EmailTextView)
    TextView mEmailTextView;

    SharedPref mSharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        ButterKnife.bind(this);
        mSharedPref=new SharedPref(this);

        if(mSharedPref.IsLogin()){
            Intent openHomeActvity=new Intent(this,HomeActivity.class);
            startActivity(openHomeActvity);
            finish();


        }


        mEmailTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

    }

    private void attemptLogin() {

        if (mNameTextview.getText().toString().equalsIgnoreCase("")) {
            Snackbar.make(mEmailTextView, "Please enter your name", Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (mEmailTextView.getText().toString().equalsIgnoreCase("")) {
            Snackbar.make(mEmailTextView, "Please enter your email", Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (!isEmailValid(mEmailTextView.getText().toString())) {
            Snackbar.make(mEmailTextView, "Please enter valid email", Snackbar.LENGTH_SHORT).show();
            return;
        }

        Intent openHomeActvity=new Intent(this,HomeActivity.class);

        mSharedPref.putIsLogin(true);
        mSharedPref.putUserEmail(mEmailTextView.getText().toString().trim());
        mSharedPref.putUserName(mNameTextview.getText().toString().trim());
        startActivity(openHomeActvity);
        finish();

    }

    @OnClick(R.id.enterButton)
    public void EnterButton(){
        attemptLogin();
    }


    /**
     * Validates mEmail address.
     */
    public static boolean isEmailValid(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

}

