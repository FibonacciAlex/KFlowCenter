package com.kola.kmp.flowPara;

import com.kola.kmp.util.UtilTools;


/**
 * 资产修改模板
 * @author Alex
 * 
 */
public class TreasureModifyRecordTemplate extends FlowAbsInitTemplate{

	/**角色ID*/
	private long roleID;
	
	/**资产UUID*/
	private String UUID;
	
	/**流水产生时间*/
	private long recordTime;
	
	/**备注*/
	private String tips;
	

	public TreasureModifyRecordTemplate() {
	}

	
	
	public void  initTemplate(String info){

		if (info.length() == 0) {
			return;
		}

		try {
			String[] strSplit = info.split(",");
			for (String string : strSplit) {
				String[] subSplit = string.split("=");
				String keyStr = subSplit[0];

				if (subSplit.length < 2) {
					continue;
				}

				String valueStr = subSplit[1];

				if (keyStr.contains("logType")) {
					String date = keyStr.substring(0, keyStr.indexOf("."));
					long recordTime = UtilTools.paresDateStringToLong(date);
					this.setRecordTime(recordTime);

				} else if (keyStr.equals("tips")) {
					this.setTips(valueStr);

				} else if (keyStr.equals("uuid")) {
					this.setUUID(valueStr);

				} else if (keyStr.equals("roleId")) {
					this.setRoleID(Long.parseLong(valueStr));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

	
	
	
	
	public long getRoleID() {
		return roleID;
	}



	public void setRoleID(long roleID) {
		this.roleID = roleID;
	}



	public void setUUID(String uUID) {
		UUID = uUID;
	}



	public void setRecordTime(long recordTime) {
		this.recordTime = recordTime;
	}



	public void setTips(String tips) {
		this.tips = tips;
	}



	/**
	 * 资产UUID
	 * @return
	 */
	public String getUUID() {
		return UUID;
	}

	/**
	 * 流水产生时间
	 * @return
	 */
	public long getRecordTime() {
		return recordTime;
	}

	/**
	 * 备注
	 * @return
	 */
	public String getTips() {
		return tips;
	}
	
}
