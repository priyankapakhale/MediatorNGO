package com.example.priyanka.mediatorngo.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by priyanka on 1/28/18.
 */

public class PickupRequest implements Serializable {

    @SerializedName("donation_id")
    @Expose
    private int id;

    @SerializedName("ngo")
    @Expose
    private int ngo_id;

    @SerializedName("volunteer")
    @Expose
    private int volunteer_id;

    @SerializedName("start_date")
    @Expose
    private String start_date;

    @SerializedName("end_date")
    @Expose
    private String end_date;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("contact_no")
    @Expose
    private String contact_no;

    @SerializedName("quantity")
    @Expose
    private int quantity;

    public String getContactNo() {return contact_no;}
    public String getAddress() {return address;}
    public String getName() {return name;}
    public int getId() {return id;}
    public String getStartDate() {return start_date;}
    public String getEndDate() {return end_date;}
    public int getNGOId() {return ngo_id;}
    public int getVolunteerId() {return volunteer_id;}
    public String getStatus() {return status;}
    public int getQuantity() {return quantity;}


    public void setAddress(String address) {this.address=address;}
    public void setContactNo(String contact_no){this.contact_no=contact_no;}
    public void setName(String name) {this.name=name;}
    public void setStartDate(String start_date){this.start_date= start_date;}
    public void setEndDate(String end_date){this.end_date=end_date;}
    public void setId(int id) {this.id = id;}
    public void setNGOId(int ngo_id) {this.ngo_id = ngo_id;}
    public void setVolunteerId(int volunteer_id) {this.volunteer_id = volunteer_id;}
    public void setStatus(String status) {this.status = status;}
    public void setQuantity(int quantity) {this.quantity = quantity;}





}
