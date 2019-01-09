package com.welian.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * Created by memorytale on 2017/6/30.
 */
@Component
@ConfigurationProperties(prefix = "cloudevent")
@RefreshScope
public class CloudEventConfig {

    /**
     * 正式阿里云地址
     */
    private String release_bp_prefix;

    private String access_key_id;

    private String access_key_secret;

    //是否是正式
    private boolean release;

    // 正式pdf bucket name
    private String pdf_bucket_name;

    // 测试 pdf bucket name
    private String pdf_bucket_test_name;

    //正式 img bucket name
    private String img_bucket_name;

    //测试 img bucket name
    private String img_bucket_test_name;

    private String alioss_pdf_endpoint;

    private String link_ticket_prefix;

    private String link_jump_result_prefix;

    private int commodity_platform_id;

    private String link_signup_prefix;


    private String link_common_prefix;

    private String link_form_prefix;

    private String link_custom_prefix;

    private String image_prefix;

    private String bp_prefix;

    private String url_req_source;


    private String name_of_save_link_channel_filter;

    private String name_of_save_link_channel_filter_waining_tip;

    private Integer org_revise_day;

    private Integer default_admin_id;

    private int project_count_last_days;// 统计过去几天的数据
    private String project_count_the_day;//统计特定某一天的数据

    private byte sms_send_flag;

    private String sys_message_send_name;
    private String sys_message_send_logo;
    /**
     * 普通用户默认id
     */
    private Integer default_normal_user_id;

    private String solr_url;

    private String event_content_url;

    private String project_default_logo;

    //手续费
    private Double poundage;

    //短信单价 分
    private Integer sms_price;


    //兼容安卓3.3.1版本跳转不了票券问题
    private String link_ticket_compatibility_prefix;

    //3.7.4增加welian地址

    private String test_welian_address;
    private String welian_address;
    private String test_welian_h5_address;
    private String welian_h5_address;

    private Integer upload_records_number;

    public Integer getUpload_records_number() {
        return upload_records_number;
    }

    public void setUpload_records_number(Integer upload_records_number) {
        this.upload_records_number = upload_records_number;
    }

    public String getTest_welian_address() {
        return test_welian_address;
    }

    public void setTest_welian_address(String test_welian_address) {
        this.test_welian_address = test_welian_address;
    }

    public String getWelian_address() {
        return welian_address;
    }

    public void setWelian_address(String welian_address) {
        this.welian_address = welian_address;
    }

    public String getTest_welian_h5_address() {
        return test_welian_h5_address;
    }

    public void setTest_welian_h5_address(String test_welian_h5_address) {
        this.test_welian_h5_address = test_welian_h5_address;
    }

    public String getWelian_h5_address() {
        return welian_h5_address;
    }

    public void setWelian_h5_address(String welian_h5_address) {
        this.welian_h5_address = welian_h5_address;
    }

    public String getRelease_bp_prefix() {
        return release_bp_prefix;
    }

    public void setRelease_bp_prefix(String release_bp_prefix) {
        this.release_bp_prefix = release_bp_prefix;
    }

    public String getAlioss_pdf_endpoint() {
        return alioss_pdf_endpoint;
    }

    public void setAlioss_pdf_endpoint(String alioss_pdf_endpoint) {
        this.alioss_pdf_endpoint = alioss_pdf_endpoint;
    }

    public String getAccess_key_id() {
        return access_key_id;
    }

    public void setAccess_key_id(String access_key_id) {
        this.access_key_id = access_key_id;
        Constant.ALIOSS_ACCESS_KEY_ID=access_key_id;
    }

    public String getAccess_key_secret() {
        return access_key_secret;
    }

    public void setAccess_key_secret(String access_key_secret) {
        this.access_key_secret = access_key_secret;
        Constant.ALIOSS_ACCESS_KEY_SECRET=access_key_secret;
    }

    public boolean isRelease() {
        return release;
    }

    public void setRelease(boolean release) {
        this.release = release;
    }

    public String getPdf_bucket_name() {
        return pdf_bucket_name;
    }

    public void setPdf_bucket_name(String pdf_bucket_name) {
        this.pdf_bucket_name = pdf_bucket_name;
    }

    public String getPdf_bucket_test_name() {
        return pdf_bucket_test_name;
    }

    public void setPdf_bucket_test_name(String pdf_bucket_test_name) {
        this.pdf_bucket_test_name = pdf_bucket_test_name;
    }

    public String getImg_bucket_name() {
        return img_bucket_name;
    }

    public void setImg_bucket_name(String img_bucket_name) {
        this.img_bucket_name = img_bucket_name;
    }

    public String getImg_bucket_test_name() {
        return img_bucket_test_name;
    }

    public void setImg_bucket_test_name(String img_bucket_test_name) {
        this.img_bucket_test_name = img_bucket_test_name;
    }

    public String getProject_default_logo() {
        return project_default_logo;
    }

    public void setProject_default_logo(String project_default_logo) {
        this.project_default_logo = project_default_logo;
    }

    public Double getPoundage() {
        return poundage;
    }

