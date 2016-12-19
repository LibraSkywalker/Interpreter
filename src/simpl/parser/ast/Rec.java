package simpl.parser.ast;

import simpl.interpreter.Env;
import simpl.interpreter.RecValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;

public class Rec extends Expr {

    public Symbol x;
    public Expr e;

    public Rec(Symbol x, Expr e) {
        this.x = x;
        this.e = e;
    }

    public String toString() {
        return "(rec " + x + "." + e + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeVar typeVar = new TypeVar(true);
        TypeResult typeResult = e.typecheck(TypeEnv.of(E, x, typeVar));
        Substitution compoundSubstitution = typeResult.s.compose(typeResult.t.unify(typeResult.s.apply(typeVar)));
        return TypeResult.of(typeResult.s, compoundSubstitution.apply(typeResult.t));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        RecValue value = new RecValue(s.E, x, e);
        Env env = new Env(s.E, x, value);
        return e.eval(State.of(env, s.M, s.p));
    }
}
