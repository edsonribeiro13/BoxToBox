package com.example.boxtobox.telaPrincipal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.boxtobox.R;
import com.example.boxtobox.database.DataBase;
import com.example.boxtobox.database.Review;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TelaPrincipal extends AppCompatActivity {
    FloatingActionButton btnCriaReview;
    RecyclerView recycleViewTelaPrincipal;
    ListAdapter listAdapter;
    String login = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_principal);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        recycleViewTelaPrincipal = findViewById(R.id.recycleViewTelaPrincipal);
        btnCriaReview = findViewById(R.id.btnCriaReview);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            login = bundle.getString("login");

        btnCriaReview.setOnClickListener((View view) ->{
            Intent intent = new Intent(this, CriaReview.class);

            intent.putExtra("login", login);

            this.startActivity(intent);
        });

        listAdapter = new ListAdapter(buscaReviews(login), this);
        recycleViewTelaPrincipal.setAdapter(listAdapter);
        recycleViewTelaPrincipal.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizaReview();
    }

    public void atualizaReview() {
        listAdapter.atualizaLista(buscaReviews(login));
    }

    public List<Review.ReviewTelaPrincipal> buscaReviews(String login) {
        DataBase db = Room.databaseBuilder(
                getApplicationContext(),
                DataBase.class,
                "BoxToBoxDB"
        ).allowMainThreadQueries().build();

        return db.getReviewDao().getReviewTelaPrincipal(login);
    }
}