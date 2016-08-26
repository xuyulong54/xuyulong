=======MyBatis缓存使用说明===========
1, 在应用的服务中sping-context.xml import 引入  spring-jedis.xml 先将 spring-jedis.xml 考过去
2, 目前设置 redis默认缓存 时间为1个小时。
3, 在SQLMap.xml 文件中添加了 缓存实现类才能生效如下：
  <cache eviction="LRU" type="wusc.edu.pay.common.utils.cache.redis.mybatisImpl.MybatiesRedisCache" />
具体 可以看 wusc.edu.pay.common.utils.cache.redis.mybatisImpl.RedisCache 头部说明

=======标签注解缓存使用说明===========
1, 在应用的服务中sping-context.xml import 引入  spring-jedis.xml 先将spring-jedis.xml 考过去
2, 目前设置 redis默认缓存 时间为1个小时。
3, 使用 在方法头部添加注解即可
例如：实现类  PayWayFacadeImpl
@Cacheable(value="redisCache",key="'PAYRULE_'+#userNo")
public List<PayWayVo> findPayWayByUserNo(String userNo ,String busType){
		System.out.println("from database-----------");
		return payWayBiz.findPayWayByUserNo(userNo ,busType);
}
 

 部分说明：

 @CachePut(value="redisCache",key="#account.getName()")// 更新 redisCache 缓存
 public Account updateAccount(Account account)
 
 @Cacheable(value="redisCache",key="'"+ee+"'+#merchantNo")//常量添加
 
 @Cacheable、@CachePut、@CacheEvict 注释介绍
通过上面的例子，我们可以看到 spring cache 主要使用两个注释标签，即 @Cacheable、@CachePut 和 @CacheEvict，我们总结一下其作用和配置方法。

表 1. @Cacheable 作用和配置方法
	@Cacheable 的作用	 主要针对方法配置，能够根据方法的请求参数对其结果进行缓存
	@Cacheable 主要的参数
	value	 缓存的名称，在 spring 配置文件中定义，必须指定至少一个	 例如：
		@Cacheable(value=”mycache”) 或者 
		@Cacheable(value={”cache1”,”cache2”}
	key	 缓存的 key，可以为空，如果指定要按照 SpEL 表达式编写，如果不指定，则缺省按照方法的所有参数进行组合	 例如：
		@Cacheable(value=”testcache”,key=”#userName”)
		condition	 缓存的条件，可以为空，使用 SpEL 编写，返回 true 或者 false，只有为 true 才进行缓存	 例如：
	@Cacheable(value=”testcache”,condition=”#userName.length()>2”)

表 2. @CachePut 作用和配置方法
	@CachePut 的作用	 主要针对方法配置，能够根据方法的请求参数对其结果进行缓存，和 @Cacheable 不同的是，它每次都会触发真实方法的调用
	@CachePut 主要的参数
	value	 缓存的名称，在 spring 配置文件中定义，必须指定至少一个	 例如：
		@Cacheable(value=”mycache”) 或者 
		@Cacheable(value={”cache1”,”cache2”}
	key	 缓存的 key，可以为空，如果指定要按照 SpEL 表达式编写，如果不指定，则缺省按照方法的所有参数进行组合	 例如：
		@Cacheable(value=”testcache”,key=”#userName”)
		condition	 缓存的条件，可以为空，使用 SpEL 编写，返回 true 或者 false，只有为 true 才进行缓存	 例如：
	@Cacheable(value=”testcache”,condition=”#userName.length()>2”)

表 3. @CacheEvict 作用和配置方法
	@CachEvict 的作用	 主要针对方法配置，能够根据一定的条件对缓存进行清空
	@CacheEvict 主要的参数
	value	 缓存的名称，在 spring 配置文件中定义，必须指定至少一个	 例如：
		@CachEvict(value=”mycache”) 或者 
		@CachEvict(value={”cache1”,”cache2”}
	key	 缓存的 key，可以为空，如果指定要按照 SpEL 表达式编写，如果不指定，则缺省按照方法的所有参数进行组合	 例如：
		@CachEvict(value=”testcache”,key=”#userName”)
		condition	 缓存的条件，可以为空，使用 SpEL 编写，返回 true 或者 false，只有为 true 才清空缓存	 例如：
		@CachEvict(value=”testcache”,
	condition=”#userName.length()>2”)
		allEntries	 是否清空所有缓存内容，缺省为 false，如果指定为 true，则方法调用后将立即清空所有缓存	 例如：
		@CachEvict(value=”testcache”,allEntries=true)
		beforeInvocation	 是否在方法执行前就清空，缺省为 false，如果指定为 true，则在方法还没有执行的时候就清空缓存，缺省情况下，如果方法执行抛出异常，则不会清空缓存	 例如：
	@CachEvict(value=”testcache”，beforeInvocation=true)

	 