package com.welian.controller;

import com.welian.annotation.DownloadApi;
import com.welian.beans.cloudevent.signup.SearchSignupReq;
import com.welian.service.impl.EventRecordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * created by GaoXiang on 2018/7/12
 */
@Controller
public class ServerFileDownloadController {
    @Autowired
    private EventRecordServiceImpl eventRecordService;
    /**
     * 活动报名模板下载
     *
     * @return
     */
    @DownloadApi
    @RequestMapping(value = "/signup/template/download/{eventId}", method = {RequestMethod.GET})
    public HttpServletResponse templateDownload(HttpServletResponse response, @PathVariable("eventId") Integer eventId) throws IOException {
        return eventRecordService.templateDownload(eventId,response);
    }

    /**
     * 活动报名记录下载
     *
     * @return
     */
    @DownloadApi
    @RequestMapping(value = "/signup/template/download/records", method = {RequestMethod.POST})
    public HttpServletResponse recordsDownload(HttpServletResponse response, @RequestBody SearchSignupReq req) throws IOException {
        return eventRecordService.recordsDownload(req,response);
    }

}
