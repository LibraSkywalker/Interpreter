package simpl.parser.ast;

import simpl.interpreter.BoolValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.*;

public class Cond extends Expr {

    public Expr e1, e2, e3;

    public Cond(Expr e1, Expr e2, Expr e3) {
        this.e1 = e1;
        this.e2 = e2;
        this.e3 = e3;
    }

    public String toString() {
        return "(if " + e1 + " then " + e2 + " else " + e3 + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult typeResult1 = e1.typecheck(E);
        TypeResult typeResult2 = e2.typecheck(E);
        TypeResult typeResult3 = e3.typecheck(E);

        Type type1 = typeResult1.t;
        Type type2 = typeResult2.t;
        Type type3 = typeResult3.t;

        Substitution compoundSubstitution = typeResult1.s.compose(typeResult2.s).compose(typeResult3.s);

        type1 = compoundSubstitution.apply(type1);
        type2 = compoundSubstitution.apply(type2);
        type3 = compoundSubstitution.apply(type3);

        compoundSubstitution = type1.unify(Type.BOOL).compose(compoundSubstitution);

        type2 = compoundSubstitution.apply(type2);
        type3 = compoundSubstitution.apply(type3);

        Type resultType = new TypeVar(false);

        compoundSubstitution = type2.unify(resultType).compose(compoundSubstitution);

        type3 = compoundSubstitution.apply(type3);

        compoundSubstitution = type3.unify(resultType).compose(compoundSubstitution);

        compoundSubstitution.apply(resultType);

        return TypeResult.of(compoundSubstitution,resultType);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        BoolValue value1 = (BoolValue) e1.eval(s);
        if (value1.b){
            return e2.eval(s);
        } else {
            return e3.eval(s);
        }
    }
}
