package kfs.kfsusers.dao;

import java.util.List;
import kfs.kfsusers.domain.KfsGroup;
import kfs.kfsusers.domain.KfsUser;
import kfs.kfsusers.domain.KfsUserGroup;
import kfs.springutils.BaseDao;

/**
 *
 * @author pavedrim
 */
public interface UserGroupDao extends BaseDao<KfsUserGroup, String> {

    void save(KfsUser user, List<KfsGroup> groups);
    boolean has(KfsUser user, KfsGroup group);

}
