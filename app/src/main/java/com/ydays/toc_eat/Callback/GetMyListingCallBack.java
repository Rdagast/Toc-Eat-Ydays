package com.ydays.toc_eat.Callback;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by benjaminthomas on 03/05/2017.
 */

public interface GetMyListingCallBack {
    void onSuccess(JSONArray responseArray);

    void onError(String errorMsg);
}
