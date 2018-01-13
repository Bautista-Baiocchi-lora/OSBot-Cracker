package org.osbot.jailbreak.botapp.hooks;

import org.osbot.jailbreak.data.Engine;
import org.osbot.jailbreak.ui.logger.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ethan on 1/12/2018.
 */
public class HookCollection {

	private Map<String, FieldHook> fieldHookMap = new HashMap<>();

	public HookCollection() {
		iterateMapOne("org.osbot.core.inject.hook.FieldHook");
		iterateMapTwo("org.osbot.core.inject.hook.FieldHook");
	}

	public void print() {
		for (Map.Entry<String, FieldHook> fieldHookMap : fieldHookMap.entrySet()) {
			Logger.log("MappedName: " + fieldHookMap.getKey() + " || OriginalName: " + fieldHookMap.getValue().originalName + " || desc: " + fieldHookMap.getValue().originalDesc);
		}
	}

	public Object getHookCollection() {
		return Engine.getReflectionEngine().getFieldValue("org.osbot.BotApplication", "iIIIiiiiIIii", Engine.getReflectionEngine().getBotAppInstance());
	}

	public Map<String, Object> getClassHookMapOne() {
		return (Map<String, Object>) Engine.getReflectionEngine().getFieldValue("org.osbot.core.inject.hook.HookCollection", "iiiiiiiiiiIi", getHookCollection());
	}

	public Map<String, Object> getClassHookMapTwo() {
		return (Map<String, Object>) Engine.getReflectionEngine().getFieldValue("org.osbot.core.inject.hook.HookCollection", "IiiIiiiiIiIi", getHookCollection());
	}

	public Map<String, Object> getFieldHookMapOne(Object obj) {
		return (Map<String, Object>) Engine.getReflectionEngine().getFieldValue("org.osbot.core.inject.hook.ClassHook", "iiiiiiiiiiIi", obj);
	}

	public Map<String, Object> getFieldHookMapTwo(Object obj) {
		return (Map<String, Object>) Engine.getReflectionEngine().getFieldValue("org.osbot.core.inject.hook.ClassHook", "IiiIiiiiIiIi", obj);
	}

	public void iterateMapOne(String infoClass) {
		for (Map.Entry<String, Object> entry : getClassHookMapOne().entrySet()) {
			for (Map.Entry<String, Object> entry2 : getFieldHookMapOne(entry.getValue()).entrySet()) {
				Object obj = entry2.getValue();
				fieldHookMap.put(getMappedName(infoClass, obj), new FieldHook(getOriginalName(infoClass, obj), getOriginalDesc(infoClass, obj), "", getCustomDesc(infoClass, obj), getMappedName(infoClass, obj), 1));
			}
		}
	}

	public void iterateMapTwo(String infoClass) {
		for (Map.Entry<String, Object> entry : getClassHookMapTwo().entrySet()) {
			for (Map.Entry<String, Object> entry2 : getFieldHookMapTwo(entry.getValue()).entrySet()) {
				Object obj = entry2.getValue();
				fieldHookMap.put(getMappedName(infoClass, obj), new FieldHook(getOriginalName(infoClass, obj), getOriginalDesc(infoClass, obj), "", getCustomDesc(infoClass, obj), getMappedName(infoClass, obj), 1));
			}
		}
	}


	public String getMappedName(String clazz, Object obj) {
		return (String) Engine.getReflectionEngine().getFieldValue(clazz, "mappedName", obj);
	}

	public String getOriginalName(String clazz, Object obj) {
		return (String) Engine.getReflectionEngine().getFieldValue(clazz, "originalName", obj);
	}

	public String getOriginalDesc(String clazz, Object obj) {
		return (String) Engine.getReflectionEngine().getFieldValue(clazz, "originalDesc", obj);
	}

	public String getCustomDesc(String clazz, Object obj) {
		return (String) Engine.getReflectionEngine().getFieldValue(clazz, "customDesc", obj);
	}

	public Object getFieldMultiplier(Object obj) {
		return Engine.getReflectionEngine().getFieldValue("org.osbot.core.inject.hook.FieldHook", "multiplier", obj);
	}

	public long getFieldMultDecoder(Object obj) {
		return (long) Engine.getReflectionEngine().getFieldValue("org.osbot.core.inject.hook.FieldMultiplier", "decoder", getFieldMultiplier(obj));
	}

	public long getMultiplier(Object obj) {
		return (long) Engine.getReflectionEngine().getFieldValue("org.osbot.core.inject.hook.FieldMultiplier", "iIiIiiiiIiIi", getFieldMultiplier(obj));
	}
}
