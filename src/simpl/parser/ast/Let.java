package simpl.parser.ast;

import simpl.interpreter.Env;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.typing.Substitution;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Let extends Expr {

    public Symbol x;
    public Expr e1, e2;

    public Let(Symbol x, Expr e1, Expr e2) {
        this.x = x;
        this.e1 = e1;
        this.e2 = e2;
    }

    public String toString() {
        return "(let " + x + " = " + e1 + " in " + e2 + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult typeResult1 = e1.typecheck(E);
        TypeResult typeResult2 = e2.typecheck(typeResult1.s.compose(TypeEnv.of(E, x, typeResult1.t)));
        Substitution compoundSubstitution = typeResult2.s.compose(typeResult1.s);
        return TypeResult.of(compoundSubstitution, compoundSubstitution.apply(typeResult2.t));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value value1 = e1.eval(s);
        Env env = new Env(s.E, x, value1);
        return e2.eval(State.of(env, s.M, s.p));
    }
}
