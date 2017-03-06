package com.bangware.shengyibao.model;

/**
 * 产品信息
 * @author luming.tang
 *
 */
public class Product implements java.io.Serializable{
	private static final long serialVersionUID = -5622063816597717236L;
	private String id;
	private String image;//图片
	private String name;//名称
	private String specifications;//规格
	private Integer stock;//库存
	private String activity;//活动
	private double price;//单价
	private double total_foregift;
	private double foregift;//押金
	private String unit;//单位
	private String basic_unit;//基础单位
	private double basic_price;//基础单价
	private String conversion_unit;//转换单位
	private String is_show;//最小单位的标识   0无 1有

	
	public Product() {
		super();
	}
		public Product(String id,String image, String name,Integer stock,double p_totalforegift) {
			super();
		this.id = id;
		this.image = image;
		this.name = name;
		this.stock = stock;
		this.total_foregift=p_totalforegift;
	}
	/*public Product(String id,String image, String name,Integer stock,String is_show,double basic_price) {
		super();
		this.id = id;
		this.image = image;
		this.name = name;
		this.stock = stock;
		this.is_show = is_show;
		this.basic_price = basic_price;
	}*/

	public double getP_totalforegift() {
		return total_foregift;
	}

	public void setP_totalforegift(double p_totalforegift) {
		this.total_foregift = p_totalforegift;
	}

	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSpecifications() {
		return specifications;
	}
	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getForegift() {
		return foregift;
	}
	public void setForegift(double foregift) {
		this.foregift = foregift;
	}

	public String getBasic_unit() {
		return basic_unit;
	}

	public void setBasic_unit(String basic_unit) {
		this.basic_unit = basic_unit;
	}

	public double getBasic_price() {
		return basic_price;
	}

	public void setBasic_price(double basic_price) {
		this.basic_price = basic_price;
	}

	public String getConversion_unit() {
		return conversion_unit;
	}

	public void setConversion_unit(String conversion_unit) {
		this.conversion_unit = conversion_unit;
	}

	public String getIs_show() {
		return is_show;
	}

	public void setIs_show(String is_show) {
		this.is_show = is_show;
	}
}
