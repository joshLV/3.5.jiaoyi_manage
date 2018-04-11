package cn.juhaowan.product.service.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.util.Cache;
import cn.jugame.util.DES;
import cn.jugame.util.PageInfo;
import cn.juhaowan.product.dao.ProductDao;
import cn.juhaowan.product.dao.ProductLogDao;
import cn.juhaowan.product.dao.ProductTypeDao;
import cn.juhaowan.product.service.ProductService;
import cn.juhaowan.product.vo.Product;
import cn.juhaowan.product.vo.ProductC2c;
import cn.juhaowan.product.vo.ProductImages;
import cn.juhaowan.product.vo.ProductLog;
import cn.juhaowan.product.vo.ProductType;

/**
 * 
 * @author Administrator
 * 
 */
@Service("ProductService")
public class DefaultProductService implements ProductService {
	private static Logger LOG = LoggerFactory.getLogger(DefaultProductService.class);
	@Autowired
	private ProductDao productDao;

	@Autowired
	private ProductLogDao productLogDao;

	@Autowired
	private ProductTypeDao productTypeDao;

	@Autowired
	private JdbcOperations jdbcOperations;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	// 添加商品信息
	@Override
	public int insertProductFront(Product product) {

		try {
			productDao.insertProduct(product);
			// 添加时为上架状态 插入商品上架表
			if (product.getProductStatus() == ProductDao.PRODUCT_STATUC_ONSALE) {
				productDao.delProductOnSale(product.getProductId());
				productDao.insertProductOnSale(product.getProductId());
			}

		} catch (Exception e) {
			return 1;
		}
		if (product.getUserId() != 69583 && product.getUserId() != 75199) {
			String description = "【添加商品】添加商品信息";
			productLogDao.addProductLog(product.getProductId(), ProductDao.OPERATE_INSERT, product.getUserId(), description, null);
		}
		return 0;

	}

	// 更新商品信息(后台 批量上架 修改接口用到)
	@Override
	public int updateProduct(Product product) {
		int i = 1;
		// 没有数据
		Product p = productDao.queryByProductId(product.getProductId());
		if (null == p) {
			return 1;
		}

		try {

			i = productDao.updateProduct(product);

		} catch (Exception e) {
			return 1;
		}
		if (product.getUserId() != 69583 && product.getUserId() != 75199) {
			String description = "【更新商品】商品信息更新操作--";
			productLogDao.addProductLog(product.getProductId(), ProductDao.OPERATE_UPDATE, product.getUserId(), description, null);

		}
		return i;
	}

	// 删除商品信息
	@Override
	public int deleteProduct(int userid, String productId) {
		int i = 1;
		i = productDao.deleteProduct(productId);
		if (i == 0) {
			productDao.delProductOnSale(productId);
			String description = "【删除商品】用户ID为：" + userid + "的用户对商品信息进行了删除操作";
			productLogDao.addProductLog(productId, ProductDao.OPERATE_DELETE, userid, description, null);
		}
		return i;
	}

	// 按商品id查询商品
	@Override
	public Product queryByProductId(String productId) {
		Product p = null;
		p = productDao.queryByProductId(productId);
		return p;
	}

