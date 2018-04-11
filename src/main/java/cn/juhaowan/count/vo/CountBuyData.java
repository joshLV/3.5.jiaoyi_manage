package cn.juhaowan.count.vo;

import java.util.Date;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;

/**
 *买家购买数据
 * @author Administrator
 *
 */
@Table(name = "count_buy_data")
public class CountBuyData implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id", type = DbType.Int)
	private int id;
	
	@Column(name = "create_date", type = DbType.DateTime)
	private Date createDate;
	
	@Column(name = "news_buyer_day", type = DbType.Int)
	private int newsBuyerDay;
	
	@Column(name = "buyer_total", type = DbType.Int)
	private int buyerTotal;
	
	@Column(name = "is_order_total", type = DbType.Int)
	private int isOrderTotal;
	
	@Column(name = "news_buyer_accounting", type = DbType.Double)
	private double buyerAccounting;
	
	@Column(name = "news_buyer_avg_items", type = DbType.Double)
	private double newsbuyerAvgItems;
	
	@Column(name = "buyer_avg_items", type = DbType.Double)
	private double buyerAvgItems;
	
	@Column(name = "news_buyer_avg_consumption", type = DbType.Double)
	private double newsBuyerAvgConsumption;
	
	@Column(name = "buyer_avg_consumption", type = DbType.Double)
	private double buyerAvgConsumption;
	
	
	@Column(name = "days7_zero_items", type = DbType.Int)
	private int days7ZeroItems;
	
	@Column(name = "days7_a_items", type = DbType.Int)
	private int days7Aitems;
	
	@Column(name = "days7_two_items", type = DbType.Int)
	private int days7TwoItems;
	
	@Column(name = "days7_threeToFive_items", type = DbType.Int)
	private int days7ThreeToFiveItems;
	
	@Column(name = "days7_largeFive_items", type = DbType.Int)
	private int days7LargeFiveItems;
	
	@Column(name = "days7_is_order_total", type = DbType.Int)
	private int days7IsOrderTotal;
	
	@Column(name = "days7_buy_rate", type = DbType.Double)
	private double days7BuyRate;
	
	@Column(name = "days7_repeat_buy_rate", type = DbType.Double)
	private double days7RepeatBuyRate;
	
	@Column(name = "days7_repeat_buy_avg_items", type = DbType.Double)
	private double days7RepeatBuyAvgItems;
	
	@Column(name = "days7_repeat_buy_avg_comsumption", type = DbType.Double)
	private double days7RepeatBuyAvgConsumption;
	
	
	@Column(name = "days15_zero_items", type = DbType.Int)
	private int days15ZeroItems;
	
	@Column(name = "days15_a_items", type = DbType.Int)
	private int days15Aitems;
	
	@Column(name = "days15_twoToFive_items", type = DbType.Int)
	private int days15TwoToFiveItems;
	
	@Column(name = "days15_sixToTen_items", type = DbType.Int)
	private int days15SixToTenItems;
	
	@Column(name = "days15_largeTen_items", type = DbType.Int)
	private int days15LargeTenItems;
	
	@Column(name = "days15_is_order_total", type = DbType.Int)
	private int days15isOrderTotal;
	
	@Column(name = "days15_buy_rate", type = DbType.Double)
	private double days15BuyRate;
	
	@Column(name = "days15_repeat_buy_rate", type = DbType.Double)
	private double days15RepeatBuyRate;
	
	@Column(name = "days15_repeat_buy_avg_items", type = DbType.Double)
	private double days15RepeatBuyAvgItems;
	
	@Column(name = "days15_repeat_buy_avg_comsumption", type = DbType.Double)
	private double days15RepeatBuyAvgConsumption;
	
	
	@Column(name = "days30_zero_items", type = DbType.Int)
	private int days30ZeroItems;
	
	@Column(name = "days30_a_items", type = DbType.Int)
	private int days30Aitems;
	
	@Column(name = "days30_twoToEight_items", type = DbType.Int)
	private int days30TwoToEightItems;
	
	@Column(name = "days30_eightToFifteen_items", type = DbType.Int)
	private int days30EightToFifteenItems;
	
	@Column(name = "days30_largeFifteen_items", type = DbType.Int)
	private int days30LargeFifteenItems;
	
	@Column(name = "days30_is_order_total", type = DbType.Int)
	private int days30isOrderTotal;
	
	@Column(name = "days30_buy_rate", type = DbType.Double)
	private double days30BuyRate;
	
	@Column(name = "days30_repeat_buy_rate", type = DbType.Double)
	private double days30RepeatBuyRate;
	
	@Column(name = "days30_repeat_buy_avg_items", type = DbType.Double)
	private double days30RepeatBuyAvgItems;
	
	@Column(name = "days30_repeat_buy_avg_comsumption", type = DbType.Double)
	private double days30RepeatBuyAvgConsumption;

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

	public int getNewsBuyerDay() {
		return newsBuyerDay;
	}

	public void setNewsBuyerDay(int newsBuyerDay) {
		this.newsBuyerDay = newsBuyerDay;
	}

	public int getBuyerTotal() {
		return buyerTotal;
	}

	public void setBuyerTotal(int buyerTotal) {
		this.buyerTotal = buyerTotal;
	}

	public double getNewsbuyerAvgItems() {
		return newsbuyerAvgItems;
	}

	public void setNewsbuyerAvgItems(int newsbuyerAvgItems) {
		this.newsbuyerAvgItems = newsbuyerAvgItems;
	}

	public double getBuyerAvgItems() {
		return buyerAvgItems;
	}

	public void setBuyerAvgItems(double buyerAvgItems) {
		this.buyerAvgItems = buyerAvgItems;
	}

	public double getNewsBuyerAvgConsumption() {
		return newsBuyerAvgConsumption;
	}

	public void setNewsBuyerAvgConsumption(double newsBuyerAvgConsumption) {
		this.newsBuyerAvgConsumption = newsBuyerAvgConsumption;
	}

	public double getBuyerAvgConsumption() {
		return buyerAvgConsumption;
	}

	public void setBuyerAvgConsumption(double buyerAvgConsumption) {
		this.buyerAvgConsumption = buyerAvgConsumption;
	}

	public int getDays7ZeroItems() {
		return days7ZeroItems;
	}

	public void setDays7ZeroItems(int days7ZeroItems) {
		this.days7ZeroItems = days7ZeroItems;
	}

	public int getDays7Aitems() {
		return days7Aitems;
	}

	public void setDays7Aitems(int days7Aitems) {
		this.days7Aitems = days7Aitems;
	}

	public int getDays7TwoItems() {
		return days7TwoItems;
	}

	public void setDays7TwoItems(int days7TwoItems) {
		this.days7TwoItems = days7TwoItems;
	}

	public int getDays7ThreeToFiveItems() {
		return days7ThreeToFiveItems;
	}

	public void setDays7ThreeToFiveItems(int days7ThreeToFiveItems) {
		this.days7ThreeToFiveItems = days7ThreeToFiveItems;
	}

	public int getDays7LargeFiveItems() {
		return days7LargeFiveItems;
	}

	public void setDays7LargeFiveItems(int days7LargeFiveItems) {
		this.days7LargeFiveItems = days7LargeFiveItems;
	}

	public double getDays7BuyRate() {
		return days7BuyRate;
	}

	public void setDays7BuyRate(int days7BuyRate) {
		this.days7BuyRate = days7BuyRate;
	}

	public double getDays7RepeatBuyRate() {
		return days7RepeatBuyRate;
	}

	public void setDays7RepeatBuyRate(double days7RepeatBuyRate) {
		this.days7RepeatBuyRate = days7RepeatBuyRate;
	}

	public double getDays7RepeatBuyAvgItems() {
		return days7RepeatBuyAvgItems;
	}

	public void setDays7RepeatBuyAvgItems(double days7RepeatBuyAvgItems) {
		this.days7RepeatBuyAvgItems = days7RepeatBuyAvgItems;
	}

	public double getDays7RepeatBuyAvgConsumption() {
		return days7RepeatBuyAvgConsumption;
	}

	public void setDays7RepeatBuyAvgConsumption(double days7RepeatBuyAvgConsumption) {
		this.days7RepeatBuyAvgConsumption = days7RepeatBuyAvgConsumption;
	}

	public int getDays15ZeroItems() {
		return days15ZeroItems;
	}

	public void setDays15ZeroItems(int days15ZeroItems) {
		this.days15ZeroItems = days15ZeroItems;
	}

	public int getDays15Aitems() {
		return days15Aitems;
	}

	public void setDays15Aitems(int days15Aitems) {
		this.days15Aitems = days15Aitems;
	}

	public int getDays15TwoToFiveItems() {
		return days15TwoToFiveItems;
	}

	public void setDays15TwoToFiveItems(int days15TwoToFiveItems) {
		this.days15TwoToFiveItems = days15TwoToFiveItems;
	}

	public int getDays15SixToTenItems() {
		return days15SixToTenItems;
	}

	public void setDays15SixToTenItems(int days15SixToTenItems) {
		this.days15SixToTenItems = days15SixToTenItems;
	}

	public int getDays15LargeTenItems() {
		return days15LargeTenItems;
	}

	public void setDays15LargeTenItems(int days15LargeTenItems) {
		this.days15LargeTenItems = days15LargeTenItems;
	}

	public double getDays15BuyRate() {
		return days15BuyRate;
	}

	public void setDays15BuyRate(double days15BuyRate) {
		this.days15BuyRate = days15BuyRate;
	}

	public double getDays15RepeatBuyRate() {
		return days15RepeatBuyRate;
	}

	public void setDays15RepeatBuyRate(double days15RepeatBuyRate) {
		this.days15RepeatBuyRate = days15RepeatBuyRate;
	}

	public double getDays15RepeatBuyAvgItems() {
		return days15RepeatBuyAvgItems;
	}

	public void setDays15RepeatBuyAvgItems(double days15RepeatBuyAvgItems) {
		this.days15RepeatBuyAvgItems = days15RepeatBuyAvgItems;
	}

	public double getDays15RepeatBuyAvgConsumption() {
		return days15RepeatBuyAvgConsumption;
	}

	public void setDays15RepeatBuyAvgConsumption(
			double days15RepeatBuyAvgConsumption) {
		this.days15RepeatBuyAvgConsumption = days15RepeatBuyAvgConsumption;
	}

	public int getDays30ZeroItems() {
		return days30ZeroItems;
	}

	public void setDays30ZeroItems(int days30ZeroItems) {
		this.days30ZeroItems = days30ZeroItems;
	}

	public int getDays30Aitems() {
		return days30Aitems;
	}

	public void setDays30Aitems(int days30Aitems) {
		this.days30Aitems = days30Aitems;
	}

	public int getDays30TwoToEightItems() {
		return days30TwoToEightItems;
	}

	public void setDays30TwoToEightItems(int days30TwoToEightItems) {
		this.days30TwoToEightItems = days30TwoToEightItems;
	}

	public int getDays30EightToFifteenItems() {
		return days30EightToFifteenItems;
	}

	public void setDays30EightToFifteenItems(int days30EightToFifteenItems) {
		this.days30EightToFifteenItems = days30EightToFifteenItems;
	}

	public int getDays30LargeFifteenItems() {
		return days30LargeFifteenItems;
	}

	public void setDays30LargeFifteenItems(int days30LargeFifteenItems) {
		this.days30LargeFifteenItems = days30LargeFifteenItems;
	}

	public double getDays30BuyRate() {
		return days30BuyRate;
	}

	public void setDays30BuyRate(double days30BuyRate) {
		this.days30BuyRate = days30BuyRate;
	}

	public double getDays30RepeatBuyRate() {
		return days30RepeatBuyRate;
	}

	public void setDays30RepeatBuyRate(double days30RepeatBuyRate) {
		this.days30RepeatBuyRate = days30RepeatBuyRate;
	}

	public double getDays30RepeatBuyAvgItems() {
		return days30RepeatBuyAvgItems;
	}

	public void setDays30RepeatBuyAvgItems(double days30RepeatBuyAvgItems) {
		this.days30RepeatBuyAvgItems = days30RepeatBuyAvgItems;
	}

	public double getDays30RepeatBuyAvgConsumption() {
		return days30RepeatBuyAvgConsumption;
	}

	public void setDays30RepeatBuyAvgConsumption(double days30RepeatBuyAvgConsumption) {
		this.days30RepeatBuyAvgConsumption = days30RepeatBuyAvgConsumption;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getIsOrderTotal() {
		return isOrderTotal;
	}

	public void setIsOrderTotal(int isOrderTotal) {
		this.isOrderTotal = isOrderTotal;
	}

	public double getBuyerAccounting() {
		return buyerAccounting;
	}

	public void setBuyerAccounting(double buyerAccounting) {
		this.buyerAccounting = buyerAccounting;
	}

	public int getDays7IsOrderTotal() {
		return days7IsOrderTotal;
	}

	public void setDays7IsOrderTotal(int days7IsOrderTotal) {
		this.days7IsOrderTotal = days7IsOrderTotal;
	}

	

	public int getDays15isOrderTotal() {
		return days15isOrderTotal;
	}

	public void setDays15isOrderTotal(int days15isOrderTotal) {
		this.days15isOrderTotal = days15isOrderTotal;
	}

	public int getDays30isOrderTotal() {
		return days30isOrderTotal;
	}

	public void setDays30isOrderTotal(int days30isOrderTotal) {
		this.days30isOrderTotal = days30isOrderTotal;
	}

	public void setNewsbuyerAvgItems(double newsbuyerAvgItems) {
		this.newsbuyerAvgItems = newsbuyerAvgItems;
	}

	public void setDays7BuyRate(double days7BuyRate) {
		this.days7BuyRate = days7BuyRate;
	}

	
	
}
