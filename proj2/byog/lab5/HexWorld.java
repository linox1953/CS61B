package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    /** 绘画指定边长(sLength)的六边形世界, 世界由 19 个小六边形组成, 每一列分别由 3, 4, 5, 4, 3 个小六边形组成,
     *  如 <a href="https://sp18.datastructur.es/materials/lab/lab5/img/exampleWorld.png">示例图像</a> 所示;
     *  通过调用 addRandomHexWorld 生成世界, ter.renderFrame 绘画世界 */
    public static void drawHexWorld(int sLength) {
        int width = sLength * 5 + 6 * (sLength - 1); // 获取世界的宽度, 原理: 找规律
        int height = sLength * 2 * 5; // sLength * 2 是每个小六边形的高度, 每一列最多有 5 个小六边形, * 5 就是最大高度即世界高度

        TETile[][] world = new TETile[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        addRandomHexWorld(world, sLength);

        TERenderer ter = new TERenderer();
        ter.initialize(width, height);
        ter.renderFrame(world);
    }

    /** 在传入的 world[][] 里生成随机 HexWorld, 调用 getStartedPosArr 获得 19 个小六边形的初始坐标(左下角);
     *  将 Tileset 中的五种地形列成数组, 再用 Random 对象的 nextInt() 方法生成随机数组下标;
     *  最后根据每个小六边形的坐标和随机的 style 循环传入 addHex 即可生成 HexWorld */
    public static void addRandomHexWorld(TETile[][] world, int sLength) {
        int[][] posArr = getStartedPosArr(sLength);
        TETile[] styles = {Tileset.FLOWER, Tileset.GRASS, Tileset.SAND, Tileset.MOUNTAIN, Tileset.TREE};

        Random randomIndex = new Random();
        for (int i = 0; i < 19; i++) {
            int index = randomIndex.nextInt(styles.length);
            addHex(world, posArr[i][0], posArr[i][1], sLength, styles[index]);
        }
    }

    /** 返回包含 19 个小六边形初始坐标的二维数组;
     *  通过嵌套循环一列一列地添加坐标 */
    private static int[][] getStartedPosArr(int sLength) {
        int[][] pos = new int[19][2];
        int xPos = 0;
        int yPos;
        int index = 0; // index 记录的是数组下标
        int columns;

        for (int i = 0; i < 5; i++) {
            columns = getColumnNums(i + 1); // columns 为行数(有几行)
            yPos = sLength * (5 - columns); // yPos 指在此列内 y 的初始坐标, 与六边形边长和行数相关
            for (int j = 0; j < columns; j++) {
                pos[index][0] = xPos;
                pos[index][1] = yPos;
                yPos += sLength * 2; // yPos 每次加小六边形的高度
                index++;
            }
            xPos += sLength * 2 - 1; // xPos 每次加六边形宽度 3 * sLength - 2, 再减去重合部分 sLength - 1, 即为左式(看图找规律)
        }
        return pos;
    }

    /** 通过列坐标 rNum(从 1 开始), 返回这一列的行总数 */
    private static int getColumnNums(int rNum) {
        if (rNum > 3) {
            rNum = 6 - rNum; // 若列坐标在 4 及以上, 则根据对称原理将 rNum 设置为相对应的小于 4 的列坐标
    }                        // 其实对于只有 5 种情况, 使用 switch 也可以接受
        return 2 + rNum;
    }

    /** 通过给定的坐标, 边长和样式在 world 中添加小六边形 */
    public static void addHex(TETile[][] world, int xPos, int yPos, int sLength, TETile style) {
        Random r = new Random();
        for (int i = 1; i <= 2 * sLength; i++) {
            int xIndex = xPos + getIndentedX(sLength, i);
            for (int j = 1; j <= getRowLength(sLength, i); j++) {
                world[xIndex][yPos] = TETile.colorVariant(style, 32, 32, 32, r);
                xIndex += 1;
            }
            yPos += 1;
        }
    }

    /** 根据边长和行坐标 rNum(从 1 开始), 返回缩进后的 x 坐标 */
    private static int getIndentedX(int sLength, int rNum) {
        if (rNum > sLength) {
            return rNum - sLength - 1;
        }
        return sLength - rNum;
    }

    /** 根据边长和行坐标 rNum(从 1 开始), 返回这一行的长度 */
    private static int getRowLength(int sLength, int rNum) {
        if (rNum > sLength) {
            rNum = 2 * sLength - rNum + 1;
        }
        return sLength + (rNum - 1) * 2;
    }

    @Test
    public void testGetIndentedX() {
        assertEquals(1, getIndentedX(3, 2));
        assertEquals(2, getIndentedX(3, 6));
        assertEquals(4, getIndentedX(5, 10));
        assertEquals(0, getIndentedX(2, 2));
        assertEquals(2, getIndentedX(4, 7));
    }

    @Test
    public void testGetRowLength() {
        assertEquals(13, getRowLength(5, 6));
        assertEquals(7, getRowLength(5, 9));
        assertEquals(8, getRowLength(4, 6));
        assertEquals(4, getRowLength(2, 2));
        assertEquals(5, getRowLength(3, 2));
    }

    @Test
    public void testGetColumnNums() {
        assertEquals(3, getColumnNums(1));
        assertEquals(4, getColumnNums(2));
        assertEquals(5, getColumnNums(3));
        assertEquals(4, getColumnNums(4));
        assertEquals(3, getColumnNums(5));
    }

    public static void main(String[] args) {
        HexWorld.drawHexWorld(4);
    }
}
