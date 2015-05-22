package com.kola.kmp.flow.db.dataaccess.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import com.kola.kmp.flow.db.dataaccess.FlowDataAccess;
import com.kola.kmp.flow.db.dbconnectionpool.DBConnectionPoolAdapter;
import com.kola.kmp.flow.db.dbconnectionpool.mysql.DefineDataSourceManagerIF;
import com.kola.kmp.flowPara.CurrencyFlowRecordTemplate;
import com.kola.kmp.flowPara.ExpFlowTemplate;
import com.kola.kmp.flowPara.OtherFlowTemplate;
import com.kola.kmp.flowPara.TreasureFlowRecordTemplate;
import com.kola.kmp.flowPara.TreasureModifyRecordTemplate;
import com.kola.kmp.util.KGameLogger;

public class FlowDataAccessImpl implements FlowDataAccess {
	private static final KGameLogger logger = KGameLogger
			.getLogger(FlowDataAccessImpl.class);

	private DefineDataSourceManagerIF dbPool;

	public FlowDataAccessImpl() {
		dbPool = DBConnectionPoolAdapter.getDBConnectionPool();
	}

	@Override
	public void addTresureFlowRecords(int zoneId, int serverId,
			List<TreasureFlowRecordTemplate> dataList) throws Exception {
		String sql = "insert into property_in_out_record_"
				+ zoneId
				+ "_"
				+ serverId
				+ "(role_id,UUID,property_type,property_template_id,flow_type,record_time,remark)"
				+ " values(?,?,?,?,?,?,?)";
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = dbPool.getConnection();
			ps = dbPool.writeStatement(con, sql);

			for (TreasureFlowRecordTemplate info : dataList) {
				try {
					ps.setLong(1, info.getOwnerID());
					ps.setString(2, info.getUUID());
					ps.setInt(3, info.getProperType());
					ps.setString(4, info.getTreasureTemplateID());
					ps.setInt(5, info.getIsAdd());
					ps.setTimestamp(6, new Timestamp(info.getRecordTime()));
					ps.setString(7, info.getTips());
					ps.execute();
				} catch (SQLException ex) {
					logger.error("添加资产出入流水记录失败。", ex);
				} finally {
					ps.clearParameters();
				}
			}

		} catch (SQLException ex) {
			logger.error("添加资产出入流水记录失败。", ex);
		} finally {
			dbPool.closePreparedStatement(ps);
			dbPool.closeConnection(con);
		}
	}

	@Override
	public void addTresureModifyRecords(int zoneId, int serverId,
			List<TreasureModifyRecordTemplate> dataList) throws Exception {
		String sql = "insert into property_modify_record_"
				+ zoneId
				+ "_"
				+ serverId
				+ "(role_id,UUID,record_time,remark)"
				+ " values(?,?,?,?)";
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = dbPool.getConnection();
			ps = dbPool.writeStatement(con, sql);

			for (TreasureModifyRecordTemplate info : dataList) {
				try {
					ps.setLong(1, info.getRoleID());
					ps.setString(2, info.getUUID());
					ps.setTimestamp(3, new Timestamp(info.getRecordTime()));
					ps.setString(4, info.getTips());
					ps.execute();
				} catch (SQLException ex) {
					logger.error("添加资产改变流水记录失败。", ex);
				} finally {
					ps.clearParameters();
				}
			}

		} catch (SQLException ex) {
			logger.error("添加资产改变流水记录失败。", ex);
		} finally {
			dbPool.closePreparedStatement(ps);
			dbPool.closeConnection(con);
		}
	}

	@Override
	public void addCurrencyFlowRecords(int zoneId, int serverId,
			List<CurrencyFlowRecordTemplate> dataList) throws Exception {
		String sql = "insert into currency_record_"
				+ zoneId
				+ "_"
				+ serverId
				+ "(role_id,currency_type,currency_count,flow_type,record_time,remark)"
				+ " values(?,?,?,?,?,?)";
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = dbPool.getConnection();
			ps = dbPool.writeStatement(con, sql);

			for (CurrencyFlowRecordTemplate info : dataList) {
				try {
					ps.setLong(1, info.getOwnerID());
					ps.setInt(2, info.getCurrencyType());
					ps.setLong(3, info.getValue());
					ps.setInt(4, info.getIsAdd());
					ps.setTimestamp(5, new Timestamp(info.getRecordTime()));
					ps.setString(6, info.getTips());
					ps.execute();
				} catch (SQLException ex) {
					logger.error("添加货币流水记录失败。", ex);
				} finally {
					ps.clearParameters();
				}
			}

		} catch (SQLException ex) {
			logger.error("添加货币流水记录失败。", ex);
		} finally {
			dbPool.closePreparedStatement(ps);
			dbPool.closeConnection(con);
		}
	}

	@Override
	public void addExpFlowRecords(int zoneId, int serverId,
			List<ExpFlowTemplate> dataList) throws Exception {
		String sql = "insert into experience_record_"
				+ zoneId
				+ "_"
				+ serverId
				+ "(role_id,exp_count,record_time,remark)"
				+ " values(?,?,?,?)";
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = dbPool.getConnection();
			ps = dbPool.writeStatement(con, sql);

			for (ExpFlowTemplate info : dataList) {
				try {
					ps.setLong(1, info.getRoleID());
					ps.setInt(2, info.getValue());
					ps.setTimestamp(3, new Timestamp(info.getRecordTime()));
					ps.setString(4, info.getTips());
					ps.execute();
				} catch (SQLException ex) {
					logger.error("添加经验流水记录失败。", ex);
				} finally {
					ps.clearParameters();
				}
			}

		} catch (SQLException ex) {
			logger.error("添加经验流水记录失败。", ex);
		} finally {
			dbPool.closePreparedStatement(ps);
			dbPool.closeConnection(con);
		}
	}

	@Override
	public void addOtherFlowRecords(int zoneId, int serverId,
			List<OtherFlowTemplate> dataList) throws Exception {
		String sql = "insert into other_log_record_"
				+ zoneId
				+ "_"
				+ serverId
				+ "(role_id,flow_type,record_time,remark)"
				+ " values(?,?,?,?)";
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = dbPool.getConnection();
			ps = dbPool.writeStatement(con, sql);

			for (OtherFlowTemplate info : dataList) {
				try {
					ps.setLong(1, info.getRoleID());
					ps.setInt(2, info.getOtherFlowType());
					ps.setTimestamp(3, new Timestamp(info.getRecordTime()));
					ps.setString(4, info.getTips());
					ps.execute();
				} catch (SQLException ex) {
					logger.error("添加其他流水记录失败。", ex);
				} finally {
					ps.clearParameters();
				}
			}

		} catch (SQLException ex) {
			logger.error("添加其他流水记录失败。", ex);
		} finally {
			dbPool.closePreparedStatement(ps);
			dbPool.closeConnection(con);
		}
	}

}
