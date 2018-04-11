package cn.juhaowan.product.dao;

import java.util.List;
import java.util.Map;
import cn.jugame.dal.dao.BaseDao;
import cn.jugame.util.PageInfo;
import cn.juhaowan.product.vo.Product;
import cn.juhaowan.product.vo.ProductC2c;
import cn.juhaowan.product.vo.ProductImages;

/**
 * 
 * @author Administrator
 * 
 */
public interface ProductDao extends BaseDao<Object> {

	/**
	 * 操作类型 添加
	 */
	public static int OPERATE_INSERT = 1;

	/**
	 * 操作类型 更新
	 */
	public static int OPERATE_UPDATE = 2;

	/**
	 * 操作类型 删除
	 */
	public static int OPERATE_DELETE = 3;

	/**
	 * 操作类型 更改状态
	 */
	public static int OPERATE_MODIFY_STATUS = 4;

	/**
	 * 操作类型 上架
	 */
	public static int OPERATE_MODIFY_ONSALE = 5;
	/**
	 * 操作类型 下架
	 */
	public static int OPERATE_MODIFY_OFFSALE = 6;

	/**
	 * 操作类型 库存回滚
	 */
	public static int OPERATE_MODIFY_STOCK_ROLLBACK = 7;
	/**
	 * 操作类型 更新销售量
	 */
	public static int OPERATE_MODIFY_SOLDOUTNUM = 8;

	/**
	 * 操作类型 扣减库存
	 */
	public static int OPERATE_MODIFY_STOCK_DEDUCT = 9;

	/**
	 * 操作类型 c2c商品审核审核不通过
	 */
	public static int OPERATE_MODIFY_VERIFY_FAIL = 10;

	/**
	 * 操作类型 c2c商品审核审核通过
	 */
	public static int OPERATE_MODIFY_VERIFY_SUCC = 11;

	/**
	 * 商品状态：-1:删除
	 */
	public static int PRODUCT_STATUC_DEL = -1;
	/**
	 * 商品状态：1:暂存中 （草稿）
	 */
	public static int PRODUCT_STATUC_DRAFT = 1;
	
	/**
	 * 商品状态：3：待审核 （
	 */
	public static int PRODUCT_STATUC_VERIFY_WAIT = 3;

	/**
	 * 商品状态：4：审核中 （已发布）
	 */
	public static int PRODUCT_STATUC_VERIFY = 4;

	/**
	 * 商品状态：5:审核失败 （已发布）
	 */
	public static int PRODUCT_STATUC_VERIFY_FAIL = 5;

	/**
	 * 商品状态：7:出售中 （上架）
	 */
	public static int PRODUCT_STATUC_ONSALE = 7;

	/**
	 * 商品状态：8:用户下架 （下架）
	 */
	public static int PRODUCT_STATUC_USER_OFF = 8;

	/**
	 * 商品状态：9:已售完 （下架）
	 */
	public static int PRODUCT_STATUC_SOLDOUT = 9;

	/**
	 * 商品状态：10:游戏下架 （下架）
	 */
	public static int PRODUCT_STATUC_GAME_OFF = 10;

	/**
	 * 商品状态：11:已过期 （下架）
	 */
	public static int PRODUCT_STATUC_OUTDATE = 11;

	/**
	 * 商品状态：12:后台管理员 （下架）
	 */
	public static int PRODUCT_STATUC_MANAGEOFF = 12;

	/**
	 * 商品状态：13:下架失败
	 */
	public static int PRODUCT_STATUC_OFFFALIL = 13;

	/**
	 * 商品售卖模式 1:C2C
	 */
	public static int PRODUCT_ONSALE_MODEL_C2C = 1;

	/**
	 * 商品售卖模式 2:游戏内寄售(世界ol) product_onsale_model
	 */
	public static int PRODUCT_ONSALE_MODEL_SALE_ON_GAME = 2;

	/**
	 * 商品售卖模式 3:普通寄售(其他游戏) common
	 */
	public static int PRODUCT_ONSALE_MODEL_SALE_ON_COMMON = 3;

	/**
	 * 商品类型 暂时只有金币quick_deliver_flag
	 */
	public static int PRODUCT_TYPE_GOLD = 1;

	/**
	 * 商品类型 虚拟游戏币(类型ID小于100)
	 */
	public static int PRODUCT_TYPE_GOLD_RANGE = 100;

	/**
	 * 是否快速发货 0 否
	 */
	public static int QUICK_DELIVER_FALSE = 0;
	/**
	 * 是否快速发货 1 是
	 */
	public static int QUICK_DELIVER_TRUE = 1;

	/**
	 * 商品添加（前台）
	 * 
	 * @param product 商品实体
	 * @return 0：成功 1：失败
	 * @pdOid 061de976-2d58-4a8c-b123-8cbcb84b3347
	 */
	int insertProduct(Product product);

	/**
	 * 修改商品信息
	 * 
	 * @param product 商品实体
	 * @return 0：成功 1：失败
	 * @pdOid 595a7047-6be2-4523-a3a6-2706bfede6e3
	 */
	int updateProduct(Product product);

