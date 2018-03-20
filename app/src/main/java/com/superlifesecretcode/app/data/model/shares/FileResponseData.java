package com.superlifesecretcode.app.data.model.shares;

import java.io.Serializable;

/**
 * Created by Divya on 19-03-2018.
 */

public class FileResponseData implements Serializable{
    private String type;
    private String file;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
