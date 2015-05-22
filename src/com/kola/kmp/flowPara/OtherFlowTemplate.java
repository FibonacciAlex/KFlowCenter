package com.kola.kmp.flowPara;

import com.kola.kmp.util.UtilTools;

/**
 * 其他流水模板
 * @author Alex
 *
 */
public class OtherFlowTemplate extends FlowAbsInitTemplate{

	/**角色id*/
	private long roleID;
	
	/**其他流水类型*/
	private int otherType;
	
	/**流水产生时间*/
	private long recordTime;
	
	/**备注*/
	private String tips;

	
	
	
	public OtherFlowTemplate() {
	}

	public void initTemplate(String info) {

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

				} else if (keyStr.equals("otherType")) {
					this.setOtherFlowType(Integer.parseInt(valueStr));

				} else if (keyStr.equals("tips")) {
					this.setTips(valueStr);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void setRoleID(long roleID) {
		this.roleID = roleID;
	}

	public void setOtherFlowType(int flowType) {
		this.otherType = flowType;
	}

	public void setRecordTime(long recordTime) {
		this.recordTime = recordTime;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	/**
	 * 角色id
	 * @return
	 */
	public long getRoleID() {
		return roleID;
	}

	/**
	 * 流水类型   这里表示的是其他自定义流水类型
	 * @return
	 */
	public int getOtherFlowType() {
		return otherType;
	}

	/**
	 * 流水产生时间
	 * @return
	 */
	public long getRecordTime() {
		return recordTime;
	}

	
	/**
	 * 流水备注
	 * @return
	 */
	public String getTips() {
		return tips;
	}
	
	
	
	
}
