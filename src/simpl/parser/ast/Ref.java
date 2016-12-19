package simpl.parser.ast;

import simpl.interpreter.RefValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.RefType;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Ref extends UnaryExpr {

    public Ref(Expr e) {
        super(e);
    }

    public String toString() {
        return "(ref " + e + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult typeResult = e.typecheck(E);
        return TypeResult.of(typeResult.s, new RefType(typeResult.s.apply(typeResult.t)));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        int pointer = s.p.get();
        s.p.set(pointer + 1);
        Value value = e.eval(s);
        s.M.put(pointer, value);
        return new RefValue(pointer);
    }
}
