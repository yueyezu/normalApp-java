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
    private String id;
    @ApiModelProperty(value = "账号", required = true)
    private String account; // 帐号
    @ApiModelProperty(value = "真实姓名", required = true)
    private String realName; // 真实姓名
    @ApiModelProperty(value = "昵称", required = true)
    private String nickName; // 昵称
    @ApiModelProperty(value = "性别", required = true)
    private String sex; // 性别：1男， 0女
    @ApiModelProperty(value = "手机", required = true)
    private String phone; // 手机
    @ApiModelProperty(value = "微信", required = true)
    private String wechat; // 微信
    @ApiModelProperty(value = "邮箱", required = true)
    private String email; // 邮箱
    @ApiModelProperty(value = "生日", required = true)
    private Date birthday; // 生日
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
        this.id = sysUser.getId();
        this.account = sysUser.getAccount();
        this.realName = sysUser.getRealName();
        this.nickName = sysUser.getNickName();
        this.sex = sysUser.getSex();
        this.phone = sysUser.getPhone();
        this.wechat = sysUser.getWechat();
        this.email = sysUser.getEmail();
        this.birthday = sysUser.getBirthday();
        this.fHeadicon = sysUser.getPhone();

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
    public String getId() {
        return id;
    }

    /**
     * 获取账号
     *
     * @return 账号
     */
    public String getAccount() {
        return account;
    }

    /**
     * 获取真实姓名
     *
     * @return 真实姓名
     */
    public String getRealName() {
        return realName;
    }

    /**
     * 获取昵称
     *
     * @return 昵称
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 获取 性别
     *
     * @return 性别
     */
    public String getSex() {
        return sex;
    }

    /**
     * 获取手机号
     *
     * @return 手机号
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 获取微信
     *
     * @return 微信
     */
    public String getWechat() {
        return wechat;
    }

    /**
     * 获取邮箱
     *
     * @return 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 获取生日
     *
     * @return 生日
     */
    public Date getBirthday() {
        return birthday;
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
    private String id;

    /**
     * 获取ID
     *
     * @return ID
     */
    public String getId() {
        return id;
    }

}

@ApiModel(value = "菜单")
class SsoMenu {
    @ApiModelProperty(value = "Id", required = true)
    private String id;
    @ApiModelProperty(value = "父节点id")
    private String parentId;
    @ApiModelProperty(value = "层", required = true)
    private String layers;
    @ApiModelProperty(value = "类型", required = true)
    private Integer type;
    @ApiModelProperty(value = "号码", required = true)
    private String code;
    @ApiModelProperty(value = "名字", required = true)
    private String name;
    @ApiModelProperty(value = "图标", required = true)
    private String icon;
    @ApiModelProperty(value = "Url地址", required = true)
    private String url;
    @ApiModelProperty(value = "类别编号", required = true)
    private Integer sortNum;
    @ApiModelProperty(value = "系统编号", required = true)
    private String systemCode;

    /**
     * 系统菜单的构造方法
     *
     * @param sysMenu 系统菜单
     */
    public SsoMenu(SysMenu sysMenu) {
        this.id = sysMenu.getId();
        this.parentId = sysMenu.getParentId();
        this.layers = sysMenu.getLayers();
        this.type = sysMenu.getType();
        this.code = sysMenu.getCode();
        this.name = sysMenu.getName();
        this.icon = sysMenu.getIcon();
        this.url = sysMenu.getUrl();
        this.sortNum = sysMenu.getSortNum();
        this.systemCode = sysMenu.getSystemCode();
    }

    /**
     * 获取ID
     *
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * 获取父节点·ID
     *
     * @return ID
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * 获取层
     *
     * @return 层
     */
    public String getLayers() {
        return layers;
    }

    /**
     * 获取类型
     *
     * @return 类型
     */
    public Integer getType() {
        return type;
    }

    /**
     * 获取编号
     *
     * @return 编号
     */
    public String getCode() {
        return code;
    }

    /**
     * 获取名字
     *
     * @return 名字
     */
    public String getName() {
        return name;
    }

    /**
     * 获取图标
     *
     * @return 图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 获取Url
     *
     * @return Url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 获取类别编号
     *
     * @return 列别编号
     */
    public Integer getSortNum() {
        return sortNum;
    }

    /**
     * 获取系统编号
     *
     * @return 系统编号
     */
    public String getsystemCode() {
        return systemCode;
    }
}
