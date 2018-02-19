package com.example.priyanka.mediatorngo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.priyanka.mediatorngo.Fragments.PickupRequestsFragment;
import com.example.priyanka.mediatorngo.Helpers.CircleTransform;
import com.example.priyanka.mediatorngo.Models.NGO;
import com.example.priyanka.mediatorngo.Models.NGOResponse;
import com.example.priyanka.mediatorngo.Network.ApiClient;
import com.example.priyanka.mediatorngo.Network.ApiInterface;
import com.example.priyanka.mediatorngo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by priyanka on 1/31/18.
 */

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    private FirebaseAuth mAuth;
    private ViewPager viewPager;

    private NGO ngo;
    TextView TVname, TVemail;
    String name,email;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private NavigationView navigationView;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;


    private static final String urlNavHeaderBg = "https://api.androidhive.info/images/nav-menu-header-bg.jpg";
    private static final String urlProfileImg = "https://pbs.twimg.com/profile_images/3766735565/6e0118f54f242cf5978fa990c9c838df.jpeg";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        String email = currentUser.getEmail();
        Log.d("current user",currentUser.toString());
        Log.d("Email",email);
        getNGO(email);

        TVname = (TextView)findViewById(R.id.user_name);
        TVemail = (TextView)findViewById(R.id.email);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);


        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.openDrawer,R.string.closeDrawer );
        mToolbar = (Toolbar)findViewById(R.id.nav_action_bar);
        setSupportActionBar(mToolbar);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        TVname = (TextView) navHeader.findViewById(R.id.user_name);
        TVemail = (TextView) navHeader.findViewById(R.id.email);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);
        // load nav menu header data
        loadNavHeader();
        // initializing navigation menu
        setUpNavigationView();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(mToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void settings()
    {
        Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
        startActivity(intent);
    }


    private void loadNavHeader() {
        // name, website

        // loading header background image
        Glide.with(this).load(urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);

        // Loading profile image
        Glide.with(this).load(urlProfileImg)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);


    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_activity_home:
                        mDrawerLayout.closeDrawers();
                        return true;
                    case R.id.nav_settings:
                        settings();
                        break;
                    case R.id.nav_logout:
                        break;

                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);


                return true;
            }
        });
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        Bundle bundle = new Bundle();
        Fragment mrFragment = new PickupRequestsFragment();
        adapter.addFragment(mrFragment, "Current");
        viewPager.setAdapter(adapter);

    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }



    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.B_order_medicine: orderMedicine(); break;

        }
    }


    public void orderMedicine()
    {
        Intent intent = new Intent(HomeActivity.this, OrderMedicineActivity.class);
        startActivity(intent);


    }

    public void getNGO(String email)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<NGOResponse> call = apiService.getNGO(email);
        call.enqueue(new Callback<NGOResponse>() {
            @Override
            public void onResponse(Call<NGOResponse> call, Response<NGOResponse> response) {
                Log.d("response",response.message());
                ngo = response.body().getNGO();
                int id = response.body().getId();
                ngo.setId(id);
                Log.d("Id fetched", ""+id);
                TVname.setText(ngo.getName());
                TVemail.setText(ngo.getEmailId());

            }

            @Override
            public void onFailure(Call<NGOResponse> call, Throwable t) {
                Log.d("Failure",t.getMessage());
            }
        });
    }

}
