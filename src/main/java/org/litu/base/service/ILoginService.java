package org.litu.base.service;

import org.litu.app.entity.SysUser;
import org.litu.app.vo.LoginUserMsg;
import org.litu.core.exception.LtServerException;

public interface ILoginService {

	/**
	 * 系统编号认证，用于判断当前系统编码是否被管理
	 * 
	 * @param systemCode 系统编号
	 * @return true则为被管理
	 */
	public boolean checkSystemCode(String systemCode);

	/**
	 * 根据用户的账号、密码信息，对账号信息进行校验，包括账号的用户名、密码等信息。
	 * 
	 * @author yueye
	 *
	 * @param systemCode
	 *            当前用户登录系统的编号
	 * @param account
	 *            用户账号信息
	 * @param password
	 *            账号密码信息
	 * @return 如果登录成功返回用户信息，如果登录失败返回null， 或者抛出对应错误信息的异常
	 */
	public SysUser checkLogin(String systemCode, String account, String password) throws Exception, LtServerException;

	/**
	 * 对用户的登录信息进行校验，这里校验不包含用户的密码校验 该功能主要是针对Shario校验之前的处理
	 * 
	 * @author yueye
	 *
	 * @param systemCode
	 *            当前用户登录系统的编号
	 * @param account
	 *            用户账号信息
	 * @param password
	 *            账号密码信息
	 * @return  登陆信息校验结果
	 */
	public SysUser checkLoginShiro(String systemCode, String account, String password);

	/**
	 * 根据系统用户的信息，获取当前用户登录需要反馈的信息。
	 * 
	 * @author yueye
	 *
	 * @param userId
	 *            用户ID
	 * @param systemCode
	 *            系统的Code
	 * @return  当前用户登录需要反馈的信息。
	 */
	public LoginUserMsg getLoginUserMsg(String userId, String systemCode);

	/**
	 * 根据系统用户的信息，获取当前用户登录需要反馈的信息。
	 * 
	 * @author yueye
	 *
	 * @param user
	 *            用户信息
	 * @return  当前用户登录需要反馈的信息。
	 */
	public LoginUserMsg getSsoUserMsg(SysUser user, String systemCode);

	/**
	 * 更新用户登录表中的登录状态信息
	 * 
	 * @author yueye
	 *
	 * @param userId   用户id
	 * @param loginStatus  登陆状态
	 * @return true则为更新成功
	 */
	public boolean ChangeLoginStatus(String userId, Integer loginStatus);

	/**
	 * shario认证，注销登录
	 * 
	 * @author yueye
	 *
	 * @return  true为注销成功
	 */
	public boolean logoutShiro();

	/**
	 * 根据用户账号，获取用户的基本信息
	 * 
	 * @param account   用户账号
	 * @return  用户基本信息
	 */
	public SysUser getUserByAccount(String account);
}
