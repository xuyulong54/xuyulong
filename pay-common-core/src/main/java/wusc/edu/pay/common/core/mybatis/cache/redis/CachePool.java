package wusc.edu.pay.common.core.mybatis.cache.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.exceptions.JedisConnectionException;
import wusc.edu.pay.common.utils.cache.redis.RedisUtils;

/**
 * ClassName: CachePool <br/>
 * Function:  redis 初始化与连接池实现<br/>
 * <per>
 * 由于需结合Mybatis实现 不与Spring redis注解实现混用。
 * 与Spring redis注解实现 各独立实现各自功能。
 * </per>
 * date: 2014-8-13 下午4:49:36 <br/>
 * 
 * @author laich
 */
public class CachePool {

	JedisSentinelPool pool;  
     private static final CachePool cachePool = new CachePool();  
     private  RedisUtils readisUtils = new RedisUtils();
     
     /**单例模式*/
     public static CachePool getInstance(){  
             return cachePool;  
     }  
     
     /**初始化*/
     private CachePool() {  
              pool = readisUtils.getJedisSentinelPool();
     }  
     
     public  Jedis getJedis(){  
             Jedis jedis = null;  
             boolean borrowOrOprSuccess = true;  
             try {  
                     jedis = pool.getResource();  
             } catch (JedisConnectionException e) {  
                     borrowOrOprSuccess = false;  
                     if (jedis != null)  
                             pool.returnBrokenResource(jedis);  
             } finally {  
                     if (borrowOrOprSuccess)  
                             pool.returnResource(jedis);  
             }  
             jedis = pool.getResource();  
             return jedis;  
     }  
       
     public JedisSentinelPool getJedisPool(){  
             return this.pool;  
     }  
}
