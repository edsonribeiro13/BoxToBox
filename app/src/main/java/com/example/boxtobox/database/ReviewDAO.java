package com.example.boxtobox.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ReviewDAO {
    @Query(
            "SELECT " +
                    "p.mandante || ' ' || p.placar_mandante || ' X ' || " +
                    "p.placar_visitante || ' ' || p.visitante as titulo, " +
                    "p.timestamp_partida, r.local_assistiu_partida, r.avaliacao_escrita, " +
                    "r.nota_partida, r.id_review, r.login " +
                    "FROM review r INNER JOIN partida p on p.id_partida = r.id_partida " +
                    "WHERE r.login = :login ORDER BY r.timestamp_criacao_registro"
    )
    List<Review.ReviewTelaPrincipal> getReviewTelaPrincipal(String login);

    @Query(
            "SELECT " +
                    "p.mandante, p.placar_mandante, placar_visitante, p.visitante, " +
                    "p.timestamp_partida, r.local_assistiu_partida, r.avaliacao_escrita, " +
                    "r.nota_partida, r.id_review, p.nome_competicao, p.fase_competicao, " +
                    "r.local_assistiu_partida " +
                    "FROM review r INNER JOIN partida p on p.id_partida = r.id_partida " +
                    "WHERE r.id_review = :id_review"
    )
    Review.CriaReview getReviewEditar(int id_review);
    @Insert
    void insertReview(Review review);

    @Query("UPDATE review set " +
            "avaliacao_escrita = :avaliacao_escrita, " +
            "local_assistiu_partida = :local_assistiu_partida, " +
            "nota_partida = :nota_partida WHERE id_review = :id_review")
    void updateReview(
            String avaliacao_escrita,
            String local_assistiu_partida,
            int nota_partida,
            int id_review
    );
}
