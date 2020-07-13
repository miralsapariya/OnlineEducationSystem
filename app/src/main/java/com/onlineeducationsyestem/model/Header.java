package com.onlineeducationsyestem.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Header  implements Serializable {

    private String header;
    private boolean isHeader;

    public Header(String header, boolean isHeader) {
        this.header = header;
        this.isHeader = isHeader;
    }
    public String getHeader() {
        return header;
    }

    public boolean getIsHeader() {
        return isHeader;
    }

    public ArrayList<ContentItem> listContent =new ArrayList<>();

}