	// 生成商品id
	@Override
	public String generationProductId(String productTypeCode) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmssSSS");
		int b = (int) (Math.random() * 100);
		String a = String.valueOf(b);
		String d = sdf.format(new Date());
		String productId = productTypeCode + "-" + d;
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 两位随机数
		StringBuffer s = new StringBuffer();
		if (a.length() < 2 && a.length() > 0) {
			int len = 2 - a.length();
			for (int j = 0; j < len; j++) {
				s = s.append("0");
			}
		}
		return productId + s.toString() + a;

	}

	@Override
	public String generationSellPid(String productId, String sellPid) {
		if (StringUtils.isBlank(sellPid)) {
			return productId + "-001";
		}
		// 规则：productid + “-” + 3位流水（32进制,最大支持到32767 [即：vvv]）
		int x = Integer.parseInt(sellPid.substring(sellPid.length() - 3), 32);
		x++;
		// String id = x + "";
		String id = Integer.toString(x, 32);
		while (id.length() < 3) {
			id = "0" + id;
		}
		String newSellPid = productId + "-" + id;
		return newSellPid;
	}

	@Override
	public PageInfo<Product> queryProductPageBack(String proTypeGroupStr,String productId, String userId, int productStatus, int gameId, int gamePartitionId, int gameServerId, String beginTime, String endTime, int proModel, int productType, String sonQudaoId,int pageNo, int pageSize, String sort,
			String order) {
		Map<String, Object> conditions = new HashMap<String, Object>();

		if (!("".equals(productId)) && null != productId) {
			conditions.put("product_id", productId);
		}
		if (null != userId && !"".equals(userId) && !"0".equals(userId)) {
			conditions.put("user_id", userId);
		}
		if (productStatus == -1 || productStatus > 0) {
			conditions.put("product_status", productStatus);
		}

		if (gameId > 0) {
			conditions.put("game_id", gameId);
		}
		if (gamePartitionId > 0) {
			conditions.put("game_area_id", gamePartitionId);
		}
		if (gameServerId > 0) {
			conditions.put("game_server_id", gameServerId);
		}
		if (productType > 0) {
			conditions.put("productType", productType);
		}
		if (!("".equals(beginTime)) && null != beginTime) {
			conditions.put("beginTime", beginTime);
		}
		if (!("".equals(endTime)) && null != endTime) {
			conditions.put("endTime", endTime);
		}
		if (proModel > 0) {
			conditions.put("pro_model", proModel);
		}
		if(StringUtils.isNotBlank(sonQudaoId)){
			conditions.put("son_qudao_id", sonQudaoId);
		}
		if(StringUtils.isNotBlank(proTypeGroupStr)){
			conditions.put("proTypeGroupStr", proTypeGroupStr);
		}
		return productDao.queryPageInfo(conditions, pageNo, pageSize, sort, order);
	}

	// 日志查询分页列表
	@Override
	public PageInfo<ProductLog> queryProductLog(String productId, int operateUserid, int operateType, int pageNo, int pageSize, String sort, String order) {
		Map<String, Object> conditions = new HashMap<String, Object>();

		if (null != productId && !("".equals(productId))) {
			conditions.put("product_id", productId);
		}
		if (operateUserid > 0) {
			conditions.put("operate_userid", operateUserid);
		}
		if (operateType > 0) {
			conditions.put("operate_type", operateType);
		}

		return productLogDao.queryProductLog(conditions, pageNo, pageSize, sort, order);
	}

	// 商品上架
	@Override
	public int productOnsale(int userid, String productId, String sellPid) {

		int i = productDao.productOnsale(userid, productId, sellPid);

		if (i == 0) {
			// 上架表如果已有数据 先删除
			productDao.delProductOnSale(productId);
			// 插入新数据
			productDao.insertProductOnSale(productId);
			// 记录日志 批量上架用户不记录日志
			if (userid != 69583 && userid != 75199) {
				String description = "【用户商品上架】 商品上架号：" + sellPid;
				productLogDao.addProductLog(productId, ProductDao.OPERATE_MODIFY_ONSALE, userid, description, null);
			}
		}

		return i;
	}

	// 商品下架
	@Override
	public int productOffShelves(int userid, String productId, int status) {

		int i = productDao.productOffShelves(userid, productId, status, null);
		if (i == 0) {
			// 删除上架表数据
			productDao.delProductOnSale(productId);
			// 清理缓存
			String cachekey = "product/product_info/" + productId;
			Cache.delete(cachekey);

			// 写日志
			if (userid != 69583 && userid != 75199) {
				String description = "【用户商品下架】 商品状态更新为" + status;
				productLogDao.addProductLog(productId, ProductDao.OPERATE_MODIFY_OFFSALE, userid, description, null);
			}
		}
		return i;
	}

	// 管理员后台下架
	@Override
	public int productAdminOffShelves(String productId, int status, int userid, String remark) {
		int i = productDao.productOffShelves(userid, productId, status, remark);
		if (i == 0) {
			// 删除上架表
			productDao.delProductOnSale(productId);
		}
		return i;
	}

	// 修改销售量 0：成功 1：失败
	@Override
	public int modifyProductSoldoutNumber(String productId, int saleNum, String orderId, int buyUId) {
		if (null == productId) {
			return 1;
		}
		Product p = new Product();
		p = queryByProductId(productId);
		if (null == p) {
			return 1;
		}

		// 已售数量
		int baseProductSoldoutNumber = p.getProductSoldoutNumber();

		int productSoldoutNumber = baseProductSoldoutNumber + saleNum;

		String sql = "update product set  product_soldout_number = ? where product_id = ? ";
		int j = jdbcOperations.update(sql, productSoldoutNumber, productId);
		if (j == 1) {
			productDao.delProductOnSale(productId);
			if (p.getProductStatus() == ProductDao.PRODUCT_STATUC_ONSALE) {
				productDao.insertProductOnSale(productId);
			}

			// 写日志
			String description = "【商品销售量更改】订单ID:" + orderId + "买家UID:" + buyUId + "上架号：" + p.getSellPid();
			productLogDao.addProductLog(productId, ProductDao.OPERATE_MODIFY_SOLDOUTNUM, 0, description, "销售量由" + baseProductSoldoutNumber + "修改为" + productSoldoutNumber);
		}
		return j ^ 1;
	}

	// 添加商品分类
	@Override
	public int insertProductType(ProductType productType) {
		return productTypeDao.insertProductType(productType);
	}

	// 更新商品分类
	@Override
	public int updateProductType(ProductType productType) {
		return productTypeDao.updateProductType(productType);
	}

	// 删除商品分类
	@Override
	public int deleteProductType(int id) {
		return productTypeDao.deleteProductType(id);
	}

	// 根据商品分类id查询商品分类
	@Override
	public ProductType findProductType(int id) {
		return productTypeDao.findbyId(id);
	}

	@Override
	public List<ProductType> queryProductTypeList() {
		List<ProductType> list = null;
		list = productTypeDao.queryProductTypeList();
		return list;
	}

	// 库存回滚
	@Override
	public int modifyProductStock(String productId, int stock, String orderId, int buyUId) {

		if (null == productId || stock < 0) {
			return 1;
		}

		Product p = new Product();
		p = queryByProductId(productId);
		if (null == p) {
			return 1;
		}
		// 如果商品不是上架状态
		if (ProductDao.PRODUCT_STATUC_ONSALE != p.getProductStatus()) {
			return 1;
		}
		int oldStock = p.getProductStock();
		// 更新product表 库存
		int newStock = oldStock + stock;
		p.setProductStock(newStock);
		p.setModifyTime(new Date());
		int i = productDao.updateProduct(p);
		// 更新成功
		if (i == 0) {
			// 修改productonsale表
			String sql = "select count(product_id) from product_onsale where product_id = ?";
			@SuppressWarnings("deprecation")
			int f = jdbcOperations.queryForInt(sql, productId);
			if (f > 0) {
				productDao.delProductOnSale(productId);
				productDao.insertProductOnSale(productId);

				// 写日志
				String description = "【商品库存回滚】订单ID:" + orderId + " 买家UID:" + buyUId + "上架号：" + p.getSellPid();
				productLogDao.addProductLog(productId, ProductDao.OPERATE_MODIFY_STOCK_ROLLBACK, 0, description, "库存由" + oldStock + "修改为" + newStock);
			}
		}
		return i;
	}

	// 查询用户 在售商品 的总价格
	@Override
	public double findUserOnsaleTotalPrice(int userId) {
		String sql = "SELECT user_id,`product_single_price`,`product_stock` FROM product WHERE user_id = ? " + "AND `product_status`= 7 ";
		SqlRowSet rs = jdbcOperations.queryForRowSet(sql, userId);
		double totalPrice = 0;
		while (rs.next()) {
			totalPrice += (rs.getInt(3) * rs.getFloat(2));
		}
		DecimalFormat df = new DecimalFormat("##.00");
		totalPrice = Double.parseDouble(df.format(totalPrice));
		return totalPrice;
	}

	@Override
	public int addProductLog(String productId, int operateType, int userid, String discretion, String remark) {
		int i = productLogDao.addProductLog(productId, operateType, userid, discretion, remark);
		return i ^ 1;
	}

	@Override
	public int insertProductC2c(String productId, String gameLogin, String gamePassword, String securityLock, String contactQQ, int goodsPosition) {
		int i = productDao.insertProductC2c(productId, gameLogin, gamePassword, securityLock, contactQQ, goodsPosition);
		return i;
	}
	
	@Override
	public int insertProductC2c(String productId, String gameLogin, String gamePassword, String securityLock, String contactQQ) {
		String sql = "INSERT INTO product_c2c (`product_id`,`game_login_id`,`game_login_password`,`security_lock`,`contact_qq`,`is_verify`,`goods_position`,`account_verify`) " + " VALUES (?,?,?,?,?,1,0,1)";
		int i = jdbcOperations.update(sql, productId, gameLogin, gamePassword, securityLock, contactQQ);
		return i ^ 1;
	}

	@Override
	public ProductC2c findProductC2cByProductId(String productId) {
		ProductC2c p = productDao.findProductC2cByProductId(productId);
		return p;
	}

	@Override
	public List<ProductImages> findImagesByProductId(String productId) {
		List<ProductImages> list = productDao.findImagesByProductId(productId);
		return list;
	}
	
	@Override
	public int insertProductImages(String productId, String filePath){
		String sql = "INSERT INTO product_images(product_id, pic_url, create_time, status) VALUES(?,?,now(),1)";
		int i = jdbcOperations.update(sql, productId, filePath);
		return i;
	}

	@Override
	public PageInfo<Map<String, Object>> queryProductC2CPageBack(Map<String, Object> conditions, int pageNo, int pageSize, String sort, String order) {

		return productDao.queryC2CPageInfo(conditions, pageNo, pageSize, sort, order);
	}

	@Override
	public int productC2COnsaleVerify(String productId, int status, String remark, int userid) {
		int i = 1;
		Product p = productDao.queryByProductId(productId);
		if (null == p || p.getProductStatus() != productDao.PRODUCT_STATUC_VERIFY_WAIT && p.getProductStatus() != productDao.PRODUCT_STATUC_VERIFY) {
			LOG.error("c2c商品审核出错,商品不存在 或者 商品不是待审核状态");
			return 1;
		}
		String remarkMsg = "";
		int operatetype = 0;
		int accountverify = 0;
		if (status == productDao.PRODUCT_STATUC_ONSALE) {
			accountverify = 1;
		} else if (status == productDao.PRODUCT_STATUC_VERIFY_FAIL) {
			accountverify = 2;
		}
		String sql = " UPDATE `product_c2c` SET `is_verify` = 1, account_verify = ? WHERE product_id = ? ";
		int k = jdbcOperations.update(sql, accountverify, productId);
		if (k > 0) {
			i = productDao.verifyProductStatus(productId, status, remark);
		} else {
			LOG.debug("c2c商品审核出错,商品c2c表更新出错");
			return 1;
		}
		// 更新商品状态
		// 审核通过 并上架商品
		if (i == 0 && status == productDao.PRODUCT_STATUC_ONSALE) {
			this.productOnsale(userid, productId, p.getSellPid());
			remarkMsg = "审核通过并上架";
			operatetype = ProductDao.OPERATE_MODIFY_VERIFY_SUCC;
			// 审核不通过
		} else if (i == 0 && status == productDao.PRODUCT_STATUC_VERIFY_FAIL) {
			remarkMsg = "审核不通过";
			operatetype = ProductDao.OPERATE_MODIFY_VERIFY_FAIL;
			// 其他情况
		} else {
			LOG.error("c2c商品审核上架出错,回滚扩展表审核状态");
			// 回滚
			String sql1 = " UPDATE `product_c2c` SET `is_verify` = 0,account_verify = 0 WHERE product_id = ? ";
			jdbcOperations.update(sql1, productId);
			return 1;
		}

		String description = "【c2c商品审核】 结果:" + remarkMsg;
		productLogDao.addProductLog(productId, operatetype, userid, description, remark);
		return 0;
	}

	@Override
	public int countWaitForVerify(int uid) {
		int i = 0;
		if (uid == 0) {
			String sql = "SELECT COUNT(a.product_id) FROM product a ,product_c2c b WHERE a.product_id = b.product_id AND a.product_onsale_model =1 AND a.product_status = 3 AND b.is_verify = 0";
			SqlRowSet r = jdbcOperations.queryForRowSet(sql);
			while (r.next()) {
				i = r.getInt(1);
			}
			return i;
		} else {
			String sql = "SELECT COUNT(a.product_id) FROM product a ,product_c2c b WHERE a.product_id = b.product_id AND a.product_onsale_model =1 AND a.product_status = 3 AND b.is_verify = 0 AND b.customer_service_id = ?";
			SqlRowSet r = jdbcOperations.queryForRowSet(sql, uid);
			while (r.next()) {
				i = r.getInt(1);
			}
			return i;
		}

	}

	@Override
	public List<ProductType> queryProductTypeListNoCache() {
		String sql = "select * from  product_type where status = 1 order by id asc";
		JuRowCallbackHandler<ProductType> cb = new JuRowCallbackHandler<ProductType>(ProductType.class);
		jdbcOperations.query(sql, cb);
		List<ProductType> productTypeList = cb.getList();
		return productTypeList;
	}

	@Override
	public ProductImages findImgesById(int imgId) {
		ProductImages result = null;
		String sql = "SELECT * FROM `product_images` WHERE `id`= ? ";
		JuRowCallbackHandler<ProductImages> cb = new JuRowCallbackHandler<ProductImages>(ProductImages.class);
		jdbcOperations.query(sql, cb, imgId);
		if (cb.getList().size() > 0) {
			result = cb.getList().get(0);
		}
		return result;
	}

	@Override
	public int productIdExist(String productId) {
		String sql = "SELECT product_id FROM product WHERE product_id = ?";
		SqlRowSet rs = jdbcOperations.queryForRowSet(sql, productId);
		while (rs.next()) {
			return 0;
		}
		return 1;
	}

	@Override
	public int updateProductGameArea(int gameId, int oldAreaId, int newAreaId) {
		String sql = "UPDATE product SET game_area_id = ? WHERE game_id = ? AND game_area_id = ? AND product_status = ?;";
		String sqlOnsale = "UPDATE product_onsale SET game_area_id = ? WHERE game_id = ? AND game_area_id = ? AND product_status = ?;";
		int i = jdbcOperations.update(sql, newAreaId, gameId, oldAreaId, ProductDao.PRODUCT_STATUC_ONSALE);
		System.out.println("servi == i == " + i);
		if (i > 0) {
			jdbcOperations.update(sqlOnsale, newAreaId, gameId, oldAreaId, ProductDao.PRODUCT_STATUC_ONSALE);
			return 0;
		} else {
			return 1;
		}

	}

	@Override
	public int updateC2CSellerAccountStatus(String encodeString) {
		// status 0：未验证 1：账号正常 2：账号异常
		String key = "s98slksdf2lfoi0slkdf2342lsdlkf4s";
		if (null == encodeString || "".equals(encodeString)) {
			return 4; // 加密字符串为空
		}
		String result = "";
		try {
			result = DES.decode(encodeString, key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 2; // 解密异常
		}

		if (!result.contains("##")) {
			return 3; // 解密后的字符串格式不对
		}
		String productId = "";
		String status = "";

		try {
			productId = result.split("##")[0];
			status = result.split("##")[1];
		} catch (Exception e) {
			e.printStackTrace();
			return 3;
		}

		String sql = "UPDATE `product_c2c` SET `account_verify` = ? WHERE `product_id`= ?";
		int i = jdbcOperations.update(sql, status, productId);

		return i ^ 1;
	}

	@Override
	public int insertProductC2c(ProductC2c c2c) {
		try {
			productDao.insertProductC2C(c2c);
		} catch (Exception e) {
			return 1;
		}
		return 0;
	}

	@Override
	public int updateC2C(ProductC2c c2c) {
		int i = 1;
		// 没有数据
		ProductC2c productc2c = productDao.findProductC2cByProductId(c2c.getProductId());
		if (null == productc2c) {
			return 1;
		}
		try {
			i = productDao.updateProductC2C(c2c);
		} catch (Exception e) {
			return 1;
		}
		return i;
	}

	@Override
	public int insertProductOnSale(String productId) {
		return productDao.insertProductOnSale(productId);
	}

	@Override
	public Product queryProductOnsaleByProductId(String productId) {
		Product p = null;
		p = productDao.queryProductOnsaleById(productId);
		return p;
	}

	@Override
	public int productOnsaleByMamage(int userid, String productId, String seelPid) {
		int i = productDao.productOnsale(userid, productId, seelPid);
		if (i == 0) {
			// 更新商品有效时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			Date day = new Date();
			Calendar newDay = Calendar.getInstance();
			newDay.setTime(day);
			newDay.add(newDay.DATE, 30);
			day = newDay.getTime();
			String datenow = sdf.format(day);

			String sql = "UPDATE `product` SET `product_validity` = ? WHERE product_id= ?";
			jdbcOperations.update(sql, datenow, productId);

			// 上架表如果已有数据 先删除
			productDao.delProductOnSale(productId);
			// 插入新数据
			productDao.insertProductOnSale(productId);
		}
		return i;
	}

	@Override
	public boolean checkRepeat(int uid, int gameid, String gameLoginName) {
		String sql = "SELECT * FROM product INNER JOIN product_c2c ON product.product_id = product_c2c.product_id AND (product.product_status = ? OR product.product_status = ?) AND product.product_type >= ? AND product.product_type < ? AND product.user_id = ? AND product.game_id = ? AND product_c2c.game_login_id = ?";
		SqlRowSet rs = jdbcOperations.queryForRowSet(sql, ProductDao.PRODUCT_STATUC_VERIFY, ProductDao.PRODUCT_STATUC_ONSALE, 200, 300, uid, gameid, gameLoginName);
		if (rs.next()) {
			return true;
		}
		return false;
	}

	@Override
	public int productOnsaleSql(List<String[]> sqlListarr) {

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(jdbcTemplate.getDataSource());
		TransactionStatus status = transactionManager.getTransaction(def);
		int result = 0;
		try {
			for (int i = 0; i < sqlListarr.size(); i++) {
				jdbcTemplate.batchUpdate(sqlListarr.get(i));
			}
			transactionManager.commit(status);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("出现事务异常");
			try {
				transactionManager.rollback(status);
			} catch (Exception e) {

			}
			return -1;
		}
		return result;
	}

}
