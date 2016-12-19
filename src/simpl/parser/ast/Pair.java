package simpl.parser.ast;

import simpl.interpreter.PairValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.*;

public class Pair extends BinaryExpr {

    public Pair(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(pair " + l + " " + r + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult leftResult = l.typecheck(E);
        TypeResult rightResult = r.typecheck(leftResult.s.compose(E));
        Substitution compoundResult = rightResult.s.compose(leftResult.s);
        PairType pairType = new PairType(compoundResult.apply(leftResult.t), compoundResult.apply(rightResult.t));
        return TypeResult.of(compoundResult, pairType);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value value1 = l.eval(s);
        Value value2 = r.eval(s);
        return new PairValue(value1, value2);
    }
}
