public class ArrayDeque<T> {
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
    private T[] organized(int arrLength) {
        T[] a = (T[]) new Object[arrLength];
        if (nextFirst < nextLast - 1) { // 当 First 小于 Last, 即 nextFirst < nextLast - 1 时
                                        // 这种情况一般出现在执行removeFirst/Last后
            System.arraycopy(items, nextFirst + 1, a, 0, nextLast - nextFirst - 1);
        } else {
            System.arraycopy(items, nextFirst + 1, a, 0, items.length - nextFirst - 1);
            System.arraycopy(items, 0, a, items.length - nextFirst - 1, nextLast);
        }
        return a;
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
    private double getArrayUsage(double size, double length) {
        return size / length;
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
        if (size == items.length) {
            resize(2);
        }

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
        if (size == items.length) {
            resize(2);
        }

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

        if (getArrayUsage(size, items.length) < 0.5 && items.length > 16) {
            resize(0.5);
        }

        if (nextFirst == items.length - 1) {
            nextFirst = 0;
        } else {
            nextFirst++;
        }

        size--;
        return items[nextFirst];
    }

    /** 移除数组的最后一个数据, 并返回被移除的数据,
     *  若数组为空则返回 null */
    public T removeLast () {
        if (isEmpty()) {
            return null;
        }

        if (getArrayUsage(size, items.length) < 0.5 && items.length > 16) {
            resize(0.5);
        }

        if (nextLast == 0) {
            nextLast = items.length - 1;
        } else {
            nextLast--;
        }

        size--;
        return items[nextLast];
    }

    /** 根据索引返回数组的数据 */
    public T get(int index) {
        T[] a = organized(size);
        if (index >= size) {
            return null;
        }
        return a[index];
    }

    /** 按照格式输出数组  */
    public void printDeque() {
        T[] a = organized(size);

        for (T item : a) {
            System.out.print(item + " ");
        }
    }
}
