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


public class ReadXMLfile {

	public static void main(String[] args) {
		
		try {
			//This is for EQ 
			File inputFile_EQ = new File("src/dataset/MicroGridTestConfiguration_T1_BE_EQ_V2.xml");
			DocumentBuilderFactory As1EQ_Factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder As1EQ_Builder = As1EQ_Factory.newDocumentBuilder();
			Document As1EQ_Doc = As1EQ_Builder.parse(inputFile_EQ);
			As1EQ_Doc.getDocumentElement().normalize();
			System.out.println("Root element of the EQ file given is :" + As1EQ_Doc.getDocumentElement().getNodeName());
			//This is for SSH
			File inputFile_SSH = new File("src/dataset/MicroGridTestConfiguration_T1_BE_SSH_V2.xml");
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
			System.out.println("Initialization of the NodeList: Mission Complete !!!");
			//Call of the duty of the method to parse and extract the information from the File
			ExtraNode_BaseVoltage(NodeList_BaseVoltage);
			ExtraNode_Substation(NodeList_Substation);
			ExtraNode_VoltageLevel(NodeList_VoltageLevel);
			ExtraNode_GeneratingUnit(NodeList_GeneratingUnit);
			ExtraNode_SynchronousMachine(NodeList_EQ_SynchronousMachine,NodeList_SSH_SynchronousMachine);
			ExtraNode_RegulatingControl(NodeList_EQ_RegulatingControl, NodeList_SSH_RegulatingControl);
			ExtraNode_PowerTransformer(NodeList_PowerTransformer);
			ExtraNode_EnergyConsumer(NodeList_EQ_EnergyConsumer,NodeList_SSH_EnergyConsumer);
			ExtraNode_PowerTransformerEnd(NodeList_PowerTransformerEnd);
			ExtraNode_Breaker(NodeList_EQ_Breaker,NodeList_SSH_Breaker);
			ExtraNode_RatioTapChanger(NodeList_EQ_RatioTapChanger,NodeList_SSH_RatioTapChanger);
			
			
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
				public static void ExtraNode_EnergyConsumer(NodeList MyNodeList_EQ, NodeList MyNodeList_SSH){
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
				public static void ExtraNode_PowerTransformerEnd(NodeList MyNodeList){
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
						}
					}
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
