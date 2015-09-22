package kfs.kfsusers.dao.jpa;

import java.util.Arrays;
import java.util.List;
import kfs.kfsusers.dao.UserGroupDao;
import kfs.kfsusers.domain.KfsGroup;
import kfs.kfsusers.domain.KfsUser;
import kfs.kfsusers.domain.KfsUserGroup;
import kfs.springutils.BaseDaoJpa;
import org.springframework.stereotype.Repository;

/**
 *
 * @author pavedrim
 */
@Repository
public class UserGroupDaoJpa extends BaseDaoJpa<KfsUserGroup, String> implements UserGroupDao {

    @Override
    protected Class<KfsUserGroup> getDataClass() {
        return KfsUserGroup.class;
    }

    @Override
    protected String getId(KfsUserGroup data) {
        return data.getId();
    }

    @Override
    public void save(KfsUser user, List<KfsGroup> groups) {
        List<KfsUserGroup> lst = em.createQuery("select u from KfsUserGroup u where u.user = :user")
                .setParameter("user", user)
                .getResultList();
        if (groups == null) {
            groups = Arrays.asList();
        }
        for (KfsUserGroup u : lst) {
            boolean isIn = false;
            for (KfsGroup kg : groups) {
                if (u.getGroup().getGroupName().equals(kg.getGroupName())) {
                    isIn = true;
                    break;
                }
            }
            if (!isIn) {
                em.remove(u);
            }
        }
        for (KfsGroup kg : groups) {
            boolean isIn = false;
            for (KfsUserGroup u : lst) {
                if (u.getGroup().getGroupName().equals(kg.getGroupName())) {
                    isIn = true;
                    break;
                }
            }
            if (!isIn) {
                KfsUserGroup kug = new KfsUserGroup();
                kug.setUser(user);
                kug.setGroup(kg);
                em.persist(kug);
            }
        }
    }

    @Override
    public boolean has(KfsUser user, KfsGroup group) {
        Integer i = (Integer) em.createQuery("select count(q) from KfsUserGroup q where q.group = :group and q.user = :user")
                .setParameter("group", group)
                .setParameter("user", user)
                .getSingleResult();
        return (i != null) && (i > 0);
    }

}
