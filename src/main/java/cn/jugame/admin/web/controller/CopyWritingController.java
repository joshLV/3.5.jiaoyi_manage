package cn.jugame.admin.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jugame.util.RequestUtils;
import cn.juhaowan.cpoywriting.service.ICopyWritingService;
import cn.juhaowan.cpoywriting.vo.CopyWriting;
import cn.juhaowan.cpoywriting.vo.CopyWritingType;
import cn.juhaowan.cpoywriting.vo.PageResult;
/**
 * @author th
 * @fileName CopyWriting.java
 * @declaration 
 * @date 2018年8月23日上午10:18:04
 */
@Controller
public class CopyWritingController {
	
	@Autowired
	private ICopyWritingService copyWritingService;
	
	
	@RequestMapping(value="/copy/copyList")
	public String copyWritingList(){
		return "/copyWriting/copyWriting";
	} 
	
	@RequestMapping(value="/copy/copyList_json")
	@ResponseBody
	public PageResult copyWritingJson(HttpServletRequest request){
		int status = RequestUtils.getParameterInt(request, "status", 0);
		int platform = RequestUtils.getParameterInt(request, "platform", 0);
		int type = RequestUtils.getParameterInt(request, "type", 0);
		String starTime = RequestUtils.getParameter(request, "startTime", "");
		String endTime = RequestUtils.getParameter(request, "endTime", "");
		String title = RequestUtils.getParameter(request, "title", "");
		int pageNumber = RequestUtils.getParameterInt(request, "page", 1);
    	int pageSize = RequestUtils.getParameterInt(request, "rows", 10);
    	int startPageNum = pageNumber * pageSize - pageSize;
		List<CopyWriting> findAll = copyWritingService.findAll(status, platform, type, starTime, endTime, title ,startPageNum,pageSize);
		PageResult pageResult = new PageResult();
		double total = findAll.size();
		Double num = Math.ceil(total / pageSize);
		pageResult.setTotal(String.valueOf(Double.valueOf(total).intValue()));
		pageResult.setRows(findAll);
		pageResult.setPageNumber(String.valueOf(pageNumber));
		pageResult.setPageSize(String.valueOf(pageSize));
		pageResult.setTotalPage(String.valueOf(num.intValue()));
		return pageResult;
	} 
	
	@RequestMapping(value="/copy/copyType_json")
	@ResponseBody
	public List<CopyWritingType> copyWritingTypeList(HttpServletRequest request){
		List<CopyWritingType> findList = copyWritingService.findList();
		return findList;
	}
	
	@RequestMapping(value="/copy/CopyWritingType")
	@ResponseBody
	public List<CopyWritingType> copyWritingType(HttpServletRequest request){
		List<CopyWritingType> findList = copyWritingService.findList();
		return findList;
	}
	
	@RequestMapping(value="/copy/Copydel_json")
	@ResponseBody
	public String copyWritingdel(HttpServletRequest request){
		String[] ids = request.getParameterValues("ids");
		if(ids.length <= 0 || ids == null){
			return null;
		}
		int delete = copyWritingService.delete(ids);
		if(delete > 0){
			return "ok";
		}
		return null;
	}
	
	@RequestMapping(value="/copy/CopyTypedel_json")
	@ResponseBody
	public String copyWritingTypedel(HttpServletRequest request){
		String[] ids =  request.getParameterValues("ids");
		if(ids.length <= 0 || ids == null){
			return null;
		}
		int delete = copyWritingService.typeDelete(ids);
		if(delete > 0){
			return "ok";
		}
		return null;
	}
	
	@RequestMapping(value="/copy/CopySave_json")
	@ResponseBody
	public String copyWritingSave(HttpServletRequest request){
		String title = RequestUtils.getParameter(request, "title", "");
		String content = RequestUtils.getParameter(request, "content", "");
		int type = RequestUtils.getParameterInt(request, "type", 0);
		int platform = RequestUtils.getParameterInt(request, "platform", 0);
		int status = RequestUtils.getParameterInt(request, "status", 0);
		int weight = RequestUtils.getParameterInt(request, "weight", 0);
		int id = RequestUtils.getParameterInt(request, "id", 0);
		int update;
		if(id > 0 ){
			update = copyWritingService.update(title, content, type, platform, status, weight, id);
		}else{
			update = copyWritingService.save(title, content, type, platform, status, weight);
		}
		if(update > 0){
			return "ok";
		}
		return null;
	}
	
	@RequestMapping(value="/copy/CopyTypeSave_json")
	@ResponseBody
	public String copyWritingTypeSave(HttpServletRequest request){
		String name = RequestUtils.getParameter(request, "name", "");
		int status = RequestUtils.getParameterInt(request, "status", 0);
		int weight = RequestUtils.getParameterInt(request, "weight", 0);
		int id = RequestUtils.getParameterInt(request, "id", 0);
		int update;
		if(id > 0 ){
			update = copyWritingService.update(name, status, weight, id);
		}else{
			update = copyWritingService.save(name, status, weight);
		}
		if(update > 0){
			return "ok";
		}
		return null;
	}

}
