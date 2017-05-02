package com.ydays.toc_eat.toc_eat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ydays.toc_eat.Adapter.ConvAdapter;
import com.ydays.toc_eat.App.AppConfig;
import com.ydays.toc_eat.App.AppController;
import com.ydays.toc_eat.Callback.LoginCallback;
import com.ydays.toc_eat.Callback.MessagingCallback;
import com.ydays.toc_eat.Class.Conversation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    private String TAG = getClass().getName();
    Intent myIntent;
    String idConv;
    ListView chats;
    Button sendMessage;
    EditText messageToSend;
    ListAdapter listAdapter;
    final ArrayList<String> listMessages = new ArrayList<String>();;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        myIntent = getIntent();
        idConv = myIntent.getExtras().getString("id");
        chats = (ListView) findViewById(R.id.listChat);
        sendMessage = (Button) findViewById(R.id.sendMessage);


        tryGetChat(new MessagingCallback()  {
            @Override
            public void onSuccess(JSONArray conversations) {
                Log.d(" result ", "succes conv");



                for (int i=0; i<conversations.length(); i++) {
                    JSONObject message = null;
                    try {
                        message = conversations.getJSONObject(i);
                        listMessages.add(message.getString("body"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                listAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listMessages);
                chats.setAdapter(listAdapter);

            }

            @Override
            public void onError(String errorMsg) {
                Log.d(" result ", errorMsg);
            }
        });

        sendMessage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                messageToSend = (EditText) findViewById(R.id.addMessage);

                tryAddMessage(new LoginCallback()  {
                    @Override
                    public void onSuccess(JSONObject response) {
                        try {
                            Log.d(" result ", response.getString("message"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        listMessages.add(messageToSend.getText().toString());
                        chats.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listMessages) );
                        InputMethodManager imm = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                        messageToSend.setText("");
                    }

                    @Override
                    public void onError(String errorMsg) {
                        Log.d(" result ", errorMsg);
                    }
                });
            }
        });
    }


    public void tryGetChat(final MessagingCallback callBack){
        final String tag_string_req = "req_All_Message";
        Log.d(tag_string_req, AppConfig.url_chat);
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.url_chat, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, tag_string_req + " Response: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("errors");

                    if (!error) {
                        JSONObject jObj2 = jObj.getJSONObject("conversation");
                        JSONArray responseArray = jObj2.getJSONArray("message");
                        callBack.onSuccess(responseArray);
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
                SharedPreferences settings = getApplicationContext().getSharedPreferences("MyPref", 0);
                String auth = settings.getString("auth_token"," ");
                headers.put("Authorization", "Bearer " + auth);
                return headers;
            }
            @Override
            public Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("conversation_id", idConv);
                    return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    public void tryAddMessage(final LoginCallback callBack){
        final String tag_string_req = "req_All_Conv";
        Log.d(tag_string_req, AppConfig.url_send_message);
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.url_send_message, new Response.Listener<String>() {

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
                SharedPreferences settings = getApplicationContext().getSharedPreferences("MyPref", 0);
                String auth = settings.getString("auth_token"," ");
                headers.put("Authorization", "Bearer " + auth);
                return headers;
            }
            @Override
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("message", messageToSend.getText().toString());
                params.put("conversation_id", idConv);
                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }



}
