package kfs.kfsusers.service;

import java.util.List;
import kfs.kfsusers.domain.KfsGroup;
import kfs.kfsusers.domain.KfsUser;

/**
 *
 * @author pavedrim
 */
public interface KfsUsersService {

    void userInsert(KfsUser user, List<KfsGroup> grpList);
    void userSave(KfsUser user);
    void userSave(KfsUser user, List<KfsGroup> grpList);
    KfsUser userCreate(String user, String pass, List<KfsGroup> grpList);
    KfsUser userLoad(String user);

    List<KfsUser> userLoad();
    List<KfsUser> userLoadByGroup(KfsGroup grp0);

    // GROUP
    KfsGroup groupCreate(String groupName, String groupLabel);
    KfsGroup groupFindByName(String groupName);
    KfsGroup groupLoadGroupByUserAndGroupName(KfsUser user, String groupName);
    void groupSave(KfsGroup group);
    List<KfsGroup> groupLoadAll();

    List<KfsGroup> groupLoadUser(KfsUser user);

    boolean isUserInGroup(KfsUser user, String groupName);
    boolean isUserInGroup(KfsUser user, KfsGroup group);
}
