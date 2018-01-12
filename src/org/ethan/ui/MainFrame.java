package org.ethan.ui;

import org.ethan.data.Engine;
import org.ethan.ui.logger.Logger;
import org.ethan.ui.logger.LoggerPanel;
import org.ethan.util.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.instrument.Instrumentation;
import java.util.Collection;

/**
 * Created by Ethan on 7/8/2017.
 */
public class MainFrame extends JFrame implements ActionListener {


	private final Instrumentation instrumentation;
	private final JButton dump, load;
	private final JTextField packageName;

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
		controlsLayout.add(packageName);

		this.add(controlsLayout, BorderLayout.NORTH);
		this.add(new LoggerPanel(new Logger()), BorderLayout.SOUTH);

		pack();
		setLocationRelativeTo(getOwner());
		setVisible(true);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		switch (e.getActionCommand()) {
			case "load":
				Collection<Class<?>> result = Utilities.getDifference(Engine.getClassCache(), Utilities.getAllClasses(instrumentation));
				for (Class<?> clazz : result) {
					if (clazz.getName().contains("osfisher")) {
						Logger.log(clazz.getName());
					}
				}
				break;
			case "dump":
				//insert dump code here
				break;
		}
	}
}
