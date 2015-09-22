package kfs.kfsusers.dao.jpa;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import kfs.kfsusers.dao.GroupDao;
import kfs.kfsusers.domain.KfsGroup;
import kfs.kfsusers.domain.KfsUser;
import kfs.springutils.BaseDaoJpa;
import org.springframework.stereotype.Repository;

/**
 *
 * @author pavedrim
 */
@Repository
public class JpaGroupDao extends BaseDaoJpa<KfsGroup, String> implements GroupDao {
    
//    @Override
//    public List<KfsGroup> loadGroupsByUser(KfsUser user) {
//        return em.createQuery("select g FROM KfsGroup g, KfsUser u Where g member u.groups and u.login = :login")//
//                .setParameter("login", user.getLogin())//
//                .getResultList();
//    }
    
    @Override
    public List<KfsGroup> loadGroups() {
        return em.createQuery("select g from KfsGroup g").getResultList();
    }
    
    @Override
    public KfsGroup loadGroupByUserAndGroupName(KfsUser user, String groupName) {
        try {
        return (KfsGroup) em.createQuery("select g FROM KfsGroup g Where g.groupName in (select q.group from KfsUserGroup q where q.user = :user) and g.groupName = :groupName")//
                .setParameter("user", user)//
                .setParameter("groupName", groupName)
                .setMaxResults(1)
                .getSingleResult();
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Cannot find: " + groupName, ex);
            return null;
        }
    }

    @Override
    protected Class<KfsGroup> getDataClass() {
        return KfsGroup.class;
    }

    @Override
    protected String getId(KfsGroup data) {
        return data.getGroupName();
    }

    @Override
    public List<KfsGroup> loadGroups(KfsUser user) {
        return em.createQuery("select g FROM KfsGroup g Where g.groupName in (select q.group from KfsUserGroup q where q.user = :user) ")//
                .setParameter("user", user)//
                .getResultList();
        
    }
    
}
