import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class StickFigure extends JPanel {

    Boolean head = false;
    Boolean body = false;
    Boolean leftArm = false;
    Boolean rightArm = false;
    Boolean leftLeg = false;
    Boolean rightLeg = false;

    public void paintComponent(Graphics g) {
        g.drawLine(10,275,170,275);
        g.drawLine(90,275,90,50);
        g.drawLine(90,50,160,50);
        g.drawLine(160,50,160,80);

        Graphics2D g2 = (Graphics2D) g;

        if (head) {
            Ellipse2D.Double circle = new Ellipse2D.Double(140,80,40,40);
            g2.draw(circle);
        }
        if (body) {
            g.drawLine(160,120,160,190);
        }
        if (leftLeg) {
            g.drawLine(160,190,135,230);
        }
        if (rightLeg) {
            g.drawLine(160,190,185,230);
        }
        if (leftArm) {
            g.drawLine(160,155,135,135);
        }
        if (rightArm) {
            g.drawLine(160,155,185,135);
        }
    }

    public void setHead() {
        head = true;
    }

    public void setBody() {
        body = true;
    }

    public void setLeftArm() {
        leftArm = true;
    }

    public void setRightArm() {
        rightArm = true;
    }

    public void setLeftLeg() {
        leftLeg = true;
    }

    public void setRightLeg() {
        rightLeg = true;
    }
}