	/**
	 * 删除商品信息
	 * 
	 * @param productId 商品id
	 * @return 0：成功 1：失败
	 * @pdOid fcddab5d-640f-4ade-8eaa-ad1a8f127bef
	 */
	int deleteProduct(String productId);

	/**
	 * 按商品id查询 product表
	 * 
	 * @param productId 商品id
	 * @return 商品实体
	 * @pdOid bc43fc73-1132-40c5-a510-5690ff42572e
	 */
	Product queryByProductId(String productId);

	/**
	 * 按商品id查询 productonsale表
	 * 
	 * @param productId 商品id
	 * @return 商品实体
	 * @pdOid bc43fc73-1132-40c5-a510-5690ff42572e
	 */
	Product queryProductOnsaleById(String productId);

	/**
	 * 按条件查询分页
	 * 
	 * @param conditions 条件
	 * @param pageNo 首页
	 * @param pageSize 每页条数
	 * @param sort 排序字段
	 * @param order DESC/ASC
	 * @return 分页
	 * @pdOid 4a7afc63-56a5-4c58-81fc-2d394d68effc
	 */
	PageInfo<Product> queryPageInfo(Map<String, Object> conditions, int pageNo, int pageSize, String sort, String order);

	/**
	 * 修改商品权重
	 * 
	 * @param productId 商品id
	 * @param weight 商品权重
	 * @return 0 成功 1 失败
	 * @pdOid e1314d44-a1b4-457a-b1b9-52545398f790
	 */
	int modifyWeight(String productId, int weight);

	/**
	 * 商品状态 审核
	 * 
	 * @param productId 商品id
	 * @param status 商品状态
	 * @param remark 备注
	 * @return 0 成功 1 失败
	 * @pdOid 5aa15a1e-dc4a-441d-8d67-fbf3443fd484
	 */
	int verifyProductStatus(String productId, int status, String remark);

	/**
	 * 商品上架
	 * 
	 * @param userid 操作用户id
	 * @param productId 商品id
	 * @param seelPid 上架号
	 * @return 0 成功 1 失败
	 */
	int productOnsale(int userid, String productId, String seelPid);

	/**
	 * 商品下架
	 * 
	 * @param userid 用户id 系统自动下架userid = 1000
	 * @param productId 商品id
	 * @param status 9:已售完 （用户下架/系统下架）11:已过期 （系统下架）
	 * @param remark 备注
	 * @return 0 成功 1 失败
	 * @pdOid 73f642e8-9dc9-4cae-ac73-dbcd3e36f972
	 */
	int productOffShelves(int userid, String productId, int status, String remark);

	/**
	 * 商品下架(游戏下架)10:游戏下架 日志记录userid为-1
	 * 
	 * @param productId 商品id
	 * @param remark 备注
	 * @return -1： 商品不存在 -2： 商品状态已被游戏下架 -3： 商品不是上架状态 >0： 商品数量（库存*单件数量）
	 * @pdOid 73f642e8-9dc9-4cae-ac73-dbcd3e36f972
	 */
	long productGameOffShelves(String productId, String remark);

	/**
	 * 删除商品上架表 product_onsale
	 * 
	 * @param productId 商品id
	 * @return 0 成功 1 失败
	 */
	int delProductOnSale(String productId);

	/**
	 * 添加商品上架表product_onsale
	 * 
	 * @param productId 商品id
	 * @return 0 成功 1 失败
	 */
	int insertProductOnSale(String productId);

	/**
	 * =c2c 新增方法 添加商品扩展字段 [c2c ]
	 * 
	 * @param productId 商品id
	 * @param gameLogin 游戏登陆账号
	 * @param gamePassword 游戏登陆密码
	 * @param securityLock 游戏密码锁
	 * @param contactQQ 卖家联系qq
	 * @param goodsPosition 装备存放位置
	 * @return
	 */
	int insertProductC2c(String productId, String gameLogin, String gamePassword, String securityLock, String contactQQ, int goodsPosition);

	/**
	 * 根据商品id 查询商品扩展表 （账户信息内容）
	 * 
	 * @return
	 */
	ProductC2c findProductC2cByProductId(String productId);

	/**
	 * 根据商品id查询商品图片
	 * 
	 * @param productId 商品id
	 * @return 图片列表
	 */
	List<ProductImages> findImagesByProductId(String productId);

	/**
	 * 按条件查询分页(c2c上架审核列表)
	 * 
	 * @param conditions 条件
	 * @param pageNo 首页
	 * @param pageSize 每页条数
	 * @param sort 排序字段
	 * @param orderDESC/ASC
	 * @return 分页
	 */
	PageInfo<Map<String, Object>> queryC2CPageInfo(Map<String, Object> conditions, int pageNo, int pageSize, String sort, String order);

	/**
	 * 添加商品c2c
	 * 
	 * @param productc2c
	 * @return
	 */
	public int insertProductC2C(ProductC2c productc2c);

	/**
	 * 更新商品c2c
	 * 
	 * @param productc2c
	 * @return
	 */
	int updateProductC2C(ProductC2c productc2c);
}
