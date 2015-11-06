package com.project.workgroup.piuradivierte.view.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mingle.widget.LoadingView;
import com.project.workgroup.model.entidades.Event;
import com.project.workgroup.model.entidades.EventsWrapper;
import com.project.workgroup.piuradivierte.EventsApp;
import com.project.workgroup.piuradivierte.R;
import com.project.workgroup.piuradivierte.di.components.DaggerBasicEventsUsecaseComponent;
import com.project.workgroup.piuradivierte.di.modules.BasicEventsUsecaseModule;
import com.project.workgroup.piuradivierte.mvp.presenter.EventsPresenter;
import com.project.workgroup.piuradivierte.mvp.views.EventsView;
import com.project.workgroup.piuradivierte.util.RecyclerViewClickListener;
import com.project.workgroup.piuradivierte.view.adapter.EventsAdapter;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Junior on 03/11/2015.
 */
public class EventsFragment extends Fragment implements RecyclerViewClickListener , EventsView{


    public static final String ARG_SECTION_TITLE = "section_number";
    private final static String BUNDLE_EVENTS_WRAPPER = "events_wrapper";
    private LoadingView mLoadingView;
    private RecyclerView mRecycler;
    private EventsAdapter mEventsAdapter;
    @Inject EventsPresenter mEventsPresenter;
    Context context = getContext();
    Activity main = getActivity();

    public  static EventsFragment newInstance(String sectionTitle){
        EventsFragment fragment =  new EventsFragment();
        Bundle args = new Bundle();
        args.getString(ARG_SECTION_TITLE, sectionTitle);
        fragment.setArguments(args);

        return fragment;
    }
    public EventsFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeDependencyInjector();
        if (savedInstanceState == null){

            mEventsPresenter.attachView(this);
        }
            initializeFromParams(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_events, container, false);
        mRecycler = (RecyclerView) rootView.findViewById(R.id.activity_event_recycler);
        initializeRecycler();


        return rootView;
    }
    @Override
    public void onStart() {
        super.onStart();
        mEventsPresenter.start();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mEventsAdapter!=null){
            outState.putSerializable("", new EventsWrapper(mEventsAdapter.getEventList()));
        }
    }

    private void initializeDependencyInjector(){
        EventsApp app = (EventsApp) getActivity().getApplication();
        DaggerBasicEventsUsecaseComponent.builder()
                .appComponent(app.getAppComponent())
                .basicEventsUsecaseModule(new BasicEventsUsecaseModule())
                .build().inject(this);
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
    public void showEvents(List<Event> eventList) {
        mEventsAdapter = new EventsAdapter(eventList);
        mEventsAdapter.setRecyclerlistListener(this);
        mRecycler.setAdapter(mEventsAdapter);
    }


    @Override
    public boolean isTheListEmpety() {
        return (mEventsAdapter==null || mEventsAdapter.getEventList().isEmpty());
    }

    @Override
    public void appendMovies(List<Event> eventList) {
        mEventsAdapter.appendEvent(eventList);
    }

    private void initializeRecycler() {

        //mRecycler.addItemDecoration(new RecyclerInsetsDecoration(getContext()));
        mRecycler.setOnScrollListener(recyclerScrollListener);
    }
    private void initializeFromParams(Bundle savedInstanceState) {

        EventsWrapper eventsWrapper = (EventsWrapper) savedInstanceState.getSerializable(BUNDLE_EVENTS_WRAPPER);

        mEventsPresenter.onPopularEventsRecivrd(eventsWrapper);

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

            if((visibleItemCount + pastVisibleItems) >= totalItemCount && ! mEventsPresenter.isLoading()) {
                mEventsPresenter.onEndListReached();
            }

            // Is scrolling up
            if (dy > 10) {

                if (!flag) {
                    //showToolbar();
                    flag = true;
                }

                // Is scrolling down
            } else if (dy < -10) {

                if (flag) {
                    //hideToolbar();
                    flag = false;
                }
            }

        }
    };

    @Override
    public void onClick(View v, int position, float x, float y) {
        Intent i = new Intent(context, null);
        startActivity(i);
    }

    @Override
    public Context getContext() {
        return context;
    }
}
