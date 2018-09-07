package com.superlifesecretcode.app.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.AlertModel;
import com.superlifesecretcode.app.data.model.category.CategoryResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.book.first.BookBean;
import com.superlifesecretcode.app.ui.book.first.FirstBookActivity;
import com.superlifesecretcode.app.ui.book.myorder_book.OrderBookActivity;
import com.superlifesecretcode.app.ui.countryactivities.CountryAcitvitiesActivity;
import com.superlifesecretcode.app.ui.dailyactivities.interestedevent.InterestedEventCalendarActivity;
import com.superlifesecretcode.app.ui.dailyactivities.personalevent.PersonalEventCalendarActivity;
import com.superlifesecretcode.app.ui.events.EventActivity;
import com.superlifesecretcode.app.ui.main.MainActivity;
import com.superlifesecretcode.app.ui.myannouncement.MyAnnouncementActivity;
import com.superlifesecretcode.app.ui.mycountryactivities.MyCountryActivity;
import com.superlifesecretcode.app.ui.news.NewsActivity;
import com.superlifesecretcode.app.ui.picker.AlertDialog;
import com.superlifesecretcode.app.ui.sharing_latest.LatestActivity;
import com.superlifesecretcode.app.ui.sharing_submit.SubmitListActivity;
import com.superlifesecretcode.app.ui.subcategory.SubCategoryActivity;
import com.superlifesecretcode.app.ui.webview.WebViewActivity;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;
import com.superlifesecretcode.app.util.ImageLoadUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Divya on 26-02-2018.
 */

public class SubListAdapter extends RecyclerView.Adapter<SubListAdapter.ItemViewHolder> {
    private final List<CategoryResponseData> list;
    private final String colorCode;
    private final SuperLifeSecretPreferences preferences;
    private Context mContext;

