package simpl.parser.ast;

import simpl.interpreter.FunValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.typing.ArrowType;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;

public class Fn extends Expr {

    public Symbol x;
    public Expr e;

    public Fn(Symbol x, Expr e) {
        this.x = x;
        this.e = e;
    }

    public String toString() {
        return "(fn " + x + "." + e + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        Type parameterType = new TypeVar(false);
        TypeResult typeResult = e.typecheck(TypeEnv.of(E,x,parameterType));
        parameterType = typeResult.s.apply(parameterType);
        Type resultType = typeResult.s.apply(typeResult.t);
        return TypeResult.of(typeResult.s,new ArrowType(parameterType,resultType));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        return new FunValue(s.E,x,e);
    }
}
