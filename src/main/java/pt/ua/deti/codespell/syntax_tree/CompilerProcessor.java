package pt.ua.deti.codespell.syntax_tree;

import com.sun.source.util.TreePathScanner;
import com.sun.source.util.Trees;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

@SupportedSourceVersion( SourceVersion.RELEASE_11 )
@SupportedAnnotationTypes( "*" )
public class CompilerProcessor extends AbstractProcessor {

    private final TreePathScanner<Object, Trees>[] scanners;
    private Trees trees;
     
    @SafeVarargs
    public CompilerProcessor(TreePathScanner<Object, Trees>... scanners) {
        this.scanners = scanners;
    }
     
    @Override
    public synchronized void init(final ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        trees = Trees.instance(processingEnvironment);
    }
     
    public boolean process( final Set< ? extends TypeElement> types, final RoundEnvironment environment ) {
 
        if (!environment.processingOver()) {
            for (Element element : environment.getRootElements()) {
                for (TreePathScanner<Object, Trees> scanner : scanners) {
                    scanner.scan(trees.getPath(element), trees);
                }
            }
        }
         
        return true;

    }

}