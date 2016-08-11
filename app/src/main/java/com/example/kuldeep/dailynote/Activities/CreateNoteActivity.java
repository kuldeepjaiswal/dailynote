package com.example.kuldeep.dailynote.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.kuldeep.dailynote.Constants.DailyNoteMacros;
import com.example.kuldeep.dailynote.Models.DailyNoteDataModel;
import com.example.kuldeep.dailynote.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Kuldeep on 1/8/2016.
 */

public class CreateNoteActivity extends AppCompatActivity {

    @Bind(R.id.SubjectEditText)
    EditText mSubjectEditText;

    @Bind(R.id.DescriptionEditText)
    EditText mDescriptionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("Create Note");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveNote();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    private void SaveNote(){

        if(mSubjectEditText.getText().toString().trim().equalsIgnoreCase("")){
            Snackbar.make(mSubjectEditText,"Please enter subject",Snackbar.LENGTH_SHORT).show();
            return;
        }
        if(mDescriptionEditText.getText().toString().trim().equalsIgnoreCase("")){
            Snackbar.make(mSubjectEditText,"Please enter description",Snackbar.LENGTH_SHORT).show();
            return;
        }
        Random rand = new Random();
//
        int  n = rand.nextInt(50) + 1;

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh:mm:a", Locale.ENGLISH);
        String currentDateandTime = sdf.format(new Date());

        DailyNoteDataModel mModel= new DailyNoteDataModel(String.valueOf(n),mSubjectEditText.getText().toString().trim(),
                mDescriptionEditText.getText().toString().trim(),currentDateandTime);

        Intent i=new Intent();
        i.putExtra("CreateNoteModel",mModel);
        setResult(DailyNoteMacros.CreateNoteResultCode,i);
        finish();


    }

}
