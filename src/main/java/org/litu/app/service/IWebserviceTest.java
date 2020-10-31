package org.litu.app.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;

import org.litu.base.vo.BaseRes;

/**
 * webservice demo
 */
@javax.jws.WebService
public interface IWebserviceTest {

	@WebMethod
	String getName(@WebParam(name = "userId") Long userId);

	@WebMethod
    BaseRes getUser(Integer userId);
}