    public SubListAdapter(List<CategoryResponseData> list, Context mContext, String colorCode) {
        this.list = list;
        this.mContext = mContext;
        this.colorCode = colorCode;
        preferences = SuperLifeSecretPreferences.getInstance();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_subcategory, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.textView.setText(list.get(position).getTitle());
        holder.textViewChar.setText(list.get(position).getTitle().substring(0, 1));
        if (list.get(position).getImage() != null && !list.get(position).getImage().isEmpty()) {
            holder.imageView.setVisibility(View.VISIBLE);
            ImageLoadUtils.loadImage(list.get(position).getImage(), holder.imageView);
        } else {
            holder.imageView.setVisibility(View.GONE);
        }

        if (list.get(position).getType().equalsIgnoreCase(ConstantLib.TYPE_NEWS) && preferences.getNewsUnread() > 0) {
            holder.textViewCount.setVisibility(View.VISIBLE);
            holder.textViewCount.setText(String.valueOf(preferences.getNewsUnread()));
        } else if (list.get(position).getType().equalsIgnoreCase(ConstantLib.TYPE_EVENT) && preferences.getEventUnread() > 0) {
            holder.textViewCount.setVisibility(View.VISIBLE);
            holder.textViewCount.setText(String.valueOf(preferences.getEventUnread()));
        } else {
            holder.textViewCount.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView textViewCount;
        ImageView imageView;
        TextView textView;
        TextView textViewChar;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView_item);
            textViewChar = itemView.findViewById(R.id.textView_char);
            textViewCount = itemView.findViewById(R.id.textView_count);
            GradientDrawable gradientDrawable = (GradientDrawable) textViewChar.getBackground();
            gradientDrawable.setColor(Color.parseColor(colorCode));
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPostion = getAdapterPosition();
            if (list.get(clickedPostion).getAlert() != null && list.get(clickedPostion).getAlert().equalsIgnoreCase("1")) {
                AlertModel alertModel = new AlertModel();
                alertModel.setCount(Integer.parseInt(list.get(clickedPostion).getAlert_count()));
                alertModel.setId(list.get(clickedPostion).getId());
                if (!SuperLifeSecretPreferences.getInstance().getAcceptedIds().contains(alertModel)) {
                    showAlert(list.get(clickedPostion).getAlert_text(), clickedPostion, null, null);
                } else {
                    List<AlertModel> alertModelList = SuperLifeSecretPreferences.getInstance().getAcceptedIds();
                    int index = alertModelList.indexOf(alertModel);
                    AlertModel alertModel1 = alertModelList.get(index);
                    if (alertModel.getCount() > alertModel1.getShowCount()) {
                        showAlert(list.get(clickedPostion).getAlert_text(), clickedPostion, alertModel1, alertModelList);
                    } else {
                        openNext(clickedPostion);
                    }
              /*  AlertModel alertModel = new AlertModel();
                alertModel.setCount(Integer.parseInt(list.get(clickedPostion).getAlert_count()));
                alertModel.setId(list.get(clickedPostion).getId());
                if (!SuperLifeSecretPreferences.getInstance().getAcceptedIds().contains(alertModel)) {
                    showAlert(list.get(clickedPostion).getAlert_text(), clickedPostion, null, null);
                } else {
                    List<AlertModel> alertModelList = SuperLifeSecretPreferences.getInstance().getAcceptedIds();
                    int index = alertModelList.indexOf(alertModel);
                    AlertModel alertModel1 = alertModelList.get(index);
                    if (alertModel1.getCount() == 0) {
                        openNext(clickedPostion);
                    } else {
                        showAlert(list.get(clickedPostion).getAlert_text(), clickedPostion, alertModel1, alertModelList);
                    }
*/
                }
            } else {
                openNext(clickedPostion);
            }
        }
    }

    private void showAlert(final String alert_text, final int clikedPostion, final AlertModel alertModel1, final List<AlertModel> alertModelList) {
        String positive_resp = list.get(clikedPostion).getPositive_resp();
        String negative_resp = list.get(clikedPostion).getNegative_resp();
        CommonUtils.showAlert(mContext, alert_text, positive_resp, negative_resp, new AlertDialog.OnClickListner() {
            @Override
            public void onPositiveClick() {
                if (alertModel1 != null) {
                    alertModel1.setShowCount(alertModel1.getShowCount() + 1);
                    SuperLifeSecretPreferences.getInstance().updateAlertList(alertModelList);
                } else {
                    AlertModel alertModel = new AlertModel();
                    alertModel.setShowCount(1);
                    alertModel.setId(list.get(clikedPostion).getId());
                    alertModel.setCount(Integer.parseInt(list.get(clikedPostion).getAlert_count()) - 1);
                    SuperLifeSecretPreferences.getInstance().setAlertAccepted(alertModel);
                }
//                openNext(clikedPostion);
            }

            @Override
            public void onNegativeClick() {

            }
        });
    }

    private void openNext(int position) {
        if (list.get(position).getType().equalsIgnoreCase("1") || list.get(position).getType().equalsIgnoreCase("2")) {
            Bundle bundle = new Bundle();
            bundle.putString("title", list.get(position).getTitle());
            bundle.putBoolean("is_link", list.get(position).getType().equalsIgnoreCase("1"));
            bundle.putString("url", list.get(position).getLink());
            bundle.putString("content", list.get(position).getContent());
            CommonUtils.startActivity((AppCompatActivity) mContext, WebViewActivity.class, bundle, false);
        } else {
            switch (list.get(position).getType()) {
                case ConstantLib.TYPE_NEWS:
                    CommonUtils.startActivity((AppCompatActivity) mContext, NewsActivity.class);
                    break;

                case ConstantLib.TYPE_EVENT:
                    CommonUtils.startActivity((AppCompatActivity) mContext, EventActivity.class);
                    break;

                case ConstantLib.TYPE_LATEST:
                    CommonUtils.startActivity((AppCompatActivity) mContext, LatestActivity.class);
                    break;

                case ConstantLib.TYPE_SUBMIT:
                    CommonUtils.startActivity((AppCompatActivity) mContext, SubmitListActivity.class);
                    break;
                case ConstantLib.TYPE_PERSONAL_CALENDAR:
                    CommonUtils.startActivity((AppCompatActivity) mContext, PersonalEventCalendarActivity.class);
                    break;

                case ConstantLib.TYPE_EVENT_CALENDAR:
                    CommonUtils.startActivity((AppCompatActivity) mContext, InterestedEventCalendarActivity.class);
                    break;

                case ConstantLib.TYPE_STUDY_GROUP:
                    Bundle bundle = new Bundle();
                    bundle.putString("title", SuperLifeSecretPreferences.getInstance().getConversionData().getStudy_group());
                    bundle.putBoolean("isStudyGroup", true);
                    CommonUtils.startActivity((AppCompatActivity) mContext, CountryAcitvitiesActivity.class, bundle, false);
                    break;

                case ConstantLib.TYPE_ONSITE:
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("title", SuperLifeSecretPreferences.getInstance().getConversionData().getOnsite());
                    bundle2.putBoolean("isStudyGroup", false);
                    CommonUtils.startActivity((AppCompatActivity) mContext, CountryAcitvitiesActivity.class, bundle2, false);
                    break;

                case ConstantLib.TYPE_PRINT_BOOK:
                    Bundle bundle3 = new Bundle();
                    bundle3.putString("type", "1");
                    SuperLifeSecretPreferences.getInstance().putString("book_type", "1");
                    SuperLifeSecretPreferences.getInstance().putString("book_print_status", "1");
                    SuperLifeSecretPreferences.getInstance().putString("book_stake_page_no", "0");
                    SuperLifeSecretPreferences.getInstance().setSelectedBooks(new ArrayList<String>());
                    SuperLifeSecretPreferences.getInstance().setSelectedBooksList(new ArrayList<BookBean>());
                    SuperLifeSecretPreferences.getInstance().putString("total_amount","");
                    SuperLifeSecretPreferences.getInstance().putString("book_address","");
                    SuperLifeSecretPreferences.getInstance().putString("book_full_name","");
                    SuperLifeSecretPreferences.getInstance().putString("book_mobile","");
                    SuperLifeSecretPreferences.getInstance().putString("book_email","");
                    SuperLifeSecretPreferences.getInstance().putString("book_state","");
                    SuperLifeSecretPreferences.getInstance().putString("book_city","");
                    CommonUtils.startActivity((AppCompatActivity) mContext, FirstBookActivity.class, bundle3, false);
                    break;

                case ConstantLib.TYPE_BUY_BOOK:
                    Bundle bundle4 = new Bundle();
                    bundle4.putString("type", "2");
                    SuperLifeSecretPreferences.getInstance().putString("book_stake_page_no", "0");
                    SuperLifeSecretPreferences.getInstance().putString("book_type", "2");
                    SuperLifeSecretPreferences.getInstance().putString("book_print_status", "2");
                    SuperLifeSecretPreferences.getInstance().setSelectedBooks(new ArrayList<String>());
                    SuperLifeSecretPreferences.getInstance().setSelectedBooksList(new ArrayList<BookBean>());
                    SuperLifeSecretPreferences.getInstance().putString("total_amount","");
                    SuperLifeSecretPreferences.getInstance().putString("book_address","");
                    SuperLifeSecretPreferences.getInstance().putString("book_full_name","");
                    SuperLifeSecretPreferences.getInstance().putString("book_mobile","");
                    SuperLifeSecretPreferences.getInstance().putString("book_email","");
                    SuperLifeSecretPreferences.getInstance().putString("book_state","");
                    SuperLifeSecretPreferences.getInstance().putString("book_city","");
                    CommonUtils.startActivity((AppCompatActivity) mContext, FirstBookActivity.class, bundle4, false);
                    break;
                case ConstantLib.TYPE_MY_ANNOUNCEMENT:
                    CommonUtils.startActivity((AppCompatActivity) mContext, MyAnnouncementActivity.class);
                    break;
                case ConstantLib.TYPE_MY_COUNTRY_ACTIVITIES:
                    CommonUtils.startActivity((AppCompatActivity) mContext, MyCountryActivity.class);
                    break;

                case ConstantLib.TYPE_BOOK_ORDER_HISTORY:

                    Bundle bundle5 = new Bundle();
                    bundle5.putString("title","My Order");
                    SuperLifeSecretPreferences.getInstance().setSelectedBooks(new ArrayList<String>());
                    CommonUtils.startActivity((AppCompatActivity) mContext, OrderBookActivity.class, bundle5, false);
                    break;
            }
        }
    }
}
