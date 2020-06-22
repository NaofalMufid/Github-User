package id.dipikul.githubuser.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;

public class MainModel extends ViewModel {
    private MutableLiveData<ArrayList<User>> listUsers = new MutableLiveData<>();

    public void fetchUser(final String username) {
        final ArrayList<User> listItems = new ArrayList<>();

        String url = "https://api.github.com/search/users?q=" + username;

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("User-Agent", "request");
        client.addHeader("Authorization", "DELETE TOKEN");
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject respJsonObject = new JSONObject(result);
                    JSONArray list = respJsonObject.getJSONArray("items");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject object = list.getJSONObject(i);
                        User user = new User();
                        user.setUsername(object.getString("login"));
                        user.setHtmlUrl(object.getString("html_url"));
                        user.setAvatar(object.getString("avatar_url"));

                        listItems.add(user);
                    }
                    listUsers.postValue(listItems);
                } catch (Exception e) {
                    Log.d("[Exception] : ", Objects.requireNonNull(e.getMessage()));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("[OnFailure]", Objects.requireNonNull(error.getMessage()));
            }
        });
    }

    public LiveData<ArrayList<User>> getUsers() {
        return listUsers;
    }
}
