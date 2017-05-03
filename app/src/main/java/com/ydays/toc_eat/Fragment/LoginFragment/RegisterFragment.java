package com.ydays.toc_eat.Fragment.LoginFragment;

/**
 * Created by clemb on 22/02/2017.
 */


import android.app.ProgressDialog;
import android.content.Intent;
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


public class RegisterFragment extends Fragment {
    private String TAG = getClass().getName();
    private static final String ARG_SECTION_NUMBER = "section_number";
    private ProgressDialog progress;

    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance(int sectionNumber) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        final EditText edEmail,edPassword, edPasswordConfirm;
        Button btnRegister;
        final TextView tvErrorEmail,tvErrorPassword;


        btnRegister = (Button) view.findViewById(R.id.btnRegister);
        edEmail = (EditText) view.findViewById(R.id.edEmail);
        edPassword = (EditText) view.findViewById(R.id.edPassword);
        edPasswordConfirm = (EditText) view.findViewById(R.id.edPasswordConfirm);
        tvErrorEmail = (TextView) view.findViewById(R.id.tvErrorEmail);
        tvErrorPassword = (TextView) view.findViewById(R.id.tvErrorPassword);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvErrorPassword.setVisibility(View.GONE);
                tvErrorEmail.setVisibility(View.GONE);
                if(testEmail(edEmail.getText().toString())){
                    if(testPassword(edPassword.getText().toString(), edPasswordConfirm.getText().toString())){
                        tryConnection(edEmail.getText().toString(), edPassword.getText().toString(), edPasswordConfirm.getText().toString(),
                                new LoginCallback() {
                                    @Override
                                    public void onSuccess(JSONObject user) {
                                        Log.d(" result ", "succes");
                                        ((LoginActivity)getActivity()).getmViewPager().setCurrentItem(1);
                                    }

                                    @Override
                                    public void onError(String errorMsg) {
                                        Log.d(" result ", "fail");
                                    }
                                });
                    }else {
                        tvErrorPassword.setVisibility(View.VISIBLE);
                        tvErrorPassword.setText("Password should have 6 caracteres and can't be null");
                    }
                }else {
                    tvErrorEmail.setVisibility(View.VISIBLE);
                    tvErrorEmail.setText("Email can't be null and need real email");
                }
            }
        });
    }

    public boolean testEmail(String email){
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean testPassword(String password, String passwordConfirm){
        if(password.length()>= 6 && password.equals(passwordConfirm)){
            return true;
        }else {
            return false;
        }
    }

    public void tryConnection(final String email, final String password, final String passwordConfirm, final LoginCallback callBack){
        final String tag_string_req = "req_Login";
        Log.d(tag_string_req, AppConfig.url_register);
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.url_register, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, tag_string_req + " Response: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("errors");

                    if (!error) {
                        JSONObject user = jObj.getJSONObject("user");
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
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                params.put("passwordConfirm", passwordConfirm);
                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
