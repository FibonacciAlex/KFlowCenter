package com.kola.kmp.flowPara;

import com.kola.kmp.util.UtilTools;


/**
 * 货币流水记录表
 * @author Alex
 *
 */
public class CurrencyFlowRecordTemplate extends FlowAbsInitTemplate {
	
	/**货币所有者id*/
	private long ownerID;
	
	/**货币类型*/
	private int currencyType;
	
	/**货币金额*/
	private long value;
	
	/**流水类型，标志进或出*/
	private int isAdd;
	
	/**流水产生时间*/
	private long recordTime;
	
	/**备注*/
	private String tips;

	public CurrencyFlowRecordTemplate() {
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
					this.setOwnerID(Long.parseLong(valueStr));

				} else if (keyStr.equals("moneyType")) {
					this.setCurrencyType(Integer.parseInt(valueStr));

				} else if (keyStr.equals("count")) {
					this.setValue(Long.parseLong(valueStr));

				} else if (keyStr.equals("isAdd")) {
					this.setIsAdd(Integer.parseInt(valueStr));

				} else if (keyStr.equals("tips")) {
					this.setTips(valueStr);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void setOwnerID(long ownerID) {
		this.ownerID = ownerID;
	}

	public void setCurrencyType(int currencyType) {
		this.currencyType = currencyType;
	}

	public void setValue(long value) {
		this.value = value;
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
	 * 获取货币所有都角色id
	 * @return
	 */
	public long getOwnerID() {
		return ownerID;
	}

	/**
	 * 获取货币类型
	 * @return
	 */
	public int getCurrencyType() {
		return currencyType;
	}

	/**
	 * 获取流水金额
	 * @return
	 */
	public long getValue() {
		return value;
	}


	/**
	 * 获取流水类型  标志进或者出
	 * @return
	 */
	public int getIsAdd() {
		return isAdd;
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
