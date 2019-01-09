package com.welian.controller;

import com.welian.beans.account.User;
import com.welian.function.DefautFunc;
import com.welian.mapper.EventMapper;
import com.welian.pojo.EventExample;
import com.welian.service.UserService;
import org.junit.Before;
import org.sean.framework.bean.BaseResult;
import org.sean.framework.code.ResultCodes;
import org.sean.framework.exception.CodeI18NException;
import org.sean.framework.util.JSONUtil;
import org.sean.framework.util.NumberUtil;
import org.sean.framework.web.JWT;
import org.sean.framework.web.filter.RequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


public class BaseMockTest implements DefautFunc {

    @Autowired
    public WebApplicationContext wac;
    @Autowired
    public UserService userService;
    @Autowired
    private EventMapper eventMapper;

    /**
     * 固定的jwt
     */
    public String jwt;

    public MockMvc mockMvc;

    //最近创建的创业活动id
    protected Integer eventId;

    //最近创建的路演大赛id
    protected Integer roadEventId;

    //最新活动所在城市id
    protected Integer cityId;

    //用户下的最新发布活动的最新机构id

    protected Integer orgId;

    public static final ResultMatcher SUCCESS = MockMvcResultMatchers.content().json("{\"state\":1000}");

    //用户id
    protected Integer uid = 511211;

