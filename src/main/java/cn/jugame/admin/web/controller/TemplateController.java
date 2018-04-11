package cn.jugame.admin.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jugame.util.RequestUtils;
import cn.juhaowan.customer.dao.D;
import cn.juhaowan.util.XObject;
import cn.juhaowan.util.XProperty;

@Controller
@RequestMapping(value = "/tmpl")
public class TemplateController {
	Logger logger = LoggerFactory.getLogger(TemplateController.class);
	@Autowired
	private JdbcOperations jdbc;
	
	private JSONObject build_msg(boolean succ, String msg){
		JSONObject obj = new JSONObject();
		obj.put("success", succ);
		obj.put("msg", msg);
		return obj;
	}
	
	private JSONObject build_msg(boolean succ, String msg, JSONObject data){
		JSONObject obj = new JSONObject();
		obj.put("success", succ);
		obj.put("msg", msg);
		obj.put("data", data);
		return obj;
	}
	
	private <T> T fixValue(T val, T def){
		if(val == null)
			return def;
		return val;
	}
	
	private String fixValue(String val){
		if(val == null)
			return "";
		return val;
	}
	
	@RequestMapping(value = "/get_tmpls")
	@ResponseBody
	public JSONObject getTemplates(HttpServletRequest request){
		int game_id = RequestUtils.getParameterInt(request, "game_id", 0);
		if(game_id <= 0){
			JSONObject rtn = new JSONObject();
			rtn.put("total", 0);
			rtn.put("data", new ArrayList<>());
			return rtn;
		}
		
		List<XObject> rows = D.proxy("game_product_type_template", jdbc).all(new XProperty("game_id", game_id));
		
		//为每个模板查出当前所引用的商品分类
		Map<Integer, XObject> product_type_cache = new TreeMap<Integer, XObject>();
		
		for(XObject row : rows){
			StringBuffer sb = new StringBuffer();
			
			int tmpl_id = row.getAsInt("id");
			List<XObject> gps = D.proxy("game_product_type", jdbc).all(new XProperty("tmpl_id", tmpl_id));
			for(XObject gp : gps){
				int product_type_id = gp.getAsInt("product_type_id");
				XObject product_type = product_type_cache.get(product_type_id);
				if(product_type == null){
					product_type = D.proxy("product_type", jdbc).get(new XProperty("id", product_type_id));
					product_type_cache.put(product_type_id, product_type);
				}
				
				sb.append(product_type.getAsString("name")).append("；");
			}
			
			row.put("product_type", sb.toString());
		}
		
		JSONObject rtn = new JSONObject();
		rtn.put("data", rows);
		return rtn;
	}
	
	@RequestMapping(value = "/save_tmpl")
	@ResponseBody
	public JSONObject saveTemplate(HttpServletRequest request){
		String data = RequestUtils.getParameter(request, "data", "");
		JSONObject json = null;
		try{
			json = JSONObject.fromObject(data);
		}catch(Exception e){
			return build_msg(false, "错误的数据格式");
		}
		
		int game_id = json.containsKey("game_id")?json.getInt("game_id"):0;
		if(game_id <= 0){
			return build_msg(false, "非法的游戏ID");
		}
		
		//如果是修改则有id这个参数
		int id = json.containsKey("id")?json.getInt("id"):0;
		
		String name = json.containsKey("name")?json.getString("name"):"";
		if(name.trim().length() == 0){
			return build_msg(false, "模板名称不能为空");
		}
		
		//如果已经有了相同名称的模板
		XObject row = D.proxy("game_product_type_template", jdbc).get(new XProperty[]{
				new XProperty("game_id", game_id),
				new XProperty("name", name)
		});
		if(row != null && row.getAsInt("id") != id){
			return build_msg(false, "已经存在相同名称的模板");
		}
		
		//解析ext_info
		JSONObject ext_info = json.getJSONObject("ext_info");
		List<XProperty> props = new ArrayList<>();
		props.add(new XProperty("name", name));
		props.add(new XProperty("game_id", game_id));
		props.add(new XProperty("image_url", fixValue(ext_info.getString("image_url"))));
		props.add(new XProperty("id_display", fixValue(ext_info.getString("id_display"))));
		props.add(new XProperty("role_display", fixValue(ext_info.getString("role_display"))));
		props.add(new XProperty("account_display", fixValue(ext_info.getString("account_display"))));
		props.add(new XProperty("password_display", fixValue(ext_info.getString("password_display"))));
		props.add(new XProperty("safekey_display", fixValue(ext_info.getString("safekey_display"))));
		props.add(new XProperty("area_display", fixValue(ext_info.getString("area_display"))));
		if(id <= 0){
			id = (int)D.proxy("game_product_type_template", jdbc).add(props.toArray(new XProperty[0]));
		}else{
			D.proxy("game_product_type_template", jdbc).update(props.toArray(new XProperty[0]), new XProperty[]{
				new XProperty("id", id)
			});
		}
		
		//解析buy_info
		JSONArray buy_info = json.getJSONArray("buy_info");
		//先清除旧关联的数据
		D.proxy("game_product_property_template", jdbc).del(new XProperty("tmpl_id", id));
		for(int i=0; i<buy_info.size(); ++i){
			JSONObject obj = buy_info.getJSONObject(i);
			D.proxy("game_product_property_template", jdbc).add(new XProperty[]{
					new XProperty("tmpl_id", id),
					new XProperty("game_id", game_id),
					new XProperty("property_type", 3), //购买信息值为3
					new XProperty("key", obj.getString("key")),
					new XProperty("key_type", obj.getInt("key_type")),
					new XProperty("value", obj.getString("value")),
					new XProperty("value_type_limit", obj.getInt("value_type_limit")),
					new XProperty("value_required", obj.getInt("value_required")),
					new XProperty("value_encode", obj.getInt("value_encode")),
					new XProperty("weight", obj.getInt("weight")),
					new XProperty("key_desc", obj.getString("key_desc")),
			});
		}
		
		return build_msg(true, "ok");
	}
	
