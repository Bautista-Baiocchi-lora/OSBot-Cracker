package org.ethan.ui;


import jdk.internal.org.objectweb.asm.ClassWriter;
import org.ethan.agent.AgentMain;
import org.ethan.data.Engine;

import org.ethan.ui.logger.Logger;
import org.ethan.util.Utilities;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import java.lang.instrument.Instrumentation;

import java.lang.instrument.UnmodifiableClassException;
import java.util.ArrayList;
import java.util.Collection;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ethan on 7/10/2017.
 */
public class ePopUpMenu extends JMenuBar implements ActionListener {

    private static final long serialVersionUID = 1L;
    private final JMenu view;
    private final JMenuItem newClasses, textBox, dumpClasses;
    private Instrumentation instrumentation;
    private String packaging;
    public ePopUpMenu(Instrumentation instrumentation) {
        this.instrumentation = instrumentation;
        view = new JMenu("Debugging");

        newClasses = new JMenuItem("Load Classes");
        newClasses.addActionListener(this);

        textBox = new JCheckBoxMenuItem("Packaging");
        textBox.addActionListener(this);

        dumpClasses = new JCheckBoxMenuItem("Dump");
        dumpClasses.addActionListener(this);

        view.add(newClasses);
        view.add(textBox);
        view.add(dumpClasses);


        add(view);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Load Classes":
                Collection<Class<?>> result = Utilities.getDifference(Engine.getClassCache(), Utilities.getAllClasses(instrumentation));
                for (Class<?> clazz : result) {
                   Logger.log(clazz.toGenericString());
                }
                break;
            case "Packaging":
                packaging = JOptionPane.showInputDialog("What is the packaging", null);
                break;
            case "Dump":
              // AgentMain.dumpClass2();
              break;
        }
    }



    }


