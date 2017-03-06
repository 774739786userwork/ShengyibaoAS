package com.bangware.shengyibao.customer.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3033663599091826822L;
	private String id;//客户id
	private String code;
	private String name;
	private String address;
	private String telephone;
	private String lasttime;//进货时间
	private String deliver_good_count;//进货次数
	private String purchase_total_sum;//进货金额
	private String province;//所属省份
	private String city;//所属行政区域
	private String district;//所属营销区域
	private String salerareaId;//营销区域ID
	private int total_record_sum; //总记录数
	private String longitude;//经度
	private String latitude;//纬度
	private String nearBy;//附近
	private String type;//区分附近与区域来进行取值判断
	private String compositor;
	
	private boolean isFavorites = false;
	
	public Customer() {
		// TODO Auto-generated constructor stub
	}
	
	public Customer(String id,String name,String address,List<Contacts> contacts) {
		super();
		this.id = id;
		this.address = address;
		this.name=name;
		this.contacts = contacts;
	}
	
	public String getSalerareaId() {
		return salerareaId;
	}

	public void setSalerareaId(String salerareaId) {
		this.salerareaId = salerareaId;
	}

	private List<CustomerKind> kinds = new ArrayList<CustomerKind>();
	private List<Contacts> contacts = new ArrayList<Contacts>();
	private List<CustomerImage> images = new ArrayList<CustomerImage>();
	
	public List<CustomerImage> getImages() {
		return images;
	}

	public void setImages(List<CustomerImage> images) {
		this.images = images;
	}
	
	public void addImages(CustomerImage images){
		this.images.add(images);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getLasttime() {
		return lasttime;
	}

	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}

	public String getDeliver_good_count() {
		return deliver_good_count;
	}

	public void setDeliver_good_count(String deliver_good_count) {
		this.deliver_good_count = deliver_good_count;
	}

	public String getPurchase_total_sum() {
		return purchase_total_sum;
	}

	public void setPurchase_total_sum(String purchase_total_sum) {
		this.purchase_total_sum = purchase_total_sum;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public List<Contacts> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contacts> contacts) {
		this.contacts = contacts;
	}
	
	public void addContacts(Contacts contacts){
		this.contacts.add(contacts);
	}

	public List<CustomerKind> getKinds() {
		return kinds;
	}

	public void setKinds(List<CustomerKind> kinds) {
		this.kinds = kinds;
	}
	public void addKind(CustomerKind kind){
		this.kinds.add(kind);
	}
	
	public boolean isFavorites() {
		return isFavorites;
	}

	public void setFavorites(boolean isFavorites) {
		this.isFavorites = isFavorites;
	}

	public int getTotal_record_sum() {
		return total_record_sum;
	}

	public void setTotal_record_sum(int total_record_sum) {
		this.total_record_sum = total_record_sum;
	}

	public String getNearBy() {
		return nearBy;
	}

	public void setNearBy(String nearBy) {
		this.nearBy = nearBy;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCompositor() {
		return compositor;
	}

	public void setCompositor(String compositor) {
		this.compositor = compositor;
	}
}
