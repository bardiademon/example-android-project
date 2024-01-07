package com.bardiademon.example.ui.activities;

import android.annotation.SuppressLint;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bardiademon.example.R;
import com.bardiademon.example.data.dto.ResponseDto;
import com.bardiademon.example.data.dto.UsersDto;
import com.bardiademon.example.ui.adapter.UserListAdapter;
import com.bardiademon.example.ui.viewmodels.UserModelView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private UserModelView userModelView;

    private ImageView imgMenu;
    private ImageView imgSearch;
    private TextView txtToolbarName;
    private RecyclerView recyclerView;

    private UserListAdapter userListAdapter;

    private final List<UsersDto> users = new ArrayList<>();

    private int currentPage = 1;
    private int maxPage;

    private boolean fetch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initial();
    }

    private void initial() {
        setTools();

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0 && currentPage < maxPage) {
                    currentPage++;
                    fetchUser();
                }
            }
        });


        userListAdapter = new UserListAdapter(users);
        recyclerView.setAdapter(userListAdapter);

        userModelView = new UserModelView(this);
        fetchUser();
    }

    private void fetchUser() {
        if (fetch) {
            return;
        }
        fetch = true;
        userModelView.fetch(currentPage, listResponseDto -> {
            user(listResponseDto);
            return null;
        });
    }

    private void setTools() {
        imgMenu = findViewById(R.id.menu);
        txtToolbarName = findViewById(R.id.toolbar_txt_name);
        imgSearch = findViewById(R.id.toolbar_img_search);
        recyclerView = findViewById(R.id.recycler_view);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void user(final ResponseDto<List<UsersDto>> response) {
        if (response == null) {
            return;
        }
        maxPage = response.perPage();
        users.addAll(response.data());

        runOnUiThread(() -> {
            userListAdapter.setUsers(users);
            userListAdapter.notifyDataSetChanged();
            fetch = false;
        });


    }

}