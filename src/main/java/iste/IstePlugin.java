package iste;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import okuken.iste.plugin.api.IIsteContextMenuFactory;
import okuken.iste.plugin.api.IIsteContextMenuInvocation;
import okuken.iste.plugin.api.IIstePlugin;
import okuken.iste.plugin.api.IIstePluginCallbacks;
import okuken.iste.plugin.api.IIstePluginTab;

public class IstePlugin implements IIstePlugin {

	private IIstePluginTab pluginTab;

	@Override
	public void registerCallbacks(IIstePluginCallbacks callbacks) {

		callbacks.setIstePluginName("Sample ISTE Plugin");

		callbacks.registerIsteContextMenuFactory(new IIsteContextMenuFactory() {
			@Override
			public List<JMenuItem> createMenuItems(IIsteContextMenuInvocation invocation) {
				return Arrays.asList(createMenuItem(invocation));
			}
		});

		pluginTab = new IIstePluginTab() {
			@Override
			public String getTabCaption() {
				return "Sample ISTE Plugin Tab";
			}
			@Override
			public Component getUiComponent() {
				var ret = new JPanel();
				ret.add(new JLabel("Sample Label"));
				return ret;
			}
		};
		callbacks.addIstePluginTab(pluginTab);

	}

	private JMenuItem createMenuItem(IIsteContextMenuInvocation invocation) {
		JMenuItem ret = new JMenuItem("Sample ISTE Plugin Menu Item");
		ret.addActionListener((ActionEvent e) -> {
			var str = invocation.getSelectedMessages().stream()
					.map(message -> message.getName() + " / " + message.getRemark())
					.collect(Collectors.joining(System.lineSeparator()));

			JOptionPane.showMessageDialog(pluginTab.getUiComponent(), str);
		});
		return ret;
	}

	@Override
	public void unloaded() {
		JOptionPane.showMessageDialog(pluginTab.getUiComponent(), "Sample ISTE Plugin unloaded");
	}

}
