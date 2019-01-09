package com.welian.client.cloudevent.http;

import org.sean.framework.bean.BaseResult;
import com.welian.util.JSONType;
import org.sean.framework.util.NumberUtil;
import com.welian.util.client.BaseClient;
import com.welian.util.client.ClientChannel;

/**
 * Description:
 * Created by Sean.xie on 2017/3/13.
 */
public class DemoHttpClient extends BaseClient {

    private static final String REQ_URL = "/account-server/user/get";

    public DemoHttpClient(String host, int port) {
        super(host, port);
    }

    /**
     *
     */
    public BaseResult<DemoResp> testDemo(Integer uid) {
        if (!NumberUtil.isValidId(uid)) {
            return ClientChannel.newParamErrorResult("uid");
        }
        return client.sendGetRequestNoHeader(REQ_URL + "/" + uid, new JSONType<BaseResult<DemoResp>>() {
        });
    }
}
