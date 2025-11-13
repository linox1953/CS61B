package synthesizer;
import java.util.Iterator;

public interface BoundedQueue<T> extends Iterable<T>{
    int capacity();     // 返回队列容量
    int fillCount();    // 返回队列中数据个数
    void enqueue(T x);  // 在队列末尾添加 x
    T dequeue();        // 删除并返回数列中最前面的数据
    T peek();           // 仅返回最前面的数据

    @Override
    Iterator<T> iterator();

    default boolean isEmpty() { // 返回队列是否为空
        return fillCount() == 0;
    }

    default boolean isFull() { // 返回队列是否已满
        return fillCount() == capacity();
    }
}
