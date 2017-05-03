package com.ydays.toc_eat.Fragment.Navigation;

/**
 * Created by clemb on 22/02/2017.
 */

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ydays.toc_eat.Adapter.RepasAdapter;
import com.ydays.toc_eat.App.AppConfig;
import com.ydays.toc_eat.App.AppController;
import com.ydays.toc_eat.Callback.AddCallBack;
import com.ydays.toc_eat.Callback.GetMyListingCallBack;
import com.ydays.toc_eat.Callback.MessagingCallback;
import com.ydays.toc_eat.Class.Repas;
import com.ydays.toc_eat.toc_eat.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.android.gms.internal.zzt.TAG;


public class MyAdvertFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private ProgressDialog progress;
    private ArrayList<Repas> lesRepas;

    public MyAdvertFragment() {
        // Required empty public constructor
    }

    public static MyAdvertFragment newInstance(int sectionNumber) {
        MyAdvertFragment fragment = new MyAdvertFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_myadvert, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        lesRepas = new ArrayList<Repas>();
        getMyListings(new GetMyListingCallBack() {
            @Override
            public void onSuccess(JSONArray responseArray) {
                for (int i=0; i<responseArray.length(); i++) {
                    try {
                        JSONObject r = responseArray.getJSONObject(i);
                        Repas newRepas = new Repas(r.getInt("id"),
                                r.getString("title"),
                                r.getString("description"),
                                r.getString("ingredient"),
                                r.getInt("participation"));
                        lesRepas.add(newRepas);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                ListView lvRepas = (ListView) view.findViewById(R.id.lvRepas);
                lvRepas.setAdapter(new RepasAdapter(lesRepas,getActivity()));
            }

            @Override
            public void onError(String errorMsg) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabNewListing);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Créer une nouvelle annonce");


                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View viewDialog= inflater.inflate(R.layout.form_newlisting, null);

                builder.setView(viewDialog);

                builder.setPositiveButton("Créer mon annonce", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final EditText edTitle = (EditText) viewDialog.findViewById(R.id.edNTitle);
                        final EditText edRepas = (EditText) viewDialog.findViewById(R.id.edNRepas);
                        final AutoCompleteTextView edDesc = (AutoCompleteTextView) viewDialog.findViewById(R.id.edNDescription);
                        final EditText edPrix = (EditText) viewDialog.findViewById(R.id.edNParticipation);

                        final String title = edTitle.getText().toString();
                        final String repas = edRepas.getText().toString();
                        final String description = edDesc.getText().toString();
                        final String prix = edPrix.getText().toString();
                        Log.d(TAG,title+" "+repas+" "+description+" "+prix);
                        progress = ProgressDialog.show(getContext(), "Chargement",
                                "Création de l'annonce", true);

                        if (title!=""){
                            if (repas!=""){
                                if (description!=""){
                                    if (prix!=""){
                                        addNewListing(new AddCallBack() {
                                            @Override
                                            public void onSuccess() {
                                                progress.dismiss();
                                                Repas newRepas = new Repas(0,title,description,null,prix);
                                                lesRepas.add(newRepas);
                                                ListView lvRepas = (ListView) view.findViewById(R.id.lvRepas);
                                                lvRepas.setAdapter(new RepasAdapter(lesRepas,getActivity()));
                                            }

                                            @Override
                                            public void onError(String errorMsg) {
                                                progress.dismiss();
                                                Toast.makeText(getContext(),errorMsg,Toast.LENGTH_LONG).show();
                                            }
                                        },title,repas,description,prix);
                                    }else {
                                        Toast.makeText(getActivity(),"Le prix doit être renseigné",Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    Toast.makeText(getActivity(),"La descriptione doit être renseigné",Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(getActivity(),"Le repas doit être renseigné",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(getActivity(),"Le titre doit être renseigné",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

    }


    public void addNewListing(final AddCallBack callBack, final String title, final String repas, final String desc, final String prix){
        final String tag_string_req = "req_my";
        Log.d(tag_string_req, AppConfig.url_add);
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.url_add, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, tag_string_req + " Response: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("errors");

                    if (!error) {
                        //JSONArray responseArray = jObj.getJSONArray("listing");
                        callBack.onSuccess();
                    } else {
                        String errorMsg = jObj.getString("messages");
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
                Log.e(TAG, tag_string_req +" adding Error: " + error.getMessage());
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

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("title", title);
                params.put("repas", repas);
                params.put("description", desc);
                params.put("participation", prix);
                //params.put("ingredient", ingredient);
                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void getMyListings(final GetMyListingCallBack callBack){
        final String tag_string_req = "req_my";
        Log.d(tag_string_req, AppConfig.url_my);
        StringRequest strReq = new StringRequest(Request.Method.GET,
                AppConfig.url_my, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, tag_string_req + " Response: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("errors");

                    if (!error) {
                        JSONArray responseArray = jObj.getJSONArray("listing");
                        callBack.onSuccess(responseArray);
                    } else {
                        String errorMsg = jObj.getString("messages");
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
                Log.e(TAG, tag_string_req +" adding Error: " + error.getMessage());
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