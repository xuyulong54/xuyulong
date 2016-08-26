/**
 * wusc.edu.pay.common.core.mybatis.interceptor.ExecutorInterceptor.java
 */
package wusc.edu.pay.common.core.mybatis.interceptor;

import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;

import wusc.edu.pay.common.core.mybatis.dialect.Dialect;


/**
 * 
 * 
 * <ul>
 * <li>Title: Mybatis查询拦截器</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2014-10-13
 */
@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class,
		ResultHandler.class }) })
public class ExecutorInterceptor extends AbstractInterceptor {
	private final static Logger logger = Logger.getLogger(ExecutorInterceptor.class);
	private static int MAPPED_STATEMENT_INDEX = 0;
	private static int PARAMETER_INDEX = 1;
	private static int ROWBOUNDS_INDEX = 2;

	private Dialect dialect;

	public Object intercept(Invocation invocation) throws Throwable {
		processIntercept(invocation.getArgs());
		return invocation.proceed();
	}

	private void processIntercept(final Object[] queryArgs) {
		MappedStatement ms = (MappedStatement) queryArgs[MAPPED_STATEMENT_INDEX];
		Object parameter = queryArgs[PARAMETER_INDEX];
		final RowBounds rowBounds = (RowBounds) queryArgs[ROWBOUNDS_INDEX];
		int offset = rowBounds.getOffset();
		int limit = rowBounds.getLimit();
		// 分页
		if (dialect.supportsLimit() && (offset != RowBounds.NO_ROW_OFFSET || limit != RowBounds.NO_ROW_LIMIT)) {
			BoundSql boundSql = ms.getBoundSql(parameter);

			String sql = boundSql.getSql().replaceAll("\\s{2,}", " ").trim();
			if (dialect.supportsLimitOffset()) {
				sql = dialect.getLimitString(sql, offset, limit);
				offset = RowBounds.NO_ROW_OFFSET;
			} else {
				sql = dialect.getLimitString(sql, 0, limit);
			}

			limit = RowBounds.NO_ROW_LIMIT;

			queryArgs[ROWBOUNDS_INDEX] = new RowBounds(offset, limit);
			BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
			MappedStatement newMs = copyFromMappedStatement(ms, new BoundSqlSqlSource(newBoundSql), false);
			queryArgs[MAPPED_STATEMENT_INDEX] = newMs;

			logger.debug("==>" + sql);

		} else if (parameter instanceof CountParameter) {
			// 获取总数
			parameter = ((CountParameter) parameter).getParameter();
			BoundSql boundSql = ms.getBoundSql(parameter);

			String sql = boundSql.getSql().replaceAll("\\s{2,}", " ").replace(" FROM", " from").replace("ORDER BY", "order by")
					.replace("GROUP BY", "group by").trim();

			if (sql.split("from").length > 2 || sql.split("order by").length > 2 || sql.indexOf("group by") > -1) {
				sql = "select count(1) from (" + sql + ") tmp";
			} else {
				int fromIndex = sql.indexOf(" from");
				sql = "select count(1)" + sql.substring(fromIndex);
				
				int orderByIndex = sql.indexOf("order by");
				if (orderByIndex > 0) {
					sql = sql.substring(0, orderByIndex);
				}
			}

			BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
			MappedStatement newMs = copyFromMappedStatement(ms, new BoundSqlSqlSource(newBoundSql), true);
			queryArgs[MAPPED_STATEMENT_INDEX] = newMs;
			queryArgs[PARAMETER_INDEX] = parameter;

			logger.debug("==>" + sql);
		}
		// 行锁标识
		BoundSql boundSql = ms.getBoundSql(parameter);
		String sql = boundSql.getSql().replaceAll("\\s{2,}", " ").trim();
		if (sql.toLowerCase().endsWith("for update")) {

			if (this.dialect.getClass().getSimpleName().toLowerCase().equals("db2dialect")) {
				// for update with rs
				sql += " with rs";
			} else if (this.dialect.getClass().getSimpleName().toLowerCase().equals("oracledialect")) {
				// for update
			} else if (this.dialect.getClass().getSimpleName().toLowerCase().equals("mysqldialect")) {
				// for update
			}

			queryArgs[ROWBOUNDS_INDEX] = new RowBounds(offset, limit);
			BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
			MappedStatement newMs = copyFromMappedStatement(ms, new BoundSqlSqlSource(newBoundSql), false);
			queryArgs[MAPPED_STATEMENT_INDEX] = newMs;

			logger.debug("==>" + sql);
		}
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
		String dialectClass = properties.getProperty("dialectClass");
		try {
			dialect = (Dialect) Class.forName(dialectClass).newInstance();
		} catch (Exception e) {
			throw new RuntimeException("cannot create dialect instance by dialectClass:" + dialectClass, e);
		}
	}

	public static class CountParameter {
		private Object parameter;

		public CountParameter(Object parameter) {
			this.parameter = parameter;
		}

		public Object getParameter() {
			return parameter;
		}
	}

	public static void main(String[] args) {
		String sql = "select temp.* from (select id,var_3 as 'abc', var_4 as 'cde' from  youtable where 1=1 and var_3='1')group by var_3) as temp order by temp.id desc";

		System.out.println(sql.split("from").length);

		sql = sql.trim().replace("\r", "").replace("\n", "").replaceAll("\\s{2,}", " ").replace(" FROM ", " from ")
				.replace("ORDER BY", "order by");
		int fromIndex = sql.lastIndexOf(" from ");
		sql = "select count(1)" + sql.substring(fromIndex);
		int orderByIndex = sql.indexOf("order by");
		sql = sql.substring(0, orderByIndex);

		System.out.println("grop by asdfads".indexOf("group by"));
	}
}
