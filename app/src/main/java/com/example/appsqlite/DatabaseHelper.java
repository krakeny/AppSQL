package com.example.appsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, "DBLogin.sql", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table tbUsuario(email text, telefone number, rg number, cpf number primary key, senha text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("drop table if exists tbUsuario");
    }
    //inserindo valores no banco de dados

    public boolean insert(String email, String telefone, String rg, String cpf, String senha) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("senha", senha);
        contentValues.put("telefone", telefone);
        contentValues.put("rg", rg);
        contentValues.put("cpf", cpf);

        long inserido = db.insert("tbUsuario", null, contentValues);

        if (inserido == 1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean validarCpf(String cpf) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from tbUsuario where cpf = ?", new String[]{cpf});
        if (cursor.getCount() > 0) {
            return false;
        } else {
            return true;
        }

    }

    //Verificando usuário e senha

    public Boolean checarCpfSenha(String cpf, String senha) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tbUsuario where email = ? and senha = ?", new String[]{cpf, senha});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }

    }
}
