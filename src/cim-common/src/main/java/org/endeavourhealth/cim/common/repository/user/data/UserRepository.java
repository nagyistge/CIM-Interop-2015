package org.endeavourhealth.cim.common.repository.user.data;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Row;
import org.endeavourhealth.cim.common.repository.DataConfiguration;
import org.endeavourhealth.cim.common.repository.common.data.InsertStatementBuilder;
import org.endeavourhealth.cim.common.repository.common.data.Repository;
import org.endeavourhealth.cim.common.repository.common.data.RepositoryException;
import org.endeavourhealth.cim.common.repository.common.data.StringKeyRepositoryHelper;
import org.endeavourhealth.cim.common.repository.user.model.User;
import org.endeavourhealth.cim.common.text.TextUtils;

public class UserRepository extends Repository {
	private static final String TableName = "user";

	public UserRepository() {
		super(DataConfiguration.DATASERVICE_KEYSPACE);
	}

	public User getByApiKey(String apiKey) throws RepositoryException {
		Row row = StringKeyRepositoryHelper.getSingleRowFromId(getSession(), getStatementCache(), TableName, "apikey", apiKey);

		if (TextUtils.isNullOrTrimmedEmpty(apiKey))
			return null;

		if (row == null)
			return null;

		return populateUser(row);
	}

	public User getByEmail(String email) throws RepositoryException {
		Row row = StringKeyRepositoryHelper.getSingleRowFromId(getSession(), getStatementCache(), TableName, "email", email);

		if (TextUtils.isNullOrTrimmedEmpty(email))
			return null;

		if (row == null)
			return null;

		return populateUser(row);
	}

	private User populateUser(Row row) {
		User result = new User();
		result.setApiKey(row.getString("apikey"));
		result.setSecret(row.getString("secret"));
		result.setEmail(row.getString("email"));
		result.setData(row.getString("data"));
		result.setDataSchemaVersion(row.getString("data_schema_version"));
		return result;
	}

	public void add(String apikey, String secret, String email, String data, String dataSchemaVersion) throws RepositoryException {

		BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), TableName)
				.addColumnString("apikey", apikey)
				.addColumnString("secret", secret)
				.addColumnString("email", email)
				.addColumnString("data", data)
				.addColumnString("data_schema_version", dataSchemaVersion)
				.build();

		getSession().execute(boundStatement);
	}
}
