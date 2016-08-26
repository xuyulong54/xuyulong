package wusc.edu.pay.web.permission.entity;

import wusc.edu.pay.common.entity.BaseEntity;

/**
 * <ul>
 * <li>Title: 系统菜单</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2013-11-12
 */
public class PmsMenu extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private String name; // 菜单名称NAME
	private String url; // 菜单地址URL
	private PmsMenu parent; // 父菜单PARNETID
	private String number; // 菜单编号NUMBER
	private boolean isLeaf; // 是否下级节点ISLEAF
	private Long level; // 级别
	private String targetName; // TARGETNAME; //用于刷新页面的配置

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public PmsMenu getParent() {
		return parent;
	}

	public void setParent(PmsMenu parent) {
		this.parent = parent;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public boolean getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

}
