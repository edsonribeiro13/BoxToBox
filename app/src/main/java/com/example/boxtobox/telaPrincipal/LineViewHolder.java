package com.example.boxtobox.telaPrincipal;

import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.boxtobox.R;

// Representa itens de uma lista do recycle view
public class LineViewHolder extends RecyclerView.ViewHolder {
    TextView tituloPartida, subtituloPartida, textReview, dataReview;
    RatingBar notaReview;
    Button btnEditarReview;
    public LineViewHolder(@NonNull View itemView) {
        super(itemView);

        tituloPartida = itemView.findViewById(R.id.tituloPartida);
        subtituloPartida = itemView.findViewById(R.id.subtituloPartida);
        textReview = itemView.findViewById(R.id.textReview);
        notaReview = itemView.findViewById(R.id.notaReviewEditar);
        btnEditarReview = itemView.findViewById(R.id.btnEditarReview);

    }
}
