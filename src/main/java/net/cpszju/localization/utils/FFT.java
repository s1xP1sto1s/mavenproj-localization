package net.cpszju.localization.utils;


public class FFT {
	
	float[] Re;
	float[] Im;
	int Flag;
	
	public float[] getresult()
	{
		int length = Re.length;
		float[] result = new float[length];
		for(int i=0;i<length;i++)
		{
			result[i] = (float) Math.sqrt(Re[i]*Re[i]+Im[i]*Im[i]);
		}
		return result;
	}
	
	public float[] getRe()
	{
		return Re;
	}
	
	public float[] getIm()
	{
		return Im;
	}
	
	public FFT(float Re[],float Im[],int Flag)
	{
		this.Re = Re;
		this.Im = Im;
		this.Flag = Flag;
		FFTgo(Re,Im,Flag);
	}
	
	private void FFTgo(float Re[], float Im[], int Flag) {
        if (Re == null || Im == null) {
            return;
        }
        int N = Re.length;
        if (N > Im.length) {
            N = Im.length;
        }
        int i, j, k, l, m, n1, n2;
        m = 0;
        double c, c1, e, s, s1, t, tr, ti;
        //最大长度是2的19次方
        for (j = 1, i = 1; i < 20; i++) {
            m = i;
            j = 2 * j;
            if (j == N) {
                break;
            }
        }
        n1 = N - 1;
        for (j = 0, i = 0; i < n1; i++) {
            if (i < j) {
                tr = Re[j];
                ti = Im[j];
                Re[j] = Re[i];
                Im[j] = Im[i];
                Re[i] = (float) tr;
                Im[i] = (float) ti;
            }
            k = N / 2;
            while (k < (j + 1)) {
                j = j - k;
                k = k / 2;
            }
            j = j + k;
        }
        n1 = 1;
        for (l = 1; l <= m; l++) {
            n1 = 2 * n1;
            n2 = n1 / 2;
            e = java.lang.Math.PI / n2;
            c = 1;
            s = 0;
            c1 = java.lang.Math.cos(e);
            s1 = -Flag * java.lang.Math.sin(e);
            for (j = 0; j < n2; j++) {
                for (i = j; i < N; i += n1) {
                    k = i + n2;
                    if (k > N - 1) {
                        k = N - 1;
                    }
                    tr = c * Re[k] - s * Im[k];
                    ti = c * Im[k] + s * Re[k];
                    Re[k] = (float) (Re[i] - tr);
                    Im[k] = (float) (Im[i] - ti);
                    Re[i] = (float) (Re[i] + tr);
                    Im[i] = (float) (Im[i] + ti);
                }
                t = c;
                c = c * c1 - s * s1;
                s = t * s1 + s * c1;
            }
        }
/*        if (Flag == -1)
        {
            for (i = 0; i < N; i++) {
                Re[i] /= (double) N;
                Im[i] /= (double) N;
            }
        }*/
        if (Flag == 1) { //
            for (i = 0; i < N; i++) {
                if (i > Re.length - 1) {
                    continue;
                }
                if (i > Im.length - 1) {
                    continue;
                }
                Re[i] /= (float) N;
                Im[i] /= (float) N;
            }
        }
    }

}
