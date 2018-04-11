package cn.juhaowan.customer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import cn.jugame.util.SpringFactory;
import cn.juhaowan.util.Helpers;
import cn.juhaowan.util.XObject;
import cn.juhaowan.util.XProperty;

public class D {
	
	static Logger logger = LoggerFactory.getLogger(D.class);
	
	private String table = "";
	
	@Autowired
	private JdbcOperations jdbc;
	
	private static String default_source;
	public static void set_default_source(String default_source){
		D.default_source = default_source;
	}
	
	private String last_sql = "";
	private Object[] last_params = null;
	
	private void save_last_query(String sql, Object[] params){
		this.last_sql = sql;
		this.last_params = params;
	}
	
	public D(String tbl){
		this.table = tbl;
		String source = D.default_source;
		
		if(!StringUtils.isBlank(source)){
			this.jdbc = SpringFactory.getBean(source);
		}
	}
	public D(String tbl, String source){
		this.table = tbl;
		
		if(!StringUtils.isBlank(source)){
			this.jdbc = SpringFactory.getBean(source);
		}
	}
	
	public D(String tbl, JdbcOperations jdbc){
		this.table = tbl;
		this.jdbc = jdbc;
	}
	
	//只是为了方便
	private static Map<String, D> cache = new TreeMap<String, D>();
	public static D proxy(String tbl, JdbcOperations jdbc){
		D db = cache.get(tbl);
		if(db == null){
			db = new D(tbl, jdbc);
			cache.put(tbl, db);
		}
		return db;
	}
	
	private String build_where_statement(XProperty p){
		String key = p.getKey().trim();
		if(key.endsWith("=") || key.endsWith(">") || key.endsWith("<")){
			String[] ss = key.split(" ", 2);
			if(ss.length == 2){
				return String.format("`%s` %s ?", ss[0], ss[1]);
			}else{
				return String.format("%s ?", p.getKey());
			}
		}else{
			return String.format("`%s` = ?", p.getKey());
		}
	}
	
	public XObject get(XProperty... where){
		StringBuffer buf = new StringBuffer("SELECT * FROM `");
		buf.append(this.table).append("`");
		if(where != null && where.length > 0){
			buf.append(" WHERE ");
			buf.append(this.build_where_statement(where[0]));
			for(int i=1; i<where.length; ++i){
				buf.append(" AND " + this.build_where_statement(where[i]));
			}
		}
		buf.append(" LIMIT 1");
		
		String sql = buf.toString();
		Object[] params = XProperty.values(where);
		
		//存一下查询
		this.save_last_query(sql, params);
		
		final XObject obj = new XObject();
		jdbc.query(sql, new RowCallbackHandler(){
			@Override
			public void processRow(ResultSet rows) throws SQLException {
				ResultSetMetaData meta = rows.getMetaData();
				int n = meta.getColumnCount();
				
				do{
					for(int i=0; i<n; ++i){
						obj.put(meta.getColumnName(i+1), rows.getObject(i+1));
					}
				}while(rows.next());
			}
		}, params);
		
		if(obj.size() == 0) return null;
		return obj;
	}
	
