public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    private static final double G = 6.67e-11;

    public Planet(double xP, double yP, double xV,
                  double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    /** 计算两个星球之间的距离 */
    public double calcDistance(Planet p) {
        double dx = p.xxPos - xxPos;
        double dy = p.yyPos - yyPos;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /** 计算两个星球间引力的大小, 由 F = G * m_1 * m_2 / r^2 确定, 其中 r 指距离 */
    public double calcForceExertedBy(Planet p) {
        double r = calcDistance(p);
        return (G * mass * p.mass) / (r * r);
    }

    /** 将力分解到 x 轴上, 由 F_x = F * dx / r 确定. dx 指水平距离差, 需要注意正负 */
    public double calcForceExertedByX(Planet p) {
        double dx = p.xxPos - xxPos;
        return calcForceExertedBy(p) * dx / calcDistance(p);
    }

    /** 将力分解到 y 轴上, 由 F_y = F * dy / r 确定. dy 指垂直距离差, 需要注意正负 */
    public double calcForceExertedByY(Planet p) {
        double dy = p.yyPos - yyPos;
        return calcForceExertedBy(p) * dy / calcDistance(p);
    }

    /** 计算 x 轴的净力(net force), 由所有分解到 x 轴上的力相加得到 */
    public double calcNetForceExertedByX(Planet[] planets) {
        double xNetForce = 0;
        for (Planet p : planets) { // 增强 for 循环, https://docs.oracle.com/javase/1.5.0/docs/guide/language/foreach.html
            if (equals(p)) {
                continue;
            }
            xNetForce += calcForceExertedByX(p);
        }
        return xNetForce;
    }

    /** 计算 y 轴的净力(net force), 由所有分解到 y 轴上的力相加得到 */
    public double calcNetForceExertedByY(Planet[] planets) {
        double yNetForce = 0;
        for (Planet p : planets) {
            if (equals(p)) {
                continue;
            }
            yNetForce += calcForceExertedByY(p);
        }
        return yNetForce;
    }

    /** 通过时间(极短)和 x 轴, y 轴的净力计算星球加速度从而更新星球的位置 */
    public void update(double dt, double fX, double fY) {
        double aNetX = fX / mass; // 计算在 x 轴(水平)上的加速度
        double aNetY = fY / mass; // 计算在 y 轴(垂直)上的加速度
        xxVel = xxVel + dt * aNetX; // 更新水平移动速度
        yyVel = yyVel + dt * aNetY; // 更新垂直移动速度
        xxPos = xxPos + dt * xxVel; // 更新 x 轴坐标
        yyPos = yyPos + dt * yyVel; // 更新 y 轴坐标
    }

    public void draw() {
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }
}
