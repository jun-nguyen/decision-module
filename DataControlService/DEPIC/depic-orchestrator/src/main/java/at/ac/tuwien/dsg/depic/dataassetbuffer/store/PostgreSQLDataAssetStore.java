/**
 * Copyright 2013 Technische Universitat Wien (TUW), Distributed SystemsGroup
  E184.  This work was partially supported by the European Commission in terms
 * of the CELAR FP7 project (FP7-ICT-2011-8 #317790).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package at.ac.tuwien.dsg.depic.dataassetbuffer.store;

import at.ac.tuwien.dsg.depic.common.utils.Configuration;

import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAsset;
import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAttribute;
import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataItem;
import at.ac.tuwien.dsg.depic.common.entity.runtime.DaaSDescription;
import at.ac.tuwien.dsg.depic.common.entity.runtime.DataPartitionRequest;
import at.ac.tuwien.dsg.depic.common.entity.runtime.DepicDescription;
import at.ac.tuwien.dsg.depic.common.utils.DepicDesciptionImporter;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.depic.common.utils.PostgreSQLConnectionManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;


public class PostgreSQLDataAssetStore implements DataStore {

    @Override
    public void saveDataAsset(String daXML, String dafName, String partitionID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeDataAsset(String dafName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getDataAssetXML(String dafName, String partitionID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String copyDataAssetRepo(DataPartitionRequest request) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getDataPartitionRepo(DataPartitionRequest request) {

        
        System.out.println("CHECK POINT A");
        System.out.println("query: " + request.getDataAssetID());
        DataAsset dataAsset = executeQueryStatement(request.getDataAssetID());
        
        System.out.println("CHECK POINT B");
        String dataAssetXML = "";
        
        
        try {
            dataAssetXML = JAXBUtils.marshal(dataAsset, DataAsset.class);
        } catch (JAXBException ex) {
            Logger.getLogger(PostgreSQLDataAssetStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        System.out.println("Data Asset Loader - Data Asset: " + dataAssetXML);
        
        return dataAssetXML;

    }

    @Override
    public String getNoOfPartitionRepo(DataPartitionRequest request) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveDataPartitionRepo(DataAsset dataAsset) {
        
        String[] strs = dataAsset.getDataAssetID().split(";");
        String tableName = strs[1];
        
        String sql = "TRUNCATE TABLE \"" + tableName+"\"; ";
        
        
        List<DataItem> listOfDataItems = dataAsset.getListOfDataItems();
        
        for (DataItem dataItem : listOfDataItems) {
            List<DataAttribute> listOfAttributes = dataItem.getListOfAttributes();
      
            String valList = "";
            for (DataAttribute dataAttribute : listOfAttributes) {
              
                valList += "'" +dataAttribute.getAttributeValue() + "',";
            }
  
            valList = valList.substring(0, valList.length()-1);

            sql += "INSERT INTO \"" + tableName + "\" VALUES(" + valList + "); ";
            
        }
        
       
       
        DepicDesciptionImporter desciptionImporter = new DepicDesciptionImporter();
        DepicDescription depicDescription = desciptionImporter.importDescription(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        DaaSDescription daaSDescription = depicDescription.getDaaSDescription();
        
        Configuration cfg = new Configuration(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        String databaseIP = cfg.getConfig("DATABASE.IP");
        
        
        PostgreSQLConnectionManager postgreSQLConnectionManager = 
                new PostgreSQLConnectionManager(
                        databaseIP, 
                        daaSDescription.getDatabasePort(), 
                        daaSDescription.getDatabaseName(), 
                        daaSDescription.getDatabaseUser(), 
                        daaSDescription.getDatabasePassword());

        postgreSQLConnectionManager.ExecuteQuery(sql);
        
        
    }

    @Override
    public void insertDataPartitionRepo(DataAsset dataAssetPartition) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public DataAsset executeQueryStatement(String sql) {

        DepicDesciptionImporter desciptionImporter = new DepicDesciptionImporter();
        DepicDescription depicDescription = desciptionImporter.importDescription(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        DaaSDescription daaSDescription = depicDescription.getDaaSDescription();
        
        Configuration cfg = new Configuration(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        String databaseIP = cfg.getConfig("DATABASE.IP");
        
        
        PostgreSQLConnectionManager postgreSQLConnectionManager = 
                new PostgreSQLConnectionManager(
                        databaseIP, 
                        daaSDescription.getDatabasePort(), 
                        daaSDescription.getDatabaseName(), 
                        daaSDescription.getDatabaseUser(), 
                        daaSDescription.getDatabasePassword());

        ResultSet rs = postgreSQLConnectionManager.ExecuteQuery(sql);
        return getDataAsset(rs);

    }

    public DataAsset getDataAsset(ResultSet rs) {

       //  Configuration cfg = new Configuration();
        
        DataAsset da = null;
        List<String> colsList = new ArrayList<String>();

        try {
            ResultSetMetaData metaData = rs.getMetaData();
            int noOfColumn = metaData.getColumnCount();

            for (int i = 0; i < noOfColumn; i++) {
                String colName = metaData.getColumnName(i + 1);
                colsList.add(colName);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDataAssetStore.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {

            List<DataItem> dataItemList = new ArrayList<DataItem>();

            while (rs.next()) {

                List<DataAttribute> attsList = new ArrayList<DataAttribute>();
                for (String colName : colsList) {

                    String colVal = rs.getString(colName);
                    DataAttribute dataAttribute = new DataAttribute(colName, colVal);
                    attsList.add(dataAttribute);
                }

                DataItem dataItem = new DataItem(attsList);
                dataItemList.add(dataItem);

            }

            da = new DataAsset("", 0, dataItemList);

            rs.close();

        } catch (SQLException ex) {
            System.err.println(ex);
        }

        
        return da;
    }

}
