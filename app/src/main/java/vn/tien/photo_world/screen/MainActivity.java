package vn.tien.photo_world.screen;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import vn.tien.photo_world.R;
import vn.tien.photo_world.constant.Constant;
import vn.tien.photo_world.screen.Favorite.FavoriteFragment;
import vn.tien.photo_world.screen.collection.CollectionFragment;
import vn.tien.photo_world.screen.home.HomeFragment;

public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private HomeFragment mHomeFragment;
    private CollectionFragment mCollectionFragment;
    private FavoriteFragment mFavoriteFragment ;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    private void initComponents() {
        mTabLayout = findViewById(R.id.tab_layout);
        mToolbar = findViewById(R.id.tool_bar);
        mViewPager = findViewById(R.id.view_pager);

        mHomeFragment = new HomeFragment();
        mCollectionFragment = new CollectionFragment();
        mFavoriteFragment = new FavoriteFragment();

        mToolbar.setTitle(R.string.app_name);
        mToolbar.setNavigationIcon(R.drawable.ic_monochrome_photos_black_24dp);
        setSupportActionBar(mToolbar);
        setUpViewPager(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    private void setUpViewPager(ViewPager viewPager) {
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(mHomeFragment, getString(R.string.title_home));
        pagerAdapter.addFragment(mCollectionFragment, getString(R.string.title_collection));
        pagerAdapter.addFragment(mFavoriteFragment,getString(R.string.title_favorites));

        mViewPager.setAdapter(pagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position,
                                       float positionOffset,
                                       int positionOffsetPixels) {
                if (mSearchView != null && !mSearchView.isIconified()) {
                    mSearchView.setIconified(false);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mTitles = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mTitles.add(title);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(this.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.search);
        mSearchView = (SearchView) menu.findItem(R.id.search).getActionView();
        if (searchItem != null) {
            mSearchView = (SearchView) searchItem.getActionView();
        }
        if (mSearchView != null) {
            mSearchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));
        }
        mSearchView.setIconifiedByDefault(true);
        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                PagerAdapter pagerAdapter = mViewPager.getAdapter();
                for (int i = 0; i < pagerAdapter.getCount(); i++) {
                    Fragment viewFragment = (Fragment) mViewPager.getAdapter()
                            .instantiateItem(mViewPager, i);
                    if (viewFragment != null && viewFragment.isAdded()) {
                        if (viewFragment instanceof HomeFragment) {
                            mHomeFragment = (HomeFragment) viewFragment;
                            if (mHomeFragment != null) {
                                mHomeFragment.beginSearch(newText);
                            }
                        }
                        if (viewFragment instanceof CollectionFragment) {
                            mCollectionFragment = (CollectionFragment) viewFragment;
                            if (mCollectionFragment != null) {
                                mCollectionFragment.beginSearch(newText);
                            }
                        }
                    }
                }
                return false;
            }
        };
        mSearchView.setOnQueryTextListener(queryTextListener);
        return super.onCreateOptionsMenu(menu);
    }
}
