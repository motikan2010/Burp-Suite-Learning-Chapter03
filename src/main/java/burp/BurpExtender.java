package burp;

import com.motikan2010.SampleTab;

import javax.swing.*;
import java.awt.*;


public class BurpExtender implements IBurpExtender, ITab {

    private static final String EXTENSION_NAME = "Sample Tab Extender";

    private SampleTab sampleTab;

    public void registerExtenderCallbacks(final IBurpExtenderCallbacks iBurpExtenderCallbacks) {
        iBurpExtenderCallbacks.setExtensionName(EXTENSION_NAME);

        SwingUtilities.invokeLater(() -> {
            sampleTab = SampleTab.getInstance();
            sampleTab.render(SampleTab.BOX_LAYOUT);
            iBurpExtenderCallbacks.addSuiteTab(BurpExtender.this);
        });
    }

    public String getTabCaption() {
        return EXTENSION_NAME;
    }

    public Component getUiComponent() {
        return sampleTab;
    }
}
