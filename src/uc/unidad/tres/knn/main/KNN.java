package uc.unidad.tres.knn.main;

import java.applet.Applet;
import java.awt.event.ActionListener;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
                                    
    
public class KNN extends Applet implements ActionListener{
    /**
	 * 
	 */
	private static final long serialVersionUID = -2540033371566551134L;
	private TextField inputN;
	private TextField inputComplexity;
	private TextField inputK;
	private TextField inputKNN;
    private Button Step1Button;
    private Button Step2Button;
    private Button Step3Button;
	private Label errLabel; 
    private KNNCanvas theKNNCanvas;
	private Canvas theTruthCanvas;
    
    private int n;
    private int complejidad;
    private int k;
    private int knn;
    private boolean[][] truth;
    
   
    Muestra[] muestras;
    
    Distancia[] distancias;
    
    @Override
    public void init(){
		GridBagLayout bag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
        this.setLayout(bag);

		Label label = new Label("Paso 1: Tamaño del campo(10--80):");
		bag.setConstraints(label, c);
		this.add(label);
        inputN = new TextField("80", 2);
		bag.setConstraints(inputN, c);
        this.add(inputN);
		
		label = new Label("      Complejidad(1--100):");
		bag.setConstraints(label, c);
		this.add(label);
        inputComplexity = new TextField("5", 2);
		bag.setConstraints(inputComplexity, c);
        this.add(inputComplexity);
        Step1Button = new Button("Crear una verdad");
		Step1Button.addActionListener(this);
		c.anchor = GridBagConstraints.WEST;
		c.gridwidth = GridBagConstraints.REMAINDER; 
		bag.setConstraints(Step1Button, c);
		c.anchor = GridBagConstraints.CENTER;
		this.add(Step1Button);
		
		label = new Label("Paso 2: muestras(1--2000):");
		c.gridwidth = 1; // reset to default
		bag.setConstraints(label, c);
		this.add(label);
        inputK = new TextField("10", 2);
		bag.setConstraints(inputK, c);
		this.add(inputK);
        Step2Button = new Button("Generar Muestras");
		Step2Button.addActionListener(this);
		c.anchor = GridBagConstraints.WEST;
		c.gridwidth = GridBagConstraints.REMAINDER; 
		bag.setConstraints(Step2Button, c);
		c.anchor = GridBagConstraints.CENTER;
		this.add(Step2Button);
		
		label = new Label("Paso 3: kNN(1--100):");
		c.anchor = GridBagConstraints.WEST;
		c.gridwidth = 1; // reset to default
		bag.setConstraints(label, c);
		c.anchor = GridBagConstraints.CENTER;
		this.add(label);
        inputKNN = new TextField("1", 2);
		bag.setConstraints(inputKNN, c);
		this.add(inputKNN);
        Step3Button = new Button("Clasificar");
		Step3Button.addActionListener(this);
		this.add(Step3Button);
		errLabel = new Label(" ");
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = GridBagConstraints.REMAINDER; 
		bag.setConstraints(errLabel, c);
		this.add(errLabel);
		
		theTruthCanvas = new TruthCanvas();
		theTruthCanvas.setSize(404, 404);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0;
		c.weighty = 1;
		c.gridwidth = 1;
		c.gridwidth = GridBagConstraints.RELATIVE; 
		c.gridheight = GridBagConstraints.REMAINDER; 
		bag.setConstraints(theTruthCanvas, c);
		this.add(theTruthCanvas);
		
		theKNNCanvas = new KNNCanvas();
		theKNNCanvas.setSize(404, 404);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = GridBagConstraints.REMAINDER; 
		c.gridheight = GridBagConstraints.REMAINDER; 
		bag.setConstraints(theKNNCanvas, c);
		this.add(theKNNCanvas);

	}
    
    @Override
    public void actionPerformed(ActionEvent e){
    	if (e.getSource() == Step1Button){
    		n = new Integer(inputN.getText()).intValue();
    		complejidad = new Integer(inputComplexity.getText()).intValue();
    		k=0; 
    		truth = new boolean[n][n];
    		int x, y;
    		for (x=0; x<n; x++)
    			for (y=0; y<n; y++)
    				truth[x][y] = true;
    		for (int i = 0; i < complejidad; i++){
    			double w1, w2, b;
    			w1 = Math.random() * 2 - 1;
    			w2 = Math.random() * 2 - 1;
    			b  = Math.random() * n/2;
    			for (x = 0; x < n; x++){
    				for (y = 0; y < n; y++){
    					Double d = w1 * (x-n/2) + w2 * (y-n/2) + b;
    					if ( d > 0){
    						truth[x][y] = !truth[x][y];
    					}	
    				}
    			}			
    		}
    		theTruthCanvas.repaint();
		} else if (e.getSource() == Step2Button){
			k = new Integer(inputK.getText()).intValue();
			muestras = new Muestra[k];
			int i;
			for (i=0; i < k; i++){   
				muestras[i] = new Muestra();
				muestras[i].x = (int)(Math.random()*n);
				muestras[i].y = (int)(Math.random()*n);
				muestras[i].label = truth[muestras[i].x][muestras[i].y];
			}
	 		theTruthCanvas.repaint();
		} else if (e.getSource() == Step3Button){
			int i;	
			knn = new Integer(inputKNN.getText()).intValue();
			distancias = new Distancia[knn];
			for (i = 0; i < knn; i++){   
				distancias[i] = new Distancia();
			}
			theKNNCanvas.repaint();
		}
	}    

