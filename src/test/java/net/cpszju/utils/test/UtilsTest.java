package net.cpszju.utils.test;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import net.cpszju.localization.utils.FFTcc;
import net.cpszju.localization.utils.FFTprepare;
import net.cpszju.localization.utils.FindPeak;
import net.cpszju.localization.utils.FindPeaksNew;
import net.cpszju.localization.utils.WaveFileReader;

import org.junit.Test;

import Jama.Matrix;

public class UtilsTest {
	@Test
	public void test(){
		String path1 = "e:/tdoadata/sigD.wav";
		String path2 = "e:/data_refer.wav";

		WaveFileReader wr1 = new WaveFileReader(path1);
		WaveFileReader wr2 = new WaveFileReader(path2);
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
		
		System.out.println(delay);
	}
	
	@Test
	public void jama(){
		//3ÐÐ1ÁÐ
		Matrix m = new Matrix(new double[][]{{123},{456},{789}});
		Matrix b = m.copy();
		System.out.println("ok");
	}
}
