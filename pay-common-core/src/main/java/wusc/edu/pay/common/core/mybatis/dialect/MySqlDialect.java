/**
 * wusc.edu.pay.common.core.mybatis.dialect.MySqlDialect.java
 */
package wusc.edu.pay.common.core.mybatis.dialect;

/**
 * 
 * 
 * <ul>
 * <li>Title: MySQL数据库分页适配器</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2014-10-13
 */
public class MySqlDialect extends Dialect {
	public boolean supportsLimitOffset() {
		return true;
	}

	public boolean supportsLimit() {
		return true;
	}

	public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder) {

		if (offset > 0) {
			sql += " limit " + offsetPlaceholder + "," + limitPlaceholder;
		} else {
			sql += " limit " + limitPlaceholder;
		}

		return sql;
	}
}
