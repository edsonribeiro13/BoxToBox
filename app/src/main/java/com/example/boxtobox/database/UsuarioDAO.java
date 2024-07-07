package com.example.boxtobox.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
@Dao
public interface UsuarioDAO {
    @Query(
        "SELECT * FROM usuario WHERE login=:login AND senha=:senha"
    )
    public Usuario getUserLogin(String login, String senha);

    @Insert(onConflict = OnConflictStrategy.ABORT)
    public void insertUser(Usuario usuario);
}
