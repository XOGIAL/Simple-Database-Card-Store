package com.example.tm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    DatePicker dp_dob;
    RadioButton selected;
    int date = 0, month, year;
    ArrayList<User> user_list = new ArrayList<User>();
    String user_json;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        DBHandler db = new DBHandler(this);


        SharedPreferences shared_preferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = shared_preferences.edit();
        Gson gson = new Gson();

        user_list = loadUserList(shared_preferences, gson);

        if(user_list == null){
            user_list = new ArrayList<>();
        }

        EditText et_username = findViewById(R.id.et_username_rgs);
        EditText et_password = findViewById(R.id.et_password_rgs);
        EditText et_confirm = findViewById(R.id.et_confirm);
        EditText et_phone = findViewById(R.id.et_phone);
        CheckBox cb_terms = findViewById(R.id.cb_terms);


        RadioGroup rg_gender = findViewById(R.id.rg_gender);

        rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                selected = radioGroup.findViewById(i);
            }
        });

        Button btn_dob = findViewById(R.id.btn_dob);

        btn_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                View picker = getLayoutInflater().inflate(R.layout.dialog_date_picker, null);
                dp_dob = picker.findViewById(R.id.dp_dob);
                builder.setView(picker);
                builder.setTitle("Pick date");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        date = dp_dob.getDayOfMonth();
                        month = dp_dob.getMonth();
                        year = dp_dob.getYear();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.create();
                builder.show();
            }
        });

        Button btn_register = findViewById(R.id.btn_register_rgs);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_username.length() < 6 || et_username.length() > 12){
                    Toast.makeText(getApplicationContext(), "Please input a username between 6-12 characters", Toast.LENGTH_SHORT).show();
                }
                else if(et_password.length() < 8){
                    Toast.makeText(getApplicationContext(), "Please input a password with 8 characters", Toast.LENGTH_SHORT).show();
                }
                else if(!alphanumbericFinder(et_password.getText())){
                    Toast.makeText(getApplicationContext(), "Please include alphabet and numbers on your password", Toast.LENGTH_SHORT).show();
                }
                else if(!et_confirm.getText().toString().equals(et_password.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Confirm password does not match", Toast.LENGTH_SHORT).show();
                }
                else if(et_phone.length() < 10 || et_phone.length() > 12){
                    Toast.makeText(getApplicationContext(), "Please input a phone number with 10-12 digits", Toast.LENGTH_SHORT).show();
                }
                else if(selected == null){
                    Toast.makeText(getApplicationContext(), "Please pick a gender", Toast.LENGTH_SHORT).show();
                }
                else if(date == 0){
                    Toast.makeText(getApplicationContext(), "Please pick your Date Of Birth", Toast.LENGTH_SHORT).show();
                }
                else if(!cb_terms.isChecked()){
                    Toast.makeText(getApplicationContext(), "Please agree to terms of service", Toast.LENGTH_SHORT).show();
                }
                else{
                    String dob = date + "-" + (month + 1) + "-" + year;

                    User user = new User();
                    user.setUsername(et_username.getText().toString());
                    user.setPassword(et_password.getText().toString());
                    user.setPhone_number(et_phone.getText().toString());
                    user.setGender(selected.getText().toString());
                    user.setDob(dob);




                    db.insertUser(user);

                    user_json = gson.toJson(user_list);
                    editor.putString("user list", user_json);
                    editor.apply();

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

        Button btn_login = findViewById(R.id.btn_login_rgs);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    boolean alphanumbericFinder(Editable string){
        boolean alphabet = false, numeric = false;
       for(int i = 0; i < string.length(); i++){
           if(string.charAt(i) >= 'A' && string.charAt(i) <= 'Z'){
               alphabet = true;
           }
           if(string.charAt(i) >= 'a' && string.charAt(i) <= 'z'){
               alphabet = true;
           }
           if(string.charAt(i) >= '0' && string.charAt(i) <= '9'){
               numeric = true;
           }
           if(alphabet && numeric){
               return true;
           }
       }
       return false;
    }

    ArrayList<User> loadUserList(SharedPreferences shared_preferences, Gson gson){
        user_json = shared_preferences.getString("user list", null);
        Type type = new TypeToken<ArrayList<User>>() {}.getType();
        return gson.fromJson(user_json, type);
    }

}