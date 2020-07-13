package com.onlineeducationsyestem.model;

import java.io.Serializable;

public class ContentItem implements Serializable {

    private String name;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private boolean isSelected;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
