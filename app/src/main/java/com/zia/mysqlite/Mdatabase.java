package com.zia.mysqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zia on 2017/4/1.
 */

/*使用方法：
----------------------第一步
//创建一个默认数据库名字的Default.db
//两个参数，context和建表语句
Mdatabase mdatabase = new Mdatabase(this,
"create table Student(id integer primary key autoincrement,name text,num text,age text);");
----------------------
    //添加数据
    ContentValues values = new ContentValues();
                    values.put("name","jzl");
                    values.put("num","2016211541");
                    values.put("age","18");
    mdatabase.insert(values);
----------------------
    //删除数据
    mdatabase.Delete();
----------------------
    //修改数据
    ContentValues values = new ContentValues();
                    values.put("name","zia");
                    values.put("num","2016211541");
                    values.put("age","19");
    mdatabase.update(values,"id = ?",new String[] {"1"});
----------------------
    //查询数据
    Cursor cursor = mdatabase.getCursor();
    if(cursor != null && cursor.move(Integer.parseInt(id)) && !cursor.isNull(Integer.parseInt(id))){
        tv_name.setText(cursor.getString(cursor.getColumnIndex("name")));
        tv_num.setText(cursor.getString(cursor.getColumnIndex("num")));
        tv_age.setText(cursor.getString(cursor.getColumnIndex("age")));
    }
    cursor.close();
*/
public class Mdatabase {
    public final String DEFUALT_DB = "Default.db";
    private final String TAG = "Mdatabase";
    private Context context = null;
    private String creat = null;
    private String table = null;
    private SQLiteDatabase db = null;
    private Helper databaseHelper = null;
    private String name = null;

    /**
     * 创建默认数据库名的db
     * @param mcontext Context
     * @param mcreat String
     */
    public Mdatabase(Context mcontext,String mcreat){
        this.context = mcontext;
        this.creat = mcreat;
        databaseHelper = new Helper(context, "Default.db", null, 1,creat);
        db = databaseHelper.getWritableDatabase();
        table = regex(creat);
        Log.d(TAG,"Get default database");
    }

    /**
     * 创建自定义名字的db
     * @param mcontext Context
     * @param mcreat String
     */
    public Mdatabase(Context mcontext,String mcreat,String mname){
        this.context = mcontext;
        this.creat = mcreat;
        this.name = mname;
        databaseHelper = new Helper(context, name, null, 1,creat);
        db = databaseHelper.getWritableDatabase();
        table = regex(creat);
        Log.d(TAG,"Get custom database");
    }

    /**
     * 创建数据库
     * @return SQLiteDatabase
     */
    public SQLiteDatabase getDb(){
        if(db == null) {
            if (name == null) {
                databaseHelper = new Helper(context, "Default.db", null, 1, creat);
                db = databaseHelper.getWritableDatabase();
            } else {
                databaseHelper = new Helper(context, name, null, 1, creat);
                db = databaseHelper.getWritableDatabase();
            }
        }
        return db;
    }

    /**
     * 插入数据
     * @param table String
     * @param nullColumnHack String
     * @param values ContentValues
     */
    public void insert(String table, String nullColumnHack, ContentValues values){
        getDb().insert(table,nullColumnHack,values);
        Log.d(TAG,"insert database");
    }

    /**
     * 插入数据（正则+null）
     * @param values ContentValues
     */
    public void insert(ContentValues values){
        getDb().insert(table,null,values);
        Log.d(TAG,"insert database");
    }

    /**
     * 获取cursor查询数据库，基本没用
     * @param table 表名
     * @return Cursor
     */
    public Cursor getCursor(String table){
        Cursor cursor = getDb().query(table,null,null,null,null,null,null);
        if(cursor != null){
            return cursor;
        }
        return null;
    }

    /**
     * 以正则获取的table得到cursor
     * @return Cursor
     */
    public Cursor getCursor(){
        if (getDb().query(table,null,null,null,null,null,null) == null) Log.d(TAG,"query == null");
        Log.d(TAG,"get query succeed!");
        return getDb().query(table,null,null,null,null,null,null);
    }

    /**
     * 更改数据
     * @param values ContentValues
     * @param whereClause 条件判断
     * @param whereArgs ?的内容
     */
    public void update(ContentValues values, String whereClause, String[] whereArgs){
        db.update(table,values,whereClause,whereArgs);
    }

    /**
     * 以原方法删除
     * @param table table
     */
    public void Delete(String table, String whereClause, String[] whereArgs){
        getDb().delete(table,whereClause,whereArgs);
        Log.d(TAG,"delete succeed!");
    }

    /**
     * 如果正则匹配成功，则可直接删除
     */
    public void Delete(){
        if(table != null) {
            getDb().delete(table,"id > ?",new String[] {"0"});
            Log.d(TAG,"deleted!");
        }else
            Log.d(TAG,"failed delete...");
    }

    /**
     * 正则尝试提取建表语句中的table
     * @param source 建表语句
     * @return table
     */
    private String regex(String source){
        String regEx = "table\\s([\\w]*?).id";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(source);
        if(matcher.find()){
            Log.d(TAG,"catch table = "+matcher.group(1));
            return  matcher.group(1);
        }
        else {
            Log.d(TAG,"can't catch table...");
            return null;
        }
    }
}

class Helper extends SQLiteOpenHelper {

    Context mContext;
    private String mCreat;

    public Helper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version,String creat) {
        super(context, name, factory, version);
        mContext = context;
        mCreat = creat;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(mCreat);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
