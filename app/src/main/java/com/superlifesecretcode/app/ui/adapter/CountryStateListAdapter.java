package com.superlifesecretcode.app.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.dining.countrypicker.Country;
import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.country.CountryResponseData;
import com.superlifesecretcode.app.data.model.country.CountryResponseModel;
import com.superlifesecretcode.app.ui.picker.CountryPicker;
import com.superlifesecretcode.app.ui.picker.CountryStatePicker;
import com.superlifesecretcode.app.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Divya on 21-11-2017.
 */

public class CountryStateListAdapter extends RecyclerView.Adapter<CountryStateListAdapter.ViewHolder> implements Filterable {

    private List<CountryResponseData> allCountries;
    private final CountryStatePicker.PickerListner pickerListner;
    private final Context mContext;
    private List<CountryResponseData> filterList;
    private MainFilter mainFilter;

    public CountryStateListAdapter(Context mContext, CountryStatePicker.PickerListner pickerListner, List<CountryResponseData> allCountries) {
        this.pickerListner = pickerListner;
        filterList = new ArrayList<>();
        this.allCountries=allCountries;
        filterList.addAll(allCountries);
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_country_state_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.textViewName.setText(filterList.get(position).getName());
      //  holder.imageViewFlag.setImageResource(filterList.get(position).getFlag());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.hideKeyboard(mContext);
                pickerListner.onPick(filterList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    @Override
    public Filter getFilter() {
        if (mainFilter == null)
            mainFilter = new MainFilter();
        return mainFilter;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        ImageView imageViewFlag;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textView_country_name);
            imageViewFlag = itemView.findViewById(R.id.imageView_flag);
        }

    }

    public class MainFilter extends Filter {

        @Override
        public FilterResults performFiltering(CharSequence constraint) {


            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<CountryResponseData> list = allCountries;

            int count = list.size();
            final ArrayList<CountryResponseData> nlist = new ArrayList<>(count);

            CountryResponseData countryModel;

            for (int i = 0; i < count; i++) {
                countryModel = list.get(i);
                if (countryModel != null && countryModel.getName().toLowerCase().contains(filterString)) {
                    nlist.add(countryModel);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filterList = (ArrayList<CountryResponseData>) results.values;
            notifyDataSetChanged();
        }

    }
}
