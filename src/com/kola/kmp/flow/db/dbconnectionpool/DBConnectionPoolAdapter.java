/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kola.kmp.flow.db.dbconnectionpool;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jdom.Document;
import org.jdom.Element;

import com.kola.kmp.flow.db.dbconnectionpool.mysql.DBConnectionFactory;
import com.kola.kmp.flow.db.dbconnectionpool.mysql.DefineDataSourceManagerIF;
import com.kola.kmp.util.KGameLogger;
import com.kola.kmp.util.XmlUtil;

/**
 * 数据库连接池管理器，可以通过方法{@link #getDBConnectionPool()}获取mysql连接池，通过
 * {@link #getHsConnectionPoolManager()} 获取handlerSocket连接池
 * 
 * @author Administrator
 */
public class DBConnectionPoolAdapter {

	private static final KGameLogger logger = KGameLogger
			.getLogger(DBConnectionPoolAdapter.class);

	private static final String dBConPoolUrl = "./resource/config/dbconfig/proxool_pool_mysql.properties";
	private static DefineDataSourceManagerIF dBConPool;
	private static boolean isInitDbPool = false;

	public static void init() {
		logger.info("！！！数据库配置加载开始！！！");
		dBConPool = DBConnectionFactory.getInstance()
				.newProxoolDataSourceInstance(dBConPoolUrl);
		logger.info("！！！数据库配置加载完成！！！");
	}


	public static DefineDataSourceManagerIF getDBConnectionPool() {
		return dBConPool;
	}

}
