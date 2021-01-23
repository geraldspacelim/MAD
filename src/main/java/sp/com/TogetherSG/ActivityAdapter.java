package sp.com.TogetherSG;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ActivityAdapter extends FirestoreRecyclerAdapter<model, ActivityAdapter.ActivitiesViewHolder> {
    public ActivityAdapter(@NonNull FirestoreRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ActivitiesViewHolder holder, int position, @NonNull model model) {
        holder.list_name.setText(model.getName());
        holder.list_date.setText(model.getDate());
        holder.list_time.setText(model.getTime());
        holder.list_venue.setText(model.getVenue());
        holder.list_cost.setText(model.getCost());
    }

    @NonNull
    @Override
    public ActivitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.act_list_item,parent,false);
        return new ActivitiesViewHolder(view);
    }

    public class ActivitiesViewHolder extends RecyclerView.ViewHolder{

        private TextView list_name, list_date, list_time, list_venue, list_cost;

        public ActivitiesViewHolder(@NonNull View itemView) {
            super(itemView);
            list_name = itemView.findViewById(R.id.list_name);
            list_date = itemView.findViewById(R.id.list_date);
            list_time = itemView.findViewById(R.id.list_time);
            list_venue = itemView.findViewById(R.id.list_venue);
            list_cost= itemView.findViewById(R.id.list_cost);
        }
    }
}

