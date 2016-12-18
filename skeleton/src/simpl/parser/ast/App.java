package simpl.parser.ast;

import simpl.interpreter.*;
import simpl.parser.Symbol;
import simpl.typing.ArrowType;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;

public class App extends BinaryExpr {

    public App(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " " + r + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult leftResult = l.typecheck(E);
        TypeResult rightResult = r.typecheck(E);
        Type leftType = leftResult.t;
        Type rightType = rightResult.t;
        Substitution compoundSubstitution = leftResult.s.compose(rightResult.s);
        leftType = compoundSubstitution.apply(leftType);
        rightType = compoundSubstitution.apply(rightType);

        Type thisType;
        Substitution newSubstitution;
        if (leftType instanceof ArrowType){
            newSubstitution = ((ArrowType) leftType).t1.unify(rightType);
            newSubstitution.compose(compoundSubstitution);
            thisType = ((ArrowType) leftType).t2;
        } else if (leftType instanceof TypeVar){
            TypeVar typeVar = new TypeVar(false);
            newSubstitution = leftType.unify(new ArrowType(rightType,typeVar));
            newSubstitution.compose(compoundSubstitution);
            thisType = compoundSubstitution.apply(typeVar);
        } else {
            throw new TypeError("left expression isn't a function, application failed.");
        }
        return TypeResult.of(compoundSubstitution,thisType);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        FunValue funValue = (FunValue) l.eval(s);
        State newState = s;
        //if (funValue.e..get(funValue.x) != null){ //pseudo Lazy evaluation
            Value parameter = r.eval(s);
            newState = State.of(new Env(funValue.E, funValue.x, parameter), s.M, s.p);
       // }
        return funValue.e.eval(newState);

    }
}
