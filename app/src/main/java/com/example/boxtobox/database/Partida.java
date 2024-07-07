package com.example.boxtobox.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "partida", indices = {
        @Index(
                value = {
                        "mandante",
                        "visitante",
                        "nome_competicao",
                        "fase_competicao"
                },
                unique = true
        )
    }
)
public class Partida {
    @PrimaryKey(autoGenerate = true)
    public int id_partida;

    @NonNull
    @ColumnInfo
    public String mandante = "";
    @NonNull
    @ColumnInfo
    public String visitante = "";
    @NonNull
    public String nome_competicao = "";
    @NonNull
    @ColumnInfo
    public String fase_competicao = "";
    @ColumnInfo
    public int placar_mandante = 0;
    @ColumnInfo
    public int placar_visitante = 0;
    @ColumnInfo
    public long timestamp_partida;

    public Partida(
            @NonNull String mandante,
            @NonNull String visitante,
            @NonNull String nome_competicao,
            @NonNull String fase_competicao,
            int placar_mandante,
            int placar_visitante,
            long timestamp_partida
    ) {
        this.mandante = mandante;
        this.visitante = visitante;
        this.nome_competicao = nome_competicao;
        this.fase_competicao = fase_competicao;
        this.placar_mandante = placar_mandante;
        this.placar_visitante = placar_visitante;
        this.timestamp_partida = timestamp_partida;
    }

    public static class PartidaSelect {
        public int id_partida;
    }
}
