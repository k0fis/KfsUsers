package kfs.kfsusers.tools;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.lang.reflect.Field;
import kfs.kfsusers.domain.KfsUser;
import kfs.kfsusers.service.KfsUsersService;
import kfs.kfsvaalib.comps.KfsButton;
import kfs.kfsvaalib.kfsTable.KfsTable;
import kfs.kfsvaalib.kfsTable.Pos;
import kfs.kfsvaalib.listener.KfsButtonClickListener;
import kfs.kfsvaalib.utils.KfsI18n;
import kfs.kfsvaalib.utils.KfsRefresh;

/**
 *
 * @author pavedrim
 */
public class AdmUsersList extends Window implements KfsRefresh,
        KfsTable.TableGeneratorFactory, Table.ColumnGenerator, Button.ClickListener {

    private final UI ui;
    private final AdmUserDlg newDlg;
    private final AdmUserDlg editDlg;
    private final KfsUsersService usersService;
    private final BeanItemContainer<KfsUser> cont;
    private KfsRefresh kfsRefresh;

    public AdmUsersList(KfsI18n i18n, UI ui, KfsUsersService usersService, Component ... comps) {
        this(i18n, ui, usersService, 
                new AdmUserDlg(i18n, ui, usersService, true),
                new AdmUserDlg(i18n, ui, usersService, false),
                "AdmUsers", "AdmUsers");
    }
    public AdmUsersList(KfsI18n i18n, UI ui, KfsUsersService usersService, 
            AdmUserDlg newDlg, AdmUserDlg editDlg, 
            String tableName, String i18nPrefix, Component ... comps) {
        this.ui = ui;
        this.newDlg = newDlg;
        this.editDlg = editDlg;
        this.usersService = usersService;
        this.cont = new BeanItemContainer<>(KfsUser.class);
        KfsTable<KfsUser> table;
        HorizontalLayout hl;
        VerticalLayout vl = new VerticalLayout(
                (table = new KfsTable<>(tableName, i18n, KfsUser.class,
                        i18n.getMsg(i18nPrefix+".title"), cont, null, this)),
                (hl = new HorizontalLayout(
                        new Button(
                                i18n.getMsg(i18nPrefix+".new"),
                                new KfsButtonClickListener(this, "newClick")),
                        new Button(
                                i18n.getMsg(i18nPrefix+".refresh"),
                                new KfsButtonClickListener(this, "refreshClick"))
                ))
        );
        vl.addComponents(comps);
        table.setWidth("100%");
        table.setHeight("500px");
        hl.setSpacing(true);
        vl.setSpacing(true);
        vl.setMargin(true);
        setContent(vl);
        setSizeFull();
    }

    public void show(KfsRefresh kfsRefresh) {
        this.kfsRefresh = kfsRefresh;
        kfsRefresh();
        ui.addWindow(this);
    }

    @Override
    public void kfsRefresh() {
        cont.removeAllItems();
        cont.addAll(usersService.userLoad());
        if (kfsRefresh!= null) {
            kfsRefresh.kfsRefresh();
        }
    }

    public void newClick(Button.ClickEvent event) {
        KfsUser ne = new KfsUser();
        newDlg.show(ne, this);
    }

    public void refreshClick(Button.ClickEvent event) {
        kfsRefresh();
    }

    @Override
    public Table.ColumnGenerator getColumnGenerator(Class type, Field field, Pos pos) {
        if ((type == KfsUser.class) && (field.getName().equals("login"))) {
            return this;
        }
        return null;
    }

    @Override
    public Object generateCell(Table source, Object itemId, Object columnId) {
        KfsUser ku = (KfsUser) itemId;
        KfsButton<KfsUser> user = new KfsButton<>(ku.getLogin(), ku, this);
        user.addStyleName("small");
        return user;
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        editDlg.show(((KfsButton<KfsUser>) event.getButton()).getButtonData(), this);
    }
}
