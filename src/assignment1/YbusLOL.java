package assignment1;


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


public class YbusLOL {

	public static void main(String[] args) {
		
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
			DocumentBuilder As1SSH_Builder = As1EQ_Factory.newDocumentBuilder();
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
			
			NodeList NodeList_EQ_LinearShuntCompensator = As1EQ_Doc.getElementsByTagName("cim:LinearShuntCompensator");
			NodeList NodeList_EQ_ACLineSegment= As1EQ_Doc.getElementsByTagName("cim:ACLineSegment");
			System.out.println("Initialization of the NodeList: Mission Complete !!!");
			
			
			//Call of the duty of the method to parse and extract the information from the File
			ExtraNode_BaseVoltage(NodeList_BaseVoltage);
			ExtraNode_Substation(NodeList_Substation);
			ExtraNode_VoltageLevel(NodeList_VoltageLevel);
			ExtraNode_GeneratingUnit(NodeList_GeneratingUnit);
			ExtraNode_SynchronousMachine(NodeList_EQ_SynchronousMachine,NodeList_SSH_SynchronousMachine);
			ExtraNode_RegulatingControl(NodeList_EQ_RegulatingControl, NodeList_SSH_RegulatingControl);
			ExtraNode_PowerTransformer(NodeList_PowerTransformer);
			//ExtraNode_EnergyConsumer(NodeList_EQ_EnergyConsumer,NodeList_SSH_EnergyConsumer);
			//ExtraNode_PowerTransformerEnd(NodeList_PowerTransformerEnd);
			ExtraNode_Breaker(NodeList_EQ_Breaker,NodeList_SSH_Breaker);
			ExtraNode_RatioTapChanger(NodeList_EQ_RatioTapChanger,NodeList_SSH_RatioTapChanger);
			//ExtraNode_LinearShuntCompensator(NodeList_EQ_LinearShuntCompensator);
			ExtraNode_ACLineSegment(NodeList_EQ_ACLineSegment);
			
			
			
			//Setting of the base voltage and power
			double S_base = 1000;
			double[] U_base = {10.5, 110, 380, 225};
			//Calculating the base impedance within each voltage level section
			double[] Z_base = new double[4];
			for(int q=0;q<4;q++){
				Z_base[q] = (U_base[q] * U_base[q])/ S_base;
			}
			//The Thevenin equivalent impedance is neglected from this exercise.
			//Calculate the per-unit value of the transformer 
			
			Transformerend end = ExtraNode_PowerTransformerEnd(NodeList_PowerTransformerEnd);
			double[] Tr_r = end.getarray1();
			double[] Tr_x = end.getarray2();
			for(int w=0;w<Tr_r.length;w++){
				System.out.println("ooooooo,Let's try to see: The number " + w + "of the real array is :" + Tr_r[w]);
				System.out.println("ooooooo,Let's try to see: The number " + w + "of the imaginary array is :" + Tr_x[w]);
			}
			double[] y_Trpu_real = new double[3];
			double[] y_Trpu_imag = new double[3];
			
		    y_Trpu_real[0] = (Z_base[2] * Tr_r[0])/ (Tr_r[0] * Tr_r[0] + Tr_x[0] * Tr_x[0]);
		    y_Trpu_real[1] = (Z_base[3] * Tr_r[1])/ (Tr_r[1] * Tr_r[1] + Tr_x[1] * Tr_x[1]);
		    y_Trpu_real[2] = (Z_base[1] * Tr_r[2])/ (Tr_r[2] * Tr_r[2] + Tr_x[2] * Tr_x[2]);
		    
		    y_Trpu_imag[0] = -(Z_base[2] * Tr_x[0])/ (Tr_r[0] * Tr_r[0] + Tr_x[0] * Tr_x[0]);
		    y_Trpu_imag[1] = -(Z_base[3] * Tr_x[1])/ (Tr_r[1] * Tr_r[1] + Tr_x[1] * Tr_x[1]);
		    y_Trpu_imag[2] = -(Z_base[1] * Tr_x[2])/ (Tr_r[2] * Tr_r[2] + Tr_x[2] * Tr_x[2]);
			
			// Calculate the per unit value of the load:
		    
			Energyconsumer load = ExtraNode_EnergyConsumer(NodeList_EQ_EnergyConsumer,NodeList_SSH_EnergyConsumer);
			double[] load_P = load.getarray1();
			double[] load_Q = load.getarray2();
			for(int w=0; w< load_P.length; w++){
				System.out.println("The number :" + w +"of the load active power array is: " + load_P[w]);
				System.out.println("The number :" + w +"of the load active power array is: " + load_Q[w]);
			}
			
		    double[] y_Load_realpu = new double[3];
		    double[] y_Load_imagpu = new double[3];
		    for(int w=0; w<3; w++){
		    	System.out.println(w);
		    	y_Load_realpu[w] = load_P[w]/ S_base;
		    	y_Load_imagpu[w] = -load_Q[w]/ S_base;
		    	System.out.println(w);
		    }
			System.out.println(y_Load_realpu[0] + ";" + y_Load_realpu[1] + ";" + y_Load_realpu[2] );
			System.out.println(y_Load_imagpu[0] + ";" + y_Load_imagpu[1] + ";" + y_Load_imagpu[2] );
			
			
			// Calculate the per unit value of the shunt capacitance
			double[] shunt = ExtraNode_LinearShuntCompensator(NodeList_EQ_LinearShuntCompensator);
			System.out.println(shunt[0] + "; " + shunt[1]);
			
			double[] y_shunt_imagpu = new double[2];
			y_shunt_imagpu[0] = shunt[0] * Z_base[1];
			y_shunt_imagpu[1] = shunt[1] * Z_base[2];
			
			//Calculate the per unit value of the Ac transmission line
			
			ACLineSegment ACL = ExtraNode_ACLineSegment(NodeList_EQ_ACLineSegment);
			double[] ACL_r = ACL.getarray1();
			double[] ACL_x = ACL.getarray2();
			double[] ACL_bch = ACL.getarray3() ;
			double[] ACL_length = ACL.getarray4();
			for(int w=0; w<2; w++){
				System.out.println("let's seeeeeeee the resistance of the ac line" + ACL_r[w]);
				System.out.println("let's seeeeeeee the reactance of the ac line" + ACL_x[w]);
				System.out.println("let's seeeeeeee the shunt capacitance of the AC line" + ACL_bch[w]);
				System.out.println("let's seeeeeeee the length of the AC line" + ACL_length[w]);
				
			}
			double[] y_ACL_realpu = new double [2];
			double[] y_ACL_imagpu = new double [2];
			double[] y_ACL_bchpu = new double[2];
			
			
			for(int w=0; w<2;w++){
		         y_ACL_realpu[w] = (Z_base[3] * ACL_r[w]) / (ACL_r[w] * ACL_r[w] + ACL_x[w] * ACL_x[w]);
		         y_ACL_imagpu[w] = -(Z_base[3] * ACL_x[w]) / (ACL_r[w] * ACL_r[w] + ACL_x[w] * ACL_x[w]);
		         y_ACL_bchpu[w] = (ACL_bch[w]/2.0) * Z_base[3];
		}
			
			
			
			/*for(int w=0; w<2;w++){
			         y_ACL_realpu[w] = (Z_base[3] * ACL_r[w]) / ((ACL_length[w]) * (ACL_r[w] * ACL_r[w] + ACL_x[w] * ACL_x[w]));
			         y_ACL_imagpu[w] = -(Z_base[3] * ACL_x[w]) / ((ACL_length[w]) * (ACL_r[w] * ACL_r[w] + ACL_x[w] * ACL_x[w]));
			         y_ACL_bchpu[w] = ((ACL_length[w] * ACL_bch[w])/2.0) * Z_base[3];
			}*/
			
			
			
		    // Computing the Y_matrix:
			//First we need to calculate for each element of the matrix and then print it out!
			double Y_11_real = y_Trpu_real[0] ;
			double Y_11_imag = y_Trpu_imag[0] + y_shunt_imagpu[1];
			
			double Y_16_real = -y_Trpu_real[0];
			double Y_16_imag = -y_Trpu_imag[0];
			
			double Y_22_real = y_Load_realpu[2] +y_ACL_realpu[0] + y_ACL_realpu[1] ;
			double Y_22_imag = y_Load_imagpu[2] + y_ACL_imagpu[0] + y_ACL_imagpu[1] + y_ACL_bchpu[0] + y_ACL_bchpu[1];
			
			double Y_23_real = -(y_ACL_realpu[0] + y_ACL_realpu[1]);
			double Y_23_imag = -(y_ACL_imagpu[0] + y_ACL_imagpu[1] + y_ACL_bchpu[0] + y_ACL_bchpu[1]);
			
			double Y_33_real = y_ACL_realpu[0] + y_ACL_realpu[1] + y_Load_realpu[1] + y_Trpu_real[1];
			double Y_33_imag = y_ACL_imagpu[0] + y_ACL_imagpu[1] + y_Load_imagpu[1] + y_Trpu_imag[1] + y_ACL_bchpu[0] + y_ACL_bchpu[1];
			
			System.out.println("acline 0 pu is " + y_ACL_realpu[0] );
			System.out.println("acline 1 pu is " + y_ACL_realpu[1] );
			System.out.println("load   1 pu is " + y_Load_realpu[1] );
			System.out.println("transformer 1 pu is " + y_Trpu_real[1]);
			
			double Y_36_real  = -y_Trpu_real[1];
			double Y_36_imag = -y_Trpu_imag[1];
			
			double Y_44_real = y_Trpu_real[2];
			double Y_44_imag = y_Trpu_imag[2];
			
			double Y_46_real = -Y_44_real;
			double Y_46_imag = -Y_44_imag;
			
			double Y_66_real = y_Trpu_real[2] + y_Trpu_real[1] + y_Trpu_real[0] + y_Load_realpu[0];
			double Y_66_imag = 	y_Trpu_imag[2] + y_Trpu_imag[1] + y_Trpu_imag[0] + y_Load_imagpu[0]	+ y_shunt_imagpu[0];
			
			String[][] Y_bus = new String[6][6];
			//Initialization of the Y-bus_matrix
			Y_bus[0][0] = Y_11_real + "" + Y_11_imag + "i ";
			System.out.println(Y_bus[0][0] + " we success! ");
			Y_bus[0][4] = Y_16_real + "+" + Y_16_imag + "i ";
			Y_bus[1][1] = Y_22_real + "" + Y_22_imag + "i ";
			Y_bus[1][2] = Y_23_real + "+" + Y_23_imag + "i ";
			Y_bus[2][1] = Y_23_real + "+" + Y_23_imag + "i ";
			Y_bus[2][2] = Y_33_real + "" + Y_33_imag + "i ";
			Y_bus[2][4] = Y_36_real + "+" + Y_36_imag + "i ";
			Y_bus[3][3] = Y_44_real + "" + Y_44_imag + "i ";
			Y_bus[3][4] = Y_46_real + "+" + Y_46_imag + "i ";
			Y_bus[4][0] = Y_16_real + "+" + Y_16_imag + "i ";
			Y_bus[4][2] = Y_bus[2][4];
			Y_bus[4][3] = Y_bus[3][4];
			Y_bus[4][4] = Y_66_real + "" + Y_66_imag + "i ";
			Y_bus[0][1] = "0"; Y_bus[0][2] = "0";Y_bus[0][3] = "0";
			Y_bus[1][0] = "0"; Y_bus[1][3] = "0"; Y_bus[1][4] = "0";
			Y_bus[2][0] = "0"; Y_bus[2][3] = "0"; Y_bus[0][1] = "0";
			Y_bus[3][0] = "0"; Y_bus[3][1] = "0"; Y_bus[3][2] = "0";
			Y_bus[4][1] = "0";
			
			
			System.out.println("Now it's time to see the magic:");
			for(int i=0; i<5;i++){
				for(int j=0; j<5; j++){
					System.out.print("  \t" + Y_bus[i][j]);
				}
				System.out.println("");	
			} 
			
		} catch(Exception e){
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
          
	}

