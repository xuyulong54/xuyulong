package wusc.edu.pay.common.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;

import wusc.edu.pay.common.utils.json.JSONUtils;


/**
 * 类型转换工具类
 * 
 * @version:
 */
public class ConvertUtils {
	public enum Type {
		BOOLEAN(new String[] { "boolean", "java.lang.boolean" }), STRING(new String[] { "string", "java.lang.string" }), INT(new String[] {
				"int", "integer", "java.lang.integer" }), LONG(new String[] { "long", "java.lang.long" }), BIGINT(new String[] { "bigint",
				"biginteger", "java.math.biginteger" }), DOUBLE(new String[] { "double", "java.lang.double" }), DATE(new String[] { "date",
				"java.util.date" }), SQLDATE(new String[] { "sqldate", "java.sql.date" }), TIMESTAMP(new String[] { "timestamp",
				"java.sql.timestamp" }), MAP(new String[] { "map" }), LIST(new String[] { "list" }), DECIMAL(new String[] { "decimal",
				"java.math.bigdecimal" });

		private String[] names;

		private Type(String[] names) {
			this.names = names;
		}

		public static Type parseType(String name) {
			if (name != null) {
				name = name.toLowerCase();
			}
			for (Type type : Type.values()) {
				for (String tname : type.names) {
					if (tname.equals(name)) {
						return type;
					}
				}
			}
			return null;
		}

		public static Type parseType(Class clz) {
			if (clz == null) {
				return null;
			}
			return parseType(clz.getName());
		}
	}

	public static Object convert(String toType, String value) {
		Type type = Type.parseType(toType);
		if (type == null) {
			throw new RuntimeException("unsupport type : " + toType);
		}
		return convert(type, value);
	}

	public static Object convert(Class toType, String value) {
		if (toType != null && toType.isEnum()) {
			try {
				return Enum.valueOf(toType, value);
			} catch (Exception e) {
			}
		}

		Type type = Type.parseType(toType);
		if (type == null) {
			throw new RuntimeException("unsupport type : " + toType);
		}
		return convert(type, value);
	}

	public static Object convert(Type toType, String value) {
		CheckUtils.notNull(toType, "toType");
		if (Type.STRING.equals(toType)) {
			return value;
		}
		if (CheckUtils.isEmpty(value)) {
			return null;
		}
		switch (toType) {
		case INT:
			return Integer.parseInt(value);
		case LONG:
			return Long.parseLong(value);
		case BIGINT:
			return new BigInteger(value);
		case DOUBLE:
			return Double.parseDouble(value);
		case DATE: {
			try {
				return DateUtils.parseDate(value, new String[] { DateUtils.DATE_FORMAT_DATEONLY, DateUtils.DATE_FORMAT_DATETIME });
			} catch (ParseException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		case SQLDATE: {
			try {
				return new java.sql.Date(DateUtils.parseDate(value, DateUtils.DATE_FORMAT_DATEONLY).getTime());
			} catch (ParseException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		case TIMESTAMP: {
			try {
				return new java.sql.Timestamp(DateUtils.parseDate(value, DateUtils.DATE_FORMAT_DATETIME).getTime());
			} catch (ParseException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		case BOOLEAN:
			return Boolean.parseBoolean(value);
		case DECIMAL:
			return new BigDecimal(value);
			// TODO
		case MAP: {
			return JSONUtils.jsonToMap(value, String.class, String.class);
		}
		case LIST: {
			return JSONUtils.jsonToList(value, String.class);
		}
		default:
			throw new RuntimeException("unsupport type : " + toType);
		}
	}
}
