
package com.example.kuldeep.dailynote.database;

import android.provider.BaseColumns;

public class TableList implements BaseColumns {

    public static String TABLE_DAILY_NOTE_LIST = "daily_note_list_table";

    public static String ID_DAILY_NOTE_LIST = "id_notification_list";
    public static String SUBJECT_DAILY_NOTE_LIST = "title_notification_list";
    public static String DESCRIPTION_DAILY_NOTE_LIST = "message_notification_list";
    public static String DATE_TIME_DAILY_NOTE_LIST = "date_notification_list";



    public static final String CREATE_TABLE_DAILY_NOTE_LIST = "CREATE TABLE IF NOT EXISTS " + TABLE_DAILY_NOTE_LIST
            + "("
            + ID_DAILY_NOTE_LIST + " TEXT PRIMARY KEY NOT NULL,"
            + SUBJECT_DAILY_NOTE_LIST + " TEXT NOT NULL,"
            + DESCRIPTION_DAILY_NOTE_LIST + " TEXT NOT NULL,"
            + DATE_TIME_DAILY_NOTE_LIST + " TEXT NOT NULL"
            + ")";



}

