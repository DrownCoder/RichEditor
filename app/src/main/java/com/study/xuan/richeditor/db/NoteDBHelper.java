package com.study.xuan.richeditor.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.study.xuan.editor.common.Const;
import com.study.xuan.richeditor.model.NoteModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Author : xuan.
 * Date : 2018/4/23.
 * Description :input the description of this file
 */

public class NoteDBHelper extends SQLiteOpenHelper implements INoteDbHelper<NoteModel> {
    public static final String TABLE_NAME = "RichNote";
    public static final String CREATE_DB = "create table if not exists " + TABLE_NAME + " (" +
            "_id integer primary key autoincrement, " +
            "content text" +
            ")";

    public NoteDBHelper(Context context) {
        super(context, Const.DB_NAME, null, Const.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void insert(String content) {
        if (getWritableDatabase() != null) {
            ContentValues cv = new ContentValues();
            cv.put("content", content);
            this.getWritableDatabase().insert(TABLE_NAME, null, cv);
        }
    }

    @Override
    public List<NoteModel> queryAll() {
        SQLiteDatabase db = getReadableDatabase();
        if (db != null) {
            List<NoteModel> data = new ArrayList<>();
            StringBuilder str = new StringBuilder();
            str.append("select * from")
                    .append(TABLE_NAME);
            Cursor cursor = db.rawQuery(str.toString(), null);
            if (cursor.moveToNext()) {//如果光标可以移动到下一位,代表就是查询到了数据
                NoteModel model = new NoteModel();
                model.id = cursor.getInt(0);
                model.content = cursor.getString(1);
                data.add(model);
            }
            cursor.close();//关闭掉游标,释放资源
            db.close();//关闭数据库,释放资源
            return data;
        }
        return null;

    }

    @Override
    public NoteModel queryById(int id) {
        NoteModel model = null;
        SQLiteDatabase db = getReadableDatabase();
        if (db != null) {
            StringBuilder str = new StringBuilder();
            str.append("select * from ")
                    .append(TABLE_NAME)
                    .append("where _id = ")
                    .append(id);
            Cursor cursor = db.rawQuery(str.toString(), null);
            if(cursor.moveToNext()){//如果光标可以移动到下一位,代表就是查询到了数据
                model = new NoteModel();
                model.id = cursor.getInt(0);
                model.content = cursor.getString(1);
            }
            cursor.close();//关闭掉游标,释放资源
            db.close();//关闭数据库,释放资源
        }
        return model;
    }

    @Override
    public void delete(int id) {
        //判断这个数据是否存在.
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            StringBuilder str = new StringBuilder();
            str.append("delete from")
                    .append(TABLE_NAME)
                    .append("where _id = ")
                    .append(id);
            db.execSQL(str.toString());
            Log.i("SQL", str.toString());
            db.close();
        }
    }

    @Override
    public void deleteAll() {

    }
}
