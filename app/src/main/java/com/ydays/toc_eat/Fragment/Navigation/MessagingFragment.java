package com.ydays.toc_eat.Fragment.Navigation;

/**
 * Created by clemb on 22/02/2017.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Debug;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ydays.toc_eat.Adapter.ConvAdapter;
import com.ydays.toc_eat.App.AppConfig;
import com.ydays.toc_eat.App.AppController;
import com.ydays.toc_eat.Callback.MessagingCallback;
import com.ydays.toc_eat.Class.Conversation;
import com.ydays.toc_eat.toc_eat.ChatActivity;
import com.ydays.toc_eat.toc_eat.HomeActivity;
import com.ydays.toc_eat.toc_eat.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MessagingFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private String TAG = getClass().getName();


    public MessagingFragment() {
        // Required empty public constructor
    }

    public static MessagingFragment newInstance(int sectionNumber) {
        MessagingFragment fragment = new MessagingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_messaging, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        tryConnection( new MessagingCallback() {
                    @Override
                    public void onSuccess(JSONArray conversations) {
                        Log.d(" result ", "succes conv");

                        final ListView listViewConv = (ListView) view.findViewById(R.id.listConv);
                        final ArrayList<Conversation> listConv = new ArrayList<Conversation>();

                        for (int i=0; i<conversations.length(); i++) {

                            try {
                                JSONObject conv = conversations.getJSONObject(i);

                                String id = Integer.toString(conv.getInt("id"));
                                String title = conv.getString("title");
                                String recipient_id = Integer.toString(conv.getInt("recipient_id"));
                                String sender_id = Integer.toString(conv.getInt("sender_id"));
                                String created_ad = conv.getString("created_at");
                                String updated_at = conv.getString("updated_at");

                                Log.d("id", id);
                                Log.d("title", title);
                                Conversation conversation = new Conversation(id,title,recipient_id,sender_id,created_ad,updated_at);

                                listConv.add(conversation);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        listViewConv.setAdapter(new ConvAdapter(listConv,getContext()));

                        listViewConv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position,
                                                    long id) {
                                String itemId = listConv.get(listViewConv.getSelectedItemPosition()+1).id;
                                Intent myIntent = new Intent(getActivity(), ChatActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("id", itemId);
                                myIntent.putExtras(bundle);
                                startActivity(myIntent);
                            }

                        });
                    }

                    @Override
                    public void onError(String errorMsg) {
                        Log.d(" result ", errorMsg);
                    }
        });
    }


    public void tryConnection(final MessagingCallback callBack){
        final String tag_string_req = "req_All_Conv";
        Log.d(tag_string_req, AppConfig.url_convers);
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.url_convers, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, tag_string_req + " Response: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("errors");

                    if (!error) {
                        JSONArray responseArray = jObj.getJSONArray("conversation");
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
