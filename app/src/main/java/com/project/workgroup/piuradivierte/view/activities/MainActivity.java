package com.project.workgroup.piuradivierte.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.FrameLayout;

import com.mingle.widget.LoadingView;
import com.project.workgroup.model.entidades.Event;
import com.project.workgroup.model.entidades.EventsWrapper;
import com.project.workgroup.piuradivierte.BaseActivity;
import com.project.workgroup.piuradivierte.EventsApp;
import com.project.workgroup.piuradivierte.R;
import com.project.workgroup.piuradivierte.WelcomeActivity;
import com.project.workgroup.piuradivierte.di.components.DaggerBasicEventsUsecaseComponent;
import com.project.workgroup.piuradivierte.di.modules.BasicEventsUsecaseModule;
import com.project.workgroup.piuradivierte.mvp.presenter.EventsPresenter;
import com.project.workgroup.piuradivierte.mvp.views.EventsView;
import com.project.workgroup.piuradivierte.util.PrefUtils;
import com.project.workgroup.piuradivierte.util.RecyclerInsetsDecoration;
import com.project.workgroup.piuradivierte.util.RecyclerViewClickListener;
import com.project.workgroup.piuradivierte.view.adapter.EventsAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

public class MainActivity extends BaseActivity implements EventsView, RecyclerViewClickListener {


    private EventsAdapter mEventsAdapter;
    private LoadingView mLoadingView;

    @Optional
    @InjectView(R.id.activity_event_recycler)           RecyclerView mRecycler;
    @Inject EventsPresenter mEventsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //mEventsPresenter = new EventsPresenter();
        mRecycler = (RecyclerView) findViewById(R.id.activity_event_recycler);
        mLoadingView = (LoadingView)findViewById(R.id.loadView);

        if(!PrefUtils.isTosAccepted(this)){
            Intent i = new Intent(this, WelcomeActivity.class);
            startActivity(i);
            finish();
        }

        initializeDependencyInjector();
        inicializarRecycler();



        if(savedInstanceState==null){
           mEventsPresenter.attachView(this);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void inicializarRecycler(){

        //mRecycler.addItemDecoration(new RecyclerInsetsDecoration(this));
        mRecycler.setOnScrollListener(recyclerScrollListener);
    }
    private RecyclerView.OnScrollListener recyclerScrollListener = new RecyclerView.OnScrollListener() {
        public boolean flag;
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount    = mRecycler.getLayoutManager().getChildCount();
            int totalItemCount      = mRecycler.getLayoutManager().getItemCount();
            int pastVisibleItems    = ((GridLayoutManager) mRecycler.getLayoutManager())
                    .findFirstVisibleItemPosition();

            if((visibleItemCount + pastVisibleItems) >= totalItemCount && !mEventsPresenter.isLoading()) {
                mEventsPresenter.onEndListReached();
            }

            // Is scrolling up
            if (dy > 10) {

                if (!flag) {
                    showToolbar();
                    flag = true;
                }

                // Is scrolling down
            } else if (dy < -10) {

                if (flag) {
                    hideToolbar();
                    flag = false;
                }
            }

        }
    };

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
    public Context getContext() {
        return this;
    }

    @Override
    public void showEvents(List<Event> eventList) {
        mEventsAdapter = new EventsAdapter(eventList);
        mEventsAdapter.setRecyclerlistListener(this);
        mRecycler.setAdapter(mEventsAdapter);
    }



    @Override
    public void showLoading() {
        mLoadingView.animate();
    }

    @Override
    public void hideLoading() {
        mLoadingView.cancelLongPress();
    }


    @Override
    public boolean isTheListEmpety() {
        return (mEventsAdapter==null || mEventsAdapter.getEventList().isEmpty());
    }

    @Override
    public void appendMovies(List<Event> eventList) {
        mEventsAdapter.appendEvent(eventList);
    }

    @Override
    public void onClick(View v, int eventPosition , float touchedX, float touchedY) {
        Intent i = new Intent(MainActivity.this, null);

    }



}
