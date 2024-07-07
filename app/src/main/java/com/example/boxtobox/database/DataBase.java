package com.example.boxtobox.database;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(
        entities = {Usuario.class, Review.class, Partida.class},
        version = 1
)
public abstract class DataBase extends RoomDatabase {
    public abstract UsuarioDAO getUsuarioDAO();
    public abstract ReviewDAO getReviewDao();
    public abstract PartidaDAO getPartidaDao();

}
