package org.osbot.jailbreak.util.injection;

/**
 * Created by Ethan on 7/11/2017.
 */



import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import org.osbot.jailbreak.data.Constants;
import org.osbot.jailbreak.ui.logger.Logger;
import org.osbot.jailbreak.util.Utilities;
import java.util.HashMap;
import java.util.Map;

public class ASMClassLoader extends ClassLoader {

	public Map<String, Class<?>> classCache;
	public ClassArchive classArchive;


	public ASMClassLoader(final ClassArchive classArchive) {
		this.classCache = new HashMap<>();
		this.classArchive = classArchive;
	}


	public void loadAll() {
		for (Map.Entry<String, ClassNode> map : classArchive.classes.entrySet()) {
			Logger.log("back at top.");
			try {
				if (!Utilities.stringContainsItemFromList(map.getKey().replace('/', '.'), Constants.ignore)) {
					Logger.log("Gonna give load all a go: " + classArchive.classes.size());
					if(classArchive.classes.get(map.getKey()) != null || !classArchive.classes.keySet().contains(map.getKey())) {
						classArchive.classes.remove(map.getKey());
					}
					Class<?> c = nodeToClass(map.getValue());
					Logger.log("Node to class is done: " + map.getKey());
					//classCache.put(map.getKey(), c);
				}
			} catch (Exception e) {
				Logger.logException("ERROR P: " + e.getLocalizedMessage());
			}
		}
	}

	private final Class<?> nodeToClass(ClassNode node) {

		try {
			if (super.findLoadedClass(node.name) != null) {
				Logger.log("Returning super loaded");
				return findLoadedClass(node.name);
			}
			ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
			node.accept(cw);
			byte[] b = cw.toByteArray();
			Logger.log("Returning BYTE");
			return this.defineClass(node.name.replace('/', '.'), b, 0, b.length);

		}catch(Exception e) {
			Logger.logException("ERROR P2: "+e.getLocalizedMessage());
		}
		return null;
}


}

