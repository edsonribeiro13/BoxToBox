package com.example.boxtobox.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "usuario")
public class Usuario {
    @NonNull
    @PrimaryKey
    public String login = "";
    @ColumnInfo
    public String senha;
    @ColumnInfo
    public int idade;
}
