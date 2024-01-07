package com.bardiademon.example.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bardiademon.example.R;
import com.bardiademon.example.data.dto.UsersDto;
import com.bumptech.glide.Glide;
import de.hdodenhof.circleimageview.CircleImageView;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {


    @Setter
    private List<UsersDto> users;

    public UserListAdapter(final List<UsersDto> users) {
        this.users = users;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, final int position) {
        final UsersDto user = users.get(position);
        Glide.with(holder.itemView).load(user.uriAvatar()).placeholder(R.drawable.bardiademon).into(holder.getImgProfile());
        holder.getTxtName().setText(String.format("%s %s", user.firstName(), user.lastName()));
        holder.getTxtEmail().setText(user.email());
        holder.getTxtId().setText(String.valueOf(user.id()));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @Getter
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final CircleImageView imgProfile;
        private final TextView txtName;
        private final TextView txtEmail;
        private final TextView txtId;

        public ViewHolder(@NonNull View view) {
            super(view);
            imgProfile = view.findViewById(R.id.user_list_item__profile_image);
            txtName = view.findViewById(R.id.user_list_item__txt_name);
            txtEmail = view.findViewById(R.id.user_list_item__txt_email);
            txtId = view.findViewById(R.id.user_list_item__txt_id);
        }
    }
}
