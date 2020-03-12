package vn.tien.photo_world.screen.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.tien.photo_world.R;
import vn.tien.photo_world.data.model.Photo;

public class HomeFragment extends Fragment implements HomeContract.View {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private HomeAdapter mAdapter;
    private List<Photo> mPhotoList = new ArrayList<>();
    private HomePresenter mHomePresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_home);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mHomePresenter = new HomePresenter(this);
        mHomePresenter.getPhoto();
        return view;
    }

    @Override
    public void onGetPhotoSuccess(List<Photo> photo) {
        mAdapter = new HomeAdapter(getContext(), photo);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mPhotoList = photo;
    }

    @Override
    public void onGetDataFailure(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void beginSearch(String newText) {
        final List<Photo> filteredList = filter(mPhotoList, newText);
        mAdapter.setSearchResult(filteredList);
    }

    private List<Photo> filter(List<Photo> photos, String query) {
        query = query.toLowerCase();
        final List<Photo> filteredPhotos = new ArrayList<>();
        for (Photo photo : photos) {
            final String text = photo.getAuthor().getName().toLowerCase();
            if (text.contains(query)) {
                filteredPhotos.add(photo);
            }
        }
        return filteredPhotos;
    }


}
