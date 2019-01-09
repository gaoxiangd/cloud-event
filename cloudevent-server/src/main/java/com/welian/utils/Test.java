package com.welian.utils;

import org.sean.framework.util.StringUtil;

public class Test {
    public static void main(String[] ARG) {
        //String newContent = CharUtil.wordToXX("我来自QQ空间,你好啊bitch");
        //setDB(2099, 7);
        //String SQL = getSaasReceiveProjectString(10047);
        String SQL = getReceiveProjectSqlStringForNoSee(43262);
        System.out.println(SQL);

    }

    private static void setDB(int newId, int oldId) {
        String n1 = "update org_info set new_id = " + newId + " where id = " + oldId;
        String n2 = "update event set org_id = " + newId + " where org_id = " + oldId;
        String n3 = "update event_record set org_id = " + newId + " where org_id = " + oldId;
        String n4 = "update event_record_ad_relation set org_id = " + newId + " where org_id = " + oldId;
        String n5 = "update event_sys_message set org_id = " + newId + " where org_id = " + oldId;
        String n6 = "update investor_group set org_id = " + newId + " where org_id = " + oldId;
        String n7 = "update project_receive_per_org_one_day set org_id = " + newId + " where org_id =" + oldId;
        String n8 = "select * from org_info where new_id = " + newId;
        System.out.println(n1+";");
        System.out.println(n2+";");
        System.out.println(n3+";");
        System.out.println(n4+";");
        System.out.println(n5+";");
        System.out.println(n6+";");
        System.out.println(n7+";");
        System.out.println(n8+";");

    }

    private static String getSaasReceiveProjectString(Integer uid) {
        String sql;
        String searchStr = null;// 组装筛选条件
        sql = "select * from (";
        //直通车+fa未处理
        sql = sql + "(select case o.source when 6 then '1' when 5 then '2' else '3' end as sort, o.id,o.pid,o.content, o.investor_id, o.bp_id, o.status, o.status as rstatus, o.created, o.feedback_time, o.readed, o.read_time, o.create_time, o.modify_time, case o.source when 6 then '2' when 5 then '99' else 1 end as type, o.message, o.star, o.source, o.channel_id,p.stage as stage, p.city_id,p.name as project_name, p.intro as project_intro,  industry.industry_id, case p.amount_unit when 1 then p.amount*10000*7 when 2 then p.amount*7 when 3 then p.amount*10000 when 4 then p.amount end as amount from investor_order o left join project p on o.pid = p.id left join project_industry industry on o.pid = industry.pid";
        sql = sql + " where  o.investor_id = " + uid + " and o.source in (5,6) and o.status = 0 and o.is_remove = 0 and o.is_invalid = 0 group by o.id) ";
        sql = sql + " union all ";
        //普通  直接投递 + 直通车   处理过 +fa    处理过的
        sql = sql + "(select '3' as sort, o.id,o.pid,o.content, o.investor_id, o.bp_id, o.status, o.status as rstatus, o.created, o.feedback_time, o.readed, o.read_time, o.create_time, o.modify_time, case o.source when 6 then '2' when 5 then '99'  when 7 then '3' else '1' end as type, o.message, o.star, o.source, o.channel_id,p.stage as stage, p.city_id,p.name as project_name, p.intro as project_intro,  industry.industry_id, case p.amount_unit when 1 then p.amount*10000*7 when 2 then p.amount*7 when 3 then p.amount*10000 when 4 then p.amount end as amount from investor_order o left join project p on o.pid = p.id left join project_industry industry on o.pid = industry.pid";
        sql = sql + " where ((o.type = 0 and o.score + p.score > 50) or o.star = 1 or o.status = 2)  and o.status = 0 and o.is_remove = 0 and o.investor_id = " + uid + " and (o.source NOT IN (5 , 6) or (o.source in (5,6) and o.status != 0)) group by o.id) ";
        sql = sql + "  )data";

        if (!StringUtil.isEmpty(searchStr)) {
            sql = sql + searchStr;
        }
        sql = sql + " order by sort, created desc, id desc";
        return sql;
    }


    private static String getReceiveProjectSqlStringForNoSee(Integer uid) {
        StringBuilder sql = new StringBuilder("select * from ((");
        sql = sql.append("select o.id, o.pid, o.investor_id, o.bp_id, o.status, o.created, o.feedback_time, o.feedback, o.readed, o.read_time, if( gr.message_latest_time > 0,gr.message_latest_time,o.create_time) as create_time, '1' as type, o.message, o.star, o.source, o.channel_id,gr.id as communicate_id from investor_order o left join project  p on o.pid = p.id join communicate_group gr on gr.relation_id = o.id and gr.type = 0");
        sql = sql.append(" where  o.investor_id = ").append(uid).append(" and  o.type = 0 and (o.score + p.score <= 50 or (o.is_remove = 1 and o.is_invalid = 0) ) )");
        sql = sql.append(" union all ");
        sql = sql.append("(select r.id, r.pid, r.investor_id, '0' as bp_id, r.status, r.created, r.feedback_time,'' as feedback,'0' as readed,r.read_time, if( gr.message_latest_time > 0,gr.message_latest_time,r.create_time) as create_time, '4' as type,'' as message,'0' as star, '4' as source, null as channel_id,gr.id as communicate_id  from investor_required r left join project p on r.pid = p.id  join communicate_group gr on gr.relation_id = r.id and gr.type = 1");
        sql = sql.append(" where r.investor_id = ").append(uid).append(" and r.type = 1 and r.is_remove = 1 group by r.id )");
        sql = sql.append(" ) data order by create_time desc");
        return sql.toString();
    }
}
