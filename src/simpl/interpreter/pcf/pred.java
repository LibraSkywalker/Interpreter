package simpl.interpreter.pcf;

import simpl.interpreter.Env;
import simpl.interpreter.FunValue;
import simpl.interpreter.IntValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.parser.ast.Expr;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class pred extends FunValue {

    public pred() {
        super(Env.empty,Symbol.symbol("x"),new Expr(){

            @Override
            public TypeResult typecheck(TypeEnv E) throws TypeError {
                return null;
            }

            @Override
            public Value eval(State s) throws RuntimeError {
                IntValue intValue = (IntValue)(s.E.get(Symbol.symbol("x")));
                int now = intValue.n - 1;
                return new IntValue((now > 0) ? now : 0);
            }

        });
    }
}
