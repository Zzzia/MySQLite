# MySQLite
> 数据库的封装（虽然感觉并没有什么卵用）
>

[代码快捷传送门](https://github.com/Zzzia/MySQLite/tree/master/app/src/main/java/com/zia/mysqlite)

[apk](https://github.com/Zzzia/Files/blob/master/apks/MySQLite.apk)

直接使用继承SQLiteOpenHelper的类会多一点点代码，于是也当是练习，制作了这个工具类。。

### example

以下为这个工具类最简单的用法，使用参数最少的方法，都有原生方法的重载，就不列举了。

##### 第一步，创建Mdatabase的对象，其中两个构造参数为Context和建表语句

~~~
String creat = "create table Student(id integer primary key autoincrement,name text,num text,age text);";//建表语句

Mdatabase mdatabase = new Mdatabase(MainActivity.this,creat);
~~~

##### 添加数据，使用insert方法，参数传入一个ContentValues

~~~
ContentValues values = new ContentValues();
values.put("name", zia);
values.put("num", 2016);
values.put("age", 19);

mdatabase.insert(values);
~~~

##### 删除数据（目前只加入了一个删除所有内容的方法）

~~~
mdatabase.Delete();
~~~

##### 更改数据，update方法，参数三个：ContentValues，条件判断，？的替代内容

~~~
ContentValues values = new ContentValues();
values.put("name",name);
values.put("num",num);
values.put("age",age);

mdatabase.update(values,"id = ?",new String[] {id});
~~~

##### 查询，调用getCursor方法，得到Cursor再查询

~~~
Cursor cursor = mdatabase.getCursor();
cursor.getString(cursor.getColumnIndex("name"))//获取name
~~~

##### 还可以直接用getDb方法得到一个SQLiteDatabase的实例

~~~
SQLiteDatabase db = mdatabase.getDb();
db.execSQL(...);
db.update(...);
...
~~~



