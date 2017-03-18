package net.cpszju.localization.utils;

public class FFTprepare {
	
	float[] data;
	float[] FFTsig;
	int num;
	int len1,len2;
	
	public FFTprepare(float[] data)
	{
		FFTprepareGo(data);
	}
	
	public int getnum()
	{
		return len2;
	}
	
	public float[] getsig()
	{
		return FFTsig;
	}
	
	private void FFTprepareGo(float[] data)
	{
		this.data = data;
		this.len1 = this.data.length;
		if(len1>2400)
		{
			while(len1>=2)
			{
				num++;
				len1 = len1/2;
			}
			
			double temp = Math.pow(Double.parseDouble(String.valueOf(2)), Double.parseDouble(String.valueOf(num)));
			this.len2 = (int)temp;
			if((this.data.length - this.len2  <6000)&&(num>15))
			{
				FFTsig = new float[len2];
				for(int i=0;i<len2;i++)
				{
					FFTsig[i] = this.data[i];
				}
			}
			else{
				FFTsig = new float[2*len2];
				for(int i=0;i<this.data.length;i++)
				{
					FFTsig[i] = this.data[i];
				}
			}
		}
		else{
			len2 = 4096;
			FFTsig = new float[len2];
			for(int i=0;i<this.data.length;i++)
			{
				FFTsig[i] = this.data[i];
			}
		}
	}

}
