package edu.utoledo.panorama.dao;

import java.io.IOException;
import java.io.Reader;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * class for configuring the PowerCampus DB for MyBatis configurator
 * @author Anil Sehgal
 */
public class BaseDAO 
{
	private static SqlSessionFactory factory;

	protected BaseDAO() {
	}

	static
	{
		Reader reader = null;
		try {
			reader = Resources.getResourceAsReader("resources/sqlmap-admin.xml");
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
		factory = new SqlSessionFactoryBuilder().build(reader);
	}

	public static SqlSessionFactory getSqlSessionFactory() 
	{
		return factory;
	}
}
