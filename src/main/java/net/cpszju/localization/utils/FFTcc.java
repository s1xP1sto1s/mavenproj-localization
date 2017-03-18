package net.cpszju.localization.utils;


public class FFTcc {
	
	float[] datasig;//预处理后的信号
	float[] dataref;//参考信号
	int len;//fft变换的长度
	float[] rcc;//频域互相关之后的结果
	
	float maxvalue;
	int maxplace;
	
	public FFTcc(float[] datasig,float[] dataref)
	{
		FFTccGo(datasig,dataref);
	}
	
	public float[] getrcc()
	{
		return rcc;
	}
	
	public float getmaxvalue()
	{
		return maxvalue;
	}
	
	public int getmaxplace()
	{
		return maxplace;
	}
	
	private void FFTccGo(float[] datasig,float[] dataref)
	{
		this.datasig = datasig;
		this.dataref = dataref;
		len = this.datasig.length;
		
		float[] datasigIm = new float[len];
		FFT fft = new FFT(datasig,datasigIm,1);
		float[] DataRe = fft.getRe();
		float[] DataIm = fft.getIm();
		
		float[] refersig = new float[len];
		for(int i=0;i<dataref.length;i++)
		{
			refersig[i] = dataref[i];
		}
		float[] refersigIm = new float[len];
		FFT fft2 = new FFT(refersig,refersigIm,1);
		float[] ReferRe = fft2.getRe();
		float[] ReferIm = fft2.getIm();
		
		float[] ccRe = new float[len];
		float[] ccIm = new float[len];
		for(int i=0;i<len;i++)
		{
			ccRe[i] = DataRe[i]*ReferRe[i] + DataIm[i]*ReferIm[i];//ac+bd
			ccIm[i] = DataIm[i]*ReferRe[i] - DataRe[i]*ReferIm[i];//bc-ad
		}
		
		FFT fft3 = new FFT(ccRe,ccIm,1);
		rcc = fft3.getresult();
	}
	
	public void FindFccMax()
	{
		this.maxvalue = rcc[0];
		this.maxplace = 0;
		for(int i=1;i<rcc.length;i++)
		{
			if(rcc[i]>maxvalue)
			{
				maxvalue = rcc[i];
				maxplace = i;
			}
		}
	}

}
