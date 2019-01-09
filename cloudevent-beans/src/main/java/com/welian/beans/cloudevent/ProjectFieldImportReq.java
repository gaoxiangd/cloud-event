package com.welian.beans.cloudevent;

import com.welian.beans.cloudevent.project.ProjectImportBaseReq;
import org.sean.framework.bean.BaseBean;

import java.util.List;

public class ProjectFieldImportReq extends BaseBean {
    public Integer eventId;
    public List<ProjectImportBaseReq> projects;
}
