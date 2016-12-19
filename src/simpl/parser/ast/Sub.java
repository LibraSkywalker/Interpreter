package simpl.parser.ast;

import simpl.interpreter.IntValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;

public class Sub extends ArithExpr {

    public Sub(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " - " + r + ")";
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        IntValue value1 = (IntValue) l.eval(s);
        IntValue value2 = (IntValue) r.eval(s);

        if (value1.n < 0){
            if (Integer.MIN_VALUE - value1.n > -value2.n)
                throw new RuntimeError("Integer Overflow");
        } else {
            if (Integer.MAX_VALUE - value1.n < -value2.n)
                throw new RuntimeError("Integer Overflow");
        }
        int result = value1.n - value2.n;
        return new IntValue(result);
    }
}
