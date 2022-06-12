package pt.ua.deti.codespell.syntax_tree.scanners;

import com.sun.source.tree.IfTree;
import com.sun.source.util.TreePathScanner;
import com.sun.source.util.Trees;
import lombok.Getter;
import pt.ua.deti.codespell.syntax_tree.scanners.utils.IfStatement;

import java.util.ArrayList;
import java.util.List;

@Getter
public class IfsScanner extends TreePathScanner<Object, Trees> {

    private final List<IfStatement> ifStatements;

    public IfsScanner() {
        this.ifStatements = new ArrayList<>();
    }

    @Override
    public Object visitIf(final IfTree tree, Trees trees) {
        ifStatements.add(IfStatement.creatNewInstance(tree.getCondition(), tree.getThenStatement(), tree.getElseStatement()));
        return super.visitIf(tree, trees);

    }

}
