package sp.com.TogetherSG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;


public class ActivitiesFragment extends Fragment {

    private FirebaseFirestore firebaseFirestore;
    private View v;
    private RecyclerView ActivitiesList;
    //private FirestoreRecyclerAdapter adapter;
    ActivityAdapter adapter;
    DatabaseReference databaseReference;
    ArrayList<model> models;
    FirebaseDatabase FirebaseDatabase;

    public ActivitiesFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    /*public static ActivitiesFragment newInstance(String param1, String param2) {
        ActivitiesFragment fragment = new ActivitiesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    } */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_activities, container, false);
        ActivitiesList = v.findViewById(R.id.activity_list);
        //setUpRecyclerView();

        databaseReference = FirebaseDatabase.getInstance().getReference("Activities");
        fetchdata();


        /*Query
        Query query = firebaseFirestore.collection("Activities");
        //RecyclerOptions
        FirestoreRecyclerOptions<model> options = new FirestoreRecyclerOptions.Builder<model>()
                .setQuery(query, model.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<model, ActivitiesViewHolder>(options) {
            @NonNull
            @Override
            public ActivitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.act_list_item,parent,false);
                return new ActivitiesViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ActivitiesViewHolder holder, int position, @NonNull model model) {
                holder.list_name.setText(model.getName());
                holder.list_date.setText(model.getDate());
                holder.list_time.setText(model.getTime());
                holder.list_venue.setText(model.getVenue());
                holder.list_cost.setText(model.getCost());
            }
        };
        //View Holder
        ActivitiesList.setAdapter(adapter); */

        return v;
    }

    private void fetchdata() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                models = new ArrayList<model>();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    model m = dataSnapshot.getValue(model.class);
                    models.add(m);
                }
                Log.e("i", String.valueOf(models.size()));
                adapter = new ActivityAdapter(ActivitiesFragment.this.getActivity(), models);
                ActivitiesList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /*private class ActivitiesViewHolder extends RecyclerView.ViewHolder{

        private TextView list_name, list_date, list_time, list_venue, list_cost;

        public ActivitiesViewHolder(@NonNull View itemView) {
            super(itemView);
            list_name = itemView.findViewById(R.id.list_name);
            list_date = itemView.findViewById(R.id.list_date);
            list_time = itemView.findViewById(R.id.list_time);
            list_venue = itemView.findViewById(R.id.list_venue);
            list_cost= itemView.findViewById(R.id.list_cost);
        }
    } */

   /* public void setUpRecyclerView() {
        Query query = FirebaseFirestore.getInstance().collection("Activities");
        FirestoreRecyclerOptions<model> options = new FirestoreRecyclerOptions.Builder<model>().setQuery(query, model.class).build();

        adapter = new ActivityAdapter(options);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        ActivitiesList.setLayoutManager(linearLayoutManager);
        ActivitiesList.setAdapter(adapter);
    } */

    /* @Override
    public void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    } */
}