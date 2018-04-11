<%@page import="org.springframework.web.multipart.MaxUploadSizeExceededException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
    <head>
        <title>用户导入页面</title>
    </head>
    <body>
    
         <div style="padding: 5px 10px">
          <form id="ruleForm" method="post" action="system/userInfoImport.do" enctype="multipart/form-data">
                                  所属组：
          <select name="kefuItemName">
              <option value="充值">充值</option>
              <option value="资料">资料</option>
              <option value="交互">交互</option>
              <option value="质检">质检</option>
               <option value="培训">培训</option>
          </select>&nbsp;&nbsp;
                                所属角色：<select name="roleid">
              <option value="34">寄售客服</option>
              <option value="35">寄售物服</option>
          </select>&nbsp;&nbsp;
          <br/><br/>
                                   文件：
          <input type="file" name="excel"/>
          <br/><br/>
          <input type="submit" value="导入"/>
        </form>
        </div>
    
    </body>
</html>
