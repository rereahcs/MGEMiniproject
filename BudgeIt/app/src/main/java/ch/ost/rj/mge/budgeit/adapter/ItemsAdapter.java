package ch.ost.rj.mge.budgeit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import ch.ost.rj.mge.budgeit.R;
import ch.ost.rj.mge.budgeit.model.Item;

public class ItemsAdapter extends RecyclerView.Adapter<ItemViewHolder> {
    private List<Item> items;
    private OnItemClickListenerHome onItemClickListenerHome;

    public ItemsAdapter(List<Item> items, OnItemClickListenerHome onItemClickListenerHome) {
        this.items=items;
        this.onItemClickListenerHome = onItemClickListenerHome;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int vt) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(
                R.layout.layout_listitem,
                parent,
                false
        );

        return new ItemViewHolder(
                view,
                onItemClickListenerHome
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = this.items.get(position);
        holder.category.setText(item.getCategory());
        holder.name.setText(item.getDescription());
        holder.amount.setText(String.valueOf(item.getAmount()));
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
}
