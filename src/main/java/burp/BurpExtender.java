package burp;

import com.motikan2010.RequestTableModel;
import com.motikan2010.SampleTab;
import com.motikan2010.RequestContextMenu;
import com.motikan2010.util.RequestResponseUtils;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class BurpExtender implements IBurpExtender, IContextMenuFactory, ITab {

    private IBurpExtenderCallbacks iBurpExtenderCallbacks;

    private static final String EXTENSION_NAME = "Sample Tab Extender";

    private RequestTableModel requestTableModel;
    private SampleTab sampleTab;
    private RequestResponseUtils requestResponseUtils;

    public void registerExtenderCallbacks(final IBurpExtenderCallbacks callbacks) {
        callbacks.setExtensionName(EXTENSION_NAME);
        this.iBurpExtenderCallbacks = callbacks;
        this.requestResponseUtils = RequestResponseUtils.getInstance(callbacks);

        SwingUtilities.invokeLater(() -> {
            sampleTab = new SampleTab(callbacks, requestResponseUtils);
            sampleTab.render();
            callbacks.addSuiteTab(BurpExtender.this);
        });

        callbacks.registerContextMenuFactory(this);
    }

    public String getTabCaption() {
        return EXTENSION_NAME;
    }

    public Component getUiComponent() {
        return sampleTab;
    }

    @Override
    public List<JMenuItem> createMenuItems(IContextMenuInvocation iContextMenuInvocation) {
        IHttpRequestResponse[] httpRequestResponseArray = iContextMenuInvocation.getSelectedMessages();
        if (null == httpRequestResponseArray) {
            return null;
        }

        List<JMenuItem> jMenuItemList = new LinkedList<>();

        JMenuItem requestJMenuItem = new JMenuItem("Save Request");
        requestJMenuItem.addMouseListener(new RequestContextMenu(this.iBurpExtenderCallbacks, httpRequestResponseArray, sampleTab));
        jMenuItemList.add(requestJMenuItem);

        return jMenuItemList;
    }
}
