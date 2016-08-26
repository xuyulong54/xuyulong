package wusc.edu.pay.web.boss.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.boss.entity.GlobalSet;
import wusc.edu.pay.facade.boss.service.GlobalSetFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;


/***
 * 
 * 类描述： 运营平台全局设置
 * 
 * @author: blank
 * @date： 日期：2013-10-24 时间：下午7:42:33
 * @version 1.0
 */
@Scope("prototype")
public class GlobalSetAction extends BossBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3290872072383501272L;
	@Autowired
	private GlobalSetFacade globalSetFacade;

	/**
	 * 列出全局设置记录.
	 * @return
	 */
	@Permission("boss:globalset:view")
	public String listGlobalSet() {
		// 查询参数集合
		Map<String, Object> paramMap = new HashMap<String, Object>();
		super.pageBean = globalSetFacade.listPage(getPageParam(), paramMap);
		this.pushData(pageBean);
		this.putData("param", paramMap); // 回显查询条件
		return "listGlobalSet";
	}

	@Permission("boss:globalset:view")
	public String viewGlobalSet() {
		Long id = getLong("id");
		if (null == id) {
			return operateError("全局设置ID为空");
		}
		GlobalSet globalSet = globalSetFacade.getById(id);
		this.pushData(globalSet);
		return "viewGlobalSet";
	}

	@Permission("boss:globalset:edit")
	public String editGlobalSet() {
		Long id = getLong("id");
		if (id == null) {
			return operateError("全局设置ID为空");
		}
		// 查询参数集合
		GlobalSet globalSet = globalSetFacade.getById(id);
		globalSet.setId(id);
		String key = getString("setKey");
		globalSet.setSetKey(getString("setKey"));
		globalSet.setSetContent(getString("setContent"));
		globalSet.setDescription(getString("description"));
		globalSetFacade.update(globalSet);
		// 记录系统操作日志
		super.logEdit("修改营运平台全局设置，编号为["+key+"]");
		return operateSuccess();
	}

	@Permission("boss:globalset:edit")
	public String editGlobalSetUI() {
		Long id = getLong("id");
		if (id == null) {
			return operateError("全局设置ID为空");
		}
		GlobalSet globalSet = globalSetFacade.getById(id);
		this.pushData(globalSet);
		return "editGlobalSet";
	}

	public String ClobToString(Clob clob) {
		String reString = "";
		Reader is = null;
		try {
			is = clob.getCharacterStream();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 得到流
		BufferedReader br = new BufferedReader(is);
		String s = null;
		try {
			s = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuffer sb = new StringBuffer();
		while (s != null) {
			// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
			sb.append(s);
			try {
				s = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		reString = sb.toString();
		return reString;
	}
}
