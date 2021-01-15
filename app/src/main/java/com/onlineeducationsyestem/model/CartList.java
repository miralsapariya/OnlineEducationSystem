package com.onlineeducationsyestem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CartList {

@SerializedName("status")
@Expose
private Integer status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("data")
@Expose
private List<Datum> data = null;

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

public List<Datum> getData() {
return data;
}

public void setData(List<Datum> data) {
this.data = data;
}
//
public class Datum {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("cart_item_qty")
    @Expose
    private Integer cartItemQty;
    @SerializedName("list")
    @Expose
    private ArrayList<ListData> listData = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCartItemQty() {
        return cartItemQty;
    }

    public void setCartItemQty(Integer cartItemQty) {
        this.cartItemQty = cartItemQty;
    }

    public ArrayList<ListData> getList() {
        return listData;
    }

    public void setList(ArrayList<ListData> list) {
        this.listData = list;
    }

}

//
public class ListData {

    @SerializedName("courseid")
    @Expose
    private Integer courseid;
    @SerializedName("cartid")
    @Expose
    private Integer cartid;
    @SerializedName("course_name")
    @Expose
    private String courseName;
    @SerializedName("course_price")
    @Expose
    private String coursePrice;
    @SerializedName("course_old_price")
    @Expose
    private String courseOldPrice;
    @SerializedName("course_discount")
    @Expose
    private Integer courseDiscount;
    @SerializedName("instructor_name")
    @Expose
    private String instructorName;
    @SerializedName("image")
    @Expose
    private String image;

    public Float amount;


    public Integer getCourseid() {
        return courseid;
    }

    public void setCourseid(Integer courseid) {
        this.courseid = courseid;
    }

    public Integer getCartid() {
        return cartid;
    }

    public void setCartid(Integer cartid) {
        this.cartid = cartid;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(String coursePrice) {
        this.coursePrice = coursePrice;
    }

    public String getCourseOldPrice() {
        return courseOldPrice;
    }

    public void setCourseOldPrice(String courseOldPrice) {
        this.courseOldPrice = courseOldPrice;
    }

    public Integer getCourseDiscount() {
        return courseDiscount;
    }

    public void setCourseDiscount(Integer courseDiscount) {
        this.courseDiscount = courseDiscount;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}


}
//



