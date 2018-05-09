import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * A class that draws the stick figure.
 */
public class StickFigure extends JPanel {

    Boolean head = false;
    Boolean body = false;
    Boolean leftArm = false;
    Boolean rightArm = false;
    Boolean leftLeg = false;
    Boolean rightLeg = false;

    /**
     * Draws the gallow and any needed body parts.
     * @param g Graphics
     */
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

    /**
     * Sets the head to true so that it is drawn.
     */
    public void setHead() {
        head = true;
    }

    /**
     * Sets the body to true so that it is drawn.
     */
    public void setBody() {
        body = true;
    }

    /**
     * Sets the left arm to true so that it is drawn.
     */
    public void setLeftArm() {
        leftArm = true;
    }

    /**
     * Sets the right arm to true so that it is drawn.
     */
    public void setRightArm() {
        rightArm = true;
    }

    /**
     * Sets the left leg to true so that it is drawn.
     */
    public void setLeftLeg() {
        leftLeg = true;
    }

    /**
     * Sets the right leg to true so that it is drawn.
     */
    public void setRightLeg() {
        rightLeg = true;
    }
}