	@RequestMapping(value = "/del_tmpl")
	@ResponseBody
	public JSONObject deleteTemplate(HttpServletRequest request){
		int tmpl_id = RequestUtils.getParameterInt(request, "id", 0);
		if(tmpl_id <= 0){
			return build_msg(false, "不存在的模板");
		}
		
		D.proxy("game_product_property_template", jdbc).del(new XProperty("tmpl_id", tmpl_id));
		D.proxy("game_product_type_template", jdbc).del(new XProperty("id", tmpl_id));
		return build_msg(true, "ok");
	}
	
	@RequestMapping(value = "/get_tmpl")
	@ResponseBody
	public JSONObject getTemplate(HttpServletRequest request){
		int id = (int)RequestUtils.getParameterInt(request, "id", 0);
		if(id <= 0){
			return build_msg(false, "没有模板ID");
		}
		
		XObject row = D.proxy("game_product_type_template", jdbc).get(new XProperty("id", id));
		if(row == null)
			return build_msg(false, "不存在的模板");
		
		//组合成saveTemplate中同样的数据格式
		JSONObject rtn = new JSONObject();
		rtn.put("game_id", row.getAsInt("game_id"));
		rtn.put("id", row.getAsInt("id"));
		rtn.put("name", row.getAsString("name"));
		
		{
			JSONObject ext_info = new JSONObject();
			ext_info.put("image_url", fixValue(row.getAsString("image_url")));
			ext_info.put("id_display", fixValue(row.getAsString("id_display")));
			ext_info.put("role_display", fixValue(row.getAsString("role_display")));
			ext_info.put("account_display", fixValue(row.getAsString("account_display")));
			ext_info.put("password_display", fixValue(row.getAsString("password_display")));
			ext_info.put("safekey_display", fixValue(row.getAsString("safekey_display")));
			ext_info.put("area_display", fixValue(row.getAsString("area_display")));
			
			rtn.put("ext_info", ext_info);
		}
		
		{
			JSONArray buy_info = new JSONArray();
			List<XObject> rows = D.proxy("game_product_property_template", jdbc).all(new XProperty("tmpl_id", row.getAsInt("id")));
			for(int i=0; i<rows.size(); ++i){
				XObject property = rows.get(i);
				JSONObject obj = new JSONObject();
				obj.put("key", fixValue(property.getAsString("key")));
				obj.put("key_type", property.getAsInt("key_type"));
				obj.put("value", fixValue(property.getAsString("value")));
				obj.put("value_type_limit", property.getAsInt("value_type_limit"));
				obj.put("value_required", property.getAsInt("value_required"));
				obj.put("value_encode", property.getAsInt("value_encode"));
				obj.put("weight", property.getAsInt("weight"));
				obj.put("key_desc", fixValue(property.getAsString("key_desc")));
				
				buy_info.add(obj);
			}
			rtn.put("buy_info", buy_info);
		}
		
		return build_msg(true, "ok", rtn);
	}
	
}
