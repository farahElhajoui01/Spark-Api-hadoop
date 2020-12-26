/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hbase.api.rest;

import java.io.IOException;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.FirstKeyOnlyFilter;
import org.apache.hadoop.hbase.filter.KeyOnlyFilter;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;


/**
 *
 * @author Lenovo
 */
public class ServiceUtilisateur {
       
    public void insererUser(HTable hTable,String famille,String colonne1,String colonne2,String nom ,String prenom) throws IOException{
              
              Put p = new Put(getRowCount(hTable,famille,colonne1).getBytes()); 
              p.add(famille.getBytes(),colonne1.getBytes(),nom.getBytes());
              p.add(famille.getBytes(),colonne2.getBytes(),prenom.getBytes());
              p.add(famille.getBytes(),"id".getBytes(),getRowCount(hTable,famille,colonne1).getBytes());

              //Saving the put Instance to the HTable.
              hTable.put(p);
              System.out.println("data inserted");

     }
    
    public void insertUserApi(HTable htable){
    // exemple de url http://localhost:4567/AddUser/contact/nom/prenom/elalami/laila
    Spark.get("/AddUser/:famille/:colonne1/:colonne2/:nom/:prenom", new Route() {
            @Override
        public Object handle(Request request, Response response) throws IOException {
            insererUser(htable,request.params(":famille"), request.params(":colonne1"), request.params(":colonne2"),request.params(":nom") ,request.params(":prenom") );
            return  "Ajout User";
        }
            });
    }
    
    public String getRowCount(HTable table,String famille,String colonne) throws IOException{
      int count=0;
      Scan scan = new Scan();
      scan.addColumn(famille.getBytes(),colonne.getBytes());
      FilterList allFilters = new FilterList();
        allFilters.addFilter(new FirstKeyOnlyFilter());
        allFilters.addFilter(new KeyOnlyFilter());

        scan.setFilter(allFilters);
      ResultScanner scanner = table.getScanner(scan);
        for (Result rs = scanner.next(); rs != null; rs = scanner.next()) {
       count++;
    }
        count+=1;
        return count+"";
    
    }
    
    
    public String[] getUsersById(HTable hTable,String famille,String colonne1,String colonne2,String id) throws IOException{
        

        
        
       Get get = new Get(id.getBytes());
       get.addColumn(famille.getBytes(), colonne1.getBytes()) ;
       get.addColumn(famille.getBytes(), colonne2.getBytes()) ;
       Result result = hTable.get(get);
       byte [] value1 = result.getValue(famille.getBytes(),colonne1.getBytes());
       byte [] value2 = result.getValue(famille.getBytes(),colonne2.getBytes());

       String[] userInfo={new String(value1),new String(value2)};
       
       return userInfo;
 }
    
    public void getUserByIdApi(HTable htable){
        // exemple de url http://localhost:4567/getUserById/5
        
    Spark.get("/getUserById/:id", new Route() {
            @Override
        public Object handle(Request request, Response response) throws IOException {
         String[]  user=getUsersById(htable,"contact", "nom","prenom",request.params(":id") );
         
            return  "User: "+user[0]+" "+user[1];
        }
            });
    
    
    }
    
    
}
