package vn.tien.photo_world.screen.Favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

import vn.tien.photo_world.R;
import vn.tien.photo_world.data.model.Wallpapers;

public class FavoriteFragment extends Fragment {
    private RecyclerView mRecyclerView;
    public static List<Wallpapers> mWallpapers = new LinkedList<>();
    private FavoriteAdapter mFavoriteAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_favorite);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        return view;
    }

}
