package simpl.parser.ast;

import simpl.interpreter.RefValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.RefType;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;

public class Deref extends UnaryExpr {

    public Deref(Expr e) {
        super(e);
    }

    public String toString() {
        return "!" + e;
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult typeResult1 = e.typecheck(E);
        RefType refType = new RefType(new TypeVar(true));
        Substitution resultSubstitution = typeResult1.t.unify(refType);
        resultSubstitution = resultSubstitution.compose(typeResult1.s);
        return TypeResult.of(resultSubstitution, resultSubstitution.apply(refType.t));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        RefValue value = (RefValue) e.eval(s);
        return s.M.get(value.p);
    }
}
