package cn.jugame.web.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import cn.jugame.util.GamePasswdUtil;

/**
 * 操作Excel表格的功能类
 */
public class ReadXlsUtils {

	private Workbook workbook = null;

	public void init(InputStream is) {
		try {
			workbook = Workbook.getWorkbook(is);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 将xls文件按照指定的sql语句模板转换成sql脚本。(不用)
	 * 
	 * @param xlsFile
	 *            xls文件路径
	 * @param sqlFile
	 *            要输出的sql脚本文件路径
	 * @param template
	 *            sql语句模板
	 * @param sheetIndex
	 *            xls中标签页的序号(从0开始)
	 * @param start
	 *            转换开始的行数 (从0开始)
	 * @param end
	 *            转换结束的行数 (从0开始)
	 * @throws IOException
	 * @throws BiffException
	 */
	@SuppressWarnings("unchecked")
	public void convertRecharge(String sqlFile, String template, int sheetIndex,int start, int end) throws IOException, BiffException {
		// 获取工作薄
		Sheet sheet = workbook.getSheet(sheetIndex);
		System.out.println(sheet.getRows());
		// 获取sql中的所有字段点位符
		Matcher matcher = Pattern.compile("(:---\\d{1,2}:---)").matcher(template);
		List columns = new ArrayList();
		if (sheet.getRows() == 0) {// 判断sheet是否为空
			System.out.println("Sheet为空!");

		} else {
			// 通用的获取cell值的方式,getCell(int column, int row) 行和列
			int rows = sheet.getRows();// 总行
			int cols = sheet.getColumns();// 总列
			String[][] str = new String[rows][cols];
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					str[i][j] = (sheet.getCell(j, i)).getContents();// getCell(Col,Row)获得单元格的值
					System.out.print(str[i][j] + "\t");
				}
				System.out.print("\n");
			}
		}

		while (matcher.find()) {
			columns.add(Integer.valueOf(matcher.group().replace(":---", "")));

		}

		// 输出sql脚本文件
		PrintWriter writer = new PrintWriter(new FileWriter(sqlFile));
		for (int i = 0; i < sheet.getRows(); i++) {
			if (i >= start && i <= end) {
				// 组装sql语句
				String line = new String(template);
				for (int k = 0; k < columns.size(); k++) {
					String content = "";
					int index = Integer.parseInt(columns.get(k).toString());
					content = sheet.getCell(Integer.parseInt(columns.get(k).toString()),i).getContents().trim();

					if (index == 19 && null != content && !"　".equals(content)) {
						content = GamePasswdUtil.endcode(content);
					}

					line = line.replace(":---" + columns.get(k) + ":---",content);

				}
				writer.println(line);
			} else {
				continue;
			}
		}
		writer.flush();
		writer.close();
	}

	/**
	 * 获得商品表和上架表sql
	 * 
	 * @param table
	 * @return
	 */
	public static String getProductSql(String table) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date day = new Date();
		Calendar newDay = Calendar.getInstance();
		newDay.setTime(day);
		newDay.add(newDay.DATE, 365);
		day = newDay.getTime();
		String datenow = sdf.format(day);
		String sql = "INSERT INTO `"
				+ table
				+ "`(`product_id`,`product_name`,`product_subtitle`,`product_type`,"
				+ " `product_single_number`,`product_soldout_number`,`product_single_price`,`product_stock`,`product_info`,"
				+ " `product_onsale_model`, `product_status`,`product_remark`,`product_weight`,`no_product_flag`,`product_validity`,"
				+ " `user_id`,`game_id`,`game_area_id`,`game_server_id`,`game_uid`,`game_role_id`,`game_role_name`,`game_user_lever`,"
				+ " `create_time`,`modify_time`,`on_sale_time`,`off_shelves_time`,`final_transaction_time`,`verify_time`,`sell_pid`,"
				+ " `unit`,`soldout_time`,`user_nickName`,`channel_id`,`son_channel_id`,`quick_deliver_flag`,product_original_price,`is_show`)"
				+ "VALUES (':---0:---'," // product_id
				+ "':---1:---'," // product_name
				+ "''," // product_subtitle
				+ ":---2:---," // product_type
				+ "':---3:---'," // product_single_number
				+ "'0'," // product_soldout_number
				+ "':---4:---'," // product_single_price
				+ "':---5:---'," // product_stock
				+ "':---6:---'," // product_info
				+ "':---7:---'," // product_onsale_model
				+ "7," // product_status
				+ "''," // product_remark
				+ "':---8:---'," // product_weight
				+ "':---9:---'," // no_product_flag
				+ "'" + datenow + "'," // product_validity
				+ "':---10:---'," // user_id
				+ "':---11:---'," // game_id
				+ "':---12:---'," // game_area_id
				+ "':---13:---'," // game_server_id
				+ "'10000'," // game_uid
				+ "'10000'," // game_role_id
				+ "'10000'," // game_role_name
				+ "'11'," // game_user_lever
				+ "now()," // create_time
				+ "now()," // modify_time
				+ "now()," // on_sale_time
				+ "NULL," // off_shelves_time
				+ "NULL," // final_transaction_time
				+ "now()," // verify_time
				+ "''," // sell_pid
				+ "':---14:---'," // unit
				+ "NULL," // soldout_time
				+ "':---15:---'," // user_nickName
				+ "':---16:---'," // channel_id
				+ "':---17:---'," // son_channel_id
				+ "':---22:---'," // quick_deliver_flag
				+ "':---23:---',"// product_original_price
		        + "':---24:---');"; // is_show