    public void setPoundage(Double poundage) {
        this.poundage = poundage;
    }

    public String getLink_ticket_compatibility_prefix() {
        return link_ticket_compatibility_prefix;
    }

    public void setLink_ticket_compatibility_prefix(String link_ticket_compatibility_prefix) {
        this.link_ticket_compatibility_prefix = link_ticket_compatibility_prefix;
    }

    public String getLink_ticket_prefix() {
        return link_ticket_prefix;
    }

    public void setLink_ticket_prefix(String link_ticket_prefix) {
        this.link_ticket_prefix = link_ticket_prefix;
    }

    public String getLink_jump_result_prefix() {
        return link_jump_result_prefix;
    }

    public void setLink_jump_result_prefix(String link_jump_result_prefix) {
        this.link_jump_result_prefix = link_jump_result_prefix;
    }

    public String getLink_signup_prefix() {
        return link_signup_prefix;
    }

    public void setLink_signup_prefix(String link_signup_prefix) {
        this.link_signup_prefix = link_signup_prefix;
    }

    public int getCommodity_platform_id() {
        return commodity_platform_id;
    }

    public void setCommodity_platform_id(int commodity_platform_id) {
        this.commodity_platform_id = commodity_platform_id;
    }

    public String getEvent_content_url() {
        return event_content_url;
    }

    public void setEvent_content_url(String event_content_url) {
        this.event_content_url = event_content_url;
    }

    public String getSolr_url() {
        return solr_url;
    }

    public void setSolr_url(String solr_url) {
        this.solr_url = solr_url;
    }

    public Integer getDefault_normal_user_id() {
        return default_normal_user_id;
    }

    public void setDefault_normal_user_id(Integer default_normal_user_id) {
        this.default_normal_user_id = default_normal_user_id;
    }

    public String getSys_message_send_logo() {
        return sys_message_send_logo;
    }

    public void setSys_message_send_logo(String sys_message_send_logo) {
        this.sys_message_send_logo = sys_message_send_logo;
    }

    public String getSys_message_send_name() {
        return sys_message_send_name;
    }

    public void setSys_message_send_name(String sys_message_send_name) {
        this.sys_message_send_name = sys_message_send_name;
    }

    public String getLink_common_prefix() {
        return link_common_prefix;
    }

    public void setLink_common_prefix(String link_common_prefix) {
        this.link_common_prefix = link_common_prefix;
    }

    public String getLink_form_prefix() {
        return link_form_prefix;
    }

    public void setLink_form_prefix(String link_form_prefix) {
        this.link_form_prefix = link_form_prefix;
    }

    public String getLink_custom_prefix() {
        return link_custom_prefix;
    }

    public void setLink_custom_prefix(String link_custom_prefix) {
        this.link_custom_prefix = link_custom_prefix;
    }

    public String getImage_prefix() {
        return image_prefix;
    }

    public void setImage_prefix(String image_prefix) {
        this.image_prefix = image_prefix;
    }

    public String getBp_prefix() {
        return bp_prefix;
    }

    public void setBp_prefix(String bp_prefix) {
        this.bp_prefix = bp_prefix;
    }

    public String getUrl_req_source() {
        return url_req_source;
    }

    public void setUrl_req_source(String url_req_source) {
        this.url_req_source = url_req_source;
    }

    public String getName_of_save_link_channel_filter() {
        return name_of_save_link_channel_filter;
    }

    public void setName_of_save_link_channel_filter(String name_of_save_link_channel_filter) {
        this.name_of_save_link_channel_filter = name_of_save_link_channel_filter;
    }

    public String getName_of_save_link_channel_filter_waining_tip() {
        return name_of_save_link_channel_filter_waining_tip;
    }

    public void setName_of_save_link_channel_filter_waining_tip(String name_of_save_link_channel_filter_waining_tip) {
        this.name_of_save_link_channel_filter_waining_tip = name_of_save_link_channel_filter_waining_tip;
    }

    public Integer getOrg_revise_day() {
        return org_revise_day;
    }

    public void setOrg_revise_day(Integer org_revise_day) {
        this.org_revise_day = org_revise_day;
    }

    public int getProject_count_last_days() {
        return project_count_last_days;
    }

    public void setProject_count_last_days(int project_count_last_days) {
        this.project_count_last_days = project_count_last_days;
    }

    public String getProject_count_the_day() {
        return project_count_the_day;
    }

    public void setProject_count_the_day(String project_count_the_day) {
        this.project_count_the_day = project_count_the_day;
    }

    public byte getSms_send_flag() {
        return sms_send_flag;
    }

    public void setSms_send_flag(byte sms_send_flag) {
        this.sms_send_flag = sms_send_flag;
    }

    public Integer getDefault_admin_id() {
        return default_admin_id;
    }

    public void setDefault_admin_id(Integer default_admin_id) {
        this.default_admin_id = default_admin_id;
    }

    public Integer getSms_price() {
        return sms_price;
    }

    public void setSms_price(Integer sms_price) {
        this.sms_price = sms_price;
    }
}
