package com.ydays.toc_eat.Callback;

/**
 * Created by clemb on 22/02/2017.
 */

import org.json.JSONObject;

public interface LoginCallback {
    void onSuccess(JSONObject user);

    void onError(String errorMsg);
}
