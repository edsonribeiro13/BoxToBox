package com.example.boxtobox;

import static com.example.boxtobox.utils.validaCampoEditText.validaCampo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.boxtobox.database.DataBase;
import com.example.boxtobox.database.Usuario;
import com.example.boxtobox.telaPrincipal.TelaPrincipal;

public class MainActivity extends AppCompatActivity {

    EditText userLogin, userPassword;
    Button btnLogin, btnSair;
    TextView txtCadastrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        userLogin = findViewById(R.id.userLogin);
        userPassword = findViewById(R.id.userPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSair = findViewById(R.id.btnSair);
        txtCadastrar = findViewById(R.id.txtCadastrar);

        txtCadastrar.setOnClickListener((View view) -> {
            Intent intent = new Intent(MainActivity.this, Cadastro.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener((View view) -> {
            logar();
        });

        btnSair.setOnClickListener((View view) -> {
            finishAndRemoveTask();
        });
    }

    private void logar() {
        if (!validaCampo(userLogin) || !validaCampo(userPassword))
            return;

        String login = userLogin.getText().toString();
        String senha = userPassword.getText().toString();

        if (!validarLogin(login, senha)){
            Toast.makeText(
                    this,
                    "Usuário e/ou senha incorretos",
                    Toast.LENGTH_LONG
            ).show();
            return;
        }

        Intent intent = new Intent(this, TelaPrincipal.class);
        intent.putExtra("login",login);
        startActivity(intent);
        finish();
    }

    private boolean validarLogin(String login, String senha) {
        DataBase db = Room.databaseBuilder(
                getApplicationContext(),
                DataBase.class,
                "BoxToBoxDB"
        ).allowMainThreadQueries().build();

        Usuario usuario = db.getUsuarioDAO().getUserLogin(login, senha);

        return usuario != null;
    }
}