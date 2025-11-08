public class LinkedListDeque<T> implements Deque<T> {
    /* 创建内部类是为了更好的创建和使用 sentinel 节点,
     * sentinel 节点可以大幅简化程序逻辑. 程序员只需关注对 sentinel 的调用和改写,
     * 而不必关注链表的具体数据和实现细节 */
    private class StuffNode {
        StuffNode prev;
        T item;
        StuffNode next;
        public StuffNode(StuffNode p, T t, StuffNode n) {
            prev = p;
            item = t;
            next = n;
        }
    }

    private int size;
    private StuffNode sentinel;

    /* 创建空链表时,让 sentinel 的 prev 和 next 都指向自身,
     * 利用引用类型的特性来完成这一点 */
    public LinkedListDeque() {
        sentinel = new StuffNode(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    /** 插入首个数据 */
    @Override
    public void addFirst(T item) {
        StuffNode fstItem = new StuffNode(sentinel, item, sentinel.next); // 创建第一个节点
        sentinel.next.prev = fstItem; // 更新节点配置
        sentinel.next = fstItem;
        size++;
    }

    /** 添加末尾数据 */
    @Override
    public void addLast(T item) {
        StuffNode lstItem = new StuffNode(sentinel.prev, item, sentinel); // 创建最末尾的节点
        sentinel.prev.next = lstItem;
        sentinel.prev = lstItem;
        size++;
    }

    /** 移除首个数据 */
    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        T item = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size--;

        return item;
    }

    /** 移除末尾数据 */
    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        T item = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size--;

        return item;
    }

    /** 返回链表是否为空,
     *  若为空返回 true, 若不为空返回 false */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /** 返回链表大小(数据个数) */
    @Override
    public int size() {
        return size;
    }

    /** 格式化打印链表数据 */
    @Override
    public void printDeque() {
        StuffNode node = sentinel;
        while (node.next != sentinel) {
            node = node.next;
            System.out.print(node.item + " ");
        }
    }

    /** 根据索引返回对应位置的数据 */
    @Override
    public T get(int index) {
        StuffNode node = sentinel.next;
        while (node != sentinel) {
            if (index == 0) {
                return node.item;
            }
            node = node.next;
            index--;
        }
        return null;
    }

    private T getRecursiveHelper(StuffNode node, int index) {
        // 到达哨兵节点或链表末尾
        if (node == sentinel) {
            return null;
        }
        // 找到目标索引
        if (index == 0) {
            return node.item;
        }
        // 递归向后查找
        return getRecursiveHelper(node.next, index - 1);
    }

    /** get()方法的递归版本,需要通过 helper 函数辅助完成 */
    public T getRecursive(int index) {
        return getRecursiveHelper(sentinel.next, index);
    }
}
