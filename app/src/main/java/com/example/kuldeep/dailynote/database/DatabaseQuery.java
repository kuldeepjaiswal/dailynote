
package com.example.kuldeep.dailynote.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.kuldeep.dailynote.Models.DailyNoteDataModel;

import java.util.ArrayList;

public class DatabaseQuery {
    private static final String TAG = "DatabaseQuery";
    private DatabaseHandler m_cObjDataHandler;

    public DatabaseQuery(Context context) {
        m_cObjDataHandler = DatabaseHandler.getInstance(context);
    }

    public void insertNote(DailyNoteDataModel mModel) {

        if (m_cObjDataHandler.getCount(TableList.TABLE_DAILY_NOTE_LIST) >= 100) {
            deleteOldestNote();
        }

        ContentValues values = new ContentValues();
        values.put(TableList.ID_DAILY_NOTE_LIST, mModel.getId());
        values.put(TableList.SUBJECT_DAILY_NOTE_LIST, mModel.getSubject());
        values.put(TableList.DESCRIPTION_DAILY_NOTE_LIST, mModel.getDescription());
        values.put(TableList.DATE_TIME_DAILY_NOTE_LIST, mModel.getDateTime());

        m_cObjDataHandler.insertContentValues(TableList.TABLE_DAILY_NOTE_LIST, values);

    }


    public void deleteOldestNote() {

        String Id = "";
        Cursor lCursor;
        String lRetreiveQuery = "Select *FROM " + TableList.TABLE_DAILY_NOTE_LIST;
        lCursor = m_cObjDataHandler.retriveQuery(lRetreiveQuery);
        if (lCursor != null) {
            if (lCursor.moveToFirst()) {

                Id = lCursor.getString(lCursor.getColumnIndex(TableList.ID_DAILY_NOTE_LIST));
                m_cObjDataHandler.delete(TableList.TABLE_DAILY_NOTE_LIST, Id);
            }
        }

    }


    public ArrayList<DailyNoteDataModel> getDailyNoteListModel() {

        ArrayList<DailyNoteDataModel> lNotificationsDataModelArrayList = new ArrayList<>();
        Cursor lCursor;
        String lRetreiveQuery = "Select *FROM " + TableList.TABLE_DAILY_NOTE_LIST + " ORDER BY " + TableList.ID_DAILY_NOTE_LIST + " DESC";
        lCursor = m_cObjDataHandler.retriveQuery(lRetreiveQuery);
        if (lCursor != null) {
            if (lCursor.moveToFirst()) {
                DailyNoteDataModel mSubmissionModel = null;
                do {
                    mSubmissionModel = new DailyNoteDataModel(
                            lCursor.getString(lCursor.getColumnIndex(TableList.ID_DAILY_NOTE_LIST)),
                            lCursor.getString(lCursor.getColumnIndex(TableList.SUBJECT_DAILY_NOTE_LIST)),
                            lCursor.getString(lCursor.getColumnIndex(TableList.DESCRIPTION_DAILY_NOTE_LIST)),
                            lCursor.getString(lCursor.getColumnIndex(TableList.DATE_TIME_DAILY_NOTE_LIST)));
                    lNotificationsDataModelArrayList.add(mSubmissionModel);
                } while (lCursor.moveToNext());
            }
            lCursor.close();
        }
        return lNotificationsDataModelArrayList;
    }


public ArrayList<DailyNoteDataModel> getDailyNoteListModelOldestFirst() {

        ArrayList<DailyNoteDataModel> lNotificationsDataModelArrayList = new ArrayList<>();
        Cursor lCursor;
        String lRetreiveQuery = "Select *FROM " + TableList.TABLE_DAILY_NOTE_LIST;
        lCursor = m_cObjDataHandler.retriveQuery(lRetreiveQuery);
        if (lCursor != null) {
            if (lCursor.moveToFirst()) {
                DailyNoteDataModel mSubmissionModel = null;
                do {
                    mSubmissionModel = new DailyNoteDataModel(
                            lCursor.getString(lCursor.getColumnIndex(TableList.ID_DAILY_NOTE_LIST)),
                            lCursor.getString(lCursor.getColumnIndex(TableList.SUBJECT_DAILY_NOTE_LIST)),
                            lCursor.getString(lCursor.getColumnIndex(TableList.DESCRIPTION_DAILY_NOTE_LIST)),
                            lCursor.getString(lCursor.getColumnIndex(TableList.DATE_TIME_DAILY_NOTE_LIST)));
                    lNotificationsDataModelArrayList.add(mSubmissionModel);
                } while (lCursor.moveToNext());
            }
            lCursor.close();
        }
        return lNotificationsDataModelArrayList;
    }

    public boolean deleteNote(String id) {

        return m_cObjDataHandler.delete(id, TableList.TABLE_DAILY_NOTE_LIST);


    }




}