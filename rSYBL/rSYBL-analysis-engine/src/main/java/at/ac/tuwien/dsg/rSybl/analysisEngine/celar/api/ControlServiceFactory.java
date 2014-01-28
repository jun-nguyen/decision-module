package at.ac.tuwien.dsg.rSybl.analysisEngine.celar.api;

import at.ac.tuwien.dsg.rSybl.analysisEngine.utils.AnalysisLogger;


public class ControlServiceFactory {
	private static ControlService controlService;
	public static ControlService getControlServiceInstance(){

		if (controlService==null){
			controlService=new ControlService();
		}
		
		return controlService;
	}
}
