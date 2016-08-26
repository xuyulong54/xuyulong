package wusc.edu.pay.facade.boss.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.core.boss.biz.ProvinceBiz;
import wusc.edu.pay.facade.boss.entity.City;
import wusc.edu.pay.facade.boss.entity.Province;
import wusc.edu.pay.facade.boss.entity.Town;
import wusc.edu.pay.facade.boss.exceptions.BossBizException;
import wusc.edu.pay.facade.boss.service.ProvinceFacade;


/**
 * 类描述：省市区对外发布接口
 * 
 * @author: huangbin
 * @date： 日期：2013-11-27 时间：下午3:22:14
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
@Component("provinceFacade")
public class ProvinceFacadeImpl implements ProvinceFacade {
	@Autowired
	private ProvinceBiz provinceBiz;

	/***
	 * 查询省列表
	 * 
	 * @param paramMap
	 * @return
	 */
	public List listProvince(Map<String, Object> paramMap) throws BossBizException {
		return provinceBiz.listProvince(paramMap);
	}

	/***
	 * 查询城市列表
	 * 
	 * @param paramMap
	 * @return
	 */
	public List listCityBy(Map<String, Object> paramMap) throws BossBizException {
		return provinceBiz.listCityBy(paramMap);
	}

	/***
	 * 查询地区城镇列表
	 * 
	 * @param paramMap
	 * @return
	 */
	public List listTownBy(Map<String, Object> paramMap) throws BossBizException {
		return provinceBiz.listTownBy(paramMap);
	}

	public Province getProvinceByCode(String code) throws BossBizException {
		return provinceBiz.getProvinceByCode(code);
	}

	public City getCityByCode(String code) throws BossBizException {
		return provinceBiz.getCityByCode(code);
	}

	public Town getTownByCode(String code) throws BossBizException {
		return provinceBiz.getTownByCode(code);
	}
	
	
	
}
