package com.hystra.controllers.data;


import com.google.gson.Gson;
import com.hystra.dal.DashboardNumbers;
import com.hystra.dal.Response;
import com.hystra.entity.login.LoginStatus;
import com.hystra.entity.login.User;
import com.hystra.utils.HibernateUtil;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

/**
 * @author Syed Muhammad Hassan
 * 30th July, 2023
 */
@Controller
public class Dashboard {

    /*
    Token will be sent that is sent to you on login.
    City can be Karachi, Islamabad or ALL
    type can be MIO or CHO

    APIType 1 means Sales Target
    APIType 2 means Achievement
    APIType 3 means totalProviders
    APIType 4 means activeProviders
     */
    @RequestMapping(value = "/getSalesTarget", method = RequestMethod.GET,params={"token","city","APItype", "userType"})
    @ResponseBody
    public String getSalesTarget(String token, String city, int APItype, String userType){
        Response response = new Response();

        LoginStatus loginStatus = HibernateUtil.getLoginObjFromToken(token);
        if(loginStatus!=null && loginStatus.getStatus()==1){
            DashboardNumbers dashboardNumbers = new DashboardNumbers();
            dashboardNumbers.setAPIType(APItype);
            dashboardNumbers.setCity(city);
            String titleName = "";
            String query = "";
            if(APItype==1){
                query = "SELECT SUM(TGT_NET_VALUE) FROM BAS_TARGET WHERE POSITION_CODE LIKE '%HYST%' and GROUP_ON_ID='WELLMA'";
                titleName = "Sales Target";
            }else if(APItype==2){
                query = "SELECT SUM(NET_SALE_VALUE) FROM SALE_DETAIL_TEMP WHERE POSITION_CODE LIKE '%HYST%' and GROUPON='WELLMA'";
                titleName = "Sales Achievement";
            }else if(APItype==3){
                query = "SELECT COUNT(UPDATE_DATE) FROM BASE_EMP_TAGGING BET WHERE POSITION_ID LIKE '%HYST%'";
                titleName = "Total Providers";
            }else if (APItype==4){
                query = "SELECT COUNT(distinct(PROVIDER_CODE)) FROM SALE_DETAIL_TEMP WHERE POSITION_CODE LIKE '%HYST%'";
                titleName = "Active Providers";
            }
            ArrayList<Object> objs = HibernateUtil.getDBObjectsFromSQLQuery(query);
            if(objs!=null){
                Object obj = objs.get(0);
                dashboardNumbers.setNumber(123);
                dashboardNumbers.setName(titleName);
                response.setMessage("Successful");
                response.setStatus("200");
                response.setData(new Gson().toJson(dashboardNumbers));
            }
        }
        return new Gson().toJson(response);
    }
}
