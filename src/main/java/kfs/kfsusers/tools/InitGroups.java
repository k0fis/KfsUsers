package kfs.kfsusers.tools;

import java.util.HashMap;
import java.util.Map;
import kfs.kfsusers.domain.KfsGroup;
import kfs.kfsusers.service.KfsUsersService;
import kfs.kfsvaalib.utils.KfsI18n;

/**
 *
 * @author pavedrim
 * @param <T> access enum names
 */
public class InitGroups<T extends Enum<T>> {

    private final KfsUsersService userService;
    private final KfsI18n i18n;
    private final Class<T> cls;
    private final Map<T, KfsGroup> groups;
    
    public InitGroups(KfsUsersService userService, KfsI18n i18n, Class<T> cls) {
        this.userService = userService;
        this.cls = cls;
        this.i18n = i18n;
        groups = new HashMap<>();
    }
    
    public void create() {
        for (T val : cls.getEnumConstants()) {
            KfsGroup group = userService.groupCreate(val.name(), i18n.getMsg(val.getClass().getSimpleName()+"."+val.name()));
            groups.put(val, group);
        }
    }
    
    public KfsGroup getGroup(T val) {
        return groups.get(val);
    }
    
}
