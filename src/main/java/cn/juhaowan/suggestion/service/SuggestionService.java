package cn.juhaowan.suggestion.service;
/***********************************************************************
 * Module:  SuggestionService.java
 * Author:  Administrator
 * Purpose: Defines the Interface SuggestionService
 ***********************************************************************/

import java.util.*;

import cn.jugame.util.PageInfo;
import cn.juhaowan.suggestion.vo.Suggestion;

/** @pdOid 1663a1c6-4cdb-42bd-9153-c8cfbe617c44 */
public interface SuggestionService {
   /** @param categoryID 类型ID,0为所有类型
    * @param status 状态，0：正常（未读） 1：已读 9：删除（以后可考虑增加已回复的状态）
    * @param pageSize 每页记录数
    * @param pageNo 页码
    * @param sort 排序字段
    * @param index 升序/降序
    * @pdOid 9c5ac400-05e2-4aff-a80a-6913b8e79288 */
   PageInfo<Suggestion> querySuggestionList(int categoryID, int status,int handledUserID,int isHandled, int pageSize, int pageNo, String sort, String order,int source);
   /** @param userID 
    * @param content 
    * @param categoryID 
    * @param userGameID 
    * @param userQQ 
    * @param userEmail 
    * @param userPhone 
    * @param sourceType 来源，1：内部 2：外部
    * @pdOid 2074ae28-1538-40e6-a8fb-a2434788a69f */
   int addSuggestion(int userID, String content, int categoryID, String userGameID, String userQQ, String userEmail, String userPhone, int sourceType);
   /** 删除一条建议
    * 
    * @param suggestionID
    * @pdOid 3bf86e91-a16c-4720-b2c9-3f99487af68f */
   int deleteSuggestion(int suggestionID);
   /** 改变建议状态
    * 
    * @param suggestionID 
    * @param status
    * @pdOid f68f92c1-d8d0-46b8-b4ec-cbafede08bf2 */
   int changeSuggestionStatus(int suggestionID, int status);
   /** 读取意见反馈详情
    * 
    * @param suggestionID
    * @pdOid 20292017-fa80-46e3-be20-5c16b80edce5 */
   Suggestion getSuggestionDetail(int suggestionID);
   
   int updateRemarkRelated(int isHandled,String remark,int hUID,int suggestionId,Date handledtime);

}