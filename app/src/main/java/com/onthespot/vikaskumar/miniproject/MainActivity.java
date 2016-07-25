package com.onthespot.vikaskumar.miniproject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.support.job.JobInfo;
import me.tatarka.support.job.JobScheduler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String status;
    Toolbar toolbar;
    private static final long POLL_FREQUENCY = 5000;
    private static final int JOB_ID = 100;
    private JobScheduler mJobScheduler;
    static List<String> listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        setupDrawer();
//      makeStausRequest(getBaseContext());
        setFragment(StatusFragment.newInstance("","", getBaseContext(),listData));

    }


    private void setupJob() {
        mJobScheduler = JobScheduler.getInstance(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                buildJob();
            }
        }, 30000);
    }

    private void buildJob() {

        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, new ComponentName(this, ServiceClass.class));
        builder.setPeriodic(POLL_FREQUENCY)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true);
        mJobScheduler.schedule(builder.build());
    }

    public void requestStatusData(){
        //RequestUtility.requestStatusData("","");
    }

//    private void makeStausRequest(final Context context) {
//
//        RetroInterface i = Utility.createRetrofit();
//        Call<List<StatusBodyPojo>> statusBodyPojoCall = i.getToken(constructHeader());
//        statusBodyPojoCall.enqueue(new Callback<List<StatusBodyPojo>>() {
//            @Override
//            public void onResponse(Call<List<StatusBodyPojo>> call, Response<List<StatusBodyPojo>> response) {
//                Log.i("vikas",response.body().get(0).getPostContent()+ "-post Content" );
//                Utility.parseDatabase(response,context);
//                listData = new ArrayList<>();
//                for(StatusBodyPojo body:response.body()){
//                    Log.i("vikas","data first----"+ body.getPostContent());
//                    listData.add(body.getPostContent());
//                }
//
//
//
//            }
//
//            @Override
//            public void onFailure(Call<List<StatusBodyPojo>> call, Throwable t) {
//
//            }
//        });
//    }


    private String constructHeader() {
        String hashPassword = Utility.getHashString("abcd", "SHA-1");
        String header = "admin3" + ":" + "abcd";
        return "Basic " + Base64.encodeToString(header.getBytes(), Base64.NO_WRAP);
    }

    private void setupDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void setFragment(Fragment fragment){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.container,fragment,"vik");
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_new_feed) {
            setFragment(StatusFragment.newInstance("","",getBaseContext(), listData));
        } else if (id == R.id.nav_users) {
            setFragment(UsersListFragment.newInstance("",""));
        }else if(id == R.id.nav_sign_out){

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
