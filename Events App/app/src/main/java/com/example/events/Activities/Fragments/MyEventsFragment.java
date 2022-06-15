package com.example.events.Activities.Fragments;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.events.DataStructures.Event;
import com.example.events.DataStructures.Users.AuthUser;
import com.example.events.DataStructures.Users.User;
import com.example.events.Persistence.DownloadImageTask;
import com.example.events.Persistence.ServiceAPI;
import com.example.events.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyEventsFragment extends Fragment {
    private TextView dropEvent;
    private ImageView image;
    private TextView name;
    private TextView category;
    private TextView startDate;
    private TextView location;
    private Event event;
    private RecyclerView myEventRecView;
    private List<Event> myEventList;
    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_events_fragment, container, false);
        myEventRecView = (RecyclerView) view.findViewById(R.id.my_events_rec_view);
        myEventRecView.setLayoutManager(new LinearLayoutManager(getContext()));
        getAPI();

        /* image = view.findViewById(R.id.imageView);
        name = (TextView) view.findViewById(R.id.name_attend_event);
        category = (TextView) view.findViewById(R.id.category_attend_event);
        startDate = (TextView) view.findViewById(R.id.start_attend_event);
        location = (TextView) view.findViewById(R.id.location_attend_event);

        new DownloadImageTask(image).execute(event.getImageURL());
        name.setText(event.getName());
        category.setText(event.getEventType());
        startDate.setText(event.getStartDate());
        location.setText(event.getLocation()); */

        return view;
    }

    private void getAPI() {
        ServiceAPI.getInstance().getEvents(AuthUser.getAuthUser().getId(), AuthUser.getAuthUser().getToken().getToken()).enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.isSuccessful()) {
                    myEventList = new ArrayList<>();
                    myEventList = (ArrayList<Event>) response.body();
                    /* for (Event e : response.body()) {
                        if (e.getOwnerID() == user.getId())
                            myEventList.add(e);
                    } */
                    updateUI();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Toast.makeText(getContext(), R.string.bad_connection, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI() {
        MyEventAdapter myEventAdapter = new MyEventAdapter(myEventList);
        myEventRecView.setAdapter(myEventAdapter);
    }

    private class MyEventAdapter extends RecyclerView.Adapter<MyEventsFragment.MyEventHolder> {
        private final List<Event> myEventList;

        private MyEventAdapter(List<Event> myEventList) {
            this.myEventList = myEventList;
        }

        @Override
        public MyEventsFragment.MyEventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            return new MyEventsFragment.MyEventHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(MyEventsFragment.MyEventHolder holder, int position) {
            Event event = myEventList.get(position);
            holder.bind(event);
        }

        @Override
        public int getItemCount() {
            return myEventList.size();
        }
    }

    private class MyEventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView myEventName;
        private final TextView myEventCategory;
        private final TextView startDate;
        private final TextView location;

        public MyEventHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.my_event_item, parent, false));
            itemView.setOnClickListener(this);
            ImageView picture = (ImageView) itemView.findViewById(R.id.imageView1);
            myEventName = (TextView) itemView.findViewById(R.id.my_event_name);
            myEventCategory = (TextView) itemView.findViewById(R.id.my_event_category);
            startDate = (TextView) itemView.findViewById(R.id.my_event_start);
            location = (TextView) itemView.findViewById(R.id.my_event_location);

        }

        public void bind(Event event) {
            new DownloadImageTask((ImageView) itemView.findViewById(R.id.imageView1)).execute(event.getImageURL());
            this.myEventName.setText(event.getName());
            this.myEventCategory.setText(event.getEventType());
            this.startDate.setText(event.getStartDate());
            this.location.setText(event.getLocation());
        }

        @Override
        public void onClick(View view) {

        }

    }

    /*private void initFields() {
        dropEvent = (TextView) view.findViewById(R.id.dropEvent);
    }

    private void setButtonListener() {
        // MOVE THE POP UP TO TIMELINE WHEN FINISHED
        dropEvent.setOnClickListener(view -> {
            Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.drop_event_pop_up);

            Button no = dialog.findViewById(R.id.no);
            Button yes = dialog.findViewById(R.id.yes);

            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View view){
                    dialog.dismiss();
                }
            });

            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // DELETE INFORMATION OF THE EVENT SELECTED IN THE TIMELINE VIEW
                    // ServiceAPI.getInstance().updateEvent();
                }
            });

            dialog.show();
        });
    }*/
}