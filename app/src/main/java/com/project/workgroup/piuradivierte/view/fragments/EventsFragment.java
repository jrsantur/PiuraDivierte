package com.project.workgroup.piuradivierte.view.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.project.workgroup.piuradivierte.view.activities.MainActivity;
import com.project.workgroup.piuradivierte.view.adapter.EventsAdapter;

import java.util.List;

import javax.annotation.Nullable;


/**
 * Created by Junior on 03/11/2015.
 */
public class EventsFragment extends Fragment implements RecyclerViewClickListener , EventsView {


    public static final String ARG_SECTION_TITLE = "section_number";
    private final static String BUNDLE_EVENTS_WRAPPER = "events_wrapper";
    private LoadingView mLoadingView;
    private RecyclerView mRecycler;
    private EventsAdapter mEventsAdapter;
    Bundle saveInstance;

    Context context = getContext();
    Activity main = getActivity();

    public static EventsFragment newInstance(String sectionTitle) {
        EventsFragment fragment = new EventsFragment();
        Bundle args = new Bundle();
        args.getString(ARG_SECTION_TITLE, sectionTitle);
        fragment.setArguments(args);

        return fragment;
    }

    public EventsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            Log.e("onCreate", "no se adjunto vista");
            MainActivity.mEventsPresenter.attachView(this);
        }else{
            initializeFromParams(savedInstanceState);

            Log.e("initializeFromParams", "se inicio los parametros");
        }

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_events, container, false);
        mRecycler = (RecyclerView) rootView.findViewById(R.id.activity_event_recycler);
        mLoadingView = (LoadingView) rootView.findViewById(R.id.loadView);
        initializeRecycler();

        return rootView;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("onSaveInstanceState", " estas en onSaveInstanceState");
        if (mEventsAdapter != null) {
            Log.e("onSaveInstanceState", " estas en onSaveInstanceState");
            outState.putSerializable("",null);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        MainActivity.mEventsPresenter.start();
        Log.e("onStart", "se inicio el preseter, mEventsPresenter.start() ");
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
        Log.e("showEvents", "estas en showEvents");
        mEventsAdapter = new EventsAdapter(eventList);
        mEventsAdapter.setRecyclerlistListener(this);
        mRecycler.setAdapter(mEventsAdapter);
    }


    @Override
    public boolean isTheListEmpety() {
        return (mEventsAdapter == null || mEventsAdapter.getEventList().isEmpty());
    }

    @Override
    public void appendMovies(List<Event> eventList) {
        mEventsAdapter.appendEvent(eventList);
    }

    private void initializeRecycler() {

        //mRecycler.addItemDecoration(new RecyclerInsetsDecoration(getContext()));
        mRecycler.setOnScrollListener(recyclerScrollListener);
    }


    private RecyclerView.OnScrollListener recyclerScrollListener = new RecyclerView.OnScrollListener() {
        public boolean flag;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = mRecycler.getLayoutManager().getChildCount();
            int totalItemCount = mRecycler.getLayoutManager().getItemCount();
            int pastVisibleItems = ((GridLayoutManager) mRecycler.getLayoutManager())
                    .findFirstVisibleItemPosition();

            if ((visibleItemCount + pastVisibleItems) >= totalItemCount && !MainActivity.mEventsPresenter.isLoading()) {
                MainActivity.mEventsPresenter.onEndListReached();
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

    private void initializeFromParams(Bundle savedInstanceState) {
        EventsWrapper eventsWrapper = (EventsWrapper) savedInstanceState.getSerializable(BUNDLE_EVENTS_WRAPPER);

        if(mEventsAdapter==null){
            Log.e("mEventsAdapter","mEventsAdapter es nulo");
        }
        if(mEventsAdapter.getEventList()==null){
            Log.e("mEventsAdapter","mEventsAdapter es nulo");
        }
        else {
            Log.e("mEventsAdapter","mEventsAdapter no es nulo " +mEventsAdapter.getEventList().get(0).getTitle());
        }
        MainActivity.mEventsPresenter.onPopularEventsRecivrd(eventsWrapper);
        Log.e("initializeFromParams", "Se inicio nitializeFromParams");
    }


}
