package simpl.parser.ast;

import simpl.typing.ListType;
import simpl.typing.PairType;
import simpl.typing.RefType;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;

public abstract class EqExpr extends BinaryExpr {

    public EqExpr(Expr l, Expr r) {
        super(l, r);
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult leftResult = l.typecheck(E);
        TypeResult rightResult = r.typecheck(E);

        Substitution compoundSubstitution = leftResult.s.compose(rightResult.s);

        Type type1 = compoundSubstitution.apply(leftResult.t);
        Type type2 = compoundSubstitution.apply(rightResult.t);

        compoundSubstitution.compose(type1.unify(type2));
        return TypeResult.of(compoundSubstitution,Type.BOOL);
    }
}
