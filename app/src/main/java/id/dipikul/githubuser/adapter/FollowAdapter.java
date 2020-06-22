package id.dipikul.githubuser.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.dipikul.githubuser.R;
import id.dipikul.githubuser.activity.DetailActivity;
import id.dipikul.githubuser.model.User;

public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.FollowViewHolder> {
    private ArrayList<User> mData = new ArrayList<>();

    public void setData(ArrayList<User> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    public FollowAdapter() {

    }

    @NonNull
    @Override
    public FollowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_follow, parent, false);
        return new FollowViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowAdapter.FollowViewHolder holder, final int position) {
        holder.setItem(mData.get(position));
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_USER, mData.get(position));
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class FollowViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.follow_txt_htmlUrl)
        TextView htmlUrl;
        @BindView(R.id.follow_txt_username)
        TextView username;
        @BindView(R.id.follow_img_avatar)
        ImageView avatar;

        public FollowViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setItem(User item) {
            htmlUrl.setText(item.getHtmlUrl());
            username.setText(item.getUsername());
            Picasso.get()
                    .load(item.getAvatar())
                    .resize(100, 100)
                    .centerCrop()
                    .into(avatar);
        }
    }
}
