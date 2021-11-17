package web.dao;

import org.springframework.stereotype.Repository;
import web.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<Role> getAll() {
        return entityManager.createQuery("from Role").getResultList();
    }

    @Override
    public void create(Role role) {
        entityManager.persist(role);
    }

    @Override
    public Role readById(int id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public void delete(Role role) {
        entityManager.remove(entityManager.merge(role));
    }

    @Override
    public void updateRole(Role role) {
        entityManager.merge(role);
    }

    @Override
    public Role getByName(String roleName) {
        TypedQuery<Role> query = entityManager.createQuery(
                "SELECT role FROM Role role WHERE role.role = :role", Role.class);
        return query
                .setParameter("role", roleName)
                .getSingleResult();
    }

}
