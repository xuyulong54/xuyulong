package wusc.edu.pay.facade.boss.service;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.facade.boss.entity.City;
import wusc.edu.pay.facade.boss.entity.Province;
import wusc.edu.pay.facade.boss.entity.Town;
import wusc.edu.pay.facade.boss.exceptions.BossBizException;


/**
 * 类描述：省市区对外发布接口
 * 
 * @author: huangbin
 * @date： 日期：2013-11-27 时间：下午3:20:15
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public interface ProvinceFacade {

	/***
	 * 查询省列表
	 * 
	 * @param paramMap
	 * @return
	 */
	public List listProvince(Map<String, Object> paramMap) throws BossBizException;
	
	/***
	 * 根据省编号查询
	 * @param code
	 * @return
	 * @throws BossBizException
	 */
	public Province getProvinceByCode(String code) throws BossBizException;

	/***
	 * 查询城市列表
	 * 
	 * @param paramMap
	 * @return
	 */
	public List listCityBy(Map<String, Object> paramMap) throws BossBizException;
	
	/***
	 * 根据市编号查询
	 * @param code
	 * @return
	 * @throws BossBizException
	 */
	public City getCityByCode(String code) throws BossBizException;

	/***
	 * 查询地区城镇列表
	 * 
	 * @param paramMap
	 * @return
	 */
	public List listTownBy(Map<String, Object> paramMap) throws BossBizException;
	
	/***
	 * 根据区编号查询
	 * @param code
	 * @return
	 * @throws BossBizException
	 */
	public Town getTownByCode(String code) throws BossBizException;

}
