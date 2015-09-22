package kfs.kfsusers.dao;

import java.util.List;
import kfs.kfsusers.domain.KfsGroup;
import kfs.kfsusers.domain.KfsUser;
import kfs.springutils.BaseDao;

/**
 *
 * @author pavedrim
 */
public interface GroupDao extends BaseDao<KfsGroup, String>{
    
    public List<KfsGroup> loadGroups();
    public List<KfsGroup> loadGroups(KfsUser user);
    
    KfsGroup loadGroupByUserAndGroupName(KfsUser user, String groupName);

}
