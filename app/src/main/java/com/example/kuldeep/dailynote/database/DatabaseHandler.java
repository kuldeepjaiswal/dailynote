
package com.example.kuldeep.dailynote.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by Kuldeep on 1/8/2016.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private String TAG = "DatabaseHandler";

    private static DatabaseHandler mInstance = null;
    public static String DATABASE_NAME = "DailyNote.db";
    private static final int DATABASE_VERSION = 1;
    public SQLiteDatabase m_cDB;

    public static DatabaseHandler getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DatabaseHandler(context.getApplicationContext());
        }
        return mInstance;
    }

    public DatabaseHandler(Context context) {
        // TODO Auto-generated constructor stub
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        m_cDB = this.getWritableDatabase();

    }

    public boolean createTable(String pQuery) {
        boolean lRetVal = false;
        lRetVal = executeQuery(pQuery);
        Log.i(TAG, pQuery);
        return lRetVal;
    }

    public boolean DeleteQuery(String pQuery) {
        return executeQuery(pQuery);
    }

    public int retriveCount(String pQuery) {
        return executeScalar(pQuery);
    }

    // get the count
    private int executeScalar(String pQuery) {
        int lRetVal = 0;
        Cursor lCursor = null;
        try {
            if (m_cDB != null) {
                synchronized (m_cDB) {
                    lCursor = m_cDB.rawQuery(pQuery, null);
                    if (null != lCursor) {
                        lRetVal = lCursor.getCount();
                        lCursor.close();
                        lCursor = null;
                    }
                }
            }
            // closeDatabase();
        } catch (Exception ex) {
            System.out.println("executeScalar: " + ex.toString());
        }
        return lRetVal;
    }

    //return total no of row in a table
    public int getNumberOfRowInATable(String pTableName) {

        Cursor c = retriveQuery("Select * FROM " + pTableName);
        return c.getCount();

    }

    public long getCount(String pTableName) {
        Log.i("Number of Row in " + pTableName + " ", String.valueOf(DatabaseUtils.queryNumEntries(m_cDB, pTableName)));
        return DatabaseUtils.queryNumEntries(m_cDB, pTableName);
    }

    private boolean executeQuery(String pQuery) {
        boolean lRetVal = false;
        try {
            if (m_cDB != null) {
                synchronized (m_cDB) {
                    m_cDB.execSQL(pQuery);
                    lRetVal = true;
                }
            }
            // closeDatabase();
        } catch (Exception ex) {
            System.out.println("executequery: " + ex.toString());
        }
        return lRetVal;
    }



    //delete row record according id
    public boolean delete(String id, String pTableName) {

        try {
            m_cDB.delete(pTableName, TableList.ID_DAILY_NOTE_LIST + " = ?", new String[]{id});
            return true;
        } catch (Exception ex) {
            System.out.println("deleteRowinATable: " + ex.toString());
            return false;
        }
    }


    public long insertContentValues(String pTableName, ContentValues initialValues) {
        /*
         * COntent values population example ContentValues initialValuesUser = new ContentValues(); initialValuesUser.put("pid", upid); initialValuesUser.put("username", username);
         * initialValuesUser.put("password", passwrd);
         */
        long rowsInserted = 0;
        if (m_cDB != null) {
            synchronized (m_cDB) {
                rowsInserted = m_cDB.insert(pTableName, null, initialValues);
            }
        }
        Log.i(TAG, "no. of rows inserted " + String.valueOf(rowsInserted));
        return rowsInserted;
    }

    public Cursor retriveQuery(String pQuery) {
        return executeCursor(pQuery);
    }

    // select statement
    private Cursor executeCursor(String pQuery) {
        Cursor lRetVal = null;
        try {
            if (m_cDB != null) {
                synchronized (m_cDB) {
                    lRetVal = m_cDB.rawQuery(pQuery, null);
                }
            }
            // closeDatabase();
        } catch (Exception ex) {
            System.out.println("executeCursor " + ex.toString());
        }
        return lRetVal;
    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {
        // TODO Auto-generated method stub
        m_cDB = arg0;
        createTable(TableList.CREATE_TABLE_DAILY_NOTE_LIST);

    }


    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
        System.out.println("Called onUpgrade()");
    }

}