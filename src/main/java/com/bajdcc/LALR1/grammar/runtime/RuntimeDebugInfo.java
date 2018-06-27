package com.bajdcc.LALR1.grammar.runtime;

import com.bajdcc.LALR1.grammar.runtime.data.RuntimeArray;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 【扩展】调试与开发
 *
 * @author bajdcc
 */
public class RuntimeDebugInfo implements IRuntimeDebugInfo, Serializable {

	private static final long serialVersionUID = 1L;
	private HashMap<String, Object> dataMap = new HashMap<>();
	private HashMap<String, Integer> exports = new HashMap<>();
	private HashMap<Integer, String> func = new HashMap<>();
	private HashMap<String, IRuntimeDebugValue> externalValue = new HashMap<>();
	private HashMap<String, IRuntimeDebugExec> externalExec = new HashMap<>();

	public void addExports(String name, Integer addr) {
		exports.put(name, addr);
	}

	public void addFunc(String name, int addr) {
		func.put(addr, name);
	}

	@Override
	public String getFuncNameByAddress(int addr) {
		return func.get(addr);
	}

	@Override
	public int getAddressOfExportFunc(String name) {
		return exports.getOrDefault(name, -1);
	}

	@Override
	public IRuntimeDebugValue getValueCallByName(String name) {
		return externalValue.get(name);
	}

	@Override
	public IRuntimeDebugExec getExecCallByName(String name) {
		return externalExec.get(name);
	}

	@Override
	public boolean addExternalValue(String name, IRuntimeDebugValue func) {
		return externalValue.put(name, func) != null;
	}

	@Override
	public boolean addExternalFunc(String name, IRuntimeDebugExec func) {
		return externalExec.put(name, func) != null;
	}

	private static String argsToString(RuntimeObjectType[] args) {
		if (args == null) {
			return "无";
		}
		return Arrays.stream(args).map(RuntimeObjectType::getName).collect(Collectors.joining("，"));
	}

	@Override
	public List<RuntimeArray> getExternFuncList() {
		List<RuntimeArray> array = new ArrayList<>();
		externalExec.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).forEach(a -> {
			RuntimeArray arr = new RuntimeArray();
			arr.add(new RuntimeObject(a.getKey()));
			arr.add(new RuntimeObject(BigInteger.valueOf(exports.getOrDefault(a.getKey(), -1))));
			arr.add(new RuntimeObject(argsToString(a.getValue().getArgsType())));
			arr.add(new RuntimeObject(String.valueOf(a.getValue().getDoc())));
			array.add(arr);
		});
		return array;
	}

	@Override
	public HashMap<String, Object> getDataMap() {
		return dataMap;
	}
}
