package simpl.typing;

import simpl.parser.Symbol;

public class DefaultTypeEnv extends TypeEnv {

    private TypeEnv E;

    public DefaultTypeEnv() {
        E = TypeEnv.empty;
        E = TypeEnv.of(E,Symbol.symbol("iszero"),new ArrowType(Type.INT,Type.BOOL));
        E = TypeEnv.of(E,Symbol.symbol("pred"),new ArrowType(Type.INT,Type.INT));
        E = TypeEnv.of(E,Symbol.symbol("succ"),new ArrowType(Type.INT,Type.INT));
        TypeVar tv1 = new TypeVar(false);
        TypeVar tv2 = new TypeVar(false);
        E = TypeEnv.of(E,Symbol.symbol("fst"),new ArrowType(new PairType(tv1,tv2),tv1));
        E = TypeEnv.of(E,Symbol.symbol("snd"),new ArrowType(new PairType(tv1,tv2),tv2));
        E = TypeEnv.of(E,Symbol.symbol("hd"),new ArrowType(new ListType(tv1),tv1));
        E = TypeEnv.of(E,Symbol.symbol("tl"),new ArrowType(new ListType(tv1),new ListType(tv1)));

        // build in functions
    }

    @Override
    public Type get(Symbol x) {
        return E.get(x);
    }
}
