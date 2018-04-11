package cn.juhaowan.product.service;

import java.util.List;
import java.util.Map;

import cn.jugame.util.PageInfo;
import cn.juhaowan.product.vo.Product;
import cn.juhaowan.product.vo.ProductC2c;
import cn.juhaowan.product.vo.ProductImages;
import cn.juhaowan.product.vo.ProductLog;
import cn.juhaowan.product.vo.ProductType;

/**
 * 商品接口信息
 */
public interface ProductService {

	/**
	 * 修改商品信息 （后台）
	 * 
	 * @param product 商品
	 * @return 0 成功 1 失败
	 * @pdOid 595a7047-6be2-4523-a3a6-2706bfede6e3
	 */
	int updateProduct(Product product);

	/**
	 * 产生一个新的上架号，在原来的基础上加1 规则 : 商品编号-3位顺序号
	 * 
	 * @param productId 商品编号
	 * @param sellPid 旧的上架号
	 * @return 上架号
	 */
	String generationSellPid(String productId, String sellPid);

	/**
	 * 删除商品信息 返回值 0：成功 1：失败
	 * 
	 * @param userid 用户uid
	 * @param productId 商品id
	 * @return 0 成功 1 失败
	 * @pdOid fcddab5d-640f-4ade-8eaa-ad1a8f127bef
	 */
	int deleteProduct(int userid, String productId);

	/**
	 * 按商品id查询
	 * 
	 * @param productId 商品id
	 * @return 商品实体
	 * @pdOid bc43fc73-1132-40c5-a510-5690ff42572e
	 */
	Product queryByProductId(String productId);

	/**
	 * 商品编号生成规则：商品分类编码 + 发布年月日 + 8位日自增序号
	 * 
	 * @param productTypeCode 商品类型编码
	 * @return 商品编号
	 * @pdOid 449a024b-162d-4da8-80d2-029ac04c0152
	 */
	String generationProductId(String productTypeCode);

	/**
	 * //后台分页查询商品列表方法
	 * 
	 * @param productId 商品id
	 * @param userId 商品所属人id
	 * @param productStatus 商品状态
	 * @param gameId 所属游戏id
	 * @param gamePartitionId 所属游戏分区
	 * @param gameServerId 所属游戏服务器
	 * @param beginTime 开始时间
	 * @param endTime 结束时间
	 * @param pageNo 第几页
	 * @param pageSize 每页条数
	 * @param sort 排序条件
	 * @param order DESC/ASC
	 * @return 分页
	 */
	public PageInfo<Product> queryProductPageBack(String proTypeGroupStr,String productId, String userId, int productStatus, int gameId, int gamePartitionId, int gameServerId, String beginTime, String endTime, int proModel, int productType,String sonQudaoId, int pageNo, int pageSize, String sort,
			String order);

	/**
	 * 分页查询商品日志
	 * 
	 * @param productId 商品id
	 * @param operateUserid 操作用户id
	 * @param operateType 操作类型
	 * @param pageNo 首页
	 * @param pageSize 每页条数
	 * @param sort 排序字段
	 * @param order ASC/DESC
	 * @return 分页
	 */
	PageInfo<ProductLog> queryProductLog(String productId, int operateUserid, int operateType, int pageNo, int pageSize, String sort, String order);

	/**
	 * 商品添加（前台 保存草稿和发布商品）
	 * 
	 * @param product 商品
	 * @return 0 成功 1 失败
	 * @pdOid 061de976-2d58-4a8c-b123-8cbcb84b3347
	 */
	int insertProductFront(Product product);

	/**
	 * 商品上架
	 * 
	 * @param userid 用户uid
	 * @param productId 商品id
	 * @param seelPid 上架号
	 * @return 0 成功 1 失败
	 * @pdOid a15e0365-315c-4c65-a4b9-8a637ecf3b6f
	 */
	int productOnsale(int userid, String productId, String seelPid);

	/**
	 * 商品下架
	 * 
	 * @param userid 用户id
	 * @param productId 商品id
	 * @param status 商品状态
	 * @return 0 成功 1 失败
	 * @pdOid 73f642e8-9dc9-4cae-ac73-dbcd3e36f972
	 */

	int productOffShelves(int userid, String productId, int status);

	/**
	 * 商品下架(管理员下架)
	 * 
	 * @param productId 商品id
	 * @param status 12:管理员下架
	 * @param userid 用户id
	 * @param remark 备注
	 * @return 0 成功 1 失败
	 * @pdOid 73f642e8-9dc9-4cae-ac73-dbcd3e36f972
	 */
	int productAdminOffShelves(String productId, int status, int userid, String remark);

	/**
	 * 更新销售量
	 * 
	 * @param productId 商品id
	 * @param saleNum 售出数量
	 * @param orderId 订单id
	 * 
	 * @param buyUId 买家uid
	 * 
	 * @return 0 成功 1 失败
	 */
	int modifyProductSoldoutNumber(String productId, int saleNum, String orderId, int buyUId);

	/**
	 * 添加
	 * 
	 * @param productType 商品分类
	 * @return 0 成功 1 失败
	 */
	int insertProductType(ProductType productType);

	/**
	 * 修改
	 * 
	 * @param productType 商品分类
	 * @return 0 成功 1 失败
	 */
	int updateProductType(ProductType productType);

	/**
	 * 删除
	 * 
	 * @param id 商品分类id
	 * @return 0 成功 1 失败
	 */
	int deleteProductType(int id);

	/**
	 * 根据id查询商品类型
	 * 
	 * @param id 商品类型id
	 * @return 商品分类
	 */
	public ProductType findProductType(int id);