	//This is the method for CIM object:"Base Voltage"
	//Beginning of the method "ExtraNode_BaseVoltage"
	public static void ExtraNode_BaseVoltage(NodeList MyNodeList){
		System.out.println("List Length :" + MyNodeList.getLength());
		for (int temp = 0; temp < MyNodeList.getLength(); temp++){
			Node MyNode = MyNodeList.item(temp);
			System.out.println("\n Current Element :" + MyNode.getNodeName());
			if(MyNode.getNodeType()== Node.ELEMENT_NODE){
				Element MyElement = (Element)MyNode;
				System.out.println("\t rdf:ID given is :" + MyElement.getAttribute("rdf:ID"));
				System.out.println("\t Nominal Value of the Base Voltage is :" + MyElement.getElementsByTagName("cim:BaseVoltage.nominalVoltage").item(0).getTextContent());
				
			}
		}
	}
	//End of the method "ExtraNode_BaseVoltage"
	
	
	//This is the method for CIM object:"Linear Shunt Compensator"
		//Beginning of the method "ExtraNode_LinearShuntCompensator"
		public static double[] ExtraNode_LinearShuntCompensator(NodeList MyNodeList){
			System.out.println("List Length :" + MyNodeList.getLength());
			double[] shunt = new double[2];
			for (int temp = 0; temp < MyNodeList.getLength(); temp++){
				Node MyNode = MyNodeList.item(temp);
				System.out.println("\n Current Element :" + MyNode.getNodeName());
				if(MyNode.getNodeType()== Node.ELEMENT_NODE){
					Element MyElement = (Element)MyNode;
					System.out.println("\t rdf:ID given is :" + MyElement.getAttribute("rdf:ID"));
					System.out.println("\t The shunt capacitance is :" + MyElement.getElementsByTagName("cim:LinearShuntCompensator.bPerSection").item(0).getTextContent());
					shunt[temp] = Double.parseDouble(MyElement.getElementsByTagName("cim:LinearShuntCompensator.bPerSection").item(0).getTextContent());
				}
			} return shunt;
		}
		//End of the method "ExtraNode_LinearShuntCompensator"
	
	
		//This is the method for CIM object:"ACLineSegment"
				//Beginning of the method "ExtraNode_ACLineSegment"
				public static ACLineSegment ExtraNode_ACLineSegment(NodeList MyNodeList){
					System.out.println("List Length :" + MyNodeList.getLength());
					double[] AC_r = new double[2];
					double[] AC_x = new double[2];
					double[] AC_bch = new double[2];
					double[] AC_length = new double[2];
					for (int temp = 0; temp < MyNodeList.getLength(); temp++){
						Node MyNode = MyNodeList.item(temp);
						System.out.println("\n Current Element :" + MyNode.getNodeName());
						if(MyNode.getNodeType()== Node.ELEMENT_NODE){
							Element MyElement = (Element)MyNode;
							System.out.println("\t rdf:ID given is :" + MyElement.getAttribute("rdf:ID"));
							System.out.println("\t The ACLine is :" + MyElement.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent());
							System.out.println("\t The shunt capacitance of the line is :" + MyElement.getElementsByTagName("cim:ACLineSegment.bch").item(0).getTextContent());            
							System.out.println("\t The resistance is :" + MyElement.getElementsByTagName("cim:ACLineSegment.r").item(0).getTextContent());
							System.out.println("\t The reactance is :" + MyElement.getElementsByTagName("cim:ACLineSegment.x").item(0).getTextContent());
							AC_length[temp] = Double.parseDouble(MyElement.getElementsByTagName("cim:Conductor.length").item(0).getTextContent());
					        AC_r[temp] = Double.parseDouble(MyElement.getElementsByTagName("cim:ACLineSegment.r").item(0).getTextContent());
					        AC_x[temp] = Double.parseDouble(MyElement.getElementsByTagName("cim:ACLineSegment.x").item(0).getTextContent());
						    AC_bch[temp] = Double.parseDouble(MyElement.getElementsByTagName("cim:ACLineSegment.bch").item(0).getTextContent());
						}
					} return new ACLineSegment(AC_r, AC_x, AC_bch, AC_length);
				}
				//End of the method "ExtraNode_ACLineSegment"
		
		
		
		
		
	
	
	
	
