

public class Var extends Node implements Expression {
    public final String name;

    public Var(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Var) {
            return this.name.equals(((Var)obj).name);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return name;
    }
}