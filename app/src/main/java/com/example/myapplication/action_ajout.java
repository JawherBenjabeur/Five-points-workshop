package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;

public class action_ajout extends Activity {
    EditText _txtref, _txttitre, _txtprix, _txtquantit;
    Button _btnAjout;
    Button _btnAnnuler, _btnEnreg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_ajout);

            _txtref = (EditText) findViewById(R.id.txtref);
            _txttitre = (EditText) findViewById(R.id.txtref);
            _txtprix = (EditText) findViewById(R.id.txtprix);
            _txtquantit = (EditText) findViewById(R.id.txtquantit);


            _btnAjout = (Button) findViewById(R.id.btnAjout);
            _btnAjout.setEnabled(false);
            _btnAnnuler = (Button) findViewById(R.id.btnAnnuler);
            _btnEnreg = (Button) findViewById(R.id.btnEnreg);

            _btnAjout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    _txtref.setText("");
                    _txttitre.setText("");
                    _txtprix.setText("");
                    _txtquantit.setText("");

                    _btnEnreg.setVisibility(View.VISIBLE);
                    _btnAnnuler.setVisibility(View.VISIBLE);
                    _btnAjout.setEnabled(false);
                }
            });

            _btnEnreg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String nom = _txtref.getText().toString().trim();
                    String adresse = _txttitre.getText().toString().trim();
                    String tel1 = _txtprix.getText().toString().trim();
                    String tel2 = _txtquantit.getText().toString().trim();
                    if (nom.isEmpty()) {
                        Toast.makeText(action_ajout.this, "enter ref", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (adresse.isEmpty()) {
                        Toast.makeText(action_ajout.this, "enter titre", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (tel1.isEmpty()) {
                        Toast.makeText(action_ajout.this, "enter prix", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (tel2.isEmpty()) {
                        Toast.makeText(action_ajout.this, "enter quantité", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        StringRequest request = new StringRequest(Request.Method.POST, "ipadress",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.contains("success insertion")) {
                                            Toast.makeText(action_ajout.this, "produit insérer", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), action.class));
                                        } else {
                                            Toast.makeText(action_ajout.this, response, Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                }
                                , new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(action_ajout.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                        ) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();

                                params.put("refrerence", _txtref.getText().toString());
                                params.put("titre", _txttitre.getText().toString());
                                params.put("prix", _txtprix.getText().toString());
                                params.put("quantité", _txtquantit.getText().toString());
                                Log.e("params", "params=" + params);

                                return params;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(action_ajout.this);
                        requestQueue.add(request);
                    }
                }


            });
            _btnAnnuler.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), action.class));
                }
            });
        }
    }
