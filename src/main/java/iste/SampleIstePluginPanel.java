package iste;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import okuken.iste.plugin.api.IIsteImportMessage;
import okuken.iste.plugin.api.IIsteMessage;
import okuken.iste.plugin.api.IIsteMessageNotes;
import okuken.iste.plugin.api.IIstePluginCallbacks;
import okuken.iste.plugin.api.IIstePluginTab;
import okuken.iste.plugin.api.impl.DefaultIsteImportMessage;
import okuken.iste.plugin.api.impl.DefaultIsteMessage;
import okuken.iste.plugin.api.impl.DefaultIsteMessageNotes;

import javax.swing.JTextArea;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class SampleIstePluginPanel extends JPanel implements IIstePluginTab {

	private static final long serialVersionUID = 1L;

	private IIstePluginCallbacks callbacks;

	private JTextField textFieldProjectOption1;
	private JTextField textFieldProjectOption2;

	private JTextField textFieldName;
	private JTextField textFieldRemark;
	private JTextField textFieldPriority;
	private JComboBox<IsteProgress> comboBoxProgress;
	private JTextField txtFieldProgressNotes;
	private JTextArea textAreaNotes;

	private JTextField textFieldHost;
	private JTextField textFieldPort;
	private JCheckBox chckbxUseHttps;

	private JTextArea textAreaRequest;
	private JTextArea textAreaResponse;

	public SampleIstePluginPanel(IIstePluginCallbacks callbacks) {
		this.callbacks = callbacks;
		setLayout(new BorderLayout(0, 0));
		
		JPanel panelNorth = new JPanel();
		panelNorth.setBorder(new LineBorder(new Color(128, 128, 128)));
		FlowLayout flowLayout = (FlowLayout) panelNorth.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		add(panelNorth, BorderLayout.NORTH);
		
		JLabel lblProjectOption1 = new JLabel("Project Option 1:");
		panelNorth.add(lblProjectOption1);
		
		textFieldProjectOption1 = new JTextField();
		panelNorth.add(textFieldProjectOption1);
		textFieldProjectOption1.setColumns(10);
		
		JLabel lblProjectOption2 = new JLabel("Project Option 2:");
		panelNorth.add(lblProjectOption2);
		
		textFieldProjectOption2 = new JTextField();
		panelNorth.add(textFieldProjectOption2);
		textFieldProjectOption2.setColumns(10);
		
		JButton btnSaveProjectOption = new JButton("Save Project Options");
		btnSaveProjectOption.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveProjectOptions();
			}
		});
		panelNorth.add(btnSaveProjectOption);
		
		JPanel panelCenter = new JPanel();
		panelCenter.setBorder(new LineBorder(Color.GRAY));
		add(panelCenter);
		
		JButton btnAddToListTab = new JButton("Add to List tab");
		btnAddToListTab.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addToListTab();
			}
		});
		panelCenter.add(btnAddToListTab);
		
		JLabel lblName = new JLabel("Name:");
		panelCenter.add(lblName);
		
		textFieldName = new JTextField();
		textFieldName.setText("Sample name");
		panelCenter.add(textFieldName);
		textFieldName.setColumns(10);
		
		JLabel lblRemark = new JLabel("Remark:");
		panelCenter.add(lblRemark);
		
		textFieldRemark = new JTextField();
		textFieldRemark.setText("Sample remark");
		panelCenter.add(textFieldRemark);
		textFieldRemark.setColumns(10);
		
		JLabel lblPriority = new JLabel("Priority:");
		panelCenter.add(lblPriority);
		
		textFieldPriority = new JTextField();
		textFieldPriority.setText("A");
		panelCenter.add(textFieldPriority);
		textFieldPriority.setColumns(2);
		
		JLabel lblProgress = new JLabel("Progress:");
		panelCenter.add(lblProgress);
		
		comboBoxProgress = new JComboBox<IsteProgress>();
		Arrays.stream(IsteProgress.values()).forEach(isteProgress -> {
			comboBoxProgress.addItem(isteProgress);
		});
		panelCenter.add(comboBoxProgress);
		
		JLabel lblProgressNotes = new JLabel("Progress Notes:");
		panelCenter.add(lblProgressNotes);
		
		txtFieldProgressNotes = new JTextField();
		txtFieldProgressNotes.setText("Sample progress notes");
		panelCenter.add(txtFieldProgressNotes);
		txtFieldProgressNotes.setColumns(10);
		
		JLabel lblNotes = new JLabel("Notes:");
		panelCenter.add(lblNotes);
		
		JScrollPane scrollPaneNotes = new JScrollPane();
		panelCenter.add(scrollPaneNotes);
		
		textAreaNotes = new JTextArea();
		textAreaNotes.setRows(5);
		textAreaNotes.setColumns(20);
		textAreaNotes.setText("Notes about \"Sample name\".\r\nhoge\r\nfuga");
		scrollPaneNotes.setViewportView(textAreaNotes);
		
		JLabel lblHost = new JLabel("Host:");
		panelCenter.add(lblHost);
		
		textFieldHost = new JTextField();
		textFieldHost.setText("localhost");
		panelCenter.add(textFieldHost);
		textFieldHost.setColumns(10);
		
		JLabel lblPort = new JLabel("Port:");
		panelCenter.add(lblPort);
		
		textFieldPort = new JTextField();
		textFieldPort.setText("8080");
		panelCenter.add(textFieldPort);
		textFieldPort.setColumns(10);
		
		chckbxUseHttps = new JCheckBox("Use HTTPS");
		panelCenter.add(chckbxUseHttps);
		
		JLabel lblRequest = new JLabel("Request:");
		panelCenter.add(lblRequest);
		
		JScrollPane scrollPaneRequest = new JScrollPane();
		panelCenter.add(scrollPaneRequest);
		
		textAreaRequest = new JTextArea();
		textAreaRequest.setText("GET /index.html?p=1 HTTP/1.1\r\nHost: localhost\r\n\r\n");
		scrollPaneRequest.setViewportView(textAreaRequest);
		textAreaRequest.setRows(5);
		textAreaRequest.setColumns(30);
		
		JLabel lblResponse = new JLabel("Response:");
		panelCenter.add(lblResponse);
		
		JScrollPane scrollPaneResponse = new JScrollPane();
		panelCenter.add(scrollPaneResponse);
		
		textAreaResponse = new JTextArea();
		textAreaResponse.setText("HTTP/1.1 200 OK\r\nSet-Cookie: sessionid=xxxxxxxxxx\r\nContent-Type: text/html\r\nContent-Length: 44\r\n\r\n<html><body>Hello ISTE Plugin!</body></html>");
		scrollPaneResponse.setViewportView(textAreaResponse);
		textAreaResponse.setRows(5);
		textAreaResponse.setColumns(30);
		
		loadProjectOptions();
	}

	private static final String PROJECT_OPTION_KEY_OPTION1 = "option1";
	private static final String PROJECT_OPTION_KEY_OPTION2 = "option2";
	private void loadProjectOptions() {
		textFieldProjectOption1.setText(Optional.ofNullable(callbacks.loadIstePluginProjectOption(PROJECT_OPTION_KEY_OPTION1)).orElse(""));
		textFieldProjectOption2.setText(Optional.ofNullable(callbacks.loadIstePluginProjectOption(PROJECT_OPTION_KEY_OPTION2)).orElse(""));
	}
	private void saveProjectOptions() {
		callbacks.saveIstePluginProjectOption(PROJECT_OPTION_KEY_OPTION1, textFieldProjectOption1.getText());
		callbacks.saveIstePluginProjectOption(PROJECT_OPTION_KEY_OPTION2, textFieldProjectOption2.getText());
	}
	void refreshProjectOptions() {
		loadProjectOptions();
	}

	private void addToListTab() {
		try {
			callbacks.importIsteMessages(Arrays.asList(createIsteImportMessage()));
		} catch (Exception e) {
			e.printStackTrace(new PrintStream(callbacks.getStderr(), true));
			JOptionPane.showMessageDialog(this, e.getMessage() + System.lineSeparator() + "detail: Extender > Extensions > Errors");
		}
	}
	private IIsteImportMessage createIsteImportMessage() {
		var ret = new DefaultIsteImportMessage();
		ret.setMessage(createIsteMessage());
		ret.setNotes(createIsteMessageNotes());
		return ret;
	}
	private IIsteMessage createIsteMessage() {
		var ret = new DefaultIsteMessage();
		ret.setHost(textFieldHost.getText());
		ret.setPort(Integer.parseInt(textFieldPort.getText()));
		ret.setProtocol(chckbxUseHttps.isSelected() ? "https" : "http");
		ret.setRequest(textAreaRequest.getText().getBytes(StandardCharsets.ISO_8859_1));
		ret.setResponse(textAreaResponse.getText().getBytes(StandardCharsets.ISO_8859_1));
		return ret;
	}
	private IIsteMessageNotes createIsteMessageNotes() {
		var ret = new DefaultIsteMessageNotes();
		ret.setName(textFieldName.getText());
		ret.setRemark(textFieldRemark.getText());
		ret.setPriority(textFieldPriority.getText());
		ret.setProgress(comboBoxProgress.getItemAt(comboBoxProgress.getSelectedIndex()).id);
		ret.setProgressNotes(txtFieldProgressNotes.getText());
		ret.setNotes(textAreaNotes.getText());
		return ret;
	}

	private enum IsteProgress {
		NEW  (IIsteMessageNotes.PROGRESS_NEW,  "New"),
		WORK (IIsteMessageNotes.PROGRESS_WORK, "Work"),
		HOLD (IIsteMessageNotes.PROGRESS_HOLD, "Hold"),
		ABORT(IIsteMessageNotes.PROGRESS_ABORT,"Abort"),
		DONE (IIsteMessageNotes.PROGRESS_DONE, "Done");

		private final Integer id;
		private final String caption;

		private IsteProgress(Integer id, String caption) {
			this.id = id;
			this.caption = caption;
		}
		@Override
		public String toString() {
			return caption;
		}
	}

	@Override
	public String getTabCaption() {
		return "Sample ISTE Plugin Tab";
	}

	@Override
	public Component getUiComponent() {
		return this;
	}

}
