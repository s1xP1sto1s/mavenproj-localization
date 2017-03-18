package net.cpszju.localization.utils;

public class FindPeak {
	private float[] cc;
	private int peakPlace;
	private float peak;
	
	public FindPeak(float[] cc){
		this.cc = reverse(cc);
		findPeakGo();
	}
	
	public int getPeakPlace(){
		return peakPlace;
	}
	
	public float getPeak(){
		return peak;
	}
	
	private void findPeakGo(){
		float max = cc[0];
		int maxp = 0;
		for(int i=1;i<cc.length;i++){
			if(cc[i]>max){
				max = cc[i];
				maxp = i;
			}
		}
		int n = 3000;
		while(n!=0){
			if(maxp-n>=0){
				for(int i=maxp-n;i<maxp;i++){
					if(cc[i]>max*0.3){
						peak = cc[i];
						peakPlace = i;
						break;
					}
				}
				break;
			}
			else{
				n /= 2;
			}
		}
	}
	
	private float[] reverse(float[] cc){
		float[] tmp = new float[cc.length];
		for(int i=0;i<cc.length;i++){
			tmp[i] = cc[cc.length-1-i];
		}
		return tmp;
	}
}
