package com.happytalk.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import com.happytalk.database.SqlMapClient;
import com.happytalk.model.*;

public class DojeDao {
	Logger logger = Logger.getLogger("app");
	SqlSessionFactory mSqlSessionFactory = SqlMapClient.getSqlSessionFactory();

	public List<Users> findUserList(HashMap<String, Object> hashMap) {
		long stasrtTime = System.currentTimeMillis();
		SqlSession session = null;
		List<Users> result = null;
		try {
			session = mSqlSessionFactory.openSession(true);
			result = session.selectList("mysql.findUserList", hashMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		} finally {
			if (session != null)
				session.close();
		}
		long responseTime = System.currentTimeMillis() - stasrtTime;
		logger.debug("findUserList : " + responseTime + "ms");
		return result;
	}
	public int countUserList(HashMap<String, Object> hashMap) {
		long stasrtTime = System.currentTimeMillis();
		SqlSession session = null;
		int result = 0;
		try {
			session = mSqlSessionFactory.openSession(true);
			result = session.selectOne("mysql.countUserList", hashMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		} finally {
			if (session != null)
				session.close();
		}
		long responseTime = System.currentTimeMillis() - stasrtTime;
		logger.debug("countUserList : " + responseTime + "ms");
		return result;
	}

	public int putUserList(HashMap<String, Object> hashMap) {
		long stasrtTime = System.currentTimeMillis();
		SqlSession session = null;
		int result = 0;
		try {
			session = mSqlSessionFactory.openSession(true);
			result = session.insert("mysql.putUserList", hashMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		} finally {
			if (session != null)
				session.close();
		}
		long responseTime = System.currentTimeMillis() - stasrtTime;
		logger.debug("putUserList : " + responseTime + "ms");
		return result;
	}


	public int delUserList(HashMap<String, Object> hashMap) {
		long stasrtTime = System.currentTimeMillis();
		SqlSession session = null;
		int result = 0;
		try {
			session = mSqlSessionFactory.openSession(true);
			result = session.delete("mysql.delUserList", hashMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		} finally {
			if (session != null)
				session.close();
		}
		long responseTime = System.currentTimeMillis() - stasrtTime;
		logger.debug("delUserList : " + responseTime + "ms");
		return result;
	}

	public int editUserList(HashMap<String, Object> hashMap) {
		long stasrtTime = System.currentTimeMillis();
		SqlSession session = null;
		int result = 0;
		try {
			session = mSqlSessionFactory.openSession(true);
			result = session.update("mysql.editUserList", hashMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		} finally {
			if (session != null)
				session.close();
		}
		long responseTime = System.currentTimeMillis() - stasrtTime;
		logger.debug("editUserList : " + responseTime + "ms");
		return result;
	}

}
