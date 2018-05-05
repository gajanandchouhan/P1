package com.superlifesecretcode.app.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.ui.picker.DropDownWindow;

import java.util.List;

/**
 * Created by Divya on 26-02-2018.
 */

public class PoupupWindowListAdapter extends RecyclerView.Adapter<PoupupWindowListAdapter.ItemViewHolder> {
    private final List<String> list;
    private final DropDownWindow.SelectedListner lislner;
    private final PopupWindow window;

    public PoupupWindowListAdapter(List<String> list, DropDownWindow.SelectedListner listner, PopupWindow popupWindow) {
        this.list = list;
        this.lislner = listner;
        this.window = popupWindow;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_popup, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        String s = list.get(position);
        holder.textView.setText(s);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView textView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (window != null) {
                window.dismiss();
            }
            lislner.onSelected(list.get(getAdapterPosition()),getAdapterPosition());
        }
    }
}
