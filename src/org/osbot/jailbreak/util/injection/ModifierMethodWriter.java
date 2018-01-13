package org.osbot.jailbreak.util.injection;

import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

/**
 * Created by Ethan on 1/13/2018.
 */

public class ModifierMethodWriter extends MethodVisitor {

    private String methodName;

    public ModifierMethodWriter(int api, MethodVisitor mv, String methodName) {
        super(api, mv);
        this.methodName = methodName;
    }

    @Override
    public void visitCode() {
        super.visitCode();
        super.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        super.visitLdcInsn("method: " + methodName);
        super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
    }

}