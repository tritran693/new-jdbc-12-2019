package com.laptrinhjavaweb.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.laptrinhjavaweb.dao.GenericDAO;
import com.laptrinhjavaweb.mapper.RowMapper;

public class AbstractDAO<T> implements GenericDAO<T> {

	ResourceBundle rb = ResourceBundle.getBundle("db");

	public Connection getConnection() {
		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			String url = "jdbc:mysql://localhost:3306/servlet";
//			String user = "root";
//			String password = "";
			Class.forName(rb.getString("driverName"));
			String url = rb.getString("url");
			String user = rb.getString("user");
			String password = rb.getString("password");
			return DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException | SQLException e) {
			return null;
		}
	}

	@Override
	public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... parameters) {
		List<T> results = new ArrayList<>();
		Connection connection = null;
		PreparedStatement stament = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection();
			stament = connection.prepareStatement(sql);
			setParemater(stament, parameters);
			resultSet = stament.executeQuery();
			while (resultSet.next()) {
				results.add(rowMapper.mapRow(resultSet));
			}
			return results;
		} catch (SQLException e) {
			return null;
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (stament != null) {
					stament.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				return null;
			}
		}
	}

	private void setParemater(PreparedStatement stament, Object... parameters) {
		try {
			for (int i = 0; i < parameters.length; i++) {
				Object parameter = parameters[i];
				int index = i + 1;
				if (parameter instanceof Long) {
					stament.setLong(index, (long) parameter);
				} else if (parameter instanceof String) {
					stament.setString(index, (String) parameter);
				} else if (parameter instanceof Integer) {
					stament.setInt(index, (int) parameter);
				} else if (parameter instanceof Timestamp) {
					stament.setTimestamp(index, (Timestamp) parameter);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(String sql, Object... parameters) {
		Connection connection = null;
		PreparedStatement stament = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			stament = connection.prepareStatement(sql);
			setParemater(stament, parameters);
			stament.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e2) {
					// TODO: handle exception
				}
			}
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (stament != null) {
					stament.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Long insert(String sql, Object... parameters) {
		ResultSet resultSet = null;
		Connection connection = null;
		PreparedStatement stament = null;
		try {
			Long id = null;
			connection = getConnection();
			connection.setAutoCommit(false);
			stament = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			setParemater(stament, parameters);
			stament.executeUpdate();
			resultSet = stament.getGeneratedKeys();
			if (resultSet.next()) {
				id = resultSet.getLong(1);
			}
			connection.commit();
			return id;
		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (stament != null) {
					stament.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public int count(String sql, Object... parameters) {
		Connection connection = null;
		PreparedStatement stament = null;
		ResultSet resultSet = null;
		try {
			int count = 0;
			connection = getConnection();
			stament = connection.prepareStatement(sql);
			setParemater(stament, parameters);
			resultSet = stament.executeQuery();
			while (resultSet.next()) {
				count = resultSet.getInt(1);
			}
			return count;
		} catch (SQLException e) {
			return 0;
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (stament != null) {
					stament.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				return 0;
			}
		}
	}
}
