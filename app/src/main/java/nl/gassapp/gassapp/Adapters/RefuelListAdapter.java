package nl.gassapp.gassapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import nl.gassapp.gassapp.DataModels.Refuel;
import nl.gassapp.gassapp.R;

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

        holder.kilometers.setText(String.valueOf(refuels.get(position).getKilometers()));
        holder.price.setText(String.valueOf(refuels.get(position).getPrice()));

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                System.out.println(position);
            }

        });

    }

    @Override
    public int getItemCount() {

        return refuels.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView kilometers;
        TextView price;
        RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            kilometers = itemView.findViewById(R.id.kilometers);
            price = itemView.findViewById(R.id.price);
            relativeLayout = itemView.findViewById(R.id.list_item_container);

        }


    }

}
