package com.ogg.crm.network.config;

/**
 * remote request url
 */
public class RequestUrl {

	public static final String HOST_URL = "http://218.2.105.13:20001/CRM1.0";


	public interface account {

		/**
		 * 登陆
		 */
		public String login = "/b2bUser/loginUser.do";

	}

    public interface appointment {

        /**
         * 登陆
         */
        public String list = "/reserveMobile/findReserveList.do";

    }


    public interface search {
        /**
         * 搜索
         */
        public String normal = "/search/normal";


    }


}
