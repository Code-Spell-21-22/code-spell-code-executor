package pt.ua.deti.codespell.syntax_tree.scanners.utils;

import com.sun.source.tree.ExpressionTree;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.lang.model.element.Modifier;
import java.util.Set;

@Getter
@RequiredArgsConstructor
@ToString
public class Variable {

    private final String name;
    private final String kind;
    private final Set<Modifier> modifiers;
    private final String initializer;

}
