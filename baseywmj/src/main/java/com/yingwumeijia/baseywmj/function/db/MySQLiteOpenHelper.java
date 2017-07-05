package com.yingwumeijia.baseywmj.function.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jam on 16/10/19 下午5:01.
 * Describe:
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    public MySQLiteOpenHelper(Context context) {
        //v1.2.2创建数据库，创建发第一条消息的表
        super(context, DBManager.DB_NAME, null, DBManager.DB_VERSATION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBManager.CREATE_TB_FIRST_CALL());
        db.execSQL(DBManager.CREATE_TB_MESSAGE());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == 2) {
            //v1.3.0新增的站内信的表
            db.execSQL(DBManager.CREATE_TB_MESSAGE());
        }
    }
}
