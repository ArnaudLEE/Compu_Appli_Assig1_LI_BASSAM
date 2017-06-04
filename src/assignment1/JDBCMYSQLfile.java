package assignment1;

//STEP 1. Import required packages
import java.sql.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;


public class JDBCMYSQLfile {
	
// JDBC driver name and database URL
		static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		static final String DB_URL = "jdbc:mysql://localhost:3306/";
		
		// Databse credentials
		static final String USER = "root";
		static final String PASS = "457001qaz";
		static final String DISABLE_SSL = "?useSSL=false";
		
		
	public static void main(String[] args) {
			
		
		
		
		
		
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement Pstmt = null;
		
		try{
		      //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");

		      //STEP 3: Open a connection
		      System.out.println("LEE ,I'm Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL + DISABLE_SSL , USER, PASS);

		      //STEP 4: Execute a query
		      System.out.println("LEE , I'm checking if the database already exist...");
		      stmt = conn.createStatement();
		      System.out.println("Deleting database...");
		      String sql_deletedatabase = "DROP DATABASE if exists PowerSystem";
		      stmt.executeUpdate(sql_deletedatabase);
		      
		      String sql_Createdatabase = "CREATE DATABASE PowerSystem";
		      stmt.executeUpdate(sql_Createdatabase);
		      System.out.println("LEE , The Database created successfully...");
		    
		      conn = DriverManager.getConnection(DB_URL + "PowerSystem" + DISABLE_SSL, USER, PASS);
		      System.out.println("xxxxx");
		      System.out.println("---------------------------LI Yunyue-------BASSAM-------------------");
		      String sql = "USE PowerSystem; ";
		      stmt.execute(sql);
		      String sql_Createtable_BaseVoltage = "CREATE TABLE BaseVoltage " +
		      " ( rdf VARCHAR(100) PRIMARY KEY ,\n "  +
		      "  ID INTEGER AUTO_INCREMENT UNIQUE KEY NOT NULL,\n" +
		      
		      "NominalValue VARCHAR(12) NOT NULL )";
		      stmt.executeUpdate(sql_Createtable_BaseVoltage);
		      
		      System.out.println("LEE, the Table BaseVoltage is created !");
		      
		      String sql_Createtable_Substation = "CREATE TABLE Substation " + 
		      " ( rdfID VARCHAR(100) PRIMARY KEY NOT NULL,\n " +
		    		  
		      "  ID INTEGER AUTO_INCREMENT UNIQUE KEY NOT NULL,\n" + 
		      
		      " Name VARCHAR(28) , \n" +  
		      " region_rdfID VARCHAR(100) )";
		      
		      stmt.executeUpdate(sql_Createtable_Substation);
		      System.out.println("LEE, The Table Substation is created!");
		      
		      String sql_Createtable_VoltageLevel = "CREATE TABLE VoltageLevel" +
		      " ( rdfID VARCHAR(65) PRIMARY KEY NOT NULL,\n" + 
		      " ID INTEGER AUTO_INCREMENT UNIQUE KEY NOT NULL,\n" + 
		      
		      " name_Voltagelevel VARCHAR(28) ,\n" + 
		      " substation_rdfID  VARCHAR(100) ,\n" +
		      " baseVoltage_rdfID   VARCHAR(100)  " +
		     // " FOREIGN KEY (substation_rdfID) REFERENCES Substation(rdfID) ON UPDATE CASCADE ON DELETE SET NULL ,\n " +
		      //" FOREIGN KEY (baseVoltage_rdfID) REFERENCES BaseVoltage(rdf) ON UPDATE CASCADE ON DELETE SET NULL " +
		      ")";
		      // TEsting right now 2017/06/03.........
		      stmt.executeUpdate(sql_Createtable_VoltageLevel);
		      System.out.println("LEE, The Table VoltageLevel is created!");
		      
		      String sql_Createtable_GeneratingUnit = "CREATE TABLE GeneratingUnit " + 
		      " ( rdfID VARCHAR(65) PRIMARY KEY ,\n" + 
		      " ID INTEGER AUTO_INCREMENT UNIQUE KEY NOT NULL,\n" +      
		      " name_Container VARCHAR(28) NOT NULL,\n" + 
		      " maxP VARCHAR(65) NOT NULL,\n" +
		      " minP VARCHAR(65) NOT NULL,\n" +
		      " equipmentContainer_rdfID VARCHAR(100) NOT NULL " + 
		      ")";
		      
		      stmt.executeUpdate(sql_Createtable_GeneratingUnit);
		      System.out.println("LEE, The Table GeneratingUnit is created!");
		      
		      String sql_Createtable_SynchronousMachine = "CREATE TABLE SynchronousMachine " +
		    		  " ( ID INTEGER AUTO_INCREMENT UNIQUE KEY NOT NULL,\n" +
				      " rdfID VARCHAR(65)PRIMARY KEY NOT NULL,\n" + 
				      " name_SM VARCHAR(28) NOT NULL,\n" +
				      " RatedS VARCHAR(25) NOT NULL,\n " + 
				      " P VARCHAR(30) NOT NULL,\n" +
				      " Q VARCHAR(30) NOT NULL,\n" +
				      " genUnit_rdfID VARCHAR(100) ,\n" +
				      " regControl_rdfID VARCHAR(100) ,\n" +
				      " equipmentContainer_rdfID VARCHAR(100) NOT NULL " + 
				      ")";
		      stmt.executeUpdate(sql_Createtable_SynchronousMachine);
		      System.out.println("LEE, The Table SynchronousMachine is Created !");
		      
		      String sql_Createtable_RegulatingControl = "CREATE TABLE RegulatingControl " +
		              "( rdfID VARCHAR(60) PRIMARY KEY NOT NULL,\n" +
		    		  "  ID INTEGER AUTO_INCREMENT UNIQUE KEY NOT NULL,\n" +       
				      " name VARCHAR(28) NOT NULL,\n" + 
				      " targetValue VARCHAR(66) NOT NULL " +
				      ")";
		      stmt.executeUpdate(sql_Createtable_RegulatingControl);
		      System.out.println("LEE, The Table RegulatingControl is created!");
		      
		      String sql_Createtable_PowerTransformer = "CREATE TABLE PowerTransformer " + 
				      " ( ID INTEGER AUTO_INCREMENT UNIQUE KEY NOT NULL,\n" +
				      " rdfID VARCHAR(66) PRIMARY KEY  NOT NULL,\n" + 
				      " name VARCHAR(28) NOT NULL,\n" +
				      " equipmentContainer_rdfID VARCHAR(100) NOT NULL " + 
				      ")";
				      
				      stmt.executeUpdate(sql_Createtable_PowerTransformer);
				      System.out.println("LEE, The Table PowerTransformer is created!");
		      
			  String sql_Createtable_EnergyConsumer = "CREATE TABLE EnergyConsumer " +
			 	    		  " ( ID INTEGER AUTO_INCREMENT UNIQUE KEY NOT NULL,\n" +
						      " rdfID VARCHAR(100) PRIMARY KEY NOT NULL,\n" + 
						      " name VARCHAR(28) NOT NULL,\n" + 
						      " P FLOAT NOT NULL,\n" +
						      " Q FLOAT NOT NULL,\n" +
						      " equipmentContainer_rdfID VARCHAR(100) NOT NULL " + 
						      ")";
				      stmt.executeUpdate(sql_Createtable_EnergyConsumer);
				      System.out.println("LEE, The Table EnergyConsumer is Created !");
				      
			  String sql_Createtable_PowerTransformerEnd = "CREATE TABLE PowerTransformerEnd " +
				    		  " ( ID INTEGER AUTO_INCREMENT UNIQUE KEY NOT NULL,\n" +
						      " rdfID VARCHAR(65) PRIMARY KEY NOT NULL,\n" + 
						      " name VARCHAR(28) NOT NULL,\n" + 
						      " Transformer_r FLOAT NOT NULL,\n" +
						      " Transformer_x FLOAT NOT NULL,\n" +
						      " Transformer_rdfID VARCHAR(100) NOT NULL,\n" +
						      " baseVoltage_rdfID VARCHAR(100)  " +
						      ")";
				      stmt.executeUpdate(sql_Createtable_PowerTransformerEnd);
				      System.out.println("LEE, The Table PowerTransformerEnd is Created !");
				      
			  String sql_Createtable_Breaker = "CREATE TABLE Breaker " + 
						      " ( ID INTEGER AUTO_INCREMENT UNIQUE KEY NOT NULL,\n" +
						      " rdfID VARCHAR(75) PRIMARY KEY NOT NULL,\n" + 
						      " name VARCHAR(28) NOT NULL,\n" +
						      " state_open VARCHAR(28) NOT NULL,\n" + 
						      " equipmentContainer_rdfID VARCHAR(100) NOT NULL " + 
						      ")";
						      
						      stmt.executeUpdate(sql_Createtable_Breaker);
						      System.out.println("LEE, The Table Breaker is created!");
						      
			  String sql_Createtable_RatioTapChanger = "CREATE TABLE RatioTapChanger " + 
								      " ( ID INTEGER AUTO_INCREMENT UNIQUE KEY NOT NULL,\n" + 
								      " rdfID VARCHAR(100) PRIMARY KEY NOT NULL UNIQUE,\n" + 
								      " NominalValue VARCHAR(99) NOT NULL,\n" +
								      " Step INTEGER NOT NULL " +
								      ")";
								      stmt.executeUpdate(sql_Createtable_RatioTapChanger);
								      System.out.println("LEE, the Table RatioTapChanger is created !");
								 
								      
								      
								      
			
								      		      
								      
								    try {
		
  	  //This is for EQ 
			File inputFile_EQ = new File("src/dataset/Assignment_EQ_reduced.xml");
			DocumentBuilderFactory As1EQ_Factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder As1EQ_Builder = As1EQ_Factory.newDocumentBuilder();
			Document As1EQ_Doc = As1EQ_Builder.parse(inputFile_EQ);
			As1EQ_Doc.getDocumentElement().normalize();
			System.out.println("Root element of the EQ file given is :" + As1EQ_Doc.getDocumentElement().getNodeName());
			
			//This is for SSH
			File inputFile_SSH = new File("src/dataset/Assignment_SSH_reduced.xml");
			DocumentBuilderFactory As1SSH_Factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder As1SSH_Builder = As1SSH_Factory.newDocumentBuilder();
			Document As1SSH_Doc = As1SSH_Builder.parse(inputFile_SSH);
			As1SSH_Doc.getDocumentElement().normalize();
			System.out.println("Root element of the SSH file given is :" + As1SSH_Doc.getDocumentElement().getNodeName());
			
			//Initialization of the NodeList concerned 
			System.out.println("Initialization of the NodeList: Mission start....");
			NodeList NodeList_BaseVoltage = As1EQ_Doc.getElementsByTagName("cim:BaseVoltage");
			NodeList NodeList_Substation = As1EQ_Doc.getElementsByTagName("cim:Substation");
			NodeList NodeList_VoltageLevel = As1EQ_Doc.getElementsByTagName("cim:VoltageLevel");
			NodeList NodeList_GeneratingUnit = As1EQ_Doc.getElementsByTagName("cim:GeneratingUnit");
			NodeList NodeList_EQ_SynchronousMachine = As1EQ_Doc.getElementsByTagName("cim:SynchronousMachine");
			NodeList NodeList_SSH_SynchronousMachine = As1SSH_Doc.getElementsByTagName("cim:SynchronousMachine");
			NodeList NodeList_EQ_RegulatingControl = As1EQ_Doc.getElementsByTagName("cim:RegulatingControl");
			NodeList NodeList_SSH_RegulatingControl = As1SSH_Doc.getElementsByTagName("cim:RegulatingControl");
			NodeList NodeList_PowerTransformer = As1EQ_Doc.getElementsByTagName("cim:PowerTransformer");
			
			NodeList NodeList_EQ_EnergyConsumer = As1EQ_Doc.getElementsByTagName("cim:EnergyConsumer");
			NodeList NodeList_SSH_EnergyConsumer = As1SSH_Doc.getElementsByTagName("cim:EnergyConsumer");
			NodeList NodeList_PowerTransformerEnd = As1EQ_Doc.getElementsByTagName("cim:PowerTransformerEnd");
			
			NodeList NodeList_EQ_Breaker = As1EQ_Doc.getElementsByTagName("cim:Breaker");
			NodeList NodeList_SSH_Breaker = As1SSH_Doc.getElementsByTagName("cim:Breaker");
			
			NodeList NodeList_EQ_RatioTapChanger = As1EQ_Doc.getElementsByTagName("cim:RatioTapChanger");
			NodeList NodeList_SSH_RatioTapChanger = As1SSH_Doc.getElementsByTagName("cim:RatioTapChanger");
			System.out.println("");
			System.out.println("Initialization of the NodeList: Mission Complete !!!");
			
			//Call of the duty of the method to parse and extract the information from the File
			ExtraNode_BaseVoltage(NodeList_BaseVoltage,Pstmt,conn);
			ExtraNode_Substation(NodeList_Substation,Pstmt,conn);
			ExtraNode_VoltageLevel(NodeList_VoltageLevel,Pstmt,conn);
			ExtraNode_GeneratingUnit(NodeList_GeneratingUnit,Pstmt,conn);
			ExtraNode_SynchronousMachine(NodeList_EQ_SynchronousMachine,NodeList_SSH_SynchronousMachine, Pstmt, conn);
			ExtraNode_RegulatingControl(NodeList_EQ_RegulatingControl, NodeList_SSH_RegulatingControl, Pstmt, conn);
			ExtraNode_PowerTransformer(NodeList_PowerTransformer, Pstmt, conn);
			ExtraNode_EnergyConsumer(NodeList_EQ_EnergyConsumer,NodeList_SSH_EnergyConsumer, Pstmt, conn);
			ExtraNode_PowerTransformerEnd(NodeList_PowerTransformerEnd, Pstmt, conn);
			ExtraNode_Breaker(NodeList_EQ_Breaker,NodeList_SSH_Breaker,Pstmt, conn);
			ExtraNode_RatioTapChanger(NodeList_EQ_RatioTapChanger,NodeList_SSH_RatioTapChanger,Pstmt, conn);
			
			
		} catch(Exception e){
			e.printStackTrace();
		}
		// TODO Auto-generated method stub 
	
	        String sql_set = "SET FOREIGN_KEY_CHECKs = 0";
            String sql_tryforeignkey = "ALTER TABLE VOLTAGELEVEL ADD CONSTRAINT FK_BASEVOLTAGE_RDFID FOREIGN KEY (BASEVOLTAGE_RDFID) REFERENCES BASEVOLTAGE(RDF) ON UPDATE CASCADE ON DELETE SET NULL;";
		    String sql_tryforeignkeyVL = "ALTER TABLE VOLTAGELEVEL ADD CONSTRAINT FK_SUBSTATION_RDFID FOREIGN KEY (SUBSTATION_RDFID) REFERENCES SUBSTATION(RDFID) ON UPDATE CASCADE ON DELETE SET NULL;";
		    String sql_tryforeignkeySM1 = "ALTER TABLE SYNCHRONOUSMACHINE ADD CONSTRAINT FK_genUNIT_RDFID FOREIGN KEY (genUNIT_RDFID) REFERENCES GENERATINGUNIT(RDFID) ON UPDATE CASCADE ON DELETE SET NULL;";
		    String sql_tryforeignkeySM2 = "ALTER TABLE SYNCHRONOUSMACHINE ADD CONSTRAINT FK_regCONTROL_RDFID  FOREIGN KEY (regCONTROL_RDFID) REFERENCES REGULATINGCONTROL(RDFID) ON UPDATE CASCADE ON DELETE SET NULL;";
		    String sql_tryforeignkeyPTE = "ALTER TABLE POWERTRANSFORMEREND ADD  FOREIGN KEY (BASEVOLTAGE_RDFID) REFERENCES BASEVOLTAGE(RDF) ON UPDATE CASCADE ON DELETE SET NULL;";
		    stmt.executeUpdate(sql_set);
            stmt.executeUpdate(sql_tryforeignkey);
            stmt.executeUpdate(sql_tryforeignkeyVL);
            stmt.executeUpdate(sql_tryforeignkeySM1);
            stmt.executeUpdate(sql_tryforeignkeySM2);
            stmt.executeUpdate(sql_tryforeignkeyPTE);
				      
		    System.out.println("LEE, The Table VoltageLevel is foreign right now!");
								    
								    
								    
								    
								    
								      
								      
				          
		   }catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }finally{
		      //finally block used to close resources
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end try
		   System.out.println("LEE, All the required TABLE are already established! ");
		   System.out.println("-----------------TAble Creation completed-------------" );
		
		//end creation of the table and database.
		   
		   
		
		

	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//This is the method for CIM object:"Base Voltage"
	//Beginning of the method "ExtraNode_BaseVoltage"
	public static void ExtraNode_BaseVoltage(NodeList MyNodeList,PreparedStatement Pstmt,Connection conn){
		System.out.println("List Length :" + MyNodeList.getLength());
		for (int temp = 0; temp < MyNodeList.getLength(); temp++){
			Node MyNode = MyNodeList.item(temp);
			System.out.println("\n Current Element :" + MyNode.getNodeName());
			if(MyNode.getNodeType()== Node.ELEMENT_NODE){
				Element MyElement = (Element)MyNode;
				System.out.println("\t rdf:ID given is :" + MyElement.getAttribute("rdf:ID"));
				System.out.println("\t Nominal Value of the Base Voltage is :" + MyElement.getElementsByTagName("cim:BaseVoltage.nominalVoltage").item(0).getTextContent());
				String rdfid_bv = MyElement.getAttribute("rdf:ID");
				String Nominalvalue_bv = MyElement.getElementsByTagName("cim:BaseVoltage.nominalVoltage").item(0).getTextContent();
				String sql_insert = "INSERT INTO BASEVOLTAGE" + " (rdf, NominalValue)" + "VALUES(?,?)" ;
				String display = "SELECT * FROM BaseVoltage ORDER BY ID ASC";
				try {
					Pstmt = conn.prepareStatement(sql_insert);
					Pstmt.setString(1, rdfid_bv);
					Pstmt.setString(2, Nominalvalue_bv);
					Pstmt.executeUpdate();
					System.out.println("It looks like it works quite well");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				/*try {
					stmt.executeUpdate(sql_insert);
					System.out.println("IT works");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
				
				// Test if the preparedstatement works well 
				try {
					ResultSet rs= Pstmt.executeQuery(display);
					rs.beforeFirst();
					while(rs.next()){
					int id = rs.getInt("id");
					String rdfid = rs.getString("rdf");
					String Nominalvalue = rs.getString("NominalVAlue");
					System.out.print("ID: " + id + "\t");
					System.out.print("rdf:ID: " + rdfid + "\t");
					System.out.print("NOMINAL VALUE: " + Nominalvalue + "\n" );
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
		}
	}
	//End of the method "ExtraNode_BaseVoltage"
	
	//This is the method for CIM object:"Substation"
	//Beginning of the method "ExtraNode_Substation"
	public static void ExtraNode_Substation(NodeList MyNodeList, PreparedStatement Pstmt, Connection conn){
		System.out.println("List Length :" + MyNodeList.getLength());
		for (int temp = 0; temp < MyNodeList.getLength(); temp++){
			Node MyNode = MyNodeList.item(temp);
			System.out.println("\n Current Element :" + MyNode.getNodeName());
			if(MyNode.getNodeType()== Node.ELEMENT_NODE){
				Element MyElement = (Element)MyNode;
				System.out.println("\t rdf:ID given is :" + MyElement.getAttribute("rdf:ID"));
				System.out.println("\t The Substation's name is :" + MyElement.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent());
				System.out.println("\t The rdf:ID of the region identified by the item 'region_rdf:ID' is : " + MyElement.getElementsByTagName("cim:Substation.Region").item(0).getAttributes().item(0).getTextContent());
                System.out.println("\t I want to verify if this is correct :" + MyElement.getElementsByTagName("cim:Substation.Region").item(0).getNodeName());	
                System.out.println("\t I want to verify if this is correct or not  :" + ((Element)MyElement.getElementsByTagName("cim:Substation.Region").item(0)).getAttribute("rdf:resource"));
                String rdfid_Ss = MyElement.getAttribute("rdf:ID");
                String name = MyElement.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();
                String rdfid_region =  ((Element)MyElement.getElementsByTagName("cim:Substation.Region").item(0)).getAttribute("rdf:resource");
                String sql_insert = "INSERT INTO substation" + " (rdfid, name, region_rdfid)" + "VALUES(?,?,?)" ;
                try {
					Pstmt = conn.prepareStatement(sql_insert);
					Pstmt.setString(1, rdfid_Ss);
					Pstmt.setString(2, name);
					Pstmt.setString(3, rdfid_region);
					Pstmt.executeUpdate();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
			}
		}
	}
	//End of the method "ExtraNodee_Substation"
	
	//This is the method for CIM object:"Voltage Level"
	//Beginning of the method "ExtraNode_VoltageLevel"
	public static void ExtraNode_VoltageLevel(NodeList MyNodeList, PreparedStatement Pstmt, Connection conn){
		System.out.println("List Length :" + MyNodeList.getLength());
		for (int temp = 0; temp < MyNodeList.getLength(); temp++){
			Node MyNode = MyNodeList.item(temp);
			System.out.println("\n Current Element :" + MyNode.getNodeName());
			if(MyNode.getNodeType()== Node.ELEMENT_NODE){
				Element MyElement = (Element)MyNode;
				System.out.println("\t rdf:ID given is :" + MyElement.getAttribute("rdf:ID"));
				System.out.println("\t The Voltage Level given is :" + MyElement.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent());
				System.out.println("\t The rdf:ID of the substation identified by the item 'substation_rdf:ID' is :" + ((Element)MyElement.getElementsByTagName("cim:VoltageLevel.Substation").item(0)).getAttribute("rdf:resource"));
                System.out.println("\t The rdf:ID of the baseVoltage identified by the item 'baseVoltage_rdf:ID' is :" + ((Element)MyElement.getElementsByTagName("cim:VoltageLevel.BaseVoltage").item(0)).getAttribute("rdf:resource"));
                String rdfid = MyElement.getAttribute("rdf:ID");
                String voltagelevel = MyElement.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();
                String rdfid_substation = ((Element)MyElement.getElementsByTagName("cim:VoltageLevel.Substation").item(0)).getAttribute("rdf:resource");
                String rdfid_basevoltage = ((Element)MyElement.getElementsByTagName("cim:VoltageLevel.BaseVoltage").item(0)).getAttribute("rdf:resource");
                String insert = "INSERT INTO Voltagelevel" + "(rdfid, name_voltagelevel, substation_rdfid, basevoltage_rdfid )" + "VALUES(?,?,?,?)";
                
                try {
					Pstmt = conn.prepareStatement(insert);
					Pstmt.setString(1, rdfid);
					Pstmt.setString(2, voltagelevel);
					Pstmt.setString(3, rdfid_substation);
					Pstmt.setString(4, rdfid_basevoltage);
					Pstmt.executeUpdate();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			
			

			
			
			
			
			
			
			
			}
		}
	}
	//End of the method "ExtraNode_VoltageLevel"
	
	//This is the method for CIM object:"Generating Unit"
		//Beginning of the method "ExtraNode_GeneratingUnit"
		public static void ExtraNode_GeneratingUnit(NodeList MyNodeList,PreparedStatement Pstmt, Connection conn){
			System.out.println("List Length :" + MyNodeList.getLength());
			for (int temp = 0; temp < MyNodeList.getLength(); temp++){
				Node MyNode = MyNodeList.item(temp);
				System.out.println("\n Current Element :" + MyNode.getNodeName());
				if(MyNode.getNodeType()== Node.ELEMENT_NODE){
					Element MyElement = (Element)MyNode;
					System.out.println("\t rdf:ID given is :" + MyElement.getAttribute("rdf:ID"));
					System.out.println("\t The Generating unit's name is :" + MyElement.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent());
					System.out.println("\t The maximum operating power noted by 'maxp' is :" + MyElement.getElementsByTagName("cim:GeneratingUnit.maxOperatingP").item(0).getTextContent());
					System.out.println("\t The minimum operating power noted by 'minp' is :" + MyElement.getElementsByTagName("cim:GeneratingUnit.minOperatingP").item(0).getTextContent());
					System.out.println("\t The rdf:ID of the equipment container noted by the item 'equipmentContainer_rdf:ID' is :" + ((Element)MyElement.getElementsByTagName("cim:Equipment.EquipmentContainer").item(0)).getAttribute("rdf:resource"));
	                String rdfid = MyElement.getAttribute("rdf:ID");
	                String name = MyElement.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();
	                String maxp = MyElement.getElementsByTagName("cim:GeneratingUnit.maxOperatingP").item(0).getTextContent();
	                String minp = MyElement.getElementsByTagName("cim:GeneratingUnit.minOperatingP").item(0).getTextContent();
	                String rdfid_equipcon = ((Element)MyElement.getElementsByTagName("cim:Equipment.EquipmentContainer").item(0)).getAttribute("rdf:resource");
	                String insert = "INSERT INTO GeneratingUnit" + "(rdfid, name_Container, maxp, minp, equipmentContainer_rdfid)" + "VALUES(?,?,?,?,?)";
	               
	                
	                try {
						Pstmt = conn.prepareStatement(insert);
						Pstmt.setString(1, rdfid);
						Pstmt.setString(2, name);
						Pstmt.setString(3, maxp);
						Pstmt.setString(4, minp);
						Pstmt.setString(5, rdfid_equipcon);
						Pstmt.executeUpdate();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}
			}
		}
		//End of the method "ExtraNode_GeneratingUnit"
	
	
		//This is the method for CIM object:"Synchronous Machine"
				//Beginning of the method "ExtraNode_SynchronousMachine"
		public static void ExtraNode_SynchronousMachine(NodeList MyNodeList_EQ, NodeList MyNodeList_SSH,PreparedStatement Pstmt, Connection conn){
			System.out.println("List Length from the file 'EQ':" + MyNodeList_EQ.getLength());
			System.out.println("List Length from the file 'SSH':" + MyNodeList_SSH.getLength());
			for (int temp = 0; temp < MyNodeList_EQ.getLength(); temp++){
				Node MyNode_EQ = MyNodeList_EQ.item(temp);
				System.out.println("\n Current Element :" + MyNode_EQ.getNodeName());
				if(MyNode_EQ.getNodeType()== Node.ELEMENT_NODE){
					Element MyElement_EQ = (Element)MyNode_EQ;
					search:
					for(int i = 0; i < MyNodeList_EQ.getLength();i++){
					Node MyNode_SSH = MyNodeList_SSH.item(i);
					if(MyNode_SSH.getNodeType()== Node.ELEMENT_NODE){
					Element MyElement_SSH = (Element)MyNode_SSH;
					while((MyElement_SSH.getAttribute("rdf:about")).equals("#" + MyElement_EQ.getAttribute("rdf:ID"))){
						System.out.println("\t rdf:ID given is :" + MyElement_EQ.getAttribute("rdf:ID"));
						System.out.println("\t The Synchronous Machine's name is :" + MyElement_EQ.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent());
						System.out.println("\t The rated apparent power noted by 'ratedS' is :" + MyElement_EQ.getElementsByTagName("cim:RotatingMachine.ratedS").item(0).getTextContent());
						
		                System.out.println("\t The active power noted by 'P' is :" + MyElement_SSH.getElementsByTagName("cim:RotatingMachine.p").item(0).getTextContent());
		                System.out.println("\t The reactive power noted by 'Q' is :" + MyElement_SSH.getElementsByTagName("cim:RotatingMachine.q").item(0).getTextContent());
		                System.out.println("\t The rdf:ID of the Generating Unit noted by 'genUnit_rdf:ID' is :" + ((Element)MyElement_EQ.getElementsByTagName("cim:RotatingMachine.GeneratingUnit").item(0)).getAttribute("rdf:resource"));
		                System.out.println("\t The rdf:ID of the Regulating Control noted by the item 'regControl_rdf:ID' is :" + ((Element)MyElement_EQ.getElementsByTagName("cim:RegulatingCondEq.RegulatingControl").item(0) ).getAttribute("rdf:resource"));
		                System.out.println("\t The rdf:ID of the Equipment container noted by the item 'equipmentContainer_rdf:ID' is :" + ((Element)MyElement_EQ.getElementsByTagName("cim:Equipment.EquipmentContainer").item(0)).getAttribute("rdf:resource"));
		                String rdfid = MyElement_EQ.getAttribute("rdf:ID");
		                String name = MyElement_EQ.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();
		                String rateds = MyElement_EQ.getElementsByTagName("cim:RotatingMachine.ratedS").item(0).getTextContent();
		                String P = MyElement_SSH.getElementsByTagName("cim:RotatingMachine.p").item(0).getTextContent();
		                String Q = MyElement_SSH.getElementsByTagName("cim:RotatingMachine.q").item(0).getTextContent();
		                String rdfid_genUnit = ((Element)MyElement_EQ.getElementsByTagName("cim:RotatingMachine.GeneratingUnit").item(0)).getAttribute("rdf:resource");
		                String rdfid_regControl = ((Element)MyElement_EQ.getElementsByTagName("cim:RegulatingCondEq.RegulatingControl").item(0) ).getAttribute("rdf:resource");
		                String rdfid_equipmentContainer = ((Element)MyElement_EQ.getElementsByTagName("cim:Equipment.EquipmentContainer").item(0)).getAttribute("rdf:resource");
		                
		                String insert = "INSERT INTO synchronousmachine" + "(rdfid , name_SM, RatedS, P, Q, genUnit_rdfid, regControl_rdfID, equipmentContainer_rdfID) " + "VALUES(?,?,?,?,?,?,?,?)";
			     
		                try {
							Pstmt = conn.prepareStatement(insert);
							Pstmt.setString(1, rdfid);
							Pstmt.setString(2, name);
							Pstmt.setString(3, rateds);
							Pstmt.setString(4, P);
							Pstmt.setString(5, Q);
							Pstmt.setString(6, rdfid_genUnit);
							Pstmt.setString(7, rdfid_regControl);
							Pstmt.setString(8, rdfid_equipmentContainer);
							Pstmt.executeUpdate();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
		                
		                
		                break search; 
					}
						
					}else {
						System.out.println("THe SSH Node's Type isn't an Element Node");
					}
				}
						
				}else {
					System.out.println("The EQ Node's Type isn't an Element Node");
				}
			}
		}
		
		
		
		/*public static void ExtraNode_SynchronousMachine(NodeList MyNodeList_EQ, NodeList MyNodeList_SSH){
					System.out.println("List Length from the file 'EQ':" + MyNodeList_EQ.getLength());
					System.out.println("List Length from the file 'SSH':" + MyNodeList_SSH.getLength());
					for (int temp = 0; temp < MyNodeList_EQ.getLength(); temp++){
						Node MyNode_EQ = MyNodeList_EQ.item(temp);
						Node MyNode_SSH = MyNodeList_SSH.item(temp);
						System.out.println("\n Current Element :" + MyNode_EQ.getNodeName());
						if(MyNode_EQ.getNodeType()== Node.ELEMENT_NODE){
							if(MyNode_SSH.getNodeType()== Node.ELEMENT_NODE){
								Element MyElement_EQ = (Element)MyNode_EQ;
								Element MyElement_SSH = (Element)MyNode_SSH; 
								System.out.println("\t rdf:ID given is :" + MyElement_EQ.getAttribute("rdf:ID"));
								System.out.println("\t The Synchronous Machine's name is :" + MyElement_EQ.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent());
								System.out.println("\t The rated apparent power noted by 'ratedS' is :" + MyElement_EQ.getElementsByTagName("cim:RotatingMachine.ratedS").item(0).getTextContent());
								
				                System.out.println("\t The active power noted by 'P' is :" + MyElement_SSH.getElementsByTagName("cim:RotatingMachine.p").item(0).getTextContent());
				                System.out.println("\t The reactive power noted by 'Q' is :" + MyElement_SSH.getElementsByTagName("cim:RotatingMachine.q").item(0).getTextContent());
				                System.out.println("\t The rdf:ID of the Generating Unit noted by 'genUnit_rdf:ID' is :" + ((Element)MyElement_EQ.getElementsByTagName("cim:RotatingMachine.GeneratingUnit").item(0)).getAttribute("rdf:resource"));
				                System.out.println("\t The rdf:ID of the Regulating Control noted by the item 'regControl_rdf:ID' is :" + ((Element)MyElement_EQ.getElementsByTagName("cim:RegulatingCondEq.RegulatingControl").item(0) ).getAttribute("rdf:resource"));
				                System.out.println("\t The rdf:ID of the Equipment container noted by the item 'equipmentContainer_rdf:ID' is :" + ((Element)MyElement_EQ.getElementsByTagName("cim:Equipment.EquipmentContainer").item(0)).getAttribute("rdf:resource"));
				                
							}else {
								System.out.println("THe Node's Type isn't an Element Node");
							}
							
						}else {
							System.out.println("The Node's Type isn't an Element Node");
						}
					}
				}*/
		
				//End of the method "ExtraNode_SynchronousMachine"
	
	
				//This is the method for CIM object:"Regulating Control"
				//Beginning of the method "ExtraNode_RegulatingControl"
				public static void ExtraNode_RegulatingControl(NodeList MyNodeList_EQ, NodeList MyNodeList_SSH, PreparedStatement Pstmt, Connection conn){
					System.out.println("List Length from the file 'EQ':" + MyNodeList_EQ.getLength());
					System.out.println("List Length from the file 'SSH':" + MyNodeList_SSH.getLength());
					for (int temp = 0; temp < MyNodeList_EQ.getLength(); temp++){
						Node MyNode_EQ = MyNodeList_EQ.item(temp);
						System.out.println("\n Current Element :" + MyNode_EQ.getNodeName());
						if(MyNode_EQ.getNodeType()== Node.ELEMENT_NODE){
							Element MyElement_EQ = (Element)MyNode_EQ;
							search:
							for(int i = 0; i < MyNodeList_EQ.getLength();i++){
							Node MyNode_SSH = MyNodeList_SSH.item(i);
							if(MyNode_SSH.getNodeType()== Node.ELEMENT_NODE){
							Element MyElement_SSH = (Element)MyNode_SSH;
							while((MyElement_SSH.getAttribute("rdf:about")).equals("#" + MyElement_EQ.getAttribute("rdf:ID"))){
								System.out.println("\t rdf:ID given is :" + MyElement_EQ.getAttribute("rdf:ID"));
								System.out.println("\t The Regulating Control of the Synchronous Machine :" + MyElement_EQ.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent());
								
								//which quantity this control about ??????
				                System.out.println("\t The target Value of the Regulating Control is :" + MyElement_SSH.getElementsByTagName("cim:RegulatingControl.targetValue").item(0).getTextContent());
				                String rdfid = MyElement_EQ.getAttribute("rdf:ID");
				                String name = MyElement_EQ.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();
				                String targetvalue = MyElement_SSH.getElementsByTagName("cim:RegulatingControl.targetValue").item(0).getTextContent();
				                
				                String insert = "INSERT INTO regulatingcontrol" + "(rdfid, name, targetvalue)" + "VALUES(?,?,?)";
					               
				                
				                try {
									Pstmt = conn.prepareStatement(insert);
									Pstmt.setString(1, rdfid);
									Pstmt.setString(2, name);
									Pstmt.setString(3, targetvalue);
									
									Pstmt.executeUpdate();
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}	
				                
				                
				                break search; 
							}
								
							}else {
								System.out.println("THe SSH Node's Type isn't an Element Node");
							}
						}
								
						}else {
							System.out.println("The EQ Node's Type isn't an Element Node");
						}
					}
				}
				//End of the method "ExtraNode_RegulatingControl"
	
				//This is the method for CIM object:"Power Transformer"
				//Beginning of the method "ExtraNode_PowerTransformer"
				public static void ExtraNode_PowerTransformer(NodeList MyNodeList,PreparedStatement Pstmt, Connection conn){
					System.out.println("List Length :" + MyNodeList.getLength());
					for (int temp = 0; temp < MyNodeList.getLength(); temp++){
						Node MyNode = MyNodeList.item(temp);
						System.out.println("\n Current Element :" + MyNode.getNodeName());
						if(MyNode.getNodeType()== Node.ELEMENT_NODE){
							Element MyElement = (Element)MyNode;
							System.out.println("\t rdf:ID given is :" + MyElement.getAttribute("rdf:ID"));
							System.out.println("\t The Power Transformer is :" + MyElement.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent());			
			                System.out.println("\t The rdf:ID of the Equipment container noted by the item 'equipmentContainer_rdf:ID' is :" + ((Element)MyElement.getElementsByTagName("cim:Equipment.EquipmentContainer").item(0)).getAttribute("rdf:resource"));
			                String rdfid = MyElement.getAttribute("rdf:ID");
			                String name = MyElement.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();
			                String rdfid_equipcon = ((Element)MyElement.getElementsByTagName("cim:Equipment.EquipmentContainer").item(0)).getAttribute("rdf:resource");
			                
			                String insert = "INSERT INTO POWERTRANSFORMER" + "(rdfid, name, equipmentContainer_rdfid)" + "VALUES(?,?,?)";
				               
			                
			                
			                try {
								Pstmt = conn.prepareStatement(insert);
								Pstmt.setString(1, rdfid);
								Pstmt.setString(2, name);		
								Pstmt.setString(3, rdfid_equipcon);					
								Pstmt.executeUpdate();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}	
			                
			                
						}
					}
				}
				//End of the method "ExtraNode_PowerTransformer"
				
				//This is the method for CIM object:"Energy Consumer(Load)"
				//Beginning of the method "ExtraNode_EnergyConsumer"
				public static void ExtraNode_EnergyConsumer(NodeList MyNodeList_EQ, NodeList MyNodeList_SSH, PreparedStatement Pstmt, Connection conn){
					System.out.println("List Length from the file 'EQ':" + MyNodeList_EQ.getLength());
					System.out.println("List Length from the file 'SSH':" + MyNodeList_SSH.getLength());
					for (int temp = 0; temp < MyNodeList_EQ.getLength(); temp++){
						Node MyNode_EQ = MyNodeList_EQ.item(temp);
						System.out.println("\n Current Element :" + MyNode_EQ.getNodeName());
						if(MyNode_EQ.getNodeType()== Node.ELEMENT_NODE){
							Element MyElement_EQ = (Element)MyNode_EQ;
							search:
							for(int i = 0; i < MyNodeList_EQ.getLength();i++){
							Node MyNode_SSH = MyNodeList_SSH.item(i);
							if(MyNode_SSH.getNodeType()== Node.ELEMENT_NODE){
							Element MyElement_SSH = (Element)MyNode_SSH;
							while((MyElement_SSH.getAttribute("rdf:about")).equals("#" + MyElement_EQ.getAttribute("rdf:ID"))){
								System.out.println("\t rdf:ID given is :" + MyElement_EQ.getAttribute("rdf:ID"));
								System.out.println("\t The Energy Consumer(LOAD)'s name is :" + MyElement_EQ.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent());	
				                System.out.println("\t The active power noted by 'P' is :" + MyElement_SSH.getElementsByTagName("cim:EnergyConsumer.p").item(0).getTextContent());
				                System.out.println("\t The reactive power noted by 'Q' is :" + MyElement_SSH.getElementsByTagName("cim:EnergyConsumer.q").item(0).getTextContent());
				                System.out.println("\t The rdf:ID of the Equipment container noted by the item 'equipmentContainer_rdf:ID' is :" + ((Element)MyElement_EQ.getElementsByTagName("cim:Equipment.EquipmentContainer").item(0)).getAttribute("rdf:resource"));
				                String rdfid = MyElement_EQ.getAttribute("rdf:ID");
				                String name =  MyElement_EQ.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();
				                String P = MyElement_SSH.getElementsByTagName("cim:EnergyConsumer.p").item(0).getTextContent();
				                String Q = MyElement_SSH.getElementsByTagName("cim:EnergyConsumer.q").item(0).getTextContent();
				                String rdfid_equicontainer = ((Element)MyElement_EQ.getElementsByTagName("cim:Equipment.EquipmentContainer").item(0)).getAttribute("rdf:resource");
				             
                                String insert = "INSERT INTO energyconsumer" + "(rdfid, name, p, Q, equipmentcontainer_rdfid)" + "VALUES(?,?,?,?,?)";
					               
				                
				                try {
									Pstmt = conn.prepareStatement(insert);
									Pstmt.setString(1, rdfid);
									Pstmt.setString(2, name);
									Pstmt.setString(3, P);
									Pstmt.setString(4, Q);
									Pstmt.setString(5, rdfid_equicontainer);
									
									Pstmt.executeUpdate();
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}	
				                
				                
				                
				                break search; 
							}
								
							}else {
								System.out.println("THe SSH Node's Type isn't an Element Node");
							}
						}
								
						}else {
							System.out.println("The EQ Node's Type isn't an Element Node");
						}
					}
				}
				
				//End of the method "ExtraNode_EnergyConsumer"
				
				
				//This is the method for CIM object:"Power Transformer End"
				//Beginning of the method "ExtraNode_PowerTransformerEnd"
				public static void ExtraNode_PowerTransformerEnd(NodeList MyNodeList,PreparedStatement Pstmt, Connection conn){
					System.out.println("List Length :" + MyNodeList.getLength());
					for (int temp = 0; temp < MyNodeList.getLength(); temp++){
						Node MyNode = MyNodeList.item(temp);
						System.out.println("\n Current Element :" + MyNode.getNodeName());
						if(MyNode.getNodeType()== Node.ELEMENT_NODE){
							Element MyElement = (Element)MyNode;
							System.out.println("\t rdf:ID given is :" + MyElement.getAttribute("rdf:ID"));
							System.out.println("\t The Power Transformer concerned is :" + MyElement.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent());		
							System.out.println("\t The impedance of the Power Transformer concerned is :" + MyElement.getElementsByTagName("cim:PowerTransformerEnd.r").item(0).getTextContent());
							System.out.println("\t The reactance of the Power Transformer concerned is :" + MyElement.getElementsByTagName("cim:PowerTransformerEnd.x").item(0).getTextContent());
			                System.out.println("\t The rdf:ID of the Power Transformer concerned noted by the item 'Transformer_rdf:ID' is :" + ((Element)MyElement.getElementsByTagName("cim:PowerTransformerEnd.PowerTransformer").item(0)).getAttribute("rdf:resource"));
			                System.out.println("\t The rdf:ID of the base Voltage noted by the item 'baseVoltage_rdf:ID' is :" + ((Element)MyElement.getElementsByTagName("cim:TransformerEnd.BaseVoltage").item(0)).getAttribute("rdf:resource"));
			                
			                String rdfid = MyElement.getAttribute("rdf:ID");
			                String name = MyElement.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();
			                String IMpedance = MyElement.getElementsByTagName("cim:PowerTransformerEnd.r").item(0).getTextContent();
			                String REactance =  MyElement.getElementsByTagName("cim:PowerTransformerEnd.x").item(0).getTextContent();
			                String rdfid_transformer = ((Element)MyElement.getElementsByTagName("cim:PowerTransformerEnd.PowerTransformer").item(0)).getAttribute("rdf:resource");
			                String rdfid_basevoltage = ((Element)MyElement.getElementsByTagName("cim:TransformerEnd.BaseVoltage").item(0)).getAttribute("rdf:resource");
			                
			                String insert = "INSERT INTO powertransformerend" + "(rdfid, name, transformer_r , transformer_x, transformer_rdfid, basevoltage_rdfid)" + "VALUES(?,?,?,?,?,?)";
				               
			                
			                try {
								Pstmt = conn.prepareStatement(insert);
								Pstmt.setString(1, rdfid);
								Pstmt.setString(2, name);
								Pstmt.setString(3, IMpedance);
								Pstmt.setString(4, REactance);
								Pstmt.setString(5, rdfid_transformer);
								Pstmt.setString(6, rdfid_basevoltage);
								Pstmt.executeUpdate();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}	
			                
			                
			                 
						}
					}
				}
		//End of the method ExtraNode_PowerTransformerEnd
				
				
				//This is the method for CIM object:"Breaker"
				//Beginning of the method "ExtraNode_Breaker"
				public static void ExtraNode_Breaker(NodeList MyNodeList_EQ, NodeList MyNodeList_SSH,PreparedStatement Pstmt, Connection conn){
					System.out.println("List Length from the file 'EQ':" + MyNodeList_EQ.getLength());
					System.out.println("List Length from the file 'SSH':" + MyNodeList_SSH.getLength());
					for (int temp = 0; temp < MyNodeList_EQ.getLength(); temp++){
						Node MyNode_EQ = MyNodeList_EQ.item(temp);
						System.out.println("\n Current Element :" + MyNode_EQ.getNodeName());
						if(MyNode_EQ.getNodeType()== Node.ELEMENT_NODE){
							Element MyElement_EQ = (Element)MyNode_EQ;
							search:
							for(int i = 0; i < MyNodeList_EQ.getLength();i++){
							Node MyNode_SSH = MyNodeList_SSH.item(i);
							if(MyNode_SSH.getNodeType()== Node.ELEMENT_NODE){
							Element MyElement_SSH = (Element)MyNode_SSH;
							while((MyElement_SSH.getAttribute("rdf:about")).equals("#" + MyElement_EQ.getAttribute("rdf:ID"))){
								System.out.println("\t rdf:ID given is :" + MyElement_EQ.getAttribute("rdf:ID"));
								System.out.println("\t The Breaker's name is :" + MyElement_EQ.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent());	
				                System.out.println("\t If the breaker is open(STATE) :" + MyElement_SSH.getElementsByTagName("cim:Switch.open").item(0).getTextContent());
				                System.out.println("\t The rdf:ID of the Equipment container noted by the item 'equipmentContainer_rdf:ID' is :" + ((Element)MyElement_EQ.getElementsByTagName("cim:Equipment.EquipmentContainer").item(0)).getAttribute("rdf:resource"));
				                String rdfid = MyElement_EQ.getAttribute("rdf:ID");
				                String name = MyElement_EQ.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();
				                String State = MyElement_SSH.getElementsByTagName("cim:Switch.open").item(0).getTextContent();
				                String rdfid_equicontainer = ((Element)MyElement_EQ.getElementsByTagName("cim:Equipment.EquipmentContainer").item(0)).getAttribute("rdf:resource");
				                
				                
				                String insert = "INSERT INTO BREAKER" + "(rdfid, name, state_open , equipmentContainer_rdfid)" + "VALUES(?,?,?,?)";
					               
				                
				                
				                try {
									Pstmt = conn.prepareStatement(insert);
									Pstmt.setString(1, rdfid);
									Pstmt.setString(2, name);
									Pstmt.setString(3, State);
									Pstmt.setString(4, rdfid_equicontainer);
									
								
									Pstmt.executeUpdate();
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}	
				                
				                
				                
				                
				                
				                break search; 
							}
								
							}else {
								System.out.println("THe SSH Node's Type isn't an Element Node");
							}
						}
								
						}else {
							System.out.println("The EQ Node's Type isn't an Element Node");
						}
					}
				}
				
				//End of the method "ExtraNode_Breaker"
				
				//This is the method for CIM object:"Ratio TapChanger"
				//Beginning of the method "ExtraNode_RatioTapChanger"
				public static void ExtraNode_RatioTapChanger(NodeList MyNodeList_EQ, NodeList MyNodeList_SSH, PreparedStatement Pstmt, Connection conn){
					System.out.println("List Length from the file 'EQ' :" + MyNodeList_EQ.getLength());
					System.out.println("List Length from the file 'SSH' :" + MyNodeList_SSH.getLength());
					for (int temp = 0; temp < MyNodeList_EQ.getLength(); temp++){
						Node MyNode_EQ = MyNodeList_EQ.item(temp);
						System.out.println("\n Current Element :" + MyNode_EQ.getNodeName());
						if(MyNode_EQ.getNodeType()== Node.ELEMENT_NODE){
							Element MyElement_EQ = (Element)MyNode_EQ;
							search:
							for(int i = 0; i < MyNodeList_EQ.getLength();i++){
							Node MyNode_SSH = MyNodeList_SSH.item(i);
							if(MyNode_SSH.getNodeType()== Node.ELEMENT_NODE){
							Element MyElement_SSH = (Element)MyNode_SSH;
							System.out.println("I'm here");
							System.out.println(MyElement_SSH.getAttribute("rdf:about"));
							System.out.println("55555555555");
							System.out.println("#" + MyElement_EQ.getAttribute("rdf:ID"));
							System.out.println((MyElement_SSH.getAttribute("rdf:about")).equals("#" + MyElement_EQ.getAttribute("rdf:ID")));
							if((MyElement_SSH.getAttribute("rdf:about")).equals("#" + MyElement_EQ.getAttribute("rdf:ID"))){
								System.out.println("Just check it");
							}
							while((MyElement_SSH.getAttribute("rdf:about")).equals("#" + MyElement_EQ.getAttribute("rdf:ID"))){
								System.out.println("\t rdf:ID given is :" + MyElement_EQ.getAttribute("rdf:ID"));
								System.out.println("\t The Ratio TapChanger's name is :" + MyElement_EQ.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent());	
				                System.out.println("\t The Step of the Ratio TapChanger is :" + MyElement_SSH.getElementsByTagName("cim:TapChanger.step").item(0).getTextContent());	
				                String rdfid = MyElement_EQ.getAttribute("rdf:ID");
				                String name = MyElement_EQ.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();
				                String step = MyElement_SSH.getElementsByTagName("cim:TapChanger.step").item(0).getTextContent();
				                
                                String insert = "INSERT INTO ratiotapchanger" + "(rdfid, NominalValue, step)" + "VALUES(?,?,?)";
					               
				                
				                
				                try {
									Pstmt = conn.prepareStatement(insert);
									Pstmt.setString(1, rdfid);
									Pstmt.setString(2, name);
									Pstmt.setString(3, step);
									
									
								
									Pstmt.executeUpdate();
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}	
				                
				               break search; 
							}
								
							}else {
								System.out.println("THe SSH Node's Type isn't an Element Node");
							}
						}
								
						}else {
							System.out.println("The EQ Node's Type isn't an Element Node");
						}
					}
				}
				
				//End of the method "ExtraNode_RatioTapChanger"
				
				
				
}
