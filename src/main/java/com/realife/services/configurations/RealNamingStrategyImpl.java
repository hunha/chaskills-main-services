package com.realife.services.configurations;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;

public class RealNamingStrategyImpl extends SpringPhysicalNamingStrategy {

	public static final PhysicalNamingStrategyStandardImpl INSTANCE = new PhysicalNamingStrategyStandardImpl();

	@Override
	public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
		return apply(name, context);
	}

	private Identifier apply(Identifier name, JdbcEnvironment jdbcEnvironment) {
		if (name == null) {
			return null;
		}
		
		StringBuilder builder = new StringBuilder(name.getText().replace('.', '_'));
		for (int i = 1; i < builder.length() - 1; i++) {
			if (isUnderscoreRequired(builder.charAt(i - 1), builder.charAt(i), builder.charAt(i + 1))) {
				builder.insert(i++, '_');
			}
		}

		if (!isMarkedAsMany(name.getText())) {
			builder.append("s");
		}

		return getIdentifier(builder.toString(), name.isQuoted(), jdbcEnvironment);
	}

	private boolean isUnderscoreRequired(char before, char current, char after) {
		return Character.isLowerCase(before) && Character.isUpperCase(current) && Character.isLowerCase(after);
	}

	private boolean isMarkedAsMany(String name) {
		return name.substring(name.length() - 1).equalsIgnoreCase("s");
	}
}
