package com.example.boxtobox.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.util.Date;

@Entity(
        tableName = "review",
        foreignKeys = {
                @ForeignKey(
                        entity = Usuario.class,
                        parentColumns = "login",
                        childColumns = "login",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Partida.class,
                        parentColumns = "id_partida",
                        childColumns = "id_partida",
                        onDelete = ForeignKey.CASCADE
                )
        }
        )
public class Review {
    @PrimaryKey(autoGenerate = true)
    public int id_review;

    @ColumnInfo
    public String login;
    @ColumnInfo
    public int id_partida;
    @ColumnInfo
    public String local_assistiu_partida;
    @ColumnInfo
    public String avaliacao_escrita;
    @ColumnInfo
    public int nota_partida;
    @ColumnInfo
    public long timestamp_criacao_registro;

    public static class ReviewTelaPrincipal {
        public String titulo;
        public long timestamp_partida;
        public String local_assistiu_partida;
        public String avaliacao_escrita;
        public int nota_partida;
        public int id_review;
        public String login;
    }

    public static class CriaReview {
        public String mandante;
        public String visitante;
        public int placar_mandante;
        public int placar_visitante;
        public long timestamp_partida;
        public int nota_partida;
        public String nome_competicao;
        public String fase_competicao;
        public String local_assistiu_partida;
        public String avaliacao_escrita;
        public int id_review;
    }

}