	//This is the method for CIM object:"Substation"
	//Beginning of the method "ExtraNode_Substation"
	public static void ExtraNode_Substation(NodeList MyNodeList){
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
                
			}
		}
	}
	//End of the method "ExtraNodee_Substation"
	
	//This is the method for CIM object:"Voltage Level"
	//Beginning of the method "ExtraNode_VoltageLevel"
	public static void ExtraNode_VoltageLevel(NodeList MyNodeList){
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
			}
		}
	}
	//End of the method "ExtraNode_VoltageLevel"
	
	//This is the method for CIM object:"Generating Unit"
		//Beginning of the method "ExtraNode_GeneratingUnit"
		public static void ExtraNode_GeneratingUnit(NodeList MyNodeList){
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
	                
				}
			}
		}
		//End of the method "ExtraNode_GeneratingUnit"
	
	
		//This is the method for CIM object:"Synchronous Machine"
				//Beginning of the method "ExtraNode_SynchronousMachine"
		public static void ExtraNode_SynchronousMachine(NodeList MyNodeList_EQ, NodeList MyNodeList_SSH){
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
				public static void ExtraNode_RegulatingControl(NodeList MyNodeList_EQ, NodeList MyNodeList_SSH){
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
				public static void ExtraNode_PowerTransformer(NodeList MyNodeList){
					System.out.println("List Length :" + MyNodeList.getLength());
					for (int temp = 0; temp < MyNodeList.getLength(); temp++){
						Node MyNode = MyNodeList.item(temp);
						System.out.println("\n Current Element :" + MyNode.getNodeName());
						if(MyNode.getNodeType()== Node.ELEMENT_NODE){
							Element MyElement = (Element)MyNode;
							System.out.println("\t rdf:ID given is :" + MyElement.getAttribute("rdf:ID"));
							System.out.println("\t The Power Transformer is :" + MyElement.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent());			
			                System.out.println("\t The rdf:ID of the Equipment container noted by the item 'equipmentContainer_rdf:ID' is :" + ((Element)MyElement.getElementsByTagName("cim:Equipment.EquipmentContainer").item(0)).getAttribute("rdf:resource"));
						}
					}
				}
				//End of the method "ExtraNode_PowerTransformer"
				
				//This is the method for CIM object:"Energy Consumer(Load)"
				//Beginning of the method "ExtraNode_EnergyConsumer"
				public static Energyconsumer ExtraNode_EnergyConsumer(NodeList MyNodeList_EQ, NodeList MyNodeList_SSH){
					System.out.println("List Length from the file 'EQ':" + MyNodeList_EQ.getLength());
					System.out.println("List Length from the file 'SSH':" + MyNodeList_SSH.getLength());
					double[] P = new double[3];
					double[] Q = new double[3];
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
								System.out.println("The number is :" + temp + "\t rdf:ID given is :" + MyElement_EQ.getAttribute("rdf:ID"));
								System.out.println("\t The Energy Consumer(LOAD)'s name is :" + MyElement_EQ.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent());	
				                System.out.println("\t The active power noted by 'P' is :" + MyElement_SSH.getElementsByTagName("cim:EnergyConsumer.p").item(0).getTextContent());
				                System.out.println("\t The reactive power noted by 'Q' is :" + MyElement_SSH.getElementsByTagName("cim:EnergyConsumer.q").item(0).getTextContent());
				                System.out.println("\t The rdf:ID of the Equipment container noted by the item 'equipmentContainer_rdf:ID' is :" + ((Element)MyElement_EQ.getElementsByTagName("cim:Equipment.EquipmentContainer").item(0)).getAttribute("rdf:resource"));
				                P[temp] = Double.parseDouble(MyElement_SSH.getElementsByTagName("cim:EnergyConsumer.p").item(0).getTextContent());
			                	Q[temp] = Double.parseDouble(MyElement_SSH.getElementsByTagName("cim:EnergyConsumer.q").item(0).getTextContent());
				                
				                break search; 
							}
								
							}else {
								System.out.println("THe SSH Node's Type isn't an Element Node");
							}
						}
								
						}else {
							System.out.println("The EQ Node's Type isn't an Element Node");
						}
					} return new Energyconsumer(P, Q);
					
				}
				
				//End of the method "ExtraNode_EnergyConsumer"
				
				
				//This is the method for CIM object:"Power Transformer End"
				//Beginning of the method "ExtraNode_PowerTransformerEnd"
				public static Transformerend ExtraNode_PowerTransformerEnd(NodeList MyNodeList){
					System.out.println("List Length :" + MyNodeList.getLength());
					double[] real = new double[3];
						double[] imag = new double[3];
					
					for (int temp = 0; temp < MyNodeList.getLength(); temp++){
						Node MyNode = MyNodeList.item(temp);
						System.out.println("\n Current Element :" + MyNode.getNodeName());
						
						if(MyNode.getNodeType()== Node.ELEMENT_NODE){
							Element MyElement = (Element)MyNode;
							System.out.println("\t rdf:ID given is :" + MyElement.getAttribute("rdf:ID"));
							System.out.println("\t The Power Transformer concerned is :" + MyElement.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent());		
							System.out.println("\t The resistance of the Power Transformer concerned is :" + MyElement.getElementsByTagName("cim:PowerTransformerEnd.r").item(0).getTextContent());
							System.out.println("\t The reactance of the Power Transformer concerned is :" + MyElement.getElementsByTagName("cim:PowerTransformerEnd.x").item(0).getTextContent());
			                System.out.println("\t The rdf:ID of the Power Transformer concerned noted by the item 'Transformer_rdf:ID' is :" + ((Element)MyElement.getElementsByTagName("cim:PowerTransformerEnd.PowerTransformer").item(0)).getAttribute("rdf:resource"));
			                System.out.println("\t The rdf:ID of the base Voltage noted by the item 'baseVoltage_rdf:ID' is :" + ((Element)MyElement.getElementsByTagName("cim:TransformerEnd.BaseVoltage").item(0)).getAttribute("rdf:resource"));
			                if((MyElement.getAttribute("rdf:ID")).equals("_49ca3fd4-1b54-4c5b-83fd-4dbd0f9fec9d")){
			                	real[0] = Double.parseDouble(MyElement.getElementsByTagName("cim:PowerTransformerEnd.r").item(0).getTextContent());
			                	imag[0] = Double.parseDouble(MyElement.getElementsByTagName("cim:PowerTransformerEnd.x").item(0).getTextContent());
			                	System.out.println("QWLLLLLL, The resistance of the transformer tr2_1 is: " + real[0]);
			                	System.out.println("QWLLLLLL, The reactance of the transformer tr2_1 is: " + imag[0]);
			                }
			                
			                if((MyElement.getAttribute("rdf:ID")).equals("_81a18364-0397-48d3-b850-22a0e34b410f")){
			                	real[1] = Double.parseDouble(MyElement.getElementsByTagName("cim:PowerTransformerEnd.r").item(0).getTextContent());
			                	imag[1] = Double.parseDouble(MyElement.getElementsByTagName("cim:PowerTransformerEnd.x").item(0).getTextContent());
			                	System.out.println("QWLLLLLL, The resistance of the transformer tr2_2 is: " + real[1]);
			                	System.out.println("QWLLLLLL, The reactance of the transformer tr2_2 is: " + imag[1]);
			                }
			                
			                if((MyElement.getAttribute("rdf:ID")).equals("_f58281c5-862a-465e-97ec-d809be6e24ab")){
			                	real[2] = Double.parseDouble(MyElement.getElementsByTagName("cim:PowerTransformerEnd.r").item(0).getTextContent());
			                	imag[2] = Double.parseDouble(MyElement.getElementsByTagName("cim:PowerTransformerEnd.x").item(0).getTextContent());
			                	System.out.println("QWLLLLLL, The resistance of the transformer tr2_3 is: " + real[2]);
			                	System.out.println("QWLLLLLL, The reactance of the transformer tr2_3 is: " + imag[2]);
			                }
			                 
						}
					}
					return new Transformerend(real, imag);
					
				}
		//End of the method ExtraNode_PowerTransformerEnd
				
				
				//This is the method for CIM object:"Breaker"
				//Beginning of the method "ExtraNode_Breaker"
				public static void ExtraNode_Breaker(NodeList MyNodeList_EQ, NodeList MyNodeList_SSH){
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
				public static void ExtraNode_RatioTapChanger(NodeList MyNodeList_EQ, NodeList MyNodeList_SSH){
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
