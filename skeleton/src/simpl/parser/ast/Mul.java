package simpl.parser.ast;

import simpl.interpreter.IntValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;

public class Mul extends ArithExpr {

    public Mul(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " * " + r + ")";
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        IntValue value1 = (IntValue) l.eval(s);
        IntValue value2 = (IntValue) r.eval(s);

        if (Integer.MAX_VALUE / Math.abs(value1.n) < Math.abs(value2.n))
            throw new RuntimeError("Integer Overflow");
        int result = value1.n * value2.n;
        return new IntValue(result);
    }
}
