package com.bangware.shengyibao.updateversion.model.entity;


/**
 * 用于控制版本更新
 * @author
 */
public class VersionBean {
	private String link;
    private String packageName;
    private String md5;
    private int version;
    
    public String getLink()
    {
        return link;
    }
    public void setLink(String link)
    {
        this.link = link;
    }
    public String getPackageName()
    {
        return packageName;
    }
    public void setPackageName(String packageName)
    {
        this.packageName = packageName;
    }
    public String getMd5()
    {
        return md5;
    }
    public void setMd5(String md5)
    {
        this.md5 = md5;
    }
    public int getVersion()
    {
        return version;
    }
    public void setVersion(int version)
    {
        this.version = version;
    }
    
    public VersionBean() {
		// TODO Auto-generated constructor stub
	}
    
    public VersionBean(String link, String packageName, String md5, int version)
    {
        super();
        this.link = link;
        this.packageName = packageName;
        this.md5 = md5;
        this.version = version;
    }
}
