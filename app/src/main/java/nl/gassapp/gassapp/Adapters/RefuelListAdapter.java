package nl.gassapp.gassapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import nl.gassapp.gassapp.DataModels.Refuel;
import nl.gassapp.gassapp.R;
import nl.gassapp.gassapp.Views.RefuelDetailActivity;

public class RefuelListAdapter extends RecyclerView.Adapter<RefuelListAdapter.ViewHolder> {

    private ArrayList<Refuel> refuels;
    private Context context;


    public RefuelListAdapter(Context context, ArrayList<Refuel> refuels) {
        this.context = context;
        this.refuels = refuels;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Refuel refuel = refuels.get(position);

        holder.kilometers.setText(String.valueOf(refuel.getKilometers()));
        holder.price.setText(String.valueOf(refuel.getPrice()));
        holder.button.setOnClickListener((View view) -> {
            Intent refuelDetailIntent = new Intent(view.getContext(), RefuelDetailActivity.class);
            Bundle params = new Bundle();
            params.putInt("position", position);
            refuelDetailIntent.putExtras(params);
            context.startActivity(refuelDetailIntent);
        });

    }

    @Override
    public int getItemCount() {

        return refuels.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView kilometers;
        TextView price;
        LinearLayout listItemContainer;
        Button button;

        ViewHolder(View itemView) {
            super(itemView);
            kilometers = itemView.findViewById(R.id.kilometers);
            price = itemView.findViewById(R.id.price);
            listItemContainer = itemView.findViewById(R.id.list_item_container);
            button = itemView.findViewById(R.id.viewRefuel);

        }


    }

}
