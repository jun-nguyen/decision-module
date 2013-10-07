package at.ac.tuwien.dsg.rSybl.cloudInteractionUnit.celar;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

import at.ac.tuwien.dsg.csdg.DependencyGraph;
import at.ac.tuwien.dsg.csdg.Node;
import at.ac.tuwien.dsg.csdg.Node.NodeType;
import at.ac.tuwien.dsg.csdg.Relationship;
import at.ac.tuwien.dsg.csdg.Relationship.RelationshipType;
import at.ac.tuwien.dsg.rSybl.cloudInteractionUnit.enforcementPlugins.interfaces.EnforcementInterface;
import at.ac.tuwien.dsg.rSybl.cloudInteractionUnit.utils.RuntimeLogger;
import at.ac.tuwien.dsg.rSybl.dataProcessingUnit.api.MonitoringAPIInterface;

public class EnforcementPluginCELAR implements EnforcementInterface {
	private MonitoringAPIInterface monitoringAPI;
	private Node cloudService;
	boolean cleanupGoingOn =false;
	public static String API_URL="http://83.212.117.112/celar-orchestrator/deployment/";
	
	public EnforcementPluginCELAR(Node cloudService){
		this.cloudService=cloudService;
		
	}
	public static String executeResizingCommand(String actionType){
		String ip = "";
		 URL url = null;
	        HttpURLConnection connection = null;
	        try {
	            url = new URL(API_URL+"resize/?action="+ actionType);
	       

	            InputStream is = url.openStream();
	            try {
	              BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

	              StringBuilder sb = new StringBuilder();


	              String cp = new String();

	              while((cp=rd.readLine())!=null){

	                  sb.append(cp);
	              }
	               
	              String jsonText = sb.toString();

	              JSONObject array = new JSONObject(jsonText);
	              System.out.println(array);
	              if (array.getJSONObject("1").getString("stderr").equalsIgnoreCase("")){
	              if ((array.getJSONObject("1").getString("stdout")).contains("Removing:"))
	              {
	            	  String strs[]=(array.getJSONObject("1").getString("stdout")).split("Removing: ");
	            	

	            	  return strs[strs.length-1];
	              }
	            	  else{
	            		  if((array.getJSONObject("1").getString("stdout")).contains("Adding: ")){
	            			  String strs[]=(array.getJSONObject("1").getString("stdout")).split("Adding: ");
	            			//  System.out.println(strs[strs.length-1].split("xss")[0]);
	    	            	 // System.out.println(strs[strs.length-1].split("xss")[1]);
	    	            	  return strs[strs.length-1].split("xss")[0];  
	            		  }
	            	  }
	              if (array.getJSONObject("1").getString("stdout").charAt(0)>='0'&&array.getJSONObject("1").getString("stdout").charAt(0)<='9'&& !array.getJSONObject("1").getString("stdout").contains(" "))
	            		  return array.getJSONObject("1").getString("stdout");
	              else
	            	  return "";
	              }else
	              {
	            	  RuntimeLogger.logger.error(array.getJSONObject("1").getString("stderr"));
	    	        try{
	    		              if ((array.getJSONObject("1").getString("stdout")).contains("Removing:"))
	    		              {
	    		            	  String strs[]=(array.getJSONObject("1").getString("stdout")).split("Removing: ");
	    		            	

	    		            	  return strs[strs.length-1];
	    		              }
	    		            	  else{
	    		            		  if((array.getJSONObject("1").getString("stdout")).contains("Adding: ")){
	    		            			  String strs[]=(array.getJSONObject("1").getString("stdout")).split("Adding: ");
	    		            			//  System.out.println(strs[strs.length-1].split("xss")[0]);
	    		    	            	 // System.out.println(strs[strs.length-1].split("xss")[1]);
	    		    	            	  return strs[strs.length-1].split("xss")[0];  
	    		            		  }
	    		            	  }
	    		    	        RuntimeLogger.logger.error("Error when calling orchestrator API for "+actionType+" error is "+array.getJSONObject("1").getString("stderr"));

	    		              if (array.getJSONObject("1").getString("stdout").charAt(0)>='0'&&array.getJSONObject("1").getString("stdout").charAt(0)<='9' && !array.getJSONObject("1").getString("stdout").contains(" "))
	    		            		  return array.getJSONObject("1").getString("stdout");
	    		              else
	    		            	  return "";
	    		              
	    	        }catch(Exception e){
		    	        RuntimeLogger.logger.error("Error when calling orchestrator API for "+actionType+" error is "+array.getJSONObject("1").getString("stderr"));
	    	        	return "";
	    	        }

	              }
	            } finally {
	              is.close();
	            }
	            
	        } catch (Exception e) {
	           // Logger.getLogger(MELA_API.class.getName()).log(Level.SEVERE, e.getMessage(), e);
	        	e.printStackTrace();
	        	Logger.getLogger(EnforcementPluginCELAR.class.getName()).log(Level.WARNING, "Trying to connect to the Orchestrator - failing ... . Retrying later");
	        	RuntimeLogger.logger.error("Failing to connect to Orchestrator");
	        	
	        } finally {
	            if (connection != null) {
	                connection.disconnect();
	            }
	        }
	        return "";
//		try{
//		Process p = Runtime.getRuntime().exec(command);                                                                                                                                                     
//		BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
//		String s ="";
//		while ((s = stdInput.readLine()) != null) {
//	        if (s.contains("Adding")){
//	        	String[] x=s.split("[ :]");
//	        	if (x.length>=2)
//	        	if (x[1].charAt(0)>='0'&&x[1].charAt(0)<='9'){
//	        		ip=x[1];
//	        	}
//	        }
//	        RuntimeLogger.logger.info("From scaling command " +s);
//		}
//		
//		if (ip.length()>0 && ip.charAt(0)>='0'&&ip.charAt(0)<='9'){
//			return ip;}
//		else
//		{
//			RuntimeLogger.logger.info("Answer from scale command "+ip+" ");
//			return "";
//		}
//		}catch(Exception e ){
//			RuntimeLogger.logger.info("Answer from scale command "+ip+" "+e.getMessage());
//			return "";
//		}
		
	}

