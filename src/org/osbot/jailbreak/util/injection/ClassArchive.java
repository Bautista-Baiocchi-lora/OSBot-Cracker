package org.osbot.jailbreak.util.injection;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import org.osbot.jailbreak.data.Constants;
import org.osbot.jailbreak.ui.logger.Logger;
import org.osbot.jailbreak.util.Utilities;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by Ethan on 7/11/2017.
 */
public class ClassArchive {
	public final ArrayList<String> classNames;
	public final HashMap<String, ClassNode> classes;


	public ClassArchive() {
		this.classNames = new ArrayList<>();
		this.classes = new HashMap<>();

	}

	protected void loadClass(InputStream in) throws IOException {
		ClassReader cr = new ClassReader(in);
		ClassNode cn = new ClassNode();
		cr.accept(cn, ClassReader.EXPAND_FRAMES);
		classNames.add(cn.name.replace('/', '.'));
		if(!Utilities.stringContainsItemFromList(cn.name.replace('/', '.'), Constants.ignore)) {
			classes.put(cn.name, cn);
		}

	}

	public void addJar(final String url) {
		try {
			addJar(new URL(url));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void addJar(final File file) {
		try {

			addJar(file.toURI().toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void addJar(final URL url) {
		try {
			addJar(url.openConnection());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addJar(final URLConnection connection) {
		try {
			final ZipInputStream zin = new ZipInputStream(connection.getInputStream());
			ZipEntry e;
			while ((e = zin.getNextEntry()) != null) {
				if (e.isDirectory())
					continue;
				if (e.getName().endsWith(".class")) {
					loadClass(zin);
				}
			}
			zin.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}