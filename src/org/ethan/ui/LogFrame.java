package org.ethan.ui;

import org.ethan.ui.logger.Logger;
import org.ethan.ui.logger.LoggerPanel;

import javax.swing.*;
import java.awt.*;
import java.lang.instrument.Instrumentation;

/**
 * Created by Ethan on 7/8/2017.
 */
public class LogFrame extends JFrame {
    private Instrumentation instrumentation;

    public LogFrame(Instrumentation instrumentation) {
        this.instrumentation = instrumentation;
        setTitle("Debugger UI");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setJMenuBar(new ePopUpMenu(instrumentation));
        getContentPane().add(new LoggerPanel(new Logger()), BorderLayout.SOUTH);
        setLocationRelativeTo(getParent());
        pack();
        setLocationRelativeTo(getOwner());
        setVisible(true);
    }

}
