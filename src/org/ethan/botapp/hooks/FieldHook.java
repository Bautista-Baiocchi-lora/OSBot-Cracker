package org.ethan.botapp.hooks;

/**
 * Created by Ethan on 1/12/2018.
 */
public class FieldHook {
    public String originalName;
    public String originalDesc;
    public String originalOwner;
    public String customDesc;
    public String mappedName;
    public long multiplier;

    public FieldHook(String originalName, String originalDesc, String originalOwner, String customDesc, String mappedName, long multiplier) {
        this.originalDesc = originalDesc;
        this.customDesc = customDesc;
        this.originalName = originalName;
        this.mappedName = mappedName;
        this.multiplier = multiplier;
    }

    public String getOriginalName() {
        return originalName;
    }

    public String getOriginalDesc() {
        return originalDesc;
    }

    public String getOriginalOwner() {
        return originalOwner;
    }

    public String getCustomDesc() {
        return customDesc;
    }

    public String getMappedName() {
        return mappedName;
    }

    public long getMultiplier() {
        return multiplier;
    }
}
