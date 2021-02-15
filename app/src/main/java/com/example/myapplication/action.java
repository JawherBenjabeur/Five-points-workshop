package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class action extends AppCompatActivity {

    LinearLayout layoutNaviguer, layoutsearch, layoutAppeler;
    EditText _txtref, _txttitre, _txtprix, _txtquantit,_txtsearch;
    ImageButton _btnsearch;
    Button _btnPrevious, _btnNext;
    Button _btnAjout, _btnMiseAjour, _btnDelete;
    Button _btnAnnuler, _btnEnreg;

    int curs=0;
    String x ;
    JSONArray allproduit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_ajout);

        layoutNaviguer = (LinearLayout) findViewById(R.id.layNavig);
        layoutsearch = (LinearLayout) findViewById(R.id.laysearch);


        _txtsearch = (EditText) findViewById(R.id.txtsearch);
        _txtref = (EditText) findViewById(R.id.txtref);
        _txttitre = (EditText) findViewById(R.id.txtref);
        _txtprix = (EditText) findViewById(R.id.txtprix);
        _txtquantit = (EditText) findViewById(R.id.txtquantit);

        _btnPrevious = (Button) findViewById(R.id.btnPrevious);
        _btnNext = (Button) findViewById(R.id.btnNext);


        _btnAjout = (Button) findViewById(R.id.btnAjout);
        _btnMiseAjour = (Button) findViewById(R.id.btnMiseAjour);
        _btnDelete = (Button) findViewById(R.id.btnDelete);

        _btnAnnuler = (Button) findViewById(R.id.btnAnnuler);
        _btnEnreg = (Button) findViewById(R.id.btnEnreg);

        _btnsearch = (ImageButton) findViewById(R.id.btnsearch);


        layoutNaviguer.setVisibility(View.INVISIBLE);
        _btnEnreg.setVisibility(View.INVISIBLE);
        _btnAnnuler.setVisibility(View.INVISIBLE);


        //******** Ajouter un produit ********
        _btnAjout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),action_ajout.class));
            }
        });

        //******** Recherche d'un produit ********
        _btnsearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String search = _txtsearch.getText().toString();
                getproduit(search);
            }
        });

        //******** Naviguer to next ********
        _btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (curs<allproduit.length()-1){
                        curs++;
                        _txtref.setText(allproduit.getJSONObject(curs).getString("reference"));
                        _txttitre.setText(allproduit.getJSONObject(curs).getString("titre"));
                        _txtprix.setText(allproduit.getJSONObject(curs).getString("prix"));
                        _txtquantit.setText(allproduit.getJSONObject(curs).getString("quantite"));

                        _btnPrevious.setEnabled(true);
                        if (curs==allproduit.length()-1){
                            _btnNext.setEnabled(false);
                        }else {
                            _btnNext.setEnabled(true);
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();


                }
            }
        });

        //******** Naviguer to previous ********
        _btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (curs>0){
                        curs--;
                        _txtref.setText(allproduit.getJSONObject(curs).getString("reference"));
                        _txttitre.setText(allproduit.getJSONObject(curs).getString("titre"));
                        _txtprix.setText(allproduit.getJSONObject(curs).getString("prix"));
                        _txtquantit.setText(allproduit.getJSONObject(curs).getString("quantite"));
                        _btnNext.setEnabled(true);
                        if (curs==0){
                            _btnPrevious.setEnabled(false);
                        }else {
                            _btnPrevious.setEnabled(true);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //******** Modifier un contact ********
        _btnMiseAjour.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                _btnEnreg.setVisibility(View.VISIBLE);
                _btnAnnuler.setVisibility(View.VISIBLE);
                _btnAjout.setVisibility(View.INVISIBLE);
                _btnDelete.setVisibility(View.INVISIBLE);
                _btnMiseAjour.setEnabled(false);
                layoutsearch.setVisibility(View.INVISIBLE);
                layoutNaviguer.setVisibility(View.INVISIBLE);

            }
        });

        //******** Enregistrer les modification du produit ********
        _btnEnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest request = new StringRequest(Request.Method.POST, "ip adresse",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Toast.makeText(action.this,"Mise a jour  avec succés",Toast.LENGTH_SHORT).show();
                                _btnEnreg.setVisibility(View.INVISIBLE);
                                _btnAnnuler.setVisibility(View.INVISIBLE);
                                _btnMiseAjour.setVisibility(View.VISIBLE);
                                _btnDelete.setVisibility(View.VISIBLE);

                                _btnAjout.setVisibility(View.VISIBLE);
                                _btnAjout.setEnabled(true);
                                _btnMiseAjour.setEnabled(true);

                                layoutsearch.setVisibility(View.VISIBLE);

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(action.this,error.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        try {
                            params.put("id", String.valueOf(allproduit.getJSONObject(curs).getInt("id")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        params.put("refrerence", _txtref.getText().toString());
                        params.put("titre", _txttitre.getText().toString());
                        params.put("prix", _txtprix.getText().toString());
                        params.put("quantité", _txtquantit.getText().toString());

                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(action.this);
                requestQueue.add(request);
            }
        });

        //******** Boutton annuler ********
        _btnAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _btnEnreg.setVisibility(View.INVISIBLE);
                _btnAnnuler.setVisibility(View.INVISIBLE);
                _btnMiseAjour.setVisibility(View.VISIBLE);
                _btnDelete.setVisibility(View.VISIBLE);

                _btnAjout.setVisibility(View.VISIBLE);
                _btnAjout.setEnabled(true);
                _btnMiseAjour.setEnabled(true);

                layoutsearch.setVisibility(View.VISIBLE);
            }
        });

        //********Supprimer un produit ********
        _btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    x=  allproduit.getString(0);
                    AlertDialog dial = MesOptions();
                    dial.show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),"Sélectionner un compte puis appyuer sur le bouton de suppresssion",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });



    }

    private AlertDialog MesOptions() {
        AlertDialog MiDia = new AlertDialog.Builder(this)
                .setTitle("confirmation")
                .setMessage("Est ce que vous voulez supprimer "+_txttitre.getText()+"?")
                .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        StringRequest request=new StringRequest(Request.Method.POST, "ipadress/delete.php",
                                new Response.Listener<String>() {
                                    @Override

                                    public void onResponse(String response) {
                                        if (response.equalsIgnoreCase("suppression avec succés")){
                                            System.out.println("****************************************");
                                            Toast.makeText(action.this, "suppression avec succés",Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(action.this,"suppression avec succés",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                System.out.println("////////////////////////////////////////");
                                Toast.makeText(action.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> params = new HashMap<>();
                                try {
                                    params.put("id", String.valueOf(allproduit.getJSONObject(curs).getInt("id")));
                                }
                                catch (JSONException e){
                                    e.printStackTrace();
                                }
                                return super.getParams();
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(action.this);
                        requestQueue.add(request);
                    }
                }).setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .create();

        return MiDia;
    }



    public void getproduit (String search){
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Request.Method.GET, "ipadress/getproduit.php?search="+search , null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try{

                            allproduit=response;
                            if (response.length()>0) {
                                if(response.length()>1){
                                    layoutNaviguer.setVisibility(View.VISIBLE);
                                }
                                else {
                                    layoutNaviguer.setVisibility(View.INVISIBLE);
                                }
                                JSONObject student = response.getJSONObject(0);


                                String reference = student.getString("reference");
                                String titre = student.getString("titre");
                                String prix = student.getString("prix");
                                String quantite = student.getString("quantite");


                                _txtref.setText(reference);
                                _txttitre.setText(titre);
                                _txtprix.setText(prix);
                                _txtquantit.setText(quantite);
                                _btnPrevious.setEnabled(false);

                                layoutAppeler.setVisibility(View.VISIBLE);
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"aucun résultat",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(action.this, "error", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
        Volley.newRequestQueue(action.this).add(jsonArrayRequest);

    }




}
