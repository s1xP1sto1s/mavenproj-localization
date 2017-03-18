package net.cpszju.localization.algorithm;

import Jama.Matrix;
import net.cpszju.localization.utils.FFTcc;
import net.cpszju.localization.utils.FFTprepare;
import net.cpszju.localization.utils.FindPeak;
import net.cpszju.localization.utils.WaveFileReader;

public class TDOA {
	private String path1;
	private String path2;
	private String path3;
	private String path4;
	private String refer = "e:/data_refer.wav";
	
	private int[] beacon1;
	private int[] beacon2;
	private int[] beacon3;
	private int[] beacon4;
	
	private float[] result = new float[2];
	
	public TDOA(String[] path,int[][] beacon){
		this.path1 = path[0];
		this.path2 = path[1];
		this.path3 = path[2];
		this.path4 = path[3];
		
		this.beacon1 = beacon[0];
		this.beacon2 = beacon[1];
		this.beacon3 = beacon[2];
		this.beacon4 = beacon[3];
		
		localization();
	}
	
	public float[] getResult(){
		return result;
	}
	
	private int timeDelayEstimation(String path){
		WaveFileReader wr1 = new WaveFileReader(path);
		WaveFileReader wr2 = new WaveFileReader(refer);
		float[] DataSig = wr1.getData()[0];
		float[] DataRefer = wr2.getData()[0];
		int lensig = wr1.getDataLen();
		int lenrefer = wr2.getDataLen();
		
		FFTprepare fpp = new FFTprepare(DataSig);
		float[] FFTsig = fpp.getsig();
		
		FFTcc fcc = new FFTcc(FFTsig,DataRefer);
		fcc.FindFccMax();
		float[] rcc = fcc.getrcc();
		float max = fcc.getmaxvalue();
		int maxplace = fcc.getmaxplace();
		
		int lenrcc = rcc.length;
		final float[] rccn;
		if(lensig<2400)
		{
			lensig = 4096;
		}
		if(lensig<=lenrcc)
		{
			rccn = new float[lensig];
			for(int i=lensig-1; i>=0; i--)
			{
				rccn[i] = rcc[lenrcc-1];
				lenrcc = lenrcc-1;
			}
		}
		else{
			rccn = rcc; 
		}
		FindPeak fp = new FindPeak(rccn);
		int delay = fp.getPeakPlace();
		return delay;
	}
	
	private void localization(){
		int delay1 = timeDelayEstimation(path1);
		int delay2 = timeDelayEstimation(path2);
		int delay3 = timeDelayEstimation(path3);
		int delay4 = timeDelayEstimation(path4);
		
		float tdoa1 = ((float)(delay2-delay1)/44100)*340;
		float tdoa2 = ((float)(delay3-delay1)/44100)*340;
		float tdoa3 = ((float)(delay4-delay1)/44100)*340;
		
		Matrix a = new Matrix(new double[][]{
				{2*beacon1[0]-2*beacon2[0],2*beacon1[1]-2*beacon2[1],-2*tdoa1},
				{2*beacon1[0]-2*beacon3[0],2*beacon1[1]-2*beacon3[1],-2*tdoa2},
				{2*beacon1[0]-2*beacon4[0],2*beacon1[1]-2*beacon4[1],-2*tdoa3}
		});
		Matrix b = new Matrix(new double[][]{
				{Math.pow(tdoa1,2)+Math.pow(beacon1[0],2)-Math.pow(beacon2[0],2)+Math.pow(beacon1[1],2)-Math.pow(beacon2[1],2)},
				{Math.pow(tdoa2,2)+Math.pow(beacon1[0],2)-Math.pow(beacon3[0],2)+Math.pow(beacon1[1],2)-Math.pow(beacon3[1],2)},
				{Math.pow(tdoa3,2)+Math.pow(beacon1[0],2)-Math.pow(beacon4[0],2)+Math.pow(beacon1[1],2)-Math.pow(beacon4[1],2)}	
		});
		Matrix aTranspose = a.copy().transpose();
		Matrix tmp = aTranspose.times(a);
		tmp = tmp.inverse();
		tmp = tmp.times(aTranspose);
		tmp = tmp.times(b);
		
		result[0] = (float)tmp.get(0,0);
		result[1] = (float)tmp.get(1,0);
	}
}
