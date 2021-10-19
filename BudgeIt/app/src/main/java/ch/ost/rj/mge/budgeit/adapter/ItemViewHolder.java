package ch.ost.rj.mge.budgeit.adapter;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView itemNameView;
    public TextView itemAmountView;
    //public TextView itemCategoryView;

    OnItemClickListenerHome onItemClickListenerHome;

    public ItemViewHolder(
            View parent,
            TextView itemNameView,
            TextView itemAmountView,
            OnItemClickListenerHome onItemClickListenerHome
            ) {
        super(parent);

        this.itemNameView=itemNameView;
        this.itemAmountView=itemAmountView;

        this.onItemClickListenerHome = onItemClickListenerHome;

        itemNameView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        onItemClickListenerHome.onItemClick(getAdapterPosition());
    }
}
