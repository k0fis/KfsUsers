package kfs.kfsusers.tools;

import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kfs.kfsusers.domain.KfsGroup;
import kfs.kfsusers.domain.KfsUser;
import kfs.kfsusers.service.KfsUsersService;
import kfs.kfsvaalib.fields.KfsPasswd;
import kfs.kfsvaalib.kfsForm.KfsField;
import kfs.kfsvaalib.kfsForm.MFieldGroup;
import kfs.kfsvaalib.listener.KfsButtonClickListener;
import kfs.kfsvaalib.utils.KfsI18n;
import kfs.kfsvaalib.utils.KfsRefresh;

/**
 *
 * @author pavedrim
 */
public class AdmUserDlg extends Window {

    private final KfsUsersService userService;
    private final String i18Pref;
    private final KfsI18n i18n;
    private final UI ui;
    private final boolean isNew;
    private final MFieldGroup mfg;

    private KfsUser user;
    private KfsRefresh kfsRefresh;
    private Map<KfsGroup, CheckBox> cbLst;

    public AdmUserDlg(KfsI18n i18n, UI ui, KfsUsersService userService, boolean isNew) {
        this(i18n, ui, userService, isNew, "AdmUserDlg" + (isNew ? ".new" : ".edit"), "AdmUserDlg");
    }
    public AdmUserDlg(KfsI18n i18n, UI ui, KfsUsersService userService, boolean isNew, 
            String formName, String i18Prefix) {
        super(i18n.getMsg(i18Prefix+".title"));
        this.ui = ui;
        this.i18n = i18n;
        this.i18Pref = i18Prefix;
        this.isNew = isNew;
        this.userService = userService;
        cbLst = new HashMap<>();
        mfg = new MFieldGroup(i18n, formName, new MFieldGroup.MFieldFactory() {

            @Override
            public Field createField(Class objectClass, String filedName, KfsField kfsField, Class fieldClass) {
                if (kfsField.fieldClass().equals(KfsPasswd.class)) {
                    return new KfsPasswd(AdmUserDlg.this.i18n.getMsg(i18Pref+".changePasswd"));
                }
                return null;
            }
        }, KfsUser.class
        );
        setModal(true);
        FormLayout cont = new FormLayout(mfg.getSortedComponents());

        for (KfsGroup group : userService.groupLoadAll()) {
            CheckBox cb = new CheckBox(group.getGroupLabel());
            cont.addComponent(cb);
            cbLst.put(group, cb);
        }

        cont.setMargin(true);

        HorizontalLayout row = new HorizontalLayout();
        row.addComponents(
                new Button(i18n.getMsg(i18Pref+".ok"),
                        new KfsButtonClickListener(this, "okClick")),
                new Button(i18n.getMsg(i18Pref+".cancel"),
                        new KfsButtonClickListener(this, "cancelClick"))
        );
        row.setSpacing(true);
        cont.addComponent(row);
        setContent(cont);
        setWidth("550px");
    }

    public void show(KfsUser user, KfsRefresh kfsRefresh) {
        this.user = user;
        this.kfsRefresh = kfsRefresh;
        mfg.setItems(user);
        for (CheckBox cb : cbLst.values()) {
            cb.setValue(false);
        }
        for (KfsGroup kg : userService.groupLoadUser(user)) {
            cbLst.get(kg).setValue(true);
        }
        ui.addWindow(this);
    }

    public void cancelClick(Button.ClickEvent event) {
        this.close();
    }

    public void okClick(Button.ClickEvent event) {
        try {
            mfg.commit();
        } catch (Exception ex) {
            Notification.show(i18n.getMsg(i18Pref+".error") + "  -  " + ex.getMessage(), Notification.Type.ERROR_MESSAGE);
            throw new RuntimeException("Cannot commit data", ex);
        }
        List<KfsGroup> grpList = new ArrayList<>();
        for (KfsGroup grp : cbLst.keySet()) {
            if (cbLst.get(grp).getValue()) {
                grpList.add(grp);
            }
        }
        if (isNew) {
            userService.userInsert(user, grpList);
        } else {
            userService.userSave(user, grpList);
        }
        if (kfsRefresh != null) {
            kfsRefresh.kfsRefresh();
        }
        this.close();
    }
}
