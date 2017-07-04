package com.dannymato.hourcontrol;

import android.provider.BaseColumns;


public final class HourControlContract {

    private HourControlContract() {}

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "database.db";
    public static final String TEXT_TYPE = " TEXT";
    public static final String COMMA_SEP = ",";

    public static class HoursTable implements BaseColumns{

        public static final String TABLE_NAME = "HourTable";
        public static final String COLUMN_NAME_COL1 = "Date";
        public static final String COLUMN_NAME_COL2 = "Hours";
        public static final String COLUMN_NAME_COL3 = "Employer";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + "INTEGER PRIMARY KEY, " +
                COLUMN_NAME_COL1 + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_COL2 + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_COL3 + TEXT_TYPE + " )";

    }

    public static class EmployerTable implements BaseColumns{

        public static final String TABLE_NAME = "EmployerTable";
        public static final String COLUMN_1 = "Employers";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + "INTEGER PRIMARY KEY, " +
                COLUMN_1 + TEXT_TYPE + " )";

    }

}
