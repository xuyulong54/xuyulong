package wusc.edu.pay.facade.boss.entity;

import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/**
 * 类描述：1:企业动态 2:招聘 3:常见问题 4:产品 管理 5：通知、公告
 * 
 * @author: huangbin
 * @date： 日期：2013-12-3 时间：下午2:12:47
 * @version 1.0
 */
public class Article extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7348533345614858964L;
	private Integer type; // 类型 1:企业动态 2:招聘 3:常见问题 4:产品 管理 5：通知、公告
	private String title; // 标题
	private String content; // 内容
	private Date editTime; // 修改时间
	private Long operatorId; // 发布人ID
	private String operatorName; // 发布人姓名
	private Integer status; // 状态 100:激活,101:停用
	private Integer articleType;// 类型。 1-门户系统，2-代理商系统

	public Integer getArticleType() {
		return articleType;
	}

	public void setArticleType(Integer articleType) {
		this.articleType = articleType;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getEditTime() {
		return editTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
