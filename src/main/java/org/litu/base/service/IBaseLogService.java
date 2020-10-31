package org.litu.base.service;

import org.litu.base.vo.LtLogsVo;

public interface IBaseLogService {

	/* **添加系统日志方法，接口** */
	/**
	 * 是否允许增加系统日志
	 * @return true为允许添加
	 */
	boolean addOptLogs();

	/**
	 * 是否允许增加系统日志
	 * @param log  添加的系统操作日志
	 * @return true为允许添加
	 */
	boolean addOptLogs(LtLogsVo log);

	/**
	 * 添加系统操作日志
	 */
	void addOptLogsRunnable();

	/**
	 * 添加系统操作日志
	 * @param tempLogsVo 日志
	 * @return  日志信息
	 */
	IBaseLogService setLogs(LtLogsVo tempLogsVo);

	/**
	 * 添加系统操作日志
	 * @param module 模块
	 * @param optType 类型
	 * @param optContent 内容
	 * @param ip ip
	 * @return 日志信息
	 */
	IBaseLogService setLogs(String module, String optType, String optContent, String ip);

	/**
	 * 添加系统操作日志
	 * @param module 模块
	 * @param optType 类型
	 * @param optContent 内容
	 * @param ip ip
	 * @param userId 用户id
	 * @param systemCode  系统编码
	 * @return 日志信息
	 */
	IBaseLogService setLogs(String module, String optType, String optContent, String ip, String userId, String systemCode);
	/**
	 * 添加系统操作日志
	 * @param module 模块
	 * @param optType 类型
	 * @param optContent 内容
	 * @param ip ip
	 * @param userId 用户id
	 * @return 日志信息
	 */
	IBaseLogService setLogs(String module, String optType, String optContent, String ip, String userId);
}
