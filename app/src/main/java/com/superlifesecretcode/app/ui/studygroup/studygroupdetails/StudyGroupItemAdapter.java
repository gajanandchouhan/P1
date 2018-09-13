package com.superlifesecretcode.app.ui.studygroup.studygroupdetails;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.studygroups.studygroupitem.StudyGroupItemData;
import com.superlifesecretcode.app.player.activity.AudioPlayerActivity;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;

import java.util.List;

public class StudyGroupItemAdapter extends RecyclerView.Adapter<StudyGroupItemAdapter.ItemViewHolder> {

    private Context mContext;
    private List<StudyGroupItemData> list;

    public StudyGroupItemAdapter(Context mContext, List list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_study_group_item, parent, false);
        return new ItemViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        StudyGroupItemData studyGroupItemData = list.get(position);
        switch (ConstantLib.GroupItemType.valueOf(studyGroupItemData.getItem_type())) {
            case TYPE_AUDIO:
                break;
            case TYPE_VIDEO:
                break;
            case TYPE_WEB_LINK:
                break;
            case TYPE_TEXT:
                break;
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            /*Bundle bundle = new Bundle();
            bundle.putString("name", "Test");
            bundle.putString("url", "https://archive.org/download/count_monte_cristo_0711_librivox/count_of_monte_cristo_001_dumas.mp3");
            bundle.putString("image", "https://ia902708.us.archive.org/3/items/count_monte_cristo_0711_librivox/Count_Monte_Cristo_1110.jpg?cnt=0");
            CommonUtils.startActivity((AppCompatActivity) mContext, AudioPlayerActivity.class, bundle, false);*/
        }
    }
}
