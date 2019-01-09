package com.welian.controller;

import com.welian.ApplicationLauncher;
import com.welian.beans.cloudevent.org.CreateOrUpdateOrgReq;
import com.welian.beans.cloudevent.org.EvidencesResp;
import com.welian.beans.cloudevent.org.MembersReq;
import com.welian.beans.cloudevent.org.OrderChannelListReq;
import com.welian.beans.cloudevent.org.OrderChannelReq;
import com.welian.beans.cloudevent.org.OrgInfoResp;
import com.welian.beans.cloudevent.org.OrgPageReq;
import com.welian.beans.cloudevent.user.UserReq;
import org.json.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.sean.framework.service.RedisService;
import org.sean.framework.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * created by GaoXiang on 2018/7/16
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = {ApplicationLauncher.class})//classes 指定启动类,加载环境
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestServerOrgController extends BaseMockTest {
    @Autowired
    private RedisService redisService;

    private static Integer newOrgId;
    /**
     * 创建机构
     *
     * @throws Exception
     */
    @Test
    public void create() throws Exception {
        CreateOrUpdateOrgReq req = new CreateOrUpdateOrgReq();
        req.name = "测试用例机构" + redisService.getCurrentTime();
        req.logo = "aflp153170848646_13_13_1.png";
        req.intro = "测试用例机构";
        req.type = 2;
        newOrgId=resultData(post("/org/save",req).andReturn(), OrgInfoResp.class).id;
    }

    /**
     * 更改机构信息
     *
     * @throws Exception
     */
    @Test
    public void update() throws Exception {

        CreateOrUpdateOrgReq req = new CreateOrUpdateOrgReq();
        req.name = "测试用例机构" + redisService.getCurrentTime();
        req.logo = "aflp153170848646_13_13_1.png";
        req.intro = "测试用例机构修改";
        req.type = 2;
        req.orgId = newOrgId;
        postSuccess("/org/save",req);


    }

    /**
     * 获取机构信息
     *
     * @throws Exception
     */
    @Test
    public void info() throws Exception {

        //{"orgId":1523,"withRoles":false}
        OrderChannelReq req = new OrderChannelReq();
        req.orgId = orgId;
        req.withRoles = false;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/org/getinfo")
                .contentType(MediaType.APPLICATION_JSON).content(JSONUtil.obj2Json(req))
        ).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).
                andExpect(MockMvcResultMatchers.content().json("{\"state\":1000}")).andReturn();

    }

    /**
     * 根据机构ids获取机构列表
     *
     * @throws Exception
     */
    @Test
    public void list() throws Exception {
        OrderChannelListReq req = new OrderChannelListReq();
        req.orgIds = Arrays.asList(orgId, 2045);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/org/getlistbyids")
                .contentType(MediaType.APPLICATION_JSON).content(JSONUtil.obj2Json(req))
        ).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).
                andExpect(MockMvcResultMatchers.content().json("{\"state\":1000}")).andReturn();
    }

    /**
     * 验证机构
     *
     * @throws Exception
     */
    @Test
    public void auth() throws Exception {
        //{"verifyStatus":2,"orgId":1523,"evidences":[{"title":"a6.jpg","url":"yiqn1509607181378_200_200_18.jpg"},
        // {"title":"6.png","url":"seuk1521103394691_27_42_1.png"},{"title":"6.png",
        // "url":"qezz1521103400581_27_42_1.png"},{"title":"fen.png","url":"gouu1521103403705_51_51_1.png"}]}
        OrgPageReq req = new OrgPageReq();
        req.verifyStatus = 2;
        req.orgId = orgId;
        List<EvidencesResp> evidencesResps = new ArrayList<>();
        EvidencesResp evidencesResp1 = new EvidencesResp();
        evidencesResp1.title = "a6.jpg";
        evidencesResp1.url = "yiqn1509607181378_200_200_18.jpg";
        evidencesResps.add(evidencesResp1);
        EvidencesResp evidencesResp2 = new EvidencesResp();
        evidencesResp2.title = "6.png";
        evidencesResp2.url = "seuk1521103394691_27_42_1.png";
        evidencesResps.add(evidencesResp2);
        EvidencesResp evidencesResp3 = new EvidencesResp();
        evidencesResp3.title = "fen.png";
        evidencesResp3.url = "gouu1521103403705_51_51_1.png";
        evidencesResps.add(evidencesResp3);
        req.evidences = evidencesResps;
        mockMvc.perform(MockMvcRequestBuilders.post("/org/verify/authbyuser")
                .contentType(MediaType.APPLICATION_JSON).content(JSONUtil.obj2Json(req))
        ).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).
                andExpect(MockMvcResultMatchers.content().json("{\"state\":1000}")).andReturn();
    }

    /**
     * 获取菜单列表
     *
     * @throws Exception
     */
    @Test
    public void modules() throws Exception {
        OrgPageReq req = new OrgPageReq();
        req.orgId = orgId;
        mockMvc.perform(MockMvcRequestBuilders.post("/org/getmodules")
                .contentType(MediaType.APPLICATION_JSON).content(JSONUtil.obj2Json(req))
        ).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).
                andExpect(MockMvcResultMatchers.content().json("{\"state\":1000}")).andReturn();
    }

    /**
     * 根据用户的uid获取当前此用户绑定的机构列表，
     * 如果没有，为用户创建一个默认的机构
     *
     * @throws Exception
     */
    @Test
    public void getListByUid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/org/getlistbyuid")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).
                andExpect(MockMvcResultMatchers.content().json("{\"state\":1000}")).andReturn();
    }

    /**
     * 运营后台修改认证状态
     *
     * @throws Exception
     */
    @Test
    public void updateVerify() throws Exception {
        OrgPageReq req = new OrgPageReq();
        req.orgId = orgId;
        req.verifyStatus = 3;
        req.adminUid = uid;
        mockMvc.perform(MockMvcRequestBuilders.post("/org/verify/update")
                .contentType(MediaType.APPLICATION_JSON).content(JSONUtil.obj2Json(req))
        ).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).
                andExpect(MockMvcResultMatchers.content().json("{\"state\":1000}")).andReturn();
    }

    /**
     * 运营后台机构列表
     *
     * @throws Exception
     */
    @Test
    public void manageList() throws Exception {
        OrgPageReq req = new OrgPageReq();
        req.type = 1;
        req.verifyStatus = 3;
        req.page = 1;
        req.size = 10;
        req.keyword = "";
        mockMvc.perform(MockMvcRequestBuilders.post("/org/list")
                .contentType(MediaType.APPLICATION_JSON).content(JSONUtil.obj2Json(req))
        ).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).
                andExpect(MockMvcResultMatchers.content().json("{\"state\":1000}")).andReturn();
    }

    /**
     * 获取团队成员列表
     *
     * @throws Exception
     */
    @Test
    public void memberList() throws Exception {
        OrgPageReq req = new OrgPageReq();
        req.orgId = orgId;
        req.page = 1;
        req.size = 10;
        mockMvc.perform(MockMvcRequestBuilders.post("/org/member/list")
                .contentType(MediaType.APPLICATION_JSON).content(JSONUtil.obj2Json(req))
        ).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).
                andExpect(MockMvcResultMatchers.content().json("{\"state\":1000}")).andReturn();
    }

    /**
     * 保存团队成员
     *
     * @throws Exception
     */
    @Test
    public void memberSave() throws Exception {
        MembersReq req = new MembersReq();
        req.orgId = orgId;
        req.memberUid = 114778;
        mockMvc.perform(MockMvcRequestBuilders.post("/org/member/save")
                .contentType(MediaType.APPLICATION_JSON).content(JSONUtil.obj2Json(req))
        ).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).
                andExpect(MockMvcResultMatchers.content().json("{\"state\":1000}")).andReturn();
    }

    /**
     * 删除团队成员
     *
     * @throws Exception
     */
    @Test
    public void memberDelete() throws Exception {
        MembersReq req = new MembersReq();
        req.orgId = orgId;
        req.memberUids = Arrays.asList(114778);
        mockMvc.perform(MockMvcRequestBuilders.post("/org/member/delete")
                .contentType(MediaType.APPLICATION_JSON).content(JSONUtil.obj2Json(req))
        ).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).
                andExpect(MockMvcResultMatchers.content().json("{\"state\":1000}")).andReturn();
    }

    /**
     * 根据手机号查询团队成员
     *
     * @throws Exception
     */
    @Test
    public void memberSearch() throws Exception {
        UserReq req = new UserReq();
        req.mobile = "15757871092";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/org/member/getuserbymobile")
                .contentType(MediaType.APPLICATION_JSON).content(JSONUtil.obj2Json(req))
        ).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).
                andExpect(MockMvcResultMatchers.content().json("{\"state\":1000}")).andReturn();

        JSONObject jsonObject = getData(result);

        assert jsonObject.get("profile") != null && new JSONObject(jsonObject.get("profile").toString()).get("phone")
                .equals("15757871092");
    }

    /**
     * 账单管理列表
     *
     * @throws Exception
     */
    @Test
    public void bills() throws Exception {
        OrderChannelReq req = new OrderChannelReq();
        req.orgId = orgId;
        mockMvc.perform(MockMvcRequestBuilders.post("/org/bills")
                .contentType(MediaType.APPLICATION_JSON).content(JSONUtil.obj2Json(req))
        ).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).
                andExpect(MockMvcResultMatchers.content().json("{\"state\":1000}")).andReturn();
    }

    /**
     * 结算申请
     *
     * @throws Exception
     * @see TestEventController TestSaveEvent()
     */
    @Test
    public void settlementapply() throws Exception {

    }


    public JSONObject getData(MvcResult result) throws Exception {
        JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString());
        return new JSONObject(jsonObject.get("data").toString());
    }

}
