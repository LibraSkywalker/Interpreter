package simpl.typing;

import simpl.parser.Symbol;

public class TypeVar extends Type {

    private static int tvcnt = 0;

    private boolean equalityType;
    private Symbol name;

    public TypeVar(boolean equalityType) {
        this.equalityType = equalityType;
        name = Symbol.symbol("tv" + ++tvcnt);
    }

    @Override
    public boolean isEqualityType() {
        return equalityType;
    }

    @Override
    public Substitution unify(Type t) throws TypeCircularityError {
        if (t.contains(this)){
            if (t instanceof TypeVar) return Substitution.IDENTITY;
            throw new TypeCircularityError();
        }
        return Substitution.of(this,t);
    }

    public String toString() {
        return "" + name;
    }

    @Override
    public boolean contains(TypeVar tv) {
        return name.equals(tv.name);
    }

    @Override
    public Type replace(TypeVar a, Type t) {
        if (name.equals(a.name)) return t;
        return this;
    }
}
