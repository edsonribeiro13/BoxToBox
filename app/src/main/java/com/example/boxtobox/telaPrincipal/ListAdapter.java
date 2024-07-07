package com.example.boxtobox.telaPrincipal;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boxtobox.Cadastro;
import com.example.boxtobox.R;
import com.example.boxtobox.database.Review;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<LineViewHolder> {

    private List<Review.ReviewTelaPrincipal> lista;
    private Context context;
    public ListAdapter(List<Review.ReviewTelaPrincipal> reviewList, Context context) {
        this.lista = reviewList;
        this.context = context;
    }

    @NonNull
    @Override
    public LineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        return new LineViewHolder(
                inflater.inflate(R.layout.card_review, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull LineViewHolder holder, int position) {
        holder.tituloPartida.setText(this.lista.get(position).titulo);
        Date data_partida = new Date(this.lista.get(position).timestamp_partida * 1000);
        DateFormat format = new SimpleDateFormat("dd/MM/YYYY hh:mm");
        holder.subtituloPartida.setText(
                String.format(
                        "%s %s",
                        format.format(data_partida),
                        this.lista.get(position).local_assistiu_partida
                )
        );
        holder.textReview.setText(this.lista.get(position).avaliacao_escrita);
        holder.notaReview.setRating((float) this.lista.get(position).nota_partida);
        holder.btnEditarReview.setOnClickListener((View view) ->{
            Intent intent = new Intent(context, CriaReview.class);
            intent.putExtra("idReview", lista.get(position).id_review);
            intent.putExtra("login", lista.get(position).login);

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (!lista.isEmpty())
            return lista.size();
        return 0;
    }

    public void atualizaLista(List<Review.ReviewTelaPrincipal> reviewList) {
        this.lista = reviewList;
        notifyDataSetChanged();
    }
}
