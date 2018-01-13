package org.osbot.jailbreak.util.injection;

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.MethodVisitor;

/**
 * Created by Ethan on 1/13/2018.
 */
public class ModifierClassWriter extends ClassVisitor {
    private int api;

    public ModifierClassWriter(int api, ClassWriter cv) {
        super(api, cv);
        this.api = api;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {

        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        ModifierMethodWriter mvw = new ModifierMethodWriter(api, mv, name);
        return mvw;
    }

}