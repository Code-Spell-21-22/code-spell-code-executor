package pt.ua.deti.codespell.syntax_tree.scanners;

import com.sun.source.tree.BlockTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;
import com.sun.source.util.TreePathScanner;
import com.sun.source.util.Trees;
import lombok.Getter;
import lombok.ToString;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
public class MethodScanner extends TreePathScanner<Object, Trees> {

    private int methodsCounter;
    private final List<String> methodsNames;
    private final List<List<String>> methodsParametersNames;
    private final List<List<String>> methodsParametersTypes;
    private final List<List<Modifier>> methodsModifiers;
    private final List<Tree> methodsReturnTypes;
    private final List<BlockTree> methodsBody;

    public MethodScanner() {
        this.methodsNames = new ArrayList<>();
        this.methodsParametersNames = new ArrayList<>();
        this.methodsParametersTypes = new ArrayList<>();
        this.methodsModifiers = new ArrayList<>();
        this.methodsReturnTypes = new ArrayList<>();
        this.methodsBody = new ArrayList<>();
    }

    @Override
    public Object visitMethod(final MethodTree tree, Trees trees) {

        ++methodsCounter;
        methodsNames.add(tree.getName().toString());

        methodsParametersNames.add(tree.getParameters()
                .stream()
                .map(variableTree -> variableTree.getName().toString())
                .collect(Collectors.toList())
        );

        methodsParametersTypes.add(tree.getParameters()
                .stream()
                .map(variableTree -> variableTree.getType().toString())
                .collect(Collectors.toList())
        );

        methodsModifiers.add(new ArrayList<>(tree.getModifiers().getFlags()));
        methodsReturnTypes.add(tree.getReturnType());
        methodsBody.add(tree.getBody());

        return super.visitMethod(tree, trees);

    }

}