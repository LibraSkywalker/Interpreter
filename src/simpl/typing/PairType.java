package simpl.typing;

public final class PairType extends Type {

    public Type t1, t2;

    public PairType(Type t1, Type t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    @Override
    public boolean isEqualityType() {
        return true;
    }

    @Override
    public Substitution unify(Type t) throws TypeError {
        if(t instanceof TypeVar){
            return t.unify(this);
        }if(t instanceof PairType){
            Substitution sub1 = t1.unify(((PairType) t).t1);
            Substitution sub2 = t2.unify(((PairType) t).t2);
            return sub2.compose(sub1);
        }
        throw new TypeError(t + " cannot be " + this);
    }

    @Override
    public boolean contains(TypeVar tv) {
        return t1.contains(tv) || t2.contains(tv);
    }

    @Override
    public Type replace(TypeVar a, Type t) {
        t1 = t1.replace(a,t);
        t2 = t2.replace(a,t);
        return this;
    }

    public String toString() {
        return "(" + t1 + " * " + t2 + ")";
    }
}
