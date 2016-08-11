package com.example.kuldeep.dailynote.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.kuldeep.dailynote.Constants.DailyNoteMacros;
import com.example.kuldeep.dailynote.Models.DailyNoteDataModel;
import com.example.kuldeep.dailynote.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NoteDescriptionActivity extends AppCompatActivity {

    @Bind(R.id.DescriptionTextView)
    TextView mDescriptionTextView;

    DailyNoteDataModel mModel;
    /**
     * Created by Kuldeep on 1/8/2016.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_description);

        ButterKnife.bind(this);

        //get data from previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mModel = extras.getParcelable("Model");
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDescriptionTextView.setText(mModel.getDescription());
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(mModel.getSubject());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
//        toolbar.setNavigationIcon(R.drawable.back_arrow);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent();
                i.putExtra("isDelete",true);
                setResult(DailyNoteMacros.DeleteNoteResultCode,i);
                finish();
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
}
