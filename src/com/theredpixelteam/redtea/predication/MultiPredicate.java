package com.theredpixelteam.redtea.predication;

import java.util.*;

public class MultiPredicate<T, H> {
    public static <T, H> Builder<T, H> builder()
    {
        return new Builder<>();
    }

    MultiPredicate(Map<NamedPredicate<T, H>, Integer> shift,
                   Map<H, Integer> handleMapped,
                   List<NamedPredicate<T, H>> shiftToPredicate)
    {
        this.shift = Collections.unmodifiableMap(shift);
        this.handleMappedShift = Collections.unmodifiableMap(handleMapped);
        this.shiftToPredicate = Collections.unmodifiableList(shiftToPredicate);
    }

    public Current test(T t)
    {
        BitSet result = new BitSet();

        for(Map.Entry<NamedPredicate<T, H>, Integer> entry : shift.entrySet())
            if(entry.getKey().test(t))
                result.set(entry.getValue());

        return new Current(result);
    }

    public Current test(T t, H... handles)
    {
        BitSet result = new BitSet();

        Integer shift;
        for(H handle : handles)
            if((shift = handleMappedShift.get(handle)) != null)
                if(shiftToPredicate.get(shift).test(t))
                    result.set(shift);

        return new Current(result);
    }

    private final List<NamedPredicate<T, H>> shiftToPredicate;

    private final Map<NamedPredicate<T, H>, Integer> shift;

    private final Map<H, Integer> handleMappedShift;

    public static class Builder<T, H>
    {
        Builder()
        {
        }

        public Builder<T, H> next(NamedPredicate<T, H> predicate)
        {
            int i = index++;

            shift.put(predicate, i);
            handleMappedShift.put(predicate.getHandle(), i);
            shiftToPredicate.add(predicate);

            return this;
        }

        public MultiPredicate<T, H> build()
        {
            return new MultiPredicate<>(shift, handleMappedShift, shiftToPredicate);
        }

        int index;

        private final List<NamedPredicate<T, H>> shiftToPredicate = new ArrayList<>();

        private final Map<NamedPredicate<T, H>, Integer> shift = new HashMap<>();

        private final Map<H, Integer> handleMappedShift = new HashMap<>();
    }

    public class Current
    {
        Current(BitSet result)
        {
            this.result = result;
            this.trueCount = result.cardinality();
            this.falseCount = result.size() - result.cardinality();
        }

        public int trueCount()
        {
            return this.trueCount;
        }

        public int falseCount()
        {
            return this.falseCount;
        }

        private BitSet bitsOf(H[] handles)
        {
            BitSet bits = new BitSet();

            Integer shift;
            for(H handle : handles)
                if((shift = handleMappedShift.get(handle)) != null)
                    bits.set(shift);

            return bits;
        }

        public boolean completelyOnlyIf(H... handles)
        {
            if(handles.length == 0)
                if(trueCount != 0)
                    return false;

            BitSet bits = bitsOf(handles);

            if(bits.cardinality() != trueCount)
                return false;

            BitSet result = (BitSet) this.result.clone();
            result.xor(bits);

            return result.cardinality() == 0;
        }

        public boolean partlyOnlyIf(H... handles)
        {
            if(handles.length == 0)
                if(trueCount != 0)
                    return false;

            BitSet bits = bitsOf(handles);

            if(bits.cardinality() < trueCount)
                return false;

            BitSet result = (BitSet) this.result.clone();

            bits.flip(0, bits.size());

            if(result.size() > bits.size())
                bits.set(bits.size(), result.size(), true);

            result.and(bits);

            return result.cardinality() == 0;
        }

        public boolean completelyIf(H... handles)
        {
            if(handles.length == 0)
                if(trueCount != 0)
                    return true;

            BitSet bits = bitsOf(handles);

            if(bits.cardinality() != trueCount)
                return false;

            BitSet result = (BitSet) this.result.clone();

            result.and(bits);

            return result.cardinality() == bits.cardinality();
        }

        public boolean partlyIf(H... handles)
        {
            if(handles.length == 0)
                if(trueCount != 0)
                    return true;

            BitSet bits = bitsOf(handles);
            BitSet result = (BitSet) this.result.clone();

            result.and(bits);

            return result.cardinality() != 0;
        }

        public MultiPredicate<T, H> getPredicate()
        {
            return MultiPredicate.this;
        }

        private final int trueCount;

        private final int falseCount;

        private final BitSet result;
    }
}
