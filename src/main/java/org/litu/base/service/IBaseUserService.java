package org.litu.base.service;

public interface IBaseUserService {
    /**
     * 通过userId获取name
     *
     * @param userId 获取name所需要的userid
     * @return name
     */
    public String getNameByUserId(String userId);

}
