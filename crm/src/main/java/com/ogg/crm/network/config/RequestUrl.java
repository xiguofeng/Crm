package com.ogg.crm.network.config;

/**
 * remote request url
 */
public class RequestUrl {

    //public static final String HOST_URL = "http://218.2.105.13:20001/CRM1.0";
    public static final String HOST_URL = "http://180.96.19.139:8000/crm";

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
         * 获取配置信息
         */
        public String getConfInfo = "/customerbMobile/queryConfByCategoryJson.do";

        /**
         * 获取客户信息列表
         */
        public String list = "/customerbMobile/findMyCustomerPage.do";

        /**
         * 保存客户信息
         */
        public String save = "/customerbMobile/saveCustomerb.do";

        /**
         * 获取公海客户
         */
        public String publicList = "/customerbMobile/findPublicPage.do";

        /**
         * 获取分配用户
         */
        public String getDisUserList = "/customerbMobile/queryUserByCode.do";

        /**
         * 分配客户给用户
         */
        public String distributionCustomer = "/customerbMobile/fenpeiCustomer.do";

        /**
         * 从公海客户中获取客户
         */
        public String getCustomer = "/customerbMobile/getCustomer.do";

        /**
         * 放弃客户
         */
        public String giveUpCustomer = "/customerbMobile/giveUpCustomer.do";
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

    public interface sms {

        /**
         * 短信模板列表
         */
        public String list = "/mobileSender/queryMsgTemplates.do";

        /**
         * 发送短信
         */
        public String send = "/mobileSender/sendMessage.do";

    }


}
