package com.onlineeducationsyestem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class DefaultCategory implements Serializable
{

@SerializedName("status")
@Expose
private Integer status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("data")
@Expose
private Data data;
private final static long serialVersionUID = 2835948274634063666L;

public Integer getStatus() {
return status;
}

public void setStatus(Integer status) {
this.status = status;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public Data getData() {
return data;
}

public void setData(Data data) {
this.data = data;
}

//

    public class Data implements Serializable
    {

        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("categories")
        @Expose
        private ArrayList<Category> categories = null;
        private final static long serialVersionUID = -4768917766590660438L;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public ArrayList<Category> getCategories() {
            return categories;
        }

        public void setCategories(ArrayList<Category> categories) {
            this.categories = categories;
        }

    }
    //

    public class Category implements Serializable
    {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("category_name")
        @Expose
        private String categoryName;
        @SerializedName("category_icon")
        @Expose
        private String categoryIcon;
        private final static long serialVersionUID = 2268342625711373244L;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getCategoryIcon() {
            return categoryIcon;
        }

        public void setCategoryIcon(String categoryIcon) {
            this.categoryIcon = categoryIcon;
        }

    }

}