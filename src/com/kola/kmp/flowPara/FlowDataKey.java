package com.kola.kmp.flowPara;

public class FlowDataKey {
	
	
	/**流水类型*/
	private int flowType;
	
	/**流水日志对应区号*/
	private int areaID;

	public FlowDataKey(int flowType, int areaID) {
		this.flowType = flowType;
		this.areaID = areaID;
	}

	public int getFlowType() {
		return flowType;
	}

	public int getAreaID() {
		return areaID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + flowType;
		result = prime * result + areaID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FlowDataKey other = (FlowDataKey) obj;
		if (flowType != other.flowType)
			return false;
		if (areaID != other.areaID)
			return false;
		return true;
	}
	
	
	
	

}
