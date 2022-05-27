package pt.ua.deti.codespell.syntax_tree.scanners;

import com.sun.source.tree.ClassTree;
import com.sun.source.util.TreePathScanner;
import com.sun.source.util.Trees;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ClassScanner extends TreePathScanner<Object, Trees> {

    private int classesCounter;
    private final List<String> classesNames;

    public ClassScanner() {
        this.classesNames = new ArrayList<>();
    }

    @Override
    public Object visitClass(final ClassTree tree, Trees trees) {

        ++classesCounter;
        classesNames.add(tree.getSimpleName().toString());

        return super.visitClass(tree, trees);

    }

}
