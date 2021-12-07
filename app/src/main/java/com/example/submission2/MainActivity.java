package com.example.submission2;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.submission2.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final String TAG = MainActivity.class.getSimpleName();
    private final ArrayList<Model> arrayList = new ArrayList<>();
    private final ListAdapter listAdapter = new ListAdapter(arrayList);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.rvUser.setLayoutManager(new LinearLayoutManager(this));
        binding.rvUser.setHasFixedSize(true);

        getDataUser();
//        showRecyleList();
    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null) {
            SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    getSearchUser(newText);
                    listAdapter.getFilter().filter(newText);
                    return false;
                }
            });
        }
        return true;
    }

    private void getDataUser() {
        AndroidNetworking.get("https://api.github.com/users")
                .addHeaders("Authorization", "token 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")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // do anything with response

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String dataLogin = jsonObject.getString("login");

                                getUserDetail(dataLogin);
                                Log.i(TAG, "getDataUser  " + dataLogin);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Log.d(TAG, "onError: " + error); //untuk log pada onerror
                    }
                });
    }


    private void getSearchUser(String newText) {
        showLoading(true);
        AndroidNetworking.get("https://api.github.com/search/users?q=" + newText)
                .addHeaders("Authorization", "token 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")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        showLoading(false);

                        try {
                            JSONArray jsonArray;
                            jsonArray = response.getJSONArray("items");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String data = jsonObject.getString("login");

                                getUserDetail(data);

                            Log.i(TAG, "getSearch  " + data);
                        }

                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }

        @Override
        public void onError (ANError error){
            showLoading(false);
            Log.d(TAG, "onError: " + error); //untuk log pada onerror
        }
    });
}

    private void getUserDetail(String data) {
        showLoading(true);
        AndroidNetworking.get("https://api.github.com/users/" + data)
                .addHeaders("Authorization", "token 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")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        showLoading(false);
                        try {
                            String username = response.getString("login");
                            String name = response.getString("name");
                            String company = response.getString("company");
                            String location = response.getString("location");
                            int repository = response.getInt("public_repos");
                            int following = response.getInt("following");
                            int follower = response.getInt("followers");
                            String avatar = response.getString("avatar_url");


                            Model model = new Model();
                            model.setUsername(username);
                            model.setName(name);
                            model.setCompany(company);
                            model.setLocation(location);
                            model.setRepository(repository);
                            model.setFollowing(following);
                            model.setFollower(follower);
                            model.setAvatar(avatar);

                            arrayList.add(model);
                            ListAdapter adapter = new ListAdapter(arrayList);
                            binding.rvUser.setAdapter(adapter);


//                            arrayList.add(new Model(
//                                    username,
//                                    name,
//                                    company,
//                                    location,
//                                    repository,
//                                    following,
//                                    follower,
//                                    avatar
//                            ));

                            Log.i(TAG, "Detail  " + username);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        showLoading(false);
                        Log.d(TAG, "onError: " + error); //untuk log pada onerror
                    }
                });
    }


    private void showLoading(boolean b) {
        if (b) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
        }
    }
}