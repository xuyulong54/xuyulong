package wusc.edu.pay.web.portal.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.facade.boss.entity.City;
import wusc.edu.pay.facade.boss.entity.Province;
import wusc.edu.pay.facade.boss.entity.Town;
import wusc.edu.pay.facade.boss.service.ProvinceFacade;
import wusc.edu.pay.web.portal.base.BaseAction;

import com.alibaba.fastjson.JSONObject;

/**
 * 通讯地址三级联动控制类
 * 
 * @author liliqiong
 * @date 2013-11-28
 * @version 1.0
 */
public class AddressAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private static final Log LOG = LogFactory.getLog(AddressAction.class);
	@Autowired
	private ProvinceFacade provinceFacade;

	@SuppressWarnings("unchecked")
	public void getaddress() {
		HttpServletResponse resp = null;
		try {
			Integer provinceNo = getInteger("provinceNo");// 省
			Integer cityNo = getInteger("cityNo");// 市
			String json = null;
			Map<String, Object> paramMap = new HashMap<String, Object>();
			if (!ValidateUtils.isEmpty(provinceNo) && provinceNo != 0) {
				paramMap.put("provinceNo", provinceNo);
				List<City> list = provinceFacade.listCityBy(paramMap);
				json = JSONObject.toJSONString(list);
			} else if (!ValidateUtils.isEmpty(cityNo) && cityNo != 0) {
				paramMap.put("cityNo", cityNo);
				List<Town> list = provinceFacade.listTownBy(paramMap);
				json = JSONObject.toJSONString(list);
			} else {
				List<Province> list = provinceFacade.listProvince(paramMap);
				json = JSONObject.toJSONString(list);
			}
			resp = getHttpResponse();
			resp.setCharacterEncoding("utf-8");
			resp.getWriter().write(json.toString());
		} catch (Exception e) {
			LOG.error("AddressAction fail:", e);
		} finally {
			try {
				if (!ValidateUtils.isEmpty(resp)) {
					resp.getWriter().close();
				}
			} catch (IOException e) {
				LOG.error("AddressAction fail:", e);
			}
		}
	}

	/***
	 * 加载省份信息
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void getProvince() throws Exception {
		String province = getString("province");
		Map<String, Object> searchMap = new HashMap<String, Object>();
		List<Province> list = provinceFacade.listProvince(searchMap);
		StringBuffer sb = new StringBuffer();
		sb.append("<option value=''>请选择</option>");
		for (int i = 0; i < list.size(); i++) {
			Province provinceParam = list.get(i);
			if (!ValidateUtils.isEmpty(province) && province.equals(provinceParam.getProvinceNo())) {
				sb.append("<option value='" + provinceParam.getProvinceNo() + "' selected='selected'>" + provinceParam.getProvinceName() + "</option>");
			} else {
				sb.append("<option value='" + provinceParam.getProvinceNo() + "'>" + provinceParam.getProvinceName() + "</option>");
			}
		}
		outPrint(getHttpResponse(), sb.toString());
	}

	@SuppressWarnings("unchecked")
	public void getCity() throws Exception {
		String provinceNo = getString("province");
		String cityNo = getString("city");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("provinceNo", provinceNo);
		List<City> list = provinceFacade.listCityBy(paramMap);
		StringBuffer sb = new StringBuffer();
		sb.append("<option value=''>请选择</option>");
		for (int i = 0; i < list.size(); i++) {
			City city = list.get(i);
			if (!ValidateUtils.isEmpty(cityNo) && cityNo.equals(city.getCityNo())) {
				sb.append("<option value='" + city.getCityNo() + "' selected='selected'>" + city.getCityName() + "</option>");
			} else {
				sb.append("<option value='" + city.getCityNo() + "' >" + city.getCityName() + "</option>");
			}
		}
		outPrint(this.getHttpResponse(), sb.toString());
	}

	/***
	 * 加载省份信息
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void getArea() throws Exception {
		// String provinceNo = getString("province");
		String cityNo = getString("city");
		String areaNo = getString("area");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cityNo", cityNo);
		List<Town> list = provinceFacade.listTownBy(paramMap);
		StringBuffer sb = new StringBuffer();
		sb.append("<option value=''>请选择</option>");
		for (int i = 0; i < list.size(); i++) {
			Town town = list.get(i);
			if (!ValidateUtils.isEmpty(areaNo) && areaNo.equals(town.getTownNo())) {
				sb.append("<option value='" + town.getTownNo() + "' selected='selected'>" + town.getTownName() + "</option>");
			} else {
				sb.append("<option value='" + town.getTownNo() + "' >" + town.getTownName() + "</option>");
			}
		}
		outPrint(this.getHttpResponse(), sb.toString());
	}
}
