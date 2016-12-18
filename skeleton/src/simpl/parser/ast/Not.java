package simpl.parser.ast;

import simpl.interpreter.*;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Not extends UnaryExpr {

    public Not(Expr e) {
        super(e);
    }

    public String toString() {
        return "(not " + e + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult childResult = e.typecheck(E);
        if ( childResult.t.equals(Type.BOOL)){
            return TypeResult.of(childResult.s,Type.BOOL);
        }
        else throw new TypeError("There should be Bool after Not");
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        return new BoolValue(!((BoolValue)e.eval(s)).b);
    }
}
