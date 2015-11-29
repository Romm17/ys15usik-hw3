package ua.yandex.shad.stream;

import ua.yandex.shad.function.IntUnaryOperator;
import ua.yandex.shad.function.IntToIntStreamFunction;
import ua.yandex.shad.function.IntPredicate;
import ua.yandex.shad.function.IntConsumer;
import ua.yandex.shad.function.IntBinaryOperator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AsIntStream implements IntStream {

    private ArrayList<Integer> list;

    private AsIntStream() {
        list = new ArrayList<>();
    }

    private AsIntStream(int... values) {
        list = new ArrayList<>();
        for (int i : values) {
            list.add(i);
        }
    }

    public static IntStream of(int... values) {
        return new AsIntStream(values);
    }

    private void add(int i) {
        list.add(i);
    }

    @Override
    public Double average() {
        if (list.isEmpty()) {
            return null;
        }
        Double sum = 0.0;
        for (Integer i : list) {
            sum += i;
        }
        return sum / list.size();
    }

    @Override
    public Integer max() {
        if (list.isEmpty()) {
            return null;
        }
        Integer max = Integer.MIN_VALUE;
        for (Integer i : list) {
            if (i > max) {
                max = i;
            }
        }
        return max;
    }

    @Override
    public Integer min() {
        if (list.isEmpty()) {
            return null;
        }
        Integer min = Integer.MAX_VALUE;
        for (Integer i : list) {
            if (i < min) {
                min = i;
            }
        }
        return min;
    }

    @Override
    public long count() {
        return list.size();
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        AsIntStream res = new AsIntStream();
        for (Integer i : list) {
            if (predicate.test(i)) {
                res.add(i);
            }
        }
        return res;
    }

    @Override
    public void forEach(IntConsumer action) {
        for (Integer i : list) {
            action.accept(i);
        }
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        AsIntStream res = new AsIntStream();
        for (Integer i : list) {
            res.add(mapper.apply(i));
        }
        return res;
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        for (Integer i : list) {
            identity = op.apply(identity, i);
        }
        return identity;
    }

    @Override
    public Integer sum() {
        if (list.isEmpty()) {
            return null;
        }
        Integer sum = 0;
        for (Integer i : list) {
            sum += i;
        }
        return sum;
    }

    @Override
    public int[] toArray() {
        Object[] array = list.toArray();
        int[] res = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            res[i] = (Integer)array[i];
        }
        return res;
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {
        AsIntStream res = new AsIntStream();
        for (Integer i : list) {
            int[] result = func.applyAsIntStream(i).toArray();
            for (int j : result) {
                res.add(j);
            }
        }
        return res;
    }

}
