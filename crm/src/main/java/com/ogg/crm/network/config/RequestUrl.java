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
         * 获取列表
         */
        public String list = "/reserveMobile/findReserveList.do";


        /**
         * 设置完成
         */
        public String setState = "/reserveMobile/updateStatus.do";

    }


    public interface customer {

        /**
         * 获取列表
         */
        public String getConfInfo = "/customerbMobile/queryConfByCategoryJson.do";


    }

    public interface address {

        /**
         * 获取地址数据
         */
        public String getData = "/customerbMobile/queryRegions.do";


    }

    public interface search {
        /**
         * 搜索
         */
        public String normal = "/search/normal";


    }


}
