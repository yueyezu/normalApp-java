package org.litu.app.service;

import org.litu.app.entity.system.SysAccessToken;
import org.litu.app.entity.system.SysAccessTokenEx;
import org.litu.base.service.IBaseService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ltgk
 * @since 2019-06-27
 */
public interface ISysAccessTokenService extends IBaseService<SysAccessToken> {

    /**
     * 获取扩展的token信息
     *
     * @param id 根据id
     * @return
     */
    SysAccessTokenEx detailEx(String id);

    /**
     * 启用
     *
     * @param id 需要启用的id
     * @return true则启用成功
     */
    boolean enable(String id);

    /**
     * 禁用
     *
     * @param id 需要禁用的id
     * @return true则禁用成功
     */
    boolean disable(String id);


    /**
     * 验证客户端是否存在以及密码是否正确
     *
     * @param clientId
     * @param clientSecret
     * @return
     */
    boolean checkClient(String clientId, String clientSecret);

    /**
     * 根据传入的信息，生成token信息。
     *
     * @param accessToken
     * @return
     */
    SysAccessToken createToken(SysAccessToken accessToken);

    /**
     * 根据传入的刷新码，刷新授权码
     *
     * @param clientType
     * @param refreshToken
     * @return
     */
    SysAccessToken refreshToken(String clientType, String refreshToken);

    /**
     * 验证用户的授权码，并返回该授权码关联的用户信息。
     *
     * @param clientType 系统类型
     * @param token      授权码
     * @param mcode      设备机器码
     * @return
     */
    SysAccessToken checkToken(String clientType, String token, String mcode);

}
