package net.cpszju.localization.utils;

public class FindPeaksNew {
	
	float[] data;
	static int[] result;
	
	public FindPeaksNew(float[] data)
	{
		this.data = data;
		result = new int[2];
		result = findpeaks(data);
	}
	
	public int[] getresult()
	{
		return result;
	}
	
//	//返回信号到达时间(只检测两个最大峰)(直接调用次函数即可)
//		//第一个返回参数为第一个广播声信号到达时间，第二个返回参数为第二个广播声信号到达时间
//		public static int[] findpeaks(float[] value){
//			int[] index=new int[2];
//			int LenValue=value.length;
//			//互相关结果反转
//			float[] sig_tem=new float[LenValue];
//			for(int i=0;i<LenValue;i++){
//				sig_tem[i]=value[LenValue-i-1];
//			}
//			sig_tem[0]=0;
//			float[] data=ShortEnergy(sig_tem);
//			int len=data.length;
//			int max=0;
//			int number=0;
//			int first=0;
//			int second=0;
//
//			//最大峰
//			for(int i=1;i<len;i++){
//				if(data[max]<data[i]){
//					max=i;
//				}
//			}
//			first=max;
//			//次大峰
//			max=0;
//			for(int i=1;i<first-30;i++){
//				if(data[max]<data[i]){
//					max=i;
//				}
//			}
//			for(int i=first+30;i<len;i++){
//				if(data[max]<data[i]){
//					max=i;
//				}
//			}
//			second=max;
//			
//			first=first*150;
//			second=second*150;
//			
//			//避免溢出
//			if(first<2000||second<2000||first+5000>LenValue-1||second+5000>LenValue-1){
//				index[0]=0;index[1]=0;
//				return index;
//			}
//			
//			//互相关峰值检测
//			max=first-2000;
//			for(int i=first-2000;i<first+5000;i++){
//				if(sig_tem[max]<sig_tem[i]){
//					max=i;
//				}
//			}
//			first=max;
//			max=second-2000;
//			for(int i=second-2000;i<second+5000;i++){
//				if(sig_tem[max]<sig_tem[i]){
//					max=i;
//				}
//			}
//			second=max;
//			if(first>second){
//				max=first;
//				first=second;
//				second=max;
//			}
//			
//			//返回结果
//			index[0]=n_get(sig_tem,first);
//			index[1]=n_get(sig_tem,second);
//			return index;
//		}
	
	public static int[] findpeaks(float[] value){
		int[] index=new int[2];
		int LenValue=value.length;
		//互相关结果反转
		float[] sig_tem=new float[LenValue];
		for(int i=0;i<LenValue;i++){
			sig_tem[i]=value[LenValue-i-1];
		}
		sig_tem[0]=0;
		float[] data=ShortEnergy(sig_tem);
		int len=data.length;
		int max=0;
		int number=0;
		int first=0;
		int second=0;
		data[0]=0;
		
		//最大峰
		for(int i=1;i<len;i++){
			if(data[max]<data[i]){
				max=i;
			}
		}
		first=max;
		//次大峰
		max=0;
		for(int i=1;i<first-50;i++){
			if(data[max]<data[i]){
				max=i;
			}
		}
		for(int i=first+50;i<len;i++){
			if(data[max]<data[i]){
				max=i;
			}
		}
		second=max;
		
		first=first*150;
		second=second*150;
		
		int start=0,close=0;
		if(first<2000){
			start=0;
		}else{
			start=first-2000;
		}
		if(first+5000>LenValue-1){
			close=LenValue-1;
		}else{
			close=first+5000;
		}
			
		
		//互相关峰值检测
		if(first<2000)
			max=0;
		else
			max=first-2000;
		for(int i=start;i<close;i++){
			if(sig_tem[max]<sig_tem[i]){
				max=i;
			}
		}
		first=max;
		
		if(second<2000){
			start=0;
		}else{
			start=second-2000;
		}
		if(second+5000>LenValue-1){
			close=LenValue-1;
		}else{
			close=second+5000;
		}
		if(second<2000)
			max=0;
		else
			max=second-2000;
		for(int i=start;i<close;i++){
			if(sig_tem[max]<sig_tem[i]){
				max=i;
			}
		}
		second=max;
		if(first>second){
			max=first;
			first=second;
			second=max;
		}
		
		//返回结果
		index[0]=n_get(sig_tem,first);
		index[1]=n_get(sig_tem,second);
		return index;
	}

		//短时能量比最大峰峰值检测
		public static float[] ShortEnergy(float[] sig_tem){
			int L=2000;
			int S=200;
			int step=150;
			int len=sig_tem.length;
			int seg=(len-L)/step;
			float[] LP=new float[seg];
			float[] SP=new float[seg];
			float[] RT=new float[seg];
			for(int i=0;i<seg;i++){
				float sumLP=0;
				for(int j=i*step;j<i*step+L;j++){
					sumLP=sumLP+sig_tem[j]*sig_tem[j];
				}
				LP[i]=sumLP/L;
				float sumSP=0;
				for(int j=i*step+L-S;j<i*step+L;j++){
					sumSP=sumSP+sig_tem[j]*sig_tem[j];
				}
				SP[i]=sumSP/L;
				RT[i]=SP[i]/LP[i];
			}
			return RT;
		}
		
		//精确信号到达时间估计
		public static int n_get(float[] value,int key){
			int start=key;
			float replace=(float) (value[key]*0.8);
			int index=key;
			float sum=0;
			while(index>0){
				if(value[index]>=replace){
		            sum=0;
		            start=index;
				}else{
		            sum=sum+1;
				}
		        if(sum>=2000){
		            break;
		        }
		        index=index-1;
			}
			if(start<=3500){
		        return key;
			}
			float mean=0,sgm=0;
		    for(int i=start-3500;i<=start-3000;i++){
		        mean=mean+value[i];
		        sgm=sgm+value[i]*value[i];
		    }
		    mean=mean/501;
		    sgm=(float)Math.sqrt((double)(sgm/501-mean*mean));
		    float SNR=value[key]/(mean+3*sgm);
			
		    if(SNR>10){
		        replace=(float) (0.3*value[key]);
		        for(int i=start-3000;i<=start;i++){
		            if(value[i] >= replace){
		                return i;
		            }
		        }
		    }
		    
		    if(SNR>5){
		        replace=(float) (mean+sgm*5+(value[key]-mean-sgm*5)*0.3);
		        for(int i= start-3000;i<=start;i++){
		            if(value[i] >= replace){
		                return i;
		            }
		        }
		    }
		    
		    if(SNR>2){
		        replace=(float) (mean+sgm*3+(value[key]-mean-sgm*3)*0.4);
		        for(int i = start-3000;i<=start;i++){
		            if(value[i]>= replace){
		                return i;
		            }
		        }
		    }
		    
		    replace=(float) (mean+sgm*3+(value[key]-mean-sgm*3)*0.6);
		    for(int i = start-3000;i<=start;i++){
		        if(value[i]>= replace){
		            return i;
		        }
		    }
		    return start;
		}

}
