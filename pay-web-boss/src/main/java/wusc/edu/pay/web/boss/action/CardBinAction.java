package wusc.edu.pay.web.boss.action;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.BankAccountTypeEnum;
import wusc.edu.pay.common.utils.importExcel.ExcelUtil;
import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.bank.entity.CardBin;
import wusc.edu.pay.facade.bank.service.CardBinFacade;
import wusc.edu.pay.facade.limit.enums.CardKindEnum;
import wusc.edu.pay.web.boss.base.BossBaseAction;
import wusc.edu.pay.web.permission.entity.PmsOperator;


/***
 * 
 * 类描述：卡BIN Action
 * 
 * @author: huqian
 * @date： 日期：2014-02-17 时间：上午9:25:58
 * @version 1.0
 */
public class CardBinAction extends BossBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(CardBinAction.class);
	@Autowired
	private CardBinFacade cardBinFacade;

	private File file;
	private String uploadContentType; // 上传文件类型
	private String uploadFileName; // 上传文件名

	/**
	 * 查询卡bin并分页.
	 * 
	 * @return
	 */
	@Permission("boss:cardBin:view")
	public String listCardBin() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cardBin", getString("cardBin"));
		paramMap.put("bankName", getString("bankName"));
		paramMap.put("cardName", getString("cardName"));
		paramMap.put("status", getString("status"));
		paramMap.put("cardKind", getString("cardKind"));
		super.pageBean = cardBinFacade.listPage(getPageParam(), paramMap);
		this.putData("cardKindEnum", BankAccountTypeEnum.toList());// 卡种:1借记卡,2贷记卡,3准贷记卡,4预付费卡
		//this.putData("cardBinStatusEnum", CardBinStatusEnum.toList());// 状态
		//this.putData("cardBinStatusEnums", CardBinStatusEnum.toMap());
		this.pushData(pageBean);
		this.pushData(paramMap);
		return "CardBinList";
	}

	/**
	 * 进入添加卡bin的页面 .<br/>
	 * 
	 * @return addTerminalUI .
	 */
	@Permission("boss:cardBin:add")
	public String addCardBinUI() {
		this.putData("cardKindEnums", BankAccountTypeEnum.values());// 卡种:1借记卡,2贷记卡,3准贷记卡,4预付费卡
		///this.putData("cardBinStatusEnum", CardBinStatusEnum.values());// 状态
		///this.putData("cardBinStatusEnums", CardBinStatusEnum.toMap());
		return "CardBinAdd";
	}

	/**
	 * 添加卡bin
	 */
	@Permission("boss:cardBin:add")
	public String addCardBin() {
		try {
			CardBin check = cardBinFacade.getByCardBin(getString("cardBin"), null);
			if (check != null) {
				return operateError("该卡Bin已存在");
			}

			PmsOperator operator = (PmsOperator) this.getLoginedOperator();

			CardBin cardBin = new CardBin();
			cardBin.setBankCode(getString("bankCode"));
			cardBin.setBankName(getString("bankName"));
			cardBin.setCardBin(getString("cardBin"));
			cardBin.setCardKind(getInteger("cardKind"));
			cardBin.setCardLength(getInteger("cardLength"));
			cardBin.setCardName(getString("cardName"));
			cardBin.setLastUpdatorId(operator.getId());
			cardBin.setLastUpdatorName(operator.getRealName());
			cardBin.setLastUpdateTime(new Date());
			cardBin.setStatus(getInteger("status"));

			cardBinFacade.create(cardBin);
			// 记录系统操作日志
			super.logSave("添加卡BIN.发卡行代码[" + cardBin.getBankCode() + "],卡名[" + cardBin.getBankCode() + "]");
		} catch (Exception e) {
			log.error("== addCardBin exception:", e);
			e.printStackTrace();
			return operateError("添加卡Bin异常");
		}
		return operateSuccess();
	}

	/**
	 * 进入修改卡bin的页面 .<br/>
	 * 
	 * @return editCardBinUI .
	 */
	@Permission("boss:cardBin:edit")
	public String editCardBinUI() {
		this.putData("cardKindEnums", BankAccountTypeEnum.values());// 卡种:1借记卡,2贷记卡,3准贷记卡,4预付费卡
		//this.putData("cardBinStatusEnums", CardBinStatusEnum.toMap());// 状态
		Long id = getLong("id");
		if (null != id) {
			CardBin cardBin = cardBinFacade.getById(id);
			this.pushData(cardBin);
		}
		return "CardBinEdit";
	}

	/**
	 * 修改卡bin
	 */
	@Permission("boss:cardBin:edit")
	public String editCardBin() {
		try {
			CardBin cardBin = cardBinFacade.getById(getLong("id"));
			if (cardBin == null) {
				return operateError("无法获取要修改的卡Bin信息");
			}
			if (!getString("cardBin").equals(cardBin.getCardBin())) {
				CardBin check = cardBinFacade.getByCardBin(cardBin.getCardBin(), null);
				if (check != null) {
					return operateError("该卡Bin已存在");
				}
			}

			cardBin.setBankCode(getString("bankCode"));
			cardBin.setBankName(getString("bankName"));
			cardBin.setCardBin(getString("cardBin"));
			cardBin.setCardKind(getInteger("cardKind"));
			cardBin.setCardLength(getInteger("cardLength"));
			cardBin.setCardName(getString("cardName"));
			cardBin.setLastUpdateTime(new Date());
			cardBin.setStatus(getInteger("status"));
			cardBin.setLastUpdatorId(getLoginedOperator().getId()); // 修改人ID
			cardBin.setLastUpdatorName(getLoginedOperator().getRealName()); // 修改人的姓名
			cardBinFacade.update(cardBin);
			// 记录系统操作日志
			super.logEdit("修改卡BIN.发卡行代码[" + cardBin.getBankCode() + "],卡名[" + cardBin.getBankCode() + "]");
		} catch (Exception e) {
			log.error("== editCardBin exception:", e);
			return operateError("操作失败");
		}
		return operateSuccess();
	}

	/**
	 * 进入导Excel页面.s
	 * 
	 * @return
	 */
	public String importExcelUI() {
		return "ImportExcelCardBin";
	}

	/**
	 * 读取Excel 并解析
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String importCardBin() {
		if (StringUtil.isNotNull(file)) {
			List<Map<String, String>> datas = ExcelUtil.readSheet(file, 0);
			PmsOperator user = (PmsOperator) this.getLoginedOperator();
			try {
				checkExcelData(datas);
			} catch (Exception e1) {
				return this.operateError(e1.getMessage());
			}
			PmsOperator o = this.getLoginedOperator();
			for (int i = 0; i < datas.size(); i++) {
				Map<String, String> data = datas.get(i);
				Integer ck = null;
				CardBin cardBin = new CardBin();
				cardBin.setLastUpdatorId(user.getId());
				cardBin.setLastUpdateTime(new Date());
				String cb = data.get("卡bin");
				CardBin cbin = cardBinFacade.getByCardBin(cb, null);
				if (cbin != null) {
					return this.operateError("该cardBin[" + cb + "]存在");
				} else {
					cardBin.setCardBin(data.get("卡bin"));
				}
				cardBin.setBankCode(data.get("发卡行代码"));
				cardBin.setBankName(data.get("发卡行名称"));
				cardBin.setCardName(data.get("卡名"));
				cardBin.setCardLength(Integer.valueOf(data.get("卡片长度")));
				List<Map> list = CardKindEnum.toList();
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					Map map = (Map) iterator.next();
					if (map.get("desc").equals(data.get("卡种"))) {
						ck = Integer.parseInt(map.get("value").toString());
					} else {
						continue;
					}
				}
				cardBin.setCardKind(ck);
				//cardBin.setStatus(CardBinStatusEnum.YES.getValue());
				cardBin.setLastUpdatorName(o.getRealName());
				cardBin.setLastUpdatorId(o.getId());
				try {
					cardBinFacade.create(cardBin);
				} catch (Exception e) {
					log.error("报错数据出现错误", e);
					return this.operateError("报错数据出现错误，错误信息：" + e);
				}
			}
		} else {
			return this.operateError("文件格式有误，必须为Excel文件格式。");
		}
		super.logSave("导入卡bin信息成功");
		return this.operateSuccess("导入数据成功！");
	}

	/**
	 * 检测Excel数据是否有效
	 * 
	 * @param datas
	 * @throws Exception
	 */
	private void checkExcelData(List<Map<String, String>> datas) throws Exception {
		StringBuffer eMsg = new StringBuffer();
		if (datas == null) {
			eMsg.append("Excel数据为空!");
			throw new Exception(eMsg.toString());
		}
		for (int i = 0; i < datas.size(); i++) {
			Map<String, String> data = datas.get(i);
			if (!StringUtil.isNotNull(data.get("卡bin"))) {
				log.error("第[" + (i + 1) + "]行的卡bin不能为空！");
				eMsg.append("第[" + (i + 1) + "]行的卡bin不能为空！");
			}
			if (!StringUtil.isNotNull(data.get("发卡行代码"))) {
				log.error("第[" + (i + 1) + "]行的发卡行代码不能为空！");
				eMsg.append("第[" + (i + 1) + "]行的发卡行代码不能为空！");
			}
			if (!StringUtil.isNotNull(data.get("发卡行名称"))) {
				log.error("第[" + (i + 1) + "]行的发卡行名称不能为空！");
				eMsg.append("第[" + (i + 1) + "]行的发卡行名称不能为空！");
			}
			if (!StringUtil.isNotNull(data.get("卡名"))) {
				log.error("第[" + (i + 1) + "]行的卡名不能为空！");
				eMsg.append("第[" + (i + 1) + "]行的卡名不能为空！");
			}
			if (!StringUtil.isNotNull(data.get("卡种"))) {
				log.error("第[" + (i + 1) + "]行的卡种不能为空！");
				eMsg.append("第[" + (i + 1) + "]行的卡种不能为空！");
			}
			if (!StringUtil.isNotNull(data.get("卡片长度"))) {
				log.error("第[" + (i + 1) + "]行的卡片长度不能为空！");
				eMsg.append("第[" + (i + 1) + "]行的卡片长度不能为空！");
			}
			try {
				new Integer(data.get("卡片长度"));
			} catch (Exception e) {
				log.error("第[" + (i + 1) + "]行的卡片长度必须为数值类型！");
				eMsg.append("第[" + (i + 1) + "]行的卡片长度必须为数值类型！");
			}
		}
		if (StringUtil.isNotNull(eMsg)) {
			throw new Exception(eMsg.toString());
		}
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
}