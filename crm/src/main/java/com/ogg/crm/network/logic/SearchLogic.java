package com.ogg.crm.network.logic;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ogg.crm.entity.Customer;
import com.ogg.crm.network.config.MsgResult;
import com.ogg.crm.network.config.RequestUrl;
import com.ogg.crm.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;


public class SearchLogic {

	public static final int NET_ERROR = 0;

	public static final int NORAML_GET_SUC = NET_ERROR + 1;

	public static final int NORAML_GET_FAIL = NORAML_GET_SUC + 1;

	public static final int NORAML_GET_EXCEPTION = NORAML_GET_FAIL + 1;

	public static void queryCustomer(final Context context, final Handler handler,
			String query, String categoryName, final int pageNum,
			final int pageSize) {

		String url = RequestUrl.HOST_URL + RequestUrl.search.normal;
		Log.e("xxx_url", url);
		JSONObject requestJson = new JSONObject();
		try {
			requestJson.put("query", URLEncoder.encode(query, "UTF-8"));
			requestJson.put("category",
					URLEncoder.encode(categoryName, "UTF-8"));
			requestJson.put("c", pageNum);
			requestJson.put("s", pageSize);



		} catch (JSONException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private static void parseCustomerListData(JSONObject response, Handler handler) {

		try {
			String sucResult = response.getString(MsgResult.RESULT_TAG).trim();
			if (sucResult.equals(MsgResult.RESULT_SUCCESS)) {
				JSONObject dataJB = response
						.getJSONObject(MsgResult.RESULT_DATA_TAG);

				JSONArray jsonArray = dataJB.getJSONArray("productItems");
				ArrayList<Customer> mTempCustomerList = new ArrayList<Customer>();
				int size = jsonArray.length();
				for (int j = 0; j < size; j++) {
					JSONObject categoryJsonObject = jsonArray.getJSONObject(j);
					Customer Customer = (Customer) JsonUtils.fromJsonToJava(
                            categoryJsonObject, Customer.class);
					mTempCustomerList.add(Customer);
				}

				Message message = new Message();
				message.what = NORAML_GET_SUC;
				message.obj = mTempCustomerList;
				handler.sendMessage(message);

			} else {
				handler.sendEmptyMessage(NORAML_GET_FAIL);
			}
		} catch (JSONException e) {
			handler.sendEmptyMessage(NORAML_GET_EXCEPTION);
		}
	}

	
}
