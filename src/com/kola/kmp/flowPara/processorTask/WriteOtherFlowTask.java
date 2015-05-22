package com.kola.kmp.flowPara.processorTask;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;

import com.kola.kmp.flow.db.DataAccessFactory;
import com.kola.kmp.flowPara.FlowDataKey;
import com.kola.kmp.flowPara.OtherFlowTemplate;
import com.kola.kmp.util.exception.KGameServerException;
import com.kola.kmp.util.timer.KGameTimeSignal;
import com.kola.kmp.util.timer.KGameTimerTask;

/**
 * 其他流水保存时效
 * @author Alex
 *
 */
public class WriteOtherFlowTask implements KGameTimerTask{

	private FlowDataKey key;
	
	private List<OtherFlowTemplate> dataList;
	

	public WriteOtherFlowTask(FlowDataKey key, List<OtherFlowTemplate> dataList) {
		this.key = key;
		this.dataList = dataList;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return WriteOtherFlowTask.class.getName();
	}

	@Override
	public Object onTimeSignal(KGameTimeSignal timeSignal)
			throws KGameServerException {
		
		try {
			DataAccessFactory.getCreateDBDataAccess().checkAndCreateDB(1, key.getAreaID());
			DataAccessFactory.getFlowDataAccess().addOtherFlowRecords(1, key.getAreaID(), dataList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("start writting otherFlow to DB, dataList size:" + dataList.size());
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
