package simpl.parser.ast;

import simpl.interpreter.IntValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;

public class Mod extends ArithExpr {

    public Mod(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " % " + r + ")";
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        IntValue value1 = (IntValue) l.eval(s);
        IntValue value2 = (IntValue) r.eval(s);

        if (value2.n == 0) throw new RuntimeError("Attempt to divide 0");
        int result = value1.n % value2.n;
        return new IntValue(result);
    }
}
