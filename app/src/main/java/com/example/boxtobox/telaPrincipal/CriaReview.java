package com.example.boxtobox.telaPrincipal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.boxtobox.Cadastro;
import com.example.boxtobox.MainActivity;
import com.example.boxtobox.R;
import com.example.boxtobox.database.DataBase;
import com.example.boxtobox.database.Partida;
import com.example.boxtobox.database.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

public class CriaReview extends AppCompatActivity {

    Spinner selecaoMandate, selecaoVisitante, selecaoCompeticao,
            selecaoFaseCompeticao, localAssistiuJogo;
    TextView placarMandante, placarVisitante, dataJogo, horaJogo, avaliacaoPartidaEscrita;
    RatingBar notaReviewEditar;
    Button btnCancelarAvaliacao, btnSalvarAvaliacao;
    Review.CriaReview editReview;
    int idReview = 0;
    String login;
    String jsonString;
    int indexCompeticao = 0;
    int indexRodada = 0;
    String rodadaFase = "1";
    String[] mandatesArray;
    String[] visitanteArray;
    String[] rodadasArray;
    List<String> mandanteList = new ArrayList<>();
    List<String> visitanteList = new ArrayList<>();
    List<String> placarMandanteList = new ArrayList<>();
    List<String> placarVisitanteList = new ArrayList<>();
    List<String> dataList = new ArrayList<>();
    List<String> horaList = new ArrayList<>();
    DataBase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cria_review);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        selecaoMandate = findViewById(R.id.selecaoMandante);
        selecaoVisitante = findViewById(R.id.selecaoVisitante);
        selecaoCompeticao = findViewById(R.id.selecaoCompeticao);
        selecaoFaseCompeticao = findViewById(R.id.selecaoFaseCompeticao);
        localAssistiuJogo = findViewById(R.id.localAssistiuJogo);
        placarMandante = findViewById(R.id.placarMandante);
        placarVisitante = findViewById(R.id.placarVisitante);
        dataJogo = findViewById(R.id.dataJogo);
        horaJogo = findViewById(R.id.horaJogo);
        avaliacaoPartidaEscrita = findViewById(R.id.avaliacaoPartidaEscrita);
        notaReviewEditar = findViewById(R.id.notaReviewEditar);
        btnCancelarAvaliacao = findViewById(R.id.btnCancelarAvaliacao);
        btnSalvarAvaliacao = findViewById(R.id.btnSalvarAvaliacao);

        setListenersSelect();
        setListenterButtons();
        notaReviewEditar.setStepSize(1);

        db = Room.databaseBuilder(
                getApplicationContext(),
                DataBase.class,
                "BoxToBoxDB"
        ).allowMainThreadQueries().build();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            idReview = bundle.getInt("idReview");
            login = bundle.getString("login");
        }

        if (idReview != 0) {
            preencheValoresReviewAnterior(idReview);
            selecaoMandate.setEnabled(false);
            selecaoVisitante.setEnabled(false);
            selecaoCompeticao.setEnabled(false);
            selecaoFaseCompeticao.setEnabled(false);
        }
        preencheSelects();
    }

    private void setListenersSelect() {
        selecaoMandate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selecaoVisitante.setSelection(position);
                if (!placarMandanteList.isEmpty()) {
                    placarMandante.setText(placarMandanteList.get(position));
                    placarVisitante.setText(placarVisitanteList.get(position));
                    dataJogo.setText(dataList.get(position));
                    horaJogo.setText(horaList.get(position));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        selecaoVisitante.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selecaoMandate.setSelection(position);
                if (!placarMandanteList.isEmpty()) {
                    placarMandante.setText(placarMandanteList.get(position));
                    placarVisitante.setText(placarVisitanteList.get(position));
                    dataJogo.setText(dataList.get(position));
                    horaJogo.setText(horaList.get(position));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        selecaoFaseCompeticao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                indexRodada = position;
                rodadaFase = selecaoFaseCompeticao.getSelectedItem().toString();
                parseJSONMandanteVisitante(true);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        selecaoCompeticao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                indexCompeticao = position;
                parseJSONRodadas(true);
                parseJSONMandanteVisitante(true);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    private void setListenterButtons() {
        btnCancelarAvaliacao.setOnClickListener((View view) -> {
            finish();
        });

        btnSalvarAvaliacao.setOnClickListener((View view) -> {
            int idPartida;
            if (idReview != 0) {
                db.getReviewDao().updateReview(
                    avaliacaoPartidaEscrita.getText().toString(),
                    localAssistiuJogo.getSelectedItem().toString(),
                    (int) notaReviewEditar.getRating(),
                    idReview
                );
            }
            else {
                Partida.PartidaSelect partida = db.getPartidaDao().getPartida(
                    selecaoMandate.getSelectedItem().toString(),
                    selecaoVisitante.getSelectedItem().toString(),
                    selecaoCompeticao.getSelectedItem().toString(),
                    selecaoFaseCompeticao.getSelectedItem().toString()
                );
                if (partida == null) {
                    long datetime = transformaDateTime(
                        dataJogo.getText().toString(),
                        horaJogo.getText().toString()
                    );
                    Partida partidaInsert = new Partida(
                        selecaoMandate.getSelectedItem().toString(),
                        selecaoVisitante.getSelectedItem().toString(),
                        selecaoCompeticao.getSelectedItem().toString(),
                        selecaoFaseCompeticao.getSelectedItem().toString(),
                        Integer.parseInt(placarMandante.getText().toString()),
                        Integer.parseInt(placarVisitante.getText().toString()),
                        datetime
                    );
                    idPartida =(int) db.getPartidaDao().insertPartida(partidaInsert);
                }
                else {
                    idPartida = partida.id_partida;
                }
                Review review = new Review();

                review.login = login;
                review.avaliacao_escrita = avaliacaoPartidaEscrita.getText().toString();
                review.id_partida = idPartida;
                review.local_assistiu_partida = localAssistiuJogo.getSelectedItem().toString();
                review.login = login;
                review.nota_partida = (int) notaReviewEditar.getRating();
                review.timestamp_criacao_registro = System.currentTimeMillis() / 1000L;
                db.getReviewDao().insertReview(review);
            }
            finish();
        });
    }

    public long transformaDateTime(String dateStr, String timeStr) {
        String dateTimeStr = dateStr + " " + timeStr;

        // Define the format of the date and time
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        // Set the time zone to UTC (or your desired time zone)
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            // Parse the combined date and time string into a Date object
            Date date = sdf.parse(dateTimeStr);

            // Get the Unix epoch timestamp
            long unixTimestamp = date.getTime() / 1000L;

            // Print the Unix epoch timestamp
            return unixTimestamp;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private void preencheValoresReviewAnterior(int idReview){
        editReview = db.getReviewDao().getReviewEditar(idReview);

        Date data_partida = new Date(editReview.timestamp_partida * 1000);
        DateFormat formatData = new SimpleDateFormat("dd/MM/YYYY");
        DateFormat formatHora = new SimpleDateFormat("hh:mm");

        placarMandante.setText(String.valueOf(editReview.placar_mandante));
        placarVisitante.setText(String.valueOf(editReview.placar_visitante));
        dataJogo.setText(formatData.format(data_partida));
        horaJogo.setText(formatHora.format(data_partida));
        avaliacaoPartidaEscrita.setText(editReview.avaliacao_escrita);
        notaReviewEditar.setRating((float) editReview.nota_partida);
    }

    private void preencheSelects() {
        String[] localJogo = new String[]{"TV", "Internet", "Estádio"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                localJogo
        );
        localAssistiuJogo.setAdapter(adapter);

        if (idReview != 0 && editReview != null) {
            localAssistiuJogo.setSelection(Arrays.asList(
                    localJogo
            ).indexOf(editReview.local_assistiu_partida));
        }

        jsonString = loadJSONFromResource(R.raw.times);

        selecaoCompeticao.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                parseJSONCompeticao()
        ));
        if (idReview != 0 && editReview != null) {
            if (Objects.equals(editReview.nome_competicao, "Brasileirão"))
                indexCompeticao = 0;
            else if (Objects.equals(editReview.nome_competicao, "Libertadores"))
                indexCompeticao = 1;
            else
                indexCompeticao = 2;
            selecaoCompeticao.setSelection(indexCompeticao);
        }

        parseJSONRodadas(false);
        parseJSONMandanteVisitante(false);
    }

    public String loadJSONFromResource(int resourceId) {
        String json = null;
        InputStream inputStream = this.getResources().openRawResource(resourceId);
        try {
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    public String[] parseJSONCompeticao() {
        List<String> competicoesList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                competicoesList.add(jsonObject.get("competicao").toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String[] competicoesArray = competicoesList.toArray(new String[0]);

        return competicoesArray;
    }

    public void parseJSONRodadas(Boolean ignoreId) {
        List<String> rodadasList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            Object jsonRodada = jsonArray.getJSONObject(
                    indexCompeticao
            ).get("rodadas");
            JSONArray jsonArrayRodada = new JSONArray(jsonRodada.toString());
            for (int i = 0; i < jsonArrayRodada.length(); i++) {
                JSONObject jsonObject = jsonArrayRodada.getJSONObject(i);
                String rodada = jsonObject.keys().next();
                if (
                    idReview != 0 &&
                    editReview != null &&
                    Objects.equals(rodada, editReview.fase_competicao) &&
                    !ignoreId
                ) {
                    indexRodada = i;
                    rodadaFase = rodada;
                }
                rodadasList.add(rodada);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        rodadasArray = rodadasList.toArray(new String[0]);

        selecaoFaseCompeticao.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                rodadasArray
        ));
    }

    public void parseJSONMandanteVisitante(Boolean ignoreId) {
        int indexMandanteVisitante = 0;
        mandanteList = new ArrayList<>();
        visitanteList = new ArrayList<>();
        placarMandanteList = new ArrayList<>();
        placarVisitanteList = new ArrayList<>();
        dataList = new ArrayList<>();
        horaList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            Object jsonRodada = jsonArray.getJSONObject(
                    indexCompeticao
            ).get("rodadas");
            JSONArray jsonArrayRodada = new JSONArray(jsonRodada.toString());
            JSONObject jsonRodadaEscolhida = jsonArrayRodada.getJSONObject(indexRodada);
            JSONArray jsonArrayRodadaEscolhida = jsonRodadaEscolhida.getJSONArray(rodadaFase);
            for (int i = 0; i < jsonArrayRodadaEscolhida.length(); i++) {
                JSONObject jsonObject = jsonArrayRodadaEscolhida.getJSONObject(i);
                if (
                    idReview != 0 &&
                    editReview != null &&
                    !ignoreId
                ) {
                    if (Objects.equals(jsonObject.get("mandante").toString(), editReview.mandante))
                        indexMandanteVisitante = i;
                }
                placarMandanteList.add(jsonObject.get("placarMandante").toString());
                placarVisitanteList.add(jsonObject.get("placarVisitante").toString());
                dataList.add(jsonObject.get("data").toString());
                horaList.add(jsonObject.get("hora").toString());
                mandanteList.add(jsonObject.get("mandante").toString());
                visitanteList.add(jsonObject.get("visitante").toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mandatesArray = mandanteList.toArray(new String[0]);
        visitanteArray = visitanteList.toArray(new String[0]);

        selecaoMandate.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                mandatesArray
        ));
        selecaoVisitante.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                visitanteArray
        ));

        selecaoMandate.setSelection(indexMandanteVisitante);
        selecaoVisitante.setSelection(indexMandanteVisitante);

        if (idReview == 0 || editReview == null || ignoreId) {
            placarMandante.setText(placarMandanteList.get(indexMandanteVisitante));
            placarVisitante.setText(placarVisitanteList.get(indexMandanteVisitante));
            dataJogo.setText(dataList.get(indexMandanteVisitante));
            horaJogo.setText(horaList.get(indexMandanteVisitante));
        }
    }
}