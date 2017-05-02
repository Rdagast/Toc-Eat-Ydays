package com.ydays.toc_eat.Callback;

/**
 * Created by clemb on 24/02/2017.
 */

import org.json.JSONArray;
import org.json.JSONObject;

public interface MessagingCallback {
    void onSuccess(JSONArray conversations);

    void onError(String errorMsg);
}