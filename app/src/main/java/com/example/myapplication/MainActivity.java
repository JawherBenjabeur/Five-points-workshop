package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button _btnconnection;
    EditText _txtuser,_txtpass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _btnconnection = findViewById(R.id._btnconnection);
        _txtuser = findViewById(R.id._txtuser);
        _txtpass = findViewById(R.id._txtpass);

        _btnconnection.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();

            }
        });



    }
    public void login(){

        StringRequest request= new StringRequest(Request.Method.POST, "192.168.100.48/android1/login.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("success")){

                            startActivity(new Intent(getApplicationContext(),acceuil.class));

                        }else {

                            Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();

                params.put("user",_txtuser.getText().toString());
                params.put("pass",_txtpass.getText().toString());
                Log.e("params","params="+params);
                return params;

            }
        };
        Volley.newRequestQueue(this).add(request);
    }


}