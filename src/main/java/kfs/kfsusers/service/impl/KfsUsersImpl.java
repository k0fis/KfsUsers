package kfs.kfsusers.service.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Logger;
import kfs.kfsusers.dao.GroupDao;
import kfs.kfsusers.dao.UserDao;
import kfs.kfsusers.dao.UserGroupDao;
import kfs.kfsusers.domain.KfsGroup;
import kfs.kfsusers.domain.KfsUser;
import kfs.kfsusers.service.KfsUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author pavedrim
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class KfsUsersImpl implements KfsUsersService {

    @Autowired
    UserDao userDao;
    @Autowired
    GroupDao groupDao;
    @Autowired
    UserGroupDao userGroupDao;

    @Override
    public List<KfsUser> userLoad() {
        return userDao.load();
    }

    @Override
    public KfsUser userLoad(String username) {
        try {
            return userDao.findById(username);
        } catch (RuntimeException ex) {
            return null;
        }
    }

    @Override
    public void userInsert(KfsUser user, List<KfsGroup> grpList) {
        if ((user.getUserName() == null) || (user.getUserName().isEmpty())) {
            user.setUserName(user.getLogin());
        }
        if ((user.getPasswordOld()== null) || (user.getPasswordOld().length() < 2)) {
            throw new KfsUsersException("Bad Password");
        }
        user.setPassword(encode(user.getPasswordOld()));
        userDao.insert(user);
        groupSaveUsersGroups(user, grpList);
    }

    @Override
    public void userSave(KfsUser user) {
        if ((user.getUserName() == null) || (user.getUserName().isEmpty())) {
            user.setUserName(user.getLogin());
        }
        if (user.getPasswordOld() != null) {
            Logger.getLogger(getClass().getName()).info("User " + user.getLogin() + " change password");
            user.setPassword(encode(user.getPasswordOld()));
        }
        userDao.update(user);

    }
    @Override
    public void userSave(KfsUser user, List<KfsGroup> grpList) {
        userSave(user);
        groupSaveUsersGroups(user, grpList);

    }

    private String encode(String password) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            throw new KfsUsersException("Cannot find MD5 class", ex);
        }
        md.update(password.getBytes());
        byte[] digest = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", (int) (b & 0xff)));
        }
        return sb.toString();
    }

    @Override
    public KfsUser userCreate(String userLogin, String passwd, List<KfsGroup> grpList) {
        KfsUser usr;
        try {
            usr = userDao.loadUserByLogin(userLogin);
        } catch (RuntimeException ex) {
            usr = null;
        }
        if (usr == null) {
            usr = new KfsUser();
            usr.setLogin(userLogin);
            usr.setUserName(userLogin);
            usr.setLine("");
            usr.setPhone("");
            usr.setMail("");
            usr.setPasswordOld(passwd);
            userInsert(usr, grpList);
        } else {
            if (grpList != null) {
                groupSaveUsersGroups(usr, grpList);
            }
        }
        return usr;
    }

    @Override
    public KfsGroup groupCreate(String groupName, String groupLabel) {
        KfsGroup group = groupDao.findById(groupName);
        if (group == null) {
            group = new KfsGroup();
            group.setGroupName(groupName);
            group.setGroupLabel(groupLabel);
            groupDao.insert(group);
        } else {
            if (!group.getGroupLabel().equals(groupLabel)) {
                group.setGroupLabel(groupLabel);
                groupDao.update(group);
            }
        }
        return group;
    }

    @Override
    public void groupSave(KfsGroup group) {
        groupDao.update(group);
    }

    @Override
    public List<KfsGroup> groupLoadAll() {
        return groupDao.loadGroups();
    }

    @Override
    public List<KfsUser> userLoadByGroup(KfsGroup grp0) {
        return userDao.loadByGroup(grp0);
    }

    @Override
    public KfsGroup groupFindByName(String groupName) {
        return groupDao.findById(groupName);
    }

    @Override
    public KfsGroup groupLoadGroupByUserAndGroupName(KfsUser user, String groupName) {
        return groupDao.loadGroupByUserAndGroupName(user, groupName);
    }

    @Override
    public List<KfsGroup> groupLoadUser(KfsUser user) {
        return groupDao.loadGroups(user);
    }

    @Override
    public boolean isUserInGroup(KfsUser user, String groupName) {
        KfsGroup group = groupDao.findById(groupName);
        return isUserInGroup(user, group);
    }

    public void groupSaveUsersGroups(KfsUser user, List<KfsGroup> groups) {
        userGroupDao.save(user, groups);
    }

    @Override
    public boolean isUserInGroup(KfsUser user, KfsGroup group) {
        return userGroupDao.has(user, group);
    }

}