		return sql;
	}

	public static String getProductC2cSql() {
		String sql = "INSERT INTO `product_c2c`(`product_id`,`game_login_id`,`game_login_password`,`security_lock`,"
				+ "  `contact_qq`, `customer_service_id`,`assign_time`,`is_verify`, `goods_position`)"
				+ " VALUES (':---0:---'," // product_id
				+ " ':---18:---'," // game_login_id
				+ " ':---19:---',"// game_login_password
				+ " ':---20:---'," // security_lock
				+ " ':---21:---'," // contact_qq
				+ " '0'," // customer_service_id
				+ " now()," // assign_time
				+ " 1," // is_verify
				+ " '4');"; // goods_position
		return sql;
	}

	// 更新商品分区id
	public static String updateProductGameAreaSql(String table) {
		String sql = "UPDATE "
				+ table
				+ " SET `game_area_id` = ':---2:---'  WHERE product_id = ':---0:---';";
		return sql;
	}

	// 添加游戏分区表
	public static String getGameAreaSql(int gameId,int versionId) {
		String sql = "INSERT INTO `game_partition`(`game_id`,`partition_name`, `weight`,`status`, `is_ty`,`area_coefficient`,`currency_ratio`,game_version_id)"
				+ "VALUES (" // game_partition_id
				+ gameId + "," // game_id
				+ " ':---0:---'," // partition_name
				+ " ':---1:---'," // weight
				+ " ':---2:---'," // status
				+ " ':---3:---'," // is_ty
				+ " ':---4:---'," // area_coefficient
				+ " ':---5:---',"
				+versionId+");"; // currency_ratio
		return sql;
	}
	// 添加骏网充值卡信息
	public static String getRechargeCardSql(){
		String sql = "INSERT INTO `rechargeable_card`(`number`,`password`,`denomination`,type,`create_time`,balance,operation_type,`original_price`)"
				+ "VALUES (" 
				+ " ':---0:---'," // number
				+ " ':---1:---'," // password
				+ " ':---2:---'," // denomination
				+ " ':---3:---'," // type
				+"now()," // create_time
		        + " ':---4:---'," // type
			    + " ':---5:---',"
			    + " ':---6:---')"; // type
		return sql;
	}
	
	// 添加骏网充值卡日志
	public static String getRechargeCardLogSql(String remark,int oprator){
		String sql = "INSERT INTO `rechargeable_card_log`(`card_num`,`remark`,`oprator`,`create_time`)"
				+ "VALUES (" 
				+ " ':---0:---'," // number
				+ "'"+remark+"'," // password
				+ oprator+"," // denomination
				+"now());"; // create_time
		return sql;
	}

	/**
	 * 将xls文件按照指定的sql语句模板转换成sql脚本。
	 * 
	 * @param xlsFile
	 *            xls文件路径
	 * @param template
	 *            sql语句模板
	 * @param sheetIndex
	 *            xls中标签页的序号(从0开始)
	 * @param start
	 *            转换开始的行数 (从0开始)
	 * @param end
	 *            转换结束的行数 (从0开始)
	 * @throws IOException
	 * @throws BiffException
	 */
	public List<String> convert(String template, int sheetIndex, int start,int end) throws IOException, BiffException {
		// 获取工作薄
		Sheet sheet = workbook.getSheet(sheetIndex);
		System.out.println(sheet.getRows());
		// 获取sql中的所有字段点位符
		Matcher matcher = Pattern.compile("(:---\\d{1,2}:---)").matcher(
				template);
		List columns = new ArrayList();

		while (matcher.find()) {
			columns.add(Integer.valueOf(matcher.group().replace(":---", "")));

		}
		// 输出sql脚本文件list
		List<String> result = new ArrayList<String>();
		int rowCount = 0;
		for (int i = 0; i < sheet.getRows(); i++) {
			if (i >= start && i <= end) {
				// 组装sql语句
				String line = new String(template);
				for (int k = 0; k < columns.size(); k++) {
					String content = "";
					int index = Integer.parseInt(columns.get(k).toString());
					content = sheet.getCell(Integer.parseInt(columns.get(k).toString()),i).getContents().trim();

					if (index == 19 && null != content && !"　".equals(content)) {
						content = GamePasswdUtil.endcode(content);
					}
					if (index == 20 && null != content && !"　".equals(content)) {
						content = GamePasswdUtil.endcode(content);
					}

					line = line.replace(":---" + columns.get(k) + ":---",content);

				}
				result.add(line);
				rowCount++;
			} else {
				continue;
			}
		}
		return result;
	}
	
	
	public List<String> convertRecharge(String template, int sheetIndex, int start,int end) throws IOException, BiffException {
		// 获取工作薄
		Sheet sheet = workbook.getSheet(sheetIndex);
		System.out.println(sheet.getRows());
		// 获取sql中的所有字段点位符
		Matcher matcher = Pattern.compile("(:---\\d{1,2}:---)").matcher(
				template);
		List columns = new ArrayList();

		while (matcher.find()) {
			columns.add(Integer.valueOf(matcher.group().replace(":---", "")));

		}
		// 输出sql脚本文件list
		List<String> result = new ArrayList<String>();
		int rowCount = 0;
		for (int i = 0; i < sheet.getRows(); i++) {
			if (i >= start && i <= end) {
				// 组装sql语句
				String line = new String(template);
				for (int k = 0; k < columns.size(); k++) {
					String content = "";
					int index = Integer.parseInt(columns.get(k).toString());
					content = sheet.getCell(Integer.parseInt(columns.get(k).toString()),i).getContents().trim();

					if (index == 1 && null != content && !"　".equals(content)) {
						content = GamePasswdUtil.endcode(content);
					}

					line = line.replace(":---" + columns.get(k) + ":---",content);

				}
				result.add(line);
				rowCount++;
			} else {
				continue;
			}
		}
		return result;
	}

	/**
	 * 读取商品id列表
	 * 
	 * @param xlsFile
	 *            xls文件路径
	 * @param sheetIndex
	 *            xls中标签页的序号(从0开始)
	 * @param start
	 *            转换开始的行数 (从0开始)
	 * @param end
	 *            转换结束的行数 (从0开始)
	 * @throws IOException
	 * @throws BiffException
	 */
	public List<String> readProductIdList(int sheetIndex, int start, int end)
			throws IOException, BiffException {
		// 获取工作薄
		Sheet sheet = workbook.getSheet(sheetIndex);

		List<String> result = new ArrayList<String>();
		if (sheet.getRows() == 0) { // 判断sheet是否为空
			// System.out.println("Sheet为空!");

		} else {
			// 通用的获取cell值的方式,getCell(int column, int row) 行和列
			int rows = sheet.getRows();// 总行
			int cols = sheet.getColumns();// 总列
			String[][] str = new String[rows][cols];
			// 只导出商品id
			for (int i = 1; i < rows; i++) {

				str[i][0] = (sheet.getCell(0, i)).getContents();// getCell(Col,Row)获得单元格的值
				result.add(str[i][0]);

			}
		}
		return result;
	}

	/**
	 * 读取xls指定数据
	 * 
	 * @param xlsFile
	 *            xls文件路径
	 * @param sheetIndex
	 *            xls中标签页的序号(从0开始)
	 * @param row
	 *            行 0开始
	 * @param col
	 *            列 0开始
	 * @throws IOException
	 * @throws BiffException
	 */
	public String readProductTitle(int sheetIndex, int row, int col)
			throws Exception {
		// 获取工作薄
		Sheet sheet = workbook.getSheet(sheetIndex);

		String result = "";
		if (sheet.getRows() == 0) {// 判断sheet是否为空
			return result;

		} else {
			// 通用的获取cell值的方式,getCell(int column, int row) 行和列
			int rows = sheet.getRows();// 总行
			int cols = sheet.getColumns();// 总列
			// 行列数超出实际数据 返回""
			if (row > rows || col > cols) {
				return result;
			}
			result = (sheet.getCell(col, row)).getContents();// getCell(Col,Row)获得单元格的值
		}
		return result;
	}

	/**
	 * 读取商品xls 列数
	 * 
	 * @param xlsFile
	 *            xls文件路径
	 * @param sheetIndex
	 *            xls中标签页的序号(从0开始)
	 * @throws IOException
	 * @throws BiffException
	 */
	public int readXlsCols(int sheetIndex) throws IOException, BiffException {
		// 获取工作薄
		Sheet sheet = workbook.getSheet(sheetIndex);
		int cols = sheet.getColumns();// 总列
		return cols;
	}

	/**
	 * 读取商品xls 行数
	 * 
	 * @param xlsFile
	 *            xls文件路径
	 * @param sheetIndex
	 *            xls中标签页的序号(从0开始)
	 * @throws IOException
	 * @throws BiffException
	 */
	public int readXlsRows(int sheetIndex) throws IOException, BiffException {
		// 获取工作薄
		Sheet sheet = workbook.getSheet(sheetIndex);
		int rows = sheet.getRows();// 总行
		return rows;
	}

	/**
	 * 返回xls数据空数据位置
	 * 
	 * @param xlsFile
	 *            xls文件路径
	 * @param sheetIndex
	 *            xls中标签页的序号(从0开始)
	 * @param col
	 *            要判断为空的列
	 * @throws IOException
	 * @throws BiffException
	 */
	public String readXlsNull(int sheetIndex, int[] col) throws IOException,
			BiffException {
		// 获取工作薄
		Sheet sheet = workbook.getSheet(sheetIndex);

		String result = "";
		if (sheet.getRows() == 0) {// 判断sheet是否为空
			return result;

		} else {
			// 通用的获取cell值的方式,getCell(int column, int row) 行和列
			int rows = sheet.getRows();// 总行
			int cols = sheet.getColumns();// 总列
			String[][] str = new String[rows][cols];
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					str[i][j] = (sheet.getCell(j, i)).getContents();// getCell(Col,Row)获得单元格的值
					if ("".equals(str[i][j])) {
						for (int k = 0; k < col.length; k++) {
							if (j == (col[k] - 1)) {
								result = "第" + (i + 1) + "行,第" + (j + 1)
										+ "列数据不能为空,请修改xls文件重新上传";
								return result;
							}
						}

					}
				}

			}
		}
		return result;
	}

	/**
	 * 返回xls数据不是数字位置
	 * 
	 * @param xlsFile
	 *            xls文件路径
	 * @param sheetIndex
	 *            xls中标签页的序号(从0开始)
	 * @param col
	 *            要判断为空的列
	 * @throws IOException
	 * @throws BiffException
	 */
	public String readisNotNum(int sheetIndex, int[] col) throws IOException,
			BiffException {
		// 获取工作薄
		Sheet sheet = workbook.getSheet(sheetIndex);

		String result = "";
		if (sheet.getRows() == 0) {// 判断sheet是否为空
			return result;

		} else {
			// 通用的获取cell值的方式,getCell(int column, int row) 行和列
			int rows = sheet.getRows();// 总行
			int cols = sheet.getColumns();// 总列
			String[][] str = new String[rows][cols];
			for (int i = 1; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					str[i][j] = (sheet.getCell(j, i)).getContents();// getCell(Col,Row)获得单元格的值
					int num = 0;
					try {
						num = Integer.parseInt(str[i][j]);
					} catch (NumberFormatException e) {
						num = -1;
					}
					for (int k = 0; k < col.length; k++) {
						if (j == (col[k] - 1) && num == -1) {
							result = "第" + (i + 1) + "行,第" + (j + 1)
									+ "列数据必须为数字,请修改xls文件重新上传";
							return result;
						}
					}

				}

			}
		}
		return result;
	}

	/**
	 * 返回xls数据不是浮点型位置
	 * 
	 * @param xlsFile
	 *            xls文件路径
	 * @param sheetIndex
	 *            xls中标签页的序号(从0开始)
	 * @param col
	 *            要判断为空的列
	 * @throws IOException
	 * @throws BiffException
	 */
	public String readisNotFloat(int sheetIndex, int[] col) throws IOException,
			BiffException {
		// 获取工作薄
		Sheet sheet = workbook.getSheet(sheetIndex);

		String result = "";
		if (sheet.getRows() == 0) {// 判断sheet是否为空
			return result;

		} else {
			// 通用的获取cell值的方式,getCell(int column, int row) 行和列
			int rows = sheet.getRows();// 总行
			int cols = sheet.getColumns();// 总列
			String[][] str = new String[rows][cols];
			for (int i = 1; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					str[i][j] = (sheet.getCell(j, i)).getContents();// getCell(Col,Row)获得单元格的值
					float num = 0;
					try {
						num = Float.parseFloat(str[i][j]);
					} catch (Exception e) {
						num = -1;
					}
					for (int k = 0; k < col.length; k++) {
						if (j == (col[k] - 1) && num == -1) {
							result = "第" + (i + 1) + "行,第" + (j + 1)
									+ "列数据必须为价格数字,请修改xls文件重新上传";
							return result;
						}
					}

				}

			}
		}
		return result;
	}

	/**
	 * 返回xls数据字符串长度是否超长
	 * 
	 * @param xlsFile
	 *            xls文件路径
	 * @param sheetIndex
	 *            xls中标签页的序号(从0开始)
	 * @param col
	 *            要判断为空的列
	 * @param length
	 *            长度限制
	 * @throws IOException
	 * @throws BiffException
	 */
	public String readStrLength(int sheetIndex, int[] col, int length)
			throws IOException, BiffException {
		// 获取工作薄
		Sheet sheet = workbook.getSheet(sheetIndex);

		String result = "";
		if (sheet.getRows() == 0) {// 判断sheet是否为空
			return result;

		} else {
			// 通用的获取cell值的方式,getCell(int column, int row) 行和列
			int rows = sheet.getRows();// 总行
			int cols = sheet.getColumns();// 总列
			String[][] str = new String[rows][cols];
			for (int i = 1; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					str[i][j] = (sheet.getCell(j, i)).getContents();// getCell(Col,Row)获得单元格的值
					for (int k = 0; k < col.length; k++) {
						if (j == (col[k] - 1)) {
							if (str[i][j].length() != length) {
								result = "第" + (i + 1) + "行,第" + (j + 1) + "列数据长度必须是" + length + "位";
								return result;
							}
						}
					}

				}

			}
		}
		return result;
	}
	
	/**
	 * 返回xls数据字符串长度是否超长
	 * 
	 * @param xlsFile
	 *            xls文件路径
	 * @param sheetIndex
	 *            xls中标签页的序号(从0开始)
	 * @param col
	 *            要判断为空的列
	 * @param length
	 *            长度限制
	 * @throws IOException
	 * @throws BiffException
	 */
	public String readStrMaxLength(int sheetIndex, int[] col, int length)
			throws IOException, BiffException {
		// 获取工作薄
		Sheet sheet = workbook.getSheet(sheetIndex);

		String result = "";
		if (sheet.getRows() == 0) {// 判断sheet是否为空
			return result;

		} else {
			// 通用的获取cell值的方式,getCell(int column, int row) 行和列
			int rows = sheet.getRows();// 总行
			int cols = sheet.getColumns();// 总列
			String[][] str = new String[rows][cols];
			for (int i = 1; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					str[i][j] = (sheet.getCell(j, i)).getContents();// getCell(Col,Row)获得单元格的值
					for (int k = 0; k < col.length; k++) {
						if (j == (col[k] - 1)) {
							if (str[i][j].length() > length) {
								result = "第" + (i + 1) + "行,第" + (j + 1) + "列数据长度不能超过" + length + "位";
								return result;
							}
						}
					}

				}

			}
		}
		return result;
	}

	public static String FormetFileSize(long fileS) { // 转换文件大小
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";

		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

	/**
	 * 读取指定列数据
	 * 
	 * @param xlsFile
	 *            xls文件路径
	 * @param sheetIndex
	 *            xls中标签页的序号(从0开始)
	 * @param start
	 *            转换开始的行数 (从0开始)
	 * @param end
	 *            转换结束的行数 (从0开始)
	 * @throws IOException
	 * @throws BiffException
	 */
	public List<String> readColList(int sheetIndex, int col, int start, int end)
			throws IOException, BiffException {
		// 获取工作薄
		Sheet sheet = workbook.getSheet(sheetIndex);

		List<String> result = new ArrayList<String>();
		if (sheet.getRows() == 0) { // 判断sheet是否为空
			// System.out.println("Sheet为空!");

		} else {
			// 通用的获取cell值的方式,getCell(int column, int row) 行和列
			int rows = sheet.getRows();// 总行
			int cols = sheet.getColumns();// 总列
			String[][] str = new String[rows][cols];
			// 只导出商品id

			for (int i = 1; i < rows; i++) {

				str[i][col] = (sheet.getCell(col, i)).getContents();// getCell(Col,Row)获得单元格的值
				result.add(str[i][col]);

			}
		}
		return result;
	}

	/**
	 * 测试方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		String xlsFile = "d://out//product_test.xls";
		String productFile = "d:\\ttt\\product_out.sql";
		String productonsaleFile = "d:\\ttt\\product_onsale_out.sql";
		String productc2cFile = "d:\\ttt\\product_c2c_out.sql";

		try {
			// 导出文件
			// // 商品表
			// ReadProductXlsUtils.convert(xlsFile, productFile,
			// getProductSql("product"), 0, 1,1500);
			// // 商品上架表
			// ReadProductXlsUtils.convert(xlsFile,
			// productonsaleFile,getProductSql("product_onsale"), 0, 1, 1500);
			// // 商品c2c表
			// ReadProductXlsUtils.convert(xlsFile, productc2cFile,
			// getProductC2cSql(), 0, 1, 1500);

			// ---------导出list
			// 商品表
			// List<String> list = ReadProductXlsUtils.convert(xlsFile,
			// getProductSql("product"), 0, 1,1500);
			// System.out.println(list.get(10));
			// 列出所有商品id
			// List<String> list2 =
			// ReadProductXlsUtils.readProductIdList(xlsFile, 0, 1, 15000);
			// for(int i = 0;i < list2.size();i++){
			// System.out.println("======" + list2.get(i));
			// }
			// String r = ReadProductXlsUtils.readProductTitle(xlsFile, 0, 0,1);
			// System.out.println("===" + r);
			// 需要判断非空的字段
			// int [] i = {1,2,3,4,5,6,8,9,10,12,13,14,15,19,24,29,31};
			// String st = ReadProductXlsUtils.readXlsNull(xlsFile, 0,i);
			// System.out.println("+++++" + st);
			// 需要判断数字的字段
			// int [] i = {3,4,5,6,8,9,10,12,13,14,15,19,24,29,31};
			// String ss = ReadProductXlsUtils.readisNotNum(xlsFile, 0, i);
			// System.out.println("====" + ss);

			// 商品上架表
			// ReadProductXlsUtils.convert(xlsFile,getProductSql("product_onsale"),
			// 0, 1, 1500);
			// 商品c2c表
			// ReadProductXlsUtils.convert(xlsFile, getProductC2cSql(), 0, 1,
			// 1500);

			// 更新商品游戏服务器sql
			// System.out.println("===" +
			// ReadProductXlsUtils.updateProductGameAreaSql("product"));

			// ReadProductXlsUtils.convert(xlsFile,
			// productFile,updateProductGameAreaSql("product"), 0, 1, 1500);

			// List<String> r = ReadProductXlsUtils.readColList(xlsFile, 0,3,
			// 0,1);
			// System.out.println("===" + r);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}