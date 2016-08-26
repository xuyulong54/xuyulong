package wusc.edu.pay.common.utils;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述：
 * File Name:ThreadUtil
 * @author laich
 * @date  2013-11-15上午12:29:12
 * @verstion 1.0
 * Copyright (c) 2013, laichunhua@gzzyzz.com All Rights Reserved. 
 */

public class ThreadUtil {
	
	@SuppressWarnings("rawtypes")
	private static final ThreadLocal ctx = new ThreadLocal();

	 @SuppressWarnings({ "rawtypes", "unchecked" })
	public static void put(Object key, Object value) {
	  Map m = (Map) ctx.get();
	  if (m == null) {
	   m = new HashMap();
	  }
	  m.put(key, value);
	  ctx.set(m);
	 }

	 public static Object get(Object key) {
	  @SuppressWarnings("rawtypes")
	Map m = (Map) ctx.get();
	  if (m != null) {
	   return m.get(key);
	  }
	  return null;
	 }

}
