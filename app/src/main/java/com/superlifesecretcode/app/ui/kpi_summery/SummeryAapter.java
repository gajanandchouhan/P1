package com.superlifesecretcode.app.ui.kpi_summery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.util.ImageLoadUtils;
import java.util.List;
/**
 * Created by Divya on 26-02-2018.
 */

public class SummeryAapter extends RecyclerView.Adapter<SummeryAapter.ItemViewHolder> {
    private final List<StudySharingBean> list;
    private Context mContext;
    LanguageResponseData languageResponseData;

    public SummeryAapter(List<StudySharingBean> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_summery, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        languageResponseData = SuperLifeSecretPreferences.getInstance().getConversionData();
        holder.tv_event.setText(list.get(position).getTitle());
        holder.tv_unattented_header.setText(""+languageResponseData.getJoin_at()+" "+list.get(position).getActivity_time().substring(0,5)+" "+languageResponseData.getIn()+" "+list.get(position).getCityName());
        ImageLoadUtils.loadImage(list.get(position).getImage(), holder.image_event);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            Spanned spanned = Html.fromHtml(list.get(position).getDescription(), Html.FROM_HTML_MODE_LEGACY);
//            holder.textview_book_descrption.setText(spanned);
//        } else {
//            Spanned spanned = Html.fromHtml(list.get(position).getDescription());
//            holder.textview_book_descrption.setText(spanned);
//        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image_event;
        TextView tv_event;
        TextView tv_unattented_header;

        public ItemViewHolder(View itemView) {
            super(itemView);
            image_event = itemView.findViewById(R.id.image_event);
            tv_event = itemView.findViewById(R.id.tv_event);
            tv_unattented_header = itemView.findViewById(R.id.tv_unattented_header);
        }

        @Override
        public void onClick(View v) {
        }
    }
}
