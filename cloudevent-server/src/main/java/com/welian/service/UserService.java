package com.welian.service;

import org.sean.framework.bean.BaseResult;
import org.sean.framework.bean.PageData;
import com.welian.beans.account.User;
import com.welian.beans.account.UserReq;
import com.welian.beans.cloudevent.user.UserResp;
import com.welian.client.account.AccountClient;
import com.welian.client.account.UserClient;
import org.sean.framework.code.ResultCodes;
import com.welian.pojo.EventRecordUser;
import org.sean.framework.util.StringUtil;
import com.welian.utils.ExceptionUtil;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by Sean.xie on 2017/3/14.
 */
@Service
public class UserService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private AccountClient accountClient;

    /**
     * 根据uid列表批量获取用户的信息列表
     *
     * @param uids
     * @return
     */
    public Map<Integer, User> getUserInfoListByIds(List<Integer> uids) {
        //批量查询用户的信息列表
        com.welian.beans.account.User userReq = new com.welian.beans.account.User();
        userReq.uids = uids;
        BaseResult<PageData<User>> baseResult = userClient.query(userReq);
        if (!baseResult.isSuccess() || baseResult.getData() == null || StringUtil.isEmpty(baseResult.getData()
                .getList())) {
            throw ExceptionUtil.createParamException("获取用户信息失败");
        }
        //将返回的用户信息放入map，方便遍历
        PageData<User> users = baseResult.getData();
        Map<Integer, User> map = new HashMap<>();
        for (User user : users.getList()) {
            if (user == null) {
                continue;
            }
            if (user != null) {
                map.put(user.id, user);
            }
        }
        return map;
    }

    /**
     * 根据用户的uid获取用户的信息
     *
     * @param uid
     * @return
     */
    public User getUserInfoById(Integer uid) {
        BaseResult<User> baseResult = userClient.getById(uid);
        if (!baseResult.isSuccess() || baseResult.getData() == null) {
            throw ExceptionUtil.createParamException("用户不存在");
        }
        return baseResult.getData();
    }


    /**
     * @param mobile
     * @return
     */
    public User getUserInfoByMobile(String mobile) {
        //判断是否是微链用户
        BaseResult<User> baseResult = userClient.getByPhone(mobile);
        if (!baseResult.isSuccess()) {
            if (baseResult.getState().equals(ResultCodes.Code.COMMON_ERROR_NOT_EXIST.getCode())) {
                throw ExceptionUtil.createParamException("添加的用户必须为微链用户");
            } else {
                throw ExceptionUtil.createParamException(baseResult.getErrormsg());
            }
        }
        if (baseResult.getData() == null || baseResult.getData().id == null) {
            throw ExceptionUtil.createParamException("用户不存在");
        }
        if (TextUtils.isEmpty(baseResult.getData().name)) {
            throw ExceptionUtil.createParamException("用户名称不存在");
        }
        return baseResult.getData();
    }

    /**
     * @param mobile
     * @return
     */
    public User getUserAccountByMobile(String mobile) {
        //判断是否是微链用户
        BaseResult<User> baseResult = userClient.getByPhone(mobile);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException("用户不存在");
        }
        if (baseResult.getData() == null || baseResult.getData().id == null) {
            throw ExceptionUtil.createParamException("用户不存在");
        }
        return baseResult.getData();
    }


    /**
     * 根据微信id获取account详情
     *
     * @param unionid
     * @return
     */
    public User getAccountbyUnionid(String unionid) {
        User user = new User();
        user.unionid = unionid;
        BaseResult<PageData<User>> baseResult = userClient.query(user);
        if (!baseResult.isSuccess() || baseResult.getData() == null) {
            throw ExceptionUtil.createParamException("获取用户信息失败");
        }
        PageData<User> users = baseResult.getData();
        if (StringUtil.isEmpty(users.getList()) || users.getList().size() == 0) {
            return null;
        }
        return users.getList().get(0);
    }

    /**
     * User -> UserResp
     * @param user
     * @return
     */
    public UserResp user2UserResp(User user) {
        UserResp userResp = new UserResp();
        userResp.mobile = user.phone;
        userResp.name = user.name;
        userResp.company = user.company;
        userResp.position = user.position;
        userResp.phone = user.phone;
        userResp.id = user.id;
        userResp.uid = user.id;
        return userResp;
    }
    public UserResp eventRecordUser2UserResp(EventRecordUser eventRecordUser){
        UserResp userResp = new UserResp();
        userResp.mobile = eventRecordUser.getPhone();
        userResp.name = eventRecordUser.getName();
        userResp.company = eventRecordUser.getCompany();
        userResp.position = eventRecordUser.getPosition();
        userResp.phone = eventRecordUser.getPhone();
        userResp.id = eventRecordUser.getUid();
        userResp.uid = eventRecordUser.getUid();
        return userResp;
    }


    /**
     * 根据手机号走account服务获取uid
     * @param req
     * @return
     */
    public Integer getUidByUser(UserReq req){
        BaseResult<Integer> baseResult=userClient.register(req);
        if(!baseResult.isSuccess()){
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        return baseResult.getData();
    }


}
