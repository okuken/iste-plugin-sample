package iste;

import javax.swing.JOptionPane;

import okuken.iste.plugin.api.IIstePlugin;
import okuken.iste.plugin.api.IIstePluginCallbacks;

public class IstePlugin implements IIstePlugin {

	private SampleIstePluginPanel sampleIstePluginPanel;

	@Override
	public void registerCallbacks(IIstePluginCallbacks callbacks) {

		callbacks.setIstePluginName("Sample ISTE Plugin");

		callbacks.registerIsteContextMenuFactory(new SampleIsteContextMenuFactory(this));

		callbacks.registerIsteRepeaterContextMenuFactory(new SampleIsteRepeaterContextMenuFactory(this));

		sampleIstePluginPanel = new SampleIstePluginPanel(callbacks);
		callbacks.addIstePluginTab(sampleIstePluginPanel);

	}

	@Override
	public void projectChanged() {
		sampleIstePluginPanel.refreshProjectOptions();
	}

	@Override
	public void unloaded() {
		showDialog("Sample ISTE Plugin unloaded");
	}

	void showDialog(String msg) {
		JOptionPane.showMessageDialog(sampleIstePluginPanel, msg);
	}

}
