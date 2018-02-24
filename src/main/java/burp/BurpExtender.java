package burp;

import com.motikan2010.SampleTab;
import com.motikan2010.RequestContextMenu;
import com.motikan2010.util.RequestResponseUtils;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class BurpExtender implements IBurpExtender, IContextMenuFactory, ITab {

    private static IBurpExtenderCallbacks callbacks;
    private static IExtensionHelpers helpers;

    private static final String EXTENSION_NAME = "Sample Tab Extender";

    private SampleTab sampleTab;
    private RequestResponseUtils requestResponseUtils;

    public void registerExtenderCallbacks(final IBurpExtenderCallbacks c) {
        c.setExtensionName(EXTENSION_NAME);
        callbacks = c;
        helpers = c.getHelpers();
        this.requestResponseUtils = RequestResponseUtils.getInstance();

        // タブの追加
        SwingUtilities.invokeLater(() -> {
            sampleTab = new SampleTab(requestResponseUtils);
            sampleTab.render();
            c.addSuiteTab(BurpExtender.this);
        });

        // コンテキストメニューの登録
        c.registerContextMenuFactory(this);
    }

    public String getTabCaption() {
        return EXTENSION_NAME;
    }

    public Component getUiComponent() {
        return sampleTab;
    }

    public static IBurpExtenderCallbacks getCallbacks() {
        return callbacks;
    }

    public static IExtensionHelpers getHelpers() {
        return helpers;
    }

    /**
     * コンテキストメニューの作成
     * @param iContextMenuInvocation
     * @return
     */
    @Override
    public List<JMenuItem> createMenuItems(IContextMenuInvocation iContextMenuInvocation) {
        IHttpRequestResponse[] httpRequestResponseArray = iContextMenuInvocation.getSelectedMessages();
        if (null == httpRequestResponseArray) {
            return null;
        }

        List<JMenuItem> jMenuItemList = new LinkedList<>();

        JMenuItem requestJMenuItem = new JMenuItem("Save Request");
        requestJMenuItem.addMouseListener(new RequestContextMenu(httpRequestResponseArray, sampleTab));
        jMenuItemList.add(requestJMenuItem);

        return jMenuItemList;
    }
}
