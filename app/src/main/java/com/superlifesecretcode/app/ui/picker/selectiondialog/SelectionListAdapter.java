package com.superlifesecretcode.app.ui.picker.selectiondialog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.standardevent.StandardEventResponseData;

import java.util.List;

/**
 * Created by Divya on 21-11-2017.
 */

public class SelectionListAdapter extends RecyclerView.Adapter<SelectionListAdapter.ViewHolder> {
    private final List<Object> list;
    private final Context mContext;
    private final SelectionListDialog selectionListDialog;
    private final SelectionListDialog.SelectedListner selectedListner;

    public SelectionListAdapter(SelectionListDialog selectionListDialog, Context mContext, List<Object> list, SelectionListDialog.SelectedListner selectedListner) {
        this.list = list;
        this.mContext = mContext;
        this.selectionListDialog = selectionListDialog;
        this.selectedListner = selectedListner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_selection_popup, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Object object = list.get(position);
       if (object instanceof StandardEventResponseData) {
            StandardEventResponseData standardEventResponseData = (StandardEventResponseData) object;
            holder.textViewTitle.setText(standardEventResponseData.getTitle());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionListDialog.dismiss();
                selectedListner.onSelected(position, object);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;


        public ViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textView_title);
        }
    }


}
