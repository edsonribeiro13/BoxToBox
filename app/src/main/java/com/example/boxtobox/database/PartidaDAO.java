package com.example.boxtobox.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface PartidaDAO {
    @Query(
            "SELECT id_partida FROM PARTIDA " +
            "WHERE mandante = :mandante AND visitante = :visitante " +
            "AND nome_competicao = :competicao AND fase_competicao = :fase"
    )
    Partida.PartidaSelect getPartida(String mandante, String visitante, String competicao, String fase);

    @Insert
    long insertPartida(Partida partida);
}
