package com.hystra.controllers.data;


import com.google.gson.Gson;
import com.hystra.controllers.login.Codes;
import com.hystra.dal.*;
import com.hystra.entity.login.LoginStatus;
import com.hystra.entity.login.User;
import com.hystra.utils.HibernateUtil;
import org.apache.commons.collections.ArrayStack;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

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

    period 'ytd'
    period 'mtd'
    period 'ptd'

     */
    @CrossOrigin(origins = "*" )
    @RequestMapping(value = "/getSalesTarget", method = RequestMethod.GET,params={"token","city","APItype", "userType", "period"})
    @ResponseBody
    public String getSalesTarget(String token, String city, int APItype, String userType, String period){
        Response response = new Response();

        LoginStatus loginStatus = HibernateUtil.getLoginObjFromToken(token);
        if(loginStatus!=null && loginStatus.getStatus()==1){
            DashboardNumbers dashboardNumbers = new DashboardNumbers();
            dashboardNumbers.setAPIType(APItype);
            dashboardNumbers.setCity(city);
            String titleName = "";
            String query = "";
            String periodQuery = "";
            if(APItype==1){
                if(period.equals("ytd")){
                    periodQuery = " AND MONTH BETWEEN '2023-07-01' AND CURDATE()";
                }else if(period.equals("mtd")){
                    periodQuery = " AND MONTH BETWEEN '2023-09-13' AND CURDATE()";
                }
                query = "SELECT SUM(TGT_NET_VALUE) FROM BASE_TARGET WHERE POSITION_CODE LIKE '%HYST-"+userType+"%' and GROUP_ON_ID='WELLMA' " + periodQuery;
                titleName = "Sales Target";
            }else if(APItype==2){
                if(period.equals("ytd")){
                    periodQuery = " AND TRANSACTION_DATE BETWEEN '2023-07-01' AND CURDATE()";
                }else if(period.equals("mtd")){
                    periodQuery = " AND TRANSACTION_DATE BETWEEN '2023-09-01' AND CURDATE()";
                }
                query = "SELECT SUM(NET_SALE_VALUE) FROM SALE_DETAIL_TEMP WHERE POSITION_CODE LIKE '%HYST-"+userType+"%' and GROUPON='WELLMA'" + periodQuery;
                titleName = "Sales Achievement";
            }else if(APItype==3){
                query = "SELECT COUNT(UPDATE_DATE) FROM BASE_EMP_TAGGING BET WHERE POSITION_ID LIKE '%HYST-"+userType+"%'";
                titleName = "Total Providers";
            }else if (APItype==4){
                if(period.equals("ytd")){
                    periodQuery = " AND TRANSACTION_DATE BETWEEN '2023-07-01' AND CURDATE()";
                }else if(period.equals("mtd")){
                    periodQuery = " AND TRANSACTION_DATE BETWEEN '2023-09-01' AND CURDATE()";
                }
                query = "SELECT COUNT(distinct(PROVIDER_CODE)) FROM SALE_DETAIL_TEMP WHERE POSITION_CODE LIKE '%HYST-"+userType+"%' AND PROVIDER_CODE is NOT NULL " + periodQuery;
                titleName = "Active Providers";
            }
            ArrayList<Object> objs = HibernateUtil.getDBObjectsFromSQLQuery(query);
            if(objs!=null){
                Object obj = objs.get(0);
                if(obj!=null){
                    dashboardNumbers.setNumber((int) Math.round(Double.valueOf(obj.toString())));
                }else{
                    dashboardNumbers.setNumber(0);
                }

                dashboardNumbers.setName(titleName);
                response.setMessage("Successful");
                response.setStatus("200");
                response.setData(new Gson().toJson(dashboardNumbers));
            }
        }
        return new Gson().toJson(response);
    }

    private String getTaggedToWhereClause(List<String> taggedTo, String columnName){
        boolean isFirst = true;
        String where= "";
        if(taggedTo!=null && taggedTo.size()>0){
            for(String tagged : taggedTo){
                if(!isFirst){
                    where +=" OR ";

                }else{
                    where += "(";
                    isFirst = false;
                }
                where += " "+columnName+" LIKE '%"+tagged+"%' ";
            }
            where +=") AND ";
        }
        return where;
    }


    /*
    token = Token that was issued at the time of login
    fromDate = date which is selected from date selector in filters
    toDate = date which is selected from date selector in filters
     */
    @CrossOrigin(origins = "*" )
    @RequestMapping(value = "/getMonthlySales", method = RequestMethod.GET,params={"token","fromDate","toDate"})
    @ResponseBody
    public String getMonthlySales(String token, String fromDate, String toDate){
        Response response = new Response();
        List<MonthlySalesReport> monthlySalesReports = new ArrayList<>();
        MonthlySalesReport monthlySalesReport = new MonthlySalesReport();

        String queryTarget = "SELECT epm.POSITION_ID as 'position_code', BE.NAME as 'name', COUNT(*) as 'totalProviders' FROM base_emp_tagging T  INNER JOIN base_empid_positionid_mapping epm ON epm.POSITION_ID=T.POSITION_ID INNER JOIN BASE_EMPLOYEE BE ON BE.ID = epm.EMPLOYEE_ID WHERE T.POSITION_ID LIKE '%HYST%' GROUP BY T.POSITION_ID ORDER BY T.POSITION_ID ASC;";
        LoginStatus loginStatus = HibernateUtil.getLoginObjFromToken(token);
        List<String> positionCodes = new ArrayList<>();
        String positionCode = "";
        if(loginStatus!=null && loginStatus.getStatus()==1){
            ArrayList<Object> objs = HibernateUtil.getDBObjectsFromSQLQuery(queryTarget);
            if(objs!=null && objs.size()>0){
                for(Object obj : objs){
                    if(obj!=null){
                        Object[] objectArray = (Object[]) obj;

                        positionCode = objectArray[0]==null?"":objectArray[0].toString();
                        positionCodes.add(positionCode);
                        monthlySalesReport = new MonthlySalesReport();
                        monthlySalesReport.setPositionCode(positionCode);
                        monthlySalesReport.setEmpName(objectArray[1]==null?"":objectArray[1].toString());
                        monthlySalesReport.setNoOfDoctors(objectArray[2]==null?0:Integer.valueOf(objectArray[2].toString()));
                        monthlySalesReports.add(monthlySalesReport);
                    }
                }
            }
            String whereClause = getTaggedToWhereClause(positionCodes, "position_code");
            String saleQuery = "SELECT POSITION_CODE, count(DISTINCT(PROVIDER_CODE)), SUM(TP_SALE_VALUE) FROM SALE_Detail_TEMP WHERE "+whereClause+ " TRANSACTION_DATE BETWEEN '"+ fromDate +"' AND '" + toDate + "' GROUP BY POSITION_CODE ORDER BY POSITION_CODE ASC";
            ArrayList<Object> saleData = HibernateUtil.getDBObjectsFromSQLQuery(saleQuery);
            List<MonthlySalesReport> monthlySalesReports2 = new ArrayList<>();

            if(saleData!=null && saleData.size()>0){
                for(Object obj : saleData){
                    if(obj!=null){
                        Object[] saleArray = (Object[]) obj;

                        positionCode = saleArray[0]==null?"":saleArray[0].toString();
                        for(MonthlySalesReport monthlySalesReport1 : monthlySalesReports){
                            if(positionCode.equals(monthlySalesReport1.getPositionCode())){
                                monthlySalesReport1.setNoOfDoctorsActive(saleArray[1]==null?0:Integer.valueOf(saleArray[1].toString()));
                                monthlySalesReport1.setSale(saleArray[2]==null?0:((Double) saleArray[2]).intValue());
                                monthlySalesReports2.add(monthlySalesReport1);
                                break;
                            }
                        }
                    }
                }
            }
            response.setMessage("Successful");
            response.setStatus("200");
            response.setData(new Gson().toJson(monthlySalesReports2));
        }
        return new Gson().toJson(response);
    }

    @CrossOrigin(origins = "*" )
    @RequestMapping(value = "/getGraphData", method = RequestMethod.GET,params={"token","type","positionCode"})
    @ResponseBody
    public String getGraphData(String token, String type, String positionCode){
        Response response = new Response();
        GraphData graphData = new GraphData();
        LoginStatus loginStatus = HibernateUtil.getLoginObjFromToken(token);

        if(loginStatus!=null && loginStatus.getStatus()==1) {
            String query = "";
            if("1".equals(type)){
                query = "SELECT POSITION_CODE, SUM(TP_SALE_VALUE) FROM sale_detail_temp where position_code like '%HYST-"+positionCode+"%' AND reportingMonth = '"+getCurrentMonth()+"' GROUP BY POSITION_CODE";
            } else if("2".equals(type)){
                String whereClause = getTaggedToWhereClause(getLastSixMonths(), "reportingMonth");
                query = "SELECT reportingMonth, SUM(TP_SALE_VALUE) FROM sale_detail_temp where "+ whereClause +" position_code like '%HYST-"+positionCode+"%' GROUP BY reportingMonth ORDER BY TRANSACTION_DATE DESC;";
            }

            ArrayList<Object> objs = HibernateUtil.getDBObjectsFromSQLQuery(query);

            ArrayList<GraphData> graphData1 = new ArrayList<>();

            if (objs != null && objs.size() > 0) {
                for (Object obj : objs) {
                    if (obj != null) {
                        graphData = new GraphData();
                        Object[] objectArray = (Object[]) obj;

                        graphData.setxAxis(objectArray[0] == null ? "" : objectArray[0].toString());
                        graphData.setyAxis(objectArray[1] == null ? "" : objectArray[1].toString());
                        graphData1.add(graphData);
                    }else{
                        response.setStatus(Codes.SOMETHING_WENT_WRONG);
                    }
                }
                response.setMessage("Successful");
                response.setStatus(Codes.ALL_OK);
                response.setData(new Gson().toJson(graphData1));
            } else {
                response.setStatus(Codes.NOT_FOUND);
            }
        }else{
            response.setStatus(Codes.INVALID_CREDENTIALS);
        }

        return new Gson().toJson(response);
    }

    @CrossOrigin(origins = "*" )
    @RequestMapping(value = "/getMIOReport", method = RequestMethod.GET,params={"token","team"})
    @ResponseBody
    public String getMIOReport(String token, String team){
        Response response = new Response();

        MIOReport mioReport = new MIOReport();
        LoginStatus loginStatus = HibernateUtil.getLoginObjFromToken(token);

        if(loginStatus!=null && loginStatus.getStatus()==1) {
            String query = "SELECT TRANSACTION_DATE AS DATE, provider_code AS 'PROVIDER CODE', POSITION_CODE AS MIO, DISTRICT, cust_number AS 'PHARMACY CODE', cust_name AS 'TAGGED PHARMACY', SUM(net_sale_value) AS 'MONTHLY SALE' FROM sale_detail_temp WHERE position_code LIKE '%HYST-"+team+"%' GROUP BY TRANSACTION_DATE, PROVIDER_CODE, POSITION_CODE, DISTRICT, CUST_NUMBER, CUST_NAME";

            ArrayList<Object> objs = HibernateUtil.getDBObjectsFromSQLQuery(query);

            ArrayList<MIOReport> mioReports = new ArrayList<>();

            if (objs != null && objs.size() > 0) {
                for (Object obj : objs) {
                    if (obj != null) {
                        mioReport = new MIOReport();
                        Object[] objectArray = (Object[]) obj;

                        mioReport.setTransactionDate(objectArray[0] == null ? "" : objectArray[0].toString());
                        mioReport.setProviderCode(objectArray[1] == null ? "" : objectArray[1].toString());
                        mioReport.setMIO(objectArray[2] == null ? "" : objectArray[2].toString());
                        mioReport.setDistrict(objectArray[3] == null ? "" : objectArray[3].toString());
                        mioReport.setPharmacyCode(objectArray[4] == null ? "" : objectArray[4].toString());
                        mioReport.setTaggedPharmacy(objectArray[5] == null ? "" : objectArray[5].toString());
                        mioReport.setSales(objectArray[6] == null ? "" : objectArray[6].toString());
                        mioReports.add(mioReport);
                    }else{
                        response.setStatus(Codes.SOMETHING_WENT_WRONG);
                    }
                }
                response.setMessage("Successful");
                response.setStatus(Codes.ALL_OK);
                response.setData(new Gson().toJson(mioReports));
            } else {
                response.setStatus(Codes.NOT_FOUND);
            }
        }else{
            response.setStatus(Codes.INVALID_CREDENTIALS);
        }

        return new Gson().toJson(response);
    }

    private String getCurrentMonth(){
        // Create a SimpleDateFormat to format the date as "MonthName,year"
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM, yyyy");

        // Get the current date
        Calendar calendar = Calendar.getInstance();

        // Format the current date
        String currentMonthYear = dateFormat.format(calendar.getTime());

        // Remove spaces after the comma
        currentMonthYear = currentMonthYear.replace(", ", ",");

        // Print the current month and year
        return currentMonthYear;
    }

    private List<String> getLastSixMonths(){
        // Create a SimpleDateFormat to format the date as "MonthName, year"
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM, yyyy");

        // Get the current date
        Calendar calendar = Calendar.getInstance();

        // Create an ArrayList to store the last six months
        ArrayList<String> lastSixMonths = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            // Format the current date and add it to the ArrayList
            String formattedDate = dateFormat.format(calendar.getTime());

            // Remove spaces after the comma
            formattedDate = formattedDate.replace(", ", ",");

            lastSixMonths.add(formattedDate);

            // Move the calendar back one month
            calendar.add(Calendar.MONTH, -1);
        }

        return lastSixMonths;
    }
}
