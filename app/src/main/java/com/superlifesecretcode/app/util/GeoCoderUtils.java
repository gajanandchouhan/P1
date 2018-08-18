package com.superlifesecretcode.app.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Divya on 23-02-2018.
 */

public class GeoCoderUtils {
    public static void getCountryCode(final double lat, final double lng, final Context mContext, final GeocoderListner listner) {
        final ProgressDialog show = ProgressDialog.show(mContext, "", "");
        new Thread(new Runnable() {
            @Override
            public void run() {

                    Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
                    if (addresses!=null&&addresses.size()>0){
                        final Address obj = addresses.get(0);
                        String add = obj.getAddressLine(0);
                        add = add + "\n" + obj.getCountryName();
                        add = add + "\n" + obj.getCountryCode();
                        add = add + "\n" + obj.getAdminArea();
                        add = add + "\n" + obj.getPostalCode();
                        add = add + "\n" + obj.getSubAdminArea();
                        add = add + "\n" + obj.getLocality();
                        add = add + "\n" + obj.getSubThoroughfare();
                        Log.v("IGA", "Address" + add);

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run(){
                                listner.onGetCode(obj.getCountryName(),obj.getCountryCode(),obj.getAdminArea(),obj.getLocality());
                                show.dismiss();
                            }
                        });
                    }
                    else{
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run(){
                                listner.onGetCode(null,null,null,null);
                                //manage your edittext and Other UIs here
                                show.dismiss();
                            }
                        });
                    }

                } catch (IOException e) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run(){
                            listner.onGetCode(null,null,null,null);
                            //manage your edittext and Other UIs here
                            show.dismiss();
                        }
                    });
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public interface GeocoderListner {
        void onGetCode(String countryName,String countryCode,String stateName,String cityName);
    }
}
