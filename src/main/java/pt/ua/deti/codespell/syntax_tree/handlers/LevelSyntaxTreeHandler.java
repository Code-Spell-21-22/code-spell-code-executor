package pt.ua.deti.codespell.syntax_tree.handlers;

import com.sun.source.util.TreePathScanner;
import com.sun.source.util.Trees;
import pt.ua.deti.codespell.syntax_tree.CompilerProcessor;

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class LevelSyntaxTreeHandler {

    public static File getLevelClassFile(int chapterNumber, int levelNumber) {
        return new File(String.format("/code-spell-code-executor/src/main/java/pt/ua/deti/codespell/chapters/chapter_%d/Level_%d.java", chapterNumber, levelNumber));
    }

    @SafeVarargs
    public static <T extends TreePathScanner<Object, Trees>> T[] scanFile(File fileToAnalyze, T... scanners) {

        final JavaCompiler compiler =  ToolProvider.getSystemJavaCompiler();
        final DiagnosticCollector<JavaFileObject> diagnosticCollector = new DiagnosticCollector<>();

        final CompilerProcessor compilerProcessor = new CompilerProcessor(scanners);

        try (final StandardJavaFileManager manager = compiler.getStandardFileManager(diagnosticCollector, null, null ) ) {

            final Iterable<? extends JavaFileObject> sources = manager.getJavaFileObjectsFromFiles(Collections.singletonList(fileToAnalyze));

            JavaCompiler.CompilationTask task = compiler.getTask( null, manager, diagnosticCollector, null, null, sources );
            task.setProcessors(List.of(compilerProcessor));
            task.call();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return scanners;

    }

}
