package com.example.boxtobox.utils;

import android.util.Log;
import android.widget.EditText;

public class validaCampoEditText {
     public static boolean validaCampo(EditText text) {
         if(text.getText().toString().trim().isEmpty()) {
             text.setError("Campo não pode ser vazio");
             return false;
         }

         return true;
    }
}
