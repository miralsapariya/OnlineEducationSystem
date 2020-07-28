package com.onlineeducationsyestem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Category1 implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("sub_categories")
    @Expose
    private List<List<SubCategory_>> subCategories = null;
    private final static long serialVersionUID = -5043257296848095925L;

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

    public List<List<SubCategory_>> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<List<SubCategory_>> subCategories) {
        this.subCategories = subCategories;
    }
    public class CourseList implements Serializable
    {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("course_name")
        @Expose
        private String courseName;
        @SerializedName("course_price")
        @Expose
        private String coursePrice;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("instructor_name")
        @Expose
        private String instructorName;
        private final static long serialVersionUID = 1524159936346323123L;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getInstructorName() {
            return instructorName;
        }

        public void setInstructorName(String instructorName) {
            this.instructorName = instructorName;
        }

    }

    public class Datum implements Serializable
    {

        @SerializedName("categories")
        @Expose
        private List<Category> categories = null;
        @SerializedName("instructor_label")
        @Expose
        private String instructorLabel;
        @SerializedName("instructor_list")
        @Expose
        private List<InstructorList> instructorList = null;
        private final static long serialVersionUID = -2923495393502265649L;

        public List<Category> getCategories() {
            return categories;
        }

        public void setCategories(List<Category> categories) {
            this.categories = categories;
        }

        public String getInstructorLabel() {
            return instructorLabel;
        }

        public void setInstructorLabel(String instructorLabel) {
            this.instructorLabel = instructorLabel;
        }

        public List<InstructorList> getInstructorList() {
            return instructorList;
        }

        public void setInstructorList(List<InstructorList> instructorList) {
            this.instructorList = instructorList;
        }

    }

    public class InstructorList implements Serializable
    {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("total_course")
        @Expose
        private Integer totalCourse;
        @SerializedName("profile_picture")
        @Expose
        private String profilePicture;
        private final static long serialVersionUID = -1211460966100013326L;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getTotalCourse() {
            return totalCourse;
        }

        public void setTotalCourse(Integer totalCourse) {
            this.totalCourse = totalCourse;
        }

        public String getProfilePicture() {
            return profilePicture;
        }

        public void setProfilePicture(String profilePicture) {
            this.profilePicture = profilePicture;
        }

    }

    public class SubCategory implements Serializable
    {

        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("meesage")
        @Expose
        private String meesage;
        @SerializedName("data")
        @Expose
        private List<Datum> data = null;
        private final static long serialVersionUID = -6559579989514744487L;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getMeesage() {
            return meesage;
        }

        public void setMeesage(String meesage) {
            this.meesage = meesage;
        }

        public List<Datum> getData() {
            return data;
        }

        public void setData(List<Datum> data) {
            this.data = data;
        }

    }


    public class SubCategory_ implements Serializable
    {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("sub_category_name")
        @Expose
        private String subCategoryName;
        @SerializedName("course_list")
        @Expose
        private List<CourseList> courseList = null;
        private final static long serialVersionUID = -1930753326559395074L;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getSubCategoryName() {
            return subCategoryName;
        }

        public void setSubCategoryName(String subCategoryName) {
            this.subCategoryName = subCategoryName;
        }

        public List<CourseList> getCourseList() {
            return courseList;
        }

        public void setCourseList(List<CourseList> courseList) {
            this.courseList = courseList;
        }

    }
}
