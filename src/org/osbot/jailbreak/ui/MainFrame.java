package org.osbot.jailbreak.ui;

import org.osbot.jailbreak.botapp.hooks.HookCollection;
import org.osbot.jailbreak.data.Constants;
import org.osbot.jailbreak.data.Engine;
import org.osbot.jailbreak.ui.logger.Logger;
import org.osbot.jailbreak.ui.logger.LoggerPanel;
import org.osbot.jailbreak.util.Utilities;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.instrument.Instrumentation;
import java.util.Collection;

public class MainFrame extends JFrame implements ActionListener {


	private final Instrumentation instrumentation;
	private final JButton dump, load;
	private final JTextField packageName;
	private final JMenuBar menuBar;
	private final JMenu fileMenu;
	private final JMenuItem hookCollection;

	public MainFrame(Instrumentation instrumentation) {
		super("Jailbroken OSBot");
		this.instrumentation = instrumentation;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		Box controlsLayout = Box.createHorizontalBox();

		this.dump = new JButton("Dump");
		this.dump.setActionCommand("dump");
		this.dump.addActionListener(this::actionPerformed);
		controlsLayout.add(dump);

		this.load = new JButton("Load");
		this.load.setActionCommand("load");
		this.load.addActionListener(this::actionPerformed);
		controlsLayout.add(load);

		this.packageName = new JTextField();
		packageName.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				Logger.log("Setting pattern: "+packageName.getText());
				Engine.setPattern(packageName.getText());
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				Engine.setPattern(packageName.getText());
			}

			@Override
			public void changedUpdate(DocumentEvent e) {

			}
		});
		controlsLayout.add(packageName);

		this.add(controlsLayout, BorderLayout.NORTH);
		this.add(new LoggerPanel(new Logger()), BorderLayout.SOUTH);


		this.hookCollection = new JMenuItem("Strip hooks");
		this.hookCollection.setActionCommand("strip hooks");
		this.hookCollection.addActionListener(this::actionPerformed);
		this.fileMenu = new JMenu("File");
		this.fileMenu.add(hookCollection);
		this.menuBar = new JMenuBar();
		this.menuBar.add(fileMenu);
		setJMenuBar(menuBar);

		pack();
		setLocationRelativeTo(getOwner());
		setVisible(true);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		switch (e.getActionCommand()) {
			case "load":
				Engine.setPattern(packageName.getText());
				Logger.log("Searching pattern: " + Engine.getPattern());
				Collection<Class<?>> result = Utilities.getDifference(Engine.getClassCache(), Utilities.getAllClasses(instrumentation));
				for (Class<?> clazz : result) {
					if (clazz.toGenericString().contains(Engine.getPattern())) {
						Logger.log(clazz.toGenericString());
					}
				}
				break;
			case "dump":
				Utilities.dumpJar(new File(Constants.dumpDir + Engine.getPattern() + ".jar"));
				break;
			case "strip hooks":
				if (Engine.getHookCollection() == null) {
					Engine.setHookCollection(new HookCollection());
				}
				Engine.getHookCollection().print();
				break;
		}
	}
}
