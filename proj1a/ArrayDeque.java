public class ArrayDeque<T> {
    private static final int INIT_CAPACITY = 8;
    private static final double SHRINK_FACTOR = 0.5;
    private static final double EXPAND_FACTOR = 2.0;
    private static final double USAGE_THRESHOLD = 0.5;

    private T[] items;
    private int nextFirst;
    private int nextLast;
    private int size;

    /** 构造器初始创造一个大小为8的数组
      * nextFirst 指向末尾, nextLast 指向开头,
      * 这样做使得在编写addFirst/Last removeFirst/Last方法时方便地多.
      * 假设我们要在数组开头添加一个数据,若不使用循环数组,
      * 则要把之后的所有数据都往后移一位,意味着要遍历数组里的所有数据(这将带来极大的性能消耗, linear time),
      * 而使用循环数组则只需要在 nextFirst 的位置加入数据即可(constant time) */
    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 7; // nextFirst 指向末尾,每次添加都减一,这样在整理数组时比较快捷
        nextLast = 0; // nextLast 指向开头,每次添加都加一
    }

    /** 按照从 first 到 last 的顺序整理数组 */
    private T[] organized(int capacity) {
        T[] newArray = (T[]) new Object[capacity];
        int start = (nextFirst + 1) % items.length; // start 指数组开始的位置,
                                                    // % 为了处理nextFirst 在末尾的情况, 此时 start 为 0

        /* start < nextLast 处理的是当数组的开始位置(start)在数组结束位置之前或相等的情况(常见于连续执行多次 remove)
         * start == nextLast && nextLast == 0 && size == capacity 处理的是数组完全填满且数组开始位置等于 nextLast 等于 0,
         * 例如当多次执行 addFirst 操作后, 数组已满且 nextFirst 回到起始位置(数组末尾, 此时 start 为 0), 就会出现这种情况 */
        if (start < nextLast || (start == nextLast && nextLast == 0 && size == items.length)) {
            System.arraycopy(items, start, newArray, 0, size);
        } else {
            int firstSegment = items.length - start; // 第一段的长度
            System.arraycopy(items, start, newArray, 0, firstSegment);
            System.arraycopy(items, 0, newArray, firstSegment, size - firstSegment);
        }
        return newArray;
    }

    /** 通过 dFactor 倍率给数组扩容/减容, 用于 add 时数组已满或 remove 时使用率小于0.5,
     *  扩容后让 nextFirst 指向数组末端, nextLast 指向元素末端 */
    private void resize(double dFactor) {
        int arrLength = (int) (items.length * dFactor);
        items = organized(arrLength);

        nextFirst = arrLength - 1;
        nextLast = size;
    }

    /** 返回数组的使用率 */
    private double getArrayUsage(double arrSize, double length) {
        return arrSize / length;
    }

    /** 满足一定条件则自动 resize, 这个方法一般放在 add 方法的开头和 remove 方法的末尾
     *  这样使 organized 函数比较简单, 无需过多判定(反复思考) */
    private void checkAutoResize() {
        if (size == items.length) {
            resize(2);
        } else if (size >= 16 && getArrayUsage(size, items.length) < 0.5) {
            resize(0.5);
        }
    }

    /** 返回数组大小(元素个数) */
    public int size() {
        return size;
    }

    /** 返回数组是否为空.
     *  若为空,返回 true;
     *  若不为空,返回 false */
    public boolean isEmpty() {
        return size() == 0;
    }

    /** 向数组的最前端添加数据 */
    public void addFirst(T item) {
        checkAutoResize();

        items[nextFirst] = item;

        if (nextFirst == 0) {
            nextFirst = items.length - 1;
        } else {
            nextFirst--;
        }

        size++;
    }

    /** 向数组最后端添加数据 */
    public void addLast(T item) {
        checkAutoResize();

        items[nextLast] = item;

        if (nextLast == items.length - 1) {
            nextLast = 0;
        } else {
            nextLast++;
        }

        size++;
    }

    /** 移除数组的第一个数据, 并返回被移除的数据,
     *  若数组为空则返回 null */
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        if (nextFirst == items.length - 1) {
            nextFirst = 0;
        } else {
            nextFirst++;
        }
        T item = items[nextFirst];

        size--;
        checkAutoResize();
        return item;
    }

    /** 移除数组的最后一个数据, 并返回被移除的数据,
     *  若数组为空则返回 null */
    public T removeLast() {

        if (isEmpty()) {
            return null;
        }

        if (nextLast == 0) {
            nextLast = items.length - 1;
        } else {
            nextLast--;
        }
        T item = items[nextLast];

        size--;
        checkAutoResize();
        return item;
    }

    /** 根据给出的下标返回数组中的实际下标,
     *  nextFirst + 1 即指数组开始的位置
     *  (nextFirst + 1 + index) % items.length 相当于索引 0 处的偏移量(即数组实际下标) */
    private int getActualIndex(int index) {
        return (nextFirst + 1 + index) % items.length;
    }

    /** 根据索引返回数组的数据 */
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        int actualIndex = getActualIndex(index);
        return items[actualIndex];
    }

    /** 按照格式输出数组  */
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            int actualIndex = getActualIndex(i);
            System.out.print(items[actualIndex] + " ");
        }
    }
}
