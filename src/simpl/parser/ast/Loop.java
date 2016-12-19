package simpl.parser.ast;

import simpl.interpreter.BoolValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Loop extends Expr {

    public Expr e1, e2;

    public Loop(Expr e1, Expr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    public String toString() {
        return "(while " + e1 + " do " + e2 + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult typeResult1 = e1.typecheck(E);
        TypeResult typeResult2 = e2.typecheck(typeResult1.s.compose(E));
        Substitution compoundSubstitution = typeResult2.s.compose(typeResult1.s);
        compoundSubstitution = compoundSubstitution.apply(typeResult1.t).unify(Type.BOOL).compose(compoundSubstitution);
        return TypeResult.of(compoundSubstitution, Type.UNIT);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value value = e1.eval(s);
        if (value.equals(new BoolValue(true))) {
            Seq sequence = new Seq(e2, this);
            return sequence.eval(s);
        }
        return Value.UNIT;
    }
}
