package com.ydays.toc_eat.Fragment.Navigation;

/**
 * Created by clemb on 22/02/2017.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ydays.toc_eat.App.AppConfig;
import com.ydays.toc_eat.App.AppController;
import com.ydays.toc_eat.Callback.ProfilCallback;
import com.ydays.toc_eat.Callback.SearchCallback;
import com.ydays.toc_eat.toc_eat.HomeActivity;
import com.ydays.toc_eat.toc_eat.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.google.android.gms.wearable.DataMap.TAG;
import static com.ydays.toc_eat.toc_eat.R.*;
import static com.ydays.toc_eat.toc_eat.R.layout.fragment_modify_profile;
import static com.ydays.toc_eat.toc_eat.R.layout.fragment_profile;


public class ProfileFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private JSONArray user;
    private String prenom;
    private String nom;
    private String birthday;
    private String email;
    private String adresse;
    private String postal;
    private String ville;
    private String statut;
    private String repas;
    private String desc;
    private String lastNote;

    private TextView editNom;
    private TextView editPrenom;
    private TextView editBirthday;
    private TextView editAdresse;
    private TextView editPostal;
    private TextView editVille;
    private TextView editStatut;
    private TextView editRepas;
    private TextView editDesc;
    private TextView editLastNote;
    private TextView editEmail;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(int sectionNumber) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(fragment_profile, container, false);
        editEmail = (TextView) v.findViewById(id.email);
        editAdresse = (TextView) v.findViewById(id.adresse);
        editBirthday = (TextView) v.findViewById(id.birthday);
        editDesc = (TextView) v.findViewById(id.description);
        editLastNote = (TextView) v.findViewById(id.lastnote);
        editPostal = (TextView) v.findViewById(id.postal);
        editPrenom = (TextView) v.findViewById(id.prenom);
        editStatut = (TextView) v.findViewById(id.statut);
        editNom = (TextView) v.findViewById(id.nom);
        editRepas = (TextView) v.findViewById(id.repas);
        editVille = (TextView) v.findViewById(id.ville);



        return v;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        tryConnection( new ProfilCallback() {
            @Override
            public void onSuccess(JSONArray user) {
                Log.d(" result ", "succes listing");
                JSONArray use = user;

                for (int i=0; i<use.length(); i++) {
                    try {
                        JSONObject infos = use.getJSONObject(i);
                        prenom = infos.getString("first_name");
                        editPrenom.setText(prenom);
                        email = infos.getString("email");
                        editEmail.setText(email);
                        ville = infos.getString("ville");
                        editVille.setText(ville);
                        nom = infos.getString("last_name");
                        editNom.setText(nom);
                        postal = infos.getString("postal");
                        editPostal.setText(postal);
                        repas = infos.getString("repas_pref");
                        editRepas.setText(repas);
                        birthday= infos.getString("birthday");
                        editBirthday.setText(birthday);
                        statut = infos.getString("statut");
                        editStatut.setText(statut);
                        desc = infos.getString("description");
                        editDesc.setText(desc);
                        adresse = infos.getString("adress");
                        editAdresse.setText(adresse);
                        lastNote = infos.getString("last_note");
                        editLastNote.setText(lastNote);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
        }


            @Override
            public void onError(String errorMsg) {
                Log.d(" result ", errorMsg);
            }
        });
    }
    public void tryConnection(final ProfilCallback callBack){
        final String tag_string_req = "req_Login";
        Log.d(tag_string_req, AppConfig.url_user);
        StringRequest strReq = new StringRequest(Request.Method.GET,
                AppConfig.url_user, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, tag_string_req + " Response: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response.toString());
                    boolean error = jObj.getBoolean("errors");

                    if (!error) {
                        user = jObj.getJSONArray("user");
                        callBack.onSuccess(user);
                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        callBack.onError(errorMsg);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    callBack.onError(e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, tag_string_req +" findUser Error: " + error.getMessage());
                callBack.onError(error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
            SharedPreferences settings = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0);
            String auth = settings.getString("auth_token"," ");
            headers.put("Authorization", "Bearer " + auth);
            return headers;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}