package com.cdac.springproject.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.cdac.springproject.dto.User;


@Repository
public class UserDaoImple implements UserDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;
	public void save(final User user) {
		hibernateTemplate.execute(new HibernateCallback<List<User>>() {
			public List<User> doInHibernate(Session session) throws HibernateException {
				Transaction t = session.beginTransaction();
				session.save(user);
				t.commit();
				session.flush();
				session.close();
				return null;
			}
		});
	}

	public void deleteById(final String emailAddress) {
		hibernateTemplate.execute(new HibernateCallback<List<User>>() {
			public List<User> doInHibernate(Session session) throws HibernateException {
				Transaction t = session.beginTransaction();
				session.delete(new User(emailAddress));
				t.commit();
				session.flush();
				session.close();
				return null;
			}
		});
	}

	public void update(final User user) {
		
		hibernateTemplate.execute(new HibernateCallback<List<User>>() {
			public List<User> doInHibernate(Session session) throws HibernateException {
				Transaction t = session.beginTransaction();
				session.update(user);
				t.commit();
				session.flush();
				session.close();
				return null;
			}
		});
	}

	public List<User> getAll() {
		List<User> ul = hibernateTemplate.execute(new HibernateCallback<List<User>>() {
			public List<User> doInHibernate(Session session) throws HibernateException {
				Transaction t = session.beginTransaction();
				Query q = session.createQuery("from User");
				List<User> userList = q.list();
				t.commit();
				session.flush();
				session.close();
				return userList;
			}
		});
		return ul;
	}
	
	@Override
	public User validateUser(User user) {
		List<User> ul = hibernateTemplate.execute(new HibernateCallback<List<User>>() {
			public List<User> doInHibernate(Session session) throws HibernateException {
				Transaction t = session.beginTransaction();
				Query q = session.createQuery("from User where emailAddress = ? and userPass = ? and userRole = ?");
				q.setString(0, user.getEmailAddress());
				q.setString(1, user.getUserPass());
				q.setString(2, user.getUserRole());
				List<User> userList = q.list();
				t.commit();
				session.flush();
				session.close();
				return userList;
			}
		});
		if(!ul.isEmpty()) {
			return ul.get(0);
		}	
		return null;
	}
	
	
	
}