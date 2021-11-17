package web.dao;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;


@Repository
public class UserDaoImpl implements UserDao {


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAll() {
        return entityManager.createQuery("from User").getResultList();
    }

    @Override
    public void create(User user) {
        entityManager.persist(user);
    }

    @Override
    public User readById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void delete(User user) {
        entityManager.remove(entityManager.merge(user));
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

//    @Override
//    public User getUserByName(String name) {
//        System.out.println("Hello DAO");
//        User user = (User) entityManager.createQuery("FROM User u where u.name = :name").getSingleResult();
//        System.out.println(user);
//        return user;
//    }

    @Override
    public User getUserByName(String name) {
        TypedQuery<User> q = entityManager.createQuery("SELECT o FROM User AS o WHERE o.name = :name", User.class);
        q.setParameter("name", name);
        return q.getSingleResult();
    }
}
