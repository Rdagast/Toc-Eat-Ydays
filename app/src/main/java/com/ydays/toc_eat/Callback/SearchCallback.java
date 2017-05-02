package com.ydays.toc_eat.Callback;

/**
 * Created by clemb on 22/02/2017.
 */

import org.json.JSONArray;
import org.json.JSONObject;

public interface SearchCallback {
    void onSuccess(JSONArray listing);

    void onError(String errorMsg);
}
