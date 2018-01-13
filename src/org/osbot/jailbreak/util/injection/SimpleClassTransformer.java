package org.osbot.jailbreak.util.injection;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.Opcodes;
import org.osbot.jailbreak.ui.logger.Logger;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * Created by Ethan on 1/13/2018.
 */

public class SimpleClassTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

        if(className.contains("LPT8")) {
            Logger.log("We're testing this.");
            ClassReader classReader = new ClassReader(classfileBuffer);

            final ClassWriter cw = new ClassWriter(classReader, ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);

            ModifierClassWriter mcw = new ModifierClassWriter(Opcodes.ASM5, cw);

            classReader.accept(mcw, 0);

           return cw.toByteArray();
        }

        return classfileBuffer;
    }

}

