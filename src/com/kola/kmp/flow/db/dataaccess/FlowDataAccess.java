package com.kola.kmp.flow.db.dataaccess;

import java.util.List;

import com.kola.kmp.flowPara.CurrencyFlowRecordTemplate;
import com.kola.kmp.flowPara.ExpFlowTemplate;
import com.kola.kmp.flowPara.OtherFlowTemplate;
import com.kola.kmp.flowPara.TreasureFlowRecordTemplate;
import com.kola.kmp.flowPara.TreasureModifyRecordTemplate;

public interface FlowDataAccess {
	/**
	 * 资产出入记录数据写入DB方法
	 * @param dataList
	 * @param zoneId
	 * @param serverId
	 * @throws Exception
	 */
	public void addTresureFlowRecords(int zoneId, int serverId,List<TreasureFlowRecordTemplate> dataList) throws Exception;
	
	
	/**
	 * 资产修改记录据写入DB方法
	 * @param dataList
	 * @param zoneId
	 * @param serverId
	 * @throws Exception
	 */
	public void addTresureModifyRecords(int zoneId, int serverId, List<TreasureModifyRecordTemplate> dataList) throws Exception;
	
	/**
	 * 货币流水记录数据写入DB方法
	 * @param dataList
	 * @param zoneId
	 * @param serverId
	 * @throws Exception
	 */
	public void addCurrencyFlowRecords(int zoneId, int serverId, List<CurrencyFlowRecordTemplate> dataList) throws Exception;
	
	/**
	 * 经验流水记录数据写入DB方法
	 * @param dataList
	 * @param zoneId
	 * @param serverId
	 * @throws Exception
	 */
	public void addExpFlowRecords(int zoneId, int serverId, List<ExpFlowTemplate> dataList) throws Exception;
	
	/**
	 * 其他流水记录数据写入DB方法
	 * @param dataList
	 * @param zoneId
	 * @param serverId
	 * @throws Exception
	 */
	public void addOtherFlowRecords(int zoneId, int serverId, List<OtherFlowTemplate> dataList) throws Exception;
}
