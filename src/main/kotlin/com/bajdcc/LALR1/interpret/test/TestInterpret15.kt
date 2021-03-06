package com.bajdcc.LALR1.interpret.test

import com.bajdcc.LALR1.grammar.Grammar
import com.bajdcc.LALR1.grammar.runtime.RuntimeCodePage
import com.bajdcc.LALR1.grammar.runtime.RuntimeException
import com.bajdcc.LALR1.interpret.Interpreter
import com.bajdcc.LALR1.syntax.handler.SyntaxException
import com.bajdcc.util.lexer.error.RegexException

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

object TestInterpret15 {

    @JvmStatic
    fun main(args: Array<String>) {
        try {
            val codes = arrayOf("""
import "sys.base";
import "sys.proc";
g_proc_exec("
import \"user.base\";
g_fork();
for (var i = 1; i <= 10; i++) {
    var w = g_window(g_guid());
    var width = 80 * i;
    var height = 60 * i;
    var border = 10;
    w.\"msg\"(0, width, height);
    w.\"svg\"('M', border, border);
    w.\"svg\"('L', width - border, border);
    w.\"svg\"('L', width - border, height - border);
    w.\"svg\"('L', border, height - border);
    w.\"svg\"('L', border, border);
    g_sleep_s(1);
    w.\"destroy\"();
}
");
""".trimIndent(), """
import "sys.base";
import "sys.proc";
g_proc_exec("
import \"user.base\";
g_fork();
for (var i = 0; i < 2; i++) {
    var w = g_window(g_guid());
    var width = 800;
    var height = 600;
    var border = 10;
    w.\"msg\"(0, width, height);
    w.\"svg\"('M', border, border);
    w.\"svg\"('L', width - border, border);
    w.\"svg\"('L', width - border, height - border);
    w.\"svg\"('L', border, height - border);
    w.\"svg\"('L', border, border);
    w.\"svg\"('M', border * 2, border * 2);
    w.\"svg\"('S', width - border * 4, height - border * 4);
    w.\"str\"(1, g_string_rep(\"Hello world! \", 20));
    w.\"svg\"('m', 0, 200);
    w.\"str\"(1, g_string_rep(\"Hello world! \", 20));
    w.\"svg\"('m', 0, 200);
    w.\"str\"(0, g_string_rep(\"Hello world! \", 20));
    g_sleep_s(1);
    w.\"msg\"(2, 0, 0);
}
");
""".trimIndent(), """
import "sys.base";
import "sys.proc";
g_proc_exec("
import \"user.base\";
if (g_fork() == -1) {
    g_printn(g_http_get(\"http://www.baidu.com\"));
} else {
    var w = g_window(\"test\");
    var width = 400;
    var height = 300;
    w.\"msg\"(0, width, height);
    w.\"msg\"(2, 0, 0);
}
");
""".trimIndent())

            println(codes[codes.size - 1])
            val interpreter = Interpreter()
            val grammar = Grammar(codes[codes.size - 1])
            println(grammar.toString())
            val page = grammar.codePage
            //System.out.println(page.toString());
            val baos = ByteArrayOutputStream()
            RuntimeCodePage.exportFromStream(page, baos)
            val bais = ByteArrayInputStream(baos.toByteArray())
            interpreter.run("test_1", bais)
            System.exit(0)

        } catch (e: RegexException) {
            System.err.println()
            System.err.println(e.position.toString() + "," + e.message)
            e.printStackTrace()
        } catch (e: SyntaxException) {
            System.err.println()
            System.err.println(String.format("模块名：%s. 位置：%s. 错误：%s-%s(%s:%d)",
                    e.pageName, e.position, e.message,
                    e.info, e.fileName, e.position.line + 1))
            e.printStackTrace()
        } catch (e: RuntimeException) {
            System.err.println()
            System.err.println(e.position.toString() + ": " + e.info)
            e.printStackTrace()
        } catch (e: Exception) {
            System.err.println()
            System.err.println(e.message)
            e.printStackTrace()
        }

    }
}
