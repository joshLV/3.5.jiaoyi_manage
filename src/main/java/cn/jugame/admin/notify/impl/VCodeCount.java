package cn.jugame.admin.notify.impl;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

public class VCodeCount extends MenuCount {

	@Override
	public int getCount(int uid, int userType) {

		// List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		try {
			DefaultHttpClient httpclient = new DefaultHttpClient(); // 实例化一个
			HttpResponse response = null;
			HttpEntity entity = null;
			HttpPost httpost = new HttpPost("http://jugame.f3322.org:10100/test.php");
			httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
			httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);
			// httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8)); //
			// 将参数传入post方法中
			response = httpclient.execute(httpost);
			entity = response.getEntity(); // 返回服务器响应
			String responseString = null;
			if (response.getEntity() != null) {
				responseString = EntityUtils.toString(response.getEntity()); // 返回服务器响应的HTML代码
				// JSONObject jo = JSONObject.fromObject(responseString.trim());
				return Integer.parseInt(responseString.trim());
			} else {
				return 0;
			}

		} catch (Exception e) {
			//e.printStackTrace();
			return 0;
		} // 执行

	}

	public static void main(String[] args) {
		VCodeCount v = new VCodeCount();
		int i = v.getCount(0, 0);
		System.out.println("-- " + i);
	}

}
