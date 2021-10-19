package ch.ost.rj.mge.budgeit.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import ch.ost.rj.mge.budgeit.R;

public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ConstraintLayout parentView;
    TextView category;
    TextView name;
    TextView amount;

    OnItemClickListenerHome onItemClickListenerHome;

    public ItemViewHolder(
            View itemView,
            OnItemClickListenerHome onItemClickListenerHome
            ) {
        super(itemView);

        this.onItemClickListenerHome = onItemClickListenerHome;

        parentView = itemView.findViewById(R.id.listitem_layout_parent);
        category = itemView.findViewById(R.id.listitem_textView_category);
        name = itemView.findViewById(R.id.listitem_textView_name);
        amount = itemView.findViewById(R.id.listitem_textView_amount);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        onItemClickListenerHome.onItemClick(getAdapterPosition());
    }
}
