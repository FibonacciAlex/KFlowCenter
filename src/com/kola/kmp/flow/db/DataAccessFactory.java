package com.kola.kmp.flow.db;

import com.kola.kmp.flow.db.dataaccess.CreateDBDataAccess;
import com.kola.kmp.flow.db.dataaccess.FlowDataAccess;
import com.kola.kmp.flow.db.dataaccess.impl.CreateDBDataAccessImpl;
import com.kola.kmp.flow.db.dataaccess.impl.FlowDataAccessImpl;

public class DataAccessFactory {

	private static CreateDBDataAccess createDBDataAccess;
	
	private static FlowDataAccess flowDataAccess;

	public static CreateDBDataAccess getCreateDBDataAccess() {
		if(createDBDataAccess == null){
			createDBDataAccess = new CreateDBDataAccessImpl();
		}
		return createDBDataAccess;
	}

	public static FlowDataAccess getFlowDataAccess() {
		if(flowDataAccess == null){
			flowDataAccess = new FlowDataAccessImpl();
		}
		return flowDataAccess;
	}
	
	
	
}
