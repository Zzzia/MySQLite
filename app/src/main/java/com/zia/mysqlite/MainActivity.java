package com.zia.mysqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tv_name,tv_age,tv_num = null;
    EditText e_name, e_age, e_num, e_id = null;
    Button save,clear,show,update = null;
    String name,age,num,id = "";
    Mdatabase mdatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind();
        init();
        Click();
    }

    private void Click(){
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getEdit();
                insert();
            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getEdit();
                Search();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
                Toast.makeText(MainActivity.this, "delete success", Toast.LENGTH_SHORT).show();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getEdit();
                update();
            }
        });
    }

    /**
     *查找
     */
    private void Search(){
        Cursor cursor = mdatabase.getCursor();
        if(cursor != null && cursor.move(Integer.parseInt(id)) && !cursor.isNull(Integer.parseInt(id))){
            tv_name.setText(cursor.getString(cursor.getColumnIndex("name")));
            tv_num.setText(cursor.getString(cursor.getColumnIndex("num")));
            tv_age.setText(cursor.getString(cursor.getColumnIndex("age")));
            Toast.makeText(this, "get!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "not found!", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }

    /**
     * 删除数据库中所有内容
     */
    private void delete(){
        mdatabase.Delete();
    }

    /**
     * 增加
     */
    private void insert(){
        if(name != null && num != null && age != null) {
            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("num", num);
            values.put("age", age);
            mdatabase.insert(values);
            Toast.makeText(MainActivity.this, "save!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 修改内容
     */
    private void update(){
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("num",num);
        values.put("age",age);
        mdatabase.update(values,"id = ?",new String[] {id});
        Toast.makeText(this, "updated", Toast.LENGTH_SHORT).show();
    }

    /**
     * 初始化数据库
     */
    private void init(){
        String creat = "create table Student(id integer primary key autoincrement,name text,num text,age text);";
        mdatabase = new Mdatabase(MainActivity.this,creat);
    }

    /**
     * 获取输入内容
     */
    private void getEdit(){
        name = e_name.getText().toString();
        age = e_age.getText().toString();
        num = e_num.getText().toString();
        id = e_id.getText().toString();
    }

    private void bind(){
        tv_name = (TextView)findViewById(R.id.tv_name);
        tv_age = (TextView)findViewById(R.id.tv_age);
        tv_num = (TextView)findViewById(R.id.tv_number);
        e_name = (EditText)findViewById(R.id.e_name);
        e_age = (EditText)findViewById(R.id.e_age);
        e_num = (EditText)findViewById(R.id.e_number);
        e_id = (EditText)findViewById(R.id.e_id);
        save = (Button)findViewById(R.id.save);
        clear = (Button)findViewById(R.id.clear);
        show = (Button)findViewById(R.id.show);
        update = (Button)findViewById(R.id.update);
    }
}