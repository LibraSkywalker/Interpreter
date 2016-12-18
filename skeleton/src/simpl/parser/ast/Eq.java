package simpl.parser.ast;

import simpl.interpreter.*;

public class Eq extends EqExpr {

    public Eq(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " = " + r + ")";
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value value1 = l.eval(s);
        Value value2 = r.eval(s);
        return new BoolValue(value1.equals(value2));
    }
}
