package projeto.les.doevida.doevida.utils;

import org.json.JSONException;
import org.json.JSONObject;


public interface HttpListener {
    void onSucess(JSONObject response) throws JSONException;
    void onTimeout();
}