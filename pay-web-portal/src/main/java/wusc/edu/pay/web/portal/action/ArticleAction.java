package wusc.edu.pay.web.portal.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.ArticleTypeEnum;
import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.facade.boss.entity.Article;
import wusc.edu.pay.facade.boss.service.ArticleFacade;
import wusc.edu.pay.web.portal.base.BaseAction;


/**
 * 文章管理控制类（文章，常见问题，人才招聘，产品发布）
 * 
 * @author liliqiong
 * @date 2013-12-3
 * @version 1.0
 */
public class ArticleAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	@Autowired
	private ArticleFacade articleFacade;

	/**
	 * 列表
	 * 
	 * @return
	 */
	public String listArticle() {
		String returnPage = "";
		Integer type = getInteger("type");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("type", type);
		paramMap.put("status", PublicStatusEnum.ACTIVE.getValue());
		paramMap.put("articleType", 1);
		super.pageBean = articleFacade.listPage(getPageParam(), paramMap);
		pushData(pageBean);
		if (ArticleTypeEnum.ARTICLE.getValue() == type) {
			returnPage = "listArticle";
		} else if (ArticleTypeEnum.RECRUITMENT.getValue() == type) {
			returnPage = "listRecruitment";
		} else if (ArticleTypeEnum.ASKEDQUESTION.getValue() == type) {
			returnPage = "listAskedquestion";
		} else if (ArticleTypeEnum.PRODUCT.getValue() == type) {
			returnPage = "listProduct";
		} else if (ArticleTypeEnum.NOTICE.getValue() == type) {
			returnPage = "listNotice";
		}
		return returnPage;
	}

	/**
	 * 详情
	 * 
	 * @return
	 */
	public String viewArticle() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", getLong("id"));
		paramMap.put("status", PublicStatusEnum.ACTIVE.getValue());
		Article article = articleFacade.getBy(paramMap);
		putData("article", article);
		String returnPage = "";
		Integer type = article.getType();
		if (ArticleTypeEnum.ARTICLE.getValue() == type) {
			returnPage = "viewArticle";
		} else if (ArticleTypeEnum.RECRUITMENT.getValue() == type) {
			returnPage = "viewRecruitment";
		} else if (ArticleTypeEnum.ASKEDQUESTION.getValue() == type) {
			returnPage = "viewAskedquestion";
		} else if (ArticleTypeEnum.PRODUCT.getValue() == type) {
			returnPage = "viewProduct";
		} else if (ArticleTypeEnum.NOTICE.getValue() == type) {
			returnPage = "viewNotice";
		}
		return returnPage;
	}

	/**
	 * 关于我们
	 */
	public String aboutUs() {
		return "aboutUs";
	}

	/**
	 * 联系我们
	 */
	public String contactUs() {
		return "contactUs";
	}

	/**
	 * 合作伙伴
	 */
	public String partner() {
		return "partner";
	}

	/**
	 * 网站地图
	 */
	public String websiteMap() {
		return "websiteMap";
	}

	/**
	 * 商户服务
	 */
	public String gwService() {
		return "gwService";
	}

	/**
	 * 产品
	 */
	public String gwProcuct() {
		return "gwProcuct";
	}

	/**
	 * 安全中心
	 */
	public String securityCenter() {
		return "securityCenter";
	}

	/**
	 * 客服中心
	 */
	public String consultCenter() {
		return "consultCenter";
	}

}
