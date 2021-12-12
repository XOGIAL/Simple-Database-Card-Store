package com.example.tm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String user_json;
    int position = 0;
    ArrayList<User> user_list = new ArrayList<User>();
    DBHelper db = new DBHelper(this);
    DBHandler dbhandler = new DBHandler(this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences shared_preferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = shared_preferences.edit();
        Gson gson = new Gson();

        user_list = loadUserList(shared_preferences, gson);

        if(user_list == null){
            user_list = new ArrayList<>();
        }


        EditText et_username = findViewById(R.id.et_username);
        EditText et_password = findViewById(R.id.et_password);
        Button btn_login = findViewById(R.id.btn_login);
        Button btn_register = findViewById(R.id.btn_register);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();



                if(username.isEmpty() && password.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please input your username and password!", Toast.LENGTH_SHORT).show();
                }
                else if(username.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please input your username", Toast.LENGTH_SHORT).show();
                }
                else if(password.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please input your password!", Toast.LENGTH_SHORT).show();
                }
                else if(dbhandler.usernameFinder(username)==null){
                    Toast.makeText(MainActivity.this, "Username does not exist", Toast.LENGTH_SHORT).show();
                }
                else if(dbhandler.passwordChecker(dbhandler.usernameFinder(username),password)==false){
                    Toast.makeText(MainActivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                }
                else{
//                    setUser(position, editor, user_list);




                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);

                    intent.putExtra("userid",dbhandler.usernameFinder(username));
                    startActivity(intent);
                    finish();
                }


            }

        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    ArrayList<User> loadUserList(SharedPreferences shared_preferences, Gson gson){
        user_json = shared_preferences.getString("user list", null);
        Type type = new TypeToken<ArrayList<User>>() {}.getType();
        return gson.fromJson(user_json, type);
    }



    void setUser(int position, SharedPreferences.Editor editor, ArrayList<User> user_list){
        editor.putInt("current user id", user_list.get(position).getUser_id());
        editor.apply();
    }
}