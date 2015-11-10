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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.project.workgroup.model.entidades.EventsWrapper;
import com.project.workgroup.piuradivierte.EventsApp;
import com.project.workgroup.piuradivierte.R;
import com.project.workgroup.piuradivierte.WelcomeActivity;
import com.project.workgroup.piuradivierte.di.components.DaggerBasicEventsUsecaseComponent;
import com.project.workgroup.piuradivierte.di.modules.BasicEventsUsecaseModule;
import com.project.workgroup.piuradivierte.mvp.presenter.EventsPresenter;
import com.project.workgroup.piuradivierte.util.PrefUtils;
import com.project.workgroup.piuradivierte.view.fragments.EventsFragment;
import com.project.workgroup.piuradivierte.view.fragments.MisEventosFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar)                           Toolbar toolbar;
    @InjectView(R.id.drawer_layout)                     DrawerLayout drawerLayout;
    @InjectView(R.id.nav_view)                          NavigationView navigationView;
    private String drawerTitle;
    @Inject public static EventsPresenter mEventsPresenter;
    private final static String BUNDLE_EVENTS_WRAPPER = "events_wrapper";

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

        if(savedInstanceState==null){

        }else {
            //initializeFromParams(savedInstanceState);
        }


        if(!PrefUtils.isTosAccepted(this)){
            Intent i = new Intent(this, WelcomeActivity.class);
            startActivity(i);
            finish();
        }
    }

    private void setToolbar(){
        setSupportActionBar(toolbar);

    }
    private void initializeDependencyInjector(){

        Log.e("initializeDependency", " se inicio initializeDependency");
        EventsApp app = (EventsApp) getApplication();
        DaggerBasicEventsUsecaseComponent.builder()
                .appComponent(app.getAppComponent())
                .basicEventsUsecaseModule(new BasicEventsUsecaseModule())
                .build().inject(this);
    }

    private void setupDrawerContent (NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //marcar item seleccionado
                menuItem.setChecked(true);
                //crear nuevo fragmento
                String title = menuItem.getTitle().toString();

                Log.e("Titulo", title);
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

        if(title.equals("Eventos")){
            args.putString(EventsFragment.ARG_SECTION_TITLE, title);
            fragment = EventsFragment.newInstance(title);
            fragment.setArguments(args);
            fragmentManager.beginTransaction().replace(R.id.main_content,fragment).commit();
            Log.e("Fragment","Eventos");
        }
        if(title.equals("Mis Eventos")){
            args.putString(MisEventosFragment.ARG_SECTION_TITLE, title);
            fragment = MisEventosFragment.newInstance(title);
            fragment.setArguments(args);
            fragmentManager.beginTransaction().replace(R.id.main_content,fragment).commit();
            Log.e("Fragment", "Mis Eventos");
        }

        drawerLayout.closeDrawers();
        setTitle(title);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_base_drawer, menu);
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





}
