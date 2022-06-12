package pt.ua.deti.codespell.syntax_tree.scanners.utils;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.ParenthesizedTree;
import com.sun.source.tree.StatementTree;
import com.sun.source.tree.Tree;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class IfStatement {

    private final ExpressionTree conditionExpression;
    private final StatementTree thenStatement;
    private final StatementTree elseStatement;

    @Setter
    private Tree.Kind conditionKind;

    private IfStatement(ExpressionTree condition, StatementTree thenStatement, StatementTree elseStatement) {
        this.conditionExpression = condition;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    public static IfStatement creatNewInstance(ExpressionTree condition, StatementTree thenStatement, StatementTree elseStatement) {

        IfStatement ifStatement = new IfStatement(condition, thenStatement, elseStatement);

        if (condition instanceof ParenthesizedTree && ((ParenthesizedTree) condition).getExpression() != null) {
            ifStatement.setConditionKind(((ParenthesizedTree) condition).getExpression().getKind());
        }

        return ifStatement;

    }

}
