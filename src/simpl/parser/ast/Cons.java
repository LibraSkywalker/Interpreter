package simpl.parser.ast;

import simpl.interpreter.ConsValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.ListType;
import simpl.typing.Substitution;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Cons extends BinaryExpr {

    public Cons(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " :: " + r + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult leftResult = l.typecheck(E);
        TypeResult rightResult = r.typecheck(leftResult.s.compose(E));

        Substitution compoundSubstitution = rightResult.s.compose(leftResult.s);
        ListType listType = new ListType(compoundSubstitution.apply(leftResult.t));
        compoundSubstitution = rightResult.t.unify(listType).compose(compoundSubstitution);
        return TypeResult.of(compoundSubstitution, compoundSubstitution.apply(listType));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value value1 = l.eval(s);
        Value value2 = r.eval(s);
        return new ConsValue(value1, value2);
    }
}
