package simpl.parser.ast;

import simpl.interpreter.BoolValue;
import simpl.interpreter.IntValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;

public class GreaterEq extends RelExpr {

    public GreaterEq(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " >= " + r + ")";
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        IntValue value1 = (IntValue) l.eval(s);
        IntValue value2 = (IntValue) r.eval(s);
        return new BoolValue(value1.n >= value2.n);
    }
}
