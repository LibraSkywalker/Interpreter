package simpl.parser.ast;

import simpl.interpreter.RefValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.*;

public class Assign extends BinaryExpr {

    public Assign(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return l + " := " + r;
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult leftResult = l.typecheck(E);
        TypeResult rightResult = r.typecheck(E);
        Type leftType = leftResult.t;
        Type rightType = rightResult.t;
        Substitution compoudSubstitution = rightResult.s.compose(leftResult.s);

        leftType = compoudSubstitution.apply(leftType);
        rightType = compoudSubstitution.apply(rightType);

        Substitution newSubstitution;
        if (leftType instanceof RefType){
            newSubstitution = rightType.unify(((RefType) leftType).t);
            compoudSubstitution = newSubstitution.compose(compoudSubstitution);
        } else {
            throw new TypeError("There should be reference on the left!");
        }

        return TypeResult.of(compoudSubstitution,Type.UNIT);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        RefValue value1 =(RefValue) l.eval(s);
        Value value2 = r.eval(s);
        s.M.put(value1.p,value2);
        return Value.UNIT;

    }
}
