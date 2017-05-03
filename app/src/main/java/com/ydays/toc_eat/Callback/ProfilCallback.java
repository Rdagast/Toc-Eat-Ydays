package com.ydays.toc_eat.Callback;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by loic on 02/05/2017.
 */

public interface ProfilCallback {

    void onSuccess(JSONArray user);
    void onError(String errorMsg);
}
