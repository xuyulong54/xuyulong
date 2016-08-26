package wusc.edu.pay.core.boss.biz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.core.boss.dao.CityDao;
import wusc.edu.pay.core.boss.dao.ProvinceDao;
import wusc.edu.pay.core.boss.dao.TownDao;
import wusc.edu.pay.facade.boss.entity.City;
import wusc.edu.pay.facade.boss.entity.Province;
import wusc.edu.pay.facade.boss.entity.Town;


/**
 * 类描述：省市区Biz接口
 * 
 * @author: huangbin
 * @date： 日期：2013-11-27 时间：下午3:14:42
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
@Component("provinceBiz")
public class ProvinceBiz {
	@Autowired
	private ProvinceDao provinceDao;
	@Autowired
	private CityDao cityDao;
	@Autowired
	private TownDao townDao;

	/***
	 * 查询省列表
	 * 
	 * @param paramMap
	 * @return
	 */
	public List listProvince(Map<String, Object> paramMap) {
		return provinceDao.listBy(paramMap);
	}

	/***
	 * 查询城市列表
	 * 
	 * @param paramMap
	 * @return
	 */
	public List listCityBy(Map<String, Object> paramMap) {
		return cityDao.listBy(paramMap);
	}

	/***
	 * 查询地区城镇列表
	 * 
	 * @param paramMap
	 * @return
	 */
	public List listTownBy(Map<String, Object> paramMap) {
		return townDao.listBy(paramMap);
	}

	public Province getProvinceByCode(String code) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("provinceNo", code);
		return provinceDao.getBy(map);
	}

	public City getCityByCode(String code) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cityNo", code);
		return cityDao.getBy(map);
	}

	public Town getTownByCode(String code) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("townNo", code);
		return townDao.getBy(map);
	}
}
