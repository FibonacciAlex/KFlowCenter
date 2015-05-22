package com.kola.kmp.flow;

import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import com.kola.kmp.flow.db.DataAccessFactory;
import com.kola.kmp.flowPara.BaseFlowTypeEnum;
import com.kola.kmp.flowPara.CurrencyFlowRecordTemplate;
import com.kola.kmp.flowPara.FlowAbsInitTemplate;
import com.kola.kmp.flowPara.FlowDataKey;
import com.kola.kmp.flowPara.processorTask.WriteCurrencyFlowTask;
import com.kola.kmp.flowPara.processorTask.WriteExpFlowTask;
import com.kola.kmp.flowPara.processorTask.WriteOtherFlowTask;
import com.kola.kmp.flowPara.processorTask.WriteTreasureFlowTask;
import com.kola.kmp.flowPara.processorTask.WriteTreasureModifyRecordTask;

public class FlowOutputToDB {

	
	/**分析后将要保存入数据库的对象  key=流水类型    value=保存入数据库的对象列表*/
	public static ConcurrentHashMap<FlowDataKey, List<FlowAbsInitTemplate>> recordMap = new ConcurrentHashMap<FlowDataKey, List<FlowAbsInitTemplate>>();
	
	
	
	
		
		public static void processToDB() {

			try {

				if (!FlowCenter.systemStart.get() && !recordMap.isEmpty()) {

					for (Entry<FlowDataKey, List<FlowAbsInitTemplate>> entry : recordMap.entrySet()) {
						FlowDataKey key = entry.getKey();
						List value = entry.getValue();
						if (value == null || value.isEmpty()) {
							continue;
						}
						//检查数据库内是否存在目标表
						DataAccessFactory.getCreateDBDataAccess().checkAndCreateDB(1, key.getAreaID());
						BaseFlowTypeEnum baseFlowType = BaseFlowTypeEnum.getBaseFlowType(key.getFlowType());
						switch (baseFlowType) {
							case TYPE_CURRENCY_CHANGE:
								FlowCenter.getTimer().newTimeSignal(new WriteCurrencyFlowTask(key,value), 10, TimeUnit.SECONDS);
								break;
							case TYPE_EXP:
								FlowCenter.getTimer().newTimeSignal(new WriteExpFlowTask(key,value), 10, TimeUnit.SECONDS);
								break;
							case TYPE_OTHER:
								FlowCenter.getTimer().newTimeSignal(new WriteOtherFlowTask(key,value), 10, TimeUnit.SECONDS);
								break;
							case TYPE_TREASURE_EXCHANGE:
								FlowCenter.getTimer().newTimeSignal(new WriteTreasureFlowTask(key,value), 10, TimeUnit.SECONDS);
								break;
							case TYPE_TREASURE_MODIFY:
								FlowCenter.getTimer().newTimeSignal(new WriteTreasureModifyRecordTask(key,value), 10, TimeUnit.SECONDS);
								break;

						default:
							break;
						}

					}
					
					recordMap.clear();

				}

			} catch (Exception ee) {

				ee.printStackTrace();

			}

		}
	
}
