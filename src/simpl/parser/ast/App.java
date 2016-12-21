package simpl.parser.ast;

import simpl.interpreter.*;
import simpl.interpreter.lib.fst;
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

        Type returnType = null;

        Substitution compoundSubstitution = leftResult.s.compose(rightResult.s);

        Type FunctionType = compoundSubstitution.apply(leftResult.t);
        Type parameterType = compoundSubstitution.apply(rightResult.t);

        if (FunctionType instanceof ArrowType){
            compoundSubstitution = ((ArrowType) FunctionType).t1.unify(parameterType);
            FunctionType = compoundSubstitution.apply(FunctionType);
            returnType = ((ArrowType) FunctionType).t2;
        } else if (FunctionType instanceof TypeVar){
            returnType = new TypeVar(false);
            compoundSubstitution = FunctionType.unify(new ArrowType(parameterType,returnType)).compose(compoundSubstitution);
            returnType = compoundSubstitution.apply(returnType);
        } else throw new TypeError("The left side is not a function");

        return TypeResult.of(compoundSubstitution, returnType);
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
