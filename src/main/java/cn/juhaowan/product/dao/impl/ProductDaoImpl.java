package cn.juhaowan.product.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.dal.dao.impl.BaseDaoImplJdbc;
import cn.jugame.util.GamePasswdUtil;
import cn.jugame.util.PageInfo;
import cn.juhaowan.product.dao.ProductDao;
import cn.juhaowan.product.vo.Product;
import cn.juhaowan.product.vo.ProductC2c;
import cn.juhaowan.product.vo.ProductImages;

/**
 * 
 * @author Administrator
 * 
 */
@Repository("ProductDao")
public class ProductDaoImpl extends BaseDaoImplJdbc<Object> implements ProductDao {

	@Autowired
	private JdbcOperations jdbcOperations;

	// 添加商品信息
	@Override
	public int insertProduct(Product product) {

		insert(product);
		return 0;
	}

	// 更新商品信息
	@Override
	public int updateProduct(Product product) {
		if (null == product) {
			return 1;
		}

		update(product);

		return 0;
	}

	// 删除商品信息
	@Override
	public int deleteProduct(String productId) {
		if (null == productId) {
			return 1;
		}
		String sql = "update product set product_status = ?  where product_id = ? ";
		int i = jdbcOperations.update(sql, ProductDao.PRODUCT_STATUC_DEL, productId);

		return i ^ 1;
	}

	// 删除商品上架表信息
	@Override
	public int delProductOnSale(String productId) {

		if (null == productId) {
			return 1;
		}

		String sql = "delete from product_onsale where product_id = ? ";
		int i = jdbcOperations.update(sql, productId);

		return i ^ 1;
	}

	// 按商品id查询商品信息
	@Override
	public Product queryByProductId(String productId) {
		Product product = null;
		JuRowCallbackHandler<Product> cb = new JuRowCallbackHandler<Product>(Product.class);
		String sql = " select * from product  where product_id = ? ";
		jdbcOperations.query(sql, cb, productId);
		List<Product> productList = cb.getList();
		if (productList.size() > 0) {
			product = productList.get(0);
		}
		return product;
	}

	// 修改商品表权重
	@Override
	public int modifyWeight(String productId, int weight) {
		int i = 0;
		if (null == productId) {
			return 1;
		}
		String sql = "update product set product_weight = ?  where product_id = ?";
		i = jdbcOperations.update(sql, weight, productId);
		return i ^ 1;
	}

	// 商品信息审核
	@Override
	public int verifyProductStatus(String productId, int status, String remark) {
		int i = 0;
		if (null == productId) {
			return 1;
		}
		String sql = "update product set product_status = ? , product_remark = ?,verify_time = now() where product_id = ?";
		i = jdbcOperations.update(sql, status, remark, productId);

		return i ^ 1;
	}

	// 商品信息分页查询后台
	@SuppressWarnings("deprecation")
	@Override
	public PageInfo<Product> queryPageInfo(Map<String, Object> conditions, int pageNo, int pageSize, String sort, String order) {

		List<Object> conList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		String sqlCount = null;
		int count = 0;
		int firstResult = 1;

		if (null == sort) {
			sort = "create_time";
		}
		if (null == order) {
			order = "asc";
		}
		sql.append("from product where 1 = 1 ");

		if (null != conditions) {

			if (null != conditions.get("product_id")) {
				sql.append(" and product_id = ? ");
				conList.add(conditions.get("product_id"));
			}
			if (null != conditions.get("user_id")) {
				sql.append(" and user_id = ? ");
				conList.add(conditions.get("user_id"));
			}
			if (null != conditions.get("product_status")) {
				sql.append(" and product_status = ? ");
				conList.add(conditions.get("product_status"));
			}
			if (null != conditions.get("game_id")) {
				sql.append(" and game_id = ? ");
				conList.add(conditions.get("game_id"));
			}
			if (null != conditions.get("game_area_id")) {
				sql.append(" and game_area_id = ? ");
				conList.add(conditions.get("game_area_id"));
			}
			if (null != conditions.get("game_server_id")) {
				sql.append(" and game_server_id = ? ");
				conList.add(conditions.get("game_server_id"));
			}
			if (null != conditions.get("lowprice")) {
				sql.append(" and product_single_price >= ? ");
				conList.add(conditions.get("lowprice"));
			}
			if (null != conditions.get("hightprice")) {
				sql.append(" and product_single_price <= ? ");
				conList.add(conditions.get("hightprice"));
			}
			if (null != conditions.get("pro_model")) {
				sql.append(" and product_onsale_model = ? ");
				conList.add(conditions.get("pro_model"));
			}
			if (null != conditions.get("productType")) {
				sql.append(" and product_type = ? ");
				conList.add(conditions.get("productType"));
			}

			if (null != conditions.get("beginTime")) {
				sql.append(" and create_time >= ? ");
				conList.add(conditions.get("beginTime"));
			}
			if (null != conditions.get("endTime")) {
				sql.append(" and create_time <=  ? ");
				conList.add(conditions.get("endTime"));
			}
			if(null != conditions.get("son_qudao_id")){
				sql.append(" and son_channel_id =  ? ");
				conList.add(conditions.get("son_qudao_id"));
			}
			if(null != conditions.get("proTypeGroupStr") && !"".equals(conditions.get("proTypeGroupStr"))){
				sql.append(" and product_type in  (?) ");
				conList.add(conditions.get("proTypeGroupStr"));
			}

		}

		sql.append(" order by " + sort + " " + order);
		sqlCount = "select count(product_id) " + sql.toString().toString();
		count = jdbcOperations.queryForInt(sqlCount, conList.toArray());
		PageInfo<Product> pageinfo = new PageInfo<Product>("", count, pageSize);
		if (count == 0) {
			return pageinfo;
		}
		pageinfo.setPageno(pageNo);
		if (pageNo <= 0) {
			pageNo = 1;
		}
		if (pageNo > pageinfo.getPageCount()) {
			pageNo = pageinfo.getPageCount();
		}
		firstResult = (pageNo - 1) * pageinfo.getPageSize();

		sql.append(" limit " + firstResult + "," + pageinfo.getPageSize());

		String sqlPage = "select * " + sql.toString();
		JuRowCallbackHandler<Product> cb = new JuRowCallbackHandler<Product>(Product.class);

		jdbcOperations.query(sqlPage, cb, conList.toArray());
		pageinfo.setItems(cb.getList());

		return pageinfo;
	}

