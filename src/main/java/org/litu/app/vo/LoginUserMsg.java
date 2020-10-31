package org.litu.app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.litu.app.entity.SysMenu;
import org.litu.app.entity.SysUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yueye
 */
@ApiModel(value = "用户登陆信息")
public class LoginUserMsg {
    @ApiModelProperty(value = "id", required = true)
    private String fId;
    @ApiModelProperty(value = "账号", required = true)
    private String fAccount; // 帐号
    @ApiModelProperty(value = "真实姓名", required = true)
    private String fRealname; // 真实姓名
    @ApiModelProperty(value = "昵称", required = true)
    private String fNickname; // 昵称
    @ApiModelProperty(value = "性别", required = true)
    private String fSex; // 性别：1男， 0女
    @ApiModelProperty(value = "手机", required = true)
    private String fPhone; // 手机
    @ApiModelProperty(value = "微信", required = true)
    private String fWechat; // 微信
    @ApiModelProperty(value = "邮箱", required = true)
    private String fEmail; // 邮箱
    @ApiModelProperty(value = "生日", required = true)
    private Date fBirthday; // 生日
    @ApiModelProperty(value = "头像")
    private String fHeadicon; // 头像
    @ApiModelProperty(value = "角色列表", required = true)
    private List<String> roles = new ArrayList<>();
    @ApiModelProperty(value = "菜单列表", required = true)
    private List<SsoMenu> menus = new ArrayList<>();

    /**
     * 用户登陆信息构造方法
     *
     * @param sysUser  用户
     * @param sysMenus 系统菜单
     * @param roles    角色
     */
    public LoginUserMsg(SysUser sysUser, List<SysMenu> sysMenus, List<String> roles) {
        this.fId = sysUser.getfId();
        this.fAccount = sysUser.getfAccount();
        this.fRealname = sysUser.getfRealname();
        this.fNickname = sysUser.getfNickname();
        this.fSex = sysUser.getfSex();
        this.fPhone = sysUser.getfPhone();
        this.fWechat = sysUser.getfWechat();
        this.fEmail = sysUser.getfEmail();
        this.fBirthday = sysUser.getfBirthday();
        this.fHeadicon = sysUser.getfPhone();

        this.roles = roles;
        for (SysMenu sysMenu : sysMenus) {
            menus.add(new SsoMenu(sysMenu));
        }
    }

    /**
     * 获取id
     *
     * @return id
     */
    public String getfId() {
        return fId;
    }

    /**
     * 获取账号
     *
     * @return 账号
     */
    public String getfAccount() {
        return fAccount;
    }

    /**
     * 获取真实姓名
     *
     * @return 真实姓名
     */
    public String getfRealname() {
        return fRealname;
    }

    /**
     * 获取昵称
     *
     * @return 昵称
     */
    public String getfNickname() {
        return fNickname;
    }

    /**
     * 获取 性别
     *
     * @return 性别
     */
    public String getfSex() {
        return fSex;
    }

    /**
     * 获取手机号
     *
     * @return 手机号
     */
    public String getfPhone() {
        return fPhone;
    }

    /**
     * 获取微信
     *
     * @return 微信
     */
    public String getfWechat() {
        return fWechat;
    }

    /**
     * 获取邮箱
     *
     * @return 邮箱
     */
    public String getfEmail() {
        return fEmail;
    }

    /**
     * 获取生日
     *
     * @return 生日
     */
    public Date getfBirthday() {
        return fBirthday;
    }

    /**
     * 获取头像
     *
     * @return 头像
     */
    public String getfHeadicon() {
        return fHeadicon;
    }


    /**
     * 获取角色
     *
     * @return 角色
     */
    public List<String> getRoles() {
        return roles;
    }

    /**
     * 获取菜单
     *
     * @return 菜单
     */
    public List<SsoMenu> getMenus() {
        return menus;
    }
}

@ApiModel(value = "角色")
class SsoRole {
    @ApiModelProperty(value = "Id", required = true)
    private String fId;

    /**
     * 获取ID
     *
     * @return ID
     */
    public String getfId() {
        return fId;
    }

}

@ApiModel(value = "菜单")
class SsoMenu {
    @ApiModelProperty(value = "Id", required = true)
    private String fId;
    @ApiModelProperty(value = "父节点id")
    private String fParentid;
    @ApiModelProperty(value = "层", required = true)
    private String fLayers;
    @ApiModelProperty(value = "类型", required = true)
    private Integer fType;
    @ApiModelProperty(value = "号码", required = true)
    private String fCode;
    @ApiModelProperty(value = "名字", required = true)
    private String fName;
    @ApiModelProperty(value = "图标", required = true)
    private String fIcon;
    @ApiModelProperty(value = "Url地址", required = true)
    private String fUrl;
    @ApiModelProperty(value = "类别编号", required = true)
    private Integer fSortnum;
    @ApiModelProperty(value = "系统编号", required = true)
    private String fSystemcode;

    /**
     * 系统菜单的构造方法
     *
     * @param sysMenu 系统菜单
     */
    public SsoMenu(SysMenu sysMenu) {
        this.fId = sysMenu.getfId();
        this.fParentid = sysMenu.getfParentid();
        this.fLayers = sysMenu.getfLayers();
        this.fType = sysMenu.getfType();
        this.fCode = sysMenu.getfCode();
        this.fName = sysMenu.getfName();
        this.fIcon = sysMenu.getfIcon();
        this.fUrl = sysMenu.getfUrl();
        this.fSortnum = sysMenu.getfSortnum();
        this.fSystemcode = sysMenu.getfSystemcode();
    }

    /**
     * 获取ID
     *
     * @return ID
     */
    public String getfId() {
        return fId;
    }

    /**
     * 获取父节点·ID
     *
     * @return ID
     */
    public String getfParentid() {
        return fParentid;
    }

    /**
     * 获取层
     *
     * @return 层
     */
    public String getfLayers() {
        return fLayers;
    }

    /**
     * 获取类型
     *
     * @return 类型
     */
    public Integer getfType() {
        return fType;
    }

    /**
     * 获取编号
     *
     * @return 编号
     */
    public String getfCode() {
        return fCode;
    }

    /**
     * 获取名字
     *
     * @return 名字
     */
    public String getfName() {
        return fName;
    }

    /**
     * 获取图标
     *
     * @return 图标
     */
    public String getfIcon() {
        return fIcon;
    }

    /**
     * 获取Url
     *
     * @return Url
     */
    public String getfUrl() {
        return fUrl;
    }

    /**
     * 获取类别编号
     *
     * @return 列别编号
     */
    public Integer getfSortnum() {
        return fSortnum;
    }

    /**
     * 获取系统编号
     *
     * @return 系统编号
     */
    public String getfSystemcode() {
        return fSystemcode;
    }
}