	public long add(XProperty... properties){
		if(properties == null || properties.length == 0) return -1;
		
		StringBuffer buf = new StringBuffer("INSERT INTO `");
		buf.append(this.table).append("` (`").append(properties[0].getKey());
		for(int i=1; i<properties.length; ++i){
			buf.append("`,`").append(properties[i].getKey());
		}
		buf.append("`) VALUES (?");
		for(int i=1; i<properties.length; ++i){
			buf.append(",?");
		}
		buf.append(")");
		final String sql = buf.toString();
		final Object[] params = XProperty.values(properties);

		//存一下查询
		this.save_last_query(sql, params);
		
		try {
		    KeyHolder keyHolder = new GeneratedKeyHolder();   
		    jdbc.update(new PreparedStatementCreator() {
		        @Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {   
		               PreparedStatement ps = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);   
		               for(int i=0; i<params.length; ++i){
		            	   ps.setObject(i+1, params[i]);
		               }
		               return ps;
		        }
		    }, keyHolder);   
		    Number n = keyHolder.getKey();
		    if(n != null)
		    	return (int) n.longValue();
		    return 0;
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
			return -1;
		}
	}
	
	public void update(XProperty[] data, XProperty[] where){
		if(data == null || data.length == 0) return;
		
		StringBuffer buf = new StringBuffer("UPDATE `");
		buf.append(this.table).append("` SET `").append(data[0].getKey()).append("`=?");
		for(int i=1; i<data.length; ++i){
			buf.append(",`").append(data[i].getKey()).append("`=?");
		}
		if(where != null && where.length > 0){
			buf.append(" WHERE ").append(this.build_where_statement(where[0]));
			for(int i=1; i<where.length; ++i){
				buf.append(" AND ").append(this.build_where_statement(where[i]));
			}
		}
		
		final String sql = buf.toString();
		final List<Object> params = new ArrayList<Object>();
		for(Object obj : XProperty.values(data)){
			params.add(obj);
		}
		for(Object obj : XProperty.values(where)){
			params.add(obj);
		}

		//存一下查询
		this.save_last_query(sql, params.toArray());
		
		jdbc.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				   PreparedStatement ps = connection.prepareStatement(sql);
	               for(int i=0; i<params.size(); ++i){
	            	   ps.setObject(i+1, params.get(i));
	               }
	               return ps;
			}
		});
	}
	
	public void del(XProperty... where){
		if(where == null || where.length == 0) return;	//为了安全考虑，还是强制要求有where语句的好
		
		StringBuffer buf = new StringBuffer("DELETE FROM `");
		buf.append(this.table).append("`");
		if(where.length > 0){
			buf.append(" WHERE ").append(this.build_where_statement(where[0]));
			for(int i=1; i<where.length; ++i){
				buf.append(" AND ").append(this.build_where_statement(where[i]));
			}
		}
		
		final String sql = buf.toString();
		
		final Object[] params = XProperty.values(where);

		//存一下查询
		this.save_last_query(sql, params);
		
		jdbc.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				   PreparedStatement ps = connection.prepareStatement(sql);   
				   for(int i=0; i<params.length; ++i){
				       ps.setObject(i+1, params[i]);
				   }
				   return ps;
			}
		});
	}
	
	public List<XObject> all(XProperty... where){
		return this.all(-1, -1, where, null);
	}
	
	public List<XObject> all(XProperty[] where, XProperty[] orderby){
		return this.all(-1, -1, where, orderby);
	}
	
	public List<XObject> all(long start, long limit, XProperty[] where, XProperty[] orderby){
		StringBuffer buf = new StringBuffer("SELECT * FROM `");
		buf.append(this.table).append("`");
		if(where != null && where.length > 0){
			buf.append(" WHERE ");
			buf.append(this.build_where_statement(where[0]));
			for(int i=1; i<where.length; ++i){
				buf.append(" AND " + this.build_where_statement(where[i]));
			}
		}
		if(orderby != null && orderby.length > 0){
			buf.append(" ORDER BY `").append(orderby[0].getKey()).append("` ").append(orderby[0].getValue());
			for(int i=1; i<orderby.length; ++i){
				buf.append(", `").append(orderby[i].getKey()).append("` ").append(orderby[i].getValue());
			}
		}
		if(start >= 0 && limit >= 0){
			buf.append(" LIMIT ").append(start).append(", ").append(limit);
		}
		
		String sql = buf.toString();
		Object[] params = XProperty.values(where);

		//存一下查询
		this.save_last_query(sql, params);
		
		final List<XObject> objs = new ArrayList<XObject>();
		jdbc.query(sql, new RowCallbackHandler(){
			@Override
			public void processRow(ResultSet rows) throws SQLException {
				do{
					XObject obj = new XObject();
					ResultSetMetaData meta = rows.getMetaData();
					int n = meta.getColumnCount();
					for(int i=0; i<n; ++i){
						obj.put(meta.getColumnName(i+1), rows.getObject(i+1));
					}
					objs.add(obj);
				}while(rows.next());
			}
		}, params);
		
		return objs;
	}
	
	public long getCount(XProperty... where){
		StringBuffer buf = new StringBuffer("SELECT COUNT(*) AS _COUNT FROM `");
		buf.append(this.table).append("`");
		if(where != null && where.length > 0){
			buf.append(" WHERE ").append(this.build_where_statement(where[0]));
			for(int i=1; i<where.length; ++i){
				buf.append(" AND ").append(this.build_where_statement(where[i]));
			}
		}
		
		final String sql = buf.toString();
		Object[] params = XProperty.values(where);
		
		//存一下查询
		this.save_last_query(sql, params);
		
		final XObject obj = new XObject();
		jdbc.query(sql, new RowCallbackHandler(){
			@Override
			public void processRow(ResultSet rows) throws SQLException {
				ResultSetMetaData meta = rows.getMetaData();
				int n = meta.getColumnCount();
				
				do{
					for(int i=0; i<n; ++i){
						obj.put(meta.getColumnName(i+1), rows.getObject(i+1));
					}
				}while(rows.next());
			}
		}, params);
		return obj.getAsLong("_COUNT");
	}
	
	public List<XObject> query_result(final String sql, Object... params){
		//存一下查询
		this.save_last_query(sql, params);
		
		final List<XObject> objs = new ArrayList<XObject>();
		jdbc.query(sql, new RowCallbackHandler(){
			@Override
			public void processRow(ResultSet rows) throws SQLException {
				do{
					XObject obj = new XObject();
					ResultSetMetaData meta = rows.getMetaData();
					int n = meta.getColumnCount();
					for(int i=0; i<n; ++i){
						obj.put(meta.getColumnName(i+1), rows.getObject(i+1));
					}
					objs.add(obj);
				}while(rows.next());
			}
		}, params);
		
		return objs;
	}
	
	public XObject query_row(final String sql, Object... params){
		//存一下查询
		this.save_last_query(sql, params);
		
		final XObject obj = new XObject();
		jdbc.query(sql, new RowCallbackHandler(){
			@Override
			public void processRow(ResultSet rows) throws SQLException {
				do{
					ResultSetMetaData meta = rows.getMetaData();
					int n = meta.getColumnCount();
					for(int i=0; i<n; ++i){
						obj.put(meta.getColumnName(i+1), rows.getObject(i+1));
					}
				}while(rows.next());
			}
		}, params);
		
		return obj;
	}
	
	public int execute(final String sql, Object... params){
		//存一下查询
		this.save_last_query(sql, params);
		
		return jdbc.update(sql, params);
	}
	
	/* 坑爹的JDBC，你就那么自信每个程序员都不需要看最终生成的sql对不对么！ */
	public String last_query(){
		StringBuffer real_sql = new StringBuffer();
		if(StringUtils.isBlank(this.last_sql)){
			return "";
		}
		
		int pos = 0;
		for(int i=0; i<this.last_sql.length(); ++i){
			char c = this.last_sql.charAt(i);
			//遇到一个占位符
			if(c == '?'){
				real_sql.append("'").append(this.save_get_param(pos++)).append("'");
			}else{
				real_sql.append(c);
			}
		}
		return real_sql.toString();
	}
	private Object save_get_param(int pos){
		if(this.last_params == null)
			return "?";
		if(pos < 0 || pos >= this.last_params.length)
			return "?";
		
		return Helpers.mysql_escape_string(this.last_params[pos].toString());
	}
}
