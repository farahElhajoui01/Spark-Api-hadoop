/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hbase.api.rest;
import static com.oracle.jrockit.jfr.ContentType.Bytes;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
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
        
         ServiceUtilisateur serviceUtilisateur=new ServiceUtilisateur();
         Configuration config = HBaseConfiguration.create();
         HTable utilisateurTable = new HTable(config, "Utilisateur");

     
    serviceUtilisateur.insertUserApi(utilisateurTable);
    serviceUtilisateur.getUserByIdApi(utilisateurTable);

}
}
    