	public static void main(String[] args){
		String ip=executeResizingCommand("addvm");
		if (!ip.equalsIgnoreCase("")){
			System.err.println(ip);
		}else{
			System.err.println("IP is empty "+ip);
		}

		//System.err.println(executeCommand("removevm"));
	}
	public void cleanup(){
		 URL url = null;
	        try {
	            url = new URL(API_URL+"resizestatus/?action=cleanup");            
	              
	              while (checkStatus("","cleanup")){
	      			try {
	      				Thread.sleep(10000);
	      			} catch (InterruptedException e) {
	      				// TODO Auto-generated catch block
	      				e.printStackTrace();
	      			}
	      		}
	        }catch(Exception e ){
	        	RuntimeLogger.logger.error("Error at cleanup"+e.getMessage()+" "+e.getCause());
	        }
	}
	public boolean checkStatus(String ip, String action){
		 URL url = null;
	        HttpURLConnection connection = null;
	        try {
	        	if (!ip.equalsIgnoreCase(""))
	            url = new URL(API_URL+"resizestatus/?action="+ action+"&ip="+ip);
	        	else
		            url = new URL(API_URL+"resizestatus/?action="+ action);


	            InputStream is = url.openStream();
	               BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

	              StringBuilder sb = new StringBuilder();


	              String cp = new String();

	              while((cp=rd.readLine())!=null){

	                  sb.append(cp);
	              }
	              JSONObject jsonObject=new JSONObject(sb);
	             String status= (String) jsonObject.get("finished");
	             if (status.equalsIgnoreCase("true"))return true;
	             else return false;
	            }catch(Exception e){
	            	RuntimeLogger.logger.error("Error when checking for status of "+ip+" with action "+action);
	            	return false;
	            }
	              
	              
	}
	@Override
	public void scaleOut(Node toBeScaled) {
		while (cleanupGoingOn){
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String ip = executeResizingCommand("addvm");
		while (checkStatus(ip,"addvm")){
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (!ip.equalsIgnoreCase("")){
			RuntimeLogger.logger.info("The IP of the Virtual Machine to be ADDED is "+ip);	
		DependencyGraph dependencyGraph=new DependencyGraph();
		dependencyGraph.setCloudService(cloudService);
			Node toAdd = dependencyGraph.getNodeWithID(toBeScaled.getId());
		Node newVM = new Node();
		newVM.setNodeType(NodeType.VIRTUAL_MACHINE);
		Relationship rel = new Relationship();
		rel.setSourceElement(toBeScaled.getId());
		rel.setTargetElement(ip);
		rel.setType(RelationshipType.HOSTED_ON_RELATIONSHIP);
		newVM.setId(ip);
		toAdd.addNode(newVM,rel);
		RuntimeLogger.logger.info("Cloud new service is "+dependencyGraph.graphToString());
		monitoringAPI.refreshServiceStructure(cloudService);
}else{
	RuntimeLogger.logger.error("IP is empty "+ip);
}
	}

	@Override
	public void scaleIn(Node toBeScaled) {
		while (cleanupGoingOn){
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		DependencyGraph d = new DependencyGraph();
		d.setCloudService(cloudService);
		if (d.getNodeWithID(toBeScaled.getId()).getAllRelatedNodesOfType(RelationshipType.HOSTED_ON_RELATIONSHIP,NodeType.VIRTUAL_MACHINE).size()>1){
			
		String ip = executeResizingCommand("removevm");
		while (checkStatus(ip,"removevm")){
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (!ip.equalsIgnoreCase("")){
			RuntimeLogger.logger.info("The IP of the Virtual Machine to be REMOVED is "+ip);	
			DependencyGraph dep = new DependencyGraph();
			dep.setCloudService(cloudService);
			Node toBeDel = dep.getNodeWithID(ip); 
			toBeScaled.removeNode(toBeDel);
			RuntimeLogger.logger.info("Cloud new service is "+dep.graphToString());
			monitoringAPI.refreshServiceStructure(cloudService);
		}
		}
	}

	@Override
	public List<String> getElasticityCapabilities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void enforceAction(String actionName, Node entity) {
	if (actionName.equalsIgnoreCase("cleanup")){
		cleanupGoingOn=true;
		cleanup();
		cleanupGoingOn=false;
	}
		
	}

	@Override
	public void setControlledService(Node controlledService) {
		cloudService=controlledService;
		
	}

	@Override
	public Node getControlledService() {
		return cloudService;
	}

	@Override
	public void setMonitoringPlugin(MonitoringAPIInterface monitoring) {
		monitoringAPI=monitoring;
	}

}