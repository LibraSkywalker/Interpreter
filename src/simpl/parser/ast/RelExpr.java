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

        Substitution compoundSubstitution = leftResult.s.compose(rightResult.s);
        compoundSubstitution.apply(leftType);
        compoundSubstitution.apply(rightType);

        Substitution sub1 = leftType.unify(Type.INT);
        Substitution sub2 = rightType.unify(Type.INT);

        compoundSubstitution = sub1.compose(sub2.compose(compoundSubstitution));

        compoundSubstitution.apply(leftType);
        compoundSubstitution.apply(rightType);

        return TypeResult.of(compoundSubstitution,Type.INT);
    }
}
