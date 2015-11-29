package ua.yandex.shad.stream;

import ua.yandex.shad.collections.MyArrayList;
import ua.yandex.shad.function.IntUnaryOperator;
import ua.yandex.shad.function.IntToIntStreamFunction;
import ua.yandex.shad.function.IntPredicate;
import ua.yandex.shad.function.IntConsumer;
import ua.yandex.shad.function.IntBinaryOperator;

public class AsIntStream implements IntStream {

    private MyArrayList list;

    private AsIntStream() {
        list = new MyArrayList();
    }

    private AsIntStream(int... values) {
        list = new MyArrayList();
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
        double sum = 0.0;
        for (int i : list) {
            sum += i;
        }
        return sum / list.size();
    }

    @Override
    public Integer max() {
        if (list.isEmpty()) {
            return null;
        }
        int max = Integer.MIN_VALUE;
        for (int i : list) {
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
        int min = Integer.MAX_VALUE;
        for (int i : list) {
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
        for (int i : list) {
            if (predicate.test(i)) {
                res.add(i);
            }
        }
        return res;
    }

    @Override
    public void forEach(IntConsumer action) {
        for (int i : list) {
            action.accept(i);
        }
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        AsIntStream res = new AsIntStream();
        for (int i : list) {
            res.add(mapper.apply(i));
        }
        return res;
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        int result = identity;
        for (int i : list) {
            result = op.apply(result, i);
        }
        return result;
    }

    @Override
    public Integer sum() {
        if (list.isEmpty()) {
            return null;
        }
        int sum = 0;
        for (int i : list) {
            sum += i;
        }
        return sum;
    }

    @Override
    public int[] toArray() {
        return list.toArray();
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {
        AsIntStream res = new AsIntStream();
        for (int i : list) {
            int[] result = func.applyAsIntStream(i).toArray();
            for (int j : result) {
                res.add(j);
            }
        }
        return res;
    }

}
