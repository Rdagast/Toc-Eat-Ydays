package com.ydays.toc_eat.Fragment.LoginFragment;

/**
 * Created by clemb on 22/02/2017.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ydays.toc_eat.App.*;
import com.ydays.toc_eat.Callback.*;
import com.ydays.toc_eat.toc_eat.HomeActivity;
import com.ydays.toc_eat.toc_eat.LoginActivity;
import com.ydays.toc_eat.toc_eat.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginFragment extends Fragment {
    private String TAG = getClass().getName();
    private static final String ARG_SECTION_NUMBER = "section_number";

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(int sectionNumber) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        final EditText edEmail,edPassword;
        Button btnLogin;
        final TextView tvErrorEmail,tvErrorPassword;


        btnLogin = (Button) view.findViewById(R.id.btnLogin);
        edEmail = (EditText) view.findViewById(R.id.edEmail);
        edPassword = (EditText) view.findViewById(R.id.edPassword);
        tvErrorEmail = (TextView) view.findViewById(R.id.tvErrorEmail);
        tvErrorPassword = (TextView) view.findViewById(R.id.tvErrorPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                tvErrorPassword.setVisibility(View.GONE);
//                tvErrorEmail.setVisibility(View.GONE);
                if(testEmail(edEmail.getText().toString())){
                    if(testPassword(edPassword.getText().toString())){
                        tryConnection(edEmail.getText().toString(), edPassword.getText().toString(), new LoginCallback() {
                            @Override
                            public void onSuccess(JSONObject user) {

                                SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                                SharedPreferences.Editor editor = pref.edit();
                                try {
                                    editor.putString("auth_token", user.getString("auth_token")); // Storing string
                                    editor.commit(); // commit changes
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                                Intent myIntent = new Intent(getActivity(), HomeActivity.class);
                                startActivity(myIntent);
                            }

                            @Override
                            public void onError(String errorMsg) {
                                Log.d(" result ", errorMsg);
                                Toast.makeText(getContext(), "Mauvais mot de passe ou Mauvais email", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }else {
//                        tvErrorPassword.setVisibility(View.VISIBLE);
//                        tvErrorPassword.setText("Password should have 6 caracteres and can't be null");
                        Toast.makeText(getContext(), "Mauvais mot de passe", Toast.LENGTH_SHORT).show();
                    }
                }else {
//                    tvErrorEmail.setVisibility(View.VISIBLE);
//                    tvErrorEmail.setText("Email can't be null and need real email");
                    Toast.makeText(getContext(), "Mauvais email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean testEmail(String email){
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean testPassword(String password){
        if(password.length()<1){
            return false;
        }else {
            return true;
        }
    }

    public void tryConnection(final String email, final String password, final LoginCallback callBack){
        final String tag_string_req = "req_Login";
        Log.d(tag_string_req,AppConfig.url_login);
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.url_login, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, tag_string_req + " Response: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("errors");

                    if (!error) {
                        callBack.onSuccess(jObj);
                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        callBack.onError(errorMsg);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(),"Erreur",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, tag_string_req +" findUser Error: " + error.getMessage());
                Toast.makeText(getActivity(),"Erreur",Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}

