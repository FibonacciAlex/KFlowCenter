package com.kola.kmp.flowPara.processorTask;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;

import com.kola.kmp.flow.db.DataAccessFactory;
import com.kola.kmp.flow.db.dataaccess.CreateDBDataAccess;
import com.kola.kmp.flow.db.dataaccess.FlowDataAccess;
import com.kola.kmp.flowPara.CurrencyFlowRecordTemplate;
import com.kola.kmp.flowPara.FlowDataKey;
import com.kola.kmp.util.exception.KGameServerException;
import com.kola.kmp.util.timer.KGameTimeSignal;
import com.kola.kmp.util.timer.KGameTimerTask;


/**
 * 处理货币流水保存时效
 * @author Alex
 *
 */
public class WriteCurrencyFlowTask implements KGameTimerTask{

	private FlowDataKey key;
	
	private List<CurrencyFlowRecordTemplate> dataList;
	
	
	
	

	public WriteCurrencyFlowTask(FlowDataKey key,	List<CurrencyFlowRecordTemplate> dataList) {
		this.key = key;
		this.dataList = dataList;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return WriteCurrencyFlowTask.class.getName();
	}

	@Override
	public Object onTimeSignal(KGameTimeSignal timeSignal)
			throws KGameServerException {
		// TODO Auto-generated method stub
		try {
			DataAccessFactory.getCreateDBDataAccess().checkAndCreateDB(1, key.getAreaID());
			DataAccessFactory.getFlowDataAccess().addCurrencyFlowRecords(1, key.getAreaID(), dataList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("start writting currency to DB, dataList size:" + dataList.size());
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
