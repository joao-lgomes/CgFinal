/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Utils.Vertex;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.GLU;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author herba
 */
public class Renderer implements GLEventListener {
    private float R;
    private float G;
    private float B;
    private float transX = 0f;
    private float transY = 0f;
    private float alpha = 0f; //rotação
    private float sX = 1f;
    private float sY = 1f; //escala em Y
    
    
    private String primitive;
    
    private List<Vertex> vertex;
    
    private boolean draw;
    
    private GLAutoDrawable drawable;

    public String getPrimitive() {
        return primitive;
    }

    public void setPrimitive(String primitive) {
        this.primitive = primitive;
    }

    public List<Vertex> getVertex() {
        return vertex;
    }

    public void setVertex(List<Vertex> vertex) {
        this.vertex = vertex;
    }

    public boolean isDraw() {
        return draw;
    }

    public void setDraw(boolean draw) {
        this.draw = draw;
    }
    
    public void clearVertexList(){
        this.vertex.clear();
    }
    
    private int getGLPrimitive(String primitive){
        
        if(primitive != null){
            switch(primitive){
                case "GL_LINES":
                    return GL2.GL_LINES;
                case "GL_POINTS":
                    return GL2.GL_POINTS;
                case "GL_LINE_STRIP":
                    return GL2.GL_LINE_STRIP;
                case "GL_LINE_LOOP":
                    return GL2.GL_LINE_LOOP;
                case "GL_TRIANGLES":
                    return GL2.GL_TRIANGLES;
                case "GL_TRIANGLE_STRIP":
                    return GL2.GL_TRIANGLE_STRIP;
                case "GL_TRIANGLE_FAN":
                    return GL2.GL_TRIANGLE_FAN;
                case "GL_QUADS":
                    return GL2.GL_QUADS;
                case "GL_QUAD_STRIP":
                    return GL2.GL_QUAD_STRIP;
                case "GL_POLYGON":
                    return GL2.GL_POLYGON;
            }
        }
        
        return GL.GL_LINES;
        
    }
    
    public void display(float R, float G, float B, float transX, float transY, float alpha, float sX, float sY){
        this.R=R;
        this.G=G;
        this.B=B;
        this.transX = transX;
        this.transY = transY;
        this.sX = sX;
        this.sY = sY;
        this.alpha = alpha;
        //this.drawing(drawable, R, G,B);
        this.display(drawable);
    }
    
    
    @Override
    public void display(GLAutoDrawable drawable) {
        
        GL2 gl = drawable.getGL().getGL2();
        
        this.drawable = drawable;
        System.out.println("R: "+this.R);
        System.out.println("G: "+this.G);
        System.out.println("B: "+this.B);
        //  Limpa a tela e o Z-Buffer
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT|GL2.GL_DEPTH_BUFFER_BIT);
        gl.glColor3f(this.R, this.G, this.B);
        gl.glPushMatrix();
	gl.glTranslatef(transX, transY, 0);
	gl.glRotatef(alpha, 0, 0, 1.0f);//rotaciona em 90 graus
	gl.glScalef(sX, sY, 0);
        
        if(draw) generateImage(gl);
        
	gl.glPopMatrix();
        
        

        gl.glFlush();

    }
    
    private void generateImage(GL2 gl){
        System.out.println("Lista de pontos do desenho: ");
        gl.glBegin(getGLPrimitive(getPrimitive()));
        for(Vertex v: vertex){
            System.out.println("Point " + v.getX() + " " + v.getY());
            gl.glVertex2f(v.getX(), v.getY());
        }
        gl.glEnd();
    }
    
    private void done(GL2 gl){
        gl.glEnd();
        gl.glFlush();
    }
    
    @Override
    public void dispose(GLAutoDrawable arg0) {

    }

    @Override
    public void init(GLAutoDrawable drawable) {

            GL2 gl = drawable.getGL().getGL2();

            gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            gl.glMatrixMode(gl.GL_MATRIX_MODE);
            gl.glLoadIdentity();

            GLU glu = new GLU();
            glu.gluOrtho2D(-100f, 100f, -100f, 100f);
            
            vertex = new ArrayList<>();
            

    }

    @Override
    public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
    }

    public GLJPanel getPanel(){

        GLProfile profile = GLProfile.get(GLProfile.GL2);

        GLCapabilities caps = new GLCapabilities(profile);

        GLJPanel glJpanel = new GLJPanel(caps); 
        glJpanel.addGLEventListener(this); 


        return glJpanel;
    }

}
