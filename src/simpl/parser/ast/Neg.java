package simpl.parser.ast;

import simpl.interpreter.IntValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Neg extends UnaryExpr {

    public Neg(Expr e) {
        super(e);
    }

    public String toString() {
        return "~" + e;
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult childResult = e.typecheck(E);
        if ( childResult.t.equals(Type.INT)){
            return TypeResult.of(childResult.s,Type.INT);
        }
        else throw new TypeError("There should be Int after Neg");
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        return new IntValue(-((IntValue)e.eval(s)).n);
    }
}
