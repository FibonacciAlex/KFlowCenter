package com.kola.kmp.flowPara;



/**
 * @author Alex
 *
 */
public enum BaseFlowTypeEnum {
	
	
	TYPE_CURRENCY_CHANGE(11), //货币增减
	TYPE_TREASURE_EXCHANGE(12), //财产增减
	TYPE_TREASURE_MODIFY(13), //财产修改
	TYPE_EXP(14), //经验
	TYPE_OTHER(15), //其它
	;
	
	// 标识数值
	public final int sign;
	
	private BaseFlowTypeEnum(int sign) {
		this.sign = sign;
	}
	
	private static BaseFlowTypeEnum[] baseFlowType;
	
	static{
		baseFlowType = BaseFlowTypeEnum.values();
	}
	
	public static BaseFlowTypeEnum getBaseFlowType(int type){
		for (BaseFlowTypeEnum flowType : baseFlowType) {
			if(flowType.sign == type){
				return flowType;
			}
		}
		return null;
		
	}
}


enum PropertyFlowTypeEnum {
	
	
	道具(1), //
	时装(2), //
	坐驾(3), //
	宠物(4), //
	技能(5), //
	;

	// 标识数值
	public final int sign;

	private PropertyFlowTypeEnum(int sign) {
		this.sign = sign;
	}

}


 enum OtherFlowTypeEnum {

	
	背包扩容(1), //
	资源战竞价(2), //
	买随机物品(3), //
	登陆奖励大奖(4), //
	登陆奖励小奖(5), //
	删除附件邮件(6), //
	VIP充值处理(7), //
	退出军团(8), //
	被开除出军团(9), //
	转让团长(10), //
	自动转让团长(11), //
	删除角色转让团长(12), //
	军团科技升级(13), //
	军团升级(14), //
	军团解散(15), //
	示例(99), ;

	// 标识数值
	public final int sign;

	private OtherFlowTypeEnum(int sign) {
		this.sign = sign;
	}


	
}