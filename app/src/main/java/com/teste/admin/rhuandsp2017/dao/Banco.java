package com.teste.admin.rhuandsp2017.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.teste.admin.rhuandsp2017.R;
import com.teste.admin.rhuandsp2017.model.Time;

import java.util.ArrayList;

/**
 * Created by Admin on 10/06/2017.
 */

public class Banco extends SQLiteOpenHelper {

    private static final String DB_NAME = "CadastroDeTime";
    private static final int DB_VERSION = 1;
    private Context context;

    public Banco(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(context.getResources()
                .getString(R.string.createTime));
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public void inserirTime(Time e) {
        ContentValues cv = new ContentValues();
        cv.put("nome", e.getNome());
        cv.put("fundacao", e.getFundacao());
        cv.put("cadastro", e.getCadastro());

        e.setId((int) getWritableDatabase().insert("time", null, cv));
    }

    public void updateTime(Time e) {
        ContentValues cv = new ContentValues();
        cv.put("nome", e.getNome());
        cv.put("fundacao", e.getNome());

        getWritableDatabase().update("Time", cv, "id=?", new String[]{e.getId().toString()});
    }

    public void removerTime(Time e) {
        getWritableDatabase().execSQL(("DELETE FROM time where id =" + e.getId()));
    }

    public ArrayList<Time> getTimes() {
        Cursor cursor = getReadableDatabase().rawQuery(("SELECT * FROM time"), null);
        ArrayList<Time> timesFutebol = new ArrayList<>();

        while (cursor.moveToNext()) {
            Time e = new Time();
            e.setId(cursor.getInt(cursor.getColumnIndex("id")));
            e.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            e.setFundacao(cursor.getString(cursor.getColumnIndex("fundacao")));
            e.setCadastro(cursor.getString(cursor.getColumnIndex("cadastro")));

            timesFutebol.add(e);
        }
        return timesFutebol;
    }
}
