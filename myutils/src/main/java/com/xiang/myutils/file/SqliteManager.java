package com.xiang.myutils.file;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/9/13.
 */
public class SqliteManager extends SQLiteOpenHelper {
    public SqliteManager(Context context) {
        super(context, "carts.db", null, 1);
    }
    private int id;
    private String name;
    private String imgUrl;
    private String description;
    private float price;
    private int sale;
    @Override
    public void onCreate(SQLiteDatabase db) {
        //写创建表的语句
        String sql = "create table good(id integer primary key," +
                "name varchar(100) not null," +
                "imgUrl varchar(100) not null," +
                "description varchar(100) ," +
                "price varchar(100) not null," +
                "count integer not null," +
                "sale varchar(100) not null)";
        //执行创建表的操作  系统自己完成
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
