package iste;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import okuken.iste.plugin.api.IIsteContextMenuFactory;
import okuken.iste.plugin.api.IIsteContextMenuNode;
import okuken.iste.plugin.api.IIsteExportMessage;
import okuken.iste.plugin.api.IIsteExportRepeatMessage;
import okuken.iste.plugin.api.IIstePlugin;
import okuken.iste.plugin.api.IIstePluginCallbacks;
import okuken.iste.plugin.api.impl.DefaultIsteContextMenuGroup;
import okuken.iste.plugin.api.impl.DefaultIsteContextMenuItem;
import okuken.iste.plugin.api.impl.DefaultIsteRepeaterContextMenuItem;

public class IstePlugin implements IIstePlugin {

	private SampleIstePluginPanel sampleIstePluginPanel;

	@Override
	public void registerCallbacks(IIstePluginCallbacks callbacks) {

		callbacks.setIstePluginName("Sample ISTE Plugin");

		callbacks.registerIsteContextMenuFactory(new IIsteContextMenuFactory() {
			@Override
			public List<? extends IIsteContextMenuNode> createContextMenu() {
				var ret = new ArrayList<IIsteContextMenuNode>();

				var menuGroupA = new DefaultIsteContextMenuGroup("Sample ISTE Plugin Menu Group A");
				ret.add(menuGroupA);

				var menuItemA = new DefaultIsteContextMenuItem("Menu Item A");
				menuItemA.setAction(isteExportMessages -> {
					var msg = convertIsteExportMessageToString(isteExportMessages);
					showDialog(msg);
				});
				menuGroupA.addChild(menuItemA);

				var menuItemB = new DefaultIsteContextMenuItem("Sample ISTE Plugin Menu Item B");
				menuItemB.setAction(message -> {
					showDialog("Menu Item B!");
				});
				ret.add(menuItemB);

				return ret;
			}
		});

		callbacks.registerIsteRepeaterContextMenuFactory(new IIsteContextMenuFactory() {
			@Override
			public List<? extends IIsteContextMenuNode> createContextMenu() {
				var menuItemX = new DefaultIsteRepeaterContextMenuItem("Sample ISTE Plugin Repeater Menu Item X");
				menuItemX.setAction(isteExportRepeatMessages -> {
					var msg = convertIsteExportRepeatMessageToString(isteExportRepeatMessages);
					showDialog(msg);
				});
				return Arrays.asList(menuItemX);
			}
		});

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

	private String convertIsteExportMessageToString(List<? extends IIsteExportMessage> isteExportMessages) {
		return isteExportMessages.stream()
				.map(m -> Arrays.asList(
						"[Protocol]", m.getMessage().getProtocol(), "[Host]", m.getMessage().getHost(), "[Port]", Integer.toString(m.getMessage().getPort()),
						"[URL]", m.getAnalyzedInfo().getUrl(),
						"[Name]", m.getNotes().getName(), "[Remark]", m.getNotes().getRemark(), "[Priority]", m.getNotes().getPriority(),
						"[Progress]", Integer.toString(m.getNotes().getProgress()), "[Progress notes]", m.getNotes().getProgressNotes(),
						"[Notes]", m.getNotes().getNotes()
					).stream().collect(Collectors.joining(System.lineSeparator())))
				.collect(Collectors.joining(System.lineSeparator() + "----------" + System.lineSeparator()));
	}

	private String convertIsteExportRepeatMessageToString(List<? extends IIsteExportRepeatMessage> isteExportRepeatMessages) {
		return isteExportRepeatMessages.stream()
				.map(m -> Arrays.asList(
						"[Protocol]", m.getMessage().getProtocol(), "[Host]", m.getMessage().getHost(), "[Port]", Integer.toString(m.getMessage().getPort()),
						"[URL]", m.getAnalyzedInfo().getUrl(),
						"[Name]", m.getNotes().getName(), "[Remark]", m.getNotes().getRemark(), "[Priority]", m.getNotes().getPriority(),
						"[Progress]", Integer.toString(m.getNotes().getProgress()), "[Progress notes]", m.getNotes().getProgressNotes(),
						"[Notes]", m.getNotes().getNotes(),
						"[Send date]", m.getRepeatInfo().getSendDate() != null ? m.getRepeatInfo().getSendDate().toString() : null,
						"[User Id (Auth)]", m.getRepeatInfo().getUserId(),
						"[Time]", m.getRepeatInfo().getTime() != null ? Integer.toString(m.getRepeatInfo().getTime()) : null,
						"[Repeater notes]", m.getRepeatInfo().getNotes()
					).stream().collect(Collectors.joining(System.lineSeparator())))
				.collect(Collectors.joining(System.lineSeparator() + "----------" + System.lineSeparator()));
	}

	private void showDialog(String msg) {
		JOptionPane.showMessageDialog(sampleIstePluginPanel, msg);
	}

}
