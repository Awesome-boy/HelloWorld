package com.example.helloworld.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.helloworld.entity.ContactsBean;

import java.util.ArrayList;
import java.util.List;

public class ContactsDao {

    private final DBOpenHelper helper;

    public ContactsDao(Context context) {
        helper = DBOpenHelper.getInstance(context);
    }

    /**
     * 从数据库中新增一个联系人
     *
     * @param phone 删除电话号码
     */
    public void insert(String phone, String name) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("phone", phone);
        cv.put("name", name);
        db.insert("contacts", null, cv);
        db.close();
    }

    /**
     * 从数据库中删除一条电话号码
     *
     * @param phone 删除电话号码
     */
    public void delete(String phone) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete("contacts", "phone = ?", new String[]{phone});
        db.close();

    }

    /**
     * 根据电话号码去,更新名字
     *
     * @param phone
     * @param name
     */
    public void updateName(String phone, String name) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        db.update("contacts", contentValues, "phone = ?", new String[]{phone});
        db.close();
    }

    /**
     * 根据名字，更新电话号码
     *
     * @param phone
     * @param name
     */
    public void updatePhone(String name, String phone) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("phone", phone);
        db.update("contacts", contentValues, "name = ?", new String[]{name});
        db.close();
    }

    public List<ContactsBean> queryAll() {
        List<ContactsBean> list = new ArrayList<>();
        SQLiteDatabase db = helper.getWritableDatabase();
        // 查询Contacts表中所有的数据
        String sqlQuery = "select * from " + "contacts";
        Cursor cursor = db.rawQuery(sqlQuery, null);
        if (cursor.moveToFirst()) {
            do {
                // 遍历Cursor对象，取出数据并打印
                ContactsBean bean = new ContactsBean();
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String phone = cursor.getString(cursor.getColumnIndex("phone"));
                bean.setName(name);
                bean.setPhone(phone);
                list.add(bean);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

}


