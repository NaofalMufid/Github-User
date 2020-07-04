package id.dipikul.githubuser.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.dipikul.githubuser.R;
import id.dipikul.githubuser.adapter.UserAdapter;
import id.dipikul.githubuser.model.MainModel;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.linearNotFound)
    LinearLayout linearNotFound;
    @BindView(R.id.main_search_username)
    SearchView searchUsername;
    private UserAdapter adapter;
    private MainModel mainModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            searchUsername.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchUsername.onActionViewExpanded();
            searchUsername.setQueryHint(getResources().getString(R.string.search_hint));
            searchUsername.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    linearNotFound.setVisibility(View.GONE);
                    showLoading(true);
                    mainModel.fetchUser(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new UserAdapter();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        mainModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainModel.class);
        mainModel.getUsers().observe(this, users -> {
            adapter.setData(users);

            if (users.size() == 0) linearNotFound.setVisibility((View.VISIBLE));
            else linearNotFound.setVisibility(View.GONE);

            showLoading(false);
        });

        adapter.setOnItemClickCallback(data -> {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_USER, data);
            startActivity(intent);
        });
    }

    private void showLoading(Boolean state) {
        if (state) progressBar.setVisibility(View.VISIBLE);
        else progressBar.setVisibility(View.GONE);
    }
}