	/**
	 * 商品分类列表(带缓存)
	 * 
	 * @return 商品分类列表
	 */
	List<ProductType> queryProductTypeList();

	/**
	 * 商品库存修改
	 * 
	 * @param productId 商品id
	 * @param stock 库存
	 * @param orderId 订单id
	 * @param buyUId 买家uid
	 * @return 0 成功 1 失败
	 */
	int modifyProductStock(String productId, int stock, String orderId, int buyUId);

	/**
	 * 查询用户在售商品总价（库存*单价）
	 * 
	 * @param userId 用户id
	 * @return （库存*单价）
	 */
	double findUserOnsaleTotalPrice(int userId);

	/**
	 * 添加商品日志
	 * 
	 * @param productId 商品id
	 * @param operateType 操作类型 1 添加 2 更新 3 删除 (productDao 参数)
	 * @param userid 操作用户uid
	 * @param discretion 描述
	 * @param remark 备注
	 * @return 0 成功 1 失败
	 */
	public int addProductLog(String productId, int operateType, int userid, String discretion, String remark);

	/**
	 * =============================c2c 新添加方法 添加商品扩展字段 【c2c】
	 * 
	 * @param ProductC2c
	 * @return 0 成功 1 失败
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
	 * 根据商品图片id查询商品图片信息 （后台用）
	 * 
	 * @param imgId
	 * @return 商品图片
	 */
	ProductImages findImgesById(int imgId);

	/**
	 * //后台分页查询商品列表方法待审核商品列表
	 * 
	 * @param conditions 条件
	 * @param pageNo 第几页
	 * @param pageSize 每页条数
	 * @param sort 排序条件
	 * @param order DESC/ASC
	 * @return 分页
	 */
	public PageInfo<Map<String, Object>> queryProductC2CPageBack(Map<String, Object> conditions, int pageNo, int pageSize, String sort, String order);

	/**
	 * c2c 商品上架审核
	 * 
	 * @param productId 商品id
	 * @param status 审核状态
	 * @param remark 备注
	 * @param userid 审核用户uid
	 * @return 0 成功 1 失败
	 */
	int productC2COnsaleVerify(String productId, int status, String remark, int userid);

	/**
	 * 根据用户查找待审核的商品单数
	 * 
	 * @param uid 客服id uid等于0时 查询全部
	 * @return 数量
	 */
	int countWaitForVerify(int uid);

	/**
	 * 商品分类列表(后台)
	 * 
	 * @return 商品分类列表
	 */
	List<ProductType> queryProductTypeListNoCache();

	/**
	 * 判断商品ID是否已经存在
	 * 
	 * @param productId
	 * @return 0 存在 1 不存在
	 */
	int productIdExist(String productId);

	/**
	 * 商品游戏换区
	 * 
	 * @param gameId 游戏id
	 * @param oldAreaId 旧区
	 * @param newAreaId 新区
	 * @return
	 */
	int updateProductGameArea(int gameId, int oldAreaId, int newAreaId);

	/**
	 * 跟新商品c2c表验证卖家账号状态
	 * 
	 * @param encodeString 加密的字符串 格式：商品id##状态 ：PRO-20130607-19231411240##1 状态：status 0：未验证 1：账号正常 2：账号异常
	 * @return 0：更新成功 1：更新失败 2：解密异常 3：解密后的字符串格式不对 4：参数不能为空
	 */
	int updateC2CSellerAccountStatus(String encodeString);

	/**
	 * 添加c2c商品信息【接口用到】
	 * 
	 * @param c2c
	 * @return
	 */
	int insertProductC2c(ProductC2c c2c);

	/**
	 * 更新c2c商品信息
	 * 
	 * @param c2c
	 * @return
	 */
	int updateC2C(ProductC2c c2c);

	/**
	 * 添加商品上架表product_onsale
	 * 
	 * @param productId 商品id
	 * @return 0 成功 1 失败
	 */
	int insertProductOnSale(String productId);

	/**
	 * 按id查询商品详情(查询上架表 没缓存)
	 * 
	 * @param productId
	 * @return 商品
	 */
	public Product queryProductOnsaleByProductId(String productId);

	/**
	 * 后台用户上架商品
	 * 
	 * @param userid 后台用户uid
	 * @param productId 商品id
	 * @param seelPid 上架号
	 * @return 0 成功 1 失败
	 */
	int productOnsaleByMamage(int userid, String productId, String seelPid);

	/**
	 * 前台发布账号时判断该账号是否在该游戏内有出售
	 * 
	 * @param uid 上架用户id
	 * 
	 * @param gameid 上架商品所在游戏id
	 * 
	 * @param 上架游戏账号
	 * 
	 * @return true 有出售 false 没有出售 可以上架
	 * 
	 * **/
	public boolean checkRepeat(int uid, int gameid, String gameLoginName);

	/**
	 * 批量导入上架商品(新)
	 * 
	 * @param sqlListarr
	 * @return
	 */
	int productOnsaleSql(List<String[]> sqlListarr);

	/**
	 * 插入商品图片
	 * @param productId
	 * @param filePath
	 * @return 1成功，0失败
	 */
	int insertProductImages(String productId, String filePath);

	/**
	 * 后台上架退游账号时插入c2c产品表
	 * @param productId
	 * @param gameLogin
	 * @param gamePassword
	 * @param securityLock
	 * @param contactQQ
	 * @return
	 */
	int insertProductC2c(String productId, String gameLogin,
			String gamePassword, String securityLock, String contactQQ);

}
