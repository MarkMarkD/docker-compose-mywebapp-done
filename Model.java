/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.transasia.mvnproject1.model;

import ru.transasia.mvnproject1.entities.FilterValues;
import ru.transasia.mvnproject1.entities.Part;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 *
 * @author banashko.dv
 */
@Dependent
public class Model {
    static int counter = 0;
    //private static Model instance = new Model();
@Inject
FilterValues filt;
    private List<Part> listOfParts;   //РїСЂРѕРјРµР¶СѓС‚РѕС‡РЅС‹Р№ РјР°СЃСЃРёРІ РјРµР¶РґСѓ Р”Р‘ Рё view

    public Model() {
        listOfParts = new ArrayList<Part>();
        System.out.println("model constructor counter = " + counter);
        counter++;
    }

    //Get filtered positions from DB (if filter == null then get all positions)
    public List<Part> getListOfPartsFromDB() throws SQLException, ClassNotFoundException {
        Map filterMap = filt.getFilterMap();
        listOfParts.clear();
        ResultSet resultSet = null;
        Connection conn = DBConnUtils.getConnection();
        Statement statement = conn.createStatement();
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM public.listofparts WHERE 1=1");
        String query = null;
        System.out.println("Connection to db established from method getListOfPartsFromDB");

        if (filterMap == null) {
            query = "SELECT * FROM public.tableofparts";
        }
        else {
            //create query string and apply all the filter fields entered by user
            Set<String> filterMapKeySet = filterMap.keySet();
            Date date;
            String newSortingOrderString = "";
            if (filt.isNewSortingOrder() == false) newSortingOrderString = "ASC";
            else newSortingOrderString = "DESC";
            query = queryBuilder.toString();
            for (String attributeKey : filterMapKeySet) {
                String attributeValue = (String) filterMap.get(attributeKey);

                switch (attributeKey) {
                    case "pnumber":
                        queryBuilder.append(" AND pnumber LIKE '%" + attributeValue + "%'");
                        break;
                    case "pname":
                        queryBuilder.append(" AND pname LIKE '%" + attributeValue + "%'");
                        break;
                    case "vendor":
                        queryBuilder.append(" AND vendor LIKE '%" + attributeValue + "%'");
                        break;
                    case "qty":
                        queryBuilder.append(" AND qty >= '" + attributeValue + "'");
                        break;
                    case "shpdAfter": 
                        date = parseMyDateFirst(attributeValue);
                        queryBuilder.append(" AND shipped < PARSEDATETIME('" + parseMyDateSecond(date) + "','yyyyMMdd')");
                        break;
                    case "shpdBefore":
                        date = parseMyDateFirst(attributeValue);
                        queryBuilder.append(" AND received > PARSEDATETIME('" + parseMyDateSecond(date) + "','yyyyMMdd')");                       
                        break;
                    case "rcvdAfter":
                        date = parseMyDateFirst(attributeValue);
                        queryBuilder.append(" AND shipped > PARSEDATETIME('" + parseMyDateSecond(date) + "','yyyyMMdd')");
                        break;
                    case "rcvdBefore":
                        date = parseMyDateFirst(attributeValue);
                        queryBuilder.append(" AND received < PARSEDATETIME('" + parseMyDateSecond(date) + "','yyyyMMdd')");
                        break;
                    case "sortBy":
                        queryBuilder.append(" ORDER BY " + attributeValue + " " + newSortingOrderString);
                        break;
                }
            }
            query = queryBuilder.toString();
        }
        System.out.println("query string: " + query);
        resultSet = statement.executeQuery(query);
        
        //fill list of parts with filtered entities
        while (resultSet.next()) {
            String partName = resultSet.getString("Pname");
            String partNumber = resultSet.getString("Pnumber");
            String vendor = resultSet.getString("Vendor");
            int qty = resultSet.getInt("Qty");
            Date shipped = resultSet.getDate("Shipped");
            Date received = resultSet.getDate("Received");
            Part part = new Part(partName, partNumber, vendor, qty, shipped, received);
            listOfParts.add(part);
        }

        DBConnUtils.closeQuietly(conn);
        System.out.println("Connection to db closed from method getListOfPartsFromDB");
        return listOfParts;
    }

    //method creates and returns Date object out of String value entered by user
    private Date parseMyDateFirst(String someDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
        try {
            return sdf.parse(someDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("Incorrect date entered");
        return null;
    }
    
    //method prepares String date for using in SQL query
    private String parseMyDateSecond(Date someDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.US);
        try {
            String newDate = sdf.format(someDate);
            return newDate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Incorrect date entered");
        return null;
    }
}
