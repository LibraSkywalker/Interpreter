package simpl.typing;

public final class ArrowType extends Type {

    public Type t1, t2;

    public ArrowType(Type t1, Type t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    @Override
    public boolean isEqualityType() {
        return false;
    }

    @Override
    public Substitution unify(Type t) throws TypeError {
        if (t instanceof TypeVar) {
            return Substitution.of((TypeVar) t,this);
        }
        else if (t instanceof ArrowType){
            try {
                Substitution sub1 = t1.unify(((ArrowType) t).t1);
                Substitution sub2 = t2.unify(((ArrowType) t).t2);
                return sub1.compose(sub2);
            } catch (TypeError e){
                e.printStackTrace();
                throw e;
            }
        }
        else throw new TypeError(t + " cannot be" + this);
    }

    @Override
    public boolean contains(TypeVar tv) {
        return  t1.equals(tv) || t2.equals(tv);
    }

    @Override
    public Type replace(TypeVar a, Type t) {
        if (t1.equals(a)) t1 = t;
        if (t2.equals(a)) t2 = t;
        return this;
    }

    public String toString() {
        return "(" + t1 + " -> " + t2 + ")";
    }
}
