package com.kola.kmp.flowPara;

import com.kola.kmp.util.UtilTools;

/**
 * 经验流水模板
 * @author Alex
 *
 */
public class ExpFlowTemplate extends FlowAbsInitTemplate{

	/**角色id*/
	private long roleID;
	
	/**增加的经验值*/
	private int value;
	
	/**流水产生时间*/
	private long recordTime;
	
	/**备注*/
	private String tips;

	public ExpFlowTemplate() {
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

				} else if (keyStr.equals("roleId")) {
					this.setRoleID(Long.parseLong(valueStr));

				} else if (keyStr.equals("addExp")) {
					this.setValue(Integer.parseInt(valueStr));

				} else if (keyStr.equals("tips")) {
					this.setTips(valueStr);

				}

			}
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		
	}
	
	
	
	
	public void setRoleID(long roleID) {
		this.roleID = roleID;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public void setRecordTime(long recordTime) {
		this.recordTime = recordTime;
	}
	public void setTips(String tips) {
		this.tips = tips;
	}
	/**
	 * 获取角色id
	 * @return
	 */
	public long getRoleID() {
		return roleID;
	}

	/**
	 * 获取改变的经验值
	 * @return
	 */
	public int getValue() {
		return value;
	}

	/**
	 * 获取流水记录时间
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
