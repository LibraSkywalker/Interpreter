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

public class OrElse extends BinaryExpr {

    public OrElse(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " orelse " + r + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult leftResult = l.typecheck(E);
        TypeResult rightResult = r.typecheck(E);
        if (leftResult.t.equals(Type.BOOL) && rightResult.t.equals(Type.BOOL)){
            Substitution leftSubstitution = leftResult.s;
            Substitution rightSubstitution = rightResult.s;
            return TypeResult.of(leftSubstitution.compose(rightSubstitution),Type.BOOL);
        }
        else throw new TypeError("There should be two Bool for orElse");
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        if (l.eval(s).equals(new BoolValue(false)))
            return r.eval(s);
        return new BoolValue(true);
    }
}