    @Override
    public void start(){
    	
    }
    
    
    class KNNCanvas extends Canvas {
        
        /**
		 * 
		 */
		private static final long serialVersionUID = 1127498400934854400L;

		@Override
		public void paint(Graphics g) {
            int m=5; 
            
            g.setColor(Color.black);
            System.out.println(n);            
            g.drawRect(0, 0, n*m+1, n*m+1);
            
            int error = 0; 
            for (int x = 0; x < n; x++){
            	for (int y = 0; y < n; y++){
                     // find the knn
                     for (int i = 0; i < k; i++){
                         double dist = (muestras[i].x - x) * (muestras[i].x - x) + 
                        		       (muestras[i].y - y) * (muestras[i].y - y);
                         if (i<knn){
                        	 distancias[i].d = dist;
                        	 distancias[i].label = muestras[i].label;
                         } else {
                        	 double biggestd = distancias[0].d;
                             int biggestindex = 0;
                             for (int a=1; a<knn; a++)
                                 if (distancias[a].d > biggestd){
                                	 biggestd = distancias[a].d;
                                	 biggestindex = a;
                                 }
                             if (dist < biggestd){
                            	 distancias[biggestindex].d = dist;
                            	 distancias[biggestindex].label = muestras[i].label;
                             }
                         }
                     }
                     
                     int nT=0;
                     int nF=0;
					 Boolean clasificacion;
                     for (int i=0; i<knn; i++){
                         if (distancias[i].label == true)
                             nT++;
                         else
                             nF++;
                     }
					 
                     if (nT < nF)
						 clasificacion = false;
					 else if (nT>nF)
						 clasificacion = true;
					 else
						 clasificacion = Math.random() < 0.5; 
					 
                     if (!clasificacion)
                         g.setColor(Color.white);
                     else
                         g.setColor(Color.green);
                     
                     g.fillRect(m*x+1, m*y+1, m, m);
					 
					 if (clasificacion != truth[x][y]){
						 error ++;
					 }
            	}
            }
			
			if (n>0){
				errLabel.setText(" Rango del Error = " + (float)error/n/n*100 + "%");
			}	
             
			g.setColor(Color.black);
			for (int i=0; i<k; i++){
				if (muestras[i].label == true){
					 g.drawOval(muestras[i].x * m+1, 
							    muestras[i].y * m+1, m, m);
				} else {
					 g.drawLine(muestras[i].x * m+(m+1)/2, 
							    muestras[i].y * m+1, 
							    muestras[i].x * m+(m+1)/2, 
							    muestras[i].y * m+m);
					 
					 g.drawLine(muestras[i].x * m+1, 
							    muestras[i].y * m+(m+1)/2, 
							    muestras[i].x * m+m, 
							    muestras[i].y * m+(m+1)/2);
				}
			}
        }
    }

    class TruthCanvas extends Canvas {
        
        /**
		 * 
		 */
		private static final long serialVersionUID = 4186398159261546993L;

		public void paint(Graphics g) {
            int m=5; 
            
            g.setColor(Color.black);
            System.out.println(n);            
            g.drawRect(0, 0, n*m+1, n*m+1);
            
        
            for (int x=0; x<n; x++){
            	for (int y=0; y<n; y++){
            		if (truth[x][y] == false){
                         g.setColor(Color.white);
            		} else {
                         g.setColor(Color.green);
            		}
                    g.fillRect(m * x+1, m * y+1, m, m);
                }
            }
            
            g.setColor(Color.black);
            for (int i=0; i<k; i++){
            	if (muestras[i].label == true)
            		g.drawOval(muestras[i].x * m+1, 
            				   muestras[i].y * m+1, m, m);
            	else{
            		g.drawLine(muestras[i].x * m+(m+1)/2, 
            				   muestras[i].y * m+1, 
            				   muestras[i].x * m+(m+1)/2, 
            				   muestras[i].y * m+m);
            		
            		g.drawLine(muestras[i].x *m+1, 
            				   muestras[i].y *m+(m+1)/2, 
            				   muestras[i].x *m+m, 
            				   muestras[i].y *m+(m+1)/2);
                }
            }
		}
    }
}
