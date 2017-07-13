package saver;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;

public class GuiWindow {
	public static class Params {
		public final String sourcePath;
		public final String destinationPath;
		
		/**
		 * @param sourcePath
		 * @param destinationPath
		 */
		public Params(String sourcePath, String destinationPath) {
			this.sourcePath = sourcePath;
			this.destinationPath = destinationPath;
		}
	}
	
	//New class CloseableJDialog
	static class CloseableJDialog extends JDialog {
		private static final long serialVersionUID = 1L;

		public CloseableJDialog() {
			setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
		}
	}

	//Dialog dimensions
	private final int buttonWidth = 100;
	private final int buttonHeight = 25;
	private final int fieldWidth = 300;
	private final int fieldHeight = 25;
	private final int windowWidth = 600;
	private final int windowHeight = 200;
	private final int row0 = 10;
	private final int row1 = 40;
	private final int row2 = 120;
	private final int col0 = 10;
	private final int col1 = 140;
	private final int col2 = 450;
	private final String homePath = System.getProperty("user.home");
	private final String src = homePath + "\\AppData\\Local\\Packages\\Microsoft.Windows.ContentDeliveryManager_cw5n1h2txyewy\\LocalState\\Assets";
	private final String dest = homePath+ "\\Pictures\\SpotlightPictures";
	
	
	private CloseableJDialog dialog;
	private JButton sourceBrowse;
	private JButton destinationBrowse;
	private JButton runButton;
	private JButton cancelButton;
	private JLabel sourceFolderLabel;
	private JLabel destinationFolderLabel;
	private JTextField sourceFolderField;
	private JTextField destinationFolderField;
	private JFileChooser choseFolder = new JFileChooser();
	
	
	public void showGui(String sourePath, String destinationPath) {
		dialog = new CloseableJDialog();
		dialog.setModal(true);
		dialog.setBounds(100, 100, windowWidth, windowHeight);
		dialog.setLocationRelativeTo(null);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Container contentPane = dialog.getContentPane();
		contentPane.setLayout(null);
		dialog.setResizable(false);
		dialog.setTitle("Spotlight Saver");
		Font textFont = new Font("SansSerif", Font.PLAIN, 14);
		
		//Document listener
		DocumentListener docListen = new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				validateSettings();
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				validateSettings();
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				validateSettings();
			}
		};
		
		//Source
		sourceFolderLabel = new JLabel("Source Folder:");
		sourceFolderLabel.setBounds(col0, row0, fieldWidth, fieldHeight);
		sourceFolderLabel.setFont(textFont);
		contentPane.add(sourceFolderLabel);
		
		sourceFolderField = new JTextField(src);
		sourceFolderField.setBounds(col1, row0, fieldWidth, fieldHeight);
		sourceFolderField.setFont(textFont);
		sourceFolderField.getDocument().addDocumentListener(docListen);
		contentPane.add(sourceFolderField);
		
		sourceBrowse = new JButton("Browse");
		sourceBrowse.setToolTipText("Search for source folder");
		sourceBrowse.setBounds(col2, row0, buttonWidth, buttonHeight);
		contentPane.add(sourceBrowse);
		
		//Destination
		destinationFolderLabel = new JLabel("Destination Folder:");
		destinationFolderLabel.setBounds(col0, row1, fieldWidth, fieldHeight);
		destinationFolderLabel.setFont(textFont);
		contentPane.add(destinationFolderLabel);
		
		destinationFolderField = new JTextField(dest);
		destinationFolderField.setBounds(col1, row1, fieldWidth, fieldHeight);
		destinationFolderField.setFont(textFont);
		destinationFolderField.getDocument().addDocumentListener(docListen);
		contentPane.add(destinationFolderField);
		
		destinationBrowse = new JButton("Browse");
		destinationBrowse.setToolTipText("Search for destination folder");
		destinationBrowse.setBounds(col2, row1, buttonWidth, buttonHeight);
		contentPane.add(destinationBrowse);


		//Run & Cancel Buttons
		runButton = new JButton("Run");
		runButton.setToolTipText("Run the program");
		runButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		runButton.setBounds(col0, row2, buttonWidth, buttonHeight);
		contentPane.add(runButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setToolTipText("Cancel program");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		cancelButton.setBounds(col2, row2, buttonWidth, buttonHeight);
		contentPane.add(cancelButton);
		
		
		addButtonListener(sourceBrowse, sourceFolderField, "Source Folder");
		addButtonListener(destinationBrowse, destinationFolderField, "Destination Folder");
		
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setFont(textFont);
		dialog.setVisible(true);
	}
	
	
	private void validateSettings() {
		boolean enabled = sourceFolderField.getText().isEmpty() || destinationFolderField.getText().isEmpty();
		runButton.setEnabled(enabled);
		runButton.setToolTipText(enabled ? "" : "Error somewhere...");
	}
	
	private void addButtonListener(JButton button, final JTextField field, final String description) {
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Component frame = null;
				int returnVal = choseFolder.showOpenDialog(frame);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					field.setText(choseFolder.getSelectedFile().toString());
				}
			}
		});
	}
	
	public Params getParams() {
		return new Params(sourceFolderField.getText(), destinationFolderField.getText());
	}
}
