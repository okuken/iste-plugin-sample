package iste;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import okuken.iste.plugin.api.IIsteContextMenuFactory;
import okuken.iste.plugin.api.IIsteContextMenuNode;
import okuken.iste.plugin.api.IIsteExportRepeatMessage;
import okuken.iste.plugin.api.impl.DefaultIsteRepeaterContextMenuItem;

public class SampleIsteRepeaterContextMenuFactory implements IIsteContextMenuFactory {

	private IstePlugin istePlugin;

	private List<? extends IIsteContextMenuNode> contextMenu;

	public SampleIsteRepeaterContextMenuFactory(IstePlugin istePlugin) {
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
		var menuItemX = new DefaultIsteRepeaterContextMenuItem("Sample ISTE Plugin Repeater Menu Item X");
		menuItemX.setAction(isteExportRepeatMessages -> {
			var msg = convertIsteExportRepeatMessageToString(isteExportRepeatMessages);
			istePlugin.showDialog(msg);
		});
		return Arrays.asList(menuItemX);
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

}
