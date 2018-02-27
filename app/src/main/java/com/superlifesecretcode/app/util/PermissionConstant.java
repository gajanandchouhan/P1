package com.superlifesecretcode.app.util;

import android.Manifest;

/**
 * Created by Divya on 23-02-2018.
 */

public interface PermissionConstant {
    int CODE_LOCATION = 101;
    int CODE_PROFILE = 102;
    String PERMISSION_LOCATION[] = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    String PERMISSION_PROFILE[] = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};

}
