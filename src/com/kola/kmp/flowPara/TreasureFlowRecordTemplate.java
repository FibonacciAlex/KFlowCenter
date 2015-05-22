package com.kola.kmp.flowPara;


import com.kola.kmp.util.UtilTools;



/**
 * 资产进出记录模板
 * @author Alex
 *
 */
public class TreasureFlowRecordTemplate extends FlowAbsInitTemplate{
	
	/**资产所有者id*/
	private long ownerID;
	
	/**资产的UUID*/
	private String UUID;
	
	/**资产类型*/
	private int properType;
	
	/**资产模板ID*/
	private String treasureTemplateID;
	
	/**是否增加 1=增加  0=减少*/
	private int isAdd;
	
	/**流水产生时间*/
	private long recordTime;

	/**备注*/
	private String tips;
	
	
	
	public TreasureFlowRecordTemplate() {
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
					this.setOwnerID(Long.parseLong(valueStr));

				} else if (keyStr.equals("uuid")) {
					this.setUUID(valueStr);

				} else if (keyStr.equals("propertyType")) {
					this.setProperType(Integer.parseInt(valueStr));

				} else if (keyStr.equals("tips")) {
					this.setTips(valueStr);

				} else if (keyStr.equals("tempId")) {
					this.setTreasureTemplateID(valueStr);

				} else if (keyStr.equals("isAdd")) {
					this.setIsAdd(Integer.parseInt(valueStr));

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	
	
	public void setOwnerID(long ownerID) {
		this.ownerID = ownerID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}

	public void setProperType(int properType) {
		this.properType = properType;
	}

	public void setTreasureTemplateID(String treasureTemplateID) {
		this.treasureTemplateID = treasureTemplateID;
	}

	public void setIsAdd(int isAdd) {
		this.isAdd = isAdd;
	}

	public void setRecordTime(long recordTime) {
		this.recordTime = recordTime;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	/**
	 * 资产所有者id
	 * @return
	 */
	public long getOwnerID() {
		return ownerID;
	}

	/**
	 * 资产UUID
	 * @return
	 */
	public String getUUID() {
		return UUID;
	}

	/**
	 * 获取资产模板id
	 * @return
	 */
	public String getTreasureTemplateID() {
		return treasureTemplateID;
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


	/**
	 * 获取资产类型
	 * @return
	 */
	public int getProperType() {
		return properType;
	}

	public int getIsAdd() {
		return isAdd;
	}
	
	
	
	
	
	
	
}
