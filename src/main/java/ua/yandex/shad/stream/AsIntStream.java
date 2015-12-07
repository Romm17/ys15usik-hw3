package ua.yandex.shad.stream;

import ua.yandex.shad.collections.MyArrayList;
import ua.yandex.shad.function.IntUnaryOperator;
import ua.yandex.shad.function.IntToIntStreamFunction;
import ua.yandex.shad.function.IntPredicate;
import ua.yandex.shad.function.IntConsumer;
import ua.yandex.shad.function.IntBinaryOperator;

import java.util.LinkedList;

public class AsIntStream implements IntStream {

    private MyArrayList list;
    private AsIntStream result;

    private LinkedList<Object> functions;

    private AsIntStream() {
        list = new MyArrayList();
        functions = new LinkedList<>();
    }

    private AsIntStream(int ... values) {
        list = new MyArrayList();
        for (int i : values) {
            list.add(i);
        }
        functions = new LinkedList<>();
    }

    public static IntStream of(int... values) {
        return new AsIntStream(values);
    }

    private void add(int i) {
        list.add(i);
    }

    @Override
    public Double average() {
        clearStack();
        if (result.list.isEmpty()) {
            return null;
        }
        double sum = 0.0;
        for (int i : result.list) {
            sum += i;
        }
        return sum / result.list.size();
    }

    @Override
    public Integer max() {
        clearStack();
        if (result.list.isEmpty()) {
            return null;
        }
        int max = Integer.MIN_VALUE;
        for (int i : result.list) {
            if (i > max) {
                max = i;
            }
        }
        return max;
    }

    @Override
    public Integer min() {
        clearStack();
        if (result.list.isEmpty()) {
            return null;
        }
        int min = Integer.MAX_VALUE;
        for (int i : result.list) {
            if (i < min) {
                min = i;
            }
        }
        return min;
    }

    @Override
    public long count() {
        clearStack();
        return result.list.size();
    }

    private void filter(IntPredicate predicate, int notused) {
        AsIntStream res = new AsIntStream();
        for (int i : result.list) {
            if (predicate.test(i)) {
                res.add(i);
            }
        }
        result = res;
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        functions.add(predicate);
        return this;
    }

    @Override
    public void forEach(IntConsumer action) {
        clearStack();
        for (int i : result.list) {
            action.accept(i);
        }
    }

    public void map(IntUnaryOperator mapper, int notused) {
        for (int i = 0; i < result.list.size(); i++) {
            result.list.set(i, mapper.apply(result.list.get(i)));
        }
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        functions.add(mapper);
        return this;
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        clearStack();
        int res = identity;
        for (int i : result.list) {
            res = op.apply(res, i);
        }
        return res;
    }

    @Override
    public Integer sum() {
        clearStack();
        if (result.list.isEmpty()) {
            return null;
        }
        int sum = 0;
        for (int i : result.list) {
            sum += i;
        }
        return sum;
    }

    @Override
    public int[] toArray() {
        clearStack();
        return result.list.toArray();
    }

    public void flatMap(IntToIntStreamFunction func, int notused) {
        AsIntStream res = new AsIntStream();
        for (int i : result.list) {
            int[] result = func.applyAsIntStream(i).toArray();
            for (int j : result) {
                res.add(j);
            }
        }
        result = res;
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {
        functions.add(func);
        return this;
    }

    private void clearStack() {
        result = new AsIntStream(this.list.toArray());
        System.out.println("Clearing stack: ");
        for (int i : result.list) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (Object o : functions) {
            if (o instanceof IntPredicate) {
                filter((IntPredicate)o, 0);
            }
            else if (o instanceof IntUnaryOperator) {
                map((IntUnaryOperator)o, 0);
            }
            else {
                flatMap((IntToIntStreamFunction)o, 0);
            }
        }
        for (int i : result.list) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

}
