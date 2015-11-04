package com.project.workgroup.piuradivierte.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.project.workgroup.model.entidades.EventsWrapper;
import com.project.workgroup.piuradivierte.EventsApp;
import com.project.workgroup.piuradivierte.R;
import com.project.workgroup.piuradivierte.WelcomeActivity;
import com.project.workgroup.piuradivierte.di.components.DaggerBasicEventsUsecaseComponent;
import com.project.workgroup.piuradivierte.di.modules.BasicEventsUsecaseModule;
import com.project.workgroup.piuradivierte.mvp.presenter.EventsPresenter;
import com.project.workgroup.piuradivierte.util.PrefUtils;
import com.project.workgroup.piuradivierte.util.RecyclerViewClickListener;
import com.project.workgroup.piuradivierte.view.adapter.EventsAdapter;
import com.project.workgroup.piuradivierte.view.fragments.EventsFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

public class MainActivity extends AppCompatActivity implements RecyclerViewClickListener {


    private EventsAdapter mEventsAdapter;


    @Optional
    @InjectView(R.id.activity_event_recycler)           RecyclerView mRecycler;
    @InjectView(R.id.toolbar)                           Toolbar toolbar;
    @InjectView(R.id.drawer_layout)                     DrawerLayout drawerLayout;
    @InjectView(R.id.nav_view)                          NavigationView navigationView;

    private String drawerTitle;

    @Inject EventsPresenter mEventsPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);


        initializeDependencyInjector();

        setToolbar();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();


        if(navigationView!=null){
            setupDrawerContent(navigationView);
        }
        drawerTitle = getResources().getString(R.string.event_title);



        //mEventsPresenter = new EventsPresenter();


        if(!PrefUtils.isTosAccepted(this)){
            Intent i = new Intent(this, WelcomeActivity.class);
            startActivity(i);
            finish();
        }


        //inicializarRecycler();



        if(savedInstanceState==null){
           //mEventsPresenter.attachView(this);
        }
        else{

        }
        /*

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        */


    }

    private void setToolbar(){
        setSupportActionBar(toolbar);

    }

    private void setupDrawerContent (NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //marcar item seleccionado
                menuItem.setChecked(true);
                //crear nuevo fragmento
                String title = menuItem.getTitle().toString();
                selectItem(title);
                return true;
            }
        });
    }


    private void selectItem(String title){
        //enaviar titulo como argumento del fragmento
        Bundle args = new Bundle();
        Fragment fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();

        if(title =="Eventos"){
            args.putString(EventsFragment.ARG_SECTION_TITLE, title);
            fragment = EventsFragment.newInstance(title);
            fragment.setArguments(args);
            fragmentManager.beginTransaction().replace(R.id.main_content,fragment).commit();
        }



        drawerLayout.closeDrawers();
        setTitle(title);
    }




    @Override
    protected void onStart() {
        super.onStart();
        mEventsPresenter.start();
    }
    private void initializeFromParams(Bundle saveInstance){
        EventsWrapper eventsWrapper = (EventsWrapper) saveInstance.getSerializable(null);
        mEventsPresenter.onPopularEventsRecivrd(eventsWrapper);
    }


    private void initializeDependencyInjector(){
        EventsApp app = (EventsApp) getApplication();
        DaggerBasicEventsUsecaseComponent.builder()
                .appComponent(app.getAppComponent())
                .basicEventsUsecaseModule(new BasicEventsUsecaseModule())
                .build().inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id==android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
        }

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void inicializarRecycler(){

        //mRecycler.addItemDecoration(new RecyclerInsetsDecoration(this));
        //mRecycler.setOnScrollListener(recyclerScrollListener);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mEventsAdapter!=null){
            outState.putSerializable("", new EventsWrapper(mEventsAdapter.getEventList()));
        }
    }

    private void showToolbar() {

        toolbar.startAnimation(AnimationUtils.loadAnimation(this,
                R.anim.translate_up_off));
    }

    private void hideToolbar() {

        toolbar.startAnimation(AnimationUtils.loadAnimation(this,
                R.anim.translate_up_on));
    }


    @Override
    public void onClick(View v, int eventPosition , float touchedX, float touchedY) {


    }



}
