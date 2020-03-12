package vn.tien.photo_world.screen.collection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.tien.photo_world.R;
import vn.tien.photo_world.data.model.Collection;

public class CollectionFragment extends Fragment implements CollectionContract.View {
    private RecyclerView mRecyclerView;
    private CollectionAdapter mAdapter;
    private List<Collection> mCollections = new ArrayList<>();
    private CollectionPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_collection);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mPresenter = new CollectionPresenter(this);
        mPresenter.getCollection();
        return view;
    }

    public void beginSearch(String newText) {
        final List<Collection> filteredList = filter(mCollections, newText);
        mAdapter.setSearchResult(filteredList);
    }

    @Override
    public void onGetDataSuccess(List<Collection> collections) {
        mAdapter = new CollectionAdapter(getContext(), collections);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mCollections = collections;
    }

    @Override
    public void onGetDataFailure(String message) {

    }

    private List<Collection> filter(List<Collection> collections, String query) {
        query = query.toLowerCase();
        final List<Collection> filteredCollections = new ArrayList<>();
        for (Collection collection : collections) {
            final String text = collection.getTitle().toLowerCase();
            if (text.contains(query)) {
                filteredCollections.add(collection);
            }
        }
        return filteredCollections;
    }
}
