package com.bajdcc.LALR1.interpret.module.std;

import com.bajdcc.LALR1.grammar.Grammar;
import com.bajdcc.LALR1.grammar.runtime.IRuntimeDebugInfo;
import com.bajdcc.LALR1.grammar.runtime.RuntimeCodePage;
import com.bajdcc.LALR1.interpret.module.IInterpreterModule;
import com.bajdcc.util.ResourceLoader;

/**
 * 【模块】基础库
 *
 * @author bajdcc
 */
public class ModuleStdBase implements IInterpreterModule {

	private static ModuleStdBase instance = new ModuleStdBase();
	private RuntimeCodePage runtimeCodePage;

	public static ModuleStdBase getInstance() {
		return instance;
	}

	@Override
	public String getModuleName() {
		return "std.base";
	}

	@Override
	public String getModuleCode() {
		return ResourceLoader.load(getClass());
	}

	@Override
	public RuntimeCodePage getCodePage() throws Exception {
		if (runtimeCodePage != null)
			return runtimeCodePage;

		String base = ResourceLoader.load(getClass());

		Grammar grammar = new Grammar(base);
		RuntimeCodePage page = grammar.getCodePage();
		IRuntimeDebugInfo info = page.getInfo();

		return runtimeCodePage = page;
	}
}