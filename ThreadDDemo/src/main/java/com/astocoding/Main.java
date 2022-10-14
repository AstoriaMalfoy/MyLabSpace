package com.astocoding;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

/**
 * @author litao34
 * @ClassName Main
 * @Description TODO
 * @CreateDate 2022/9/15-5:04 PM
 **/
public class Main {
    public static void main(String[] args) {
        new Main().showWindow();
    }

    public void showWindow(){
        JFrame jFrame = new JFrame();
        Container pane = jFrame.getContentPane();

        pane.setLayout(new BorderLayout());

        JPanel jPanel =  new JPanel(){
            public void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.BLACK);
                g2.fillRect(0, 0, getWidth(), getHeight());
                List<Triangle> triangles = buildTriangle();
                // step1 完成绘图
                g2.translate(getWidth()/2,getHeight()/2);
                g2.setColor(Color.WHITE);
                for (Triangle triangle : triangles) {
                    Path2D path2D = new Path2D.Double();
                    path2D.moveTo(triangle.v1.x,triangle.v1.y);
                    path2D.lineTo(triangle.v2.x,triangle.v2.y);
                    path2D.lineTo(triangle.v3.x,triangle.v3.y);
                    path2D.closePath();
                    g2.draw(path2D);
                }

            }
        };
        jPanel.addMouseMotionListener(new MouseMotionListener(){

            @Override
            public void mouseDragged(MouseEvent e) {
                double yi = 180.0 / jPanel.getHeight();
                double xi = 180.0 / jPanel.getWidth();

            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });

        pane.add(jPanel,BorderLayout.CENTER);
        jFrame.setSize(600,600);
        jFrame.setVisible(true);
    }


    // 预设三角形
    public List<Triangle> buildTriangle(){

        List trid = new ArrayList();
        trid.add(new Triangle(new Vertex(100,100,100),
                new Vertex(-100,-100,100),
                new Vertex(-100,100,-100),
                Color.WHITE));
        trid.add(new Triangle(new Vertex(100,100,100),
                new Vertex(-100,-100,100),
                new Vertex(100,-100,-100),
                Color.RED));
        trid.add(new Triangle(new Vertex(-100,100,-100),
                new Vertex(100,-100,-100),
                new Vertex(100,100,100),
                Color.GREEN));
        trid.add(new Triangle(new Vertex(-100,100,-100),
                new Vertex(100,-100,-100),
                new Vertex(-100,-100,100),
                Color.BLUE));
        return trid;
    }
}

// 存储坐标点
@Data
@AllArgsConstructor
class Vertex{
    double x;
    double y;
    double z;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Triangle{
    Vertex v1;
    Vertex v2;
    Vertex v3;
    Color color;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Matrix3{
    double[] value;
    Matrix3 multiply(Matrix3 other){
        double[] result = new double[9];
        for (int row = 0 ; row < 3 ; row ++){
            for (int col = 0 ; col < 3 ; col ++ ){
                for (int i = 0 ; i < 3 ; i++){
                    result[row*3+col] += this.value[row*3+i] * other.value[i*3+col];
                }
            }
        }
        return new Matrix3(result);
    }

    Vertex transform(Vertex in){
        return new Vertex(
                in.x * value[0] + in.y * value[3] + in.z * value[6],
                in.x * value[1] + in.y * value[4] + in.z * value[7],
                in.x * value[2] + in.y * value[5] + in.z * value[8]
        );
    }
}
