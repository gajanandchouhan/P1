package com.superlifesecretcode.app.ui.sharing_latest;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.shares.FileResponseData;
import com.superlifesecretcode.app.data.model.shares.ShareListResponseData;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;
import com.superlifesecretcode.app.util.ImageLoadUtils;
import com.superlifesecretcode.app.util.OnLoadMoreListener;

import java.util.List;

/**
 * Created by Divya on 26-02-2018.
 */

public class LatestAapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<ShareListResponseData> list;
    private final LanguageResponseData conversionData;
    private Context mContext;
    private int screenWidth;
    private int pagerHeight;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    // The minimum amount of items to have below your current scroll position
// before loading more.
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;


    public LatestAapter(List list, Context mContext, LanguageResponseData conversionData) {
        this.list = list;
        this.mContext = mContext;
        screenWidth = CommonUtils.getScreenWidth(mContext);
        pagerHeight = screenWidth * 7 / 16;
        this.conversionData = conversionData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_latest, parent, false);
            vh = new ItemViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progressbar, parent, false);
            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    public void setLoaded() {
        loading = false;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder parentHolder, int position) {
        if (parentHolder instanceof ItemViewHolder) {
            ItemViewHolder holder = (ItemViewHolder) parentHolder;
            ShareListResponseData shareListResponseData = list.get(position);
            holder.textViewName.setText(shareListResponseData.getUsername());
            holder.textViewDateTime.setText(CommonUtils.getformattedDateFromString(ConstantLib.INPUT_DATE_TIME_FORMATE, ConstantLib.OUTPUT_DATE_TIME_FORMATE, shareListResponseData.getCreated_at(), true, "utc"));
            holder.textViewDesc.setText(shareListResponseData.getContent());
            holder.textViewCountryName.setText(shareListResponseData.getCountryName());
            holder.imageViewLike.setSelected(shareListResponseData.getLiked_by_user().equalsIgnoreCase("1"));
            holder.textViewLike.setText(String.format("%s " + conversionData.getLikes(), shareListResponseData.getLiked_by()));
            ImageLoadUtils.loadImage(shareListResponseData.getUser_image(), holder.imageView);
            if (shareListResponseData.getSharing_files() != null && shareListResponseData.getSharing_files().size() > 0) {
                holder.pager.setVisibility(View.VISIBLE);
                holder.pager.setAdapter(new LatestPagerAdapter(mContext, shareListResponseData.getSharing_files()));
            } else {
                holder.pager.setVisibility(View.GONE);
            }
        } else {
            ((ProgressViewHolder) parentHolder).progressBar.setIndeterminate(true);
        }
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setUpLoadMore(RecyclerView recyclerView) {
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();

            recyclerView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            totalItemCount = linearLayoutManager.getItemCount();
                            lastVisibleItem = linearLayoutManager
                                    .findLastVisibleItemPosition();
                            if (!loading
                                    && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                                // End has been reached
                                // Do something
                                if (onLoadMoreListener != null) {
                                    onLoadMoreListener.onLoadMore();
                                }
                                loading = true;
                            }
                        }
                    });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewShare;
        ViewPager pager;
        TextView textViewName;
        TextView textViewDateTime;
        TextView textViewCountryName;
        TextView textViewDesc;
        ImageView imageView;
        ImageView imageViewLike;
        TextView textViewLike;

        public ItemViewHolder(View itemView) {
            super(itemView);
            pager = itemView.findViewById(R.id.pager);
            textViewName = itemView.findViewById(R.id.textView_name);
            textViewCountryName = itemView.findViewById(R.id.textView_country);
            textViewDateTime = itemView.findViewById(R.id.textView_date_time);
            textViewDesc = itemView.findViewById(R.id.textView_desc);
            textViewName = itemView.findViewById(R.id.textView_name);
            imageViewLike = itemView.findViewById(R.id.imageView_like);
            textViewLike = itemView.findViewById(R.id.textView_like_count);
            imageView = itemView.findViewById(R.id.imageView_user);
            textViewShare = itemView.findViewById(R.id.textView_share);
            pager.getLayoutParams().height = pagerHeight;
            pager.getLayoutParams().width = screenWidth;
            textViewShare.setOnClickListener(this);
            imageViewLike.setOnClickListener(this);
            itemView.findViewById(R.id.imageView_share).setOnClickListener(this);
            itemView.setOnClickListener(this);
            textViewShare.setText(conversionData.getShare());
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.imageView_share || v.getId() == R.id.textView_share) {
                ShareListResponseData data = list.get(getAdapterPosition());
                List<FileResponseData> sharing_files = data.getSharing_files();
                if (sharing_files != null && sharing_files.size() > 0) {
                    String type = sharing_files.get(pager.getCurrentItem()).getType();
                    if (type.equalsIgnoreCase(ConstantLib.TYPE_IMAGE)) {
                        if (data.getContent() != null && !data.getContent().isEmpty()) {
                            ((LatestActivity) mContext).shareImageAndText(sharing_files.get(pager.getCurrentItem()).getFile(), data.getContent());
                        } else {
                            ((LatestActivity) mContext).shareImage(sharing_files.get(pager.getCurrentItem()).getFile());
                        }

                    } else {
                        CommonUtils.shareContent(mContext, sharing_files.get(pager.getCurrentItem()).getFile());
                    }
                } else {
                    CommonUtils.shareContent(mContext, list.get(getAdapterPosition()).getContent());
                }
            } else if (v.getId() == R.id.imageView_like) {
                String liked_by_user = list.get(getAdapterPosition()).getLiked_by_user();
                ((LatestActivity) mContext).likeShare(getAdapterPosition(), liked_by_user != null && liked_by_user.equalsIgnoreCase("1") ? "0" : "1",
                        list.get(getAdapterPosition()).getSharing_id());
            } else {
                Bundle bundle = new Bundle();
                bundle.putSerializable("share", list.get(getAdapterPosition()));
                bundle.putBoolean("from_submit", false);
                ((LatestActivity) mContext).setClickedPosition(getAdapterPosition());
                CommonUtils.startActivity((AppCompatActivity) mContext, LatestDetailsActivity.class, bundle, false);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = v.findViewById(R.id.progressBar1);
        }
    }


}
