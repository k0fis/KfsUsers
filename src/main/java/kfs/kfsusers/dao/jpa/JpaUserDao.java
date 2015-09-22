package kfs.kfsusers.dao.jpa;

import java.util.List;
import javax.persistence.Query;
import kfs.kfsusers.dao.UserDao;
import kfs.kfsusers.domain.KfsGroup;
import kfs.kfsusers.domain.KfsUser;
import kfs.springutils.BaseDaoJpa;
import org.springframework.stereotype.Repository;

/**
 *
 * @author pavedrim
 */
@Repository
public class JpaUserDao extends BaseDaoJpa<KfsUser, String> implements UserDao {

    @Override
    public KfsUser loadUserByLogin(String login) {
        Query query = em.createQuery("select u From KfsUser u Where u.login = :login ORDER BY u.userName")//
                .setParameter("login", login)
                .setMaxResults(1);
        try {
            return (KfsUser)query.getSingleResult();
        } catch (RuntimeException ex) {
            
        }
        return null;
    }

    @Override
    public List<KfsUser> loadByGroup(KfsGroup group) {
        return em.createQuery("select u from KfsUser u where u.login in ( select q.user from KfsUserGroup q where q.group = :group ) order by u.userName")
                .setParameter("group", group)
                .getResultList();
    }

    @Override
    protected Class<KfsUser> getDataClass() {
        return KfsUser.class;
    }

    @Override
    protected String getId(KfsUser data) {
        return data.getLogin();
    }

    @Override
    public List<KfsUser> load() {
        return em.createQuery("SELECT a FROM KfsUser a ORDER BY a.userName").getResultList();
    }

}