	// 商品上架
	@Override
	public int productOnsale(int userid, String productId, String seelPid) {
		if (userid < 0 || null == productId) {
			return 1;
		}
		// product表上线
		Product p = null;
		p = queryByProductId(productId);
		// 商品不存在
		if (null == p) {
			return 1;
		}

		String uSql = "update product set product_status = ? , sell_pid = ?,on_sale_time = now() where product_id = ?";
		int i = jdbcOperations.update(uSql, ProductDao.PRODUCT_STATUC_ONSALE, seelPid, productId);

		return i ^ 1;
	}

	// 商品下架
	@Override
	public int productOffShelves(int userid, String productId, int status, String remark) {
		if (userid < 0 || null == productId || status < 0) {
			return 1;
		}
		// product表下线
		Product p = null;
		p = queryByProductId(productId);
		// 商品信息不存在
		if (null == p) {
			return 1;
		}

		String uSql = "update product set product_status = ? ,off_shelves_time = now()," + "product_remark = ?  where product_id = ?";
		int i = jdbcOperations.update(uSql, status, remark, productId);

		return i ^ 1;

	}

	// 游戏内商品下架
	@Override
	public long productGameOffShelves(String productId, String remark) {
		long i = 0;
		if (null == productId) {
			return -1;
		}
		// product表下架
		Product p = new Product();
		p = queryByProductId(productId);
		// 商品不存在
		if (null == p) {
			return -1;
		}
		// 商品状态已被游戏下架 不用重复操作
		if (p.getProductStatus() == ProductDao.PRODUCT_STATUC_GAME_OFF) {
			return -2;
		}
		// 商品还没上架
		if (p.getProductStatus() != ProductDao.PRODUCT_STATUC_ONSALE) {
			return -3;
		}

		String uSql = "update product set product_status = ?,product_remark = ?,off_shelves_time = now() " + "where product_id = ?";
		int j = jdbcOperations.update(uSql, ProductDao.PRODUCT_STATUC_GAME_OFF, remark, productId);
		if (j > 0) {
			// 查询库存*单件数量
			i = p.getProductStock() * p.getProductSingleNumber();
		}
		return i;
	}

	// 按商品id查询商品上架表
	@Override
	public Product queryProductOnsaleById(String productId) {
		Product product = null;
		JuRowCallbackHandler<Product> cb = new JuRowCallbackHandler<Product>(Product.class);
		String sql = " select * from product_onsale  where product_id = ? ";
		jdbcOperations.query(sql, cb, productId);
		List<Product> productList = cb.getList();
		if (productList.size() > 0) {
			product = productList.get(0);
		}
		return product;
	}

	// 添加商品上架表
	@Override
	public int insertProductOnSale(String productId) {
		if (null == productId) {
			return 1;
		}
		String sql = "INSERT INTO product_onsale (SELECT * FROM product WHERE product_id = ?)";
		int i = jdbcOperations.update(sql, productId);

		return i ^ 1;
	}

	@Override
	public int insertProductC2c(String productId, String gameLogin, String gamePassword, String securityLock, String contactQQ, int goodsPosition) {
		String codeGameAccountPwd = GamePasswdUtil.endcode(gamePassword);
		String codesecurityLock = GamePasswdUtil.endcode(securityLock);

		String sql = "INSERT INTO product_c2c (`product_id`,`game_login_id`,`game_login_password`,`security_lock`,`contact_qq`,`goods_position`) " + " VALUES (?,?,?,?,?,?)";
		int i = jdbcOperations.update(sql, productId, gameLogin, codeGameAccountPwd, codesecurityLock, contactQQ, goodsPosition);
		return i ^ 1;
	}

