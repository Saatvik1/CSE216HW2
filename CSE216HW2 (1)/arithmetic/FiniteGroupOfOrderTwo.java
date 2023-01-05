package arithmetic;

import core.Group;

public class FiniteGroupOfOrderTwo implements Group<PlusOrMinusOne> {
    @Override
    public PlusOrMinusOne binaryOperation(PlusOrMinusOne one, PlusOrMinusOne other) {
        return new PlusOrMinusOne(Integer.parseInt(one.toString()) * Integer.parseInt(other.toString()));
    }

    @Override
    public PlusOrMinusOne identity() {
        return PlusOrMinusOne.values()[0];
    }

    @Override
    public PlusOrMinusOne inverseOf(PlusOrMinusOne plusOrMinusOne) {
        return new PlusOrMinusOne(Integer.parseInt(plusOrMinusOne.toString()) * Integer.parseInt(PlusOrMinusOne.values()[1].toString()));
    }

    @Override
    public PlusOrMinusOne exponent(PlusOrMinusOne plusOrMinusOne, int k) {
        return Group.super.exponent(plusOrMinusOne, k);
    }
}
