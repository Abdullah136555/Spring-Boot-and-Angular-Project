package com.linkup.dao;


import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.linkup.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;


@Repository(value="userDao")
@Transactional
public class UserDAO {

    @PersistenceContext
    private EntityManager entityManager;
    
   
    
    
	   
   	private Session getSession() {
   		return entityManager.unwrap(Session.class);	   		
   	}
    
   	public Optional<User> getByUsername(String username) {
   	    String hql = "from User where username = :uname";
   	    List<User> users = getSession().createQuery(hql)
   	                        .setParameter("uname", username)
   	                        .list();

   	    if (users.isEmpty()) {
   	        return Optional.empty();
   	    }
   	    return Optional.of(users.get(0));
   	}

   	
//public User getByUsername(User user) {
//	System.out.println(user.getUsername());
////	String hql = "FROM User E WHERE E.username = :uname";
////	Query q =  getSession().createQuery(hql);
////	q.setParameter("uname", user.getUsername());
////	List<User> users = q.getResultList();
//	
//	String sql = "from User where username = '" + user.getUsername() + "'";
//    List<User> users = getSession().createQuery(sql).list();
//	return users.get(0);
//}
//    

    public Optional<User> findById(Long id) {
        User user = entityManager.find(User.class, id);
        return Optional.ofNullable(user);
    }

    public User save(User user) {
        entityManager.persist(user);
        return user;
    }

    public User update(User user) {
        return entityManager.merge(user);
    }

    public List<User> findAll() {
        return entityManager.createQuery("FROM User", User.class).getResultList();
    }
    
    public void deleteById(Long id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

}
