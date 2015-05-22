package com.kola.kmp.flowPara.processorTask;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;

import com.kola.kmp.flow.db.DataAccessFactory;
import com.kola.kmp.flowPara.FlowDataKey;
import com.kola.kmp.flowPara.TreasureModifyRecordTemplate;
import com.kola.kmp.util.exception.KGameServerException;
import com.kola.kmp.util.timer.KGameTimeSignal;
import com.kola.kmp.util.timer.KGameTimerTask;


/**
 * 资产改变数据日志写入数据库时效
 * @author Alex
 *
 */
public class WriteTreasureModifyRecordTask implements KGameTimerTask{
	
	private FlowDataKey key;
	
	private List<TreasureModifyRecordTemplate> dataList ;

	
	
	

	public WriteTreasureModifyRecordTask(FlowDataKey key, List<TreasureModifyRecordTemplate> dataList) {
		this.key = key;
		this.dataList = dataList;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return WriteTreasureFlowTask.class.getName();
	}

	@Override
	public Object onTimeSignal(KGameTimeSignal timeSignal)
			throws KGameServerException {
		try {
			
			DataAccessFactory.getFlowDataAccess().addTresureModifyRecords(1, key.getAreaID(), dataList);
		} catch (Exception e) {
			// TODO 如果出现异常，则将记录存在log
		}
		
		System.out.println("start writting TreasureModify to DB, dataList size:" + dataList.size());
		return null;
	}

	@Override
	public void done(KGameTimeSignal timeSignal) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rejected(RejectedExecutionException e) {
		// TODO Auto-generated method stub
		
	}

}
