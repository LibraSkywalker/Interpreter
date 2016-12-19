package simpl.parser.ast;

import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public abstract class RelExpr extends BinaryExpr {

    public RelExpr(Expr l, Expr r) {
        super(l, r);
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult leftResult = l.typecheck(E);
        TypeResult rightResult = r.typecheck(E);
        Type leftType = leftResult.t;
        Type rightType = rightResult.t;
        if (leftType.equals(Type.INT) && rightType.equals(Type.INT)){
            Substitution leftSubstitution = leftResult.s;
            Substitution rightSubstitution = rightResult.s;
            return TypeResult.of(leftSubstitution.compose(rightSubstitution),Type.INT);
        }
        else throw new TypeError("There should be two Int for RelExpr");
    }
}
