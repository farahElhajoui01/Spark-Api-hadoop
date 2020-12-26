/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hbase.api.rest;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import static org.apache.spark.ui.PagedTable$class.table;
import static spark.Spark.get;
 
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

/**
 *
 * @author Lenovo
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
         Configuration config = HBaseConfiguration.create();

      HTable hTable = new HTable(config, "Utilisateur");

      // Instantiating Put class
      // accepts a row name.
      //attention si on determine le row on ecrase la données préinsérées dan cette ligne 
      String rowname="row2";
      String conctact="contact";
      String columnNom="nom";
      String nom="test6";

      Put p = new Put(rowname.getBytes()); 

      // adding values using add() method
      // accepts column family name, qualifier/row name ,value
      p.add(conctact.getBytes(),
      columnNom.getBytes(),nom.getBytes());

     /* p.add(Bytes.toBytes("personal"),
      Bytes.toBytes("city"),Bytes.toBytes("hyderabad"));

      p.add(Bytes.toBytes("professional"),Bytes.toBytes("designation"),
      Bytes.toBytes("manager"));

      p.add(Bytes.toBytes("professional"),Bytes.toBytes("salary"),
      Bytes.toBytes("50000"));*/
      
      // Saving the put Instance to the HTable.
      hTable.put(p);
      System.out.println("data inserted");
      
      // closing HTable
      
      
      
      //scanner le content d'une table
       // Instantiating the Scan class
      /*Scan scan = new Scan();

      // Scanning the required columns
      
      scan.addColumn(conctact.getBytes(), columnNom.getBytes());

      // Getting the scan result
      ResultScanner scanner = hTable.getScanner(scan);

      // Reading values from scan result
      System.out.println("avant for");

      for (Result result = scanner.next(); result != null; result = scanner.next())

      System.out.println("Found row : " + result);

      //closing the scanner
      scanner.close();
            System.out.println("apres for");

      */
      
      Get get = new Get(rowname.getBytes());
       get.addColumn(conctact.getBytes(), columnNom.getBytes()) ;
      // Instantiating HTable class

      // Instantiating Get class
      Get g = new Get(rowname.getBytes());

      // Reading the data
      Result result = hTable.get(g);

      // Reading values from Result class object
      byte [] value = result.getValue(conctact.getBytes(),columnNom.getBytes());


      // Printing the values
      String name = value.toString();
      
      
      
      
      hTable.close();
        Spark.get("/helloworld", new Route() {
            @Override
        public Object handle(Request request, Response response) {
            return  "User "+name;
        }
            });
    }    }
    