	@Override
	public ProductC2c findProductC2cByProductId(String productId) {
		ProductC2c pcs = null;
		JuRowCallbackHandler<ProductC2c> cb = new JuRowCallbackHandler<ProductC2c>(ProductC2c.class);
		String sql = " select * from product_c2c  where product_id = ? ";
		jdbcOperations.query(sql, cb, productId);
		List<ProductC2c> list = cb.getList();
		if (list.size() > 0) {
			pcs = list.get(0);
		}
		return pcs;
	}

	@Override
	public List<ProductImages> findImagesByProductId(String productId) {
		List<ProductImages> imagesList = null;
		JuRowCallbackHandler<ProductImages> cb = new JuRowCallbackHandler<ProductImages>(ProductImages.class);
		String sql = " select * from product_images  where product_id = ? and status = 1";
		jdbcOperations.query(sql, cb, productId);
		List<ProductImages> list = cb.getList();
		if (list.size() > 0) {
			imagesList = list;
		}
		return imagesList;
	}

	// 商品信息分页查询后台
	@SuppressWarnings("deprecation")
	@Override
	public PageInfo<Map<String, Object>> queryC2CPageInfo(Map<String, Object> conditions, int pageNo, int pageSize, String sort, String order) {

		List<Object> conList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		String sqlCount = null;
		int count = 0;
		int firstResult = 1;

		if (null == sort) {
			sort = "a.create_time";
		}
		if (null == order) {
			order = "asc";
		}
		sql.append(" from (SELECT a.`product_id`,a.`product_onsale_model`,a.`create_time`,a.`user_id`,a.`product_type`,a.`product_name`,");
		sql.append(" a.`product_single_number`,a.`product_stock`,a.`product_single_price`,a.`game_id`,a.`game_area_id`,a.`game_server_id`,a.`product_status`,");
		sql.append(" a.`user_nickName`,b.`customer_service_id`,b.`is_verify`,b.`assign_time`,a.product_original_price FROM product a ,product_c2c b WHERE a.`product_id`=b.`product_id`   ");
		// sql.append(" WHERE 1 = 1 ");

		if (null != conditions) {

			if (null != conditions.get("product_id")) {
				sql.append(" and a.product_id = ? ");
				conList.add(conditions.get("product_id"));
			}
			if (null != conditions.get("user_id")) {
				sql.append(" and a.user_id = ? ");
				conList.add(conditions.get("user_id"));
			}
			if (null != conditions.get("product_status")) {
				sql.append(" and (a.product_status = ? or a.product_status = 4)");
				conList.add(conditions.get("product_status"));
			}
			if (null != conditions.get("game_id")) {
				sql.append(" and a.game_id = ? ");
				conList.add(conditions.get("game_id"));
			}
			if (null != conditions.get("game_area_id")) {
				sql.append(" and a.game_area_id = ? ");
				conList.add(conditions.get("game_area_id"));
			}
			if (null != conditions.get("game_server_id")) {
				sql.append(" and a.game_server_id = ? ");
				conList.add(conditions.get("game_server_id"));
			}
			if (null != conditions.get("lowprice")) {
				sql.append(" and a.product_single_price >= ? ");
				conList.add(conditions.get("lowprice"));
			}
			if (null != conditions.get("hightprice")) {
				sql.append(" and a.product_single_price <= ? ");
				conList.add(conditions.get("hightprice"));
			}
			if (null != conditions.get("proModel")) {
				sql.append(" and a.product_onsale_model = ? ");
				conList.add(conditions.get("proModel"));
			}
			if (null != conditions.get("customer_service_id")) {
				sql.append(" and b.customer_service_id = ? ");
				conList.add(conditions.get("customer_service_id"));
			}

			if (null != conditions.get("beginTime")) {
				sql.append(" and a.create_time >= ? ");
				conList.add(conditions.get("beginTime"));
			}
			if (null != conditions.get("endTime")) {
				sql.append(" and a.create_time <=  ? ");
				conList.add(conditions.get("endTime"));
			}

		}

		sql.append(" order by " + sort + " " + order + ")bb");
		sqlCount = "select count(bb.product_id)  " + sql.toString().toString();
		count = jdbcOperations.queryForInt(sqlCount, conList.toArray());
		PageInfo<Map<String, Object>> pageinfo = new PageInfo<Map<String, Object>>(count, pageSize);
		if (count == 0) {
			return pageinfo;
		}
		pageinfo.setPageno(pageNo);
		if (pageNo <= 0) {
			pageNo = 1;
		}
		if (pageNo > pageinfo.getPageCount()) {
			pageNo = pageinfo.getPageCount();
		}
		firstResult = (pageNo - 1) * pageinfo.getPageSize();
		sql.append(" limit " + firstResult + "," + pageinfo.getPageSize());
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		listMap = this.jdbcTemplate.queryForList("select * " + sql.toString(), conList.toArray());
		pageinfo.setItems(listMap);

		return pageinfo;
	}

	// 添加商品c2c信息
	@Override
	public int insertProductC2C(ProductC2c productc2c) {
		insert(productc2c);
		return 0;
	}

	@Override
	public int updateProductC2C(ProductC2c productc2c) {
		if (null == productc2c) {
			return 1;
		}
		update(productc2c);
		return 0;
	}

}
