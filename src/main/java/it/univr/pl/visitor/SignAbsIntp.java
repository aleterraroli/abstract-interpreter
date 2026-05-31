package it.univr.pl.visitor;

import it.univr.pl.AbsBaseVisitor;
import it.univr.pl.SignAbsMem;
import it.univr.pl.value.Value;

public class SignAbsIntp extends AbsBaseVisitor<Value> {
    private final SignAbsMem mem;

    public SignAbsIntp(SignAbsMem mem) {
        this.mem = mem;
    }


}
