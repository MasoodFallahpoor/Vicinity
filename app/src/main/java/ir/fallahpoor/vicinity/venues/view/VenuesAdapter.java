package ir.fallahpoor.vicinity.venues.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ir.fallahpoor.vicinity.R;
import ir.fallahpoor.vicinity.venues.model.VenueViewModel;

public class VenuesAdapter extends RecyclerView.Adapter<VenuesAdapter.VenueViewHolder> {

    private LayoutInflater layoutInflater;
    private List<VenueViewModel> places;

    VenuesAdapter(Context context, List<VenueViewModel> places) {
        this.places = places;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public VenueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recycler_item_venue, parent, false);
        return new VenueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VenueViewHolder holder, int position) {
        VenueViewModel place = places.get(position);
        holder.venueNameTextView.setText(place.getName());
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    class VenueViewHolder extends RecyclerView.ViewHolder {

        TextView venueNameTextView;

        VenueViewHolder(View itemView) {
            super(itemView);
            venueNameTextView = itemView.findViewById(R.id.venue_name_text_view);
        }

    }

}
