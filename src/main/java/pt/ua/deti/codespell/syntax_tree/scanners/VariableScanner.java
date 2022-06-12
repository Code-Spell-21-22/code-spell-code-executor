package pt.ua.deti.codespell.syntax_tree.scanners;

import com.sun.source.tree.VariableTree;
import com.sun.source.util.TreePathScanner;
import com.sun.source.util.Trees;
import lombok.Getter;
import pt.ua.deti.codespell.syntax_tree.scanners.utils.Variable;

import java.util.ArrayList;
import java.util.List;

@Getter
public class VariableScanner extends TreePathScanner<Object, Trees> {

    private final List<Variable> variables;

    public VariableScanner() {
        this.variables = new ArrayList<>();
    }

    @Override
    public Object visitVariable(final VariableTree tree, Trees trees) {

        variables.add(new Variable(tree.getName().toString(), tree.getType().toString(), tree.getModifiers().getFlags(),
                tree.getInitializer() != null ? tree.getInitializer().toString() : null));

        return super.visitVariable(tree, trees);
    }


}
