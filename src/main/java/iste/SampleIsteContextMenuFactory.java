package iste;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import okuken.iste.plugin.api.IIsteContextMenuFactory;
import okuken.iste.plugin.api.IIsteContextMenuNode;
import okuken.iste.plugin.api.IIsteExportMessage;
import okuken.iste.plugin.api.impl.DefaultIsteContextMenuGroup;
import okuken.iste.plugin.api.impl.DefaultIsteContextMenuItem;

public class SampleIsteContextMenuFactory implements IIsteContextMenuFactory {

	private IstePlugin istePlugin;

	private List<? extends IIsteContextMenuNode> contextMenu;

	public SampleIsteContextMenuFactory(IstePlugin istePlugin) {
		this.istePlugin = istePlugin;
	}

	@Override
	public List<? extends IIsteContextMenuNode> createContextMenu() {
		if(contextMenu == null) {
			contextMenu = createContextMenuImpl();
		}
		return contextMenu;
	}

	private List<? extends IIsteContextMenuNode> createContextMenuImpl() {
		var ret = new ArrayList<IIsteContextMenuNode>();

		var menuGroupA = new DefaultIsteContextMenuGroup("Sample ISTE Plugin Menu Group A");
		ret.add(menuGroupA);

		var menuItemA = new DefaultIsteContextMenuItem("Menu Item A");
		menuItemA.setAction(isteExportMessages -> {
			var msg = convertIsteExportMessageToString(isteExportMessages);
			istePlugin.showDialog(msg);
		});
		menuGroupA.addChild(menuItemA);

		var menuItemB = new DefaultIsteContextMenuItem("Sample ISTE Plugin Menu Item B");
		menuItemB.setAction(message -> {
			istePlugin.showDialog("Menu Item B!");
		});
		ret.add(menuItemB);

		return ret;
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

}
