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
import com.superlifesecretcode.app.ui.picker.CountryPicker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Divya on 21-11-2017.
 */

public class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.ViewHolder> implements Filterable {

    private final List<Country> allCountries;
    private final CountryPicker.PickerListner pickerListner;
    private final Context mContext;
    private List<Country> filterList;
    private MainFilter mainFilter;

    public CountryListAdapter(Context mContext, CountryPicker.PickerListner pickerListner) {
        allCountries = Country.getAllCountries();
        this.pickerListner = pickerListner;
        filterList = new ArrayList<>();
        filterList.addAll(allCountries);
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_country_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.textViewName.setText(filterList.get(position).getName());
        holder.imageViewFlag.setImageResource(filterList.get(position).getFlag());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

            final List<Country> list = allCountries;

            int count = list.size();
            final ArrayList<Country> nlist = new ArrayList<>(count);

            Country countryModel;

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
            filterList = (ArrayList<Country>) results.values;
            notifyDataSetChanged();
        }

    }
}
