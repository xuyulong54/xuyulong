/**
 * wusc.edu.pay.web.boss.action.limit.AmountLimitPackAction.java
 */
package wusc.edu.pay.web.boss.action.limit;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.limit.entity.AmountLimitPack;
import wusc.edu.pay.facade.limit.service.AmountLimitManagementFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 
 * <ul>
 * <li>Title: 金额限制包控制器</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2014-7-10
 */
public class AmountLimitPackAction extends BossBaseAction implements ModelDriven<AmountLimitPack> {
	private static final long serialVersionUID = -5424415940629109221L;

	/**
	 * 金额限制
	 */
	@Autowired
	private AmountLimitManagementFacade amountLimitManagementFacade;

	private AmountLimitPack amountLimitPack = new AmountLimitPack();

	/**
	 * 查询列表
	 * 
	 * @return
	 */
	public String listAmountLimitPack() {
		super.pageBean = this.amountLimitManagementFacade.queryAmountLimitPackPage(getPageParam(), getParamMap_Utf8());
		pushData(pageBean);
		pushData(getParamMap_Utf8());
		return "amountLimitPackList";
	}

	/**
	 * 编辑金额限制包
	 * 
	 * @return
	 */
	@Permission("limit:amount:edit")
	public String toEditAmountLimitPack() {
		Long amountLimitPackId = getLong("id");
		if (amountLimitPackId != null) {
			pushData(this.amountLimitManagementFacade.getAmountLimitPackById(amountLimitPackId));
		}
		return "amountLimitPackEdit";
	}

	/**
	 * 执行新增或者修改操作
	 */
	@Permission("limit:amount:edit")
	public String addOrEditAmountLimitPack() {

		try {
			Long id = getLong("id");
			String name = getString("name");
			String description = getString("description");
			Date lastModifyTime = new Date();
			AmountLimitPack amountLimitPack = amountLimitManagementFacade.getAmountLimitPackByName(name);
			if (id == null) {
				if (amountLimitPack != null) {
					return this.operateError("已存在同名的限制包！");
				}
				AmountLimitPack alp = new AmountLimitPack();
				alp.setName(name);
				alp.setDescription(description);
				alp.setLastModifyTime(lastModifyTime);
				this.amountLimitManagementFacade.addAmountLimitPack(alp);
				this.logSave("增加金额限制包成功：" + getModel().getName());
			} else {
				if (amountLimitPack != null && amountLimitPack.getId().longValue() != getModel().getId().longValue()) {
					return this.operateError("已存在同名的限制包！");
				}
				AmountLimitPack alp = amountLimitManagementFacade.getAmountLimitPackById(getLong("id"));
				alp.setName(name);
				alp.setDescription(description);
				alp.setLastModifyTime(lastModifyTime);
				this.amountLimitManagementFacade.updateAmountLimitPack(alp);
				this.logEdit("更新金额限制包成功：" + getModel().getName());

			}

		} catch (Exception e) {
			this.logSaveError("增加金额限制包：" + getModel().getName() + "失败");
			return this.operateError("操作失败，错误代码");
		}

		return this.operateSuccess();
	}

	public AmountLimitPack getModel() {
		return amountLimitPack;
	}
}