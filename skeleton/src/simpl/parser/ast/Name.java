package simpl.parser.ast;

import simpl.interpreter.RecValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Name extends Expr {

    public Symbol x;

    public Name(Symbol x) {
        this.x = x;
    }

    public String toString() {
        return "" + x;
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        Type type = E.get(x);
        if (type == null) throw new TypeError("name not defined");
        return TypeResult.of(type);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value value = s.E.get(x);
        if (value instanceof RecValue){
            Rec rec = new Rec(((RecValue) value).x,((RecValue) value).e);
            return rec.eval(State.of(((RecValue) value).E,s.M,s.p));
        }
        else return value;
    }
}
