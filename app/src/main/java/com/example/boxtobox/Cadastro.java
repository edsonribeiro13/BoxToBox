package com.example.boxtobox;

import static com.example.boxtobox.utils.validaCampoEditText.validaCampo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.boxtobox.database.DataBase;
import com.example.boxtobox.database.Usuario;

import java.util.Objects;

public class Cadastro extends AppCompatActivity {

    Button btnVoltarCadastrar;
    Button btnSalvarCadastrar;
    EditText senhaCadastrar;
    EditText idadeCadastrar;
    EditText loginCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);

        btnVoltarCadastrar = findViewById(R.id.btnVoltarCadastrar);
        btnSalvarCadastrar = findViewById(R.id.btnSalvarCadastrar);
        senhaCadastrar = findViewById(R.id.senhaCadastrar);
        idadeCadastrar = findViewById(R.id.idadeCadastrar);
        loginCadastrar = findViewById(R.id.loginCadastrar);

        btnVoltarCadastrar.setOnClickListener((View view) -> {
            finish();
        });

        btnSalvarCadastrar.setOnClickListener((View view) -> {
            criaUsuario();
        });
    }

    private void criaUsuario() {
        Usuario cadastroUsuario = new Usuario();

        if (
            !validaCampo(senhaCadastrar)
            || !validaCampo(loginCadastrar)
            || !validaCampo(idadeCadastrar)
        )
            return;

        cadastroUsuario.senha = senhaCadastrar.getText().toString().trim();
        cadastroUsuario.login = loginCadastrar.getText().toString().trim();
        cadastroUsuario.idade = Integer.parseInt(idadeCadastrar.getText().toString().trim());

        DataBase db = Room.databaseBuilder(
                getApplicationContext(),
                DataBase.class,
                "BoxToBoxDB"
        ).allowMainThreadQueries().build();

        try {
            db.getUsuarioDAO().insertUser(cadastroUsuario);
        }
        catch (Exception e) {
            if (Objects.equals(
                e.getMessage(),
                "UNIQUE constraint failed: usuario.login (code 1555)"
            )) {
                Toast.makeText(
                        this,
                        "Login já existe, forneça outro login",
                        Toast.LENGTH_LONG
                ).show();

                return;
            }

            Toast.makeText(
                    this,
                    "Falha ao criar usuário",
                    Toast.LENGTH_LONG
            ).show();
            return;
        }

        Toast.makeText(
                this,
                "Usuário criado com sucesso!",
                Toast.LENGTH_LONG
        ).show();
        finish();
    }
}