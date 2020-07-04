package id.dipikul.githubuser.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import id.dipikul.githubuser.R;
import id.dipikul.githubuser.adapter.FollowAdapter;
import id.dipikul.githubuser.model.DetailModel;

public class FollowFragment extends Fragment {
    private RecyclerView rootView;
    private String username, queryType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_follow, container, false);
        rootView = view.findViewById(R.id.follow_recycler_view);
        rootView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL));

        assert getArguments() != null;
        username = getArguments().getString("username");
        queryType = getArguments().getString("query");
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FollowAdapter adapter = new FollowAdapter();
        rootView.setAdapter(adapter);

        DetailModel detailModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(DetailModel.class);
        detailModel.fetchFollow(username, queryType);
        detailModel.getUsers().observe(getViewLifecycleOwner(), adapter::setData);
    }
}