    /**
     * 生成JWT
     */
    public String getJwt(Integer uid) {
        if (!NumberUtil.isValidId(uid)) {
            throw new CodeI18NException(ResultCodes.Code.COMMON_ERROR_PARAMS, "用户ID不合法");
        }
        JWT kt = JWT.createJWT();
        if (NumberUtil.isValidId(uid)) {
            kt.payload = new JWT.Payload<User>();
            kt.payload.sub = null;
            kt.payload.exp = (long) Integer.MAX_VALUE;
            kt.payload.uid = uid.toString();
            User user = new User();
            user.id = uid;
            user.avatar = "默认";
            user.name = "gx";
            kt.payload.attr = user;
        }
        return kt.toString();
    }

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).addFilter(new RequestFilter(), "/*").build();
        jwt = getJwt(uid);
        EventExample example = new EventExample();
        example.setOrderByClause("id desc");
        example.createCriteria().andTemplateIdEqualTo(1);
        eventId = eventMapper.selectByExample(example).get(0).getId();
        cityId=eventMapper.selectByPrimaryKey(eventId).getCityId();
        example.clear();
        example.setOrderByClause("id desc");
        example.createCriteria().andTemplateIdEqualTo(2);
        roadEventId = eventMapper.selectByExample(example).get(0).getId();
        example.clear();
        example.setOrderByClause("org_id desc");
        example.createCriteria().andPublishUidEqualTo(uid);
        orgId=eventMapper.selectByExample(example).get(0).getOrgId();


    }

    /**
     * post测试用例
     */
    ResultActions post(String url, Object param) {
        try {
            return mockMvc.perform(
                    MockMvcRequestBuilders.post(url)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("jwt", jwt)
                            .content(obj2Json(param)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            throw error(e.getMessage());
        }
    }

    /**
     * post 失败测试用例
     */
    ResultActions postFail(String url, Object param,String errorMsg) {
        try {
            return mockMvc.perform(
                    MockMvcRequestBuilders.post(url)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("jwt", jwt)
                            .content(obj2Json(param)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            assert e.getMessage().contains(errorMsg);
            return null;
        }
    }
    /**
     * post成功测试用例
     */
    ResultActions postSuccess(String url, Object param) throws Exception {
        return post(url, param).andExpect(SUCCESS);

    }

    /**
     * get测试用例
     */
    ResultActions get(String url) {
        try {
            return mockMvc.perform(
                    MockMvcRequestBuilders.get(url)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("jwt", jwt))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            throw error(e.getMessage());
        }
    }

    /**
     * get成功测试用例
     */
    ResultActions getSuccess(String url) throws Exception {
        return get(url).andExpect(SUCCESS);

    }

    /**
     * get测试用例
     */
    ResultActions getFail(String url,String errorMsg) {
        try {
            return mockMvc.perform(
                    MockMvcRequestBuilders.get(url)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("jwt", jwt))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            assert e.getMessage().contains(errorMsg);
            return null;
        }
    }

    /**
     * delete测试用例
     */
    ResultActions delete(String url) {
        try {
            return mockMvc.perform(
                    MockMvcRequestBuilders.delete(url)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("jwt", jwt))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            throw error(e.getMessage());
        }
    }

    /**
     * delete成功测试用例
     */
    ResultActions deleteSuccess(String url) throws Exception {
        return delete(url).andExpect(SUCCESS);

    }

    /**
     * delete失败测试用例
     */
    ResultActions deleteFail(String url,String errorMsg) throws Exception {
        try {
            return mockMvc.perform(
                    MockMvcRequestBuilders.delete(url)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("jwt", jwt))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            assert e.getMessage().contains(errorMsg);
            return null;
        }

    }

    /**
     * patch测试用例
     */
    ResultActions patch(String url, Object param) {
        try {
            return mockMvc.perform(
                    MockMvcRequestBuilders.patch(url)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("jwt", jwt)
                            .content(obj2Json(param)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            throw error(e.getMessage());
        }
    }

    /**
     * patch成功测试用例
     */
    ResultActions patchSuccess(String url, Object param) throws Exception {
        return patch(url, param).andExpect(SUCCESS);

    }

    /**
     * patch失败测试用例
     */
    ResultActions patch(String url, Object param,String errorMsg) {
        try {
            return mockMvc.perform(
                    MockMvcRequestBuilders.patch(url)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("jwt", jwt)
                            .content(obj2Json(param)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            assert e.getMessage().contains(errorMsg);
            return null;
        }
    }

    /**
     * put测试用例
     */
    ResultActions put(String url, Object param) {
        try {
            return mockMvc.perform(
                    MockMvcRequestBuilders.put(url)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("jwt", jwt)
                            .content(obj2Json(param)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            throw error(e.getMessage());
        }
    }

    /**
     * put成功测试用例
     */
    ResultActions putSuccess(String url, Object param) throws Exception {
        return put(url, param).andExpect(SUCCESS);

    }

    /**
     * put失败测试用例
     */
    ResultActions putFail(String url, Object param,String errorMsg) {
        try {
            return mockMvc.perform(
                    MockMvcRequestBuilders.put(url)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("jwt", jwt)
                            .content(obj2Json(param)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            assert e.getMessage().contains(errorMsg);
            return null;
        }
    }

    /**
     * 结果转化成为Json
     */
    String result2Json(MvcResult result) {
        try {
            return result.getResponse().getContentAsString();
        } catch (Exception e) {
            throw error(e.getMessage());
        }
    }

    /**
     * 结果转化为对象
     */
    <T> T result2Obj(MvcResult result, Class<T> tClass) {
        return json2obj(result2Json(result), tClass);
    }

    /**
     * 结果转化为BaseResult
     */
    @SuppressWarnings("unchecked")
    <T> T resultData(MvcResult result, Class<T> tClass) {
        BaseResult<T> result1 = JSONUtil.json2Obj(result2Json(result), BaseResult.class);
        String json = obj2Json(result1.getData());
        return json2obj(json, tClass);
    }

    ResultMatcher matchState(Integer state) {
        return MockMvcResultMatchers.content().json("{\"state\":" + state + "}");
    }

    ResultMatcher matchMsg(String msg) {
        return MockMvcResultMatchers.content().json("{\"msg\":" + msg + "}");
    }

    ResultMatcher matchErrormsg(String errormsg) {
        return MockMvcResultMatchers.content().json("{\"errormsg\":" + errormsg + "}");
    }

    <T> ResultMatcher matchData(T data) {
        return MockMvcResultMatchers.content().json("{\"errormsg\":" + obj2Json(data) + "}");
    }
}
