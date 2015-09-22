package kfs.kfsusers.dao;

import java.util.List;
import kfs.kfsusers.domain.KfsGroup;
import kfs.kfsusers.domain.KfsUser;
import kfs.springutils.BaseDao;

/**
 *
 * @author pavedrim
 */
public interface UserDao extends BaseDao<KfsUser, String> {

    public KfsUser loadUserByLogin(String user);

    public List<KfsUser> loadByGroup(KfsGroup group);

    public List<KfsUser> load();


}
