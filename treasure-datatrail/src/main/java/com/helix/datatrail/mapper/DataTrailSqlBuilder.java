package com.helix.datatrail.mapper;

import org.apache.ibatis.jdbc.SQL;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author lijianyu
 * @date 2019/1/4 11:07
 */
public class DataTrailSqlBuilder {

    public String buildGetOpsHistoryById(Map<String, Object> params) {
        return new SQL() {
            {
                SELECT("id,ops_time,ops_operation_identity,ops_object_name,ops_object_id,ops_object_content");
                FROM(params.get("tableName").toString());


                if (params.get("opsObjectId") != null) {
                    WHERE("ops_object_id = " + params.get("opsObjectId"));
                } else {
                    WHERE("ops_search_id = " + params.get("opsSearchId"));
                }
                AND().WHERE("ops_object_name = '" + params.get("opsObjectName") + "'");
                AND().WHERE("ops_time <='20190105'");
                ORDER_BY(" ops_time desc ");
            }
        }.toString();
    }

}
