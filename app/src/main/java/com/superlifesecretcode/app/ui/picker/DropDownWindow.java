package com.superlifesecretcode.app.ui.picker;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.ui.adapter.PoupupWindowListAdapter;
import java.util.List;

/**
 * Created by Divya on 26-02-2018.
 */

public class DropDownWindow {

    public static void show(Context context, View parentView, List<String> list,SelectedListner listner) {
        // Inflate the popup_layout.xml
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.layout_popup_window, null);

        // Creating the PopupWindow
        PopupWindow popupWindow = new PopupWindow(context);
        popupWindow.setContentView(layout);
        popupWindow.setWidth(parentView.getWidth());
        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.
        // Clear the default translucent background
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Displaying the popup at the specified location, + offsets.
        RecyclerView recyclerView=layout.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new PoupupWindowListAdapter(list,listner,popupWindow));
        popupWindow.showAsDropDown(parentView);
        // Getting a reference to Close button, and close the popup when clicked.


    }

    public interface SelectedListner{
        void onSelected(String value,int position);
    }
}
