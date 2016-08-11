package com.example.kuldeep.dailynote.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.kuldeep.dailynote.Adapters.DailyNotesRecyclerViewAdapter;
import com.example.kuldeep.dailynote.Constants.DailyNoteMacros;
import com.example.kuldeep.dailynote.Constants.SharedPref;
import com.example.kuldeep.dailynote.Interface.Interface;
import com.example.kuldeep.dailynote.Models.DailyNoteDataModel;
import com.example.kuldeep.dailynote.R;
import com.example.kuldeep.dailynote.database.DatabaseQuery;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import au.com.bytecode.opencsv.CSVWriter;
import butterknife.Bind;
import butterknife.ButterKnife;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Interface.getDailyNotePosition {

    @Bind(R.id.recycler_view)
    RecyclerView mDailyNoteRecyclerView;

    @Bind(R.id.EmptyView)
    LinearLayout mEmptyView;

    ArrayList<DailyNoteDataModel> mDailyNoteDataModelArrayList;

    DailyNoteDataModel mModel;

    DatabaseQuery mDatabaseQuery;
    DailyNotesRecyclerViewAdapter mAdapter;

    private int mPosition = -1;

    private boolean isNewestFirst = true;


    SharedPref mSharedPref;

    /**
     * Created by Kuldeep on 1/8/2016.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mDatabaseQuery = new DatabaseQuery(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mModel = new DailyNoteDataModel();

        mSharedPref = new SharedPref(this);

        ButterKnife.bind(this);

        mDailyNoteDataModelArrayList = new ArrayList<>();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openCreateNoteActivity = new Intent(HomeActivity.this, CreateNoteActivity.class);
                startActivityForResult(openCreateNoteActivity, DailyNoteMacros.CreateNoteRequestCode);

            }
        });

        mDailyNoteDataModelArrayList = mDatabaseQuery.getDailyNoteListModel();

        populateList(mDailyNoteDataModelArrayList);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }


    private void populateList(ArrayList<DailyNoteDataModel> pDailyNoteDataModelArrayList) {


        mAdapter = new DailyNotesRecyclerViewAdapter(this, pDailyNoteDataModelArrayList, this);
        mDailyNoteRecyclerView.setAdapter(mAdapter);
        if (mDailyNoteDataModelArrayList.size() == 0) {
            mEmptyView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

         if (id == R.id.sort_by_oldest_first) {


            mDailyNoteDataModelArrayList = new ArrayList<>();
            mDailyNoteDataModelArrayList = mDatabaseQuery.getDailyNoteListModelOldestFirst();
            populateList(mDailyNoteDataModelArrayList);
            isNewestFirst = false;


        } else if (id == R.id.sort_by_newest) {
            mDailyNoteDataModelArrayList = new ArrayList<>();
            mDailyNoteDataModelArrayList = mDatabaseQuery.getDailyNoteListModel();
            populateList(mDailyNoteDataModelArrayList);
            isNewestFirst = true;

        }
        else if (id == R.id.nav_share) {
             checkForReadWritePermissionPermission();

        } else if (id == R.id.Share_app) {
             rateOnAppStore();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DailyNoteMacros.CreateNoteRequestCode && resultCode == DailyNoteMacros.CreateNoteResultCode) {

            if (data != null) {

                mModel = data.getParcelableExtra("CreateNoteModel");
                if (isNewestFirst) {
                    mDailyNoteDataModelArrayList.add(0, mModel);
                }else {
                    mDailyNoteDataModelArrayList.add(mModel);


                }
                mDatabaseQuery.insertNote(mModel);
                mAdapter.notifyDataSetChanged();
                if (mDailyNoteDataModelArrayList.size() != 0) {
                    mEmptyView.setVisibility(View.GONE);
                } else {
                    mEmptyView.setVisibility(View.VISIBLE);
                }

            }

        } else if (requestCode == DailyNoteMacros.DeleteNoteRequestCode && resultCode == DailyNoteMacros.DeleteNoteResultCode) {

            if (data != null) {

                boolean isDelete = data.getBooleanExtra("isDelete", false);

                if (isDelete) {
                    mDatabaseQuery.deleteNote(mDailyNoteDataModelArrayList.get(mPosition).getId());
                    mDailyNoteDataModelArrayList.remove(mPosition);
                    mAdapter.notifyDataSetChanged();
                    if (mDailyNoteDataModelArrayList.size() == 0) {
                        mEmptyView.setVisibility(View.VISIBLE);
                    } else {
                        mEmptyView.setVisibility(View.GONE);
                    }
                }


            }

        }

    }

    private void exportCSVfile() {
        String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US);
        String fileName = "DailyNoteData" + dateFormatter.format(Calendar.getInstance().getTime()) + ".csv";
        String filePath = baseDir + File.separator + fileName;
        File f = new File(filePath);
        CSVWriter writer;
        // File exist
        try {
            if (f.exists() && !f.isDirectory()) {
                FileWriter mFileWriter;

                mFileWriter = new FileWriter(filePath, true);

                writer = new CSVWriter(mFileWriter);
            } else {
                writer = new CSVWriter(new FileWriter(filePath));
            }


            ArrayList<DailyNoteDataModel> mBindedSubmissionsList = mDailyNoteDataModelArrayList;
            for (int i = 0; i < mBindedSubmissionsList.size(); i++) {
                writer.writeNext(new String[] { "", "" });
                writer.writeNext(new String[] { "", "" });
                writer.writeNext(
                        new String[] { "Daily Note Subject: " , (mBindedSubmissionsList.get(i).getSubject()) });
                writer.writeNext(new String[] { "Daily Note Description:: " , mBindedSubmissionsList.get(i).getDescription() });
                writer.writeNext(new String[] { "Daily Note Create Date:: " , mBindedSubmissionsList.get(i).getDateTime() });

            }

            // writer.writeAll(mList);

            writer.close();

            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            // set the type to 'email'
            emailIntent.setType("vnd.android.cursor.dir/email");
            // String to[] = {"asd@gmail.com"};
            // emailIntent .putExtra(Intent.EXTRA_EMAIL, to);
            // the attachment
            emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + f.getAbsolutePath()));
            // the mail subject
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "DailyNote Data");
            startActivity(Intent.createChooser(emailIntent, "Send email..."));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void checkForReadWritePermissionPermission() {
        if ((ContextCompat.checkSelfPermission(HomeActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(HomeActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) || ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                showMessageOKCancel("Dailynote needs to access your external storage",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(HomeActivity.this,
                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        1);
                            }
                        });
            } else {
                ActivityCompat.requestPermissions(HomeActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);
            }
        } else {
            exportCSVfile();
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(HomeActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    @Override
    public void getDailyNotePosition(int position) {
        mPosition = position;
        Intent lOpenDescriptionActiity = new Intent(this, NoteDescriptionActivity.class);
        lOpenDescriptionActiity.putExtra("Model", mDailyNoteDataModelArrayList.get(position));
        startActivityForResult(lOpenDescriptionActiity, DailyNoteMacros.DeleteNoteRequestCode);

    }

    private void rateOnAppStore() {
        // take to play store
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=com.example.kuldeep.dailynote"));
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            //open playstore url
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.frogo&hl=en");
            Intent openPlayStoreUrl = new Intent(Intent.ACTION_VIEW, uri);
            try {
                startActivity(openPlayStoreUrl);
            } catch (Exception p) {
                Snackbar.make(mEmptyView, "Play store not available", Snackbar.LENGTH_SHORT).show();
            }
        }
    }
}
