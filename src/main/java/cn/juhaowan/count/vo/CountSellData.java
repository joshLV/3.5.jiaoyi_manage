package cn.juhaowan.count.vo;

import java.io.Serializable;
import java.util.Date;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;

/**
 * 卖家销售数据
 * @author Administrator
 *
 */
@Table(name = "count_sell_data")
public class CountSellData implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id", type = DbType.Int)
	private int id;
	
	@Column(name = "create_date", type = DbType.DateTime)
	private Date createDate;
	
	@Column(name = "newsSaler_day_count", type = DbType.Int)
	private int newsSalerDayCount;
	
	@Column(name = "newsSaler_sell_count", type = DbType.Int)
	private int newsSalerSellCount;
	
	@Column(name = "newsSaler_onsaleProduct_total", type = DbType.Int)
	private int onProductTotal;
	
	@Column(name = "newsSaler_avg_onsaleProduct", type = DbType.Int)
	private int avgOnSaleProduct;
	
	@Column(name = "newsSaler_onsaleProduct_amount", type = DbType.Double)
	private double onSaleProAmount;
	
	@Column(name = "newsSaler_avg_onsaleProduct_amount", type = DbType.Double)
	private double avgOnSaleProAmount;
	
	@Column(name = "newsSaler_sell_product_total", type = DbType.Double)
	private double sellProTotal;
	
	@Column(name = "newsSaler_avg_sell_product_total", type = DbType.Double)
	private double avgSellProTotal;
	
	@Column(name = "newsSaler_sell_product_amount", type = DbType.Double)
	private double sellProAmount;
	
	@Column(name = "newsSaler_avg_sell_product_amount", type = DbType.Double)
	private double avgSellProAmount;
	
	
	@Column(name = "days7_zero_sell_items", type = DbType.Int)
	private double d7ZeroSellItems;
	
	@Column(name = "days7_a_sell_items", type = DbType.Int)
	private int d7AsellItem;
	
	@Column(name = "days7_twoToFive_sell_items", type = DbType.Int)
	private int d7TwoToFiveSellItems;
	
	@Column(name = "days7_sixToTen_sell_items", type = DbType.Int)
	private int d7sixToTenSellItems;
	
	@Column(name = "days7_largeTen_sell_items", type = DbType.Int)
	private int d7LargeTenSellItems;
	
	@Column(name = "days7_sell_rate", type = DbType.Double)
	private double d7SellRate;
	
	@Column(name = "days7_avg_sell_items", type = DbType.Double)
	private double d7AvgSellItems;
	
	@Column(name = "days7_avg_sell_amount", type = DbType.Double)
	private double d7AvgSellAmount;
	
	
	@Column(name = "days15_zero_sell_items", type = DbType.Int)
	private int d15ZeroSellItems;
	
	@Column(name = "days15_a_sell_items", type = DbType.Int)
	private int d15Aselltems;
	
	@Column(name = "days15_twoToTen_sell_items", type = DbType.Int)
	private int d15TwoToTenSellItems;
	
	@Column(name = "days15_elevenToTwenty_sell_items", type = DbType.Int)
	private int d15EleToTwentySellItems;
	
	@Column(name = "days15_largeTwenty_sell_items", type = DbType.Int)
	private int d15LargeTwentySellItems;
	
	@Column(name = "days15_sell_rate", type = DbType.Double)
	private double d15SellRate;
	
	@Column(name = "days15_avg_sell_items", type = DbType.Double)
	private double d15AvgSellItems;
	
	@Column(name = "days15_avg_sell_amount", type = DbType.Double)
	private double d15AvgSellAmount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getNewsSalerDayCount() {
		return newsSalerDayCount;
	}

	public void setNewsSalerDayCount(int newsSalerDayCount) {
		this.newsSalerDayCount = newsSalerDayCount;
	}

	public int getNewsSalerSellCount() {
		return newsSalerSellCount;
	}

	public void setNewsSalerSellCount(int newsSalerSellCount) {
		this.newsSalerSellCount = newsSalerSellCount;
	}

	public int getOnProductTotal() {
		return onProductTotal;
	}

	public void setOnProductTotal(int onProductTotal) {
		this.onProductTotal = onProductTotal;
	}

	public int getAvgOnSaleProduct() {
		return avgOnSaleProduct;
	}

	public void setAvgOnSaleProduct(int avgOnSaleProduct) {
		this.avgOnSaleProduct = avgOnSaleProduct;
	}

	public double getOnSaleProAmount() {
		return onSaleProAmount;
	}

	public void setOnSaleProAmount(double onSaleProAmount) {
		this.onSaleProAmount = onSaleProAmount;
	}

	public double getAvgOnSaleProAmount() {
		return avgOnSaleProAmount;
	}

	public void setAvgOnSaleProAmount(double avgOnSaleProAmount) {
		this.avgOnSaleProAmount = avgOnSaleProAmount;
	}

	public double getSellProTotal() {
		return sellProTotal;
	}

	public void setSellProTotal(double sellProTotal) {
		this.sellProTotal = sellProTotal;
	}

	public double getAvgSellProTotal() {
		return avgSellProTotal;
	}

	public void setAvgSellProTotal(double avgSellProTotal) {
		this.avgSellProTotal = avgSellProTotal;
	}

	public double getSellProAmount() {
		return sellProAmount;
	}

	public void setSellProAmount(double sellProAmount) {
		this.sellProAmount = sellProAmount;
	}

	public double getAvgSellProAmount() {
		return avgSellProAmount;
	}

	public void setAvgSellProAmount(double avgSellProAmount) {
		this.avgSellProAmount = avgSellProAmount;
	}

	public double getD7ZeroSellItems() {
		return d7ZeroSellItems;
	}

	public void setD7ZeroSellItems(double d7ZeroSellItems) {
		this.d7ZeroSellItems = d7ZeroSellItems;
	}

	public int getD7AsellItem() {
		return d7AsellItem;
	}

	public void setD7AsellItem(int d7AsellItem) {
		this.d7AsellItem = d7AsellItem;
	}

	public int getD7TwoToFiveSellItems() {
		return d7TwoToFiveSellItems;
	}

	public void setD7TwoToFiveSellItems(int d7TwoToFiveSellItems) {
		this.d7TwoToFiveSellItems = d7TwoToFiveSellItems;
	}

	public int getD7sixToTenSellItems() {
		return d7sixToTenSellItems;
	}

	public void setD7sixToTenSellItems(int d7sixToTenSellItems) {
		this.d7sixToTenSellItems = d7sixToTenSellItems;
	}

	public int getD7LargeTenSellItems() {
		return d7LargeTenSellItems;
	}

	public void setD7LargeTenSellItems(int d7LargeTenSellItems) {
		this.d7LargeTenSellItems = d7LargeTenSellItems;
	}

	public double getD7SellRate() {
		return d7SellRate;
	}

	public void setD7SellRate(double d7SellRate) {
		this.d7SellRate = d7SellRate;
	}

	public double getD7AvgSellItems() {
		return d7AvgSellItems;
	}

	public void setD7AvgSellItems(double d7AvgSellItems) {
		this.d7AvgSellItems = d7AvgSellItems;
	}

	public double getD7AvgSellAmount() {
		return d7AvgSellAmount;
	}

	public void setD7AvgSellAmount(double d7AvgSellAmount) {
		this.d7AvgSellAmount = d7AvgSellAmount;
	}

	public int getD15ZeroSellItems() {
		return d15ZeroSellItems;
	}

	public void setD15ZeroSellItems(int d15ZeroSellItems) {
		this.d15ZeroSellItems = d15ZeroSellItems;
	}

	public int getD15Aselltems() {
		return d15Aselltems;
	}

	public void setD15Aselltems(int d15Aselltems) {
		this.d15Aselltems = d15Aselltems;
	}

	public int getD15TwoToTenSellItems() {
		return d15TwoToTenSellItems;
	}

	public void setD15TwoToTenSellItems(int d15TwoToTenSellItems) {
		this.d15TwoToTenSellItems = d15TwoToTenSellItems;
	}

	public int getD15EleToTwentySellItems() {
		return d15EleToTwentySellItems;
	}

	public void setD15EleToTwentySellItems(int d15EleToTwentySellItems) {
		this.d15EleToTwentySellItems = d15EleToTwentySellItems;
	}

	public int getD15LargeTwentySellItems() {
		return d15LargeTwentySellItems;
	}

	public void setD15LargeTwentySellItems(int d15LargeTwentySellItems) {
		this.d15LargeTwentySellItems = d15LargeTwentySellItems;
	}

	public double getD15SellRate() {
		return d15SellRate;
	}

	public void setD15SellRate(double d15SellRate) {
		this.d15SellRate = d15SellRate;
	}

	public double getD15AvgSellItems() {
		return d15AvgSellItems;
	}

	public void setD15AvgSellItems(double d15AvgSellItems) {
		this.d15AvgSellItems = d15AvgSellItems;
	}

	public double getD15AvgSellAmount() {
		return d15AvgSellAmount;
	}

	public void setD15AvgSellAmount(double d15AvgSellAmount) {
		this.d15AvgSellAmount = d15AvgSellAmount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
