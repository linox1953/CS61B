public class NBody {

    /** 读取文件中的行星半径 */
    public static double readRadius(String fileName) {
        In in = new In(fileName);
        in.readInt(); // 省略行星数量
        return in.readDouble();
    }

    /** 通过文件中的数据创建并返回行星对象数组 */
    public static Planet[] readPlanets(String fileName) {
        In in = new In(fileName);
        Planet[] planets = new Planet[in.readInt()];
        in.readDouble(); // 省略行星半径
        // 通过循环创建行星对象并写入数组
        int i = 0;
        while (i < planets.length) {
            planets[i] = new Planet(in.readDouble(), in.readDouble(), in.readDouble(),
                                    in.readDouble(), in.readDouble(), in.readString());
            i++;
        }
        return planets;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];

        double radius = readRadius(filename);
        Planet[] planets = readPlanets(filename);

        //创建动画
        StdDraw.enableDoubleBuffering();
        int time = 0;
        while (time < T) {
            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];
            for (int i = 0; i < planets.length; i++) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }

            //更新行星位置, 速度, 加速度
            for (int i = 0; i < planets.length; i++) {
                planets[i].update(dt, xForces[i], yForces[i]);
            }

            // 画出背景图和所有行星
            StdDraw.setScale(-radius, radius);
            StdDraw.clear();
            StdDraw.picture(0, 0, "images/starfield.jpg");
            for (Planet p : planets) {
                p.draw();
            }

            StdDraw.show();

            StdDraw.pause(10);
            time += dt;
        }

        //行星时间结束后的最终状态
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }
}
